package ro.deskdash.epd.weather;

public class WeatherSnapshot {
    public final double temperatureC;
    public final int    weatherCode;
    public final int    isDay;
    public final double humidityPct;
    public final double apparentTempC;
    public final double windSpeedKmh;
    public final double maxTempC;
    public final double minTempC;
    public final String sunrise;
    public final String sunset;
    public final long   fetchedAtMs;

    // AQI: European index (0–500+), -1 if unavailable
    public final int    aqiEuropean;

    // Hourly precipitation probability [0–100], 48 values (2 days × 24 h), index 0 = 00:00 today.
    // Null if the hourly block was absent from the response.
    public final int[]  precipProb;

    public WeatherSnapshot(double temperatureC, int weatherCode, int isDay,
                           double humidityPct, double apparentTempC, double windSpeedKmh,
                           double maxTempC, double minTempC,
                           String sunrise, String sunset, long fetchedAtMs,
                           int aqiEuropean, int[] precipProb) {
        this.temperatureC  = temperatureC;
        this.weatherCode   = weatherCode;
        this.isDay         = isDay;
        this.humidityPct   = humidityPct;
        this.apparentTempC = apparentTempC;
        this.windSpeedKmh  = windSpeedKmh;
        this.maxTempC      = maxTempC;
        this.minTempC      = minTempC;
        this.sunrise       = sunrise;
        this.sunset        = sunset;
        this.fetchedAtMs   = fetchedAtMs;
        this.aqiEuropean   = aqiEuropean;
        this.precipProb    = precipProb;
    }

    public boolean isStale(long maxAgeMs) {
        return (System.currentTimeMillis() - fetchedAtMs) > maxAgeMs;
    }

    public static WeatherSnapshot placeholder() {
        return new WeatherSnapshot(
                18, 2, 1,
                65, 16, 12,
                22, 11,
                "06:02", "20:45",
                0L,
                -1, null
        );
    }
}
