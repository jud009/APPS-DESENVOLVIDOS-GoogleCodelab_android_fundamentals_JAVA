package com.example.wakeup;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static SimpleDateFormat sdf =
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final String INTENT_DATA = "intent_data";

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private Spinner spinnerHour, spinnerMinute;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerHour = findViewById(R.id.spinner_hour);
        spinnerMinute = findViewById(R.id.spinner_minutes);
        ToggleButton button = findViewById(R.id.turn_alarm);

        spinnerHour.setAdapter(new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item, getListNumber(24)));

        spinnerMinute.setAdapter(new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item, getListNumber(60)));

        Intent intent = new Intent(this, AlarmReceiver.class);

        boolean isAlarmOn = (PendingIntent.getBroadcast
                (this, AlarmReceiver.NOTIFICATION_ID, intent,
                        PendingIntent.FLAG_NO_CREATE) != null);

        button.setChecked(isAlarmOn);

        button.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                int hour = getListNumber(24).get(spinnerHour.getSelectedItemPosition());
                int minutes = getListNumber(60).get(spinnerMinute.getSelectedItemPosition());

                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                intent.putExtra(INTENT_DATA, "It's " + hour + ": " + minutes);
                pendingIntent = PendingIntent.getBroadcast
                        (this, AlarmReceiver.NOTIFICATION_ID, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);


                Log.i("Result_date", "It's " + hour + ": " + minutes);
                configAlarmManager(getTimeMillis(hour, minutes));
            } else {
                if (alarmManager != null){
                    alarmManager.cancel(pendingIntent);
                }

                Log.i("Result_date", "cancelled");
            }
        });


    }

    private void configAlarmManager(long timeMillis) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMillis,pendingIntent);
    }

    private List<Integer> getListNumber(int max) {
        List<Integer> list = new ArrayList<>();
        for (int x = 0; x < max; x++) {
            list.add(x);
        }
        return list;
    }


    private long getTimeMillis(int hour, int minute) {
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);



        calendar.add(Calendar.MINUTE, minute - currentMinute );
        calendar.add(Calendar.HOUR_OF_DAY, hour - currentHour );

        return calendar.getTimeInMillis();
    }


    private void createNotificationChannel(){
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel(AlarmReceiver.NOTIFICATION_CHANNEL_ID,
                            "WakeUp Alarm", NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.BLUE);

            manager.createNotificationChannel(channel);
        }
    }

}