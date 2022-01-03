package com.example.whowroteit;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitle, mAuthor;
    private WeakReference<MaterialButton> mButton;

    public FetchBook(TextView titleTextView, TextView authorTextView, MaterialButton button) {
        mTitle = new WeakReference<>(titleTextView);
        mAuthor = new WeakReference<>(authorTextView);
        mButton = new WeakReference<>(button);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mButton.get().setVisibility(View.INVISIBLE);
        mTitle.get().setText(R.string.loading);
        mAuthor.get().setText(null);
    }

    @Override
    protected String doInBackground(String... strings) {
        String q = strings[0];

        return NetworkUtils.getBookInfo(q);
    }

    @Override
    protected void onPostExecute(String value) {
        super.onPostExecute(value);

        mButton.get().setVisibility(View.VISIBLE);

        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            Log.d("TAG1", Integer.toString(jsonArray.length()));

            int i = 0;
            String title = null;
            String authors = null;

            while (i < jsonArray.length() && (title == null && authors == null)){

                JSONObject book = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try{
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getJSONArray("authors").getString(0);

                    if (!title.isEmpty() && !authors.isEmpty()){
                        mTitle.get().setText(title);
                        mAuthor.get().setText(authors);
                    }else{
                        mTitle.get().setText(R.string.no_results);
                        mAuthor.get().setText("");
                    }
                }
                catch (Exception e){
                    mTitle.get().setText(R.string.no_results);
                    mAuthor.get().setText("");
                    e.printStackTrace();
                }

                i++;
            }



        }catch (JSONException e){
            Log.i("TAG", e.getMessage());
        }

    }

}
