package com.example.yukicalendar.tasks;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.example.yukicalendar.model.CalendarEvent;

import java.util.Calendar;

/**
 * @author p-v
 */

public class CreateCalendarEvent extends AsyncQueryHandler {

    private CalendarEvent event;

    public CreateCalendarEvent(ContentResolver cr, CalendarEvent event) {
        super(cr);
        this.event = event;
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
    }

    public void createEvent() {
        ContentValues values = this.event.getContentValues();
        startInsert(0, null, CalendarContract.Events.CONTENT_URI, values);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);
        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
    }
}
