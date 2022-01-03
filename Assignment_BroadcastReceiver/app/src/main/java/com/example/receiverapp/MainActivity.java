package com.example.receiverapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final LocalBroadcast localBroadcast = new LocalBroadcast();

    public static final String ACTION_LOCAL_BROADCAST = "ACTION_LOCAL_BROADCAST";
    public static final String INTENT_DATA = "INTENT_DATA";

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = findViewById(R.id.number_received);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(localBroadcast,
                        new IntentFilter(ACTION_LOCAL_BROADCAST));
    }

    private int generateRandomNumber() {
        return new Random().nextInt(20);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadcast);
        super.onDestroy();
    }

    public void onSendBroadcast(View view) {

        int number = generateRandomNumber();

        mText.setText(String.valueOf(number));

        Intent intent = new Intent(ACTION_LOCAL_BROADCAST);
        intent.putExtra(INTENT_DATA, number);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}