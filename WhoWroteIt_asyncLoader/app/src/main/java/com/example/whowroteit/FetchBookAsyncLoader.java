package com.example.whowroteit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class FetchBookAsyncLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    public FetchBookAsyncLoader(@NonNull Context context, String q) {
        super(context);
        this.mQueryString = q;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }
}
