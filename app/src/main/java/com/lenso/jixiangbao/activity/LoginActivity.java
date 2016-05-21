package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.view.TopMenuBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/19.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_login)
    TopMenuBar topMenuBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        topMenuBarLogin.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, HomeActivity.class);
//              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }

}
