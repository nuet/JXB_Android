package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;
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
 * Created by Chung on 2016/6/8.
 */
public class VerifyActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_verify)
    TopMenuBar topMenuBarVerify;
    @Bind(R.id.tv_verify_tips)
    TextView tvVerifyTips;
    @Bind(R.id.et_verify_psw)
    EditText etVerifyPsw;
    @Bind(R.id.btn_verify_confirm)
    Button btnVerifyConfirm;

    private Map agrs = new HashMap();
    private static String app_key;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ButterKnife.bind(this);

//        topMenuBarVerify.setMenuTopPadding(statusHeight);

        topMenuBarVerify.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phone = Config.getInstance(this).getConfig("phone");
        tvVerifyTips.setText("您的手机号码:" + phone.substring(0, 3) + "****" + phone.substring(7, 11));
    }

    @OnClick(R.id.btn_verify_confirm)
    public void onClick() {
        if(CommonUtils.isFastClick()){
            return;
        }
        agrs.put("username", phone);
        agrs.put("password", etVerifyPsw.getText().toString().trim());
        agrs.put("actionType", "login");
        VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_LOGIN, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if(isConnectSuccess){
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getString("status").equals("1")) {
                            app_key = jsonObject.getString("app_key");
                            Config.getInstance(VerifyActivity.this).putConfig("app_key",app_key);
                            Config.getInstance(VerifyActivity.this).putConfig("phone",phone);
                            Intent intent = new Intent();
                            intent.setClass(VerifyActivity.this, GestureSettingsActivity.class);
                            intent.putExtra("jsFlag", false);
                            intent.putExtra("gestureTitle", "修改手势密码");
                            startActivity(intent);
                            finish();
                        }else{
                            showToast(jsonObject.getString("rsmsg"));
                        }
                    } catch (JSONException e) {
                        logInfo("login http error!");
                        e.printStackTrace();
                    }
                }else {
                    showToast("请检查网络设置");
                }
            }
        }, agrs);

    }
}
