package ro.deskdash.epd.calendar;

public class CalendarEvent {
    public final String title;
    public final long   startMs;
    public final long   endMs;
    public final boolean isAllDay;

    public CalendarEvent(String title, long startMs, long endMs, boolean isAllDay) {
        this.title    = title;
        this.startMs  = startMs;
        this.endMs    = endMs;
        this.isAllDay = isAllDay;
    }
}
