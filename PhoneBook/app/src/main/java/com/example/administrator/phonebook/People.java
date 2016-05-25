package com.example.administrator.phonebook;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/5/23.
 */
public class People {
    private Bitmap mPeopleImage;
    private String mPeopleName;
    private String mPeoplePhoneNumber;
    private String email;
    private String note;
    public People(Bitmap peopleImage, String peopleName, String peoplePhoneNumber) {
        this.mPeopleImage = peopleImage;
        this.mPeopleName = peopleName;
        this.mPeoplePhoneNumber = peoplePhoneNumber;
    }
    public People(Bitmap peopleImage, String peopleName, String peoplePhoneNumber, String email, String note) {
        this.mPeopleImage = peopleImage;
        this.mPeopleName = peopleName;
        this.mPeoplePhoneNumber = peoplePhoneNumber;
        this.email = email;
        this.note = note;
    }
    public Bitmap getmPeopleImage() {
        return mPeopleImage;
    }
     public String getPeopleName() {
        return mPeopleName;
    }
    public String getPeoplePhoneNumber() {
        return mPeoplePhoneNumber;
    }
    public String getPeopleEmail() {
        return email;
    }
    public String getPeopleNote() {
        return note;
    }

}
