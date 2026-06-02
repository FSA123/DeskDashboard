package ro.deskdash.epd.render;

import android.graphics.Rect;

public class Layout {
    public final int W;
    public final int H;

    // ── Top section ────────────────────────────────────────────────────────
    public final Rect date;
    public final Rect clock;
    public final Rect clockHour;    // HH sub-zone
    public final Rect clockMinute;  // mm sub-zone

    // ── Weather column (right) ─────────────────────────────────────────────
    public final Rect weatherIcon;
    public final Rect temperature;
    public final Rect conditionWord;
    public final Rect weatherHL;
    public final Rect feelsLike;
    public final Rect aqi;          // one-line AQI: "AQI 42  Good"

    // ── Next-event box (right column, below AQI) ───────────────────────────
    public final Rect nextEvent;

    // ── Full-width lower section ───────────────────────────────────────────
    public final Rect separator;
    public final Rect calendarStrip;
    public final Rect precipStrip;  // 12-hour precipitation forecast
    public final Rect footer;       // daylight remaining + sunrise/sunset + stats

    public Layout(int w, int h) {
        this.W = w;
        this.H = h;

        date          = pct(0.05, 0.08,  0.55, 0.20);
        clock         = pct(0.05, 0.30,  0.55, 0.70);

        float cSlotW  = clock.width() / 4.5f;
        float cColonW = cSlotW * 0.5f;
        float cTotalW = cSlotW * 4 + cColonW;
        float cStartX = clock.left + (clock.width() - cTotalW) / 2f;
        clockHour   = new Rect((int) cStartX, clock.top,
                               (int)(cStartX + cSlotW * 2), clock.bottom);
        clockMinute = new Rect((int)(cStartX + cSlotW * 2 + cColonW), clock.top,
                               (int)(cStartX + cTotalW), clock.bottom);

        weatherIcon   = pct(0.62, 0.08,  0.78, 0.30);
        temperature   = pct(0.78, 0.08,  0.95, 0.30);
        conditionWord = pct(0.62, 0.32,  0.95, 0.40);
        weatherHL     = pct(0.62, 0.42,  0.95, 0.50);
        feelsLike     = pct(0.62, 0.50,  0.95, 0.56);
        aqi           = pct(0.62, 0.565, 0.95, 0.615);

        nextEvent     = pct(0.62, 0.625, 0.95, 0.755);

        separator     = pct(0.05, 0.775, 0.95, 0.780);
        calendarStrip = pct(0.05, 0.790, 0.95, 0.908);
        precipStrip   = pct(0.05, 0.914, 0.95, 0.944);
        footer        = pct(0.05, 0.950, 0.95, 0.980);
    }

    private Rect pct(double l, double t, double r, double b) {
        return new Rect((int)(l * W), (int)(t * H), (int)(r * W), (int)(b * H));
    }

    public static Rect alignToEightPixels(Rect r, int maxWidth) {
        int x     = (r.left / 8) * 8;
        int right = Math.min(maxWidth, ((r.right + 7) / 8) * 8);
        return new Rect(x, r.top, right, r.bottom);
    }

    public static Rect union(Rect a, Rect b) {
        return new Rect(
                Math.min(a.left,   b.left),
                Math.min(a.top,    b.top),
                Math.max(a.right,  b.right),
                Math.max(a.bottom, b.bottom));
    }
}
