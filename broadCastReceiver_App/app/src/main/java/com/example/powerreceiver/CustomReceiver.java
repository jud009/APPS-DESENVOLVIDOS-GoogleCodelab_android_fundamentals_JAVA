package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null){
            String toastMessage = "unknown intent action";

            switch (intent.getAction()){
                case Intent.ACTION_SCREEN_ON:
                    toastMessage = "Screen on";
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    toastMessage = "Screen off";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast Received";
                    break;
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }



    }
}