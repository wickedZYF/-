package com.example.shard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final static String SharedPreferencesFileName="config";
    private final static String Key_UserName="UserName";
    private final static String Key_UserType="UserType";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

        preferences=getSharedPreferences(SharedPreferencesFileName,
                MODE_PRIVATE);
        editor=preferences.edit();
        Button Write=(Button)findViewById(R.id.Write);
        Button Read=(Button)findViewById(R.id.Read);



        Write.setOnClickListener(new View.OnClickListener() {
            @Override

        public void onClick(View view) {
                editor.putString(Key_UserName, "张羿凡");
                editor.putInt(Key_UserType, 2018011265);
                editor.apply();
            }
        });


        Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = preferences.getString(Key_UserName, null);

                int nUserType=preferences.getInt(Key_UserType,0);

                    Toast.makeText(MainActivity.this, "用户名:" + strUserName +"学号:"+nUserType, Toast.LENGTH_LONG).show();

            }
        });
     }
}