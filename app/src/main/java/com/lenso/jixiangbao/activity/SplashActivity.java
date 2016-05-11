package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;

import com.lenso.jixiangbao.R;

/**
 * Created by king on 2016/5/10.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }
     private void init(){
        Intent intent = new Intent(this, LaunchActivity.class);
        startActivity(intent);
        finish();
    }
}

