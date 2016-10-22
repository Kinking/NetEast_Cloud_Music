package com.geek_dream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.musicbean.MusicManager;
import com.geek_dream.player.MainActivity;

import java.util.List;

/**
 * Created by huangzhiyuan on 16/8/17.
 */
public class AdapterListViewMusManager extends BaseAdapter{

    List mList;
    Context mContext;


    public AdapterListViewMusManager(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        arg1 = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_manager, null);
        ImageView img_manager = (ImageView) arg1.findViewById(R.id.img_music_manager);
        TextView tv_manager = (TextView) arg1.findViewById(R.id.tv_music_manager);
        TextView tv_num = (TextView) arg1.findViewById(R.id.tv_num_music_manager);

        MusicManager musicManager = (MusicManager) mList.get(arg0);

        img_manager.setImageResource(musicManager.getId_img());
        tv_manager.setText(musicManager.getFunction());
        tv_num.setText(musicManager.getNum());


        return arg1;
    }



}
