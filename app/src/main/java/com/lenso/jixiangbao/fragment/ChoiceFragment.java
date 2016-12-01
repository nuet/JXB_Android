package com.lenso.jixiangbao.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.AppScrollPic;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.view.ChoiceLoopViewPager;
import com.lenso.jixiangbao.view.LoopViewPager;
import com.lenso.jixiangbao.view.SpeakerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by king on 2016/5/16.
 */
public class ChoiceFragment extends BaseFragment {
    @Bind(R.id.iv_info)
    SpeakerView ivInfo;
    @Bind(R.id.tv_info)
    TextView tvInfo;
    @Bind(R.id.vp_choice)
    ChoiceLoopViewPager vpChoice;
    @Bind(R.id.lvp_banner)
    LoopViewPager lvpBanner;
    @Bind(R.id.ll_dot)
    LinearLayout llDot;
    @Bind(R.id.rl_info)
    RelativeLayout rlInfo;
    @Bind(R.id.pull_refresh_scrollview)
    PullToRefreshScrollView pullRefreshScrollview;
    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.image2)
    ImageView image2;
    @Bind(R.id.image3)
    ImageView image3;
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.text3)
    TextView text3;
    private List<ImageView> dots;
    private float dp_280;

    private ChoiceActionFragment choiceActionFragment1 = new ChoiceActionFragment();
    private ChoiceActionFragment choiceActionFragment2 = new ChoiceActionFragment();
    private ChoiceActionFragment choiceActionFragment3 = new ChoiceActionFragment();
    private ChoiceActionFragment choiceActionFragment4 = new ChoiceActionFragment();
    private ChoiceActionFragment choiceActionFragment5 = new ChoiceActionFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice, null);
        ButterKnife.bind(this, view);
        logInfo("bug choice created");
        dp_280 = getResources().getDimension(R.dimen.dp_280);
        initRefreshList();
        initView();
        return view;
    }

    private void initRefreshList() {
        LoadingLayoutProxy top = (LoadingLayoutProxy) pullRefreshScrollview.getLoadingLayoutProxy(true, false);
        top.setPullLabel("下拉即可刷新...");
        top.setRefreshingLabel("刷新中...");
        top.setReleaseLabel("释放即可刷新...");
        top.setLoadingDrawable(getActivity().getResources().getDrawable(R.mipmap.sx2));
        pullRefreshScrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (!isFastPull()) {
                    CommonUtils commonUtils = new CommonUtils();
                    commonUtils.loadValues();
                    if (getActivity() != null) {
                        ((HomeActivity) getActivity()).initViewPager();
                    }
                }
            }
        });
    }

    private void initView() {
        logInfo("bug choice initView");
        if (App.BASE_BEAN == null || App.THREE_CHOICE == null || App.BASE_BEAN.getStatistic_display() == null || App.BASE_BEAN.getAppScrollPic() == null) {
            return;
        } else {
            int padding = (int) getResources().getDimension(R.dimen.dp_2);

            if (App.THREE_CHOICE.getThreeChoice().get(0).getIsday().equals("1")) {
                text1.setText(App.THREE_CHOICE.getThreeChoice().get(0).getTime_limit_day() + "天");
            } else {
                text1.setText(App.THREE_CHOICE.getThreeChoice().get(0).getTime_limit() + "个月");
            }
            if (App.THREE_CHOICE.getThreeChoice().get(0).getIsday().equals("1")) {
                text2.setText(App.THREE_CHOICE.getThreeChoice().get(1).getTime_limit_day() + "天");
            } else {
                text2.setText(App.THREE_CHOICE.getThreeChoice().get(1).getTime_limit() + "个月");
            }
            if (App.THREE_CHOICE.getThreeChoice().get(0).getIsday().equals("1")) {
                text3.setText(App.THREE_CHOICE.getThreeChoice().get(2).getTime_limit_day() + "天");
            } else {
                text3.setText(App.THREE_CHOICE.getThreeChoice().get(2).getTime_limit() + "个月");
            }


            if (App.BASE_BEAN.getStatistic_display().equals("0")) {
                rlInfo.setVisibility(View.GONE);
            } else {
                rlInfo.setVisibility(View.VISIBLE);
                tvInfo.setText(App.BASE_BEAN.getNotice_txt());
//                tvInfo2.setText(App.BASE_BEAN.getPlatformFinancialReport().getTender_total());
//                tvInfo4.setText(App.BASE_BEAN.getPlatformFinancialReport().getNew_user_total());
            }

            for (int i = 0; i < App.BASE_BEAN.getAppScrollPic().size(); i++) {
                App.BASE_BEAN.getAppScrollPic().get(i).setPic(ServerInterface.SERVER +
                        App.BASE_BEAN.getAppScrollPic().get(i).getPic());
            }

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

                private void selected(final int position) {
                    for (ImageView imageView : dots) {
                        imageView.setSelected(false);
                    }
                    dots.get(position).setSelected(true);
                }
            });
            dots = new ArrayList<>();
            for (int i = 0; i < App.BASE_BEAN.getAppScrollPic().size(); i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.drawable.selector_dot);
                dots.add(imageView);
                llDot.addView(imageView);
                imageView.setPadding(padding, padding, padding, padding);
            }
            dots.get(0).setSelected(true);
            Point outSize = new Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(outSize);

            int height = 3 * outSize.x / 8;
            logDebug("height:" + String.valueOf(height));
            FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            lvpBanner.setLayoutParams(lp1);

            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (outSize.y - dp_280));
            vpChoice.setLayoutParams(lp2);

            choiceActionFragment1.setAPR(App.THREE_CHOICE.getThreeChoice().get(2).getApr() + "%");
            choiceActionFragment1.setOnClickEvent(2);
            choiceActionFragment2.setAPR(App.THREE_CHOICE.getThreeChoice().get(0).getApr() + "%");
            choiceActionFragment2.setOnClickEvent(0);
            choiceActionFragment3.setAPR(App.THREE_CHOICE.getThreeChoice().get(1).getApr() + "%");
            choiceActionFragment3.setOnClickEvent(1);
            choiceActionFragment4.setAPR(App.THREE_CHOICE.getThreeChoice().get(2).getApr() + "%");
            choiceActionFragment4.setOnClickEvent(2);
            choiceActionFragment5.setAPR(App.THREE_CHOICE.getThreeChoice().get(0).getApr() + "%");
            choiceActionFragment5.setOnClickEvent(0);

            vpChoice.addLoopFragment(choiceActionFragment1, choiceActionFragment2, choiceActionFragment3, choiceActionFragment4, choiceActionFragment5);

            vpChoice.setOnLoopPagerChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    selectImage(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            selectImage(0);
        }

    }

    private void selectImage(int position) {
        image1.setSelected(false);
        image2.setSelected(false);
        image3.setSelected(false);
        text1.setSelected(false);
        text2.setSelected(false);
        text3.setSelected(false);
        switch (position) {
            case 0:
                image1.setSelected(true);
                text1.setSelected(true);
                break;
            case 1:
                image2.setSelected(true);
                text2.setSelected(true);
                break;
            case 2:
                image3.setSelected(true);
                text3.setSelected(true);
                break;
        }
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
        logInfo("bug choice pause");
        ivInfo.stopSpeaker();
        if (lvpBanner != null)
            lvpBanner.cancelLoopTimer();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        logInfo("bug choice destroy");
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_info)
    public void onClick() {
        if (CommonUtils.isNetworkConnected(getActivity())) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), WebViewActivity.class);
            intent.putExtra(HTMLInterface.H5_URL, App.BASE_BEAN.getNotice_url());
            intent.putExtra(HTMLInterface.H5_TITLE, "最新公告");
            startActivity(intent);
        } else {
            showToast(getString(R.string.no_internet));
        }
    }

    private static long lastClickTime;

    private synchronized static boolean isFastPull() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
