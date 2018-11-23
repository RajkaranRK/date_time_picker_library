package com.rajkaranrk.date.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.rajkaranrk.date.callback.DateTimeCallback;
import com.rajkaranrk.date.R;

import java.util.Calendar;

public final class DateTimePickerDialog extends AppCompatDialogFragment {
    private TabLayout mTabLayout;
    private TabLayout.Tab mDateTab;
    private TabLayout.Tab mTimeTab;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Calendar mCalendar = Calendar.getInstance();
    private DateTimeCallback mCallback;
    private Calendar maxCal;
    private Calendar minCal;
    private String TAG = "DateTimePickerDialog";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_time_picker,null);
        initializeAllViews(view);
        mCallback = (DateTimeCallback) getActivity();
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Ok",listener)
                .setNegativeButton("Cancel",listener)
                .setCancelable(false)
                .create();
    }




    public void setDate(Calendar calendar){
        this.mCalendar = calendar;
    }


    public void setMaxDate(Calendar maxCal){
        this.maxCal = maxCal;
    }

    public void setMinCal(Calendar minCal){
        this.minCal = minCal;
    }


    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.e(TAG,"OnClick");
            if (which == -1){
                okClick();
            }
            else if (which == -2){
                cancelClick();
            }
        }
    };




    //initialize all views
    private void initializeAllViews(View view){
        mTabLayout = view.findViewById(R.id.date_time_tabLayout);
        mTimePicker =  view.findViewById(R.id.date_time_timepicker);
        mDatePicker = view.findViewById(R.id.date_time_datePicker);
        setTab();
        setListenerForDatePickerAndTimePicker();
    }


    //set Tab for tablayout
    private void setTab(){
        mTimeTab = mTabLayout.newTab().setText("Time");
        mDateTab = mTabLayout.newTab().setText("Date");
        mTabLayout.addTab(mTimeTab);
        mTabLayout.addTab(mDateTab);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    //show time picker
                    mTimePicker.setVisibility(View.VISIBLE);
                    mDatePicker.setVisibility(View.GONE);

                }
                else if (tab.getPosition() == 1){
                    //show date picker
                    mTimePicker.setVisibility(View.GONE);
                    mDatePicker.setVisibility(View.VISIBLE);
                }
            }



            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setListenerForDatePickerAndTimePicker(){

        //initialiy setting the time picker with the mCalendar vlaues
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setMinute(mCalendar.get(Calendar.MINUTE));
        }
        else {
            mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        }



        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mTimePicker.setHour(hourOfDay);
                    mTimePicker.setMinute(minute);
                }
                else {
                    mTimePicker.setCurrentHour(hourOfDay);
                    mTimePicker.setCurrentMinute(minute);
                }
                mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                mCalendar.set(Calendar.MINUTE,minute);

            }
        });
        if (maxCal!=null){
            mDatePicker.setMaxDate(maxCal.getTimeInMillis());
        }
        if (minCal !=null ){
            mDatePicker.setMinDate(minCal.getTimeInMillis());
        }

        mDatePicker.init(mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.DAY_OF_MONTH),mCalendar.get(Calendar.MONTH),mDateChangedListener);
        mDatePicker.updateDate(mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private DatePicker.OnDateChangedListener mDateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR,year);
            mCalendar.set(Calendar.MONTH,monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }
    };


    private void okClick(){
        Log.e(TAG,"OkClicked");
        mCallback.onReceiveDateTime(mCalendar);
        this.dismiss();
    }

    private void cancelClick(){
        Log.e(TAG,"cancelClicked");
        this.dismiss();
    }




}
