package com.example.administrator.phonebook;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private Button mBtnCalling;
    private Button mBtnPeople;

    private CallingFragment mCalling;
    private PeopleFragment mPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mBtnCalling = (Button)findViewById(R.id.btn_calling);
        mBtnPeople = (Button)findViewById(R.id.btn_people);
        mBtnCalling.setOnClickListener(this);
        mBtnPeople.setOnClickListener(this);

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        mCalling = new CallingFragment();
        transaction.replace(R.id.id_frame, mCalling);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.btn_calling:
                if (mCalling == null)
                    mCalling = new CallingFragment();
                transaction.replace(R.id.id_frame, mCalling);
                break;
            case R.id.btn_people:
                if (mPeople == null)
                    mPeople = new PeopleFragment();
                transaction.replace(R.id.id_frame, mPeople);
                break;
        }
        transaction.commit();
    }
}
