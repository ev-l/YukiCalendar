package com.example.yukicalendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yukicalendar.R;
import com.example.yukicalendar.model.CalendarEvent;

import java.util.List;

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
        CalendarEvent calendarEvent = calendarEventList.get(position);
        String title = calendarEvent.getTitle();
        String noTitle = holder.eventTitleView.getContext().getString(R.string.no_title);
        holder.eventTitleView.setText(TextUtils.isEmpty(title) ? noTitle : title);
    }

    @Override
    public int getItemCount() {
        return calendarEventList == null ? 0 : calendarEventList.size();
    }

    public void setCalendarEventList(List<CalendarEvent> calendarEventList) {
        this.calendarEventList = calendarEventList;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitleView;
        ViewHolder(View itemView) {
            super(itemView);
            eventTitleView = (TextView) itemView.findViewById(R.id.event_title);
        }
    }
}
