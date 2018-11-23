package com.example.dell_parangat.datetimepicker;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dell_parangat.datetimepicker.databinding.ActivityMainBinding;
import com.rajkaranrk.date.callback.DateTimeCallback;
import com.rajkaranrk.date.dialog.DatePickerDialog;
import com.rajkaranrk.date.dialog.DateTimePickerDialog;
import com.rajkaranrk.date.dialog.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,DateTimeCallback {

    private ActivityMainBinding mBinding;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        setListener();

    }

    private void setListener(){
        mBinding.dateDialogBtn.setOnClickListener(this);
        mBinding.dateTimeDialogBtn.setOnClickListener(this);
        mBinding.timeDialogBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.date_dialog_btn:
                openDatePickerDialog();
                break;
            case R.id.date_time_dialog_btn:
                openDateTimePickerDialog();
                break;
            case R.id.time_dialog_btn:
                openTimePickerDialog();
                break;
        }
    }

    private void openTimePickerDialog(){
        TimePickerDialog dialog = new TimePickerDialog();
        dialog.registerTimePicker(this);
        dialog.show(mFragmentManager,"Dialog");
    }

    private void openDatePickerDialog(){
        DatePickerDialog dialog = new DatePickerDialog();
        dialog.registerDialog(this);
        dialog.show(mFragmentManager,"DatePickerDialog");
    }


    private void openDateTimePickerDialog(){
        DateTimePickerDialog dialog = new DateTimePickerDialog();
        dialog.show(mFragmentManager,"DateTimePickerDialog");
    }


    @Override
    public void onReceiveDateTime(Calendar calendar) {
        mBinding.dateTxt.setText(getDateStr(calendar));
    }

    @Override
    public void onReceiveTime(Calendar calendar) {
        mBinding.dateTxt.setText(getDateStr(calendar));
    }

    @Override
    public void onReceiveDate(Calendar calendar) {
        mBinding.dateTxt.setText(getDateStr(calendar));
    }


    private String getDateStr(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        dateFormat.setCalendar(calendar);
        return dateFormat.format(calendar.getTime());
    }

}
