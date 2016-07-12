package com.lenso.jixiangbao.activity;

import android.os.Environment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lenso.jixiangbao.util.CommonUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 2016/5/17.
 */
public class WebBaseActivity extends BaseActivity {
    private Map<String, WebView> webViews;
    private static final String CACHE_PATH = Environment.getDownloadCacheDirectory().getAbsolutePath();

    protected WebView getWebView(String tag) {
        if (webViews == null) {
            webViews = new HashMap<>();
        }
        if (webViews.containsKey(tag))
            return webViews.get(tag);
        WebView webView = new WebView(this);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webViews.put(tag, webView);
        return webView;
    }

    /**
     * 缩放功能
     */
    public void setWebZoom(WebSettings webSettings) {
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

    }

    /**
     * 网络连接及缓存
     */
    public void setNetworkCache(WebSettings webSettings) {
        if (CommonUtils.isNetworkConnected(getApplicationContext())) {
//            /*清除缓存*/
//            File dir = getCacheDir();
//            long numDays = System.currentTimeMillis();
//            CommonUtils.clearCacheFolder(dir, numDays);
//            /********/
//
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            logDebug("network connect!");
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            logDebug("network not connect!");
        }
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(CACHE_PATH);
    }

    protected void removeWebView(String tag) {
        if (!webViews.containsKey(tag))
            return;
        onDestroyWebView(webViews.get(tag));
        webViews.remove(tag);
    }

    protected void onDestroyWebView(WebView webView) {
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setVisibility(View.GONE);
        webView.removeAllViews();
        webView.destroy();
    }

    @Override
    protected void onDestroy() {
        if (webViews != null) {
            Collection<WebView> ws = webViews.values();
            for (WebView webView : ws) {
                onDestroyWebView(webView);
            }
        }
        super.onDestroy();
    }
}
