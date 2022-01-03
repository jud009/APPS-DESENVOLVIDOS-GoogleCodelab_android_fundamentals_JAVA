package com.example.jobscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {

    public static final String NOTIFICATION_CHANNEL_ID =
            "first_channel_id";

    public static final int NOTIFICATION_REQUEST_CODE_ID = 0;

    private NotificationManager nManager;


    @Override
    public boolean onStartJob(JobParameters params) {

        nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationChannelConfig();

        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        this, NOTIFICATION_REQUEST_CODE_ID,
                        new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);

        createNotification(pendingIntent);


        /*
         * all the work will be complete here. If true it'll run in a separate thread
         *  and the app will call jobFinished
         * */
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true; //If fail the job will be rescheduled not dropped
    }

    private void createNotification(PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this,
                NOTIFICATION_CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_job_running)
                .setContentTitle(getString(R.string.job_service))
                .setContentText(getString(R.string.content_text_notify))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        nManager.notify(NOTIFICATION_REQUEST_CODE_ID, builder.build());
    }


    private void notificationChannelConfig() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    getString(R.string.primary_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setLightColor(Color.YELLOW);
            channel.setDescription(getString(R.string.primary_channel_description));
            channel.enableVibration(true);
            channel.enableLights(true);

            nManager.createNotificationChannel(channel);
        }

    }

}
