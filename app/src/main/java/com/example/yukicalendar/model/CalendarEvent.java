package com.example.yukicalendar.model;

import android.content.ContentValues;
import android.provider.CalendarContract;

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

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, this.startTime);
        values.put(CalendarContract.Events.DTEND, this.endTime);
        values.put(CalendarContract.Events.TITLE, this.title);
//        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, this.calendarId);
//        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        return values;

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
