package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.bean.ChoiceList;
import com.lenso.jixiangbao.view.ProgressWheel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/13.
 */
public class CreditListAdapter extends BaseAdapter {
    private final List<ChoiceList> dataList;
    private final Context context;

    public CreditListAdapter(Context context, List<ChoiceList> dataList) {
        this.context = context;
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
        ChoiceList data = dataList.get(i);
        holder.tvListItemTextOne.setText(data.getName());
        holder.tvListItemContentOne.setText(new StringBuffer().append("总额：").append(Integer.parseInt(data.getAccount())/10000f).append("万"));
        holder.tvListItemContent1.setText(data.getApr());
        if(data.getIsday().equals("0")) {
            holder.tvListItemContent2.setText(data.getTime_limit());
            holder.tvListItemContent2Day.setText(R.string.month);
        }
        if(data.getIsday().equals("1")){
            holder.tvListItemContent2.setText(data.getTime_limit_day());
            holder.tvListItemContent2Day.setText(R.string.day);
        }
        float progress=(1-Float.parseFloat(data.getAccount_yes())/Float.parseFloat(data.getAccount()))*360;
        if(progress==360)
            holder.pwListItem.setText(context.getString(R.string.sell_out));
        else
            holder.pwListItem.setText(context.getString(R.string.buy));
        holder.pwListItem.setProgress((int) progress);
        holder.pwListItem.setOnClickListener(new BuyOnClickListener(data.getId()));
        return view;
    }

    class BuyOnClickListener implements View.OnClickListener{
        private final String id;

        public  BuyOnClickListener(String id){
            this.id=id;
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, WebViewActivity.class);
            intent.putExtra(JSInterface.H5_URL, HTMLInterface.DETAIL+"?id="+id);
            context.startActivity(intent);
        }
    }
    static class ViewHolder {
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
