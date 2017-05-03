package com.example.yukicalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yukicalendar.R;
import com.example.yukicalendar.model.CalendarEvent;
import com.example.yukicalendar.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author p-v
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private List<CalendarEvent> calendarEventList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.eventTitleView.getContext();
        CalendarEvent calendarEvent = calendarEventList.get(position);
        String title = calendarEvent.getTitle();
        String noTitle = holder.eventTitleView.getContext().getString(R.string.no_title);
        holder.eventTitleView.setText(TextUtils.isEmpty(title) ? noTitle : title);

        Calendar startTimeCal = Calendar.getInstance();
        startTimeCal.setTimeInMillis(calendarEvent.getStartTime());
        // Event duration
        String startTime = CalendarUtils.getDisplayTime(
                context, startTimeCal, Locale.getDefault());
        String endTime = CalendarUtils.getDisplayTime(
                context, calendarEvent.getEndTime(), Locale.getDefault());
        String eventDuration = startTime + " - " + endTime;
        holder.eventDurationView.setText(eventDuration);

        if (position > 0) {
            CalendarEvent prevEvent = calendarEventList.get(position - 1);
            if (CalendarUtils.isSameDate(calendarEvent.getStartTime(), prevEvent.getStartTime())) {
                holder.eventDayView.setVisibility(View.INVISIBLE);
            } else {
                holder.eventDayView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.eventDayView.setVisibility(View.VISIBLE);
        }

        holder.eventDom.setText(String.valueOf(CalendarUtils.getDayOfMonth(startTimeCal)));
        holder.eventDow.setText(CalendarUtils.getDayOfWeek(startTimeCal));
        holder.eventRow.setBackgroundColor(Integer.parseInt(calendarEvent.getEventColor()));
    }

    public CalendarEvent getEventAtPosition(int position) {
        if (position >= 0 && position < calendarEventList.size()) {
            return calendarEventList.get(position);
        }
        return null;
    }

    public int getPositionOfFirstUpcomingEvent() {
        if (calendarEventList != null && calendarEventList.size() > 0) {
            int pos = calendarEventList.size() - 1;

            long currentTimeInMillis = System.currentTimeMillis();
            int i = pos;
            for (; i >= 0; i--) {
                CalendarEvent event = calendarEventList.get(i);
                if (currentTimeInMillis > event.getStartTime()) {
                    break;
                }
            }
            if (i == pos) {
                return pos;
            }
            return i + 1;
        }
        return -1;
    }


    @Override
    public int getItemCount() {
        return calendarEventList == null ? 0 : calendarEventList.size();
    }

    public void setCalendarEventList(List<CalendarEvent> calendarEventList) {
        Collections.sort(calendarEventList, new Comparator<CalendarEvent>() {
            @Override
            public int compare(CalendarEvent o1, CalendarEvent o2) {
                return ((int)(o1.getStartTime()/1000) - (int)(o2.getStartTime()/1000));
            }
        });
        this.calendarEventList = calendarEventList;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitleView;
        TextView eventDurationView;
        TextView eventDow; // Day of week
        TextView eventDom; // Day of month
        View eventRow;
        View eventDayView;
        ViewHolder(View itemView) {
            super(itemView);
            eventDayView = itemView.findViewById(R.id.event_day);
            eventTitleView = (TextView) itemView.findViewById(R.id.event_title);
            eventDurationView = (TextView) itemView.findViewById(R.id.event_duration);
            eventDom = (TextView) itemView.findViewById(R.id.event_dom);
            eventDow = (TextView) itemView.findViewById(R.id.event_dow);
            eventRow = itemView.findViewById(R.id.event_row);
        }
    }
}
