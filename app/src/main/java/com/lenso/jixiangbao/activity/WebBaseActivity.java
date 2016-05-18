package com.lenso.jixiangbao.activity;

import android.view.View;
import android.webkit.WebView;

import com.lenso.jixiangbao.view.JWebView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 2016/5/17.
 */
public class WebBaseActivity extends BaseActivity {
    private Map<String, WebView> webViews;

    protected WebView getWebView(String tag) {
        if (webViews == null) {
            webViews = new HashMap<>();
        }
        if (webViews.containsKey(tag))
            return webViews.get(tag);
        WebView webView = new JWebView(this);
        webViews.put(tag, webView);
        return webView;
    }
    protected void removeWebView(String tag){
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
        if(webViews!=null){
            Collection<WebView> ws = webViews.values();
            for(WebView webView:ws){
                onDestroyWebView(webView);
            }
        }
        super.onDestroy();
    }
}
