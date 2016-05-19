package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;

import com.lenso.jixiangbao.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/19.
 */
public class LoginOrRegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login_register)
    public void onClick() {
        showToast("btn_login_register clicked!");
        Intent intent = new Intent();
        intent.setClass(LoginOrRegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
