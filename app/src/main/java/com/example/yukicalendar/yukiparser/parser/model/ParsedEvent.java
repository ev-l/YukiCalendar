package com.example.yukicalendar.yukiparser.parser.model;

import com.example.yukicalendar.model.CalendarEvent;

import java.util.Calendar;

/**
 * @author p-v
 */

public class ParsedEvent {

    private Calendar dtStart;
    private Calendar dtEnd;
    private long calendarId;
    private String title;

    public void setDtStart(long dtStart) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dtStart);
        this.dtStart = cal;
    }

    public Calendar getDtStart() {
        return dtStart;
    }

    public void setDtEnd(long dtEnd) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dtEnd);
        this.dtEnd = cal;
    }

    public Calendar getDtEnd() {
        return dtEnd;
    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CalendarEvent toCalendarEvent() {
        return new CalendarEvent(-1, calendarId, title, dtStart.getTimeInMillis(), dtEnd.getTimeInMillis(), false);
    }
}
