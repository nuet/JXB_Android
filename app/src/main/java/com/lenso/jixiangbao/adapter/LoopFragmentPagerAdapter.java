package com.lenso.jixiangbao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by king on 2016/5/17.
 */
public class LoopFragmentPagerAdapter extends FragmentPagerAdapter {
    private final Fragment[] fragments;
    private final ViewPager pager;
    private int preItem=-1;

    public LoopFragmentPagerAdapter(FragmentManager fm, ViewPager pager, Fragment... fragments) {
        super(fm);
        this.fragments = fragments;
        this.pager=pager;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        Log.d("jgm","position1:"+position);
        if (position == 0)
            position = fragments.length-1;
        else if (position == fragments.length + 1)
            position = 0;
        else
            position--;
        Log.d("jgm","position2:"+position+" "+pager.getCurrentItem());
        if(getItem(position).isAdded())
            return getItem(position);
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return fragments.length + 2;
    }
}
