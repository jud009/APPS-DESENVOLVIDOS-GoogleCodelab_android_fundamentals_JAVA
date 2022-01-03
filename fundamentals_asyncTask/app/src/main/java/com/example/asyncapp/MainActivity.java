package com.example.asyncapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    public static final String TEXT_VIEW_BACKUP = "textViewBackup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        MaterialButton mButton = findViewById(R.id.button_1);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new SimpleAsyncTask(mTextView).execute();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_VIEW_BACKUP, mTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String mText = savedInstanceState.getString(TEXT_VIEW_BACKUP);

        if (mText != null) {
            mTextView.setText(mText);
        }
    }
}
