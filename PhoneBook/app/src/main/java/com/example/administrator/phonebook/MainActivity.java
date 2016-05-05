package com.example.administrator.phonebook;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnCalling;
    private Button mBtnPeople;

    private CallingFragment mCalling;
    private PeopleFragment mPeople;

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnCalling = (Button)findViewById(R.id.btn_calling);
        mBtnPeople = (Button)findViewById(R.id.btn_people);
        mBtnCalling.setOnClickListener(this);
        mBtnPeople.setOnClickListener(this);

        setDefaultFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为ActionBar扩展菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_blacklist:
                Toast.makeText(this, "blacklist", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_export:
                Toast.makeText(this, "export", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
//                if (mCalling == null)
//                    mCalling = new CallingFragment();
//                transaction.replace(R.id.id_frame, mCalling);
                jumpToCallLog();
                break;
            case R.id.btn_people:
//                if (mPeople == null)
//                    mPeople = new PeopleFragment();
//                transaction.replace(R.id.id_frame, mPeople);
                jumpToBlackList();
                break;
        }
        transaction.commit();
    }

    private void jumpToCallLog() {
        Intent intent = new Intent(this, AllCallLogActivity.class);
        String message = "test";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void jumpToBlackList() {
        Intent intent = new Intent(this, BlackListActivity.class);
        String message = "test";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}


