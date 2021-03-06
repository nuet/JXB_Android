package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.fragment.ChoiceFragment;
import com.lenso.jixiangbao.fragment.FinancingFragment;
import com.lenso.jixiangbao.fragment.FindFragment;
import com.lenso.jixiangbao.fragment.MineFragment;
import com.lenso.jixiangbao.fragment.ScreenFragment;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.MenuItemView;
import com.lenso.jixiangbao.view.TopMenuBar;
import com.lenso.jixiangbao.view.iOSAlertDialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private FindFragment findFragment;
    private MineFragment mineFragment;

    private long backTime = 0;

    private Intent getIntent;
    public static boolean trysOut;

    public ScreenFragment screenFragment1;
    public ScreenFragment screenFragment2;

    private DownloadAsyncTask downloadAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_home);
        ButterKnife.bind(this);
//        topMenuBar.setMenuTopPadding(statusHeight);
        HOMECONTEXT = HomeActivity.this;

        ShareSDK.initSDK(this);//初始化shareSDK

        getIntent = getIntent();
        trysOut = getIntent.getBooleanExtra("trysOut", false);

        logInfo("bug home created");

        initViewPager();
    }


    @Override
    protected void onPause() {
        logInfo("bug home pause");
        super.onPause();
    }

    @Override
    protected void onStop() {
//        vpHome.removeAllViews();
        logInfo("bug home stop");
        super.onStop();
    }

    public void initViewPager() {
        logInfo("bug home initViewPager");
        List<Fragment> fragments = new ArrayList<>();
        choiceFragment = new ChoiceFragment();
        financingFragment = new FinancingFragment();
        findFragment = new FindFragment();
        mineFragment = new MineFragment();
        fragments.add(choiceFragment);
        fragments.add(financingFragment);
        fragments.add(findFragment);
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
                vpHome.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtils commonUtils = new CommonUtils();
                        commonUtils.loadValues();
                        initViewPager();
                    }
                }, 800);

            }
        });
        menuItem2.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(1);
                financingFragment.refresh();
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
                SharedPreferences sp = getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
                String gesturePsw = sp.getString("GestureLock", "");
                if (app_key == null || app_key.equals("") || TextUtils.isEmpty(gesturePsw)) {
                    Intent intentLogin = new Intent();
                    intentLogin.setClass(HomeActivity.this, LoginOrRegisterActivity.class);
                    startActivity(intentLogin);
                } else {
                    mineFragment.initData();
                    vpHome.setCurrentItem(3);
                }
            }
        });

        if (trysOut) {
            alertDialog("您已连续5次输入错误,请重新登录!");
            return;
        }

        try {
            boolean needUpdate = false;
            PackageManager pm = HOMECONTEXT.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(HOMECONTEXT.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String[] pvn = pi.versionName.split("\\.");
            if (!TextUtils.isEmpty(App.BASE_BEAN.getVersionCode())) {
                String[] svn = App.BASE_BEAN.getVersionCode().split("\\.");
                logDebug(pi.versionName);
                logDebug(App.BASE_BEAN.getVersionCode());
                for (int i = 0; i < pvn.length; i++) {
                    logDebug(pvn[i] + i);
                    if (Integer.parseInt(pvn[i]) < Integer.parseInt(svn[i])) {
                        needUpdate = true;
                        break;
                    }
                }
            }
            if (needUpdate) { //如果小于和服务器的最新版本号
                alertUpdateDialog("当前软件不是最新版本");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
                topMenuBar.setMenuVisibility(View.INVISIBLE);
//                topMenuBar.setMenuSrc(R.mipmap.top_menu_more);
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
                topMenuBar.setMenuVisibility(View.INVISIBLE);
//                topMenuBar.setMenuSrc(R.mipmap.top_menu_more);
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
                topMenuBar.setMenuVisibility(View.INVISIBLE);
//                topMenuBar.setMenuSrc(R.mipmap.calculator);
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
        setScreenFragmentOneData();
        setScreenFragmentTwoData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && isFirst) {
            isFirst = false;
            initSlidingMenu();
        }
        super.onWindowFocusChanged(hasFocus);
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
        logInfo("bug home destroy");
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

    public void sortBorrowList() {
        financingFragment.sortBorrowList();
    }

    public void sortTransferList() {
        financingFragment.sortTransferList();
    }

    private void alertDialog(String msg) {
        new iOSAlertDialog(HomeActivity.this).builder()
                .setTitle("温馨提示")
                .setMsg(msg)
                .setCancelable(false)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.clearGesturePassword(HomeActivity.this);
                        Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, LoginActivity.class);
                        intent.putExtra("mobile", Config.getInstance(HomeActivity.this).getConfig("phone"));
                        startActivity(intent);
                    }
                })
                .setShowNegBtn(false)
                .show();
    }

    private void alertUpdateDialog(String msg) {
        new iOSAlertDialog(HomeActivity.this).builder()
                .setTitle("温馨提示")
                .setMsg(msg)
                .setCancelable(false)
                .setPositiveButton("去更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadAsyncTask = new DownloadAsyncTask(App.BASE_BEAN.getAndroid_url());
                        if (CommonUtils.isNetworkConnected(HOMECONTEXT)) {
                            downloadAsyncTask.execute();
                        } else {
                            showToast("请检查网络设置");
                        }
//                        Uri uri = Uri.parse(App.BASE_BEAN.getAndroid_url());//应用更新地址
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("请及时更新到最新版本，以防漏洞");
                    }
                })
                .show();
    }

    public void setScreenFragmentOneData() {
        screenFragment1 = new ScreenFragment();

        Map<String, List<String>> data = new LinkedHashMap<>();
        List<String> texts = new ArrayList<>();
        texts.add("全部");
        texts.add("招标中");
        texts.add("还款中");
        texts.add("待审核");
        texts.add("已成功");
        data.put("借款状态", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("等额本息");
        texts.add("一次性还本付息");
        texts.add("每月还息到期还本");
        data.put("还款方式", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("1个月以下");
        texts.add("1-2个月");
        texts.add("3-4个月");
        texts.add("5-6个月");
        texts.add("6个月以上");
        data.put("借款期限", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("5万以下");
        texts.add("5-10万");
        texts.add("10-30万");
        texts.add("30-50万");
        texts.add("50万以上");
        data.put("借款金额", texts);

        screenFragment1.initData(data, 1);
    }

    public void setScreenFragmentTwoData() {
        screenFragment2 = new ScreenFragment();

        Map<String, List<String>> data = new LinkedHashMap<>();
        List<String> texts = new ArrayList<>();
        texts.add("全部");
        texts.add("有折扣");
        texts.add("无折扣");
        data.put("转让折扣", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("等额本息");
        texts.add("一次性还本付息");
        texts.add("每月还息到期还本");
        data.put("还款方式", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("1个月以下");
        texts.add("1-2个月");
        texts.add("3-4个月");
        texts.add("5-6个月");
        texts.add("6个月以上");
        data.put("借款期限", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("5万以下");
        texts.add("5-10万");
        texts.add("10-30万");
        texts.add("30-50万");
        texts.add("50万以上");
        data.put("借款金额", texts);

        screenFragment2.initData(data, 2);
    }

    public class DownloadAsyncTask extends AsyncTask<Integer, Integer, String> {
        KProgressHUD kProgressHUD;
        String url;

        public DownloadAsyncTask(String url) {
            super();
            this.url = url;
            this.kProgressHUD = KProgressHUD.create(HOMECONTEXT)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel("正在下载中...")
                    .setMaxProgress(100)
                    .setCancellable(false);
        }

        @Override
        protected String doInBackground(Integer... params) {
            CommonUtils.openFile(CommonUtils.downFile(url, HOMECONTEXT, new Handler(HOMECONTEXT.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    int progress = (int) msg.obj;
                    kProgressHUD.setProgress(progress);
                    super.handleMessage(msg);
                }
            }), HOMECONTEXT);
            return null;
        }

        @Override
        protected void onPreExecute() {
            kProgressHUD.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            kProgressHUD.dismiss();
            super.onPostExecute(s);
        }
    }
}