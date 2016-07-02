package org.incubatex.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by avikj on 7/2/16.
 */
public class NotificationDataListAdapter extends ArrayAdapter<NotificationData> {
    public NotificationDataListAdapter(Context context, int resource, NotificationData[] notificationsData) {
        super(context, resource, notificationsData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationData notificationData = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_notification_data, parent, false);
        ((TextView)rowView.findViewById(R.id.notificationTitle)).setText(notificationData.getTitle());
        ((TextView) rowView.findViewById(R.id.notificationText)).setText(notificationData.getText());
        return rowView;
    }
}
