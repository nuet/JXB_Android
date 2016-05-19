package com.lenso.jixiangbao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 2016/5/11.
 */
public class FragmentViewPageAdapter extends FragmentStatePagerAdapter{
    private final List<Fragment> fragmentList;

    public FragmentViewPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }
    public FragmentViewPageAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        fragmentList= new ArrayList<>();
        for(Fragment f:fragments){
            fragmentList.add(f);
        }
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
