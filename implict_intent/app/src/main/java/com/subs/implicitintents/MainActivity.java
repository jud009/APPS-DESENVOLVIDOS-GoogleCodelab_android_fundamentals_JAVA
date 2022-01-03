package com.subs.implicitintents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mWebSiteEditText, mLocationEditText, mShareMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebSiteEditText = findViewById(R.id.webSite_editText);
        mLocationEditText = findViewById(R.id.location_editText);
        mShareMessageEditText = findViewById(R.id.shareText_editText);

    }
    private String editTextToString(EditText editText){
        return editText.getText().toString();
    }

    public void openWebSite(View view){
        String url = editTextToString(mWebSiteEditText);
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,webPage);
        if(intent.resolveActivity(getPackageManager()) != null){
            //it must have at least one activity to handle this in my cellphone using filters
            startActivity(intent);
        }else {
            Log.i("ImplicitIntents", "Can't handle this intent!");
        }
    }

    public void openLocation(View view){
        String location = editTextToString(mLocationEditText);
        Uri address = Uri.parse("geo:0,0?q="+location);
        Intent intent = new Intent(Intent.ACTION_VIEW,address);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else {
            Log.i("ImplicitIntents", "Can't handle this intent! OnLocation");
        }
    }
    public void openCamera(View view){
        String action = MediaStore.ACTION_IMAGE_CAPTURE;
        Intent intent = new Intent(action);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }
    }

    public void shareText(View view){
        String txt = editTextToString(mShareMessageEditText);
        String mineType = "text/plain";
        //oldWay(txt);
        ShareCompat.IntentBuilder.from(this)
                .setType(mineType)
                .setChooserTitle("Sharing this text with")
                .setText(txt)
                .startChooser();
    }
    private void oldWay(String txt){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,txt);
        startActivity(intent);
    }
}
