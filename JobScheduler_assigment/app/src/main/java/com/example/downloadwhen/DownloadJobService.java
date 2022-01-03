package com.example.downloadwhen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DownloadJobService extends JobService {

    private static final String NOTIFICATION_MANAGER_ID = "PRIMARY_CHANNEL";
    private static final int NOTIFICATION_ID = 0;

    @Override
    public boolean onStartJob(JobParameters params) {
        createNotification();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private void createNotification() {
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel(manager);

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this,
                NOTIFICATION_MANAGER_ID);

        nb.setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Performing Download")
                .setContentText("Download in Progress")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        manager.notify(NOTIFICATION_ID, nb.build());
    }

    private void createChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_MANAGER_ID,
                    "DownloadConfig", NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.MAGENTA);

            manager.createNotificationChannel(channel);
        }

    }
}
