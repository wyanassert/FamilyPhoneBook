package com.example.administrator.phonebook;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactEditActivity extends AppCompatActivity {

    static String EXTRASTRING = "EXTRASTRING";
    ContactModel model;
    ImageView image;
    TextView nametext;
    TextView phoneText;
    TextView emailText;
    TextView noteText;

    Button cancelButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        model = (ContactModel) intent.getSerializableExtra(EXTRASTRING);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_contact_edit);

        image = (ImageView) this.findViewById(R.id.contact_edit_image);
        nametext = (EditText) this.findViewById(R.id.contact_edit_name);
        phoneText = (TextView) this.findViewById(R.id.contact_edit_phonenumber);
        emailText = (TextView) this.findViewById(R.id.contact_edit_email);
        noteText = (TextView) this.findViewById(R.id.contact_edit_note);

        image.setImageResource(R.drawable.image_contact_default);
        nametext.setText(model.name);
        phoneText.setText(model.phonenumber);
        emailText.setText(model.email);
        noteText.setText(model.note);

        cancelButton = (Button) findViewById(R.id.contact_edit_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
