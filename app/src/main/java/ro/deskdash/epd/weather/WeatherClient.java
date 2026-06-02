package ro.deskdash.epd.weather;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ro.deskdash.epd.BuildConfig;
import ro.deskdash.epd.cache.WeatherCache;

public class WeatherClient {
    private static final String TAG = "WeatherClient";

    private static final String URL_WEATHER =
            "https://api.open-meteo.com/v1/forecast" +
            "?latitude=%s&longitude=%s" +
            "&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,weather_code,wind_speed_10m" +
            "&hourly=precipitation_probability" +
            "&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset" +
            "&timezone=%s" +
            "&forecast_days=2";

    private static final String URL_AQI =
            "https://air-quality-api.open-meteo.com/v1/air-quality" +
            "?latitude=%s&longitude=%s&current=european_aqi";

    private final WeatherCache cache;
    private final OkHttpClient http;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private volatile boolean polling = false;
    private Handler pollHandler;
    private Consumer<WeatherSnapshot> callback;

    public WeatherClient(WeatherCache cache) {
        this.cache = cache;
        this.http  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public void startPolling(long intervalMs, Consumer<WeatherSnapshot> callback) {
        this.callback    = callback;
        this.polling     = true;
        this.pollHandler = new Handler(Looper.getMainLooper());
        Log.i(TAG, "WeatherClient polling every " + (intervalMs / 60_000) + " min");
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
            String weatherUrl = String.format(URL_WEATHER,
                    BuildConfig.WEATHER_LAT, BuildConfig.WEATHER_LON,
                    BuildConfig.WEATHER_TIMEZONE.replace("/", "%2F"));
            String aqiUrl = String.format(URL_AQI,
                    BuildConfig.WEATHER_LAT, BuildConfig.WEATHER_LON);

            Log.i(TAG, "Polling weather + AQI for "
                    + BuildConfig.WEATHER_LAT + "," + BuildConfig.WEATHER_LON);

            // Weather — with retry + cache fallback
            String weatherJson = fetchWithRetry(weatherUrl, 3);
            if (weatherJson == null) {
                Log.w(TAG, "Weather fetch failed — loading cache");
                weatherJson = cache.load();
                if (weatherJson == null) { Log.w(TAG, "No cached weather"); return; }
            } else {
                cache.save(weatherJson);
            }

            // AQI — single best-effort attempt (non-critical)
            int aqi = -1;
            try {
                Request req = new Request.Builder().url(aqiUrl).build();
                try (Response resp = http.newCall(req).execute()) {
                    if (resp.isSuccessful() && resp.body() != null) {
                        aqi = WeatherParser.parseAqi(resp.body().string());
                        Log.i(TAG, "AQI: " + aqi);
                    }
                }
            } catch (IOException e) {
                Log.w(TAG, "AQI fetch failed: " + e.getMessage());
            }

            WeatherSnapshot snap = WeatherParser.parse(weatherJson, aqi);
            if (snap == null) return;

            Log.i(TAG, String.format(
                    "Snapshot: %.0f°C code=%d H=%.0f° L=%.0f° wind=%.0f hum=%.0f AQI=%d",
                    snap.temperatureC, snap.weatherCode, snap.maxTempC,
                    snap.minTempC, snap.windSpeedKmh, snap.humidityPct, snap.aqiEuropean));

            if (callback != null) mainHandler.post(() -> callback.accept(snap));
        });
    }

    private String fetchWithRetry(String url, int maxAttempts) {
        long[] backoffMs = {0, 2000, 4000};
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            if (attempt > 0) {
                try { Thread.sleep(backoffMs[attempt]); } catch (InterruptedException ignored) {}
            }
            try {
                Request req = new Request.Builder().url(url).build();
                try (Response resp = http.newCall(req).execute()) {
                    if (resp.isSuccessful() && resp.body() != null) return resp.body().string();
                    Log.w(TAG, "HTTP " + resp.code() + " on attempt " + (attempt + 1));
                }
            } catch (IOException e) {
                Log.w(TAG, "Attempt " + (attempt + 1) + " failed: " + e.getMessage());
            }
        }
        return null;
    }
}
