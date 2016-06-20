package org.incubatex.androidapp;

/**
 * Created by avik on 6/16/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.GsonBuilder;

public class ScheduleFragment extends Fragment {
    private CityData cityData;
    ListView scheduleListView;
    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cityData = new GsonBuilder().create().fromJson(getArguments().getString("cityData"), CityData.class);
        Log.d("cityData", cityData.toString());
        View rootView = inflater.inflate(org.incubatex.androidapp.R.layout.fragment_schedule, container, false);

        scheduleListView = (ListView) rootView.findViewById(R.id.scheduleListView);
        CityData.ScheduleEvent[] saturdayEvents = cityData.getScheduleSaturday();
        CityData.ScheduleEvent[] sundayEvents = cityData.getScheduleSunday();
        CityData.ScheduleEvent[] scheduleEventsWithHeaders = new CityData.ScheduleEvent[sundayEvents.length+saturdayEvents.length+2];
        scheduleEventsWithHeaders[0] = new CityData.ScheduleEvent("Saturday", null);
        for(int i = 0; i < saturdayEvents.length; i++){
            scheduleEventsWithHeaders[i+1] = saturdayEvents[i];
        }
        scheduleEventsWithHeaders[saturdayEvents.length+1] = new CityData.ScheduleEvent("Sunday", null);
        for(int i = 0; i < sundayEvents.length; i++){
            scheduleEventsWithHeaders[i+2+saturdayEvents.length] = sundayEvents[i];
        }
        scheduleListView.setAdapter(
                new ScheduleEventListAdapter(this.getContext(), R.layout.list_item_schedule_event, scheduleEventsWithHeaders));

        return rootView;
    }
}