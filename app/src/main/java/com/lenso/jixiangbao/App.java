package com.lenso.jixiangbao;

import android.app.Application;

import com.lenso.jixiangbao.http.VolleyHttp;

/**
 * Created by king on 2016/5/19.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyHttp.getIntance().init(getApplicationContext());
    }
}
