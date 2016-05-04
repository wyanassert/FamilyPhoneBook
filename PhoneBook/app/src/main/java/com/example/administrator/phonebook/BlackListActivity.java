package com.example.administrator.phonebook;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class BlackListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<BlackListModel> modelArray;
    private MyAdapter listAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);

        modelArray = new ArrayList<BlackListModel>();
        for (int i = 0; i < 20; i++) {
            modelArray.add(new BlackListModel());
        }
        listView = (ListView) this.findViewById(R.id.blacklist_listView);
        listAdapter = new MyAdapter(this);
        listView.setAdapter(listAdapter);

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
                Log.e("convertView=", "null");
                switch (type) {
                    case TYPE_0:
                        convertView = inflater.inflate(R.layout.active_black_list_cell, parent, false);
                        holder0 = new ViewHolder0();
                        holder0.phoneNumber = (TextView) convertView.findViewById(R.id.cell_blacklist_phonenumber);
                        holder0.note = (TextView) convertView.findViewById(R.id.cell_blacklist_note);
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
                    holder0.fillData(modelArray.get(position));
                    break;

            }

            return convertView;
        }
    }

    class ViewHolder0 {
        TextView phoneNumber;
        TextView note;
        CheckBox checkBox;

        public void fillData(BlackListModel model)
        {
            phoneNumber.setText(model.phoneNumber);
            note.setText(model.note);
        }
    }
}
