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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        context = SplashActivity.this;

//        Config.getInstance(this).putConfig("statusHeight", String.valueOf(CommonUtils.getStatusHeight(this)));

        init();

    }

    private void init() {
        Time time = new Time();
        time.setToNow(); // 取得系统时间
        String url = ServerInterface.GET_SPLASH_PIC + "?time=" + time.toString();
        if(CommonUtils.isNetworkConnected(context)){
            VolleyHttp.getInstance().imageLoader(url, ivActivitSplash, null);//new Options().errImage(R.mipmap.lockback)
        } else {
            ivActivitSplash.setImageResource(R.drawable.splash_background);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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

