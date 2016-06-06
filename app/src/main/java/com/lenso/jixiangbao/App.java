package com.lenso.jixiangbao;

import android.app.Application;
import android.util.Log;

import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.http.VolleyHttp;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by king on 2016/5/19.
 */
public class App extends Application {
    public static BaseBean BASE_BEAN;
    public static final String HOST="http://app.pongyoo.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyHttp.getInstance().init(getApplicationContext());

        JPushInterface.setDebugMode(true);//设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        Log.i("App->JPush","initialed");

    }
}
