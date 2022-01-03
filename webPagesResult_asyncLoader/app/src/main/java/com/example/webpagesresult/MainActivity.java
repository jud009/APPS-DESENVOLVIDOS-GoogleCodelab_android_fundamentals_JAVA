package com.example.webpagesresult;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class MainActivity extends AppCompatActivity implements
        PopupMenu.OnMenuItemClickListener, LoaderManager.LoaderCallbacks<String> {

    public static final String TAG = "mainActivityTag";
    public static final String QUERY_BUNDLE = "queryBundle";
    public static final int LOADER_ID = 1;

    private TextView text_http, text_result;
    private EditText webSite_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportLoaderManager().getLoader(LOADER_ID) != null) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }

        text_http = findViewById(R.id.http_type);
        webSite_input = findViewById(R.id.website_input);
        text_result = findViewById(R.id.text_result);


        text_http.setOnClickListener(this::configPopUpMenu);
    }

    public void onButtonFetch(View view) {

        String mUrl = webSite_input.getText().toString();

        if (mUrl.isEmpty()) {
            text_result.setText(R.string.empty_field);
            return;
        }

        if (!AndroidUtils.checkNetworkState(getApplicationContext())){
            text_result.setText(R.string.network_error);
            return;
        }

        String mQuery = text_http.getText().toString() + "" + mUrl;
        AndroidUtils.hideKeyBoard(view, getApplicationContext());

        Bundle bundle = new Bundle();
        bundle.putString(QUERY_BUNDLE, mQuery);
        getSupportLoaderManager().restartLoader(LOADER_ID, bundle, this);
    }

    private void configPopUpMenu(View view) {
        PopupMenu popUp = new PopupMenu(this, view);
        popUp.inflate(R.menu.context_menu);
        popUp.setOnMenuItemClickListener(this);
        popUp.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String text = item.getTitle().toString();
        text_http.setText(text);
        return true;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        String mQuery = "";

        if (args != null) {
            mQuery = args.getString(QUERY_BUNDLE);
        }

        return new GetWebsiteData(this, mQuery);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data == null) {
            text_result.setText(R.string.error_msg);
        } else {
            text_result.setText(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}