package com.lenso.jixiangbao.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.MyPagerAdapter;

import java.util.ArrayList;


/**
 * Created by Chung on 2016/5/11.
 */
public class LaunchActivity extends BaseActivity {
    private ViewPager launchViewPager;//引导页视图
    private ArrayList<View> viewList;//引导页子页
    private LinearLayout ll_launch;//引导页导航视图
    private ImageView[] dots;//引导页导航点
    private int currentDot;//当前导航点
    private MyPagerAdapter mPagerAdapter;//引导页适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        launchViewPager = (ViewPager) findViewById(R.id.vp_launch);
        ll_launch = (LinearLayout) findViewById(R.id.ll_launch);

        initPageView();//初始化引导页面
        initDotView();//初始化导航小圆点

    }

    /**
     * 初始化引导页面
     */
    private void initPageView(){
        LayoutInflater mLayoutInflater = getLayoutInflater();

        viewList = new ArrayList<>();
        /**
         * 填充引导页
         */
        viewList.add(mLayoutInflater.inflate(R.layout.launch_one, null, false));
        viewList.add(mLayoutInflater.inflate(R.layout.launch_two, null, false));
        viewList.add(mLayoutInflater.inflate(R.layout.launch_three, null, false));
        viewList.add(mLayoutInflater.inflate(R.layout.launch_four, null, false));
        viewList.add(mLayoutInflater.inflate(R.layout.launch_five, null, false));

        /**
         * 设置适配器
         */
        mPagerAdapter = new MyPagerAdapter(viewList);
        launchViewPager.setAdapter(mPagerAdapter);

        /**
         * 设置监听器
         */
        launchViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                setCurrentDot(i);//设置选中导航点状态
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }

    /**
     * 初始化导航点
     */
    private void initDotView() {
        dots = new ImageView[viewList.size()];

        for (int i = 0; i < viewList.size(); i++) {
            dots[i] = (ImageView) ll_launch.getChildAt(i);
            if(i == 0){
                dots[i].setEnabled(true);// 设为选中
            }else{
                dots[i].setEnabled(false);// 设为灰色
            }
        }
        currentDot = 0;
    }

    /**
     * 设置当前导航点
     * @param position ViewPager当前位置
     */
    private void setCurrentDot(int position) {
        dots[currentDot].setEnabled(false);//改变上一页状态
        dots[position].setEnabled(true);//设置当前页状态
        currentDot = position;//重设当前导航点位置
    }

}
