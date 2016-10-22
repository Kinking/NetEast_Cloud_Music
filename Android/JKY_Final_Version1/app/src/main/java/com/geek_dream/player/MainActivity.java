package com.geek_dream.player;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.adapter.AdapterListViewDrawer;
import com.geek_dream.adapter.AdapterListViewMusList;
import com.geek_dream.adapter.AdapterListViewMusManager;
import com.geek_dream.adapter.AdapterViewPageMain;
import com.geek_dream.musicbean.GetMusicInfo;
import com.geek_dream.musicbean.ListToMain;
import com.geek_dream.musicbean.MainToMusic;
import com.geek_dream.musicbean.MusicList;
import com.geek_dream.musicbean.MusicListItem;
import com.geek_dream.musicbean.MusicManager;
import com.geek_dream.service.MyService;
import com.geek_dream.util.FunctionDrawer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //侧滑
    private List listFunction = new ArrayList();
    private ListView lv_drawer;

    //ViewPage
    private ViewPager vp_main;
    private LayoutInflater layoutInflaterVp;

    // Music界面
    private boolean isVisible = true;
    ImageView img_arr;
    ListView lv_list;

    // ActionBar
    private ImageView[] img_bt_actionbar = new ImageView[3];
    private int[] idButtonActionBar = { R.id.img_actB_discover, R.id.img_actB_music,
            R.id.img_actB_firends };


    // 音乐的信息
    long id; // 音乐id
    String title;// 音乐标题
    String artist;// 艺术家
    long duration;// 时长
    long size; // 文件大小
    String url;// 音乐的路径
    List<GetMusicInfo> musicInfo = new ArrayList<GetMusicInfo>();
    // MusicActivity的isplay
    boolean musicIsPlay = false;

    /*注意，在我所有Demo中的isPlay都为了判断音乐是否播放
     *仅仅为了测试而已，正式版根据逻辑需要选择用或不用
     */
    //最下方playbar
    private boolean isPlay = false;

    //是否点击了下一首
    private boolean isNext = false;

    private RelativeLayout layout_pBar;
    private ImageView img_pList;
    private ImageView img_pPlayorpause;
    private ImageView img_pNext;
    private TextView tv_pMusic;
    private TextView tv_pSinger;
    Intent intentConnService;
    Intent intentToMusic;

    // MainActivity和MusicActivity之间的通信工具类
    public MainToMusic mtoMusic = new MainToMusic();
    // MainActivity和MusicActivity之间的通信工具类
    public ListToMain mtoList = new ListToMain();
    // 当前曲目
    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFindView();
        initDrawer();

//        intentConnService = new Intent();
//        intentConnService.setClass(MainActivity.this, MyService.class);
//        intentConnService.putExtra("msg", "");
//        startService(intentConnService);

        //得到界面填充器 ViewPager部分使用
        layoutInflaterVp = getLayoutInflater();


        initViewPage();
        initActionBar();
        initPlayBar();

        // 获取所有歌曲信息
//        musicInfo = MusicListItem.getMp3Infos(MainActivity.this);
    }


    //findViewById
    private void initFindView() {
        layout_pBar = (RelativeLayout) findViewById(R.id.layout_plybar);
        img_pList = (ImageView) findViewById(R.id.img_plybar_list);
        img_pPlayorpause = (ImageView) findViewById(R.id.img_plybar_playorpause);
        img_pNext = (ImageView) findViewById(R.id.img_plybar_next);
        tv_pMusic = (TextView) findViewById(R.id.tv_plybar_music);
        tv_pSinger = (TextView) findViewById(R.id.tv_plybar_singer);
    }


    //初始化侧滑栏
    private void initDrawer() {
        //为listview添加适配器
        lv_drawer = (ListView) findViewById(R.id.lv_drawer);

        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_msg, "我的消息"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_store,"积分商城"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_vip,"会员中心"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_free,"在线听歌免流量"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_identify,"听歌识曲"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_skin,"主题换肤"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_clock,"音乐闹钟"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_night,"夜间模式"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_vehicle,"驾驶模式"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_cloud,"我的音乐云盘"));
        listFunction.add(new FunctionDrawer(R.drawable.topmenu_icn_set,"设置"));


        AdapterListViewDrawer adapterListViewDrawer = new AdapterListViewDrawer(listFunction, this);

        lv_drawer.setAdapter(adapterListViewDrawer);

        //定时退出
        TextView tv_plan = (TextView) findViewById(R.id.tv_fun_plan);
//		tv_plan.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Builder builder = new Builder(MainActivity.this);
//				builder.setItems("不开启", )
//			}
//		});


        //退出应用
        TextView tv_exit = (TextView) findViewById(R.id.tv_fun_exit);
        tv_exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MainActivity.this.finish();
            }
        });
    }

    // 初始化Music界面,在initViewPager方法中调用
    // 点击到歌单功能也在其中
    private void initMusicView(View view) {
        // 上ListView

        ListView lv_manager = (ListView) view
                .findViewById(R.id.lv_music_manager);



//        List<MusicManager> lm = new ArrayList<>();
//        lm=dataMusicManager();


        //AdapterListViewMusManager adapterListViewMusManager = new AdapterListViewMusManager(lm,MainActivity.this);
        int[] id_img_manager={R.drawable.music_icn_local,R.drawable.music_icn_mv,R.drawable.music_icn_recent,R.drawable.music_icn_artist};
        String[] manager = {"本地音乐","MV 播放","最近播放","我的歌手"};

        List<Map<String,Object>> list_manager = new ArrayList<Map<String, Object>>();

        for (int i = 0; i<4;i++){
            Map<String,Object> map_manager = new HashMap<>();

            map_manager.put("img",id_img_manager[i]);
            map_manager.put("manager",manager[i]);
            list_manager.add(map_manager);
        }
        SimpleAdapter sa_manager = new SimpleAdapter(MainActivity.this,list_manager,R.layout.adapter_listview_manager,new String[]{"img","manager"},new int[]{R.id.img_music_manager,R.id.tv_music_manager});


        lv_manager.setAdapter(sa_manager);

        // 下ListView

        final ListView lv_list = (ListView) view
                .findViewById(R.id.lv_music_list);


//        AdapterListViewMusList adapterListViewMusList = new AdapterListViewMusList(lm,MainActivity.this);
        int[] id_img_list = {R.drawable.demo_disk1, R.drawable.demo_disk2,
                R.drawable.demo_disk3, R.drawable.demo_disk4,
                R.drawable.demo_disk5, R.drawable.demo_disk6 };
        String[] list_name = {"我喜欢","侧耳倾听","Good night","热推","16.08.18","摇滚","轻音乐","头文字D","在下版本","齐木楠雄"};


        List<Map<String,Object>> list_list = new ArrayList<Map<String, Object>>();
        Random random = new Random();
        for (int j = 0; j<10;j++){
            Map<String,Object> map_list = new HashMap<>();
            map_list.put("img2",id_img_list[random.nextInt(6)]);
            map_list.put("list2",list_name[j]);

            list_list.add(map_list);
        }
        SimpleAdapter sa_list = new SimpleAdapter(MainActivity.this,list_list,R.layout.adapter_listview_list,new String[]{"img2","list2"},new int[]{R.id.img_music_list,R.id.tv_music_list});

        lv_list.setAdapter(sa_list);

        // 点击到歌单功能
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                TextView tv = (TextView) arg1.findViewById(R.id.tv_music_list);
//                ImageView img = (ImageView) arg1
//                        .findViewById(R.id.img_list_disk);

                String listName = tv.getText().toString();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, com.geek_dream.player.ListActivity.class);
                intent.putExtra("listName", listName);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
        });

        // 中Relativelayout

        img_arr = (ImageView) view.findViewById(R.id.img_music_arr);
        view.findViewById(R.id.layout_music_controller).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (isVisible) {
                            lv_list.setVisibility(View.GONE);
                            img_arr.setImageResource(R.drawable.recommend_icn_arr);
                            isVisible = false;
                        } else {
                            lv_list.setVisibility(View.VISIBLE);
                            img_arr.setImageResource(R.drawable.recommend_icn_arr_b);
                            isVisible = true;
                        }

                    }
                });
    }

//    // 修改MusicManager数据接口 （修改四个整数的值） 其他就不用改了
//    private List<MusicManager> dataMusicManager() {
//        int numLocal = 0;
//        int numMv = 0;
//        int numRecent = 0;
//        int numArtist = 0;
//        List<MusicManager> list_manager = new ArrayList<MusicManager>();
//        MusicManager local = new MusicManager(R.drawable.music_icn_local,
//                "本地音乐", 0);
//        MusicManager mv = new MusicManager(R.drawable.music_icn_mv, "MV 播放", 0);
//        MusicManager recent = new MusicManager(R.drawable.music_icn_recent,
//                "最近播放", 0);
//        MusicManager artist = new MusicManager(R.drawable.music_icn_artist,
//                "我的歌手", 0);
//
//        local.setNum(numLocal);
//        mv.setNum(numMv);
//        recent.setNum(numRecent);
//        artist.setNum(numArtist);
//
//        list_manager.add(local);
//        list_manager.add(mv);
//        list_manager.add(recent);
//        list_manager.add(artist);
//
//        return list_manager;
//
//    }

    // 修改歌单MusicList数据接口 (新建对象用set方法) 歌单名仅供参考 ，作为测试之用
    private List dataMusicList() {
        int[] id = { R.drawable.demo_disk1, R.drawable.demo_disk2,
                R.drawable.demo_disk3, R.drawable.demo_disk4,
                R.drawable.demo_disk5, R.drawable.demo_disk6 };
        Random random = new Random();
        List<MusicList> list = new ArrayList<MusicList>();
        list.add(new MusicList(id[random.nextInt(6)], "我喜欢"));
        list.add(new MusicList(id[random.nextInt(6)], "侧耳倾听"));
        list.add(new MusicList(id[random.nextInt(6)], "Good night!"));
        list.add(new MusicList(id[random.nextInt(6)], "AAABBBCCC"));
        list.add(new MusicList(id[random.nextInt(6)], "热推"));
        list.add(new MusicList(id[random.nextInt(6)], "宇宙无敌歌单"));
        list.add(new MusicList(id[random.nextInt(6)], "自杀小队"));
        list.add(new MusicList(id[random.nextInt(6)], "头文字D"));
        list.add(new MusicList(id[random.nextInt(6)], "在下坂本"));
        list.add(new MusicList(id[random.nextInt(6)], "有何贵干"));

        return list;

    }


    // 写死的推荐页面
    private void initDisView(View view) {
        ListView lv_dis = (ListView) view.findViewById(R.id.lv_discover);
        int[] id_img = {R.drawable.index_daily_ban1,R.drawable.index_daily_ban2,R.drawable.index_new_america,R.drawable.index_new_china,R.drawable.index_new_japan,R.drawable.index_new_korea};
        String[] text = {"时光匆匆，你错过了什么","午后 | 一杯茶，一首歌","欧美 | Hot FM","华语 | 留住，那份爱","日本 | 秒速五厘米","韩流 | 舞曲也有音乐性"};

        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<6;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img_id", id_img[i]);
            map.put("text",text[i] );
            list.add(map);
        }

        SimpleAdapter adapter_dis = new SimpleAdapter(MainActivity.this,list ,R.layout.adapter_listview_discover, new String[]{"img_id","text"}, new int[]{R.id.img_discover,R.id.tv_discover});
        lv_dis.setAdapter(adapter_dis);
    }

    //写死的朋友界面
    private void initFriendView(View view) {
        ListView lv_dis = (ListView) view.findViewById(R.id.lv_friend);
        int[] id_img = {R.drawable.pic_aboutus_1,R.drawable.pic_aboutus_2,R.drawable.pic_aboutus_3,R.drawable.pic_aboutus_4,R.drawable.pic_aboutus_5,R.drawable.pic_aboutus_6};
        String[] text = {"黄致远\t组长","彭丹","张媛媛","何亚芝","林媛媛","吴江恒"};

        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<6;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img_id", id_img[i]);
            map.put("text",text[i] );
            list.add(map);
        }

        SimpleAdapter adapter_fri = new SimpleAdapter(MainActivity.this,list ,R.layout.adapter_listview_friend, new String[]{"img_id","text"}, new int[]{R.id.img_friend,R.id.tv_friend_name});
        lv_dis.setAdapter(adapter_fri);

    }


    //初始化滑动界面
    private void initViewPage() {
        // 创建list数据集合 view page
        List<View> listVp = new ArrayList<View>();
        // ViewPage滑动效果
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        View discoverView = layoutInflaterVp.inflate(R.layout.viewpager_discover,
                null);
        View musplayView = layoutInflaterVp.inflate(R.layout.viewpager_music, null);
        View friendView = layoutInflaterVp.inflate(R.layout.viewpager_friend,
                null);
        listVp.add(discoverView);
        listVp.add(musplayView);
        listVp.add(friendView);

		initDisView(discoverView);
		initMusicView(musplayView);
        initFriendView(friendView);

        AdapterViewPageMain adapterDrawerLView = new AdapterViewPageMain(
                listVp, MainActivity.this);
        vp_main.setAdapter(adapterDrawerLView);

        //初始页为音乐
        vp_main.setCurrentItem(1);

        // ViewPage 监听
        vp_main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < img_bt_actionbar.length; i++) {
                    if (arg0 == i) {
                        img_bt_actionbar[i].setSelected(true);
                    } else {
                        img_bt_actionbar[i].setSelected(false);
                    }
                }
            }

        });
        vp_main.setCurrentItem(1);
    }

    //主界面actionbar
    private void initActionBar() {
        // 得到ActionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#C62F2F")));

        // 隐藏标题文字和logo
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // 使用自定义的布局
        actionBar.setDisplayShowCustomEnabled(true);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.actionbar_main, null);
        actionBar.setCustomView(view);

        // 初始化ActionBar上的按钮
        int i;
        for (i = 0; i < 3; i++) {
            img_bt_actionbar[i] = (ImageView) findViewById(idButtonActionBar[i]);
            img_bt_actionbar[i].setOnClickListener(this);
            // 设置标签？？？？？？？？？
            img_bt_actionbar[i].setTag(i);
        }
        //在这里设置n号界面即Discover界面为初始界面
        img_bt_actionbar[1].setSelected(true);
    }

    //主界面actionbar点击事件
    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getTag();
        int i;
        for (i = 0; i < img_bt_actionbar.length; i++) {
            img_bt_actionbar[i].setSelected(false);
        }
        img_bt_actionbar[tag].setSelected(true);
        vp_main.setCurrentItem(tag);
        v.setSelected(true);
    }

    //最下方playbar
    //点击整个界面 跳转至音乐播放界面
    //列表按钮可以不实现  UNDONE
    //播放按钮监听
    //歌曲名称 歌手 TextView 没写
    private void initPlayBar(){
        //点击界面事件
        layout_pBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                intentToMusic = new Intent();
                intentToMusic.setClass(MainActivity.this, MusicActivity.class);
                // 将MainActivity的状态传给ListActivity和MainActivity
                mtoMusic.setMainisPlay(isPlay);
                mtoMusic.setSinger(tv_pSinger.getText().toString());
                mtoMusic.setTitle(tv_pMusic.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("MUSIC", mtoMusic);
                // 设置从MainActivity到MusicActivity需要的值
                intentToMusic.putExtras(bundle);
                startActivityForResult(intentToMusic, 1);
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);

            }
        });
        //播放按钮
        img_pPlayorpause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                intentConnService.putExtra("msg", "bofang");
                startService(intentConnService);
                if (!isPlay) {// 若播放器在播放的话
                    // mediaPlayer.start();
                    // handler.post(runnable);
                    img_pPlayorpause
                            .setImageResource(R.drawable.playbar_btn_pause);
                    isPlay = true;
                } else if (isPlay) {// 若播放器没有在播放的话
                    // mediaPlayer.pause();
                    // handler.removeCallbacks(runnable);
                    img_pPlayorpause
                            .setImageResource(R.drawable.playbar_btn_play);

                    isPlay = false;
                }

            }
        });
        //下一首歌
        img_pNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (current == musicInfo.size() - 1) {
                    current = 0;
                    tv_pSinger.setText(musicInfo.get(current).getArtist());
                    tv_pMusic.setText(musicInfo.get(current).getTitle());
                } else {
                    tv_pSinger.setText(musicInfo.get(current + 1).getArtist());
                    tv_pMusic.setText(musicInfo.get(current + 1).getTitle());
                }
                // 如果播放和下一首都没点击，则改变播放图标
                if (!isNext && !isPlay) {
                    img_pPlayorpause
                            .setImageResource(R.drawable.playbar_btn_pause);

                    isPlay = true;
                }
                intentConnService.putExtra("msg", "xiayishou");
                startService(intentConnService);

            }
        });
    }

    /**
     * 接收数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 1) {// 如果是MusicActivity传回来的

            mtoMusic = (MainToMusic) data.getSerializableExtra("MUSIC");
            isPlay = mtoMusic.isMainisPlay();
            tv_pMusic.setText(mtoMusic.getTitle());
            tv_pSinger.setText(mtoMusic.getSinger());
            current = mtoMusic.getCurrent();
        } else if (requestCode == 2) {// 如果是ListActivity传回来的
            mtoList = (ListToMain) data.getSerializableExtra("LIST");
            isPlay = mtoList.isListisPlay();
        }
        if (isPlay) {// 若播放器在播放的话
            // mediaPlayer.start();
            // handler.post(runnable);
            img_pPlayorpause.setImageResource(R.drawable.playbar_btn_pause);
            isPlay = true;
        } else {// 若播放器没有在播放的话
            // mediaPlayer.pause();
            // handler.removeCallbacks(runnable);
            img_pPlayorpause.setImageResource(R.drawable.playbar_btn_play);

            isPlay = false;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        stopService(intentConnService);// 当activity毁掉之后将service关闭
    }
}
