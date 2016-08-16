package com.lenso.jixiangbao;

import android.app.Application;
import android.util.Log;

import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.bean.ThreeChoice;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;


import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by king on 2016/5/19.
 */
public class App extends Application {
    public static BaseBean BASE_BEAN = new BaseBean();
    public static ThreeChoice THREE_CHOICE = new ThreeChoice();


    @Override
    public void onCreate() {
        super.onCreate();
        VolleyHttp.getInstance().init(getApplicationContext());

        JPushInterface.setDebugMode(true);//设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        Log.i("App->JPush", "initialed");

        ShareSDK.initSDK(this);
        CommonUtils commonUtils = new CommonUtils();
        commonUtils.loadValues();
        Log.i("bug", "app onCreate");
    }

}
