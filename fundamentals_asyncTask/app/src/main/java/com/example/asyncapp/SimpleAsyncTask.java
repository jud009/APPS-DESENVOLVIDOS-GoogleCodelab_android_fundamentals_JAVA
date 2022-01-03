package com.example.asyncapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> weakTextView;

    public SimpleAsyncTask(TextView textView) {
        this.weakTextView = new WeakReference<>(textView);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        weakTextView.get().setText(Integer.toString(values[0]));
    }

    @Override
    protected String doInBackground(Void... voids) {

        Random randomNumber = new Random();

        int[] number = {10,15,20,25,30};
        int n = randomNumber.nextInt(number.length);
        int timeMilliseconds = number[n] * 1000;

        try {
            for (int x = 1;x <= (timeMilliseconds/1000); x++){
                publishProgress(x);
                Thread.sleep(1000L);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "The work took " + timeMilliseconds + " milliseconds to finish.";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        weakTextView.get().setText(s);
    }
}
