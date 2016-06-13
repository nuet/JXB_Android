package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.fragment.ChoiceFragment;
import com.lenso.jixiangbao.fragment.FinancingFragment;
import com.lenso.jixiangbao.fragment.MineFragment;
import com.lenso.jixiangbao.fragment.ScreenFragment;
import com.lenso.jixiangbao.fragment.WebViewFragment;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.MenuItemView;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import king.dominic.slidingmenu.SlidingMenu;

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
    private boolean moreOpen = false;
    private SlidingMenu menu;
    private boolean isFirst = true;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_home);
        ButterKnife.bind(this);
        initViewPager();
    }

    public SlidingMenu getSlidingMenu() {
        return menu;
    }

    private void select(int index) {
        if (index != 2) {
            moreOpen = false;
            menuItem1.setSelected(false);
            menuItem2.setSelected(false);
            menuItem3.setSelected(false);
            menuItem4.setSelected(false);
            currentItem = index;
        } else {
            moreOpen = true;
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
//                menuItem3.setSelected(true);
//                topMenuBar.setTitleText(res.getString(R.string.loan));
//                topMenuBar.setVisibility(View.VISIBLE);
//                topMenuBar.setBackVisibility(View.INVISIBLE);
//                topMenuBar.setMenuVisibility(View.VISIBLE);
                topMenuBar.setTitleText(res.getString(R.string.more));
                topMenuBar.setVisibility(View.VISIBLE);
                topMenuBar.setBackVisibility(View.VISIBLE);
                topMenuBar.setMenuVisibility(View.INVISIBLE);
                break;
            case 3:
                menuItem4.setSelected(true);
                topMenuBar.setVisibility(View.GONE);
                break;
            case 4:
//                topMenuBar.setTitleText(res.getString(R.string.more));
//                topMenuBar.setVisibility(View.VISIBLE);
//                topMenuBar.setBackVisibility(View.VISIBLE);
//                topMenuBar.setMenuVisibility(View.INVISIBLE);
                break;
        }
    }

    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidth(50);
        menu.setShadowDrawable(R.drawable.shadow);
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(outSize);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffset(outSize.x / 4);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.layout_sliding_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_sliding_menu, new ScreenFragment()).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && isFirst) {
            isFirst = false;
            initSlidingMenu();
            moreFragment.webViewLoader(HTMLInterface.GD);
        }
        super.onWindowFocusChanged(hasFocus);
    }


    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        moreFragment = new WebViewFragment();
        fragments.add(new ChoiceFragment());
        fragments.add(new FinancingFragment());
        fragments.add(moreFragment);
        mineFragment = new MineFragment();
        fragments.add(mineFragment);

        FragmentViewPageAdapter adapter = new FragmentViewPageAdapter(getSupportFragmentManager(), fragments);
        vpHome.setAdapter(adapter);
        vpHome.setScrollable(false);
        vpHome.setOffscreenPageLimit(5);
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putExtra(JSInterface.H5_TITLE, "吉车贷");
                intent.putExtra(JSInterface.H5_URL, HTMLInterface.JI_CHE_DAI);
                intent.putExtra("intent", JSInterface.JI_CHE_DAI);
                startActivity(intent);
            }
        });
        menuItem4.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String app_key = Config.getInstance(HomeActivity.this).getConfig("app_key");
                if (app_key == null || app_key.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    builder.setTitle("温馨提示");
                    builder.setMessage("系统检测到您尚未登录，是否前往登录页进行登录？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentLogin = new Intent();
                            intentLogin.setClass(HomeActivity.this, LoginOrRegisterActivity.class);
                            startActivity(intentLogin);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                } else {
                    mineFragment.initData();
                    vpHome.setCurrentItem(3);
                }
            }
        });
        topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpHome.setCurrentItem(2);
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
        if (moreOpen) {
            if (vpHome != null)
                vpHome.setCurrentItem(currentItem);
            return;
        }
        super.onBackPressed();
    }

}