package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.view.TopMenuBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/6/3.
 */
public class SetPasswordActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_set)
    TopMenuBar topMenuBarSet;
    @Bind(R.id.et_set_psw)
    EditText etSetPsw;
    @Bind(R.id.et_set_again)
    EditText etSetAgain;
    @Bind(R.id.btn_set_confirm)
    Button btnSetConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);

        topMenuBarSet.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_set_confirm)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(SetPasswordActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
