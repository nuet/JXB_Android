package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenso.jixiangbao.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Chung on 2016/5/12.
 */
public class LoanFragmentListViewAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public LoanFragmentListViewAdapter(List<Map<String, Object>> data, Context context) {
        this.data = data;
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LoanFragmentViewHolder mLoanFragmentViewHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.listitem_fragment_loan, null);
            mLoanFragmentViewHolder = new LoanFragmentViewHolder();
            mLoanFragmentViewHolder.iv_loanfragment_title = (ImageView) convertView.findViewById(R.id.iv_loanfragment_title);
            mLoanFragmentViewHolder.tv_loanfragment_title = (TextView) convertView.findViewById(R.id.tv_loanfragment_title);
            mLoanFragmentViewHolder.iv_loanfragment_arrow = (ImageView) convertView.findViewById(R.id.iv_loanfragment_arrow);
            mLoanFragmentViewHolder.tv_loanfragment_content = (TextView) convertView.findViewById(R.id.tv_loanfragment_content);
            convertView.setTag(mLoanFragmentViewHolder);
        }else{
            mLoanFragmentViewHolder = (LoanFragmentViewHolder) convertView.getTag();
        }

        mLoanFragmentViewHolder.iv_loanfragment_title.setImageResource((Integer) data.get(position).get("iv_loanfragment_title"));
        mLoanFragmentViewHolder.tv_loanfragment_title.setText((CharSequence) data.get(position).get("tv_loanfragment_title"));
        mLoanFragmentViewHolder.iv_loanfragment_arrow.setImageResource((Integer) data.get(position).get("iv_loanfragment_arrow"));
        mLoanFragmentViewHolder.tv_loanfragment_content.setText((CharSequence) data.get(position).get("tv_loanfragment_content"));

        return convertView;
    }
}
