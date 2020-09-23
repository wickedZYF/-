package com.example.xianchengsushu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 EditText num;
Button button;
TextView jieguo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = findViewById(R.id.num);
         jieguo = findViewById(R.id.jieguo);
        final Handler handler = new Handler(){
                @Override
            public void handleMessage(Message msg) {
                jieguo.setText((String)msg.obj);
            }
        };

        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                String s = num.getText().toString();
                double b = Double.valueOf(s);
                boolean c = true;
                Message msg= new Message();
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
                   msg.obj="是素数";

                }
                else
                {
                    msg.obj="不是素数";
                }
                handler.sendMessage(msg);
            }
        };
         button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();
            }
        });
    }
}

