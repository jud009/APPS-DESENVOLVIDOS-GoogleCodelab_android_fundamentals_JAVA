package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;

public class AndroidUtils {

    public static void hideKeyboard(View view, Context context) {

        WeakReference<View> mView = new WeakReference<>(view);
        WeakReference<Context> mContext = new WeakReference<>(context);

        InputMethodManager inputManager =
                (InputMethodManager) mContext.get().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(mView.get().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isNetworkConnection(Context context) {
        WeakReference<Context> mContext = new WeakReference<>(context);

        ConnectivityManager connectManager = (ConnectivityManager)
                mContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectManager != null) {

            NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();

            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }

        return false;
    }
}
