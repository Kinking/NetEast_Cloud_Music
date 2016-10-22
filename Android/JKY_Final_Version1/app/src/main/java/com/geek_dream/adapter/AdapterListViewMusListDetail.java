package com.geek_dream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.musicbean.MusicDetail;

import java.util.List;

/**
 * Created by huangzhiyuan on 16/8/17.
 */
public class AdapterListViewMusListDetail extends BaseAdapter{
    List mList;
    Context mContext;

    public AdapterListViewMusListDetail(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public AdapterListViewMusListDetail(List mList, Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
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

        arg1 = LayoutInflater.from(mContext).inflate(
                R.layout.adapter_listview_detail_list, null);
        TextView tv_num = (TextView) arg1.findViewById(R.id.tv_list_detail_num);
        TextView tv_music = (TextView) arg1
                .findViewById(R.id.tv_list_detail_music);
        TextView tv_singer = (TextView) arg1
                .findViewById(R.id.tv_list_detail_singer);
        ImageView img_dld = (ImageView) arg1
                .findViewById(R.id.img_list_detail_ok);
        ImageView img_quality = (ImageView) arg1
                .findViewById(R.id.img_list_detail_quality);

        tv_num.setText((arg0 + 1) + "");// 设置右边序号

        MusicDetail musicDetail = (MusicDetail) mList.get(arg0);
        // 设置属性
        tv_music.setText(musicDetail.getMusic());
        tv_singer.setText(musicDetail.getSinger());





        if (musicDetail.isDld()) {
            img_dld.setImageResource(R.drawable.list_icn_dld_ok);
            switch (musicDetail.getHqOrsq()) {
                case "hq":
                    img_quality.setImageResource(R.drawable.list_icn_hq_sml);
                    break;
                case "sq":
                    img_quality.setImageResource(R.drawable.list_icn_sq_sml);
                    break;
                default:
                    break;
            }

        } else {
            img_dld.setVisibility(View.GONE);
            img_quality.setVisibility(View.GONE);

        }

        return arg1;
    }
}
