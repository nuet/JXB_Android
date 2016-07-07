package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by Chung on 2016/5/19.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_login)
    TopMenuBar topMenuBarLogin;
    @Bind(R.id.tv_login_tips)
    TextView tvLoginTips;
    @Bind(R.id.et_login_psw)
    EditText etLoginPsw;

    private static String app_key;
    private Map args = new HashMap();
    private Intent getIntent;
    private String mobile;
    private SharedPreferences sp;
    private String gesturePsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        topMenuBarLogin.setMenuTopPadding(statusHeight);

        sp = getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
        gesturePsw = sp.getString("GestureLock", "");
        getIntent = getIntent();
        mobile = getIntent.getStringExtra("mobile");
        tvLoginTips.setText(mobile.substring(0, 3) + "****" + mobile.substring(7, 11));

        topMenuBarLogin.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topMenuBarLogin.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, LoginOrRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @OnClick({R.id.tv_login_forget, R.id.btn_login_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget:
                Intent intentForget = new Intent();
                intentForget.putExtra("mobile", mobile);
                intentForget.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intentForget);
//                finish();
                break;
            case R.id.btn_login_confirm:
                args.put("username", mobile);
                args.put("password", etLoginPsw.getText().toString().trim());
                args.put("actionType", "login");
                VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_LOGIN, new VolleyHttp.JsonResponseListener() {
                    @Override
                    public void getJson(String json, boolean isConnectSuccess) {
                        if(isConnectSuccess){
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                if (jsonObject.getString("status").equals("1")) {
                                    app_key = jsonObject.getString("app_key");
                                    logInfo("login succeed:"+ app_key);
                                    Config.getInstance(LoginActivity.this).putConfig("app_key",app_key);
                                    Config.getInstance(LoginActivity.this).putConfig("phone",mobile);
                                    Intent intent = new Intent();
                                    if(TextUtils.isEmpty(gesturePsw)){
                                        intent.setClass(LoginActivity.this, GestureSettingsActivity.class);
                                        intent.putExtra("gestureTitle", "设置手势密码");
                                        intent.putExtra("jsFlag", false);
                                    }else{
                                        intent.setClass(LoginActivity.this, HomeActivity.class);
                                    }
                                    startActivity(intent);
//                                    finish();
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
                }, args);
                break;
        }
    }

}
