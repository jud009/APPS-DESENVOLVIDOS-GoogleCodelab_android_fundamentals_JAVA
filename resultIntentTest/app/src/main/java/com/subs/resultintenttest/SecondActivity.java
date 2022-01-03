package com.subs.resultintenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String SENDED_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = findViewById(R.id.received);
        Intent intent = getIntent();
        String textReceived = intent.getStringExtra(MainActivity.MSG_CODE);
        if (textReceived != null && !textReceived.isEmpty()){
            textView.setText(textReceived);
        }
    }

    public void onRetrieve(View view){
        Intent intent = new Intent();
        intent.putExtra(SENDED_MESSAGE,"ok it's cool");
        setResult(RESULT_OK,intent);
        finish();
    }
}
