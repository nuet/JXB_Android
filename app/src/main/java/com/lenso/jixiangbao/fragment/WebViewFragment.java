package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lenso.jixiangbao.api.JSInterface;

/**
 * Created by king on 2016/5/18.
 */
public class WebViewFragment extends WebBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WebView webView = getWebView(toString());
        webView.addJavascriptInterface(new JSInterface(getActivity()), "api");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setWebZoom(webSettings);
        setNetworkCache(webSettings);
        return webView;
    }
    public void webViewLoader(String url){
        WebView webView = getWebView(toString());
        webView.loadUrl(url);
    }
    public WebView getWebView(){
        return getWebView(toString());
    }
}
