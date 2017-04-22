package com.example.yukicalendar.yukiparser.parser.model;

import java.util.Calendar;

/**
 * @author p-v
 */

public class ParsedEvent {

    public Calendar dtStart;

    public void setDtStart(long dtStart) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dtStart);
        this.dtStart = cal;
    }

    public Calendar getDtStart() {
        return dtStart;
    }
}
