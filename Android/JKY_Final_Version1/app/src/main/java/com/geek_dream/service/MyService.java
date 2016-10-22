package com.geek_dream.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.geek_dream.musicbean.GetMusicInfo;
import com.geek_dream.musicbean.MusicListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyService extends Service {

    // IBinder为接口
    // Binder为类
    MediaPlayer mPlayer;
    List<GetMusicInfo> musicInfo = new ArrayList<GetMusicInfo>();

    private int current = 0;// 歌曲在list中的位置
    String url;// 歌曲的路径
    String status = "shunxu";// 播放模式,默认为顺序播放
    private int currentTime; // 当前播放进度
    String msg;// 获取到的命令
    String action = "";// 是否点击了单曲
    private int duration;// 目前播放进度




    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    // 每次startservice都会调用此方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("service", "start");
        // 看看是否点击更改了播放模式


        try{

            if ((Object)intent.getStringExtra("status")!=null) {
                // 若点击了模式改变的话将播放模式改变
                status = intent.getStringExtra("status");
            } else {
                System.out.println("没有要改变模式");
            }
            Log.d("service", "start" + msg);
            // 看看是否点击了播放下一首或上一首等按钮
            if ((Object)intent.getStringExtra("msg") != null) {
                msg = intent.getStringExtra("msg");
                // 看看是否指定播放了哪一首歌曲

                if (msg.equals("bofang")) {
                    playMusic();
                } else if (msg.equals("xiayishou")) {
                    nextMusic();
                } else if (msg.equals("shangyishou")) {
                    preMusic();

                    // 如果点击了列表里的某一首歌
                } else if (msg.equals("danqubofang")) {
                    current=intent.getIntExtra("current", 0);//改变当前播放位置
                    if ((Object)intent.getStringExtra("url") != null) {// 如果路径不为空则播放该歌曲
                        url = intent.getStringExtra("url");// 获取到想要播放的歌曲路径
                        try {
                            mPlayer.reset();// 清除之前的数据
                            mPlayer.setDataSource(url);// 设置点击播放的歌曲
                            mPlayer.prepare();
                        } catch (IllegalStateException | IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        mPlayer.start();
                    }
                } else {
                    System.out.println(msg);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

//        if ((Object)intent.getStringExtra("status")!=null) {
//            // 若点击了模式改变的话将播放模式改变
//            status = intent.getStringExtra("status");
//        } else {
//            System.out.println("没有要改变模式");
//        }
//        Log.d("service", "start" + msg);
//        // 看看是否点击了播放下一首或上一首等按钮
//        if ((Object)intent.getStringExtra("msg") != null) {
//            msg = intent.getStringExtra("msg");
//            // 看看是否指定播放了哪一首歌曲
//
//            if (msg.equals("bofang")) {
//                playMusic();
//            } else if (msg.equals("xiayishou")) {
//                nextMusic();
//            } else if (msg.equals("shangyishou")) {
//                preMusic();
//
//                // 如果点击了列表里的某一首歌
//            } else if (msg.equals("danqubofang")) {
//                current=intent.getIntExtra("current", 0);//改变当前播放位置
//                if ((Object)intent.getStringExtra("url") != null) {// 如果路径不为空则播放该歌曲
//                    url = intent.getStringExtra("url");// 获取到想要播放的歌曲路径
//                    try {
//                        mPlayer.reset();// 清除之前的数据
//                        mPlayer.setDataSource(url);// 设置点击播放的歌曲
//                        mPlayer.prepare();
//                    } catch (IllegalStateException | IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    mPlayer.start();
//                }
//            } else {
//                System.out.println(msg);
//            }
//
//        }

        // //查看是否改变了seekbar
        // if(intent.getIntExtra("duration",0)!=0){
        // duration=intent.getIntExtra("duration",0);
        // mPlayer.seekTo(duration);
        // mPlayer.start();
        // }else


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        System.out.println("SecondService-->onCreate");
        super.onCreate();
        Log.d("service", "create");
        mPlayer = new MediaPlayer();

        try {
            musicInfo = new MusicListItem().getMp3Infos(MyService.this);// 获取所有的歌曲信息
            mPlayer.setDataSource(musicInfo.get(current).getUrl());// 初始化一首歌
            mPlayer.prepare();// 准备歌曲
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /**
         * 设置音乐播放完成时的监听器
         */
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                if (status.equals("shunxu")) { // 顺序播放
                    if (!mPlayer.isPlaying()) {
                        nextMusic();
                    }

                } else if (status.equals("danqu")) { // 单曲循环
                    if (!mPlayer.isPlaying()) {
                        playMusic();
                    }

                } else if (status.equals("suiji")) { // 随机播放
                    if (!mPlayer.isPlaying()) {
                        Random random = new Random();
                        current = random.nextInt(musicInfo.size() - 1);
                        try {
                            mPlayer.reset();// 清除之前的数据
                            mPlayer.setDataSource(musicInfo.get(current)
                                    .getUrl());// 设置随机播放的歌曲
                            mPlayer.prepare();
                        } catch (IllegalStateException | IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        playMusic();
                    }

                }
            }
        });
    }


    @Override
    public void onDestroy() {
        System.out.println("SecondService-->onDestroy");
        mPlayer.stop();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("SecondService-->onUnbind");
        return super.onUnbind(intent);
    }

    /**
     * 当点击中间的播放/暂停按钮的时候发生的点击事件
     *
     * @param
     */
    public void playMusic() {

        if (mPlayer.isPlaying() || action.equals("dianjidanqu")) {
            action = "";
            mPlayer.pause();
        } else {
            mPlayer.start();
        }

    }

    /**
     * 点击播放上一首
     */
    public void preMusic() {

        if (current == 0) {
            current = musicInfo.size() - 1;// 第一首的上一首设置为最后

        } else {
            current--;
        }
        if(status.equals("suiji")){
            Random random = new Random();
            current = random.nextInt(musicInfo.size() - 1);
            Log.d("status", "suijibofang");
        }
        try {
            mPlayer.reset();
            mPlayer.setDataSource(musicInfo.get(current).getUrl());// 为播放器添加歌曲

            mPlayer.prepare();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 为播放器添加了歌曲之后，用进度条获取歌曲的总时长

        mPlayer.start();

    }

    /**
     * 点击播放下一首
     */
    public void nextMusic() {

        if (current == musicInfo.size() - 1) {

            current = 0;// 若播到最后一首了，则从头开始播放

        } else {
            current++;
        }
        if(status.equals("suiji")){
            Random random = new Random();
            current = random.nextInt(musicInfo.size() - 1);
            Log.d("status", "suijibofang");
        }
        try {
            mPlayer.reset();
            mPlayer.setDataSource(musicInfo.get(current).getUrl());// 为播放器添加歌曲

            mPlayer.prepare();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mPlayer.start();
    }
}
