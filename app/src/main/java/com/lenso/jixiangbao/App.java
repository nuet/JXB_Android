package com.lenso.jixiangbao;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.bean.ThreeChoice;
import com.lenso.jixiangbao.http.VolleyHttp;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by king on 2016/5/19.
 */
public class App extends Application {
    public static BaseBean BASE_BEAN;
    public static ThreeChoice THREE_CHOICE;
    public static final String HOST = "http://app.pongyoo.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyHttp.getInstance().init(getApplicationContext());

        JPushInterface.setDebugMode(true);//设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        Log.i("App->JPush", "initialed");

        //注册广播
//        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

    }

//    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
//        String SYSTEM_REASON = "reason";
//        String SYSTEM_HOME_KEY = "homekey";
//        String SYSTEM_HOME_KEY_LONG = "recentapps";
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
//                String reason = intent.getStringExtra(SYSTEM_REASON);
//                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
//                    //表示按了home键,程序到了后台
//                    System.exit(0);
//                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
//                    //表示长按home键,显示最近使用的程序列表
//                    System.exit(0);
//                }
//            }
//        }
//    };

}
