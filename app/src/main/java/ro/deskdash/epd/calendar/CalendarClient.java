package ro.deskdash.epd.calendar;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalendarClient {
    private static final String TAG = "CalendarClient";

    // Look back 1 min (catch in-progress events) and forward 7 days.
    private static final long LOOK_BACK_MS  =  1 * 60_000L;
    private static final long LOOK_AHEAD_MS =  7 * 24 * 3600_000L;

    private final String       icalUrl;
    private final CalendarCache cache;
    private final OkHttpClient  http;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler      = new Handler(Looper.getMainLooper());

    private volatile boolean polling = false;
    private Handler pollHandler;
    private Consumer<List<CalendarEvent>> callback;

    public CalendarClient(String icalUrl, CalendarCache cache) {
        this.icalUrl = icalUrl;
        this.cache   = cache;
        this.http    = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public boolean isConfigured() {
        return icalUrl != null && !icalUrl.isEmpty();
    }

    public void startPolling(long intervalMs, Consumer<List<CalendarEvent>> callback) {
        if (!isConfigured()) {
            Log.i(TAG, "No iCal URL configured — calendar disabled");
            return;
        }
        this.callback    = callback;
        this.polling     = true;
        this.pollHandler = new Handler(Looper.getMainLooper());
        Log.i(TAG, "CalendarClient polling every " + (intervalMs / 60_000) + " min");
        fetchNow();
        pollHandler.postDelayed(new Runnable() {
            @Override public void run() {
                if (!polling) return;
                fetchNow();
                pollHandler.postDelayed(this, intervalMs);
            }
        }, intervalMs);
    }

    public void stop() {
        polling = false;
        Handler h = pollHandler;
        if (h != null) h.removeCallbacksAndMessages(null);
        executor.shutdownNow();
    }

    private void fetchNow() {
        executor.execute(() -> {
            Log.i(TAG, "Fetching iCal feed");
            String ics = fetchWithRetry(3);
            if (ics == null) {
                Log.w(TAG, "All fetches failed — trying cache");
                ics = cache.load();
                if (ics == null) { Log.w(TAG, "No cached ics"); return; }
            } else {
                cache.save(ics);
            }

            long now    = System.currentTimeMillis();
            List<CalendarEvent> events = ICalParser.parse(ics, now - LOOK_BACK_MS, now + LOOK_AHEAD_MS);
            Collections.sort(events, (a, b) -> Long.compare(a.startMs, b.startMs));
            Log.i(TAG, "Parsed " + events.size() + " events in next 7 days");

            if (callback != null) mainHandler.post(() -> callback.accept(events));
        });
    }

    private String fetchWithRetry(int maxAttempts) {
        long[] backoff = {0L, 3_000L, 6_000L};
        for (int i = 0; i < maxAttempts; i++) {
            if (i > 0) try { Thread.sleep(backoff[i]); } catch (InterruptedException ignored) {}
            try {
                Request req = new Request.Builder().url(icalUrl).build();
                try (Response resp = http.newCall(req).execute()) {
                    if (resp.isSuccessful() && resp.body() != null) return resp.body().string();
                    Log.w(TAG, "HTTP " + resp.code() + " on attempt " + (i + 1));
                }
            } catch (IOException e) {
                Log.w(TAG, "Attempt " + (i + 1) + " failed: " + e.getMessage());
            }
        }
        return null;
    }
}
