package com.geek_dream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.musicbean.MusicList;
import com.geek_dream.musicbean.MusicManager;
import com.geek_dream.player.MainActivity;

import java.util.List;

/**
 * Created by huangzhiyuan on 16/8/17.
 */
public class AdapterListViewMusList extends BaseAdapter{
    List mList;
    Context mContext;

    public AdapterListViewMusList(List<MusicManager> mContext, MainActivity mList) {
        this.mContext = (Context) mContext;
        this.mList = (List) mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        arg1 = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_list, null);
        ImageView img_list = (ImageView) arg1.findViewById(R.id.img_music_list);
        TextView tv_list = (TextView) arg1.findViewById(R.id.tv_music_list);

        MusicList musicList = (MusicList) mList.get(arg0);

        img_list.setImageResource(musicList.getId_img());
        tv_list.setText(musicList.getName_list());



        return arg1;
    }
}
