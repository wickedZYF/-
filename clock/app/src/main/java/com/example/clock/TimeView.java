package com.example.clock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;


public class TimeView extends LinearLayout {

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private TextView tvTime;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTime = (TextView)findViewById(R.id.id_tv_time);
        //tvTime.setText("Hello");

        timerHandler.sendEmptyMessage(0);

    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility ==View.VISIBLE){
            timerHandler.sendEmptyMessage(0);
        }else {
            timerHandler.removeMessages(0);
        }
    }

    //更新函数，这个函数是用来获取当前系统的时间
    private  void refreshTime(){
        Calendar c = Calendar.getInstance();
        tvTime.setText(String.format("%04d年 %d月 %d 日 %02d:%02d:%02d",
                c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1, //获取的月份是当前月份的前一个月，所以需要加1
                c.get(Calendar.DATE),c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),c.get(Calendar.SECOND)));
    }
    private Handler timerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            refreshTime();
            if(getVisibility() == View.VISIBLE){
                timerHandler.sendEmptyMessageDelayed(0,1000);   //每过1000毫秒就刷新一次
            }

        }
    };
}