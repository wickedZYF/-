package com.example.clock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.Calendar;


public class AlarmView extends LinearLayout {

    private static final String KEY_ALARM_LIST = "alarmlist";
    private Button btnAddAlarm;
    private ListView lvAlarmList;
    private AlarmManager alarmManager;
    private ArrayAdapter<AlarmData> adapter;

    public AlarmView(Context context) {
        super(context);
        init();
    }

    public AlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlarmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
    }

    private static class AlarmData {

        public AlarmData(long time) {
            this.time = time;

            date = Calendar.getInstance();
            date.setTimeInMillis(time);

            timeLabel = String.format("%d月%d日 %d:%d",
                    date.get(Calendar.MONTH) + 1,
                    date.get(Calendar.DAY_OF_MONTH),
                    date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE));
        }

        private long time = 0;//闹钟响起的时间
        private String timeLabel = "";
        private Calendar date;

        public long getTime() {
            return time;
        }

        public String getTimeLabel() {
            return timeLabel;
        }

        @Override
        public String toString() {
            return getTimeLabel();
        }

        private int getId(){
            return (int) (getTime() / 1000 / 60);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        btnAddAlarm = (Button) findViewById(R.id.id_btn_addAlarm);
        lvAlarmList = (ListView) findViewById(R.id.id_lv_alarm);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        lvAlarmList.setAdapter(adapter);
        readSavedAlarmList();//读取闹钟数据

        btnAddAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlarm();
            }
        });
        lvAlarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//长按删除闹钟
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(getContext())
                        .setTitle("操作选项")
                        .setItems(new CharSequence[]{"删除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        deleteAlarm(position);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });
    }

    /**
     * 删除闹钟
     */
    private void deleteAlarm(int position) {
        AlarmData ad = adapter.getItem(position);
        adapter.remove(ad);
        saveAlarmList();

        alarmManager.cancel(PendingIntent.getBroadcast(getContext(), ad.getId(), new Intent(getContext(), AlarmReceiver.class), 0));
    }

    /**
     * 添加闹钟
     */
    private void addAlarm() {
        Calendar c = Calendar.getInstance();

        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                calendar.set(Calendar.SECOND,0);//秒数清零
                calendar.set(Calendar.MILLISECOND, 0);//毫秒清零

                Calendar currentTime = Calendar.getInstance();
                if (calendar.getTimeInMillis() <= currentTime.getTimeInMillis()) {//时间小于当前
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + 24 * 60 * 60 * 1000L);//后推24小时
                }

                AlarmData ad = new AlarmData(calendar.getTimeInMillis());
                adapter.add(ad);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        ad.getTime(),
                        5 * 60 * 1000L,
                        PendingIntent.getBroadcast(getContext(), ad.getId(), new Intent(getContext(), AlarmReceiver.class), 0));
                saveAlarmList();//保存闹钟数据
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    /**
     * 保存闹钟数据
     */
    private void saveAlarmList() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adapter.getCount(); i++) {
            sb.append(adapter.getItem(i).getTime()).append(",");
        }
        if (sb.length() > 1) {
            String content = sb.toString().substring(0, sb.length() - 1);//去掉最后面的逗号
            editor.putString(KEY_ALARM_LIST, content);
        }
        editor.apply();
    }

    /**
     * 读取闹钟数据
     */
    private void readSavedAlarmList() {
        SharedPreferences sp = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);
        String content = sp.getString(KEY_ALARM_LIST, null);
        if (content != null) {
            String[] timeStrings = content.split(",");
            for (String string : timeStrings
            ) {
                adapter.add(new AlarmData(Long.parseLong(string)));
            }
        }
    }
}