package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.bean.TransferList;
import com.lenso.jixiangbao.view.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chung on 2016/7/14.
 */
public class TransferListAdapter extends BaseAdapter {
    private final List<TransferList> dataList;
    private final Context context;

    public TransferListAdapter(Context context, List<TransferList> dataList) {
        this.context = context;
        if (dataList == null)
            this.dataList = new ArrayList<>();
        else
            this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
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
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_preferredfinance, null);
            holder = new ViewHolder(view);
            holder.tvListItemTitle.setVisibility(View.GONE);
            holder.llListItemText.setVisibility(View.GONE);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        TransferList data = dataList.get(i);
        holder.tvListItemTextOne.setText(data.getName());
        holder.tvListItemContentOne.setText(new StringBuffer().append(Integer.parseInt(data.getPrice()) / 10000f).append("万"));
        holder.tvListItemContent1.setText(data.getApr());
        holder.tvListItemContent2.setText(data.getTime_limit());
        holder.tvListItemContent2Day.setText(R.string.month);

        float progress = (1 - Float.parseFloat(data.getSaled()) / Float.parseFloat(data.getPrice())) * 360;
        if (data.getSaled().equals(data.getPrice())) {
            holder.pwListItem.setText(context.getString(R.string.sell_out));
        } else {
            holder.pwListItem.setText(context.getString(R.string.buy));
        }
        holder.pwListItem.setProgress((int) progress);
        return view;
    }

    public TransferList getListItem(int position) {
        return dataList.get(position);
    }

    static class ViewHolder {
        @Bind(R.id.ll_item_preferredfinance)
        LinearLayout llItemPreferredfinance;
        @Bind(R.id.tv_list_item_title)
        TextView tvListItemTitle;
        @Bind(R.id.tv_list_item_text_one)
        TextView tvListItemTextOne;
        @Bind(R.id.tv_list_item_content_one)
        TextView tvListItemContentOne;
        @Bind(R.id.ll_list_item_text2)
        LinearLayout llListItemText2;
        @Bind(R.id.tv_list_item_text_two)
        TextView tvListItemTextTwo;
        @Bind(R.id.tv_list_item_content_two)
        TextView tvListItemContentTwo;
        @Bind(R.id.ll_list_item_text)
        LinearLayout llListItemText;
        @Bind(R.id.tv_list_item_content_1)
        TextView tvListItemContent1;
        @Bind(R.id.tv_list_item_content_1_percent)
        TextView tvListItemContent1Percent;
        @Bind(R.id.tv_list_item_content_2)
        TextView tvListItemContent2;
        @Bind(R.id.tv_list_item_content_2_day)
        TextView tvListItemContent2Day;
        @Bind(R.id.pw_list_item)
        ProgressWheel pwListItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
