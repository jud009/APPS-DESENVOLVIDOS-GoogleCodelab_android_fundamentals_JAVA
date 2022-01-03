package com.example.notificationme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    public static final String PRIMARY_NOTIFICATION_ID = "primary_notification_id";
    public static final String
            ACTION_UPDATE_NOTIFICATION = BuildConfig.APPLICATION_ID + "ACTION_UPDATE_NOTIFICATION";
    public static final int NOTIFICATION_ID = 1;

    private final NotificationReceiver receiver = new NotificationReceiver();

    private NotificationManager notificationManager;
    private Button notifyButton, updateButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notifyButton = findViewById(R.id.notify);
        updateButton = findViewById(R.id.update);
        cancelButton = findViewById(R.id.cancel);

        notifyButton.setOnClickListener(view -> sendNotification());
        updateButton.setOnClickListener(view -> updateNotification());
        cancelButton.setOnClickListener(view -> cancelNotification());

        createNotificationChannel();

        registerReceiver(receiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
        //now I'm delegating the responsibility to the android framework

        setNotificationButtonState(true, false, false);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void setNotificationButtonState
            (Boolean isNotifyEnable, Boolean isUpdateEnable, Boolean isCancelEnable) {

        notifyButton.setEnabled(isNotifyEnable);
        updateButton.setEnabled(isUpdateEnable);
        cancelButton.setEnabled(isCancelEnable);
    }


    private void createNotificationChannel() {
        notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        //get the service from the system

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //check if is android 8 or higher

            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_NOTIFICATION_ID,
                    "First notification",
                    NotificationManager.IMPORTANCE_DEFAULT); //create a channel

            configNotificationChannel(notificationChannel);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void configNotificationChannel(NotificationChannel channel) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            channel.setDescription("First Notification Edit");
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {

        Intent intent = new Intent(this, MainActivity.class);

        //pending intent is use when you want to execute a predefined code in the future
        PendingIntent pendingIntent = PendingIntent.getActivity
                (this,
                        NOTIFICATION_ID,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notify = new NotificationCompat.Builder
                (this, PRIMARY_NOTIFICATION_ID);

        notify.setSmallIcon(R.drawable.ic_android)
                .setContentTitle("Notify me")
                .setContentText("this is the notify me text")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        //setAutoCancel(true) when the user taps the notification closes

        return notify;
    }

    private void sendNotification() {
        Intent intent = new Intent(ACTION_UPDATE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = getNotificationBuilder();
        mBuilder.addAction(R.drawable.ic_update, "Notification Action", pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        setNotificationButtonState(false, true, true);
    }

    private void updateNotification() {

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder builder = getNotificationBuilder();

//        builder.setStyle(new NotificationCompat.BigPictureStyle()
//                .bigLargeIcon(bitmap).setBigContentTitle("Notification updated!"));

        builder.setStyle(new NotificationCompat.InboxStyle()
                .addLine("First line")
                .addLine("Second line")
                .addLine("Third line")
                .addLine("fourth line")
                .setBigContentTitle("Content title")
                .setSummaryText("Summary")
        );


        PendingIntent intent =
                PendingIntent.getBroadcast
                        (this, NOTIFICATION_ID,
                                new Intent(ACTION_UPDATE_NOTIFICATION), PendingIntent.FLAG_ONE_SHOT);
        builder.addAction(R.drawable.ic_update, "New test", intent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

        setNotificationButtonState(false, false, true);
    }

    private void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    public static class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }
}
