package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.view.ScreenItemView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by king on 2016/5/20.
 */
public class ScreenListAdapter extends BaseAdapter {
    private final Context context;
    private final Map<String, String> map;
    private final Map<String, List<String>> data;
    private final List<String> keyList;

    public ScreenListAdapter(Context context, Map<String, List<String>> data) {
        this.context = context;
        this.map = new HashMap<>();
        this.data = data;
        this.keyList = new ArrayList<>();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            map.put(key, data.get(key).get(0) + ".1");
            keyList.add(key);
        }
    }

    public void resetScreenList() {
        for (String key : keyList) {
            if (map.get(key).endsWith(".1"))
                map.put(key, data.get(key).get(0) + ".1");
            else
                map.put(key, data.get(key).get(0) + ".0");
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
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
        if (convertView == null) {
            convertView = new ScreenItemView(context);
        }
        ((ScreenItemView) convertView).setScreenArrow(R.mipmap.xl);
        ((ScreenItemView) convertView).setOnScreenItemListener(listener);
        ((ScreenItemView) convertView).setTitleText(keyList.get(position));
        String info = map.get(keyList.get(position));
        String text = info.substring(0, info.lastIndexOf("."));
        boolean isUp;
        if (info.endsWith(".1"))
            isUp = true;
        else
            isUp = false;
        ((ScreenItemView) convertView).addItem(position, data.get(keyList.get(position)), text, isUp);
        return convertView;
    }

    private ScreenItemView.OnScreenItemListener listener = new ScreenItemView.OnScreenItemListener() {
        @Override
        public void onScreenItem(int position, String text, boolean isUp) {
            if (isUp) {
                map.put(keyList.get(position), text + ".1");
            } else {
                map.put(keyList.get(position), text + ".0");
            }
        }
    };
}
