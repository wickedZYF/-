package com.example.lianxiren;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view) {
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        while(cursor.moveToNext()){
            String msg;
            String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            msg="id:"+id;
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            msg=msg+" name:"+name;
            Cursor phoneNumbers=resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"="+id,null,null);
            while(phoneNumbers.moveToNext()){
                String phoneNumber=phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                msg=msg+" phone:"+phoneNumber;
            }
            System.out.println(msg);

        }
    }
    }


