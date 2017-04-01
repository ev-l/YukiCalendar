package com.example.yukicalendar.tasks;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;

import com.example.yukicalendar.model.CalendarEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author p-v
 */

public class GetEventsForCalendarTask extends AsyncTask<Void, Void, List<CalendarEvent>> {

    public final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Events._ID,                           // 0
            CalendarContract.Events.CALENDAR_ID,                   // 1
            CalendarContract.Events.TITLE                          // 2
    };
    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_CALENDAR_ID_INDEX = 1;
    private static final int PROJECTION_TITLE_INDEX = 2;

    public interface OnCalendarEventsResponseListener {
        void onCalendarEventsResponse(List<CalendarEvent> calendarEvents);
    }

    private Context context;
    private long calendarId;
    private OnCalendarEventsResponseListener onCalendarEventsResponseListener;

    public GetEventsForCalendarTask(Context context, long calendarId) {
        this.context = context;
        this.calendarId = calendarId;
    }

    @Override
    protected List<CalendarEvent> doInBackground(Void... voids) {
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String[] selectionArgs = {String.valueOf(this.calendarId)};
        Cursor cur = cr.query(uri, EVENT_PROJECTION, CalendarContract.Events.CALENDAR_ID + "=?", selectionArgs, null);
        List<CalendarEvent> calendarEvents = new ArrayList<>();
        if (cur != null) {
            while (cur.moveToNext()) {
                // Get the field values
                long eventId = cur.getLong(PROJECTION_ID_INDEX);
                long calendarId = cur.getLong(PROJECTION_CALENDAR_ID_INDEX);
                String title = cur.getString(PROJECTION_TITLE_INDEX);
                CalendarEvent event = new CalendarEvent(eventId, calendarId, title);
                calendarEvents.add(event);
            }
            cur.close();
        }
        return calendarEvents;
    }

    @Override
    protected void onPostExecute(List<CalendarEvent> calendarEvents) {
        if (onCalendarEventsResponseListener != null) {
            onCalendarEventsResponseListener.onCalendarEventsResponse(calendarEvents);
        }
    }


    public void setOnCalendarEventsResponseListener(OnCalendarEventsResponseListener onCalendarEventsResponseListener) {
        this.onCalendarEventsResponseListener = onCalendarEventsResponseListener;
    }
}
