package com.lenso.jixiangbao.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;

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
    @JavascriptInterface
    public void open(String title,String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(H5_URL,url);
        intent.putExtra(H5_TITLE,title);
        context.startActivity(intent);
    }
    @JavascriptInterface
    public void makeCall(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        context.startActivity(intent);
    }
}
