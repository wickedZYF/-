package com.example.callphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonCall = (Button) findViewById(R.id.call);
        buttonCall.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Button button = (Button) v;
            switch(button.getId()){
                case R.id.call:
                    intent.setAction(intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:123456789"));
                    startActivity(intent);
                    break;
                 }
        }
    };
}