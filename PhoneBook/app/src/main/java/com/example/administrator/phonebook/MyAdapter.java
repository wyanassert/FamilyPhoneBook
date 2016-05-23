package com.example.administrator.phonebook;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.phonebook.People;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MyAdapter extends BaseAdapter {
    public class ViewHolder {
        public LinearLayout mLayoutHeader;
        public TextView mHeader;
        public ImageView mItemImage;
        public TextView mItemName;
        public TextView mItemPhone;
    }
    private LayoutInflater mInflater;
    private com.example.administrator.phonebook.MySectionIndexer mIndexer;
    private ArrayList<People> mPeopleList;

    public MyAdapter(Context context, MySectionIndexer sectionIndexer, ArrayList<People> peopleList) {
        this.mIndexer = sectionIndexer;
        this.mPeopleList = peopleList;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mPeopleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.mLayoutHeader = (LinearLayout)convertView.findViewById(R.id.layout_header);
            holder.mHeader = (TextView)convertView.findViewById(R.id.tv_header);
            holder.mItemImage = (ImageView)convertView.findViewById(R.id.item_image);
            holder.mItemName = (TextView)convertView.findViewById(R.id.item_name);
            holder.mItemPhone = (TextView)convertView.findViewById(R.id.item_phonenum);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        int section = mIndexer.getSectionForPosition(position);
        if (mIndexer.getPositionForSection(section) == position) {
            holder.mLayoutHeader.setVisibility(View.VISIBLE);
            holder.mHeader.setText(mIndexer.getSection(section));
        } else {
            holder.mLayoutHeader.setVisibility(View.GONE);
        }
        if (mPeopleList != null) {
            if(null == mPeopleList.get(position).getmPeopleImage())
            {
                holder.mItemImage.setImageResource(R.drawable.image_contact_default);
            }
            else
            {
                holder.mItemImage.setImageBitmap(mPeopleList.get(position).getmPeopleImage());
            }
            holder.mItemName.setText(mPeopleList.get(position).getPeopleName());
            holder.mItemPhone.setText(mPeopleList.get(position).getPeoplePhoneNumber());
        }
        return convertView;
    }
}
