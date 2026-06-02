package ro.deskdash.epd.calendar;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class CalendarCache {
    private static final String TAG  = "CalendarCache";
    private static final String FILE = "calendar.ics";

    private final File file;

    public CalendarCache(Context ctx) {
        this.file = new File(ctx.getFilesDir(), FILE);
    }

    public void save(String ics) {
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter w = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            w.write(ics);
        } catch (IOException e) {
            Log.w(TAG, "Cache write failed: " + e.getMessage());
        }
    }

    public String load() {
        if (!file.exists()) return null;
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
            return sb.toString();
        } catch (IOException e) {
            Log.w(TAG, "Cache read failed: " + e.getMessage());
            return null;
        }
    }
}
