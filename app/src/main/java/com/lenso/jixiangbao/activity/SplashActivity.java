package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.AppScrollPic;
import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.bean.ChoiceList;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.util.Config;

import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by king on 2016/5/10.
 */
public class SplashActivity extends BaseActivity {

    private Gson gson;
    private List<AppScrollPic> picList;
    private int loadCount = 0;
    private List<ChoiceList> borrowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gson = new Gson();
        App.BASE_BEAN=new BaseBean();

        Config.getInstance(this).putConfig("statusHeight",String.valueOf(CommonUtils.getStatusHeight(this)));

        load();
    }

    private void load() {
        logDebug("load...");
        loadValues();
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
                goHome();
            }
        });
    }

    private void goHome() {
        logDebug("home:"+loadCount);
        if (loadCount < 3)
            return;
        App.BASE_BEAN.setAppScrollPic(picList);
        App.BASE_BEAN.setBorrowList(borrowList);
        init();
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    private void init() {
        logDebug("init...");
        Intent intent = new Intent();
        String isFirstOpen = Config.getInstance(SplashActivity.this).getConfig("isFirstOpen");
        if (isFirstOpen == null || isFirstOpen.equals("")) {
            intent.setClass(this, LaunchActivity.class);
            Config.getInstance(SplashActivity.this).putConfig("isFirstOpen", "0");
        } else {
            String app_key = Config.getInstance(SplashActivity.this).getConfig("app_key");
            if (app_key == null || app_key.equals("")) {
                intent.setClass(this, HomeActivity.class);
            } else {
                intent.setClass(this, GestureUnlockActivity.class);
//                intent.putExtra("splashFlag", true);
            }
        }
        startActivity(intent);
        finish();
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

