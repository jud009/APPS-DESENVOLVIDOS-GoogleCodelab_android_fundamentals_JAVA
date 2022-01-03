package com.example.scorekeeperapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mScore1 = 0, mScore2 = 0;
    private TextView mTextScore1, mTextScore2;

    private static final String TEXT_SCORE_1 = "mTextScore1";
    private static final String TEXT_SCORE_2 = "mTextScore2";
    private static final String THEME_MODE = "themeMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextScore1 = findViewById(R.id.textScore_1);
        mTextScore2 = findViewById(R.id.textScore_2);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTextScore1.setText(String.valueOf(savedInstanceState.getInt(TEXT_SCORE_1)));
        mTextScore2.setText(String.valueOf(savedInstanceState.getInt(TEXT_SCORE_2)));
        AppCompatDelegate.setDefaultNightMode(savedInstanceState.getInt(THEME_MODE));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TEXT_SCORE_1, mScore1);
        outState.putInt(TEXT_SCORE_2, mScore2);
        outState.putInt(THEME_MODE, AppCompatDelegate.getDefaultNightMode());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.night_mode) {
            //
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate
                        .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }

        return true;
    }

    public void onIncreaseScore(View view) {
        switch (view.getId()) {
            case R.id.button_plus:
                mScore1++;
                mTextScore1.setText(String.valueOf(mScore1));
                break;
            case R.id.button_plus_2:
                mScore2++;
                mTextScore2.setText(String.valueOf(mScore2));
                break;
        }
    }

    public void onDecreaseScore(View view) {
        switch (view.getId()) {
            case R.id.button_minus:
                mScore1--;
                mTextScore1.setText(String.valueOf(mScore1));
                break;
            case R.id.button_minus_2:
                mScore2--;
                mTextScore2.setText(String.valueOf(mScore2));
                break;
        }
    }

}
