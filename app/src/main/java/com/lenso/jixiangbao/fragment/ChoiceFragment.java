package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.LoopViewPager;
import com.lenso.jixiangbao.view.TopMenuBar;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/16.
 */
public class ChoiceFragment extends BaseFragment {
    @Bind(R.id.iv_info)
    ImageView ivInfo;
    @Bind(R.id.tv_info_2)
    TextView tvInfo2;
    @Bind(R.id.tv_info_1)
    TextView tvInfo1;
    @Bind(R.id.vp_choice)
    LoopViewPager vpChoice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        vpChoice.setDisplayMode(JViewPager.DisplayMode.DISPLAY_BY_FIRST_ONE);
        vpChoice.addLoopViews(new TestFragment(),new TestFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
