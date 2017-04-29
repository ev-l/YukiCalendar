package com.example.yukicalendar.model;

import android.content.ContentValues;
import android.provider.CalendarContract;

import java.util.TimeZone;

public class CalendarEvent {

    private String title;
    private long eventId;
    private long calendarId;
    private long startTime;
    private long endTime;
    private boolean isAllDay;
    private String eventColor;


    public CalendarEvent(long eventId, long calendarId, String title, long startTime, long endTime, boolean isAllDay, String eventColor) {
        this.eventId = eventId;
        this.calendarId = calendarId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAllDay = isAllDay;
        this.eventColor = eventColor;
    }

    public CalendarEvent(long calendarId, String title, long startTime, long endTime, boolean isAllDay) {
        this(-1, calendarId, title, startTime, endTime, isAllDay, null);
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, this.startTime);
        values.put(CalendarContract.Events.DTEND, this.endTime);
        values.put(CalendarContract.Events.TITLE, this.title);
//        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, this.calendarId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        values.put(CalendarContract.Events.EVENT_COLOR, this.eventColor);
        return values;

    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
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

    public String getEventColor() {return this.eventColor; }
}
