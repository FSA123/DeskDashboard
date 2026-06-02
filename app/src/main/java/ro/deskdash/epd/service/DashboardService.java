package ro.deskdash.epd.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.deskdash.epd.BuildConfig;
import ro.deskdash.epd.R;
import ro.deskdash.epd.calendar.CalendarCache;
import ro.deskdash.epd.calendar.CalendarClient;
import ro.deskdash.epd.calendar.CalendarEvent;
import ro.deskdash.epd.cache.WeatherCache;
import ro.deskdash.epd.epd.EpdConnector;
import ro.deskdash.epd.render.DashboardRenderer;
import ro.deskdash.epd.render.Layout;
import ro.deskdash.epd.time.TimeTicker;
import ro.deskdash.epd.weather.WeatherClient;
import ro.deskdash.epd.weather.WeatherSnapshot;
import ro.deskdash.epd.wifi.WifiConnector;

public class DashboardService extends Service {
    private static final String TAG = "DashboardService";
    private static final String CHANNEL_ID = "desk_dashboard";
    private static final int    NOTIF_ID   = 1;

    private static final TimeZone TZ_BUCHAREST = TimeZone.getTimeZone("Europe/Bucharest");
    private static final int W = BuildConfig.DISPLAY_WIDTH;
    private static final int H = BuildConfig.DISPLAY_HEIGHT;

    private final Handler        mainHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService worker     = Executors.newSingleThreadExecutor();

    private EpdConnector      epd;
    private DashboardRenderer renderer;
    private TimeTicker        timeTicker;
    private WeatherClient     weatherClient;
    private WeatherCache      weatherCache;
    private CalendarClient    calendarClient;
    private CalendarCache     calendarCache;
    private WifiConnector     wifi;

    // Last-rendered state — worker-thread-only access
    private String          lastClockStr  = null;
    private String          lastDateStr   = null;
    private WeatherSnapshot lastWeather   = null;
    private String          lastCalKey    = "";
    private int             consecutivePartials = 0;
    private long            lastFullRefreshMs   = 0L;
    private long            lastDeepCleanDay    = -1L;

    // Latest data — written on main thread, read on worker
    private volatile WeatherSnapshot      pendingWeather = WeatherSnapshot.placeholder();
    private volatile List<CalendarEvent>  pendingEvents  = Collections.emptyList();
    private volatile boolean              weatherNetworkUpdated  = false;
    private volatile boolean              calendarNetworkUpdated = false;

    @Override
    public void onCreate() {
        super.onCreate();
        renderer       = new DashboardRenderer(this, W, H);
        weatherCache   = new WeatherCache(this);
        weatherClient  = new WeatherClient(weatherCache);
        calendarCache  = new CalendarCache(this);
        calendarClient = new CalendarClient(BuildConfig.CALENDAR_ICAL_URL, calendarCache);
        epd            = new EpdConnector(this);
        wifi           = new WifiConnector(this, epd);

        epd.setOnConnectedCallback(this::onEpdConnected);

        createNotificationChannel();
        startForeground(NOTIF_ID, buildNotification());
        Log.i(TAG, "Service started — binding EPD");
        epd.bind();
        wifi.start();
    }

    private void onEpdConnected() {
        worker.execute(() -> {
            Log.i(TAG, "EPD connected — disabling panel auto-refresh");
            epd.disableAutoRefresh();
            epd.setNtpAndTimezone(BuildConfig.NTP_SERVER, "Europe/Bucharest");

            String info = epd.getEpdInfo();
            Log.i(TAG, "EPD info: " + (info != null ? info : "(null — normal on EPC3566)"));

            lastWeather = pendingWeather;
            renderAndSendFull("initial");

            timeTicker = new TimeTicker(mainHandler, this::onMinuteTick);
            timeTicker.start();

            long weatherPollMs  = BuildConfig.WEATHER_POLL_MINUTES  * 60_000L;
            long calendarPollMs = BuildConfig.CALENDAR_POLL_MINUTES * 60_000L;
            weatherClient.startPolling(weatherPollMs, this::onWeatherUpdated);
            calendarClient.startPolling(calendarPollMs, this::onCalendarUpdated);
        });
    }

    private void onMinuteTick()                          { worker.execute(this::tick); }
    private void onWeatherUpdated(WeatherSnapshot snap)  { pendingWeather = snap; weatherNetworkUpdated = true; worker.execute(this::tick); }
    private void onCalendarUpdated(List<CalendarEvent> e){ pendingEvents = e; calendarNetworkUpdated = true; worker.execute(this::tick); }

    // ─── Main tick ────────────────────────────────────────────────────────

    private void tick() {
        Calendar cal = Calendar.getInstance(TZ_BUCHAREST);
        String clockStr = DashboardRenderer.buildClockString(cal);
        String dateStr  = DashboardRenderer.buildDateString(cal);
        long   nowMs    = System.currentTimeMillis();

        WeatherSnapshot      weather = pendingWeather;
        List<CalendarEvent>  events  = pendingEvents;

        // Split clock into hour and minute for fine-grained partial refresh
        String newHour = clockStr.substring(0, 2);
        String newMin  = clockStr.substring(3);
        String oldHour = lastClockStr != null ? lastClockStr.substring(0, 2) : "";
        String oldMin  = lastClockStr != null ? lastClockStr.substring(3)    : "";
        boolean hourChanged    = !newHour.equals(oldHour);
        boolean minuteChanged  = !newMin.equals(oldMin);
        boolean clockChanged   = hourChanged || minuteChanged;
        boolean dateChanged    = !dateStr.equals(lastDateStr);
        boolean weatherCodeChanged = lastWeather == null
                ? (weather != null)
                : (weather != null && lastWeather.weatherCode != weather.weatherCode);
        boolean weatherChanged = (lastWeather == null && weather != null)
                || (lastWeather != null && weather != null
                    && (weatherCodeChanged
                        || Math.round(lastWeather.temperatureC) != Math.round(weather.temperatureC)
                        || Math.round(lastWeather.maxTempC)     != Math.round(weather.maxTempC)
                        || Math.round(lastWeather.minTempC)     != Math.round(weather.minTempC)));

        String  calKey     = calendarDisplayKey(events, nowMs);
        boolean calChanged = !calKey.equals(lastCalKey);

        if (!clockChanged && !dateChanged && !weatherChanged && !calChanged) return;

        boolean forceFull = isForcedFull(cal, dateChanged, weatherCodeChanged);
        if (forceFull) {
            renderAndSendFull("forced");
        } else {
            // Collect individual changed zones — do NOT union across non-overlapping regions.
            // Disjoint zones (e.g. clockMinute on the left + nextEvent on the right) are sent
            // as separate sendPartImageBitmap calls to avoid a single huge bounding rect.
            java.util.List<Rect> zones = new java.util.ArrayList<>();
            Layout l = new Layout(W, H);
            if (hourChanged)         zones.add(Layout.alignToEightPixels(l.clock,        W));
            else if (minuteChanged)  zones.add(Layout.alignToEightPixels(l.clockMinute, W));
            if (clockChanged)        zones.add(Layout.alignToEightPixels(l.footer,       W)); // daylight remaining
            if (dateChanged)         zones.add(Layout.alignToEightPixels(l.date,         W));
            if (weatherChanged) zones.add(Layout.alignToEightPixels(weatherBounds(), W));
            if (calChanged)     zones.add(Layout.alignToEightPixels(l.nextEvent,    W));

            if (zones.isEmpty()) return;

            long sumArea  = 0;
            for (Rect z : zones) sumArea += (long) z.width() * z.height();
            long total = (long) W * H;
            if ((float) sumArea / total > BuildConfig.PARTIAL_AREA_MAX_FRACTION) {
                renderAndSendFull("area-cap");
            } else {
                renderAndSendPartialZones(clockStr, dateStr, weather, events, zones);
            }
        }

        lastClockStr = clockStr;
        lastDateStr  = dateStr;
        lastWeather  = weather;
        lastCalKey   = calKey;
    }

    // ─── Render + send ────────────────────────────────────────────────────

    private void renderAndSendFull(String reason) {
        Log.i(TAG, "Full refresh (" + reason + ") — " + W + "x" + H);
        Calendar cal = Calendar.getInstance(TZ_BUCHAREST);
        boolean stale = lastWeather != null
                && lastWeather.fetchedAtMs > 0
                && lastWeather.isStale(30 * 60_000L);
        int panelTemp = epd.getTconTemperature();

        Bitmap full = renderer.renderFull(
                DashboardRenderer.buildClockString(cal),
                DashboardRenderer.buildDateString(cal),
                lastWeather, panelTemp, stale, pendingEvents);

        int result = epd.sendImageBitmap(full);
        if (result != 0) {
            Log.w(TAG, "sendImageBitmap returned " + result + " — file-path fallback needed");
        }
        full.recycle();

        lastClockStr        = DashboardRenderer.buildClockString(cal);
        lastDateStr         = DashboardRenderer.buildDateString(cal);
        lastCalKey             = calendarDisplayKey(pendingEvents, System.currentTimeMillis());
        consecutivePartials    = 0;
        lastFullRefreshMs      = System.currentTimeMillis();
        weatherNetworkUpdated  = false;
        calendarNetworkUpdated = false;
    }

    private void renderAndSendPartialZones(String clockStr, String dateStr,
                                           WeatherSnapshot weather, List<CalendarEvent> events,
                                           java.util.List<Rect> zones) {
        Calendar cal = Calendar.getInstance(TZ_BUCHAREST);
        boolean stale = weather != null
                && weather.fetchedAtMs > 0
                && weather.isStale(30 * 60_000L);
        int panelTemp = epd.getTconTemperature();

        // Render once; crop one patch per zone and send each independently.
        Bitmap full = renderer.renderFull(clockStr, dateStr, weather, panelTemp, stale, events);
        for (Rect z : zones) {
            Log.i(TAG, String.format("Partial zone (%d,%d)..(%d,%d) %dx%d",
                    z.left, z.top, z.right, z.bottom, z.width(), z.height()));
            Bitmap patch = Bitmap.createBitmap(full, z.left, z.top, z.width(), z.height());
            int result = epd.sendPartImageBitmap(patch, z.left, z.top);
            if (result != 0) Log.w(TAG, "sendpartImageBitmap returned " + result + " for zone " + z);
            patch.recycle();
        }
        full.recycle();
        consecutivePartials += zones.size();
    }

    // ─── Forced-full logic ────────────────────────────────────────────────

    private boolean isForcedFull(Calendar cal, boolean dateChanged, boolean weatherCodeChanged) {
        if (consecutivePartials >= BuildConfig.MAX_CONSECUTIVE_PARTIALS) {
            Log.i(TAG, "Forcing full: consecutive partials cap");
            return true;
        }
        if (System.currentTimeMillis() - lastFullRefreshMs
                >= BuildConfig.FORCED_FULL_REFRESH_MINUTES * 60_000L) {
            Log.i(TAG, "Forcing full: wall-clock anti-ghosting");
            return true;
        }
        if (dateChanged)            { Log.i(TAG, "Forcing full: day rolled");               return true; }
        if (weatherCodeChanged)     { Log.i(TAG, "Forcing full: weather code changed");     return true; }
        if (weatherNetworkUpdated)  { Log.i(TAG, "Forcing full: weather data updated");     return true; }
        if (calendarNetworkUpdated) { Log.i(TAG, "Forcing full: calendar data updated");    return true; }

        long dayKey = cal.get(Calendar.YEAR) * 10000L
                    + cal.get(Calendar.MONTH) * 100L
                    + cal.get(Calendar.DAY_OF_MONTH);
        if (lastDeepCleanDay != dayKey
                && cal.get(Calendar.HOUR_OF_DAY) == BuildConfig.DEEP_CLEAN_HOUR
                && cal.get(Calendar.MINUTE)       == BuildConfig.DEEP_CLEAN_MINUTE) {
            Log.i(TAG, "Deep clean: clScr + full refresh");
            lastDeepCleanDay = dayKey;
            epd.clearScreen();
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            return true;
        }
        return false;
    }

    // ─── Helpers ──────────────────────────────────────────────────────────

    /**
     * A string that changes whenever the next-event display should change.
     * Encodes: which event is shown, whether it's NOW/NEXT, and the minute countdown.
     */
    private String calendarDisplayKey(List<CalendarEvent> events, long nowMs) {
        if (events == null || events.isEmpty()) return "";
        for (CalendarEvent e : events) {
            if (e.startMs <= nowMs && e.endMs > nowMs)
                return "NOW:" + e.title + ":" + e.endMs;
            if (e.startMs > nowMs)
                return "NEXT:" + e.title + ":" + e.startMs;
        }
        return "";
    }

    private Rect weatherBounds() {
        Layout l = new Layout(W, H);
        Rect r = l.weatherIcon;
        r = Layout.union(r, l.temperature);
        r = Layout.union(r, l.conditionWord);
        r = Layout.union(r, l.weatherHL);
        r = Layout.union(r, l.feelsLike);
        r = Layout.union(r, l.aqi);
        return r;
    }

    private static Rect union(Rect a, Rect b) {
        if (a == null) return b;
        return Layout.union(a, b);
    }

    // ─── Lifecycle ────────────────────────────────────────────────────────

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timeTicker != null) timeTicker.stop();
        weatherClient.stop();
        calendarClient.stop();
        wifi.stop();
        worker.shutdownNow();
        epd.unbind();
        Log.i(TAG, "Service destroyed");
    }

    // ─── Notification ─────────────────────────────────────────────────────

    private void createNotificationChannel() {
        NotificationChannel ch = new NotificationChannel(
                CHANNEL_ID, "Desk Dashboard", NotificationManager.IMPORTANCE_LOW);
        ch.setDescription("Dashboard foreground service");
        getSystemService(NotificationManager.class).createNotificationChannel(ch);
    }

    private Notification buildNotification() {
        return new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Desk Dashboard")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_dashboard)
                .setOngoing(true)
                .build();
    }
}
