package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.JSInterface;
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
        String title = intent.getStringExtra(JSInterface.H5_TITLE);
        WebView webView = getWebView(url);
        flWeb.addView(webView);
        webView.addJavascriptInterface(new JSInterface(this), "api");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        setWebZoom(webSettings);
        setNetworkCache(webSettings);

        webView.loadUrl(url);
        topMenuBar.setTitleText(title);
        topMenuBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menuSet(intent);
    }
    private void menuSet(Intent intent){
        int i=intent.getIntExtra("intent",-1);
        switch (i){
            case JSInterface.JI_CHE_DAI:
                ji_che_dai();
                break;
            case JSInterface.CALCULATOR:
                calculator();
                break;
        }
    }
    private void calculator(){
        topMenuBar.setBackSrc(R.mipmap.ic_launcher);
    }
    private void ji_che_dai(){
        topMenuBar.setMenuSrc(R.mipmap.calculator);
        topMenuBar.setMenuVisibility(View.VISIBLE);
        topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebViewActivity.this,WebViewActivity.class);
                intent.putExtra(JSInterface.H5_TITLE,"计算器");
                intent.putExtra(JSInterface.H5_URL,"http://meishusheng.len.so/assets/borrow-calculator.html");
                intent.putExtra("intent",JSInterface.CALCULATOR);
                startActivity(intent);
            }
        });
    }
}
