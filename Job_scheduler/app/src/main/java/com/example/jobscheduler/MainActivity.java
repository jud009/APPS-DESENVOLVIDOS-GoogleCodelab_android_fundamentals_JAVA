package com.example.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int NETWORK_ANY_ID = R.id.network_any;
    public static final int NETWORK_WIFI_ID = R.id.network_wifi;
    public static final int JOB_ID = 0;

    private JobScheduler jobScheduler;

    private RadioGroup networkOptions;
    private Switch mDeviceIdle, mDeviceCharging;
    private SeekBar mSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkOptions = findViewById(R.id.network_options_group);
        mDeviceCharging = findViewById(R.id.device_charging);
        mDeviceIdle = findViewById(R.id.devide_idle);
        mSeekBar = findViewById(R.id.my_seekBar);

        TextView seekBarProgress = findViewById(R.id.seekBar_progress);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    String txt = progress + " s";
                    seekBarProgress.setText(txt);
                }else {
                    seekBarProgress.setText(R.string.not_set);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public int getNetworkType() {
        int selectNetworkOption = JobInfo.NETWORK_TYPE_NONE;

        switch (networkOptions.getCheckedRadioButtonId()) {
            case NETWORK_ANY_ID:
                selectNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;

            case NETWORK_WIFI_ID:
                selectNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        return selectNetworkOption;
    }

    public void scheduleJob(View view) {
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // associate jobInfo with JobService
        ComponentName componentName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());

        int networkType = getNetworkType();
        int seekBarProgress = mSeekBar.getProgress();
        boolean seekBarSet = seekBarProgress > 0;
        boolean constraintSet = networkType != JobInfo.NETWORK_TYPE_NONE ||
                mDeviceIdle.isChecked() || mDeviceCharging.isChecked() || seekBarSet;

        if (constraintSet) {

            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName)
                    .setRequiredNetworkType(networkType)
                    .setRequiresCharging(mDeviceCharging.isChecked())
                    .setRequiresDeviceIdle(mDeviceIdle.isChecked());

            if (seekBarSet){
                builder.setOverrideDeadline(seekBarProgress*1000);// limit date
            }


            JobInfo jobInfo = builder.build();
            jobScheduler.schedule(jobInfo);

            displayToast("Job Scheduled it'll run when the configs met");
        } else {
            displayToast("Please choose at least one constraint");
        }

    }

    public void cancelJob(View view) {
        if (jobScheduler != null) {
            jobScheduler.cancelAll();
            jobScheduler = null;
            displayToast("Jobs canceled");
        }
    }

    private void displayToast(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }
}