package com.example.roomwordssample.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomwordssample.BuildConfig;
import com.example.roomwordssample.R;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = BuildConfig.APPLICATION_ID+".REPLY";

    private EditText wordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        wordInput = findViewById(R.id.word_input);
    }

    public void onSave(View view) {
        String typedWord = wordInput.getText().toString();
        Intent intent = new Intent();

        if (!typedWord.isEmpty()) {
            intent.putExtra(EXTRA_REPLY, typedWord);
            setResult(RESULT_OK, intent);
        } else {
            displayToast(getString(R.string.empty_not_saved));
            setResult(RESULT_CANCELED, intent);
        }

        finish();
    }

    private void displayToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}