package com.subs.usernavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(getApplicationContext(), "closed", Toast.LENGTH_SHORT).show();
        finish();
        return super.onSupportNavigateUp();
    }
}