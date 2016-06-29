package org.incubatex.androidapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by avikj on 6/29/16.
 */
public class Tweet {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
    private String text;
    private User user;
    private String createdAt;

    public String getText(){ return text; }
    public User getUser(){ return user; }
    public Date getTimestamp(){
        try {
            return dateFormat.parse(createdAt);
        } catch(ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static class User {
        private String name;
        private String screenName;

        public String getName(){ return name; }
        public String getScreenName(){ return screenName; }
    }
}
