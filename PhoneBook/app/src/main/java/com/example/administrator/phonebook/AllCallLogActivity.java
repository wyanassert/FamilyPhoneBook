package com.example.administrator.phonebook;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCallLogActivity extends Activity {

    private ListView listView;
    MyAdapter listAdapter;
    ArrayList <String> listString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    class MyAdapter extends BaseAdapter
    {
        Context mContext;
        LinearLayout linearLayout = null;
        LayoutInflater inflater;
        TextView text;

        final int VIEW_TYPE = 2;
        final int TYPE_0 = 0;
        final int TYPE_1 = 1;

        public MyAdapter(Context context)
        {
            mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        public int getCount()
        {
            return listString.size();
        }

        public int getItemViewType(int position)
        {
            if(position == 0)
                return TYPE_0;
            else
                return TYPE_1;
        }

        public int getViewTypeCount()
        {
            return VIEW_TYPE;
        }

        public Object getItem(int arg0)
        {
            return listString.get(arg0);
        }

        public long getItemId(int position)
        {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder1 holder1 = null;
            ViewHolder2 holder2 = null;

            int type = getItemViewType(position);
            if(convertView == null)
            {
                Log.e("convertView=", "null");
                switch(type)
                {
                    case TYPE_0:
                        convertView = inflater.inflate(R.layout.call_log_cell_contact, parent, false);
                        holder1 = new ViewHolder1();
                        holder1.textView = (TextView)convertView.findViewById(R.id.info0);
                        holder1.imageView = (ImageView)convertView.findViewById(R.id.img0);
                        holder1.title = (TextView)convertView.findViewById(R.id.title0);
                        Log.e("convertView = ", "NULL TYPE_1");
                        convertView.setTag(holder1);
                        break;
                    case TYPE_1:
                        convertView = inflater.inflate(R.layout.call_log_cell, parent, false);
                        holder2 = new ViewHolder2();
                        holder2.textView = (TextView)convertView.findViewById(R.id.info1);
                        holder2.imageView = (ImageView)convertView.findViewById(R.id.img1);
                        holder2.title = (TextView)convertView.findViewById(R.id.title1);
                        Log.e("convertView = ", "NULL TYPE_2");
                        convertView.setTag(holder2);
                        break;
                }
            }
            else
            {
                switch(type)
                {
                    case TYPE_0:
                        holder1 = (ViewHolder1) convertView.getTag();
                        Log.e("convertView !!!!!!= ", "NULL TYPE_1");
                        break;
                    case TYPE_1:
                        holder2 = (ViewHolder2) convertView.getTag();
                        Log.e("convertView !!!!!!= ", "NULL TYPE_2");
                        break;
                }
            }

            switch (type)
            {
                case TYPE_0:
                    holder1.textView.setText("contact");
                    holder1.imageView.setImageResource(R.drawable.image_contact_default);
                    holder1.title.setText("contect_name");
                    break;
                case TYPE_1:
                    holder2.textView.setText("callLog");
                    holder2.imageView.setImageResource(R.drawable.image_call);
                    holder2.title.setText("call_log_title");
                    break;
            }

            return convertView;
        }
    }

    class ViewHolder1
    {
        TextView textView;
        ImageView imageView;
        TextView title;
    }
    class ViewHolder2
    {
        TextView textView;
        ImageView imageView;
        TextView title;
    }
}
