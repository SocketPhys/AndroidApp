package org.incubatex.androidapp;

import io.realm.RealmObject;

/**
 * Created by avikj on 6/29/16.
 */
public class NotificationData extends RealmObject{
    private String title;
    private String text;

    public long getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    private long createdAt;

    public NotificationData(){
        // declared to make Realm happy :)
    }

    public NotificationData(String title, String text, long createdAt) {
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
    }


}
