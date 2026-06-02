package ro.deskdash.epd.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherParser {
    private static final String TAG = "WeatherParser";

    /** Parse the main weather JSON + pre-fetched AQI value into a WeatherSnapshot. */
    public static WeatherSnapshot parse(String json, int aqiEuropean) {
        try {
            JSONObject root    = new JSONObject(json);
            JSONObject current = root.getJSONObject("current");
            JSONObject daily   = root.getJSONObject("daily");

            double temp     = current.getDouble("temperature_2m");
            int    code     = current.getInt("weather_code");
            int    isDay    = current.getInt("is_day");
            double humidity = current.getDouble("relative_humidity_2m");
            double feels    = current.getDouble("apparent_temperature");
            double wind     = current.getDouble("wind_speed_10m");
            double maxTemp  = daily.getJSONArray("temperature_2m_max").getDouble(0);
            double minTemp  = daily.getJSONArray("temperature_2m_min").getDouble(0);
            String sunrise  = daily.getJSONArray("sunrise").getString(0);
            String sunset   = daily.getJSONArray("sunset").getString(0);

            // "2026-05-29T05:47" → "05:47"
            if (sunrise.length() > 11) sunrise = sunrise.substring(11, 16);
            if (sunset.length()  > 11) sunset  = sunset.substring(11, 16);

            // Hourly precipitation probability — 48 values (2 days)
            int[] precipProb = null;
            if (root.has("hourly")) {
                JSONObject hourly = root.getJSONObject("hourly");
                if (hourly.has("precipitation_probability")) {
                    JSONArray arr = hourly.getJSONArray("precipitation_probability");
                    int len = Math.min(arr.length(), 48);
                    precipProb = new int[len];
                    for (int i = 0; i < len; i++) {
                        precipProb[i] = arr.isNull(i) ? 0 : arr.getInt(i);
                    }
                }
            }

            return new WeatherSnapshot(temp, code, isDay, humidity, feels, wind,
                    maxTemp, minTemp, sunrise, sunset,
                    System.currentTimeMillis(), aqiEuropean, precipProb);

        } catch (Exception e) {
            Log.e(TAG, "Weather parse failed: " + e.getMessage());
            return null;
        }
    }

    /** Parse the AQI-only JSON from air-quality-api.open-meteo.com. Returns -1 on failure. */
    public static int parseAqi(String json) {
        try {
            return new JSONObject(json)
                    .getJSONObject("current")
                    .getInt("european_aqi");
        } catch (Exception e) {
            Log.w(TAG, "AQI parse failed: " + e.getMessage());
            return -1;
        }
    }
}
