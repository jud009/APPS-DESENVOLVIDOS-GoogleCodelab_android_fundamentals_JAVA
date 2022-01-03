package com.example.android.materialme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_DATA = "detailData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Sport sport = (Sport) getIntent().getSerializableExtra(DETAIL_DATA);

        ImageView imageView = findViewById(R.id.sports_image_detail);
        TextView newsText = findViewById(R.id.sportsTitle_detail);
        TextView info = findViewById(R.id.newsInfo_detail);

        String mText = getString(R.string.lorem_ipsum);
        info.setText(increaseText(mText));

        if (sport != null){
            Glide.with(this).load(sport.getImageResource()).into(imageView);
            newsText.setText(sport.getTitle());
        }

    }


    private String increaseText(String text){
        String newText = text + " ";
        for(int x=0; x<5;x++){
            newText+=newText;
        }
        return newText;
    }
}