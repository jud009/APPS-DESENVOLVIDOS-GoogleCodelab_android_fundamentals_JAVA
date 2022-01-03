package com.subs.clickableimagesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {

    private static final String DATA_KEY = "dataKey";
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
    }

    public static void open(Context context, @Nullable Bundle data) {
        Intent intent = new Intent(context, LessonActivity.class);
        if (data != null) {
            intent.putExtra(DATA_KEY, data);
        }
        context.startActivity(intent);
    }

    public void onCheckBoxClick(View view) {
        if(view instanceof CheckBox){
            CheckBox checkBox = (CheckBox) view;
            boolean isChecked = checkBox.isChecked();
            String text = checkBox.getText().toString();
            if(isChecked){
               list.add(text);
                displayToast();
            }else {
                list.remove(text);
            }
        }
    }

    private void displayToast(){
        StringBuilder sb = new StringBuilder();
        for (String value: list){
            sb.append(value).append(" ");
        }

        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();

    }

}
