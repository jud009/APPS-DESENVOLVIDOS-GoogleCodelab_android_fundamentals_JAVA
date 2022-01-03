package com.subs.clickableimagesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.EmptyStackException;

public class OrderActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TextView mTextView = findViewById(R.id.textReceivedOrder);

        Spinner spinner = findViewById(R.id.spinner);
        configSpinner(spinner);

        editText = findViewById(R.id.phone_input);
        callFromEditText();

        Bundle mData = getIntent().getBundleExtra(MainActivity.INTENT_DATA);
        if (mData != null) {
            String mMsg = mData.getString(MainActivity.ORDER);
            mTextView.setText(mMsg);
        }
    }


    private void callFromEditText(){
        if(editText != null){
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_SEND){
                        dialNumber(editText.getText().toString());
                    }
                    return true;
                }

                private void dialNumber(String phoneNumber){
                    if(!phoneNumber.isEmpty()){
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:"+phoneNumber);
                        intent.setData(data);
                        if(intent.resolveActivity(getPackageManager()) != null){
                            startActivity(intent);
                        }else {
                            Log.i("statusDial", "dialNumber: cant handle this");
                        }
                    }
                }
            });
        }
    }

    private void configSpinner(Spinner spinner){
        if(spinner != null){

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                    (this, R.array.spinner_entries,android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //spinner s de selected
                    displayToast(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else {
            throw new EmptyStackException();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetContentProvider(this).execute();
    }

    public void onRadioButtonClicked(View view) {
        RadioButton mButton = (RadioButton) view;

        switch (view.getId()) {
            case R.id.sameday:
                if (mButton.isChecked())
                    displayToast("Same day");
                break;
            case R.id.nextDay:
                if (mButton.isChecked())
                    displayToast("Next day");
                break;
            case R.id.pickUp:
                if (mButton.isChecked())
                    displayToast("Pick up");
                break;
        }

    }

    private void displayToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private static class GetContentProvider extends AsyncTask<Void, Void, String> {

        private WeakReference<OrderActivity> mReference;

        GetContentProvider(OrderActivity activity) {
            this.mReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder sb = new StringBuilder();
            Uri url = Uri.parse("content://com.subs.onboardapp/mBoardTable");
            try (Cursor cursor = mReference.get().getContentResolver().query(url, null, null, null, null)) {
                assert cursor != null;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    sb.append("\n")
                            .append(cursor.getString(cursor.getColumnIndex("id")))
                            .append(": ")
                            .append(cursor.getString(cursor.getColumnIndex("name")));
                    cursor.moveToNext();
                }

            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("mResult", "onPostExecute: " + s);
        }
    }
}
