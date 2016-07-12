package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.AppScrollPic;
import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.bean.InvestList;
import com.lenso.jixiangbao.bean.ThreeChoice;
import com.lenso.jixiangbao.fragment.ChoiceFragment;
import com.lenso.jixiangbao.fragment.FinancingFragment;
import com.lenso.jixiangbao.fragment.FindFragment;
import com.lenso.jixiangbao.fragment.MineFragment;
import com.lenso.jixiangbao.fragment.ScreenFragment;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.util.JPushSettings;
import com.lenso.jixiangbao.view.JViewPager;
import com.lenso.jixiangbao.view.MenuItemView;
import com.lenso.jixiangbao.view.TopMenuBar;
import com.lenso.jixiangbao.view.iOSAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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
    //    private LoanFragment loanFragment;
    private FindFragment findFragment;
    private MineFragment mineFragment;

    private long backTime = 0;

    private Gson gson = new Gson();
    private List<AppScrollPic> picList;
    private int loadCount = 0;
    private InvestList investList;

    private static KProgressHUD progressDialog;
    private Intent getIntent;
    private boolean trysOut;
//    private JPushSettings jPushSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_home);
        ButterKnife.bind(this);
        topMenuBar.setMenuTopPadding(statusHeight);
        HOMECONTEXT = HomeActivity.this;

        ShareSDK.initSDK(this);//初始化shareSDK

        getIntent = getIntent();
        trysOut = getIntent.getBooleanExtra("trysOut", false);

        load();

//        setAlias();//设置JPush别名

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
//        loanFragment = new LoanFragment();
        findFragment = new FindFragment();
        mineFragment = new MineFragment();
        fragments.add(choiceFragment);
        fragments.add(financingFragment);
//        fragments.add(loanFragment);
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
        }
    }

    private void load() {
        logDebug("load...");
        App.BASE_BEAN = new BaseBean();
        App.THREE_CHOICE = new ThreeChoice();

        progressDialog = KProgressHUD.create(HomeActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载数据中...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


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
                loadReports();
            }
        });
    }

    private void loadReports() {
        VolleyHttp.getInstance().getJson(ServerInterface.FINANCIAL_REPORTS, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    BaseBean bean = gson.fromJson(json, BaseBean.class);
                    App.BASE_BEAN.setPlatformFinancialReport(bean.getPlatformFinancialReport());
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
                    App.THREE_CHOICE.setThreeChoice(bean.getSanList());
                } else {
                    showToast(getString(R.string.no_internet));
                }
                loadCount++;
                loadInvestList();
            }
        });
    }

    private void loadInvestList() {
        VolleyHttp.getInstance().getJson(ServerInterface.INVEST_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    investList = gson.fromJson(json, InvestList.class);
                } else {
                    showToast(getString(R.string.no_internet));
                }
                loadCount++;
                setData();
            }
        });
    }

    private void setData() {
        logDebug("home:" + loadCount);
        if (loadCount < 4)
            return;
        App.BASE_BEAN.setAppScrollPic(picList);
        App.BASE_BEAN.setInvestList(investList);
//        logInfo("getBorrowList" + investList.getBorrowList().toString());
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

    public void sortBorrowList() {
        financingFragment.sortBorrowList();
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
//                        finish();
                    }
                })
                .setShowNegBtn(false)
//                .setNegativeButton("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                })
                .show();

    }



//    private void setAlias() {
//        String alias = Config.getInstance(HOMECONTEXT).getConfig("phone");
//        logInfo("JPush"+alias);
//        if (TextUtils.isEmpty(alias)) {
//            logError("JPush 别名设置失败，app_key为空");
//            return;
//        }
//        if (!isValidTagAndAlias(alias)) {
//            logError("JPush alias 设置失败");
////            showToast("JPush alias 设置失败");
//            return;
//        }
//
//        // 调用 Handler 来异步设置别名
//        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
//    }
//
//    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs ;
//            switch (code) {
//                case 0:
//                    logs = "Set tag and alias success";
//                    Log.i("JPush", logs);
//                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                    break;
//                case 6002:
//                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                    Log.i("JPush", logs);
//                    // 延迟 60 秒来调用 Handler 设置别名
//                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                    break;
//                default:
//                    logs = "Failed with errorCode = " + code;
//                    Log.e("JPush", logs);
//            }
//            logInfo(logs);
////            showToast(logs);
//        }
//    };
//    private static final int MSG_SET_ALIAS = 1001;
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case MSG_SET_ALIAS:
//                    Log.d("JPush", "Set alias in handler.");
//                    // 调用 JPush 接口来设置别名。
//                    JPushInterface.setAliasAndTags(getApplicationContext(),
//                            (String) msg.obj,
//                            null,
//                            mAliasCallback);
//                    break;
//                default:
//                    Log.i("JPush", "Unhandled msg:" + msg.what);
//            }
//        }
//    };
//
//    // 校验Tag Alias 只能是数字,英文字母和中文
//    public static boolean isValidTagAndAlias(String s) {
//        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA50-9a-zA-Z_@!#$&*+=.|￥¥]+$");//汉字、数字、小写字母、大写字母、下划线、@!#$&*+=.|￥
//        Matcher m = p.matcher(s);
//        return m.matches();
//    }


}