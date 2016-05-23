package com.example.administrator.phonebook;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by wyan on 16/4/30.
 */
public class ContactModel implements Serializable {
    String name;
    String phonenumber;
    String weather;
    String address;
    String email;
    String note;
    Bitmap bit;
    byte[] photoBytes;

    public ContactModel()
    {
        this.name = "wyan";
        this.phonenumber = "13343450215";
        this.weather = "sunny";
        this.address = "Wuhan";
        this.email = "1402099772@qq.com";
        this.note = "never say never";
    }

    public ContactModel(String name,
            String phonenumber,
            String email,
            String note)
    {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.note = note;
    }

    public ContactModel(String name,
                        String phonenumber,
                        String email,
                        String note,
                        Bitmap bit)
    {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.note = note;
        this.bit = bit;
    }
}
