package com.example.wakeup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "primary_notification_channel";

    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        String msg = intent.getStringExtra(MainActivity.INTENT_DATA);
        configNotification(context, msg);
    }


    private void configNotification(Context context, String message) {

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context,
                NOTIFICATION_CHANNEL_ID);

        notification
                .setSmallIcon(R.drawable.ic_android)
                .setContentTitle("It's time to wake up")
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        ;

        manager.notify(NOTIFICATION_ID, notification.build());
    }
}
