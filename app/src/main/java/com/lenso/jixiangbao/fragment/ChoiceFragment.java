package com.lenso.jixiangbao.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.adapter.LoopViewPagerAdapter;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.LoopViewPager;

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
    @Bind(R.id.lvp_banner)
    LoopViewPager lvpBanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        String[] urls=new String[]{"http://cdn.duitang.com/uploads/item/201510/11/20151011195344_aVZRx.jpeg","http://pic43.nipic.com/20140704/11284670_150403334001_2.jpg","http://att2.citysbs.com/fz/bbs_attachments/2010/month_1002/10020312250ba11460cb93cf95.jpg"};
        lvpBanner.addLoopImageUrl(urls);
        lvpBanner.setLoopTimer(3000);
        vpChoice.setAdapter(new FragmentViewPageAdapter(getActivity().getSupportFragmentManager(), new TestFragment(), new TestFragment(), new TestFragment(), new TestFragment(), new TestFragment()));
    }

    @Override
    public void onPause() {
        if (lvpBanner!=null)
            lvpBanner.cancelLoopTimer();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
