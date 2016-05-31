package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.receiver.JPushReceiver;

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
        Intent intent = new Intent(this, LaunchActivity.class);
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

