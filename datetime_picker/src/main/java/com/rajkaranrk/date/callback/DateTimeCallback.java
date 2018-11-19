package com.rajkaranrk.date.callback;

import java.util.Calendar;

public interface DateTimeCallback {

    public void onReceiveDateTime(Calendar calendar);

    public void onReceiveTime(Calendar calendar);

    public void onReceiveDate(Calendar calendar);
}
