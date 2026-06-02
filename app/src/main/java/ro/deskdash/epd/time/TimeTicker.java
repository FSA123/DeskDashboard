package ro.deskdash.epd.time;

import android.os.Handler;
import android.util.Log;

public class TimeTicker {
    private static final String TAG = "TimeTicker";

    private final Handler handler;
    private final Runnable onTick;
    private boolean running = false;

    private final Runnable ticker = new Runnable() {
        @Override
        public void run() {
            if (!running) return;
            onTick.run();
            long now = System.currentTimeMillis();
            long delayMs = 60_000 - (now % 60_000);
            handler.postDelayed(this, delayMs);
            Log.d(TAG, "minute rolled — next tick in " + delayMs + "ms");
        }
    };

    public TimeTicker(Handler handler, Runnable onTick) {
        this.handler = handler;
        this.onTick = onTick;
    }

    public void start() {
        if (running) return;
        running = true;
        long now = System.currentTimeMillis();
        long delayMs = 60_000 - (now % 60_000);
        handler.postDelayed(ticker, delayMs);
        Log.i(TAG, "TimeTicker started — first tick in " + delayMs + "ms");
    }

    public void stop() {
        running = false;
        handler.removeCallbacks(ticker);
        Log.i(TAG, "TimeTicker stopped");
    }
}
