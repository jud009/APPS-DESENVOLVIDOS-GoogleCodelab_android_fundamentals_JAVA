package com.example.reader;

import android.media.AudioAttributes;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech TTS;
    private Spinner spVoice;

    private final List<String> voices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TTS = new TextToSpeech(this, this, "com.google.android.tts");

        EditText wordInput = findViewById(R.id.word_input);
        Button btSpeak = findViewById(R.id.bt_speak);
        spVoice = findViewById(R.id.sp_voice);


        btSpeak.setOnClickListener(view -> {

            String wordTyped = wordInput.getText().toString().trim();

            if (!wordTyped.isEmpty()) {
                speakOut(wordTyped);
            } else {
                displayToast("Please fill out the input box");
            }

        });
    }

    public void onStop(View view){
        if (TTS.isSpeaking()) TTS.stop();
    }


    private void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void speakOut(String msg) {

        String voiceName = voices.get(spVoice.getSelectedItemPosition());
        setVoice(voiceName);
        TTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void setVoice(String voiceName){
        Voice voice = new Voice(voiceName,
                Locale.getDefault(), Voice.QUALITY_VERY_HIGH, Voice.LATENCY_VERY_LOW,
                false, null);

        TTS.setVoice(voice);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            TTS.setLanguage(Locale.getDefault());

            for (Voice value : TTS.getVoices()) {
                if (value.getName().contains("us") && value.getName().contains("sfg")) {
                    voices.add(value.getName());
                }
            }

            spVoice.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_dropdown_item, voices));

        } else {
            displayToast("Something went wrong");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TTS != null) {
            TTS.shutdown();
            TTS.stop();
        }
    }
}