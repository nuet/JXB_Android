package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.view.TopMenuBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/6/3.
 */
public class ForgetPasswordActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_forget)
    TopMenuBar topMenuBarForget;
    @Bind(R.id.tv_forget_tips)
    TextView tvForgetTips;
    @Bind(R.id.et_forget_code)
    EditText etForgetCode;
    @Bind(R.id.tv_forget_get)
    TextView tvForgetGet;
    @Bind(R.id.btn_forget_confirm)
    Button btnForgetConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        topMenuBarForget.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick(R.id.btn_forget_confirm)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(ForgetPasswordActivity.this, SetPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
