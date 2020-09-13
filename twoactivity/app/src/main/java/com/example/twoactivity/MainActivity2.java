package com.example.twoactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    String name;
    int age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        age=intent.getIntExtra("age", 20);
        Toast.makeText(this,name+age,Toast.LENGTH_LONG).show();
    }

    public void onButtonClick2(View view) {
        Intent intent= new Intent(MainActivity2.this,MainActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("age",age);
        startActivity(intent);
    }
}
