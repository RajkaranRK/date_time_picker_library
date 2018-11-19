package com.rajkaranrk.date.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;


import com.rajkaranrk.date.R;
import com.rajkaranrk.date.callback.DateTimeCallback;

import java.util.Calendar;

public final class DatePickerDialog  extends AppCompatDialogFragment {
    private Calendar mMaxCalendar = Calendar.getInstance();
    private Calendar mCalendar = Calendar.getInstance();
    private int request_code = -1;
    private DateTimeCallback mCallback;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_picker,null);
        initializeViews(view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("Ok",listener)
                .setNegativeButton("Cancel",listener)
                .create();
    }


    public void registerDialog(DateTimeCallback callback){
        mCallback = callback;
    }


    public void setRequest_code(int request_code){
        this.request_code = request_code;
    }




    private void initializeViews(View view){
        DatePicker datePicker = view.findViewById(R.id.date_picker);
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(Calendar.YEAR,2017);
        minCalendar.set(Calendar.DAY_OF_MONTH,1);
        minCalendar.set(Calendar.MONTH,1);
        datePicker.setMaxDate(mMaxCalendar.getTimeInMillis()); //maximum date is the current date time
        //datePicker.setMinDate(minCalendar.getTimeInMillis()); // min date is 2017/1/1
        datePicker.init(mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH),mDateChangedListener);
        datePicker.updateDate(mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
    }


    private DatePicker.OnDateChangedListener mDateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR,year);
            mCalendar.set(Calendar.MONTH,monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }
    };



    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1){
                okClick();
                dialog.dismiss();
            }else if(which == -2){
                dialog.dismiss();
            }
        }
    };


    private void okClick(){
        mCallback.onReceiveDate(mCalendar);
    }


}
