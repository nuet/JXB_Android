package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.receiver.JPushReceiver;
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
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            },1000);
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

