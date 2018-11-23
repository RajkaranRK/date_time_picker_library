package com.rajkaranrk.date.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.rajkaranrk.date.R;
import com.rajkaranrk.date.callback.DateTimeCallback;

import java.util.Calendar;

public final class TimePickerDialog extends AppCompatDialogFragment{

    private Calendar mCalendar = Calendar.getInstance();
    private TimePicker timePicker;
    private DateTimeCallback mCallback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time_picker,null);
        initializeTimePicker(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.ok,listener)
                .setNegativeButton(R.string.cancel,listener);
        return builder.show();
    }

    public void registerTimePicker(DateTimeCallback callback){
        mCallback = callback;
    }


    public void setTime(Calendar calendar){
        this.mCalendar = calendar;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(mCalendar.get(Calendar.MINUTE));
        }else{
            timePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        }
    }


    private void initializeTimePicker(View view){
        timePicker = view.findViewById(R.id.timepicker);
        timePicker.setOnTimeChangedListener(timeListener);
    }


    private TimePicker.OnTimeChangedListener timeListener =  new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            mCalendar.set(Calendar.MINUTE,minute);
        }
    };



    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1){
                //ok click
                mCallback.onReceiveTime(mCalendar);
                dialog.dismiss();
            }
            else if (which == -2){
                //cancel click
                dialog.dismiss();
            }
        }
    };
}