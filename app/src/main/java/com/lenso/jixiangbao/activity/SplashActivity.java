package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.Config;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by king on 2016/5/10.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        load();
    }

    private void load() {
        VolleyHttp.getInstance().getJson(ServerInterface.ALL_DATA, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if(json!=null && !json.equals("") && !json.equals("null")){
                    Gson gson=new Gson();
                    App.BASE_BEAN=gson.fromJson(json, BaseBean.class);
                }else{
                    showToast(getString(R.string.no_internet));
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        init();
                    }
                });
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    private void init() {
        Intent intent = new Intent();
        String isFirstOpen = Config.getInstance(SplashActivity.this).getConfig("isFirstOpen");
        if(isFirstOpen == null || isFirstOpen.equals("")){
            intent.setClass(this, LaunchActivity.class);
            Config.getInstance(SplashActivity.this).putConfig("isFirstOpen","0");
        } else {
            intent.setClass(this, HomeActivity.class);
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

