package com.lenso.jixiangbao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * Created by king on 2016/5/20.
 */
public class ScreenListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;

    public ScreenListAdapter(LayoutInflater inflater){
        this.inflater=inflater;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
