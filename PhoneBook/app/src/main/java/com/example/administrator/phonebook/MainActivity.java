package com.example.administrator.phonebook;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CallingFragment mCalling;
    private PeopleFragment mPeople;

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                switch (tab.getPosition())
                {
                    case 0:
                     if (mCalling == null)
                     {
                         mCalling = new CallingFragment();
                     }
                    transaction.replace(R.id.id_frame, mCalling);
                        break;
                    case 1:
                    if (mPeople == null)
                    {
                        mPeople = new PeopleFragment();
                    }
                    transaction.replace(R.id.id_frame, mPeople);
                        break;
                }
                transaction.commit();
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        bar.addTab(bar.newTab().setText("通话记录").setTabListener(tabListener));
        bar.addTab(bar.newTab().setText("联系人").setTabListener(tabListener));

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
//            case R.id.action_edit:
//                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_add:
//                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_blacklist:
//                jumpToBlackList();
//                Toast.makeText(this, "blacklist", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_export:
//                Toast.makeText(this, "export", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_about:
//                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
//                return true;

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


