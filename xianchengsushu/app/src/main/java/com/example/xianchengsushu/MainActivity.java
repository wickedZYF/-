package com.example.xianchengsushu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 EditText num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                String s = num.getText().toString().trim();
                double b = Double.valueOf(s);
                boolean c = true;
                if (b==1){
                    c = false;
                }
                for( int i = 2; i< b; i++)
                {
                    if(b % i ==0)
                    {
                        c = false;
                        break;
                    }
                }
                if( c == true)
                {
                    Toast.makeText(MainActivity.this, "是素数", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(MainActivity.this, "不是素数", Toast.LENGTH_LONG).show();
                }
            }
        };
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();
            }
        });
    }
}

