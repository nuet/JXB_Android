package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.bean.AppScrollPic;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.LoopViewPager;
import com.lenso.jixiangbao.view.SpeakerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/16.
 */
public class ChoiceFragment extends BaseFragment {
    @Bind(R.id.iv_info)
    SpeakerView ivInfo;
    @Bind(R.id.tv_info_2)
    TextView tvInfo2;
    @Bind(R.id.tv_info_1)
    TextView tvInfo1;
    @Bind(R.id.vp_choice)
    LoopViewPager vpChoice;
    @Bind(R.id.lvp_banner)
    LoopViewPager lvpBanner;
    @Bind(R.id.ll_dot)
    LinearLayout llDot;
    @Bind(R.id.rl_info)
    RelativeLayout rlInfo;
    private List<ImageView> dots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        int padding = (int) getResources().getDimension(R.dimen.dp_5);
        if (App.BASE_BEAN.getStatistic_display().equals("0"))
            rlInfo.setVisibility(View.GONE);
        else
            rlInfo.setVisibility(View.VISIBLE);
        ArrayList<String> pics = new ArrayList<>();
        for (AppScrollPic pic : App.BASE_BEAN.getAppScrollPic()) {
            pics.add(App.HOST + pic.getPic());
        }
        lvpBanner.addLoopImageUrl(pics);
        lvpBanner.setLoopTimer(3000);
        lvpBanner.setOnLoopPagerChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            private void selected(int position) {
                for (ImageView imageView : dots) {
                    imageView.setSelected(false);
                }
                dots.get(position).setSelected(true);
            }
        });
        dots = new ArrayList<>();
        for (int i = 0; i < pics.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.selector_dot);
            dots.add(imageView);
            llDot.addView(imageView);
            imageView.setPadding(padding, padding, padding, padding);
        }
        dots.get(0).setSelected(true);
        vpChoice.setDisplayMode(JViewPager.DisplayMode.DISPLAY_BY_EVERY_ONE);
        vpChoice.setAdapter(new FragmentViewPageAdapter(getActivity().getSupportFragmentManager(), new ChoiceActionFragment(), new ChoiceActionFragment(), new ChoiceActionFragment()));
    }

    @Override
    public void onResume() {
        ivInfo.startSpeaker(300);
        if (lvpBanner != null)
            lvpBanner.openLoopTimer();
        super.onResume();
    }

    @Override
    public void onPause() {
        ivInfo.stopSpeaker();
        if (lvpBanner != null)
            lvpBanner.cancelLoopTimer();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
