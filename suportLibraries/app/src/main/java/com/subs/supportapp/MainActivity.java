package com.subs.supportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewHello;

    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    public static final String COLOR = "color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewHello = findViewById(R.id.textView_helloWorld);

        if(savedInstanceState != null){
            mTextViewHello.setTextColor(savedInstanceState.getInt(COLOR));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COLOR,mTextViewHello.getCurrentTextColor());
    }

    public void onClickChangeColor(View view){
        int index = new Random().nextInt(20);
        String colorName = mColorArray[index];
        /*
         * getResources() -> get all resources from my app
         * when i compile my app, it gets the xml
         files resources and converts in integer ids, so to get these idd i need to use the getIdentifier() method.
         *
         *
         */

                //resources -> identifier -> name, type, package
        int colorResource = getResources().getIdentifier(colorName,"color",getApplicationContext().getOpPackageName());

         // Get the color identifier that matches the color name.

        //sometimes we need to use the ContextCompat  because this will let us use some methods in older APIs, if I don't do that, my app will crash
        int colorRes = ContextCompat.getColor(this,colorResource); //get color from the ID colorResource in the actual context
        mTextViewHello.setTextColor(colorRes);
    }
}
