package com.geek_dream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.util.FunctionDrawer;

import java.util.List;

/**
 * Created by huangzhiyuan on 16/8/16.
 */
public class AdapterListViewDrawer extends BaseAdapter{

    List mList;
    Context mContext;


    public AdapterListViewDrawer(List mList, Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_drawer, null);
        ImageView img_func = (ImageView) convertView.findViewById(R.id.img_dfunc);
        TextView tv_func = (TextView) convertView.findViewById(R.id.tv_func);

        FunctionDrawer functionDrawer = (FunctionDrawer) mList.get(position);

        img_func.setImageResource(functionDrawer.getId_imgFun());
        tv_func.setText(functionDrawer.getName_tvFun());

        return convertView;
    }
}
