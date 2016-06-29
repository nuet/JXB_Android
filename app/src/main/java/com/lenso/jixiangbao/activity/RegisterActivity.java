package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.HTMLInterface;
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
 * Created by Chung on 2016/5/19.
 */
public class RegisterActivity extends BaseActivity {
    @Bind(R.id.iv_register_agree)
    ImageView ivRegisterAgree;
    @Bind(R.id.et_register_psw)
    EditText etRegisterPsw;
    @Bind(R.id.top_menu_bar_register)
    TopMenuBar topMenuBarRegister;
    @Bind(R.id.btn_register_confirm)
    Button btnRegisterConfirm;
    @Bind(R.id.tv_register_code)
    TextView tvRegisterCode;
    @Bind(R.id.tv_register_tips)
    TextView tvRegisterTips;
    @Bind(R.id.et_register_code)
    EditText etRegisterCode;

    private Map agrsCode = new HashMap();
    private Map<String, String> agrsRegister = new HashMap();
    private Intent getIntent;
    private String mobile;
    private static boolean BOXCHECKED = true;//默认同意
    private static boolean EYECLICKED = false;//默认暗文
    private static String app_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        topMenuBarRegister.setMenuTopPadding(statusHeight);

        getIntent = getIntent();
        mobile = getIntent.getStringExtra("mobile");

        topMenuBarRegister.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_register_code, R.id.iv_register_agree, R.id.btn_register_confirm, R.id.btn_register_eye, R.id.tv_register_yhxy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_code:
                tvRegisterTips.setText("短信已发送至" + mobile.substring(0, 3) + "****" + mobile.substring(7, 11));
                agrsCode.put("mobile", mobile);
                VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_GETPHONECODE, new VolleyHttp.JsonResponseListener() {
                    @Override
                    public void getJson(String json, boolean isConnectSuccess) {
                        if (isConnectSuccess) {
                            try {
                                logInfo("getcode"+json);
                                JSONObject jsonObject = new JSONObject(json);
                                if (jsonObject.getString("status").equals("1")) {
                                    logInfo("get phone code succeed!");
                                }
                            } catch (JSONException e) {
                                logInfo("get phone code http error!");
                                e.printStackTrace();
                            }
                        } else {
                            showToast("请检查网络设置");
                        }
                    }
                }, agrsCode);
                tvRegisterCode.setClickable(false);
                new CountDownTimer(60000, 1000) {//总时间， 间隔时间
                    public void onTick(long millisUntilFinished) {
                        tvRegisterCode.setText(millisUntilFinished / 1000 + "s后重试");
                        tvRegisterCode.setTextColor(Color.parseColor("#BFBFBF"));
                    }

                    public void onFinish() {
                        tvRegisterCode.setText("获取");
                        tvRegisterCode.setTextColor(Color.parseColor("#669EFF"));
                        tvRegisterCode.setClickable(true);
                    }
                }.start();
                break;
            case R.id.iv_register_agree:
                if (!BOXCHECKED) {
                    ivRegisterAgree.setImageResource(R.mipmap.checkbox_checked);
                    BOXCHECKED = true;
                    btnRegisterConfirm.setBackgroundResource(R.drawable.shape_circle_btn_orange);
                    btnRegisterConfirm.setClickable(true);
                } else {
                    ivRegisterAgree.setImageResource(R.mipmap.checkbox_unchecked);
                    BOXCHECKED = false;
                    btnRegisterConfirm.setBackgroundResource(R.drawable.shape_circle_btn_gray);
                    btnRegisterConfirm.setClickable(false);
                }
                break;
            case R.id.btn_register_eye:
                if (!EYECLICKED) {
                    etRegisterPsw.setInputType(InputType.TYPE_CLASS_TEXT);
                    etRegisterPsw.setSelection(etRegisterPsw.getText().length());
                    EYECLICKED = true;
                } else {
                    etRegisterPsw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etRegisterPsw.setSelection(etRegisterPsw.getText().length());
                    EYECLICKED = false;
                }
                break;
            case R.id.btn_register_confirm:
                if (CheckPassword.check(etRegisterPsw.getText().toString().trim())) {
                    agrsRegister.put("phone", mobile);
                    agrsRegister.put("valicode", etRegisterCode.getText().toString().trim());
                    agrsRegister.put("password", etRegisterPsw.getText().toString().trim());
//                agrsRegister.put("invite_code", "");
                    agrsRegister.put("actionType", "register");
                    logInfo(agrsRegister.toString());
                    VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_REGISTER, new VolleyHttp.JsonResponseListener() {
                        @Override
                        public void getJson(String json, boolean isConnectSuccess) {
                            if (isConnectSuccess) {
                                try {
                                    JSONObject jsonObject = new JSONObject(json);
                                    if (jsonObject.getString("status").equals("1")) {
                                        app_key = jsonObject.getString("app_key");
                                        Config.getInstance(RegisterActivity.this).putConfig("app_key", app_key);
                                        Config.getInstance(RegisterActivity.this).putConfig("phone", mobile);
                                        Intent intent = new Intent();
                                        intent.setClass(RegisterActivity.this, IdentifyActivity.class);
                                        startActivity(intent);
//                                        finish();
                                    } else {
                                        showToast(jsonObject.getString("rsmsg"));
                                    }
                                } catch (JSONException e) {
                                    logInfo("register failed");
                                    e.printStackTrace();
                                }
                            } else {
                                showToast("请检查网络设置");
                            }
                        }
                    }, agrsRegister);
                } else {
                    etRegisterPsw.setText("");
                    showToast("您的密码不足八位，请重新输入");
                }
                break;
            case R.id.tv_register_yhxy:
                Intent intentYHXY = new Intent(RegisterActivity.this, WebViewActivity.class);
                intentYHXY.putExtra(HTMLInterface.H5_URL, HTMLInterface.YHXY);
                intentYHXY.putExtra(HTMLInterface.H5_TITLE, "用户协议");
                startActivity(intentYHXY);
                break;
        }
    }
}
