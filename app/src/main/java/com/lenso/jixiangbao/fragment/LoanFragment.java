package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.LoanFragmentListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        map.put("iv_loanfragment_title",R.mipmap.loanfragment_jfangd);
        map.put("tv_loanfragment_title","吉房贷");
        map.put("iv_loanfragment_arrow",R.mipmap.loanfragment_rightarrow);
        map.put("tv_loanfragment_content","吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷");
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title",R.mipmap.loanfragment_jxued);
        map.put("tv_loanfragment_title","吉学贷");
        map.put("iv_loanfragment_arrow",R.mipmap.loanfragment_rightarrow);
        map.put("tv_loanfragment_content","吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷");
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title",R.mipmap.loanfragment_jched);
        map.put("tv_loanfragment_title","吉车贷");
        map.put("iv_loanfragment_arrow",R.mipmap.loanfragment_rightarrow);
        map.put("tv_loanfragment_content","吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷");
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title",R.mipmap.loanfragment_jyingd);
        map.put("tv_loanfragment_title","吉英贷");
        map.put("iv_loanfragment_arrow",R.mipmap.loanfragment_rightarrow);
        map.put("tv_loanfragment_content","吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷");
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title",R.mipmap.loanfragment_jxind );
        map.put("tv_loanfragment_title","吉薪贷");
        map.put("iv_loanfragment_arrow",R.mipmap.loanfragment_rightarrow);
        map.put("tv_loanfragment_content","吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷");
        data.add(map);

        map = new HashMap<>();
        map.put("iv_loanfragment_title",R.mipmap.loanfragment_jweid);
        map.put("tv_loanfragment_title","吉微贷");
        map.put("iv_loanfragment_arrow",R.mipmap.loanfragment_rightarrow);
        map.put("tv_loanfragment_content","吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷吉房贷");
        data.add(map);

        logInfo(data.toString());

        lvLoanfragment.addFooterView(inflater.inflate(R.layout.listitem_footer_fragment_loan, null));
        mLoanFragmentListViewAdapter = new LoanFragmentListViewAdapter(data, getActivity());
        lvLoanfragment.setAdapter(mLoanFragmentListViewAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
