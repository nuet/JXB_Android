package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.lenso.jixiangbao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/10.
 */
public class SplashActivity extends BaseActivity {
    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
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

