package com.example.yukicalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yukicalendar.adapter.EventsAdapter;
import com.example.yukicalendar.model.CalendarEvent;
import com.example.yukicalendar.model.UserAccount;
import com.example.yukicalendar.model.UserCalendar;
import com.example.yukicalendar.tasks.GetAccountCalendars;
import com.example.yukicalendar.tasks.GetEventsForCalendarTask;
import com.example.yukicalendar.utils.CalendarUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GetAccountCalendars.OnAccountCalendarResponseListener,
        GetEventsForCalendarTask.OnCalendarEventsResponseListener, AdapterView.OnItemSelectedListener {



    private Spinner accountSpinner;
    private NavigationView navigationView;

    private Map<UserAccount, List<UserCalendar>> accountCalendarMap;
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventAdapter;
    private View emptyEventsView;
    private int selectedCalendarId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        final ActionBar actionBar = getSupportActionBar();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCalendarId == -1) {
                    Toast.makeText(MainScreenActivity.this, "Please select a calendar", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainScreenActivity.this, EventCreationActivity.class);
                    intent.putExtra(EventCreationActivity.CALENDAR_ID, selectedCalendarId);
                    startActivity(intent);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        accountSpinner = (Spinner) header.findViewById(R.id.account_list_spinner);
        accountSpinner.setOnItemSelectedListener(this);

        eventsRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);
        emptyEventsView = findViewById(R.id.no_events_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        eventsRecyclerView.setLayoutManager(layoutManager);
        eventAdapter = new EventsAdapter();
        eventsRecyclerView.setAdapter(eventAdapter);
        eventsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private CalendarEvent prevEvent;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = layoutManager.findFirstVisibleItemPosition();
                CalendarEvent event = eventAdapter.getEventAtPosition(pos);
                if (prevEvent != event) {
                    toolbar.setTitle(CalendarUtils.getDisplayMonth(event.getStartTime()));
                }
                prevEvent = event;
            }
        });
        fetchAllCalendar();
        fetchCalendarEvents(-1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int calendarId = item.getItemId();
        this.selectedCalendarId = calendarId;
        fetchCalendarEvents(calendarId);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAccountCalendarResponse(Map<UserAccount, List<UserCalendar>> accountCalendarMap) {
        this.accountCalendarMap = accountCalendarMap;
        if (accountCalendarMap != null && !accountCalendarMap.isEmpty()) {
            Set<UserAccount> accounts = accountCalendarMap.keySet(); // This returns a set
            String[] accountArray = new String[accounts.size()];
            int i = 0;
            // converting to array..
            for (UserAccount account : accounts) {
                accountArray[i++] = account.getAccountName();
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountArray);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            accountSpinner.setAdapter(dataAdapter);
        }
    }

    private void fetchCalendarEvents(int calendarId) {
        GetEventsForCalendarTask calendarEventsTask = new GetEventsForCalendarTask(this, calendarId);
        calendarEventsTask.setOnCalendarEventsResponseListener(this);
        calendarEventsTask.execute();
    }

    private void fetchAllCalendar() {
        GetAccountCalendars getAccountCalendars = new GetAccountCalendars(this);
        getAccountCalendars.setOnAccountCalendarResponseListener(this);
        getAccountCalendars.execute();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String account = (String) adapterView.getItemAtPosition(i);

        // Get calendars for account
        List<UserCalendar> calendars = accountCalendarMap.get(new UserAccount(account));

        Menu m = navigationView.getMenu();
        m.clear(); // clear the exiting menu

        m.add(-1, -1, Menu.NONE, "All Events");

        SubMenu topChannelMenu = m.addSubMenu("Calendars");
        if (calendars != null) {
            for (UserCalendar cal: calendars) {
                topChannelMenu.add((int)cal.getCalID(),
                        (int)cal.getCalID(), Menu.NONE, cal.getDisplayName());
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onCalendarEventsResponse(List<CalendarEvent> calendarEvents) {
        if (calendarEvents != null && !calendarEvents.isEmpty()) {
            eventAdapter.setCalendarEventList(calendarEvents);
            int position = eventAdapter.getPositionOfFirstUpcomingEvent();
            if (position != -1) {
                eventsRecyclerView.scrollToPosition(position);
            }
            eventsRecyclerView.setVisibility(View.VISIBLE);
            emptyEventsView.setVisibility(View.GONE);
        } else {
            emptyEventsView.setVisibility(View.VISIBLE);
            eventsRecyclerView.setVisibility(View.GONE);
        }
    }

}
