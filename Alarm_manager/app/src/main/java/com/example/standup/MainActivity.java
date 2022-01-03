package com.example.standup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_FIRST_CHANNEL_ID =
            BuildConfig.APPLICATION_ID + "NOTIFICATION_FIRST_CHANNEL_ID";

    private static final long INTERVAL_TWO_MINUTES = 20 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = findViewById(R.id.alarm_toggle);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);

        toggleButton.setChecked(alarmUp);


        PendingIntent notifyPendingIntent =
                PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                        notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);



        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                displayToast("Stand Up alarm is On!");
                configAlarmManager(alarmManager, notifyPendingIntent);

            } else {
                if (alarmManager != null){
                    alarmManager.cancel(notifyPendingIntent);
                }
                displayToast("Stand Up alarm is Off!");
            }

        });


        createNotificationChannel();
    }

    private void configAlarmManager(AlarmManager alarmManager, PendingIntent pendingIntent) {
        //   long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

        long triggerAlarmTime = SystemClock.elapsedRealtime() + INTERVAL_TWO_MINUTES;
        // get the real time since boot, and plus with the interval



        if (alarmManager != null && pendingIntent != null) {

            alarmManager.setInexactRepeating( //a little variate can happen
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, //if the device is asleep it'll wake it
                    triggerAlarmTime,
                    INTERVAL_TWO_MINUTES,
                    pendingIntent
            );
        }
    }

    private void createNotificationChannel() {

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel
                    (NOTIFICATION_FIRST_CHANNEL_ID,
                            "First Channel",
                            NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setDescription("Notifies every 15 minutes to stand up and walk");

            notificationManager.createNotificationChannel(channel);
        }
    }

    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}