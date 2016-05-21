package com.lenso.jixiangbao.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.GestureSettingsActivity;
import com.lenso.jixiangbao.activity.GestureUnlockActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;

/**
 * Created by king on 2016/5/17.
 */
public class JSInterface {
    public static final String H5_URL = "h5_url";
    public static final String H5_TITLE = "h5_title";
    public static final int JI_CHE_DAI = 0;
    public static final int CALCULATOR = 1;
    private final Context context;

    public JSInterface(Context context) {
        this.context = context;
    }

    /**
     * 打开网页
     *
     * @param title 标题栏标题
     * @param url   打开的网址
     */
    @JavascriptInterface
    public void open(String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(H5_URL, url);
        intent.putExtra(H5_TITLE, title);
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber 电话号码
     */
    @JavascriptInterface
    public void makeCall(String phoneNumber) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                callPhone(phoneNumber);
            } else {
                showToast("没有权限：请为"+context.getResources().getString(R.string.app_name)+"开启拨打电话权限");
            }
        } else {
            callPhone(phoneNumber);
        }
    }

    void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //noinspection ResourceType
        context.startActivity(intent);
    }

    /**
     * 我的->账户信息->手势密码
     */
    @JavascriptInterface
    public void gestureLock() {
        Log.i("JSInterface", "getstureLock() executed!");
        SharedPreferences sp = context.getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
        String password = sp.getString("GestureLock", "");
        Intent intent = new Intent();
//        if(TextUtils.isEmpty(password)){
//            intent.putExtra("gestureTitle","设置手势密码");
//            intent.putExtra("jsFlag",true);
//            intent.setClass(context, GestureSettingsActivity.class);
//        }else{
            intent.putExtra("gestureTitle","输入手势密码");
            intent.putExtra("jsFlag",true);
            intent.setClass(context, GestureUnlockActivity.class);
//        }
        context.startActivity(intent);
    }

    /**
     * 显示吐司
     *
     * @param msg 要显示的消息
     */
    @JavascriptInterface
    public void showToast(String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
