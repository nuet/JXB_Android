package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.view.JWebView;
import com.lenso.jixiangbao.view.TopMenuBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/17.
 */
public class WebViewActivity extends WebBaseActivity {
    @Bind(R.id.top_menu_bar)
    TopMenuBar topMenuBar;
    @Bind(R.id.fl_web)
    FrameLayout flWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra(JSInterface.H5_URL);
        String title=intent.getStringExtra(JSInterface.H5_TITLE);
        WebView webView = getWebView(url);
        flWeb.addView(webView);
        webView.addJavascriptInterface(new JSInterface(this), "api");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        ((JWebView)webView).onScrollChanged(webView.getScrollX(), webView.getScrollY());
        topMenuBar.setTitleText(title);
        topMenuBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
