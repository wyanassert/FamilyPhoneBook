package com.example.administrator.phonebook;
/**
 * Created by forthdim on 2016/4/11.
 */
import android.app.Activity;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import android.database.Cursor;
public class GetContactInfo {
        //private ContentResolver contentResolver;
        //private SimpleAdapter simpleAdapter;
        static Cursor cursor;
        static private  HashMap<String,String> item;
        static private ArrayList<Map<String,String>> data;
        static int idFieldIndex,id,nameFieldIndex,numCountFieldIndex,numCount;
        static String phonenumber;
        static String email;
        static int height;
        static private ArrayList<ContactModel> contactModel;

        static public ArrayList getcontactinfo(ContentResolver contentResolver){
            data = new ArrayList<Map<String,String>>();
            contactModel = new ArrayList<>();

            Uri uri = ContactsContract.Contacts.CONTENT_URI;
            cursor = contentResolver.query(uri,new String[]{ContactsContract.Contacts._ID,
                                                            ContactsContract.Contacts.HAS_PHONE_NUMBER,
                                                            ContactsContract.Contacts.DISPLAY_NAME},null,null,null);
            System.out.println("读取到"+String.valueOf(cursor.getCount())+"个联系人\n");
            System.out.println("Column"+cursor.getColumnCount()+"列\n");
            while(cursor.moveToNext()){
                idFieldIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                id = cursor.getInt(idFieldIndex);
                numCountFieldIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                numCount = cursor.getInt(numCountFieldIndex);
                nameFieldIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                //System.out.println("nameFieldIndex = "+ nameFieldIndex);
                String name = cursor.getString(nameFieldIndex);
                phonenumber = "";
                email = "";
                if(numCount > 0){
                    Cursor phonecursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"=?",
                            new String[]{Integer.toString(id)},null);
                    //ContactsContract.CommonDataKinds:  Container for definitions of common data types stored in the ContactsContract.Data table.
                    if(phonecursor.moveToFirst()){
                        phonenumber = phonecursor.getString(phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    Cursor emailcursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                            new String[]{Integer.toString(id)}, null);
                    //ContactsContract.CommonDataKinds:  Container for definitions of common data types stored in the ContactsContract.Data table.
                    if(emailcursor.moveToFirst()){
                        email = emailcursor.getString(phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                    }
                    Uri photo = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(Integer.toString(id)));
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, photo, true);
                    if(input != null){
                        Bitmap bit= BitmapFactory.decodeStream(input);
                        height = bit.getHeight();
                    }
                    else{
                        height = 0;
                    }
                }
//                item = new HashMap<String,String>();
//                item.put("name",name);
//                item.put("phonenumber",phonenumber);
//                item.put("email",email);
//                item.put("photo",Integer.toString(height));
//                data.add(item);
                if(phonenumber.startsWith("+86"))
                    phonenumber = phonenumber.substring(3);
                if(phonenumber.length() > 0)
                {
//                    Log.e("ADD", Integer.toString(contactModel.size()));
//                    Log.e(name, phonenumber);
                    ContactModel model = new ContactModel(name, phonenumber, email, "");
                    contactModel.add(model);
                }
            }
//            Iterator iterator = data.iterator();
//            while(iterator.hasNext()) {
//                //HashMap<String,> hash = iterator.next();
//                Iterator<Map.Entry<String, String>> ire = ((Map) iterator.next()).entrySet().iterator();
//                while (ire.hasNext()) {
//                    Map.Entry<String, String> entry = ire.next();
//                    String key = entry.getKey();
//                    String value = entry.getValue();
//                    System.out.println(key + "=" + value + "\n");
//                }
//            }
//            for (int i = 0; i < contactModel.size(); i++)
//                Log.e(contactModel.get(i).name, contactModel.get(i).phonenumber);
            return contactModel;
        }

        static public void selectquery(String string){

        }
}
