package com.lenso.jixiangbao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;
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
public class LoginOrRegisterActivity extends BaseActivity {

    @Bind(R.id.et_login_register)
    EditText etLoginRegister;
    @Bind(R.id.top_menu_bar_login_register)
    TopMenuBar topMenuBarLoginRegister;

    private Map args = new HashMap();
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);

//        topMenuBarLoginRegister.setMenuTopPadding(statusHeight);

        etLoginRegister.setFocusable(true);
        etLoginRegister.setFocusableInTouchMode(true);
        etLoginRegister.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(etLoginRegister, InputMethodManager.SHOW_FORCED);

        topMenuBarLoginRegister.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_login_register)
    public void onClick(View view) {
        if(CommonUtils.isFastClick()){
            return;
        }
        if(CommonUtils.checkPhoneNum(etLoginRegister.getText().toString().trim())){
            args.put("phone", etLoginRegister.getText().toString().trim());
            VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_ISPHONEREGISTER, new VolleyHttp.JsonResponseListener() {
                        @Override
                        public void getJson(String json, boolean isConnectSuccess) {
//                        logInfo(json);
                            if (isConnectSuccess) {
                                try {
                                    JSONObject jsonObject = new JSONObject(json);
                                    if (jsonObject.getString("status").equals("1")) {
                                        if (jsonObject.getString("reged").equals("1")) {
                                            intent.setClass(LoginOrRegisterActivity.this, LoginActivity.class);
                                            intent.putExtra("mobile", etLoginRegister.getText().toString().trim());
                                        } else {
                                            intent.setClass(LoginOrRegisterActivity.this, RegisterActivity.class);
                                            intent.putExtra("mobile", etLoginRegister.getText().toString().trim());
                                        }
                                        startActivity(intent);
//                                    finish();
                                    } else {
                                        showToast(jsonObject.getString("rsmsg"));
                                    }
                                } catch (JSONException e) {
                                    logInfo("next http error");
                                    e.printStackTrace();
                                }
                            } else {
                                showToast("请检查网络设置");
                            }

                        }
                    }, args
            );
        } else {
            showToast("请输入正确的手机号");
        }

    }

}
