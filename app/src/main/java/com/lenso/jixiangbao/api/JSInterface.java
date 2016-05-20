package com.lenso.jixiangbao.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lenso.jixiangbao.activity.GestureSettingsActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;

/**
 * Created by king on 2016/5/17.
 */
public class JSInterface {
    public static final String H5_URL = "h5_url";
    public static final String H5_TITLE = "h5_title";
    private final Context context;

    public JSInterface(Context context){
        this.context=context;
    }

    /**
     * 打开网页
     * @param title 标题栏标题
     * @param url 打开的网址
     */
    @JavascriptInterface
    public void open(String title,String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(H5_URL,url);
        intent.putExtra(H5_TITLE,title);
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     * @param phoneNumber 电话号码
     */
    @JavascriptInterface
    public void makeCall(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 我的->账户信息->手势密码
     */
    @JavascriptInterface
    public void gestureLock(){
        Log.i("JSInterface","getstureLock() executed!");
        Intent intent = new Intent(context, GestureSettingsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示吐司
     * @param msg 要显示的消息
     */
    @JavascriptInterface
    public void showToast(String msg){
        Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
