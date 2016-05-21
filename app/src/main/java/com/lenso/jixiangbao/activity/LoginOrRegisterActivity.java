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
public class LoginOrRegisterActivity extends BaseActivity {

    @Bind(R.id.top_menu_bar_login_register)
    TopMenuBar topMenuBarLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);

        topMenuBarLoginRegister.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.btn_login_register, R.id.tv_test})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_login_register:
                //showToast("btn_login_register clicked!");
                intent.setClass(LoginOrRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
//                finish();
                break;
            case R.id.tv_test:
                intent.setClass(LoginOrRegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
//                finish();
                break;
        }
    }

}
