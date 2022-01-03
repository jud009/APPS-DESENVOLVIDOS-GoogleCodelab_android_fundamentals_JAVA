package com.example.webpagesresult;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;

public class AndroidUtils {

    public static void hideKeyBoard(View view, Context context){

        WeakReference<View> mView = new WeakReference<>(view);
        WeakReference<Context> mContext = new WeakReference<>(context);

        InputMethodManager manager = (InputMethodManager)
                mContext.get().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (manager != null){
            manager.hideSoftInputFromWindow(mView.get().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public static boolean checkNetworkState(Context context){

        WeakReference<Context> mContext = new WeakReference<>(context);

        ConnectivityManager manager = (ConnectivityManager)
                mContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null){
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null){
                return info.isConnected();
            }
        }

        return false;
    }

}
