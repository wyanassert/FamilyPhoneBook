package com.example.administrator.phonebook;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by wyan on 16/4/30.
 */
public class CallLogCellModel { //name and phone in not necessory , may not be used
    String name;
    String phoneNumber;
    Calendar callDate;
    int callLengthSecond;
    boolean isCallIn;
    boolean isHangUp;

    public CallLogCellModel(int i) {
        this.name = "wyan" + Integer.toString(i);
        this.phoneNumber = "13343450215";
        this.callDate = Calendar.getInstance();
        this.callDate.set(2015, 9, 25, 13, 5, 28);
        this.callLengthSecond = 4762;
        this.isCallIn = true;
        this.isHangUp = false;
    }

    public CallLogCellModel(String name, String phoneNumber, Calendar callDate, int callLengthSecond, boolean isCallIn, boolean isHangUp)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callDate = callDate;
        this.callLengthSecond = callLengthSecond;
        this.isCallIn = isCallIn;
        this.isHangUp = isHangUp;
    }
}
