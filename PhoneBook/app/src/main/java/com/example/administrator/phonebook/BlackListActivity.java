package com.example.administrator.phonebook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;

public class BlackListActivity extends AppCompatActivity {
    private ListView listView;
    private Button cancelButton;
    private Button deleteButton;
    private CheckBox allSelectCheckBox;
    private ArrayList<BlackListModel> modelArray;
    private MyAdapter listAdapter;
    boolean isEditMode;
    static HashMap<Integer, Boolean> isSelected;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.scale_in, R.anim.alpha_out);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_black_list);

        modelArray = new ArrayList<BlackListModel>();
        for (int i = 0; i < 20; i++) {
            modelArray.add(new BlackListModel(i));
        }
        isSelected = new HashMap<>();
        resetIsSelect(false);
        listView = (ListView) this.findViewById(R.id.blacklist_listView);
        listAdapter = new MyAdapter(this);
        listView.setAdapter(listAdapter);
        isEditMode = false;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Long Press", "did Click");
                if(!isEditMode)
                    transIntoEditMode();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Click", "did Click");
                ViewHolder0 holder = (ViewHolder0) view.getTag();
                holder.checkBox.toggle();
            }
        });

        cancelButton = (Button) this.findViewById(R.id.blacklist_cancel_button);
        deleteButton = (Button) this.findViewById(R.id.blacklist_delete_button);
        allSelectCheckBox = (CheckBox) this.findViewById(R.id.blacklist_allselect_check);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIsSelect(false);
                transOutOfEditMode();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = modelArray.size() - 1; i >= 0; i --)
                {
                    if(isSelected.get(i))
                    {
                        modelArray.remove(i);
                    }
                }
                resetIsSelect(false);
                transOutOfEditMode();
            }
        });

        allSelectCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                "BlackList Page", // TODO: Define a title for the content shown.
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


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
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

    private void transIntoEditMode()
    {
        isEditMode = true;
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.blacklist_top_layout);
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = 60 * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        layout.getLayoutParams().height = (int) px;
        listAdapter.notifyDataSetChanged();
    }

    private void transOutOfEditMode()
    {
        isEditMode = false;
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.blacklist_top_layout);
        layout.getLayoutParams().height = 0;
        listAdapter.notifyDataSetChanged();
    }

    private void resetIsSelect(boolean boolValue)
    {
        for(int i = 0; i < modelArray.size(); i++)
            isSelected.put(i, boolValue);
    }

    class MyAdapter extends BaseAdapter {
        Context mContext;
        LinearLayout linearLayout = null;
        LayoutInflater inflater;
        final int VIEW_TYPE = 1;
        final int TYPE_0 = 0;

        public MyAdapter(Context context) {
            mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        public int getCount() {
            return modelArray.size();
        }

        public int getItemViewType(int position) {
            return TYPE_0;
        }

        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        public Object getItem(int arg0) {
            return modelArray.get(arg0);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder0 holder0 = null;


            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case TYPE_0:
                        convertView = inflater.inflate(R.layout.active_black_list_cell, parent, false);
                        holder0 = new ViewHolder0();
                        holder0.phoneNumber = (TextView) convertView.findViewById(R.id.cell_blacklist_phonenumber);
                        holder0.note = (TextView) convertView.findViewById(R.id.cell_blacklist_note);
                        holder0.checkBox = (CheckBox) convertView.findViewById(R.id.cell_blacklist_check);
                        convertView.setTag(holder0);
                        break;

                }
            } else {
                switch (type) {
                    case TYPE_0:
                        holder0 = (ViewHolder0) convertView.getTag();
                        break;

                }
            }

            switch (type) {

                case TYPE_0:
                    holder0.checkBox.setOnCheckedChangeListener(null);
                    holder0.fillData(modelArray.get(position), position);
                    break;

            }

            return convertView;
        }
    }

    class ViewHolder0 {
        TextView phoneNumber;
        TextView note;
        CheckBox checkBox;

        public void fillData(BlackListModel model, int position)
        {
            phoneNumber.setText(model.phoneNumber);
            note.setText(model.note);
            if(isEditMode)
            {
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(isSelected.get(position));
                Log.e(Integer.toString(position), Boolean.toString(isSelected.get(position)));
                checkBox.setOnCheckedChangeListener(new lvCheckBoxListener(this, position));
            }
            else
            {
                checkBox.setVisibility(View.INVISIBLE);
            }

        }
    }

    class lvCheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        ViewHolder0 holder;
        int position;

        lvCheckBoxListener(ViewHolder0 holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.e("CheckState", Boolean.toString(isChecked));
            isSelected.put(this.position, isChecked);
        }
    }

}
