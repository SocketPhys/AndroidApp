package org.incubatex.androidapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by avik on 6/17/2016.
 */
public class IncubateFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Map<String, String> notificationDataMap = remoteMessage.getData();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this.getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationDataMap.get("title"))
                .setContentText(notificationDataMap.get("text"))
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify((int)(Math.random()*Integer.MAX_VALUE), notificationBuilder.build());

        // set up Realm
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this.getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        NotificationData notificationData = realm.createObject(NotificationData.class);
        notificationData.setTitle(notificationDataMap.get("title"));
        notificationData.setText(notificationDataMap.get("text"));
        notificationData.setCreatedAt(0);//Long.parseLong(notificationDataMap.get("createdAt")));
        realm.commitTransaction();
        Log.d("Realm Data", realm.where(NotificationData.class).findAll().toString());
    }
}
