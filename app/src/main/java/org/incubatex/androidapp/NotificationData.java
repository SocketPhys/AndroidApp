package org.incubatex.androidapp;

import com.google.gson.GsonBuilder;

import io.realm.RealmObject;

/**
 * Created by avikj on 6/29/16.
 */
public class NotificationData extends RealmObject{
    private String title;
    private String text;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

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

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}
