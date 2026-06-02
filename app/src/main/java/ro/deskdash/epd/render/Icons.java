package ro.deskdash.epd.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Draws WMO weather-code icons onto a Canvas using pure black vector shapes.
 * No bitmaps — everything is Canvas primitives so it scales cleanly at any resolution.
 */
public class Icons {

    private static final int BLACK = 0xFF000000;

    /**
     * Draw the appropriate weather icon for the given WMO code into the given rect.
     * @param isDay 1 = daytime, 0 = nighttime (affects clear/partly-cloudy icon)
     */
    public static void drawWeather(Canvas canvas, Rect r, int wmoCode, int isDay) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(BLACK);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(strokeW(r));
        p.setStrokeCap(Paint.Cap.ROUND);

        if (wmoCode == 0) {
            if (isDay == 1) drawSun(canvas, r, p);
            else            drawMoon(canvas, r, p);
        } else if (wmoCode <= 2) {
            if (isDay == 1) drawSunCloud(canvas, r, p);
            else            drawMoonCloud(canvas, r, p);
        } else if (wmoCode == 3) {
            drawCloud(canvas, r, p);
        } else if (wmoCode == 45 || wmoCode == 48) {
            drawFog(canvas, r, p);
        } else if (wmoCode >= 51 && wmoCode <= 55) {
            drawDrizzle(canvas, r, p);
        } else if ((wmoCode >= 61 && wmoCode <= 65) || (wmoCode >= 80 && wmoCode <= 82)) {
            drawRain(canvas, r, p);
        } else if ((wmoCode >= 71 && wmoCode <= 75) || wmoCode == 85 || wmoCode == 86) {
            drawSnow(canvas, r, p);
        } else if (wmoCode == 95 || wmoCode == 96 || wmoCode == 99) {
            drawThunder(canvas, r, p);
        } else {
            drawCloud(canvas, r, p);
        }
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private static float strokeW(Rect r) {
        return Math.max(6f, r.width() * 0.04f);
    }

    private static float cx(Rect r) { return r.left + r.width() / 2f; }
    private static float cy(Rect r) { return r.top  + r.height() / 2f; }

    // Sun: circle + 8 rays
    private static void drawSun(Canvas c, Rect r, Paint p) {
        float radius = r.width() * 0.22f;
        float cx = cx(r), cy = cy(r);
        p.setStyle(Paint.Style.STROKE);
        c.drawCircle(cx, cy, radius, p);
        float rayLen = r.width() * 0.15f;
        float rayStart = radius + r.width() * 0.06f;
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            float dx = (float) Math.cos(angle);
            float dy = (float) Math.sin(angle);
            c.drawLine(cx + dx * rayStart, cy + dy * rayStart,
                       cx + dx * (rayStart + rayLen), cy + dy * (rayStart + rayLen), p);
        }
    }

    // Moon: crescent (large circle minus offset circle via path)
    private static void drawMoon(Canvas c, Rect r, Paint p) {
        float cx = cx(r), cy = cy(r);
        float radius = r.width() * 0.28f;
        p.setStyle(Paint.Style.STROKE);
        // Draw an arc from ~30° to ~330° for a simple crescent look
        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        c.drawArc(oval, 30, 300, false, p);
        // Inner circle offset to make the crescent
        float inner = radius * 0.75f;
        float offX  = radius * 0.35f;
        RectF ovalInner = new RectF(cx - inner + offX, cy - inner,
                                    cx + inner + offX, cy + inner);
        p.setColor(0xFFFFFFFF);
        p.setStyle(Paint.Style.FILL);
        c.drawArc(ovalInner, 0, 360, true, p);
        // Re-draw outer arc border on top
        p.setColor(BLACK);
        p.setStyle(Paint.Style.STROKE);
        c.drawArc(oval, 30, 300, false, p);
    }

    // Cloud: a composite RectF shape
    private static void drawCloud(Canvas c, Rect r, Paint p) {
        float cw = r.width() * 0.80f;
        float ch = r.height() * 0.45f;
        float left   = r.left + (r.width() - cw) / 2f;
        float top    = r.top  + (r.height() - ch) / 2f + r.height() * 0.05f;
        Path path = cloudPath(left, top, cw, ch);
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(path, p);
    }

    private static Path cloudPath(float left, float top, float w, float h) {
        Path path = new Path();
        float r1 = h * 0.45f;
        float r2 = h * 0.35f;
        float r3 = h * 0.28f;
        float bottom = top + h;
        float bumpY  = top + h * 0.10f;
        // Bottom straight line
        path.moveTo(left + r3, bottom);
        path.lineTo(left + w - r1, bottom);
        // Right dome
        path.arcTo(new RectF(left + w - 2 * r1, bumpY, left + w, bumpY + 2 * r1), 90, -180, false);
        // Middle dome
        path.arcTo(new RectF(left + w * 0.35f, top, left + w * 0.35f + 2 * r2, top + 2 * r2),
                90, -180, false);
        // Left dome
        path.arcTo(new RectF(left, bumpY, left + 2 * r3, bumpY + 2 * r3), 90, -180, false);
        path.close();
        return path;
    }

    // Sun partially behind cloud: sun in upper-right, cloud shifted lower-left
    private static void drawSunCloud(Canvas c, Rect r, Paint p) {
        // Small sun upper-right
        Rect sunR = new Rect(r.left + r.width() / 2, r.top,
                             r.right, r.top + r.height() / 2);
        drawSun(c, sunR, p);
        // Cloud lower-left (slightly larger)
        float cw = r.width() * 0.70f;
        float ch = r.height() * 0.38f;
        float left = r.left + r.width() * 0.04f;
        float top  = r.top  + r.height() * 0.45f;
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(cloudPath(left, top, cw, ch), p);
    }

    // Moon + cloud: same layout as sun+cloud but moon in upper-right
    private static void drawMoonCloud(Canvas c, Rect r, Paint p) {
        Rect moonR = new Rect(r.left + r.width() / 2, r.top,
                              r.right, r.top + r.height() / 2);
        drawMoon(c, moonR, p);
        float cw = r.width() * 0.70f;
        float ch = r.height() * 0.38f;
        float left = r.left + r.width() * 0.04f;
        float top  = r.top  + r.height() * 0.45f;
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(cloudPath(left, top, cw, ch), p);
    }

    // Fog: cloud + horizontal lines below
    private static void drawFog(Canvas c, Rect r, Paint p) {
        float cw = r.width() * 0.80f;
        float ch = r.height() * 0.35f;
        float left = r.left + (r.width() - cw) / 2f;
        float top  = r.top + r.height() * 0.05f;
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(cloudPath(left, top, cw, ch), p);
        // Fog lines
        float lineTop = r.top + r.height() * 0.55f;
        float lStep   = r.height() * 0.12f;
        for (int i = 0; i < 3; i++) {
            float y = lineTop + i * lStep;
            c.drawLine(left + cw * 0.05f, y, left + cw * 0.95f, y, p);
        }
    }

    // Drizzle: cloud + small dots
    private static void drawDrizzle(Canvas c, Rect r, Paint p) {
        float cw   = r.width() * 0.80f;
        float ch   = r.height() * 0.38f;
        float left = r.left + (r.width() - cw) / 2f;
        float top  = r.top  + r.height() * 0.04f;
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(cloudPath(left, top, cw, ch), p);
        p.setStyle(Paint.Style.FILL);
        float dotR  = strokeW(r) * 0.8f;
        float dotY  = r.top + r.height() * 0.68f;
        float dotY2 = r.top + r.height() * 0.82f;
        float spacing = cw / 4f;
        for (int i = 0; i < 3; i++) {
            float x = left + spacing * (i + 0.75f);
            c.drawCircle(x, dotY,  dotR, p);
        }
        for (int i = 0; i < 2; i++) {
            float x = left + spacing * (i + 1.25f);
            c.drawCircle(x, dotY2, dotR, p);
        }
    }

    // Rain: cloud + diagonal lines
    private static void drawRain(Canvas c, Rect r, Paint p) {
        float cw   = r.width() * 0.80f;
        float ch   = r.height() * 0.38f;
        float left = r.left + (r.width() - cw) / 2f;
        float top  = r.top  + r.height() * 0.04f;
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(cloudPath(left, top, cw, ch), p);
        float rainTop = r.top + r.height() * 0.55f;
        float spacing = cw / 5f;
        for (int i = 0; i < 4; i++) {
            float x1 = left + spacing * (i + 0.6f);
            float x2 = x1 - r.height() * 0.10f;
            c.drawLine(x1, rainTop, x2, rainTop + r.height() * 0.30f, p);
        }
    }

    // Snow: cloud + asterisk symbols
    private static void drawSnow(Canvas c, Rect r, Paint p) {
        float cw   = r.width() * 0.80f;
        float ch   = r.height() * 0.38f;
        float left = r.left + (r.width() - cw) / 2f;
        float top  = r.top  + r.height() * 0.04f;
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(cloudPath(left, top, cw, ch), p);
        float snowY   = r.top + r.height() * 0.74f;
        float armLen  = r.width() * 0.05f;
        float spacing = cw / 4f;
        for (int i = 0; i < 3; i++) {
            float x = left + spacing * (i + 0.75f);
            for (int a = 0; a < 3; a++) {
                double angle = Math.toRadians(a * 60);
                float dx = (float) Math.cos(angle) * armLen;
                float dy = (float) Math.sin(angle) * armLen;
                c.drawLine(x - dx, snowY - dy, x + dx, snowY + dy, p);
            }
        }
    }

    // Thunder: cloud + lightning bolt
    private static void drawThunder(Canvas c, Rect r, Paint p) {
        float cw   = r.width() * 0.80f;
        float ch   = r.height() * 0.38f;
        float left = r.left + (r.width() - cw) / 2f;
        float top  = r.top  + r.height() * 0.04f;
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(cloudPath(left, top, cw, ch), p);
        // Bolt
        float bx  = cx(r);
        float by1 = r.top + r.height() * 0.52f;
        float by2 = r.top + r.height() * 0.68f;
        float by3 = r.top + r.height() * 0.90f;
        float off = r.width() * 0.07f;
        Path bolt = new Path();
        bolt.moveTo(bx + off, by1);
        bolt.lineTo(bx - off * 0.3f, by2);
        bolt.lineTo(bx + off * 0.5f, by2);
        bolt.lineTo(bx - off, by3);
        p.setStyle(Paint.Style.STROKE);
        c.drawPath(bolt, p);
    }
}
