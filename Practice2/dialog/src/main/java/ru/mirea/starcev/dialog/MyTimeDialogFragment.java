package ru.mirea.starcev.dialog;

import android.app.AlertDialog;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment {
    Calendar dateAndTime=Calendar.getInstance();
    TextView time;
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setTime();
        }
    };

    public MyTimeDialogFragment(TextView txt)
    {
        time = txt;
    }

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        return new TimePickerDialog(getActivity(), t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true);
    }

    private void setTime() {

        time.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }

}