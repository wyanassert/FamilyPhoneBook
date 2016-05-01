package com.example.administrator.phonebook;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCallLogActivity extends Activity {

    private ListView listView;
    private MyAdapter listAdapter;
    private ContactModel contact;
    private ArrayList <CallLogCellModel> listString;
    boolean isEditMode;
    private static HashMap<Integer, Boolean> isSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_log_contact);
        listView = (ListView)this.findViewById(R.id.listView);
        listString = new ArrayList<CallLogCellModel>();
        isEditMode = false;
        isSelected = new HashMap<Integer, Boolean>();

        for(int i = 0 ; i < 50 ; i++)
        {
            listString.add(new CallLogCellModel(i));
        }
        contact = new ContactModel();
        listAdapter = new MyAdapter(this);
        listView.setAdapter(listAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    return false;
                isEditMode = true;
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView:", "Click:position:" + Integer.toString(position));
                if(0 == position)
                {
                    // TODO: 16/5/1
                    return;
                }
                if(!isEditMode)
                    return;
                ViewHolder1 holder = (ViewHolder1)view.getTag();
                holder.checkBox.toggle();

            }
        });
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
            return listString.size() + 1;
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
            ViewHolder0 holder0 = null;
            ViewHolder1 holder1 = null;

            int type = getItemViewType(position);
            if(convertView == null)
            {
                Log.e("convertView=", "null");
                switch(type)
                {
                    case TYPE_0:
                        convertView = inflater.inflate(R.layout.call_log_cell_contact, parent, false);
                        holder0 = new ViewHolder0();
                        holder0.phoneNumber = (TextView)convertView.findViewById(R.id.cell_contact_phonenumber);
                        holder0.imageView = (ImageView)convertView.findViewById(R.id.cell_contact_image);
                        holder0.name = (TextView)convertView.findViewById(R.id.cell_contact_name);
                        holder0.address = (TextView)convertView.findViewById(R.id.cell_contact_address);
                        holder0.email = (TextView)convertView.findViewById(R.id.cell_contact_email);
                        holder0.weather = (TextView)convertView.findViewById(R.id.cell_contact_weather);
                        holder0.note = (TextView)convertView.findViewById(R.id.cell_contact_note);
                        holder0.callButton = (ImageButton)convertView.findViewById(R.id.cell_contact_call_button);
                        holder0.smsButton = (ImageButton)convertView.findViewById(R.id.cell_contact_sms_button);
                        convertView.setTag(holder0);
                        break;
                    case TYPE_1:
                        convertView = inflater.inflate(R.layout.call_log_cell, parent, false);
                        holder1 = new ViewHolder1();
                        holder1.callLength = (TextView)convertView.findViewById(R.id.cell_calllog_calllength);
                        holder1.imageView = (ImageView)convertView.findViewById(R.id.cell_calllog_image);
                        holder1.time = (TextView)convertView.findViewById(R.id.cell_calllog_time);
                        holder1.checkBox = (CheckBox)convertView.findViewById(R.id.cell_calllog_check);
                        Log.e("convertView = ", "NULL TYPE_2");
                        convertView.setTag(holder1);
                        break;
                }
            }
            else
            {
                switch(type)
                {
                    case TYPE_0:
                        holder0 = (ViewHolder0) convertView.getTag();
                        break;
                    case TYPE_1:
                        holder1 = (ViewHolder1) convertView.getTag();
                        break;
                }
            }

            switch (type)
            {

                case TYPE_0:
                    holder0.fillData(contact);
                    holder0.onButtonAction();
                    break;
                case TYPE_1:
                    holder1.fillData(listString.get(position - 1));
                    holder1.onCheckBoxToggle(position);
                    break;
            }

            return convertView;
        }
    }

    class ViewHolder0
    {
        TextView phoneNumber;
        ImageView imageView;
        TextView name;
        TextView address;
        TextView email;
        TextView weather;
        TextView note;
        ImageButton callButton;
        ImageButton smsButton;

        public void fillData(ContactModel model)
        {
            this.phoneNumber.setText(model.phonenumber);
            this.imageView.setImageResource(R.drawable.image_contact_default);
            this.name.setText(model.name);
            this.address.setText(model.address);
            this.email.setText(model.email);
            this.note.setText(model.note);
            this.weather.setText(model.weather);
            this.callButton.setImageResource(R.drawable.image_call);
            this.smsButton.setImageResource(R.drawable.image_sms);
        }

        public void onButtonAction()
        {
            this.callButton.setOnClickListener(new lvButtonListener(this));
            this.smsButton.setOnClickListener(new lvButtonListener(this));
        }
    }
    class ViewHolder1
    {
        TextView callLength;
        ImageView imageView;
        TextView time;
        CheckBox checkBox;

        public void fillData(CallLogCellModel model)
        {
            Calendar calendar = model.callDate;
            String year= String.valueOf(calendar.get(Calendar.YEAR));
            String month= String.valueOf(calendar.get(Calendar.MONTH)+1);
            String day= String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String hour = String.valueOf(calendar.get(Calendar.HOUR));
            String minite = String.valueOf(calendar.get(Calendar.MINUTE));
            String second = String.valueOf(calendar.get(Calendar.SECOND));
            this.time.setText(year + "-" + month + "-" + day + "-" +  hour + "-" + minite + "-" + second);
            String tmp = "time: ";
            if(model.callLengthSecond > 3600 * 24)
                tmp += "long long";
            else
            {
                int tmpInt, length = model.callLengthSecond;
                tmpInt = length / 3600;
                if(tmpInt > 0)
                    tmp += Integer.toString(tmpInt) + "h:";
                length %= 3600;

                tmpInt = length / 60;
                if(tmpInt > 0)
                    tmp += Integer.toString(tmpInt) + "m:";
                length %= 60;

                tmpInt = length;
                tmp += Integer.toString(tmpInt) + "s";
            }
            this.callLength.setText(tmp);
            if(!isEditMode)
            {
                this.imageView.setVisibility(View.VISIBLE);
                this.checkBox.setVisibility(View.GONE);
                if(!model.isHangUp && model.isCallIn)
                    this.imageView.setImageResource(R.drawable.image_callin_success);
                else if(model.isHangUp && model.isCallIn)
                    this.imageView.setImageResource(R.drawable.image_callin_fail);
                else if(!model.isHangUp && !model.isCallIn)
                    this.imageView.setImageResource(R.drawable.image_callout_success);
                else
                    this.imageView.setImageResource(R.drawable.image_callout_fail);
            }
            else
            {
                this.imageView.setVisibility(View.GONE);
                this.checkBox.setVisibility(View.VISIBLE);
                Log.e("CheckBox id", Integer.toString(this.checkBox.getId()));
            }
        }

        public void onCheckBoxToggle(int position)
        {
            this.checkBox.setOnCheckedChangeListener(new lvCheckBoxListener(this, position));
        }
    }

    class lvButtonListener implements View.OnClickListener
    {
        ViewHolder0 holder;

        lvButtonListener(ViewHolder0 holder)
        {
            this.holder = holder;
        }

        @Override
        public void onClick(View v)
        {
            int vid=v.getId();
            if (vid == holder.callButton.getId())
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contact.phonenumber));
                startActivity(intent);
            }
            else if(vid == holder.smsButton.getId())
            {
                Intent tent = new Intent();
                tent.setAction(Intent.ACTION_SENDTO);
                tent.setData(Uri.parse("smsto:" + contact.phonenumber));
                startActivity(tent);
            }
        }
    }

    class lvCheckBoxListener implements CompoundButton.OnCheckedChangeListener
    {
        ViewHolder1 holder;
        int position;
        lvCheckBoxListener(ViewHolder1 holder, int position)
        {
            this.holder = holder;
            this.position = position;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            Log.e("CheckState", Boolean.toString(isChecked));
            isSelected.put(this.position, isChecked);
            Log.e(Integer.toString(position), Boolean.toString(isSelected.get(position)));
        }
    }
}
