package com.subs.resultintenttest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textR;
    private int REQUEST_CODE = 1;
    public static String MSG_CODE = "msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textR = findViewById(R.id.text_received);
        textView.setText(getString(R.string.txt_iGotIt));

    }

    public void OnSend(View view) {
        String message = textView.getText().toString();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(MSG_CODE, message);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String value = data.getStringExtra(SecondActivity.SENDED_MESSAGE);
            textR.setVisibility(View.VISIBLE);
            textR.setText(value);
        }
    }
}
