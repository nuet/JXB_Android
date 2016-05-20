package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;

import java.util.List;

/**
 * Created by king on 2016/5/13.
 */
public class CreditListAdapter extends BaseAdapter {
    private final List dataList;
    private final Context context;

    public CreditListAdapter(Context context, List dataList){
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
            holder.tv_list_item_title= (TextView) view.findViewById(R.id.tv_list_item_title);
            holder.ll_list_item_text= (LinearLayout) view.findViewById(R.id.ll_list_item_text);
            holder.tv_list_item_content_1= (TextView) view.findViewById(R.id.tv_list_item_content_1);
            holder.tv_list_item_content_2= (TextView) view.findViewById(R.id.tv_list_item_content_2);
            holder.tv_list_item_content_2_day= (TextView) view.findViewById(R.id.tv_list_item_content_2_day);
            holder.tv_list_item_title.setVisibility(View.GONE);
            holder.ll_list_item_text.setVisibility(View.GONE);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        return view;
    }
    private static class ViewHolder{
        private LinearLayout ll_list_item_text;
        private TextView tv_list_item_title;
        private TextView tv_list_item_text;
        private TextView tv_list_item_content;
        private TextView tv_list_item_content_1;
        private TextView tv_list_item_content_2;
        private TextView tv_list_item_content_2_day;
    }
}
