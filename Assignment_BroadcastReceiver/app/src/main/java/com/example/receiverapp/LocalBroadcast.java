package com.example.receiverapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class LocalBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String toastMessage = "Nothing received";


        if (intent.getAction().equals(MainActivity.ACTION_LOCAL_BROADCAST)){

            if (intent.getExtras() != null){
                Integer value = intent.getExtras().getInt(MainActivity.INTENT_DATA);
                toastMessage = "Square of random number: "+Math.pow(value, 2);
            }
        }

        Toast.makeText(context,toastMessage, Toast.LENGTH_SHORT).show();
    }
}