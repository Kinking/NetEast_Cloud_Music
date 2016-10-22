package com.geek_dream.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by huangzhiyuan on 16/8/16.
 */
public class AdapterViewPageMain extends PagerAdapter {
    List mList;
    Context mContext;

    public AdapterViewPageMain(List mList, Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView( (View) mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View) mList.get(position));
    }
}
