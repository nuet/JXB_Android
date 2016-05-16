package com.lenso.jixiangbao.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.PreferredFinanceListAdapter;
import com.lenso.jixiangbao.view.TopMenuBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/12.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_login)
    TopMenuBar topMenuBarLogin;
    @Bind(R.id.vp_login)
    ViewPager vpLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }
}
