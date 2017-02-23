package com.example.yukicalendar.tasks;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;

import com.example.yukicalendar.model.UserCalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author p-v
 */

public class GetAccountCalendars extends AsyncTask<Void, Void, Map<String, List<UserCalendar>>> {

    public interface OnAccountCalendarResponseListener {
        void onAccountCalendarResponse(Map<String, List<UserCalendar>> accountCalendarMap);
    }

    public final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private Context context;
    private OnAccountCalendarResponseListener onAccountCalendarResponseListener;

    public GetAccountCalendars(Context context) {
        this.context = context;
    }

    @Override
    protected Map<String, List<UserCalendar>> doInBackground(Void... voids) {
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        Map<String, List<UserCalendar>> accountCalendarMap = new HashMap<>();
        // Submit the query and get a Cursor object back.
        Cursor cur = cr.query(uri, EVENT_PROJECTION, null, null, null);
        while (cur.moveToNext()) {
            // Get the field values
            long calID = cur.getLong(PROJECTION_ID_INDEX);
            String displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            String accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            String ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
            UserCalendar userCalendar = new UserCalendar(calID, displayName, accountName, ownerName);
            if (!accountCalendarMap.containsKey(accountName)) {
                accountCalendarMap.put(accountName, new ArrayList<UserCalendar>());
            }
            accountCalendarMap.get(accountName).add(userCalendar);
        }
        return accountCalendarMap;
    }

    @Override
    protected void onPostExecute(Map<String, List<UserCalendar>> accountCalendarMap) {
        if (onAccountCalendarResponseListener != null) {
            onAccountCalendarResponseListener.onAccountCalendarResponse(accountCalendarMap);
        }
    }

    public void setOnAccountCalendarResponseListener(OnAccountCalendarResponseListener onAccountCalendarResponseListener) {
        this.onAccountCalendarResponseListener = onAccountCalendarResponseListener;
    }

}
