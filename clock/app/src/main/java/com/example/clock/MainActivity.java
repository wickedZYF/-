package com.example.clock;


import android.os.Bundle;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TabHost tabHost;
    private StopWatchView stopWatchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();//初始化tabhost

        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.id_tab_time));
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.id_tab_alarm));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("计时器").setContent(R.id.id_tab_timer));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("秒表").setContent(R.id.id_tab_stop_watch));

        stopWatchView = (StopWatchView) findViewById(R.id.id_tab_stop_watch);
    }

    @Override
    protected void onDestroy() {

        stopWatchView.onDestroy();
        super.onDestroy();

    }
}
