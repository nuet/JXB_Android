package com.lenso.jixiangbao.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.fragment.ChoiceFragment;
import com.lenso.jixiangbao.fragment.FinancingFragment;
import com.lenso.jixiangbao.fragment.LoanFragment;
import com.lenso.jixiangbao.fragment.MineFragment;
import com.lenso.jixiangbao.fragment.TestFragment;
import com.lenso.jixiangbao.fragment.WebViewFragment;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.MenuItemView;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/10.
 */
public class HomeActivity extends BaseActivity {
    @Bind(R.id.menu_item_1)
    MenuItemView menuItem1;
    @Bind(R.id.menu_item_2)
    MenuItemView menuItem2;
    @Bind(R.id.menu_item_3)
    MenuItemView menuItem3;
    @Bind(R.id.menu_item_4)
    MenuItemView menuItem4;
    @Bind(R.id.ll_home)
    LinearLayout llHome;
    @Bind(R.id.vp_home)
    JViewPager vpHome;
    @Bind(R.id.top_menu_bar)
    TopMenuBar topMenuBar;
    private WebViewFragment moreFragment;
    private int currentItem;
    private boolean moreOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_home);
        ButterKnife.bind(this);
        initView();
    }

    private void select(int index) {
        if (index <= 3) {
            moreOpen=false;
            menuItem1.setSelected(false);
            menuItem2.setSelected(false);
            menuItem3.setSelected(false);
            menuItem4.setSelected(false);
            currentItem = index;
        }else{
            moreOpen=true;
        }
        Resources res = getResources();
        switch (index) {
            case 0:
                menuItem1.setSelected(true);
                topMenuBar.setTitleText(res.getString(R.string.choice));
                topMenuBar.setVisibility(View.VISIBLE);
                topMenuBar.setBackVisibility(View.INVISIBLE);
                topMenuBar.setMenuVisibility(View.VISIBLE);
                break;
            case 1:
                menuItem2.setSelected(true);
                topMenuBar.setTitleText(res.getString(R.string.financing));
                topMenuBar.setVisibility(View.VISIBLE);
                topMenuBar.setBackVisibility(View.INVISIBLE);
                topMenuBar.setMenuVisibility(View.VISIBLE);
                break;
            case 2:
                menuItem3.setSelected(true);
                topMenuBar.setTitleText(res.getString(R.string.loan));
                topMenuBar.setVisibility(View.VISIBLE);
                topMenuBar.setBackVisibility(View.INVISIBLE);
                topMenuBar.setMenuVisibility(View.VISIBLE);
                break;
            case 3:
                menuItem4.setSelected(true);
                topMenuBar.setVisibility(View.GONE);
                break;
            case 4:
                topMenuBar.setTitleText(res.getString(R.string.more));
                topMenuBar.setVisibility(View.VISIBLE);
                topMenuBar.setBackVisibility(View.VISIBLE);
                topMenuBar.setMenuVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus)
            moreFragment.webViewLoader("file:///android_asset/more.html");
        super.onWindowFocusChanged(hasFocus);
    }

    private void initView() {
        List<Fragment> fragments = new ArrayList<>();
        moreFragment = new WebViewFragment();
        fragments.add(new ChoiceFragment());
        fragments.add(new FinancingFragment());
        fragments.add(new LoanFragment());
        fragments.add(new MineFragment());
        fragments.add(moreFragment);

        FragmentViewPageAdapter adapter = new FragmentViewPageAdapter(getSupportFragmentManager(), fragments);
        vpHome.setAdapter(adapter);
        vpHome.setScrollable(false);
        vpHome.setOffscreenPageLimit(5);
        vpHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                select(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        select(0);
        menuItem1.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(0);
            }
        });
        menuItem2.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(1);
            }
        });
        menuItem3.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(2);
            }
        });
        menuItem4.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(3);
            }
        });
        topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpHome.setCurrentItem(4);
            }
        });
        topMenuBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpHome.setCurrentItem(currentItem);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(moreOpen){
            if(vpHome!=null)
                vpHome.setCurrentItem(currentItem);
            return;
        }
        super.onBackPressed();
    }
}