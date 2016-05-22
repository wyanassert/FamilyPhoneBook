package com.example.administrator.phonebook;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by wyan on 16/5/22.
 */
public class ObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    private SwipeMenuListView callinglogListView;
    private ContentResolver contentResolver;

    private int cellType;

    AppAdapter mAdapter;
    boolean isCallingLogInEditMode;
    Button callCancelButton;
    Button callDeleteButton;
    CheckBox callAllSelectCheckBox;
    private ArrayList<CallLogCellModel> listCalllinglog;
    private ArrayList<ContactModel> listContact;

    HashMap<Integer, Boolean> callSelectArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listContact = new ArrayList<>();
        contentResolver = getContext().getContentResolver();
        //data = new ArrayList<String>();
        listContact = GetContactInfo.getcontactinfo(contentResolver);

        Bundle args = getArguments();
        int type = args.getInt(ARG_OBJECT);
        cellType = type;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.fragment_calling, container, false);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RelativeLayout layout = (RelativeLayout) getView().findViewById(R.id.main_calllog_top_layout);
        layout.setVisibility(RelativeLayout.GONE);

        callSelectArray =  new HashMap<>();
        if(0 == cellType)
        {
            listCalllinglog = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                listCalllinglog.add(new CallLogCellModel(i));
            }
            for(int i = 0; i < listCalllinglog.size(); i++)
                callSelectArray.put(i, false);
        }
        else
        if(1 == cellType)
        {
//            listContact = new ArrayList<>();
//            for(int i = 0; i < 10; i++) {
//                listContact.add(new ContactModel());
//            }
            for(int i = 0; i  < listContact.size(); i++)
            {
                callSelectArray.put(i, false);
            }

        }

        //Generate list View from ArrayList
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem callItem = new SwipeMenuItem(getActivity());
                callItem.setBackground(new ColorDrawable(Color.rgb(160, 102, 182)));
                callItem.setWidth(dp2px(90));
                callItem.setIcon(R.drawable.image_call);
                menu.addMenuItem(callItem);

                SwipeMenuItem smsItem = new SwipeMenuItem(getActivity());
                smsItem.setBackground(new ColorDrawable(Color.rgb(128, 175, 255)));
                smsItem.setWidth(dp2px(90));
                smsItem.setIcon(R.drawable.image_sms);
                menu.addMenuItem(smsItem);
            }

            private int dp2px(int dp) {
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                        getResources().getDisplayMetrics());
            }
        };
        callinglogListView = (SwipeMenuListView) getView().findViewById(R.id.main_calllog_listView);

        callinglogListView.setMenuCreator(creator);
        mAdapter = new AppAdapter(getActivity());
        isCallingLogInEditMode = false;
        callinglogListView.setAdapter(mAdapter);
        callinglogListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String number;
                if(0 == cellType)
                    number = listCalllinglog.get(position).phoneNumber;
                else if(1 == cellType)
                    number = listContact.get(position).phonenumber;
                else
                    number = "";
                switch (index) {
                    case 0:
                        Intent tent = new Intent();
                        tent.setAction(Intent.ACTION_SENDTO);
                        tent.setData(Uri.parse("smsto:" + number));
                        startActivity(tent);
                        break;
                    case 1:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + number));
                        startActivity(intent);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        callinglogListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Long Prress", "Did Click");
                transIntoEditMode();
                return true;
            }
        });

        callinglogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Click", "Did Click");
                if(0 == cellType)
                    jumpToCallLog();
                else if(1 == cellType)
                    jumpToContactEdit(listContact.get(position));

            }
        });

        callCancelButton = (Button) getView().findViewById(R.id.main_calllog_cancel_button);
        callCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transOutOfEditMode();
            }
        });

        callDeleteButton = (Button) getView().findViewById(R.id.main_calllog_delete_button);
        callDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void jumpToCallLog() {
        Intent intent = new Intent(getContext(), AllCallLogActivity.class);
//        String message = "test";
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void jumpToContactEdit(ContactModel model) {
        Intent intent = new Intent(getContext(), ContactEditActivity.class);

        intent.putExtra("EXTRASTRING", model);
        startActivity(intent);
    }

    private void transIntoEditMode() {
        isCallingLogInEditMode = true;
        RelativeLayout layout = (RelativeLayout) getView().findViewById(R.id.main_calllog_top_layout);
        layout.setVisibility(RelativeLayout.VISIBLE);
        mAdapter.notifyDataSetChanged();
    }

    private void transOutOfEditMode() {
        isCallingLogInEditMode = false;
        RelativeLayout layout = (RelativeLayout) getView().findViewById(R.id.main_calllog_top_layout);
        layout.setVisibility(RelativeLayout.GONE);
        mAdapter.notifyDataSetChanged();
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
            if(0 == cellType)
                return listCalllinglog.size();
            else if (1 == cellType)
                return listContact.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            if(0 == cellType)
                return listCalllinglog.get(position);
            else
                return listContact.get(position);

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
            ViewHolder1 holder1 = null;

            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case 0:
                        if(0 == cellType)
                        {
                            convertView = inflater.inflate(R.layout.call_log_cell, parent, false);
                            holder0 = new ViewHolder0();
                            holder0.callLength = (TextView) convertView.findViewById(R.id.cell_calllog_calllength);
                            holder0.imageView = (ImageView) convertView.findViewById(R.id.cell_calllog_image);
                            holder0.time = (TextView) convertView.findViewById(R.id.cell_calllog_time);
                            holder0.checkBox = (CheckBox) convertView.findViewById(R.id.cell_calllog_check);
                            Log.e("convertView = ", "NULL TYPE_0");
                            convertView.setTag(holder0);
                        }
                        else if(1 == cellType)
                        {
                            convertView = inflater.inflate(R.layout.contact_cell, parent, false);
                            holder1 = new ViewHolder1();
                            holder1.name = (TextView) convertView.findViewById(R.id.contact_cell_name);
                            holder1.imageView = (ImageView) convertView.findViewById(R.id.contact_cell_image);
                            holder1.phoneNumber = (TextView) convertView.findViewById(R.id.contact_cell_phonenumber);
                            holder1.checkBox = (CheckBox) convertView.findViewById(R.id.contact_cell_check);
                            Log.e("convertView = ", "NULL TYPE_1");
                            convertView.setTag(holder1);
                        }
                        break;

                }
            } else {
                switch (type) {
                    case 0:
                        if(0 == cellType)
                            holder0 = (ViewHolder0) convertView.getTag();
                        else if(1 == cellType)
                            holder1 = (ViewHolder1) convertView.getTag();
                        break;

                }
            }

            switch (type) {

                case 0:
                    if(0 == cellType)
                        holder0.fillData(listCalllinglog.get(position), isCallingLogInEditMode);
                    else
                        holder1.fillData(listContact.get(position), isCallingLogInEditMode);
                    break;

            }

            return convertView;
        }
    }

    private class ViewHolder0 {
        TextView callLength;
        ImageView imageView;
        TextView time;
        CheckBox checkBox;

        public void fillData(CallLogCellModel model, boolean isEditMode) {
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
            if (!isEditMode) {
                this.imageView.setVisibility(View.VISIBLE);
                this.checkBox.setVisibility(View.GONE);
                if (!model.isHangUp && model.isCallIn)
                    this.imageView.setImageResource(R.drawable.image_callin_success);
                else if (model.isHangUp && model.isCallIn)
                    this.imageView.setImageResource(R.drawable.image_callin_fail);
                else if (!model.isHangUp && !model.isCallIn)
                    this.imageView.setImageResource(R.drawable.image_callout_success);
                else
                    this.imageView.setImageResource(R.drawable.image_callout_fail);
            } else {
                this.imageView.setVisibility(View.GONE);
                this.checkBox.setVisibility(View.VISIBLE);
            }
        }
    }

    private class ViewHolder1 {
        TextView name;
        ImageView imageView;
        TextView phoneNumber;
        CheckBox checkBox;

        public void fillData(ContactModel model, boolean isEditMode) {
            name.setText(model.name);
            phoneNumber.setText(model.phonenumber);


            if (!isEditMode) {
                this.imageView.setVisibility(View.VISIBLE);
                this.checkBox.setVisibility(View.GONE);
                this.imageView.setImageResource(R.drawable.image_contact_default);
            } else {
                this.imageView.setVisibility(View.GONE);
                this.checkBox.setVisibility(View.VISIBLE);
            }
        }

    }
}