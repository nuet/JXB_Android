package com.lenso.jixiangbao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
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
public class ForgetPasswordActivity extends BaseActivity {
    @Bind(R.id.top_menu_bar_forget)
    TopMenuBar topMenuBarForget;
    @Bind(R.id.tv_forget_tips)
    TextView tvForgetTips;
    @Bind(R.id.et_forget_code)
    EditText etForgetCode;
    @Bind(R.id.tv_forget_get)
    TextView tvForgetGet;
    @Bind(R.id.btn_forget_confirm)
    Button btnForgetConfirm;

    private Map agrsCode = new HashMap();
    private Map argsGet = new HashMap();
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        topMenuBarForget.setMenuTopPadding(statusHeight);

        Intent getIntent = getIntent();
        mobile = getIntent.getStringExtra("mobile");

        topMenuBarForget.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick({R.id.tv_forget_get, R.id.btn_forget_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_get:
                tvForgetTips.setText("短信已发送至" + mobile.substring(0, 3) + "****" + mobile.substring(7, 11));
                agrsCode.put("mobile", mobile);
                VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_GETPHONECODE, new VolleyHttp.JsonResponseListener() {
                    @Override
                    public void getJson(String json, boolean isConnectSuccess) {
                        if(isConnectSuccess){
                            try {
                                logInfo(json);
                                JSONObject jsonObject = new JSONObject(json);
                                if (jsonObject.getString("status").equals("1")) {
                                    logInfo("get phone code succeed!");
                                }
                            } catch (JSONException e) {
                                logInfo("get phone code http error!");
                                e.printStackTrace();
                            }
                        }else {
                            showToast("请检查网络设置");
                        }
                    }
                }, agrsCode);
                tvForgetGet.setClickable(false);
                new CountDownTimer(60000, 1000) {//总时间， 间隔时间
                    public void onTick(long millisUntilFinished) {
                        tvForgetGet.setText(millisUntilFinished / 1000 + "s后重试");
                        tvForgetGet.setTextColor(Color.parseColor("#BFBFBF"));
                    }
                    public void onFinish() {
                        tvForgetGet.setText("获取");
                        tvForgetGet.setTextColor(Color.parseColor("#669EFF"));
                        tvForgetGet.setClickable(true);
                    }
                }.start();
                break;
            case R.id.btn_forget_confirm:
                argsGet.put("phone", mobile);
                argsGet.put("validcode", etForgetCode.getText().toString().trim());
                argsGet.put("getpwdtype", "pwd");
                argsGet.put("actionType", "getpwd");
                logInfo(argsGet.toString());
                VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_FORGETPSW, new VolleyHttp.JsonResponseListener() {
                    @Override
                    public void getJson(String json, boolean isConnectSuccess) {
                        if(isConnectSuccess){
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                if(jsonObject.getString("status").equals("1")){
                                    Intent intent = new Intent();
                                    intent.setClass(ForgetPasswordActivity.this, SetPasswordActivity.class);
                                    intent.putExtra("id", jsonObject.getString("id"));
//                                    logInfo(jsonObject.getString("id"));
                                    startActivity(intent);
//                                    finish();
                                    logInfo("verify succeed");
                                }else {
                                    showToast(jsonObject.getString("rsmsg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            showToast("请检查网络设置");
                        }
                    }
                }, argsGet);
                break;
        }
    }

}
