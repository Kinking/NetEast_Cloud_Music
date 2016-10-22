package com.geek_dream.player;

import com.geek_dream.adapter.AdapterListViewMusListDetail;
import com.geek_dream.musicbean.*;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.service.MyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends Activity {
    private boolean isPlay = false;
    private boolean isLove = false;


    //新增的属性
    private String[] hsq = { "hq", "sq" };
    long id; // 音乐id
    String title;// 音乐标题
    String artist;// 艺术家
    long duration;// 时长
    long size; // 文件大小
    String url;// 音乐的路径
    List<GetMusicInfo> musicInfo = new ArrayList<GetMusicInfo>();
    Intent intentConnService;
    TextView tv_singer;
    TextView tv_title;
    // 播放按钮
    ImageView img_lpPlayOrPause;
    // 主界面的播放按钮
    boolean mainIsPlay;
    // 下一首歌
    ImageView img_lpNext;
    // 是否点击了下一首
    private boolean isNext = false;
    ListToMain mtoList = new ListToMain();// 与MainActivity沟通对象
    MainToMusic mtoMusic = new MainToMusic();// 与MusicActivity沟通对象
    // 目前播放歌曲
    private int current = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        String listName = intent.getStringExtra("listName");
        TextView tv_ln = (TextView) findViewById(R.id.tv_list_name);
        tv_ln.setText(listName);

        ////////////////////
        initMusicList();
        initPlayBar();
        ////////////////////

        initMusicList();
        initPlayBar();
        initActionBarListener();
        initOtherView();
        // initScroll();
        // 与service联系到一起
        intentConnService = new Intent();
        intentConnService.setClass(ListActivity.this, MyService.class);
        intentConnService.putExtra("msg", "");
        startService(intentConnService);
        // 获取到所有的数据
        musicInfo = MusicListItem.getMp3Infos(ListActivity.this);
        // 获取到歌手和歌曲
        tv_singer = (TextView) findViewById(R.id.tv_lplybar_singer);
        tv_title = (TextView) findViewById(R.id.tv_lplybar_music);
        if (getIntent() != null) {
            Intent getBundle = getIntent();
            mtoList = (ListToMain) getBundle.getSerializableExtra("LIST");
            // 主界面是否已经开始播放音乐
            mainIsPlay = mtoList.isListisPlay();
            if (mainIsPlay) {// 若正在播放
                img_lpPlayOrPause.setImageResource(R.drawable.desk_pause_prs);
                isPlay = true;
            } else {
                img_lpPlayOrPause.setImageResource(R.drawable.desk_play_prs);
                isPlay = false;

            }
        }
        // 设置音乐的标题
        tv_title.setText(mtoMusic.getTitle());
        // 设置歌手名
        tv_singer.setText(mtoMusic.getSinger());
    }




    // private void initScroll(){
    // ScrollView scrollView = (ScrollView)
    // findViewById(R.id.scroll_list_detail);
    // scrollView.seton
    //
    // }


    private void initMusicList() {
        // ListView

        ListView lv_list_detail = (ListView) findViewById(R.id.lv_list_music);

        AdapterListViewMusListDetail adapterList = new AdapterListViewMusListDetail(
                dataMusic(), ListActivity.this);

        lv_list_detail.setAdapter(adapterList);
        lv_list_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                id = musicInfo.get(arg2).getId(); // 音乐id
                title = musicInfo.get(arg2).getTitle();// 音乐标题
                artist = musicInfo.get(arg2).getArtist();// 艺术家
                duration = musicInfo.get(arg2).getDuration();// 时长
                size = musicInfo.get(arg2).getSize(); // 文件大小
                url = musicInfo.get(arg2).getUrl();// 音乐的路径

                // 设置playbar中的歌曲名和歌手名
                tv_singer.setText(artist);
                tv_title.setText(title);
                // 为MusicActivity返回播放当前曲目
                mtoMusic.setCurrent(arg2);

                mtoList.setTitle(title);
                mtoList.setSinger(artist);
                // 记录目前正在播放的歌曲的位置
                current = arg2;
                // 播放音乐
                intentConnService.putExtra("msg", "danqubofang");
                intentConnService.putExtra("url", url);
                intentConnService.putExtra("current", arg2);
                startService(intentConnService);
                // 改变图标
                // mediaPlayer.start();
                // handler.post(runnable);
                img_lpPlayOrPause.setImageResource(R.drawable.desk_pause_prs);
                isPlay = true;

            }
        });
    }





//    // 音乐数据接口 数据作为测试 仅供参考 未完成
//    private List<MusicDetail> dataMusic() {
//        List<MusicDetail> list = new ArrayList<MusicDetail>();
//        list.add(new MusicDetail("周杰伦", "乱舞春秋", true, "hq"));
//        list.add(new MusicDetail("周杰伦", "花海", true, "sq"));
//        list.add(new MusicDetail("周杰伦", "告白气球", true, "hq"));
//        list.add(new MusicDetail("五月天", "转眼", true, "sq"));
//        list.add(new MusicDetail("张信哲", "爱如潮水", false, "sq"));
//        list.add(new MusicDetail("周杰伦", "借口", true, "hq"));
//        list.add(new MusicDetail("周杰伦", "扯", false, "hq"));
//        list.add(new MusicDetail("周杰伦", "四面楚歌", true, "sq"));
//        list.add(new MusicDetail("周杰伦", "不能说的秘密", true, "hq"));
//        list.add(new MusicDetail("周杰伦", "乱舞春秋", true, "hq"));
//        list.add(new MusicDetail("周杰伦", "花海", true, "sq"));
//        list.add(new MusicDetail("周杰伦", "告白气球", true, "hq"));
//        list.add(new MusicDetail("五月天", "转眼", true, "sq"));
//        list.add(new MusicDetail("张信哲", "爱如潮水", false, "sq"));
//        list.add(new MusicDetail("周杰伦", "借口", true, "hq"));
//        list.add(new MusicDetail("周杰伦", "扯", false, "hq"));
//        list.add(new MusicDetail("周杰伦", "四面楚歌", true, "sq"));
//        list.add(new MusicDetail("周杰伦", "不能说的秘密", true, "hq"));
//        return list;
//    }

    // 音乐数据接口
    private List<MusicDetail> dataMusic() {
        List<MusicDetail> list = new ArrayList<MusicDetail>();
        // 获取到所有的数据
        musicInfo = MusicListItem.getMp3Infos(ListActivity.this);
        Random ran = new Random();// 随机产生hq或sq

        for (int i = 0; i < musicInfo.size(); i++) {
            list.add(new MusicDetail(musicInfo.get(i).getArtist(),// 艺术家的名字
                    musicInfo.get(i).getTitle(), true, hsq[ran.nextInt(1)]));
        }
        return list;
    }




    // 1、actionbar监听 即 返回按钮 和 分享按钮（未实现）
    private void initActionBarListener() {
        findViewById(R.id.layout_laback).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // 与 MainActivity的通信
                        Intent result = new Intent();
                        Bundle bundle = new Bundle();
                        mtoList.setListisPlay(isPlay);
                        System.out.println(isPlay);
                        bundle.putSerializable("LIST", mtoList);
                        result.putExtras(bundle);
                        ListActivity.this.setResult(2, result);

                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);

                    }
                });
    }




    // 2、playbar监听
    private void initPlayBar() {
        // 播放按钮
        img_lpPlayOrPause = (ImageView) findViewById(R.id.img_lplybar_playorpause);
        // 下一首的按钮
        img_lpNext = (ImageView) findViewById(R.id.img_lplybar_next);
        // 点击界面事件
        findViewById(R.id.layout_lplybar).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // Intent intent = new Intent();
                        // intent.setClass(ListActivity.this,
                        // MusicActivity.class);
                        // // // 告诉MusicActivity播放器正在播放
                        // // mtoList.setMainisPlay(isPlay);
                        // // // 设置标题
                        // // mtoList.setTitle(tv_title.getText()
                        // // .toString());
                        // // // 设置歌手名
                        // // mtoMusic.setSinger(tv_singer.getText()
                        // // .toString());
                        // startActivity(intent);
                        // 与MusicActivity的通信
                        Intent intentToMusic = new Intent();
                        intentToMusic.setClass(ListActivity.this,
                                MusicActivity.class);
                        mtoMusic.setMainisPlay(isPlay);
                        mtoMusic.setSinger(tv_singer.getText().toString());
                        mtoMusic.setTitle(tv_title.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("MUSIC", mtoMusic);
                        // 设置从MainActivity到MusicActivity需要的值
                        intentToMusic.putExtras(bundle);
                        startActivityForResult(intentToMusic, 1);
                    }
                });

        img_lpPlayOrPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 播放音乐
                intentConnService.putExtra("msg", "bofang");
                startService(intentConnService);
                if (!isPlay) {
                    // mediaPlayer.start();
                    // handler.post(runnable);
                    img_lpPlayOrPause
                            .setImageResource(R.drawable.desk_pause_prs);
                    isPlay = true;
                } else if (isPlay) {
                    // mediaPlayer.pause();
                    // handler.removeCallbacks(runnable);
                    img_lpPlayOrPause
                            .setImageResource(R.drawable.desk_play_prs);

                    isPlay = true;
                }
                mtoList.setListisPlay(isPlay);

            }
        });

        // 点击播放下一首歌
        img_lpNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // 如果播放和下一首都没点击，则改变播放图标
                if (current == musicInfo.size() - 1) {
                    current = 0;
                    tv_singer.setText(musicInfo.get(current).getArtist());
                    tv_title.setText(musicInfo.get(current).getTitle());
                } else {
                    tv_singer.setText(musicInfo.get(current + 1).getArtist());
                    tv_title.setText(musicInfo.get(current + 1).getTitle());
                }

                img_lpPlayOrPause.setImageResource(R.drawable.desk_pause_prs);

                isNext = true;
                isPlay = true;
                intentConnService.putExtra("msg", "xiayishou");
                startService(intentConnService);
            }
        });

    }


    // 点赞功能
    private void initOtherView() {
        final ImageView img_love = (ImageView) findViewById(R.id.img_list_love);
        img_love.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!isLove) {
                    img_love.setSelected(true);
                    isLove = true;
                } else {
                    img_love.setSelected(false);
                    isLove = false;
                }
            }
        });

    }

    /**
     * 接收数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        mtoMusic = (MainToMusic) data.getSerializableExtra("MUSIC");
        isPlay = mtoMusic.isMainisPlay();
        if (isPlay) {// 若播放器在播放的话
            // mediaPlayer.start();
            // handler.post(runnable);
            img_lpPlayOrPause.setImageResource(R.drawable.desk_pause_prs);
            isPlay = true;
        } else {// 若播放器没有在播放的话
            // mediaPlayer.pause();
            // handler.removeCallbacks(runnable);
            img_lpPlayOrPause.setImageResource(R.drawable.desk_play_prs);

            isPlay = false;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
