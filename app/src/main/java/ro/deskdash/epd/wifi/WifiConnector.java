package ro.deskdash.epd.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import ro.deskdash.epd.epd.EpdConnector;

public class WifiConnector {
    private static final String TAG = "WifiConnector";
    private static final long CHECK_INTERVAL_MS = 60_000;

    private final Context context;
    private final EpdConnector epd;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean running = false;

    private final Runnable watchdog = new Runnable() {
        @Override
        public void run() {
            if (!running) return;
            checkWifi();
            handler.postDelayed(this, CHECK_INTERVAL_MS);
        }
    };

    public WifiConnector(Context context, EpdConnector epd) {
        this.context = context.getApplicationContext();
        this.epd = epd;
    }

    public void start() {
        if (running) return;
        running = true;
        handler.post(watchdog);
        Log.i(TAG, "WifiConnector watchdog started");
    }

    public void stop() {
        running = false;
        handler.removeCallbacks(watchdog);
    }

    private void checkWifi() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            Log.w(TAG, "Network not available — asking EPD service to re-enable WiFi");
            epd.enableWifi();
        }
    }
}
