package com.example.yukicalendar;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private TextView calendarNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarNameView = (TextView) findViewById(R.id.calendar_name);
        GetCalendarList calendarList = new GetCalendarList();
        calendarList.execute();
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }

    private class GetCalendarList extends AsyncTask<Void, Void, List<String>> {

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

        @Override
        protected List<String> doInBackground(Void... voids) {
            ContentResolver cr = getContentResolver();
            Uri uri = CalendarContract.Calendars.CONTENT_URI;

            List<String> calendarNameList = new ArrayList<>();
            // Submit the query and get a Cursor object back.
            Cursor cur = cr.query(uri, EVENT_PROJECTION, null, null, null);
            while (cur.moveToNext()) {
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;

                // Get the field values
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
                calendarNameList.add(displayName);
            }
            return calendarNameList;
        }

        @Override
        protected void onPostExecute(List<String> calendarNames) {
            super.onPostExecute(calendarNames);
            if (!calendarNames.isEmpty()) {
                calendarNameView.setText(calendarNames.get(0)); // Setting the name of the first calendar in the list
            } else {
                calendarNameView.setText(getString(R.string.no_calendars_found)); // Setting the name of the first calendar in the list
            }
        }

    }
}