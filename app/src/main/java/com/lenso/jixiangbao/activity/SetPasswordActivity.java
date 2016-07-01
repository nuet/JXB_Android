package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CheckPassword;
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

    private String id;
    private Map argsSet = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);

        topMenuBarSet.setMenuTopPadding(statusHeight);

        Intent intentGet = getIntent();
        id = intentGet.getStringExtra("id");
        topMenuBarSet.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_set_confirm)
    public void onClick() {
        if(CheckPassword.check(etSetPsw.getText().toString().trim())){
            argsSet.put("password", etSetPsw.getText().toString().trim());
            argsSet.put("confirm_password", etSetAgain.getText().toString().trim());
            argsSet.put("type", "setpwd");
            argsSet.put("id", id);
            VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_SETPSW, new VolleyHttp.JsonResponseListener() {
                @Override
                public void getJson(String json, boolean isConnectSuccess) {
                    if(isConnectSuccess){
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            if(jsonObject.getString("status").equals("1")){
                                Config.getInstance(SetPasswordActivity.this).putConfig("app_key", jsonObject.getString("app_key"));
                                Intent intent = new Intent();
                                intent.setClass(SetPasswordActivity.this, GestureSettingsActivity.class);
                                intent.putExtra("gestureTitle", "设置手势密码");
                                startActivity(intent);
//                                finish();
                            }else {
                                showToast(jsonObject.getString("rsmsg"));
                            }
                        } catch (JSONException e) {
                            logInfo("setPassword failed!");
                            e.printStackTrace();
                        }
                    }else{
                        showToast("请检查网络设置");
                    }
                }
            }, argsSet);
        }else{
            etSetPsw.setText("");
            etSetAgain.setText("");
            showToast("您的密码不足八位，请重新输入");
        }
    }
}
