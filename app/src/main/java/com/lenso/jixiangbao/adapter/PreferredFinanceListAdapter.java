package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.bean.ChoiceList;
import com.lenso.jixiangbao.view.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/13.
 */
public class PreferredFinanceListAdapter extends BaseAdapter {
    private final List<ChoiceList> dataList;
    private final Context context;
    private final int xinListCount;
    private final int sanListCount;

    public PreferredFinanceListAdapter(Context context, BaseBean data) {
        this.context = context;
        this.dataList = new ArrayList<>();
        //------------新手专享------------------
        if (data.getXinList() != null)
            this.xinListCount = data.getXinList().size();
        else
            this.xinListCount = 0;
        if (xinListCount > 0) {
            dataList.addAll(data.getXinList());
        }
        //------------体验专区------------------
        dataList.add(new ChoiceList(context.getString(R.string.example_list_text),"1","1",Float.parseFloat(data.getNew_experience_apr())*100+"","0",data.getNew_experience_valid_time(),"1","0"));
        //------------散标专区------------------
        if (data.getSanList() != null)
            this.sanListCount = data.getSanList().size();
        else
            this.sanListCount = 0;
        if (sanListCount > 0) {
            dataList.addAll(data.getSanList());
        }

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
        ViewHolder holder ;
        if (view == null) {
            view = View.inflate(context, R.layout.item_preferredfinance, null);
            holder = new ViewHolder(view);
            holder.llListItemText2.setVisibility(View.GONE);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        setData(holder,i);
        if (i < xinListCount - 1) {
            setTitle(holder,i,0,R.string.xin_list_title);
        }
        if(i==xinListCount){
            setTitle(holder,i,xinListCount,R.string.example_list_title);
        }
        if(i>xinListCount){
            setTitle(holder,i,xinListCount+1,R.string.san_list_title);
        }
        return view;
    }
    private void setTitle(ViewHolder holder, int position, int index, @StringRes int resId){
        if (position == index) {
            holder.tvListItemTitle.setText(context.getString(resId));
            holder.tvListItemTitle.setVisibility(View.VISIBLE);
        } else
            holder.tvListItemTitle.setVisibility(View.GONE);
        switch (resId){
            case R.string.xin_list_title:
                String text=dataList.get(position).getTime_limit_day()+"天尝鲜"+dataList.get(position).getApr()+"%预期年化收益";
                holder.tvListItemContentTwo.setText(text);
                holder.tvListItemContentTwo.setGravity(Gravity.LEFT);
                break;
            case R.string.example_list_title:
                holder.tvListItemContentTwo.setText("我出本金，你拿收益");
                holder.tvListItemContentTwo.setGravity(Gravity.LEFT);
                break;
            case R.string.san_list_title:
                holder.tvListItemContentTwo.setGravity(Gravity.RIGHT);
                break;
        }
    }
    private void setData(ViewHolder holder, int position){
        ChoiceList data = dataList.get(position);
        holder.tvListItemTextTwo.setText(data.getName());
        String text =context.getString(R.string.tender_amount)+Float.parseFloat(dataList.get(position).getAccount())/10000+context.getString(R.string.wan);
        holder.tvListItemContentTwo.setText(text);
        holder.tvListItemContent1.setText(data.getApr());
        if (data.getIsday().equals("0")){
            holder.tvListItemContent2.setText(data.getTime_limit());
            holder.tvListItemContent2Day.setText(R.string.month);
        }
        if (data.getIsday().equals("1")){
            holder.tvListItemContent2.setText(data.getTime_limit_day());
            holder.tvListItemContent2Day.setText(R.string.day);
        }
        float progress=(1-Float.parseFloat(data.getAccount_yes())/Float.parseFloat(data.getAccount()))*360;
        if(progress==360)
            holder.pwListItem.setText(context.getString(R.string.sell_out));
        else
            holder.pwListItem.setText(context.getString(R.string.buy));
        holder.pwListItem.setProgress((int) progress);
    }
    static class ViewHolder {
        @Bind(R.id.tv_list_item_title)
        TextView tvListItemTitle;
        @Bind(R.id.iv_list_item_type)
        ImageView ivListItemType;
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
