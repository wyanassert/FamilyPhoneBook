package com.example.administrator.phonebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about);

        TextView aboutText =(TextView) this.findViewById(R.id.about_textView);
        String authorText = "开发人员： 万延， 雷勇， 程彦文， 易晓东， 姚波，孙志伟 ";
        String sourceText = "源代码：https://github.com/wy1402099772/FamilyPhoneBook";
        aboutText.setText(authorText + "\n\n" + sourceText);
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
}
