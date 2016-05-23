package com.example.administrator.phonebook;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCallLogActivity extends AppCompatActivity {

    private ListView listView;
    private CheckBox allSelectedCheckBox;
    private Button cancelButton;
    private Button deleteButton;
    private MyAdapter listAdapter;
    private ContactModel contact;
    private ArrayList<CallLogCellModel> listString;
    boolean isEditMode;
    private static HashMap<Integer, Boolean> isSelected;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_log_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) this.findViewById(R.id.calllog_listView);
        allSelectedCheckBox = (CheckBox)this.findViewById(R.id.calllog_allselect_check);
        cancelButton = (Button) this.findViewById(R.id.calllog_cancel_button);
        deleteButton = (Button) this.findViewById(R.id.calllog_delete_button);
        listString = new ArrayList<CallLogCellModel>();
        isEditMode = false;
        isSelected = new HashMap<Integer, Boolean>();

        for (int i = 0; i < 20; i++) {
            listString.add(new CallLogCellModel(i));
        }
        contact = new ContactModel();

        resetIsSelect(false);
        listAdapter = new MyAdapter(this);
        listView.setAdapter(listAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return false;
                if(!isEditMode)
                    transIntoEditMode();

                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView:", "Click:position:" + Integer.toString(position));
                if (0 == position) {
                    if(isEditMode)
                        return;
                    else
                        jumpToContactEdit(contact);
                    return;
                }
                if (!isEditMode)
                    return;
                ViewHolder1 holder = (ViewHolder1) view.getTag();
                holder.checkBox.toggle();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Cancel", "Clicked");
                resetIsSelect(false);
                transOutOfEditMode();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = listString.size() - 1; i >= 0; i --)
                {
                    if(isSelected.get(i  + (isEditMode ? 1 : 0)))
                    {
                        listString.remove(i);
                    }
                }
                resetIsSelect(false);
                transOutOfEditMode();
            }
        });
        allSelectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                resetIsSelect(isChecked);
                listAdapter.notifyDataSetChanged();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AllCallLog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.phonebook/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AllCallLog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.phonebook/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void jumpToContactEdit(ContactModel model) {
        Intent intent = new Intent(this, ContactEditActivity.class);
        if(model.bit != null)
        {
            Bitmap b = model.bit;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 50, bs);
            model.photoBytes = bs.toByteArray();
            model.bit = null;
        }
        else
            model.photoBytes = null;
        intent.putExtra("EXTRASTRING", model);
        startActivity(intent);
    }

    private void transIntoEditMode()
    {
        isEditMode = true;
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.calllog_top_layout);
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = 60 * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        layout.getLayoutParams().height = (int) px;
        listAdapter.notifyDataSetChanged();
    }

    private void transOutOfEditMode()
    {
        isEditMode = false;
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.calllog_top_layout);
        layout.getLayoutParams().height = 0;
        listAdapter.notifyDataSetChanged();
    }

    private void resetIsSelect(boolean boolValue)
    {
        for(int i = 0; i < listString.size(); i++)
            isSelected.put(new Integer(i + 1), new Boolean(boolValue));
    }

    class MyAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;

        final int VIEW_TYPE = 2;
        final int TYPE_0 = 0;
        final int TYPE_1 = 1;

        public MyAdapter(Context context) {
            mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        public int getCount() {
            return listString.size() + (isEditMode ? 0 : 1);
        }

        public int getItemViewType(int position) {
            if (position == 0 && !isEditMode)
                return TYPE_0;
            else
                return TYPE_1;
        }

        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        public Object getItem(int arg0) {
            return listString.get(arg0);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder0 holder0 = null;
            ViewHolder1 holder1 = null;

            int type = getItemViewType(position);
            if (convertView == null) {
                Log.e("convertView=", "null");
                switch (type) {
                    case TYPE_0:
                        convertView = inflater.inflate(R.layout.call_log_cell_contact, parent, false);
                        holder0 = new ViewHolder0();
                        holder0.phoneNumber = (TextView) convertView.findViewById(R.id.cell_contact_phonenumber);
                        holder0.imageView = (ImageView) convertView.findViewById(R.id.cell_contact_image);
                        holder0.name = (TextView) convertView.findViewById(R.id.cell_contact_name);
                        holder0.address = (TextView) convertView.findViewById(R.id.cell_contact_address);
                        holder0.email = (TextView) convertView.findViewById(R.id.cell_contact_email);
                        holder0.weather = (TextView) convertView.findViewById(R.id.cell_contact_weather);
                        holder0.note = (TextView) convertView.findViewById(R.id.cell_contact_note);
                        holder0.callButton = (ImageButton) convertView.findViewById(R.id.cell_contact_call_button);
                        holder0.smsButton = (ImageButton) convertView.findViewById(R.id.cell_contact_sms_button);
                        convertView.setTag(holder0);
                        break;
                    case TYPE_1:
                        convertView = inflater.inflate(R.layout.call_log_cell, parent, false);
                        holder1 = new ViewHolder1();
                        holder1.callLength = (TextView) convertView.findViewById(R.id.cell_calllog_calllength);
                        holder1.imageView = (ImageView) convertView.findViewById(R.id.cell_calllog_image);
                        holder1.time = (TextView) convertView.findViewById(R.id.cell_calllog_time);
                        holder1.checkBox = (CheckBox) convertView.findViewById(R.id.cell_calllog_check);
                        Log.e("convertView = ", "NULL TYPE_2");
                        convertView.setTag(holder1);
                        break;
                }
            } else {
                switch (type) {
                    case TYPE_0:
                        holder0 = (ViewHolder0) convertView.getTag();
                        break;
                    case TYPE_1:
                        holder1 = (ViewHolder1) convertView.getTag();
                        break;
                }
            }

            switch (type) {

                case TYPE_0:
                    holder0.fillData(contact);
                    holder0.onButtonAction();
                    break;
                case TYPE_1:
                    holder1.fillData(listString.get(position - (isEditMode ? 0 : 1)));
                    holder1.onCheckBoxToggle(position);
                    break;
            }

            return convertView;
        }
    }

    class ViewHolder0 {
        TextView phoneNumber;
        ImageView imageView;
        TextView name;
        TextView address;
        TextView email;
        TextView weather;
        TextView note;
        ImageButton callButton;
        ImageButton smsButton;

        public void fillData(ContactModel model) {
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

        public void onButtonAction() {
            this.callButton.setOnClickListener(new lvButtonListener(this));
            this.smsButton.setOnClickListener(new lvButtonListener(this));
        }
    }

    class ViewHolder1 {
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

        public void onCheckBoxToggle(int position) {
            this.checkBox.setOnCheckedChangeListener(new lvCheckBoxListener(this, position));
            this.checkBox.setChecked(isSelected.get(position + (isEditMode ? 1 : 0)));
//            Log.e("Boolean", Boolean.toString(tmpBoolean));
        }
    }

    class lvButtonListener implements View.OnClickListener {
        ViewHolder0 holder;

        lvButtonListener(ViewHolder0 holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            int vid = v.getId();
            if (vid == holder.callButton.getId()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contact.phonenumber));
                startActivity(intent);
            } else if (vid == holder.smsButton.getId()) {
                Intent tent = new Intent();
                tent.setAction(Intent.ACTION_SENDTO);
                tent.setData(Uri.parse("smsto:" + contact.phonenumber));
                startActivity(tent);
            }
        }
    }

    class lvCheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        ViewHolder1 holder;
        int position;

        lvCheckBoxListener(ViewHolder1 holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.e("CheckState", Boolean.toString(isChecked));
            isSelected.put(this.position +  (isEditMode ? 1 : 0), isChecked);
        }
    }
}
