package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lenso.jixiangbao.R;

import java.util.List;

/**
 * Created by king on 2016/5/13.
 */
public class PreferredFinanceListAdapter extends BaseAdapter {
    private final List dataList;
    private final Context context;

    public PreferredFinanceListAdapter(Context context, List dataList){
        this.context=context;
        this.dataList=dataList;
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            holder=new ViewHolder();
            view=View.inflate(context, R.layout.item_preferredfinance,null);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        return view;
    }
    private static class ViewHolder{

    }
}
