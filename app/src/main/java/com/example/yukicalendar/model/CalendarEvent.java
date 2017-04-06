package com.example.yukicalendar.model;

/**
 * @author p-v
 */

public class CalendarEvent {

    private String title;
    private long eventId;
    private long calendarId;
    private long startTime;
    private long endTime;
    private boolean isAllDay;

    public CalendarEvent(long eventId, long calendarId, String title, long startTime, long endTime, boolean isAllDay) {
        this.eventId = eventId;
        this.calendarId = calendarId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAllDay = isAllDay;
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

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isAllDay() {
        return isAllDay;
    }
}
