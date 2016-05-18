package com.lenso.jixiangbao.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by king on 2016/5/17.
 */
public class LoopViewPagerAdapter extends PagerAdapter {
    public LoopViewPagerAdapter(){

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return false;
    }
}
