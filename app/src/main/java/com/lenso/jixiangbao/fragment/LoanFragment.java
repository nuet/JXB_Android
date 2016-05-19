package com.lenso.jixiangbao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.adapter.LoanFragmentListViewAdapter;
import com.lenso.jixiangbao.api.HTMLInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/11.
 */
public class LoanFragment extends BaseFragment {
    @Bind(R.id.lv_loanfragment)
    ListView lvLoanfragment;

    private LoanFragmentListViewAdapter mLoanFragmentListViewAdapter;
    private List<Map<String, Object>> data;
    private HashMap<String, Object> map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan, null);
        ButterKnife.bind(this, view);

        data = new ArrayList<>();

        map = new HashMap<>();
        map.put("iv_loanfragment_title", R.mipmap.loanfragment_jfangd);
        map.put("tv_loanfragment_title", "吉房贷");
        map.put("iv_loanfragment_arrow", R.mipmap.loanfragment_rightarrow);
        SpannableString ss1 = new SpannableString("有房贷就能借  无需押房，无需担保，幸福生活随房“贷”而来，最高可借50万");
        ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#EE8887")), 0, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        map.put("tv_loanfragment_content", ss1);
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title", R.mipmap.loanfragment_jxued);
        map.put("tv_loanfragment_title", "吉学贷");
        map.put("iv_loanfragment_arrow", R.mipmap.loanfragment_rightarrow);
        SpannableString ss2 = new SpannableString("大学生专享  剁手党 hold 不住？没有关系！我们“借”你来买！花钱变轻松，叫我活雷锋！");
        ss2.setSpan(new ForegroundColorSpan(Color.parseColor("#EE8887")), 0, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        map.put("tv_loanfragment_content", ss2);
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title", R.mipmap.loanfragment_jched);
        map.put("tv_loanfragment_title", "吉车贷");
        map.put("iv_loanfragment_arrow", R.mipmap.loanfragment_rightarrow);
        SpannableString ss3 = new SpannableString("有车贷就能借  不押车，不押证，有车辆交强险保单即可借");
        ss3.setSpan(new ForegroundColorSpan(Color.parseColor("#EE8887")), 0, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        map.put("tv_loanfragment_content", ss3);
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title", R.mipmap.loanfragment_jyingd);
        map.put("tv_loanfragment_title", "吉英贷");
        map.put("iv_loanfragment_arrow", R.mipmap.loanfragment_rightarrow);
        SpannableString ss4 = new SpannableString("优质收入人群专享  面向公务员、外企、上市公司工作人员");
        ss4.setSpan(new ForegroundColorSpan(Color.parseColor("#EE8887")), 0, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        map.put("tv_loanfragment_content", ss4);
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title", R.mipmap.loanfragment_jxind);
        map.put("tv_loanfragment_title", "吉薪贷");
        map.put("iv_loanfragment_arrow", R.mipmap.loanfragment_rightarrow);
        SpannableString ss5 = new SpannableString("有稳定工资收入就能借  提供社保公积金信息即可申请，最高可借50万");
        ss5.setSpan(new ForegroundColorSpan(Color.parseColor("#EE8887")), 0, 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        map.put("tv_loanfragment_content", ss5);
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title", R.mipmap.loanfragment_jweid);
        map.put("tv_loanfragment_title", "吉微贷");
        map.put("iv_loanfragment_arrow", R.mipmap.loanfragment_rightarrow);
        SpannableString ss6 = new SpannableString("合法经营的小微企业就能借  免抵押，免担保，期限灵活，利率优惠");
        ss6.setSpan(new ForegroundColorSpan(Color.parseColor("#EE8887")), 0, 12, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        map.put("tv_loanfragment_content", ss6);
        data.add(map);

        logInfo(data.toString());

        lvLoanfragment.addFooterView(inflater.inflate(R.layout.listitem_footer_fragment_loan, null));
        mLoanFragmentListViewAdapter = new LoanFragmentListViewAdapter(data, getActivity());
        lvLoanfragment.setAdapter(mLoanFragmentListViewAdapter);

        lvLoanfragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_loanfragment_title = (TextView) view.findViewById(R.id.tv_loanfragment_title);
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.LOAN);
                intent.putExtra(HTMLInterface.H5_TITLE, tv_loanfragment_title.getText());
                startActivity(intent);
            }
        });

        return view;
    }

}
