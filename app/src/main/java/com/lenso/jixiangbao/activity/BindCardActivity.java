package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/21.
 */
public class BindCardActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_bindcard)
    TopMenuBar topMenuBarBindcard;
    @Bind(R.id.sp_bindcard_bank)
    Spinner spBindcardBank;

    private List<String> dataList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindcard);
        ButterKnife.bind(this);

        topMenuBarBindcard.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        topMenuBarBindcard.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BindCardActivity.this, GestureSettingsActivity.class);
                intent.putExtra("gestureTitle", "设置手势密码");
                intent.putExtra("jsFlag", false);
                startActivity(intent);
            }
        });

        dataList = new ArrayList<String>();
        dataList.add("请选择");
        dataList.add("工商银行");
        dataList.add("中国银行");
        dataList.add("建设银行");
        dataList.add("农业银行");
        dataList.add("交通银行");
        dataList.add("上海银行");
        dataList.add("平安银行");
        dataList.add("兴业银行");
        dataList.add("上海浦东发展银行");
        dataList.add("中信银行");
        dataList.add("光大银行");
        dataList.add("邮政银行");
        //适配器
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataList);
        //设置样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spBindcardBank.setAdapter(arrayAdapter);

    }

    @OnClick(R.id.btn_bindcard)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(BindCardActivity.this, GestureSettingsActivity.class);
        intent.putExtra("gestureTitle", "设置手势密码");
        intent.putExtra("jsFlag", false);
        startActivity(intent);
    }
}
