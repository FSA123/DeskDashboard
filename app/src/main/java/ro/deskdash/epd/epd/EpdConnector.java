package ro.deskdash.epd.epd;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.geniatech.el133sdk.EpdManager;

public class EpdConnector {
    private static final String TAG = "EpdConnector";
    private static final long REBIND_DELAY_MS = 3000;

    private final Context context;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private EpdManager epdManager;
    private Runnable onConnectedCallback;
    private boolean bound = false;

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            epdManager = EpdManager.Stub.asInterface(service);
            Log.i(TAG, "EpdManager service connected");
            if (onConnectedCallback != null) {
                onConnectedCallback.run();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(TAG, "EpdManager service disconnected — rebinding in 3s");
            epdManager = null;
            handler.postDelayed(EpdConnector.this::bind, REBIND_DELAY_MS);
        }
    };

    public EpdConnector(Context context) {
        this.context = context.getApplicationContext();
    }

    public void setOnConnectedCallback(Runnable callback) {
        this.onConnectedCallback = callback;
    }

    public void bind() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "com.geniatech.epc.core",
                "com.geniatech.el133sdk.epdService"));
        Log.i(TAG, "Binding to EPD service (direct ComponentName)");
        boolean ok = false;
        try {
            ok = context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        } catch (SecurityException e) {
            Log.w(TAG, "Direct bind SecurityException: " + e.getMessage());
        }
        if (ok) {
            Log.i(TAG, "Direct bind returned true — waiting for onServiceConnected");
            bound = true;
        } else {
            Log.w(TAG, "Direct bind failed — trying fallback service");
            bindFallback();
        }
    }

    private void bindFallback() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "com.geniatech.epc.service",
                "com.geniatech.epc.service.server.SendImageService"));
        boolean ok = false;
        try {
            ok = context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        } catch (SecurityException e) {
            Log.w(TAG, "Fallback bind SecurityException: " + e.getMessage());
        }
        if (ok) {
            Log.i(TAG, "Fallback bind returned true — waiting for onServiceConnected");
            bound = true;
        } else {
            Log.w(TAG, "Fallback bind also failed — retrying in 3s");
            handler.postDelayed(this::bind, REBIND_DELAY_MS);
        }
    }

    public void unbind() {
        if (bound) {
            try {
                context.unbindService(conn);
            } catch (IllegalArgumentException ignored) {
            }
            bound = false;
        }
    }

    public boolean isConnected() {
        return epdManager != null;
    }

    public void disableAutoRefresh() {
        if (epdManager == null) return;
        try {
            epdManager.isOpenRefrushTime(false);
            Log.i(TAG, "Panel auto-refresh disabled");
        } catch (RemoteException e) {
            Log.e(TAG, "disableAutoRefresh failed: " + e.getMessage());
        }
    }

    public void setNtpAndTimezone(String ntpServer, String timezone) {
        if (epdManager == null) return;
        try {
            epdManager.setNTPServer(ntpServer);
            epdManager.setTimeZone(timezone);
            Log.i(TAG, "NTP=" + ntpServer + " TZ=" + timezone);
        } catch (RemoteException e) {
            Log.e(TAG, "setNtpAndTimezone failed: " + e.getMessage());
        }
    }

    public String getEpdInfo() {
        if (epdManager == null) return null;
        try {
            return epdManager.getEPDInfo();
        } catch (RemoteException e) {
            Log.e(TAG, "getEPDInfo failed: " + e.getMessage());
            return null;
        }
    }

    public int sendImageBitmap(Bitmap bitmap) {
        if (epdManager == null) return -99;
        try {
            int result = epdManager.sendImageBitmap(bitmap);
            if (result == 0) {
                Log.i(TAG, "sendImageBitmap accepted by EPD service");
            } else {
                Log.w(TAG, "sendImageBitmap returned " + result);
            }
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "sendImageBitmap RemoteException: " + e.getMessage());
            return -1;
        }
    }

    public int sendImageFile(String path) {
        if (epdManager == null) return -99;
        try {
            int result = epdManager.sendImage(path);
            if (result == 0) {
                Log.i(TAG, "sendImage accepted by EPD service: " + path);
            } else {
                Log.w(TAG, "sendImage returned " + result);
            }
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "sendImage RemoteException: " + e.getMessage());
            return -1;
        }
    }

    public int sendPartImageBitmap(Bitmap bitmap, int x, int y) {
        if (epdManager == null) return -99;
        try {
            int result = epdManager.sendpartImageBitmap(bitmap, x, y);
            if (result == 0) {
                Log.i(TAG, "sendpartImageBitmap accepted");
            } else {
                Log.w(TAG, "sendpartImageBitmap returned " + result);
            }
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "sendpartImageBitmap RemoteException: " + e.getMessage());
            return -1;
        }
    }

    public void enableWifi() {
        if (epdManager == null) return;
        try {
            epdManager.setWifiOn();
            Log.i(TAG, "setWifiOn called");
        } catch (RemoteException e) {
            Log.e(TAG, "setWifiOn failed: " + e.getMessage());
        }
    }

    public void clearScreen() {
        if (epdManager == null) return;
        try {
            epdManager.clScr();
            Log.i(TAG, "clScr called");
        } catch (RemoteException e) {
            Log.e(TAG, "clScr failed: " + e.getMessage());
        }
    }

    public int getTconTemperature() {
        if (epdManager == null) return -1;
        try {
            return epdManager.getTCONTemperature();
        } catch (RemoteException e) {
            return -1;
        }
    }
}
