package com.example.administrator.phonebook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.phonebook.R;

public class MultipleItemsList extends Activity {

    ListView listView;
    MyAdapter listAdapter;
    ArrayList<String> listString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.call_log_contact);
            listView = (ListView)this.findViewById(R.id.listView);
            listString = new ArrayList<String>();
            for(int i = 0 ; i < 10 ; i++)
            {
                listString.add(Integer.toString(i));
            }
            listAdapter = new MyAdapter(this);
            listView.setAdapter(listAdapter);
        }

        class MyAdapter extends BaseAdapter{
            Context mContext;
            LinearLayout linearLayout = null;
            LayoutInflater inflater;
            TextView tex;
            final int VIEW_TYPE = 2;
            final int TYPE_1 = 0;
            final int TYPE_2 = 1;

            public MyAdapter(Context context) {
                // TODO Auto-generated constructor stub
                mContext = context;
                inflater = LayoutInflater.from(mContext);
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return listString.size();
            }

        //每个convert view都会调用此方法，获得当前所需要的view样式
            // @Override
            public int getItemViewType(int position) {
                // TODO Auto-generated method stub
                if(position == 0)
                    return TYPE_1;
                else
                    return TYPE_2;
            }

            @Override
            public int getViewTypeCount() {
            // TODO Auto-generated method stub
                return 2;
            }
            @Override
            public Object getItem(int arg0) {
            // TODO Auto-generated method stub
                return listString.get(arg0);
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                viewHolder1 holder1 = null;
                viewHolder2 holder2 = null;
                int type = getItemViewType(position);

                //无convertView，需要new出各个控件
                if(convertView == null)
                {
                    Log.e("convertView = ", " NULL");

                    //按当前所需的样式，确定new的布局
                    switch(type)
                    {
                        case TYPE_1:
                            convertView = inflater.inflate(R.layout.call_log_cell, parent, false);
                            holder1 = new viewHolder1();
                            holder1.textView = (TextView)convertView.findViewById(R.id.info1);
                            Log.e("convertView = ", "NULL TYPE_1");
                            convertView.setTag(holder1);
                            break;
                        case TYPE_2:
                            convertView = inflater.inflate(R.layout.all_log_cell_contact, parent, false);
                            holder2 = new viewHolder2();
                            holder2.textView = (TextView)convertView.findViewById(R.id.info1);
                            Log.e("convertView = ", "NULL TYPE_2");
                            convertView.setTag(holder2);
                            break;
                    }
                }else{
                //有convertView，按样式，取得不用的布局
                        switch(type)
                        {
                            case TYPE_1:
                            holder1 = (viewHolder1) convertView.getTag();
                                Log.e("convertView !!!!!!= ", "NULL TYPE_1");
                                break;
                            case TYPE_2:
                                holder2 = (viewHolder2) convertView.getTag();
                                Log.e("convertView !!!!!!= ", "NULL TYPE_2");
                                break;

                        }
                }

                    //设置资源
                    switch(type)
                    {
                        case TYPE_1:
    //                      holder1.textView.setText(Integer.toString(position));
                            break;
                        case TYPE_2:
//                          holder2.textView.setText(Integer.toString(position));
                            break;

                    }

                return convertView;
            }
        }

        class viewHolder1 {
        TextView textView;
    }
    class viewHolder2 {
        TextView textView;
    }
}