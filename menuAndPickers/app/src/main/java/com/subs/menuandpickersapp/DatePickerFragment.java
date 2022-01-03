package com.subs.menuandpickersapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface DateProcess{
        void processDateResult(int year, int month, int day);
    }

    private final DateProcess dateProcess;

    public DatePickerFragment(DateProcess dateProcess) {
        this.dateProcess = dateProcess;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        dateProcess.processDateResult(year,month,day);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }
}