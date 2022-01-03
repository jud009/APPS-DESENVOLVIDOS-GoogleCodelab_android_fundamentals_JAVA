package com.subs.menuandpickersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OrderFragment.ActionView{

    private static final int ACTION_ORDER_ID = R.id.action_order;
    private static final int ACTION_STATUS_ID = R.id.action_status;
    private static final int ACTION_FAVORITES_ID = R.id.action_favorites;
    private static final int ACTION_CONTACT_ID = R.id.action_contact;

    public static final String TAG_DIALOG_FRAGMENT = "datePickerFragment";
    public static final String TAG_DIALOG_FRAGMENT_TIME = "tinmePickerFragment";

    private OrderFragment orderFragment;
    public static final String FLAG = "orderFragmentFlag";

    private Button mButton, pickerButton, timeButton;
    public static final String TAG = "logTag_mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(TAG, "onCreate: ACTIVITY");

        orderFragment = new OrderFragment(this);
        mButton = findViewById(R.id.alert_button);
        pickerButton = findViewById(R.id.picker_button);
        timeButton = findViewById(R.id.bt_timePicker);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    public void onClickShowAlert(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
        dialog.setMessage("Click OK to continue, or Cancel to stop:");
        dialog.setPositiveButton("Ok", (log, which) -> {
            displayToast("Pressed Ok");
        });
        dialog.setNegativeButton("Cancel", (log, which) -> {
            displayToast("Pressed Cancel");
        });
        dialog.show();
    }

    public void onClickShowTimePicker(View view){
        TimePickerFragment timePicker = new TimePickerFragment(((hours, minutes) -> {
            String result = hours + ":" + minutes;
            displayToast(result);
        }));
        timePicker.show(getSupportFragmentManager(),TAG_DIALOG_FRAGMENT_TIME);
    }

    public void onClickShowDatePicker(View view){
        DatePickerFragment dialog = new DatePickerFragment(((year, month, day) -> {
            String y = Integer.toString(year);
            String m = Integer.toString(month + 1);
            String d = Integer.toString(day);
            String finalDate = d +'/'+m+'/'+y;
            displayToast(finalDate);
        }));
        dialog.show(getSupportFragmentManager(),TAG_DIALOG_FRAGMENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    
    
    private void onItemSelected(int id) {
        switch (id) {
            case ACTION_ORDER_ID:
                if(!OrderFragment.isOpen){
                    startFragmentOrder();
                }else {
                    displayToast("there's a fragment open already");
                }

                break;
            case ACTION_STATUS_ID:
                displayToast(getString(R.string.action_status_message));
                break;
            case ACTION_FAVORITES_ID:
                displayToast(getString(R.string.action_favorites_message));
                break;
            case ACTION_CONTACT_ID:
                displayToast(getString(R.string.action_contact_message));
                break;
            default:
                displayToast(getString(R.string.error_message));
        }
    }

    private void startFragmentOrder() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(FLAG);
        transaction.replace(R.id.fragment_container, orderFragment, FLAG);
        transaction.commit();
    }


    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void doViewsInvisible() {
        mButton.setVisibility(View.INVISIBLE);
        pickerButton.setVisibility(View.INVISIBLE);
    }

    private void doViewsVisible(){
        mButton.setVisibility(View.VISIBLE);
        pickerButton.setVisibility(View.VISIBLE);
    }

    @Override
    public List<View> getView() {
        return Arrays.asList(mButton, pickerButton, timeButton);
    }

}
