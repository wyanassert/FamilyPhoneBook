package com.example.administrator.phonebook;

/**
 * Created by wyan on 16/4/30.
 */
public class ContactModel {
    String name;
    String phonenumber;
    String fromCity;
    String weather;
    String address;
    String email;
    String note;

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
            String fromCity,
            String weather,
            String address,
            String email,
            String note)
    {
        this.name = name;
        this.phonenumber = phonenumber;
        this.weather = fromCity;
        this.address = address;
        this.email = email;
        this.note = note;
    }
}
