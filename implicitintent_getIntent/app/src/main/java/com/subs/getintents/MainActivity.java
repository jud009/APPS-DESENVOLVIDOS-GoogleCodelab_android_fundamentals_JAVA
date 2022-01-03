package com.subs.getintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView,mTextMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textUri);
        mTextMsg = findViewById(R.id.textMsg);
        if(getIntent() != null)
            configIntent();
    }

    private void configIntent() {

        try{
            Intent intent = getIntent();
            Uri uri = intent.getData();
            if(uri != null) {
                onSetTextUri(uri.toString());
            }
            String name = intent.getStringExtra(Intent.EXTRA_TEXT);
            onSetText(name);
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.toast_msgError),Toast.LENGTH_SHORT).show();
        }
    }

    private void onSetText(String second) {
        if(second != null) {
            mTextMsg.setText(second);
        }
    }

    private void onSetTextUri(String txt) {
        mTextMsg.setText(txt);
    }
}
