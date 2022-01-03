package com.subs.menuandpickersapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface TimeProcess{
        void processTimeResult(int hours, int minutes);
    }

    private final TimeProcess timeProcess;

    public TimePickerFragment(TimeProcess timeProcess) {
        this.timeProcess = timeProcess;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        timeProcess.processTimeResult(hours,minutes);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getContext(),this,hours,minutes,true);
    }
}
