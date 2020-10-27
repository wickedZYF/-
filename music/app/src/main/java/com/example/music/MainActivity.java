package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,frag1.OnFragmentInteractionListener {
    private FrameLayout content;
    private TextView tv1;
    private FragmentManager fm;
    private FragmentTransaction ft;
    static String  songname;
    static int  pos;
    public static ArrayList<String> arrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        arrayList.add("Flash Funk");
        arrayList.add("彼女は旅に出る");
        arrayList.add("summertime");
        arrayList.add("sweets parade");
        arrayList.add("変態");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content=(FrameLayout)findViewById(R.id.content);

        tv1=(TextView)findViewById(R.id.menu1);


        tv1.setOnClickListener(this);


        fm=getSupportFragmentManager();//若是继承FragmentActivity，fm=getFragmentManger();
        ft=fm.beginTransaction();
        ft.replace(R.id.content,new frag1());//默认情况下Fragment1
        ft.commit();
    }
    @Override
    public void onClick(View v){
        ft=fm.beginTransaction();
        switch (v.getId()){
            case R.id.menu1:
                ft.replace(R.id.content,new frag1());
                break;

            default:
                break;
        }
        ft.commit();
    }

    @Override
    public void onWordItemClick(String id) {
        songname=id;
       pos=search(songname);
    }
    static int search(String songname) {
        int a=0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (songname.equals(arrayList.get(i))) {
                 a = i;
                break;
            }

        }
        return a;
    }
}