package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.AppScrollPic;
import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.bean.ChoiceList;
import com.lenso.jixiangbao.fragment.ChoiceFragment;
import com.lenso.jixiangbao.fragment.CreditListFragment;
import com.lenso.jixiangbao.fragment.FinancingFragment;
import com.lenso.jixiangbao.fragment.LoanFragment;
import com.lenso.jixiangbao.fragment.MineFragment;
import com.lenso.jixiangbao.fragment.ScreenFragment;
//import com.lenso.jixiangbao.fragment.WebViewFragment;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.MenuItemView;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
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

    public static Activity HOMECONTEXT;
    private int currentItem;
    private boolean moreOpen = false;
    private SlidingMenu menu;
    private boolean isFirst = true;
    private ChoiceFragment choiceFragment;
    private FinancingFragment financingFragment;
    private LoanFragment loanFragment;
    private MineFragment mineFragment;

    private long backTime = 0;

    private Gson gson = new Gson();
    private List<AppScrollPic> picList;
    private int loadCount = 0;
    private List<ChoiceList> borrowList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_home);
        ButterKnife.bind(this);
        topMenuBar.setMenuTopPadding(statusHeight);
        HOMECONTEXT = HomeActivity.this;

        ShareSDK.initSDK(this);//初始化shareSDK

        load();

    }

    public SlidingMenu getSlidingMenu() {
        return menu;
    }

    private void select(int index) {
        if (index != 4) {
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
                topMenuBar.setMenuSrc(R.mipmap.top_menu_more);
                topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String app_key = Config.getInstance(HomeActivity.this).getConfig("app_key");
                        String arg = "?app_key=" + app_key;
                        Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, WebViewActivity.class);
                        intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.GD + arg);
                        intent.putExtra(HTMLInterface.H5_TITLE, "更多");
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                menuItem2.setSelected(true);
                topMenuBar.setTitleText(res.getString(R.string.financing));
                topMenuBar.setVisibility(View.VISIBLE);
                topMenuBar.setBackVisibility(View.INVISIBLE);
                topMenuBar.setMenuVisibility(View.VISIBLE);
                topMenuBar.setMenuSrc(R.mipmap.top_menu_more);
                topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String app_key = Config.getInstance(HomeActivity.this).getConfig("app_key");
                        String arg = "?app_key=" + app_key;
                        Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, WebViewActivity.class);
                        intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.GD + arg);
                        intent.putExtra(HTMLInterface.H5_TITLE, "更多");
                        startActivity(intent);
                    }
                });
                break;
            case 2:
                menuItem3.setSelected(true);
                topMenuBar.setTitleText(res.getString(R.string.loan));
                topMenuBar.setVisibility(View.VISIBLE);
                topMenuBar.setBackVisibility(View.INVISIBLE);
                topMenuBar.setMenuVisibility(View.VISIBLE);
                topMenuBar.setMenuSrc(R.mipmap.calculator);
                topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String app_key = Config.getInstance(HomeActivity.this).getConfig("app_key");
                        String arg = "?app_key=" + app_key;
                        Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, WebViewActivity.class);
                        intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.CALCULATOR + arg);
                        intent.putExtra(HTMLInterface.H5_TITLE, "计算器");
                        intent.putExtra("calculator", true);
                        startActivity(intent);
                    }
                });
                break;
            case 3:
                menuItem4.setSelected(true);
                topMenuBar.setVisibility(View.GONE);
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
        }
        super.onWindowFocusChanged(hasFocus);
    }


    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        choiceFragment = new ChoiceFragment();
        financingFragment = new FinancingFragment();
        loanFragment = new LoanFragment();
        mineFragment = new MineFragment();
        fragments.add(choiceFragment);
        fragments.add(financingFragment);
        fragments.add(loanFragment);
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
                vpHome.setCurrentItem(2);
            }
        });
        menuItem4.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String app_key = Config.getInstance(HomeActivity.this).getConfig("app_key");
                if (app_key == null || app_key.equals("")) {
                    Intent intentLogin = new Intent();
                    intentLogin.setClass(HomeActivity.this, LoginOrRegisterActivity.class);
                    startActivity(intentLogin);
                } else {
                    mineFragment.initData();
                    vpHome.setCurrentItem(3);
                }
            }
        });

    }

    private void load() {
        logDebug("load...");
        App.BASE_BEAN=new BaseBean();

        progressDialog = new ProgressDialog(this); // 获取对象
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置样式为圆形样式
        progressDialog.setIcon(R.mipmap.b);
        progressDialog.setTitle("Reminder"); // 设置进度条的标题信息
        progressDialog.setMessage("正在加载数据..."); // 设置进度条的提示信息
        progressDialog.setIndeterminate(false); // 设置进度条是否为不明确
        progressDialog.setCancelable(true); // 设置进度条是否按返回键取消
        progressDialog.show();

        loadValues();
    }

    private void loadValues() {
        VolleyHttp.getInstance().getJson(ServerInterface.ALL_DATA, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    BaseBean bean = gson.fromJson(json, BaseBean.class);
                    App.BASE_BEAN.setNew_experience_apr(bean.getNew_experience_apr());
                    App.BASE_BEAN.setNew_experience_valid_time(bean.getNew_experience_valid_time());
                    App.BASE_BEAN.setStatistic_display(bean.getStatistic_display());
                } else {
                    showToast(getString(R.string.no_internet));
                }
                loadCount++;
                loadPicList();
            }
        });
    }

    private void loadPicList() {
        VolleyHttp.getInstance().getJson(ServerInterface.ALL_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    BaseBean bean = gson.fromJson(json, BaseBean.class);
                    picList = bean.getAppScrollPic();
                } else {
                    showToast(getString(R.string.no_internet));
                }
                loadCount++;
                loadBorrowList();
            }
        });
    }

    private void loadBorrowList() {
        VolleyHttp.getInstance().getJson(ServerInterface.INVEST_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    BaseBean bean = gson.fromJson(json, BaseBean.class);
                    borrowList = bean.getBorrowList();
                } else {
                    showToast(getString(R.string.no_internet));
                }
                loadCount++;
                setData();
            }
        });
    }

    private void setData() {
        logDebug("home:"+loadCount);
        if (loadCount < 3)
            return;
        App.BASE_BEAN.setAppScrollPic(picList);
        App.BASE_BEAN.setBorrowList(borrowList);

        progressDialog.dismiss();

        initViewPager();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long exitTime = System.currentTimeMillis();
            if (exitTime - backTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                showToast("再按一次退出程序");
                backTime = exitTime;//更新firstTime
                return true;
            } else {//否则退出程序
                backTime = 0;
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    public void test(){
        financingFragment.test();
    }
}