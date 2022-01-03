package com.example.choosecolor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String COUNT_VALUE = "count_value";
    public static final String COLOR_VALUE = "color_value";


    private int selectedColor, count = 0;

    private SharedPreferences mPrefs;
    private String sharedFile = BuildConfig.APPLICATION_ID + ".sharedPrefs";

    private Button btBackground;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);

        btBackground = findViewById(R.id.bt_background);
        selectedColor = (ContextCompat.getColor(this,
                R.color.gray_edited));

        mPrefs = getSharedPreferences(sharedFile, MODE_PRIVATE);


        count = mPrefs.getInt(COUNT_VALUE, 0);
        selectedColor = mPrefs.getInt(COLOR_VALUE, selectedColor);

        setBackGroundColor(selectedColor);
        setButtonText(String.valueOf(count));
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(COUNT_VALUE, count);
        editor.putInt(COLOR_VALUE, selectedColor);
        editor.apply();
        //never use commit because it's not async
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_black:
                selectedColor = (ContextCompat.getColor(this,
                        android.R.color.black));
                setBackGroundColor(selectedColor);
                break;
            case R.id.bt_red:
                selectedColor = (ContextCompat.getColor(this,
                        android.R.color.holo_red_dark));
                setBackGroundColor(selectedColor);
                break;
            case R.id.bt_blue:
                selectedColor = (ContextCompat.getColor(this,
                        android.R.color.holo_blue_dark));
                setBackGroundColor(selectedColor);
                break;
            case R.id.bt_green:
                selectedColor = (ContextCompat.getColor(this,
                        android.R.color.holo_green_dark));
                setBackGroundColor(selectedColor);
                break;
            case R.id.bt_count:
                count++;
                setButtonText(String.valueOf(count));
                break;

            case R.id.bt_reset:
                count = 0;
                setButtonText(String.valueOf(count));
                selectedColor = (ContextCompat.getColor(this,
                        R.color.gray_edited));
                setBackGroundColor(selectedColor);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.clear(); //delete everything
                editor.apply();

                break;
        }

    }

    private void setBackGroundColor(int color) {
        btBackground.setBackgroundColor(color);
    }

    private void setButtonText(String msg) {
        btBackground.setText(msg);
    }

}