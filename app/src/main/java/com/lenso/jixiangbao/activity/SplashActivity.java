package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.LruCache;

import com.lenso.jixiangbao.R;


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
            init();
    }

    private void init() {
        Intent intent = new Intent(this, LaunchActivity.class);
        startActivity(intent);
        finish();
    }
}

