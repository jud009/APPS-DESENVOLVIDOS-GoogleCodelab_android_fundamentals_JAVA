package com.example.whowroteit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String QUERY_STRING = "queryString";
    private static final String LOGCAT = "logcatMainActivity";


    private EditText mBookInput;
    private TextView mTitleText, mAuthorText;
    private MaterialButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0,null, this);
        }

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);
        mButton = findViewById(R.id.searchButton);
    }

    public void onSearchBooks(View view) {

        String mQueryString = mBookInput.getText().toString();

        if (mQueryString.trim().isEmpty()) {
            mTitleText.setText(R.string.error_message);
            return;
        }

        if (AndroidUtils.isNetworkConnection(getApplicationContext())) {
//            new FetchBook(mTitleText, mAuthorText, mButton).execute(mQueryString);
            fetchBook(mQueryString);
            AndroidUtils.hideKeyboard(view, getApplicationContext());
        } else {
            mTitleText.setText(R.string.network_error);
        }

    }

    private void fetchBook(String q) {
        Bundle bundle = new Bundle();
        bundle.putString(QUERY_STRING, q);
        getSupportLoaderManager().restartLoader(0, bundle, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        String q = "";

        if (args != null) {
            q = args.getString(QUERY_STRING);
        }
        Log.i(LOGCAT, q);
        return new FetchBookAsyncLoader(this, q);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            Log.i(LOGCAT, data);
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            Log.d("TAG1", Integer.toString(jsonArray.length()));

            int i = 0;
            String title = null;
            String authors = null;

            while (i < jsonArray.length() && (title == null && authors == null)) {

                JSONObject book = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getJSONArray("authors").getString(0);

                    if (!title.isEmpty() && !authors.isEmpty()) {
                        mTitleText.setText(title);
                        mAuthorText.setText(authors);
                    } else {
                        mTitleText.setText(R.string.no_results);
                        mAuthorText.setText("");
                    }
                } catch (Exception e) {
                    mTitleText.setText(R.string.no_results);
                    mAuthorText.setText("");
                    e.printStackTrace();
                }

                i++;
            }


        } catch (JSONException e) {
            Log.i("TAG", e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
