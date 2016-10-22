package com.geek_dream.player;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.musicbean.GetMusicInfo;
import com.geek_dream.musicbean.MainToMusic;
import com.geek_dream.musicbean.MusicListItem;
import com.geek_dream.service.MyService;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends Activity {

    private ObjectAnimator discObjectAnimator, neddleObjectAnimator;

    private ImageView img_playorpause;
    private ImageView img_mulist;
    private ImageView img_prev;
    private ImageView img_next;
    //模式变换功能
    private ImageView img_playmode;
    // 数组存放播放模式 fmode作为flag
    private String[] playmode = { "列表循环", "单曲循环", "随机播放" };
    private int fmode = 0;
    // flag 音乐是否播放
    private boolean isPlay = false;

    // ActionBar四组件
    private LinearLayout layout_aBack;
    private TextView tv_aMusic;
    private TextView tv_aSinger;
    private LinearLayout layout_aShare;

    // 音乐的信息
    long id; // 音乐id
    String title;// 音乐标题
    String artist;// 艺术家
    long duration;// 时长
    long size; // 文件大小
    String url;// 音乐的路径
    List<GetMusicInfo> musicInfo = new ArrayList<GetMusicInfo>();
    Intent intentConnService;
    // MainActivity的isplay
    boolean mainIsPlay = false;
    // 获取到seekbar
    SeekBar seekbar;
    // 是否点击了下一首
    private boolean isNext = false;
    // 是否点击了上一首
    private boolean isPre = false;
    MainToMusic mtoMusic = new MainToMusic();// 与MainActivity沟通对象
    // 是否点击了改变了fmode
    private boolean isMode = false;
    //当前曲目
    private int current=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplay);
        //intent接收上个界面数据 UNDONE

        musicInfo= MusicListItem.getMp3Infos(MusicActivity.this);


        initFindView();
        initView();
        initActionBarListener();
        initMainListener();

        // 与service联系到一起
        intentConnService = new Intent();
        intentConnService.setClass(MusicActivity.this, MyService.class);
        intentConnService.putExtra("msg", "");
        startService(intentConnService);
        Log.d("MusicActivity", "create");
        // 获取到MainActivity的intent
        // Intent
        // intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        // Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        if (getIntent() != null) {
            Intent getBundle = getIntent();
            mtoMusic = (MainToMusic) getBundle.getSerializableExtra("MUSIC");

            mainIsPlay = mtoMusic.isMainisPlay();// getString()返回指定key的值
            if (mainIsPlay) {
                // mediaPlayer.start();
                // handler.post(runnable);
                img_playorpause.setImageResource(R.drawable.desk_pause);
                img_playorpause.setBackgroundResource(R.drawable.sel_img_pause);
                discObjectAnimator.start();
                neddleObjectAnimator.start();
                isPlay = true;
            } else {
                // mediaPlayer.pause();
                // handler.removeCallbacks(runnable);

                img_playorpause.setImageResource(R.drawable.desk_play);
                img_playorpause.setBackgroundResource(R.drawable.sel_img_play);
                discObjectAnimator.cancel();
                neddleObjectAnimator.reverse();
                isPlay = false;
            }
            // 设置音乐的标题
            tv_aMusic.setText(mtoMusic.getTitle());
            // 设置歌手名
            tv_aSinger.setText(mtoMusic.getSinger());
            //设置current;
            current=mtoMusic.getCurrent();
        }
    }


    //findViewById
    private void initFindView() {
        //最下五钮
        img_playorpause = (ImageView) findViewById(R.id.img_playorpause);
        img_playmode = (ImageView) findViewById(R.id.img_playmode);
        img_mulist = (ImageView) findViewById(R.id.img_mulist);
        img_prev = (ImageView) findViewById(R.id.img_prev);
        img_next = (ImageView) findViewById(R.id.img_next);

        //Actionbar4组件
        layout_aBack= (LinearLayout) findViewById(R.id.layout_aback);
        layout_aShare = (LinearLayout) findViewById(R.id.layout_ashare);
        tv_aMusic = (TextView) findViewById(R.id.tv_amusic);
        tv_aSinger = (TextView) findViewById(R.id.tv_asinger);

    }

    // 监听器部分，分为2个方法 actionbar和主界面 但是 音乐名和歌手名要自己在其他方法改变！！！

    // 1、actionbar监听 即 返回按钮 和 分享按钮（未实现）
    private void initActionBarListener() {
        layout_aBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent result = new Intent();
                Bundle bundle = new Bundle();
                mtoMusic.setMainisPlay(isPlay);
                mtoMusic.setSinger(tv_aSinger.getText().toString());
                mtoMusic.setTitle(tv_aSinger.getText().toString());
                mtoMusic.setFmode(fmode);
                bundle.putSerializable("MUSIC", mtoMusic);
                result.putExtras(bundle);
                MusicActivity.this.setResult(1, result);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);

            }
        });
    }

    // 2、界面按钮监听接口
    private void initMainListener() {

        // 播放按钮监听器
        img_playorpause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                intentConnService.putExtra("msg", "bofang");
                startService(intentConnService);
                if (!isPlay) {
                    // mediaPlayer.start();
                    // handler.post(runnable);
                    img_playorpause.setImageResource(R.drawable.desk_pause);
                    img_playorpause
                            .setBackgroundResource(R.drawable.sel_img_pause);
                    discObjectAnimator.start();
                    neddleObjectAnimator.start();
                    isPlay = true;
                } else {
                    // mediaPlayer.pause();
                    // handler.removeCallbacks(runnable);

                    img_playorpause.setImageResource(R.drawable.desk_play);
                    img_playorpause
                            .setBackgroundResource(R.drawable.sel_img_play);
                    discObjectAnimator.cancel();
                    neddleObjectAnimator.reverse();
                    isPlay = false;
                }

            }
        });

        // 播放模式 playmode 监听器
        img_playmode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isMode) {
                    switch (fmode) {
                        case 0:
                            // 设置为单曲模式
                            img_playmode.setImageResource(R.drawable.desk2_one);
                            img_playmode
                                    .setBackgroundResource(R.drawable.sel_mode_one);
                            intentConnService.putExtra("status", "danqu");
                            startService(intentConnService);
                            fmode = 1;
                            break;

                        case 1:
                            // 随机模式
                            img_playmode.setImageResource(R.drawable.desk2_shuffle);
                            img_playmode
                                    .setBackgroundResource(R.drawable.sel_mode_shuffle);
                            intentConnService.putExtra("status", "suiji");
                            startService(intentConnService);
                            fmode = 2;
                            break;

                        case 2:
                            // 设置为顺序模式
                            img_playmode.setImageResource(R.drawable.desk2_loop);
                            img_playmode
                                    .setBackgroundResource(R.drawable.sel_mode_loop);
                            intentConnService.putExtra("status", "shunxu");
                            startService(intentConnService);
                            fmode = 0;
                            break;
                        default:
                            break;
                    }
                    if (fmode != 0) {
                        Toast.makeText(MusicActivity.this, playmode[fmode - 1],
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MusicActivity.this, playmode[2], Toast.LENGTH_LONG)
                                .show();
                    }
                } else {//
                    isMode = true;
                    img_playmode.setImageResource(R.drawable.desk2_one);
                    img_playmode.setBackgroundResource(R.drawable.sel_mode_one);
                    intentConnService.putExtra("status", "danqu");
                    startService(intentConnService);
                    fmode = 1;
                }

            }
        });
        // 上一首歌
        img_prev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(current==0){
                    current=musicInfo.size()-1;
                    tv_aSinger.setText(musicInfo.get(current).getArtist());
                    tv_aMusic.setText(musicInfo.get(current).getTitle());
                }else{
                    tv_aSinger.setText(musicInfo.get(current-1).getArtist());
                    tv_aMusic.setText(musicInfo.get(current-1).getTitle());
                }
                img_playorpause.setImageResource(R.drawable.desk_pause);

                isPlay = true;

                // TODO Auto-generated method stub
                intentConnService.putExtra("msg", "shangyishou");
                startService(intentConnService);
                // int current=MyService.getCurrent();
                // // 改变歌曲名和歌手名
                // tv_aSinger.setText(musicInfo.get(current)
                // .getArtist());
                // tv_aMusic.setText(musicInfo.get(current)
                // .getTitle());
            }
        });
        // 下一首歌
        img_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(current==musicInfo.size()-1){
                    current=0;
                    tv_aSinger.setText(musicInfo.get(current).getArtist());
                    tv_aMusic.setText(musicInfo.get(current).getTitle());
                }else{
                    tv_aSinger.setText(musicInfo.get(current+1).getArtist());
                    tv_aMusic.setText(musicInfo.get(current+1).getTitle());
                }
                img_playorpause.setImageResource(R.drawable.desk_pause);

                isPlay = true;

                intentConnService.putExtra("msg", "xiayishou");
                startService(intentConnService);
                // // 改变歌曲名和歌手名
                // tv_aSinger.setText(musicInfo.get(MyService.getCurrent())
                // .getArtist());
                // tv_aMusic.setText(musicInfo.get(MyService.getCurrent())
                // .getTitle());
            }
        });

    }



    // 专辑旋转 不用管
    private void initView() {
        // 最外部的半透明边线
        OvalShape ovalShape0 = new OvalShape();
        ShapeDrawable drawable0 = new ShapeDrawable(ovalShape0);
        drawable0.getPaint().setColor(0x10000000);
        drawable0.getPaint().setStyle(Paint.Style.FILL);
        drawable0.getPaint().setAntiAlias(true);

        // 黑色唱片边框
        RoundedBitmapDrawable drawable1 = RoundedBitmapDrawableFactory.create(
                getResources(), BitmapFactory.decodeResource(getResources(),
                        R.drawable.play_disc));
        // drawable1.setCircular(true);
        drawable1.setAntiAlias(true);

        // 内层黑色边线
        OvalShape ovalShape2 = new OvalShape();
        ShapeDrawable drawable2 = new ShapeDrawable(ovalShape2);
        drawable2.getPaint().setColor(Color.BLACK);
        drawable2.getPaint().setStyle(Paint.Style.FILL);
        drawable2.getPaint().setAntiAlias(true);

        // 最里面的图像

        Bitmap src = BitmapFactory.decodeResource(getResources(),
                R.drawable.demo_disk1);
        Bitmap dst = Bitmap.createBitmap(src);
        // 将长方形图片裁剪成正方形图片
        if (src.getWidth() >= src.getHeight()) {

            dst = Bitmap.createBitmap(src, src.getWidth() / 2 - src.getHeight()
                    / 2, 0, src.getHeight(), src.getHeight());

        } else {
            dst = Bitmap.createBitmap(src, 0,
                    src.getHeight() / 2 - src.getWidth() / 2, src.getWidth(),
                    src.getWidth());
        }
        RoundedBitmapDrawable drawable3 = RoundedBitmapDrawableFactory.create(
                getResources(), dst);
        drawable3.setCornerRadius((dst.getWidth() / 2)); // 设置圆角半径为正方形边长的一半
        drawable3.setAntiAlias(true);
        // image.setImageDrawable(drawable3);
        // RoundedBitmapDrawable drawable3 =
        // RoundedBitmapDrawableFactory.create(getResources(),
        // BitmapFactory.decodeResource(getResources(), R.drawable.demo_disk1));
        // drawable3.setCircular(true);
        // drawable3.setAntiAlias(true);

        Drawable[] layers = new Drawable[4];
        layers[0] = drawable0;
        layers[1] = drawable1;
        layers[2] = drawable2;
        layers[3] = drawable3;

        LayerDrawable layerDrawable = new LayerDrawable(layers);

        int width = 10;
        // 针对每一个图层进行填充，使得各个圆环之间相互有间隔，否则就重合成一个了。
        // layerDrawable.setLayerInset(0, width, width, width, width);
        layerDrawable.setLayerInset(1, width, width, width, width);
        layerDrawable.setLayerInset(2, width * 11, width * 11, width * 11,
                width * 11);
        layerDrawable.setLayerInset(3, width * 12, width * 12, width * 12,
                width * 12);

        final View discView = findViewById(R.id.myView);
        discView.setBackgroundDrawable(layerDrawable);

        ImageView needleImage = (ImageView) findViewById(R.id.needle);

        discObjectAnimator = ObjectAnimator.ofFloat(discView, "rotation", 0,
                360);
        discObjectAnimator.setDuration(20000);
        // 使ObjectAnimator动画匀速平滑旋转
        discObjectAnimator.setInterpolator(new LinearInterpolator());
        // 无限循环旋转
        discObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        discObjectAnimator.setRepeatMode(ValueAnimator.INFINITE);

        neddleObjectAnimator = ObjectAnimator.ofFloat(needleImage, "rotation",
                0, 25);
        needleImage.setPivotX(0);
        needleImage.setPivotY(0);
        neddleObjectAnimator.setDuration(800);
        neddleObjectAnimator.setInterpolator(new LinearInterpolator());
    }

    //监听器部分，分为2个方法 actionbar和主界面  但是 音乐名和歌手名要自己在其他方法改变！！！





}
