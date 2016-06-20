package org.incubatex.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by avik on 6/19/2016.
 */
public class ScheduleEventListAdapter extends ArrayAdapter<CityData.ScheduleEvent> {
    public ScheduleEventListAdapter(Context context, int resource, CityData.ScheduleEvent[] scheduleEvents) {
        super(context, resource, scheduleEvents);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityData.ScheduleEvent scheduleEvent = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(scheduleEvent.getTime() != null) {
            View rowView = inflater.inflate(R.layout.list_item_schedule_event, parent, false);
            ((TextView) (rowView.findViewById(R.id.eventContentTextView))).setText(scheduleEvent.getName());
            ((TextView) (rowView.findViewById(R.id.timeTextView))).setText(scheduleEvent.getTime());
            return rowView;
        } else { // if scheduleEvent.time is null, the item represents a header label
            View rowView = inflater.inflate(R.layout.list_item_header, parent, false);
            ((TextView) (rowView.findViewById(R.id.headerTextView))).setText(scheduleEvent.getName());
            return rowView;
        }
    }
}
