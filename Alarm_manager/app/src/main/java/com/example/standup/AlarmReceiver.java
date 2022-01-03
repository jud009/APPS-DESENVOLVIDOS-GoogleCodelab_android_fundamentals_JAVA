package com.example.standup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_FIRST_CHANNEL_ID =
            BuildConfig.APPLICATION_ID + "NOTIFICATION_FIRST_CHANNEL_ID";

    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        deliverNotification(context);
    }


    private void deliverNotification(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        The FLAG_UPDATE_CURRENT flag tells the system to use the old Intent
//        but replace the extras data. Because you don't have any extras in this
//        Intent, you can use the same PendingIntent over and over

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, NOTIFICATION_FIRST_CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_walk)
                .setContentText("It's time to stand up, walk around right now!")
                .setContentTitle("Stand Up Alert")
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);


        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}