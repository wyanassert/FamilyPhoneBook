package com.example.administrator.phonebook;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by wyan on 16/4/30.
 */
public class CallLogCellModel {
    String name;
    String phoneNumber;
    Date callDate;
    boolean isCallIn;
    boolean isHangUp;

    public CallLogCellModel() {
        this.name = "wyan";
        this.phoneNumber = "13343450215";
        this.callDate = new Date(2015 - 1900, 10, 29, 15, 4, 15);
        this.isCallIn = true;
        this.isHangUp = false;
    }

    public CallLogCellModel(int i) {
        this.name = "wyan" + Integer.toString(i);
        this.phoneNumber = "13343450215";
        this.callDate = new Date(2015 - 1900, 10, 29, 15, 4, 15);
        this.isCallIn = true;
        this.isHangUp = false;
    }

    public CallLogCellModel(String name, String phoneNumber, Date callDate, boolean isCallIn, boolean isHangUp)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callDate = callDate;
        this.isCallIn = isCallIn;
        this.isHangUp = isHangUp;
    }
}
