package com.example.administrator.phonebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    PhoneBookFragmentPageAdapter mFragmentPageAdapter;
    CustomViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentPageAdapter = new PhoneBookFragmentPageAdapter(getSupportFragmentManager());
        mViewPager = (CustomViewPager) findViewById(R.id.pager);
        mViewPager.setPagingEnabled(false);
        mViewPager.setAdapter(mFragmentPageAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mViewPager.setCurrentItem(tab.getPosition());
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
                jumpToBlackList();
                Toast.makeText(this, "blacklist", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {

    }


    public class PhoneBookFragmentPageAdapter extends FragmentPagerAdapter {
        public PhoneBookFragmentPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ObjectFragment();
            Bundle args = new Bundle();
            args.putInt(ObjectFragment.ARG_OBJECT, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Integer.toString(position);
        }
    }

    public class ObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";
        private SwipeMenuListView callinglogListView;
        private ArrayList<CallLogCellModel> listCalllinglog;
        AppAdapter mAdapter;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            Bundle args = getArguments();
            int type = args.getInt(ARG_OBJECT);
            if(0 == type)
            {
                View rootView = inflater.inflate(R.layout.fragment_calling, container, false);

                return rootView;
            }
            else if(1 == type)
            {

            }
            View rootView = inflater.inflate(R.layout.fragment_calling, container, false);
            return rootView;
        }

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.v("ListFragment", "onActivityCreated().");
            Log.v("ListsavedInstanceState", savedInstanceState == null ? "true" : "false");

            //Generate list View from ArrayList
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    SwipeMenuItem smsItem = new SwipeMenuItem(getActivity());
                    smsItem.setBackground(new ColorDrawable(Color.rgb(128, 175, 255)));
                    smsItem.setWidth(dp2px(90));
                    smsItem.setIcon(R.drawable.image_sms);
                    menu.addMenuItem(smsItem);

                    SwipeMenuItem callItem = new SwipeMenuItem(getActivity());
                    callItem.setBackground(new ColorDrawable(Color.rgb(160, 102, 182)));
                    callItem.setWidth(dp2px(90));
                    callItem.setIcon(R.drawable.image_call);
                    menu.addMenuItem(callItem);
                }

                private int dp2px(int dp) {
                    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                            getResources().getDisplayMetrics());
                }
            };
            callinglogListView = (SwipeMenuListView)getView().findViewById(R.id.main_calllog_listView);
            listCalllinglog = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                listCalllinglog.add(new CallLogCellModel(i));
            }
            callinglogListView.setMenuCreator(creator);
            mAdapter = new AppAdapter(getActivity());
            callinglogListView.setAdapter(mAdapter);
            callinglogListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            Intent tent = new Intent();
                            tent.setAction(Intent.ACTION_SENDTO);
                            tent.setData(Uri.parse("smsto:" + "13343450215"));
                            startActivity(tent);
                            break;
                        case 1:
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + "13343450215"));
                            startActivity(intent);
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });
        }

        class AppAdapter extends BaseAdapter {
            Context mContext;
            LayoutInflater inflater;

            public AppAdapter(Context context) {
                mContext = context;
                inflater = LayoutInflater.from(mContext);
            }
            @Override
            public int getViewTypeCount() {
                // menu type count
                return 1;
            }

            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return listCalllinglog.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public int getItemViewType(int position) {
                // current menu type
                return 0;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder0 holder0 = null;


                int type = getItemViewType(position);
                if (convertView == null) {
                    switch (type) {
                        case 0:
                            convertView = inflater.inflate(R.layout.call_log_cell, parent, false);
                            holder0 = new ViewHolder0();
                            holder0.callLength = (TextView) convertView.findViewById(R.id.cell_calllog_calllength);
                            holder0.imageView = (ImageView) convertView.findViewById(R.id.cell_calllog_image);
                            holder0.time = (TextView) convertView.findViewById(R.id.cell_calllog_time);
                            holder0.checkBox = (CheckBox) convertView.findViewById(R.id.cell_calllog_check);
                            Log.e("convertView = ", "NULL TYPE_2");
                            convertView.setTag(holder0);
                            break;

                    }
                } else {
                    switch (type) {
                        case 0:
                            holder0 = (ViewHolder0) convertView.getTag();
                            break;

                    }
                }

                switch (type) {

                    case 0:
                        holder0.fillData(listCalllinglog.get(position));
                        break;

                }

                return convertView;
            }
        }
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

    private class ViewHolder0 {
        TextView callLength;
        ImageView imageView;
        TextView time;
        CheckBox checkBox;

        public void fillData(CallLogCellModel model) {
            Calendar calendar = model.callDate;
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String hour = String.valueOf(calendar.get(Calendar.HOUR));
            String minite = String.valueOf(calendar.get(Calendar.MINUTE));
            String second = String.valueOf(calendar.get(Calendar.SECOND));
            this.time.setText(year + "-" + month + "-" + day + "-" + hour + "-" + minite + "-" + second);
            String tmp = "time: ";
            if (model.callLengthSecond > 3600 * 24)
                tmp += "long long";
            else {
                int tmpInt, length = model.callLengthSecond;
                tmpInt = length / 3600;
                if (tmpInt > 0)
                    tmp += Integer.toString(tmpInt) + "h:";
                length %= 3600;

                tmpInt = length / 60;
                if (tmpInt > 0)
                    tmp += Integer.toString(tmpInt) + "m:";
                length %= 60;

                tmpInt = length;
                tmp += Integer.toString(tmpInt) + "s";
            }
            this.callLength.setText(model.name + tmp);
//            if (!isEditMode) {
//                this.imageView.setVisibility(View.VISIBLE);
//                this.checkBox.setVisibility(View.GONE);
//                if (!model.isHangUp && model.isCallIn)
//                    this.imageView.setImageResource(R.drawable.image_callin_success);
//                else if (model.isHangUp && model.isCallIn)
//                    this.imageView.setImageResource(R.drawable.image_callin_fail);
//                else if (!model.isHangUp && !model.isCallIn)
//                    this.imageView.setImageResource(R.drawable.image_callout_success);
//                else
//                    this.imageView.setImageResource(R.drawable.image_callout_fail);
//            } else {
//                this.imageView.setVisibility(View.GONE);
//                this.checkBox.setVisibility(View.VISIBLE);
//            }
        }
    }

    private class ViewHolder1 {

    }
}


