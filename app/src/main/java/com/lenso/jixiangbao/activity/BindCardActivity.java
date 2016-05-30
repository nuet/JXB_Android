package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.view.TopMenuBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Bind(R.id.et_bindcard_cardnumber)
    EditText etBindcardCardnumber;

    private List<String> dataList;
    private ArrayAdapter<String> arrayAdapter;
    private Map args = new HashMap();
    private String bank;

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
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataList);
        //设置样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spBindcardBank.setAdapter(arrayAdapter);
        spBindcardBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank = dataList.get(position);
                showToast(bank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bank = null;
                showToast("bank is null");
            }
        });

    }

    @OnClick(R.id.btn_bindcard)
    public void onClick() {
        if (bank != null) {
            args.put("app_key", Config.getInstance(BindCardActivity.this).getConfig("app_key"));
            args.put("bank", bank);
            args.put("account", etBindcardCardnumber.getText().toString().trim());
            args.put("type", "add");

            VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_BINDCARD, new VolleyHttp.JsonResponseListener() {
                @Override
                public void getJson(String json, boolean isConnectSuccess) {
                    if (isConnectSuccess) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            if (jsonObject.getString("status").equals("1")) {
                                showToast(jsonObject.getString("rsmsg"));
                                Intent intent = new Intent();
                                intent.setClass(BindCardActivity.this, GestureSettingsActivity.class);
                                intent.putExtra("gestureTitle", "设置手势密码");
                                intent.putExtra("jsFlag", false);
                                startActivity(intent);
                                finish();
                            } else {
                                showToast(jsonObject.getString("rsmsg"));
                            }
                        } catch (JSONException e) {
                            logInfo("BindCardActivity error");
                            e.printStackTrace();
                        }
                    } else {
                        showToast("请检查网络设置");
                    }

                }
            }, args);
        }

    }
}
