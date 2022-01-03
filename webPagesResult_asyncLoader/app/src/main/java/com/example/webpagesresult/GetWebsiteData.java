package com.example.webpagesresult;

import android.content.Context;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class GetWebsiteData extends AsyncTaskLoader<String> {

    private final String webSiteUrl;

    public GetWebsiteData(@NonNull Context context, String webSiteUrl) {
        super(context);
        this.webSiteUrl = webSiteUrl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getWebSiteData(webSiteUrl);
    }
}
