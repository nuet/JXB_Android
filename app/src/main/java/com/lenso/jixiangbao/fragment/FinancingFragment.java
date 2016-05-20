package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/12.
 */
public class FinancingFragment extends BaseFragment {
    @Bind(R.id.tv_menu_preferred_finance)
    TextView tvMenuPreferredFinance;
    @Bind(R.id.tv_menu_credit_list)
    TextView tvMenuCreditList;
    @Bind(R.id.tv_menu_transfer_zone)
    TextView tvMenuTransferZone;
    @Bind(R.id.jvp_finance)
    JViewPager jvpFinance;
    private FragmentViewPageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financing, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if(adapter==null) {
            List<Fragment> fragmentList = new ArrayList<>();
            fragmentList.add(new PreferredFinanceFragment());
            fragmentList.add(new CreditListFragment());
            fragmentList.add(new CreditListFragment());
            adapter = new FragmentViewPageAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        }
        jvpFinance.setAdapter(adapter);
        tvMenuPreferredFinance.setSelected(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void unSelected() {
        tvMenuCreditList.setSelected(false);
        tvMenuPreferredFinance.setSelected(false);
        tvMenuTransferZone.setSelected(false);
    }

    @OnClick({R.id.tv_menu_preferred_finance, R.id.tv_menu_credit_list, R.id.tv_menu_transfer_zone})
    public void onClick(View view) {
        int position = 0;
        switch (view.getId()) {
            case R.id.tv_menu_preferred_finance:
                position = 0;
                break;
            case R.id.tv_menu_credit_list:
                position = 1;
                break;
            case R.id.tv_menu_transfer_zone:
                position = 2;
                break;
        }
        jvpFinance.setCurrentItem(position, false);
        unSelected();
        view.setSelected(true);
    }
}
