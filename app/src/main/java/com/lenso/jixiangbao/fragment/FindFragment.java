package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chung on 2016/7/6.
 */
public class FindFragment extends WebBaseFragment {
    @Bind(R.id.sv_fragment_find)
    ScrollView svFragmentFind;

    private final String url = HTMLInterface.GD;
    private static WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        ButterKnife.bind(this, view);

        webView = getWebView(url);
        svFragmentFind.addView(webView);
        webView.addJavascriptInterface(new JSInterface(getActivity()), "api");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        setWebZoom(webSettings);
        setNetworkCache(webSettings);

        webView.loadUrl(url);

        return view;
    }

    public static void reload() {
        webView.reload();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
