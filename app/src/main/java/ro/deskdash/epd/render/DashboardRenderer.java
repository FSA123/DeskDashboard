package ro.deskdash.epd.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ro.deskdash.epd.calendar.CalendarEvent;
import ro.deskdash.epd.weather.WeatherSnapshot;

public class DashboardRenderer {
    private static final String TAG = "DashboardRenderer";

    // ── Palette ────────────────────────────────────────────────────────────
    private static final int BLACK = Color.BLACK;
    private static final int WHITE = Color.WHITE;
    private static final int RED   = Color.rgb(183,  28,  28); // today / NOW / AQI poor
    private static final int BLUE  = Color.rgb( 13,  71, 161); // upcoming / rain / event dots

    private static final TimeZone TZ = TimeZone.getTimeZone("Europe/Bucharest");

    private final int W;
    private final int H;
    private final Layout layout;

    private final Typeface tfBlack;
    private final Typeface tfMedium;
    private final Typeface tfRegular;
    private final Typeface tfMono;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DashboardRenderer(Context context, int w, int h) {
        this.W = w;
        this.H = h;
        this.layout = new Layout(w, h);

        tfBlack   = loadFont(context, "fonts/Inter-Black.ttf",         Typeface.DEFAULT_BOLD);
        tfMedium  = loadFont(context, "fonts/Inter-Medium.ttf",         Typeface.DEFAULT);
        tfRegular = loadFont(context, "fonts/Inter-Regular.ttf",        Typeface.DEFAULT);
        tfMono    = loadFont(context, "fonts/JetBrainsMono-Medium.ttf", Typeface.MONOSPACE);
    }

    private static Typeface loadFont(Context ctx, String path, Typeface fallback) {
        try {
            Typeface tf = Typeface.createFromAsset(ctx.getAssets(), path);
            Log.i(TAG, "Loaded font: " + path);
            return tf;
        } catch (Exception e) {
            Log.w(TAG, "Font not found: " + path + " — using fallback");
            return fallback;
        }
    }

    // ─── Splash ────────────────────────────────────────────────────────────

    public Bitmap renderSplash() {
        Bitmap bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(WHITE);

        paint.setColor(BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(tfBlack);
        paint.setTextSize(H * 0.08f);
        canvas.drawText("DESK DASHBOARD", W / 2f, H * 0.45f, paint);

        paint.setTypeface(tfMedium);
        paint.setTextSize(H * 0.03f);
        canvas.drawText("EPD Service Connected  —  Initializing", W / 2f, H * 0.55f, paint);

        return bmp;
    }

    // ─── Full frame ────────────────────────────────────────────────────────

    public Bitmap renderFull(String clockStr, String dateStr,
                             WeatherSnapshot weather, int panelTempC, boolean stale,
                             List<CalendarEvent> events) {
        Bitmap bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(WHITE);

        drawDate(canvas, dateStr);
        drawClock(canvas, clockStr);
        drawSeparator(canvas);
        drawFooter(canvas, weather, panelTempC);
        drawPrecipStrip(canvas, weather);

        if (weather != null) {
            drawWeatherIcon(canvas, weather);
            drawTemperature(canvas, weather, stale);
            drawConditionWord(canvas, weather);
            drawWeatherHL(canvas, weather);
            drawFeelsLike(canvas, weather);
            drawAqi(canvas, weather);
        }

        if (events != null && !events.isEmpty()) {
            drawNextEvent(canvas, events);
        }
        drawCalendarStrip(canvas, events);

        return bmp;
    }

    // ─── Zone drawing ──────────────────────────────────────────────────────

    private void drawDate(Canvas c, String dateStr) {
        Rect r = layout.date;
        paint.setColor(BLACK);
        paint.setTypeface(tfMedium);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(H * 0.05f);
        c.drawText(dateStr, r.left, centerBaseline(r), paint);
    }

    private void drawClock(Canvas c, String clockStr) {
        Rect r = layout.clock;
        paint.setColor(BLACK);
        paint.setTypeface(tfBlack);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(H * 0.36f);

        char[] chars = clockStr.toCharArray();
        if (chars.length != 5) {
            c.drawText(clockStr, cx(r), centerBaseline(r), paint);
            return;
        }

        float slotW  = r.width() / 4.5f;
        float colonW = slotW * 0.5f;
        float totalW = slotW * 4 + colonW;
        float startX = r.left + (r.width() - totalW) / 2f;

        float[] centres = {
                startX + slotW * 0.5f,
                startX + slotW * 1.5f,
                startX + slotW * 2f   + colonW * 0.5f,
                startX + slotW * 2.5f + colonW,
                startX + slotW * 3.5f + colonW
        };

        float baseline = centerBaseline(r);
        for (int i = 0; i < 5; i++) {
            c.drawText(String.valueOf(chars[i]), centres[i], baseline, paint);
        }
    }

    private void drawWeatherIcon(Canvas c, WeatherSnapshot w) {
        Icons.drawWeather(c, layout.weatherIcon, w.weatherCode, w.isDay);
    }

    private void drawTemperature(Canvas c, WeatherSnapshot w, boolean stale) {
        Rect r = layout.temperature;
        paint.setColor(BLACK);
        paint.setTypeface(tfBlack);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(H * 0.16f);
        c.drawText(Math.round(w.temperatureC) + "°", r.right, centerBaseline(r), paint);

        if (stale) {
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(H * 0.025f);
            c.drawText("●", r.right - measureText("●", H * 0.025f) - 4, r.top + H * 0.04f, paint);
        }
    }

    private void drawConditionWord(Canvas c, WeatherSnapshot w) {
        Rect r = layout.conditionWord;
        paint.setColor(BLACK);
        paint.setTypeface(tfMedium);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(H * 0.04f);
        c.drawText(wmoToConditionString(w.weatherCode, w.isDay), r.left, centerBaseline(r), paint);
    }

    private void drawWeatherHL(Canvas c, WeatherSnapshot w) {
        Rect r = layout.weatherHL;
        paint.setColor(BLACK);
        paint.setTypeface(tfRegular);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(H * 0.03f);
        c.drawText(Math.round(w.maxTempC) + "° / " + Math.round(w.minTempC) + "°",
                r.left, centerBaseline(r), paint);
    }

    private void drawFeelsLike(Canvas c, WeatherSnapshot w) {
        Rect r = layout.feelsLike;
        paint.setColor(BLACK);
        paint.setTypeface(tfRegular);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(H * 0.03f);
        c.drawText("Feels like " + Math.round(w.apparentTempC) + "°",
                r.left, centerBaseline(r), paint);
    }

    // ─── AQI ───────────────────────────────────────────────────────────────

    private void drawAqi(Canvas c, WeatherSnapshot w) {
        if (w.aqiEuropean < 0) return;
        Rect r = layout.aqi;
        int aqi = w.aqiEuropean;

        paint.setColor(aqi >= 80 ? RED : BLACK);
        paint.setTypeface(tfMono);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(H * 0.026f);
        c.drawText("AQI  " + aqi + "  " + aqiLabel(aqi), r.left, centerBaseline(r), paint);
    }

    private static String aqiLabel(int aqi) {
        if (aqi <= 40)  return "Good";
        if (aqi <= 80)  return "Moderate";
        if (aqi <= 120) return "Poor";
        return "Unhealthy";
    }

    // ─── Next-event box ────────────────────────────────────────────────────

    private void drawNextEvent(Canvas c, List<CalendarEvent> events) {
        Rect r   = layout.nextEvent;
        long now = System.currentTimeMillis();

        CalendarEvent current  = null;
        CalendarEvent upcoming = null;
        for (CalendarEvent e : events) {
            if (current == null  && e.startMs <= now && e.endMs > now) current  = e;
            if (upcoming == null && e.startMs > now)                    upcoming = e;
            if (current != null && upcoming != null) break;
        }
        CalendarEvent show = (current != null) ? current : upcoming;
        if (show == null) return;

        boolean isNow  = (current != null);
        int labelColor = isNow ? RED : BLUE;

        paint.setStyle(Paint.Style.FILL);

        // "NOW" / "NEXT" tag
        paint.setColor(labelColor);
        paint.setTypeface(tfMono);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(H * 0.020f);
        c.drawText(isNow ? "NOW" : "NEXT", r.left, r.top + H * 0.030f, paint);

        float ruleY = r.top + H * 0.043f;
        c.drawRect(r.left, ruleY, r.left + r.width() * 0.32f, ruleY + 2f, paint);

        // Event title
        paint.setColor(BLACK);
        paint.setTypeface(tfBlack);
        paint.setTextSize(H * 0.042f);
        String title = show.title.toUpperCase(Locale.ROOT);
        if (paint.measureText(title) > r.width()) {
            while (title.length() > 1 && paint.measureText(title + "…") > r.width())
                title = title.substring(0, title.length() - 1);
            title += "…";
        }
        c.drawText(title, r.left, r.top + H * 0.100f, paint);

        // Time line
        paint.setColor(labelColor);
        paint.setTypeface(tfMono);
        paint.setTextSize(H * 0.026f);
        c.drawText(buildEventTimeStr(show, isNow, now), r.left, r.top + H * 0.134f, paint);
    }

    private String buildEventTimeStr(CalendarEvent e, boolean isNow, long nowMs) {
        if (e.isAllDay) return "ALL DAY";
        Calendar ec    = Calendar.getInstance(TZ);
        Calendar today = Calendar.getInstance(TZ);

        if (isNow) {
            ec.setTimeInMillis(e.endMs);
            return String.format(Locale.ROOT, "ENDS %02d:%02d",
                    ec.get(Calendar.HOUR_OF_DAY), ec.get(Calendar.MINUTE));
        }

        ec.setTimeInMillis(e.startMs);
        String time = String.format(Locale.ROOT, "%02d:%02d",
                ec.get(Calendar.HOUR_OF_DAY), ec.get(Calendar.MINUTE));

        if (ec.get(Calendar.YEAR)        == today.get(Calendar.YEAR) &&
            ec.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "TODAY  " + time;
        }

        Calendar tomorrow = Calendar.getInstance(TZ);
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        if (ec.get(Calendar.YEAR)        == tomorrow.get(Calendar.YEAR) &&
            ec.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)) {
            return "TOMORROW  " + time;
        }

        String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        return days[ec.get(Calendar.DAY_OF_WEEK) - 1] + "  " + time;
    }

    // ─── Calendar strip (Mon → Sun) ────────────────────────────────────────

    private void drawCalendarStrip(Canvas c, List<CalendarEvent> events) {
        Rect  r      = layout.calendarStrip;
        float stripH = r.height();
        int   colW   = r.width() / 7;

        float labelY        = r.top + stripH * 0.22f;
        float circleCenterY = r.top + stripH * 0.57f;
        float circleRadius  = stripH * 0.21f;
        float dotsY         = r.top + stripH * 0.855f;
        float dotRadius     = H * 0.006f;

        String[] dayNames = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

        Calendar weekStart = Calendar.getInstance(TZ);
        weekStart.set(Calendar.HOUR_OF_DAY, 0);
        weekStart.set(Calendar.MINUTE, 0);
        weekStart.set(Calendar.SECOND, 0);
        weekStart.set(Calendar.MILLISECOND, 0);
        int dow = weekStart.get(Calendar.DAY_OF_WEEK);
        int daysFromMonday = (dow == Calendar.SUNDAY) ? 6 : (dow - Calendar.MONDAY);
        weekStart.add(Calendar.DAY_OF_YEAR, -daysFromMonday);

        Calendar today = Calendar.getInstance(TZ);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < 7; i++) {
            Calendar col = (Calendar) weekStart.clone();
            col.add(Calendar.DAY_OF_YEAR, i);

            long dayStartMs = col.getTimeInMillis();
            int  dayNum     = col.get(Calendar.DAY_OF_MONTH);
            int  colDow     = col.get(Calendar.DAY_OF_WEEK) - 1;
            boolean isToday = col.get(Calendar.YEAR)        == today.get(Calendar.YEAR)
                           && col.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);

            Calendar colNext = (Calendar) col.clone();
            colNext.add(Calendar.DAY_OF_YEAR, 1);
            long dayEndMs = colNext.getTimeInMillis();

            float colCx = r.left + i * colW + colW / 2f;

            paint.setColor(isToday ? RED : BLACK);
            paint.setTypeface(tfMono);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(H * 0.022f);
            c.drawText(dayNames[colDow], colCx, labelY, paint);

            if (isToday) {
                paint.setColor(RED);
                c.drawCircle(colCx, circleCenterY, circleRadius, paint);
                paint.setColor(WHITE);
            } else {
                paint.setColor(BLACK);
            }

            paint.setTypeface(tfBlack);
            paint.setTextSize(H * 0.050f);
            float numBaseline = circleCenterY - (paint.descent() + paint.ascent()) / 2f;
            c.drawText(String.valueOf(dayNum), colCx, numBaseline, paint);

            paint.setColor(BLUE);
            paint.setStyle(Paint.Style.FILL);
            int eventCount = 0;
            if (events != null) {
                for (CalendarEvent e : events) {
                    if (e.startMs >= dayStartMs && e.startMs < dayEndMs) eventCount++;
                }
            }
            int dots = Math.min(eventCount, 3);
            if (dots > 0) {
                float totalDotsW = dots * dotRadius * 2 + (dots - 1) * dotRadius;
                float dotStartX  = colCx - totalDotsW / 2f + dotRadius;
                for (int d = 0; d < dots; d++) {
                    c.drawCircle(dotStartX + d * dotRadius * 3f, dotsY, dotRadius, paint);
                }
            }

            if (i < 6) {
                paint.setColor(BLACK);
                float sepX = r.left + (i + 1) * colW;
                c.drawRect(sepX - 1f, r.top + stripH * 0.12f, sepX, r.bottom - stripH * 0.12f, paint);
            }
        }
    }

    // ─── 12-hour precipitation strip ──────────────────────────────────────

    private void drawPrecipStrip(Canvas c, WeatherSnapshot w) {
        if (w == null || w.precipProb == null) return;
        Rect r = layout.precipStrip;

        Calendar cal       = Calendar.getInstance(TZ);
        int      startHour = cal.get(Calendar.HOUR_OF_DAY);
        int      cellW     = r.width() / 12;

        float labelBaseline = r.top + r.height() * 0.44f;
        float barTop        = r.top + r.height() * 0.54f;
        float barBottom     = r.bottom - r.height() * 0.08f;
        float barHalfW      = cellW * 0.22f;

        for (int i = 0; i < 12; i++) {
            int probIdx = startHour + i;
            if (probIdx >= w.precipProb.length) break;

            int   hour  = (startHour + i) % 24;
            float colCx = r.left + i * cellW + cellW / 2f;
            boolean rain = w.precipProb[probIdx] >= 30;

            // Hour label
            paint.setColor(rain ? BLUE : BLACK);
            paint.setTypeface(tfMono);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(H * 0.017f);
            c.drawText(String.format(Locale.ROOT, "%02d", hour), colCx, labelBaseline, paint);

            // Rain bar — filled blue if rain, nothing if clear
            if (rain) {
                paint.setColor(BLUE);
                paint.setStyle(Paint.Style.FILL);
                c.drawRect(colCx - barHalfW, barTop, colCx + barHalfW, barBottom, paint);
            }
        }
        paint.setStyle(Paint.Style.FILL);
    }

    // ─── Separator + footer ────────────────────────────────────────────────

    private void drawSeparator(Canvas c) {
        Rect r = layout.separator;
        paint.setColor(BLACK);
        paint.setStyle(Paint.Style.FILL);
        c.drawRect(r.left, r.top, r.right, r.top + 2, paint);
    }

    private void drawFooter(Canvas c, WeatherSnapshot w, int panelTempC) {
        Rect r = layout.footer;
        paint.setColor(BLACK);
        paint.setTypeface(tfMono);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(H * 0.022f);
        float baseline = centerBaseline(r);

        StringBuilder sb = new StringBuilder();
        if (w != null) {
            sb.append(daylightStr(w));
            sb.append("      ").append(Math.round(w.humidityPct)).append("%  hum");
            sb.append("   ").append(Math.round(w.windSpeedKmh)).append(" km/h  wind");
        }
        if (panelTempC >= 0) sb.append("   ").append(panelTempC).append(" °C  panel");

        c.drawText(sb.toString(), r.left, baseline, paint);
    }

    /** "4h 32m daylight  rise 06:12  set 20:44"  (or just rise/set outside daylight hours) */
    private String daylightStr(WeatherSnapshot w) {
        int sunriseMin = parseToMin(w.sunrise);
        int sunsetMin  = parseToMin(w.sunset);
        if (sunriseMin < 0 || sunsetMin < 0)
            return "rise " + w.sunrise + "  set " + w.sunset;

        Calendar now = Calendar.getInstance(TZ);
        int nowMin = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE);

        if (nowMin >= sunriseMin && nowMin < sunsetMin) {
            int rem = sunsetMin - nowMin;
            return fmtMin(rem) + " daylight   rise " + w.sunrise + "   set " + w.sunset;
        }
        return "rise " + w.sunrise + "   set " + w.sunset;
    }

    private static int parseToMin(String hhmm) {
        try {
            String[] p = hhmm.split(":");
            return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
        } catch (Exception e) { return -1; }
    }

    private static String fmtMin(int totalMin) {
        int h = totalMin / 60;
        int m = totalMin % 60;
        if (h == 0) return m + "m";
        return h + "h " + String.format(Locale.ROOT, "%02dm", m);
    }

    // ─── Helpers ───────────────────────────────────────────────────────────

    private float centerBaseline(Rect r) {
        return r.centerY() - (paint.descent() + paint.ascent()) / 2f;
    }

    private float cx(Rect r) { return r.left + r.width() / 2f; }

    private float measureText(String s, float size) {
        paint.setTextSize(size);
        return paint.measureText(s);
    }

    public static String wmoToConditionString(int code, int isDay) {
        if (code == 0)                              return isDay == 1 ? "Clear sky" : "Clear night";
        if (code == 1)                              return "Mainly clear";
        if (code == 2)                              return "Partly cloudy";
        if (code == 3)                              return "Overcast";
        if (code == 45 || code == 48)               return "Foggy";
        if (code >= 51 && code <= 55)               return "Drizzle";
        if (code >= 61 && code <= 65)               return "Rain";
        if (code >= 71 && code <= 75)               return "Snow";
        if (code == 80 || code == 81 || code == 82) return "Rain showers";
        if (code == 85 || code == 86)               return "Snow showers";
        if (code == 95)                             return "Thunderstorm";
        if (code == 96 || code == 99)               return "Heavy thunderstorm";
        return "Unknown";
    }

    public static String buildClockString(Calendar cal) {
        return String.format(Locale.ROOT, "%02d:%02d",
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    public static String buildDateString(Calendar cal) {
        String[] days   = {"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
        String[] months = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        return String.format(Locale.ROOT, "%s   %d %s",
                days[cal.get(Calendar.DAY_OF_WEEK) - 1],
                cal.get(Calendar.DAY_OF_MONTH),
                months[cal.get(Calendar.MONTH)]);
    }
}
