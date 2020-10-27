package com.example.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    static MediaPlayer player;
    private Timer timer;



    @Override
    public  IBinder onBind(Intent intent){
        return new MusicControl();
    }
    @Override
    public void onCreate(){
        super.onCreate();
        player=new MediaPlayer();//创建音乐播放器对象
//        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                MainActivity.pos=MainActivity.search(MainActivity.songname);
//                MainActivity.pos++;
//                MainActivity.songname=MainActivity.arrayList.get(MainActivity.pos);
//                MusicService.player.reset();
//                Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/"+"music"+MainActivity.pos);
//                MusicService.player = MediaPlayer.create(getApplicationContext(), uri);
//                Music_Activity.iv_music.setImageResource(frag1.icons[MainActivity.pos]);
//                Music_Activity.name_song.setText(MainActivity.arrayList.get(MainActivity.pos));
//                MusicService.player.start();//播放音乐
//
//            }
//        });
    }




    public void addTimer(){ //添加计时器用于设置音乐播放器中的播放进度条
        if(timer==null){
            timer=new Timer();//创建计时器对象
            TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    if (player==null) return;
                    int duration=player.getDuration();//获取歌曲总时长
                    int currentPosition=player.getCurrentPosition();//获取播放进度
                    Message msg=Music_Activity.handler.obtainMessage();//创建消息对象
                    //将音乐的总时长和播放进度封装至消息对象中
                    Bundle bundle=new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    msg.setData(bundle);
                    //将消息发送到主线程的消息队列
                    Music_Activity.handler.sendMessage(msg);
                }
            };
            //开始计时任务后的5毫秒，第一次执行task任务，以后每500毫秒执行一次
            timer.schedule(task,5,500);
        }
    }
    class MusicControl extends Binder{//Binder是一种跨进程的通信方式
        public void play(int i){//String path
            Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/"+"music"+i);
            try{
                player.reset();//重置音乐播放器
                //加载多媒体文件
                player=MediaPlayer.create(getApplicationContext(),uri);
                player.start();//播放音乐
                addTimer();//添加计时器
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public void pausePlay(){
            player.pause();//暂停播放音乐
        }
        public void continuePlay(){
            player.start();//继续播放音乐
        }
        public void seekTo(int progress){
            player.seekTo(progress);//设置音乐的播放位置
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(player==null) return;
        if(player.isPlaying()) player.stop();//停止播放音乐
        player.release();//释放占用的资源
        player=null;//将player置为空
    }


}




