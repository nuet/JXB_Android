package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.widget.ImageView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.util.Config;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by king on 2016/5/10.
 */
public class SplashActivity extends BaseActivity {
    @Bind(R.id.iv_activit_splash)
    ImageView ivActivitSplash;

    private Context context;

//    private Gson gson;
//    private List<AppScrollPic> picList;
//    private int loadCount = 0;
//    private List<ChoiceList> borrowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        context = SplashActivity.this;

//        gson = new Gson();
//        App.BASE_BEAN=new BaseBean();

        Config.getInstance(this).putConfig("statusHeight", String.valueOf(CommonUtils.getStatusHeight(this)));

        init();

//        load();
    }

    private void init() {
        Time time = new Time();
        time.setToNow(); // 取得系统时间
        String url = ServerInterface.GET_SPLASH_PIC + "?time=" + time.toString();
        VolleyHttp.getInstance().imageLoader(url, ivActivitSplash, null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Time time = new Time();
//                    time.setToNow(); // 取得系统时间
//                    String url = ServerInterface.GET_SPLASH_PIC + "?time=" + time.toString();
//                    VolleyHttp.getInstance().imageLoader(url, ivActivitSplash, null);

                    Thread.sleep(3000);

                    Intent intent = new Intent();
                    String isFirstOpen = Config.getInstance(SplashActivity.this).getConfig("isFirstOpen");
                    if (isFirstOpen == null || isFirstOpen.equals("")) {
                        intent.setClass(context, LaunchActivity.class);
                        Config.getInstance(SplashActivity.this).putConfig("isFirstOpen", "0");
                    } else {
                        String app_key = Config.getInstance(SplashActivity.this).getConfig("app_key");
                        SharedPreferences sp = getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
                        String password = sp.getString("GestureLock", "");
                        if (app_key == null || app_key.equals("")) {
                            intent.setClass(context, HomeActivity.class);
                        } else if(TextUtils.isEmpty(password)){
                            intent.setClass(context, LoginActivity.class);
                            intent.putExtra("mobile", Config.getInstance(context).getConfig("phone"));
                        } else {
                            intent.setClass(context, GestureUnlockActivity.class);
                        }
                    }
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    private void load() {
//        logDebug("load...");
//        loadValues();
//    }
//
//    private void loadValues() {
//        VolleyHttp.getInstance().getJson(ServerInterface.ALL_DATA, new VolleyHttp.JsonResponseListener() {
//            @Override
//            public void getJson(String json, boolean isConnectSuccess) {
//                if (json != null && !json.equals("") && !json.equals("null")) {
//                    BaseBean bean = gson.fromJson(json, BaseBean.class);
//                    App.BASE_BEAN.setNew_experience_apr(bean.getNew_experience_apr());
//                    App.BASE_BEAN.setNew_experience_valid_time(bean.getNew_experience_valid_time());
//                    App.BASE_BEAN.setStatistic_display(bean.getStatistic_display());
//                } else {
//                    showToast(getString(R.string.no_internet));
//                }
//                loadCount++;
//                loadPicList();
//            }
//        });
//    }
//
//    private void loadPicList() {
//        VolleyHttp.getInstance().getJson(ServerInterface.ALL_LIST, new VolleyHttp.JsonResponseListener() {
//            @Override
//            public void getJson(String json, boolean isConnectSuccess) {
//                if (json != null && !json.equals("") && !json.equals("null")) {
//                    BaseBean bean = gson.fromJson(json, BaseBean.class);
//                    picList = bean.getAppScrollPic();
//                } else {
//                    showToast(getString(R.string.no_internet));
//                }
//                loadCount++;
//                loadBorrowList();
//            }
//        });
//    }
//
//    private void loadBorrowList() {
//        VolleyHttp.getInstance().getJson(ServerInterface.INVEST_LIST, new VolleyHttp.JsonResponseListener() {
//            @Override
//            public void getJson(String json, boolean isConnectSuccess) {
//                if (json != null && !json.equals("") && !json.equals("null")) {
//                    BaseBean bean = gson.fromJson(json, BaseBean.class);
//                    borrowList = bean.getBorrowList();
//                } else {
//                    showToast(getString(R.string.no_internet));
//                }
//                loadCount++;
//                goHome();
//            }
//        });
//    }
//    private void goHome() {
//        logDebug("home:"+loadCount);
//        if (loadCount < 3)
//            return;
//        App.BASE_BEAN.setAppScrollPic(picList);
//        App.BASE_BEAN.setBorrowList(borrowList);
//        init();
//    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(SplashActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(SplashActivity.this);
    }

}

