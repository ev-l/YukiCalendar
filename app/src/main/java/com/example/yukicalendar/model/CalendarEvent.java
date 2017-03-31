package com.example.yukicalendar.model;

/**
 * @author p-v
 */

public class CalendarEvent {

    private String title;
    private long eventId;
    private long calendarId;

    public CalendarEvent(long eventId, long calendarId, String title) {
        this.eventId = eventId;
        this.calendarId = calendarId;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public long getEventId() {
        return this.eventId;
    }

    public long getCalendarId() {
        return this.calendarId;
    }
}
