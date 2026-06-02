package ro.deskdash.epd.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ICalParser {

    private static final TimeZone UTC          = TimeZone.getTimeZone("UTC");
    private static final TimeZone TZ_BUCHAREST = TimeZone.getTimeZone("Europe/Bucharest");

    /**
     * Parse an iCal string and return events whose start falls within [windowStart, windowEnd).
     * Handles UTC datetimes, TZID-tagged datetimes, and all-day DATE values.
     * Skips CANCELLED events.
     */
    public static List<CalendarEvent> parse(String ics, long windowStart, long windowEnd) {
        List<CalendarEvent> result = new ArrayList<>();
        String[] lines = unfold(ics).split("\n");

        boolean inEvent = false;
        String  summary = null;
        String  status  = null;
        long    startMs = -1;
        long    endMs   = -1;
        boolean allDay  = false;

        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty()) continue;

            if (line.equals("BEGIN:VEVENT")) {
                inEvent = true;
                summary = null; status = null; startMs = -1; endMs = -1; allDay = false;
                continue;
            }
            if (line.equals("END:VEVENT")) {
                if (inEvent && summary != null && startMs >= 0
                        && !"CANCELLED".equalsIgnoreCase(status)
                        && startMs >= windowStart && startMs < windowEnd) {
                    result.add(new CalendarEvent(summary, startMs, endMs < 0 ? startMs : endMs, allDay));
                }
                inEvent = false;
                continue;
            }

            if (!inEvent) continue;

            if (line.startsWith("SUMMARY:")) {
                summary = unescape(line.substring(8));
            } else if (line.startsWith("STATUS:")) {
                status = line.substring(7).trim();
            } else if (line.startsWith("DTSTART")) {
                long[] parsed = parseDt(line);
                if (parsed != null) { startMs = parsed[0]; allDay = parsed[1] == 1; }
            } else if (line.startsWith("DTEND")) {
                long[] parsed = parseDt(line);
                if (parsed != null) endMs = parsed[0];
            }
        }
        return result;
    }

    // ─── Helpers ──────────────────────────────────────────────────────────

    /** Remove iCal line-folding (CRLF/LF + whitespace = continuation). */
    private static String unfold(String ics) {
        return ics
                .replace("\r\n ", "").replace("\r\n\t", "")
                .replace("\n ",   "").replace("\n\t",   "");
    }

    /** Un-escape iCal text escapes: \n \, \; \\ */
    private static String unescape(String s) {
        return s.replace("\\n", "\n")
                .replace("\\N", "\n")
                .replace("\\,", ",")
                .replace("\\;", ";")
                .replace("\\\\", "\\");
    }

    /**
     * Parse DTSTART or DTEND line.
     * Returns [epochMs, isAllDay(0/1)] or null on failure.
     *
     * Supported forms:
     *   DTSTART:20260529T140000Z                    (UTC)
     *   DTSTART;TZID=Europe/Bucharest:20260529T160000  (local with tz)
     *   DTSTART;VALUE=DATE:20260529                 (all-day)
     */
    private static long[] parseDt(String line) {
        int colon = line.indexOf(':');
        if (colon < 0) return null;
        String value   = line.substring(colon + 1).trim();
        String propStr = line.substring(0, colon);
        String propUp  = propStr.toUpperCase(Locale.ROOT);

        try {
            // All-day event
            if (propUp.contains("VALUE=DATE") || (value.length() == 8 && !value.contains("T"))) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ROOT);
                sdf.setTimeZone(TZ_BUCHAREST);
                return new long[]{ sdf.parse(value).getTime(), 1 };
            }

            // UTC datetime ends with Z
            if (value.endsWith("Z")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.ROOT);
                sdf.setTimeZone(UTC);
                return new long[]{ sdf.parse(value).getTime(), 0 };
            }

            // Local datetime — use TZID if present, else Bucharest
            TimeZone tz = TZ_BUCHAREST;
            int tzIdx = propUp.indexOf("TZID=");
            if (tzIdx >= 0) {
                int tzStart = tzIdx + 5;
                int tzEnd   = propStr.indexOf(';', tzStart);
                String tzId = propStr.substring(tzStart, tzEnd < 0 ? propStr.length() : tzEnd);
                TimeZone candidate = TimeZone.getTimeZone(tzId);
                if (!candidate.getID().equals("GMT")) tz = candidate;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ROOT);
            sdf.setTimeZone(tz);
            return new long[]{ sdf.parse(value).getTime(), 0 };

        } catch (ParseException e) {
            return null;
        }
    }
}
