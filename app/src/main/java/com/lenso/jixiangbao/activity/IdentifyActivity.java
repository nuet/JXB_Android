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
 * Created by Chung on 2016/5/21.
 */
public class IdentifyActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_identify)
    TopMenuBar topMenuBarIdentify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
        ButterKnife.bind(this);

        topMenuBarIdentify.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topMenuBarIdentify.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IdentifyActivity.this, GestureSettingsActivity.class);
                intent.putExtra("gestureTitle","设置手势密码");
                intent.putExtra("jsFlag",false);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btn_identify)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(IdentifyActivity.this, BindCardActivity.class);
        startActivity(intent);
    }
}
