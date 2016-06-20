package org.incubatex.androidapp;

import com.google.gson.GsonBuilder;

/**
 * Created by avik on 6/16/2016.
 */
public class CityData {
    private String city;
    private String color;
    private String twitterSearchTerm;
    private ScheduleEvent[] scheduleSaturday;
    private ScheduleEvent[] scheduleSunday;

    public String getCity(){ return city; }
    public String getColor(){ return color; }
    public String getTwitterSearchTerm(){ return twitterSearchTerm; }
    public ScheduleEvent[] getScheduleSaturday(){ return scheduleSaturday; }
    public ScheduleEvent[] getScheduleSunday(){ return scheduleSunday; }

    static class ScheduleEvent {
        private String name;
        private String time;

        public ScheduleEvent(String name, String time){
            this.name = name;
            this.time = time;
        }

        public String getName(){ return name; }
        public String getTime(){ return time; }

        @Override
        public String toString(){
            return time+": "+name;
        }
    }


    public String toString(){
        return new GsonBuilder().create().toJson(this).toString();
    }
}
