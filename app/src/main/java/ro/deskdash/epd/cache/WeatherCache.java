package ro.deskdash.epd.cache;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WeatherCache {
    private static final String TAG = "WeatherCache";
    private final File file;

    public WeatherCache(Context context) {
        this.file = new File(context.getFilesDir(), "weather.json");
    }

    public String load() {
        if (!file.exists()) return null;
        try (FileReader r = new FileReader(file)) {
            char[] buf = new char[(int) file.length()];
            int read = r.read(buf);
            return read > 0 ? new String(buf, 0, read) : null;
        } catch (IOException e) {
            Log.e(TAG, "Cache load failed: " + e.getMessage());
            return null;
        }
    }

    public void save(String json) {
        try (FileWriter w = new FileWriter(file)) {
            w.write(json);
        } catch (IOException e) {
            Log.e(TAG, "Cache save failed: " + e.getMessage());
        }
    }
}
