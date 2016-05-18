package com.lenso.jixiangbao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by king on 2016/5/18.
 */
public class JWebView extends WebView {
    public JWebView(Context context) {
        super(context);
    }

    public JWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
