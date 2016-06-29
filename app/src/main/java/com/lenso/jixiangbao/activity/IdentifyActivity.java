package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.view.TopMenuBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/21.
 */
public class IdentifyActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_identify)
    TopMenuBar topMenuBarIdentify;
    @Bind(R.id.et_identify_name)
    EditText etIdentifyName;
    @Bind(R.id.et_identify_certification)
    EditText etIdentifyCertification;

    private Map args = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
        ButterKnife.bind(this);

        topMenuBarIdentify.setMenuTopPadding(statusHeight);

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
                intent.putExtra("gestureTitle", "设置手势密码");
                intent.putExtra("jsFlag", false);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btn_identify_confirm)
    public void onClick() {
        args.put("app_key", Config.getInstance(IdentifyActivity.this).getConfig("app_key"));
        args.put("realname", etIdentifyName.getText().toString().trim());
        args.put("card_id", etIdentifyCertification.getText().toString().trim());
        args.put("type", "1");
        VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_REALNAME, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (isConnectSuccess) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getString("status").equals("1")) {
                            showToast(jsonObject.getString("rsmsg"));
                            Intent intent = new Intent();
                            intent.setClass(IdentifyActivity.this, BindCardActivity.class);
                            startActivity(intent);
//                            finish();
                        } else {
                            showToast(jsonObject.getString("rsmsg"));
                        }
                    } catch (JSONException e) {
                        logInfo("IdentifyActivity error");
                        e.printStackTrace();
                    }
                } else {
                    showToast("请检查网络设置");
                }

            }
        }, args);
    }
}
