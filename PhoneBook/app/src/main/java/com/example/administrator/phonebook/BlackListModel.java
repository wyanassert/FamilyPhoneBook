package com.example.administrator.phonebook;

import java.util.Calendar;

/**
 * Created by wyan on 16/5/3.
 */
public class BlackListModel {

    String phoneNumber;
    String note;
    Calendar addTime;
    public BlackListModel()
    {
        this.phoneNumber = "12345";
        this.note = "骚扰电话";
    }

    public BlackListModel(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}
