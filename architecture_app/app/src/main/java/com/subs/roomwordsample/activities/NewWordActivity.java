package com.subs.roomwordsample.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.subs.roomwordsample.R;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.subs.roomwordsample.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        EditText mEditText = findViewById(R.id.edit_word);
        Button mButton = findViewById(R.id.button_save);


        mButton.setOnClickListener(click -> {
            Intent intent = new Intent();

            if(TextUtils.isEmpty(mEditText.getText().toString().trim())){
                setResult(RESULT_CANCELED,intent);
            }else{
                String mWord = mEditText.getText().toString().trim();
                intent.putExtra(EXTRA_REPLY,mWord);
                setResult(RESULT_OK,intent);
            }

            finish();
        });
    }
}
