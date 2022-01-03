package com.example.downloadwhen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private static final int JOB_INFO_ID = 1;
    public static final int JOB_PERIOD = 60 * 1000;


    private JobScheduler jobScheduler;

    private SwitchMaterial isIdle, isPowering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isIdle = findViewById(R.id.is_idle);
        isPowering = findViewById(R.id.is_power);
    }

    public void startDownload(View view){
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(getPackageName(),
                DownloadJobService.class.getName());

        JobInfo.Builder jb = new JobInfo.Builder(JOB_INFO_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false);

        boolean jobSet = isIdle.isChecked() && isPowering.isChecked();

        if (jobSet){
           jb.setPeriodic(JOB_PERIOD);
        }

        jobScheduler.schedule(jb.build());
        displayToast("Job Started");
    }

    public void cancelDownload(View view){
        if (jobScheduler != null){
            jobScheduler.cancelAll();
            jobScheduler = null;
            displayToast("Job canceled");
        }
    }

    private void displayToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}