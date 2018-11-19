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

public class TimePickerDialog extends AppCompatDialogFragment {

    private DateTimeCallback mCallback;
    private TimePicker mTimePicker;
    private Calendar mCalendar = Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time_picker,null);
        initializeView(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setView(view)
                .setNegativeButton("Cancel",listener )
                .setPositiveButton("Ok",listener);
        return builder.create();
    }


    public void registerCallback(DateTimeCallback callback){
        mCallback = callback;
    }




    private DialogInterface.OnClickListener listener =  new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            if (which == -1){
                //ok clicked
                mCallback.onReceiveTime(mCalendar);
            }
            else if (which == -2){
                //cancel clicked
            }
        }
    };

    private void setCalendar(Calendar calendar){
        mCalendar = calendar;
    }

    private void initializeView(View view){
        mTimePicker = view.findViewById(R.id.time_picker);
        mTimePicker.setOnTimeChangedListener(timeChangedListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setMinute(mCalendar.get(Calendar.MINUTE));
        }
        else{
            mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        }
    }

    private TimePicker.OnTimeChangedListener timeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY,hour);
            mCalendar.set(Calendar.MINUTE,minute);
        }
    };

}