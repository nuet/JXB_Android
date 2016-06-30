package com.lenso.jixiangbao.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.LoginOrRegisterActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.UserInfo;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/11.
 */
public class MineFragment extends BaseFragment {


    @Bind(R.id.iv_headpic)
    ImageView ivHeadpic;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.usertype)
    TextView usertype;
    @Bind(R.id.iv_mine_message)
    ImageView ivMineMessage;
    @Bind(R.id.tv_mine_unreadmsg)
    TextView tvMineUnreadmsg;
    @Bind(R.id.rl_mine_top)
    RelativeLayout rlMineTop;
    @Bind(R.id.tv_zcze1)
    TextView tvZcze1;
    @Bind(R.id.tv_zcze2)
    TextView tvZcze2;
    @Bind(R.id.ll_zcze)
    LinearLayout llZcze;
    @Bind(R.id.ib_tjyj)
    ImageButton ibTjyj;
    @Bind(R.id.tv_tjyj)
    TextView tvTjyj;
    @Bind(R.id.ll_tjyj)
    LinearLayout llTjyj;
    @Bind(R.id.ib_qd)
    ImageButton ibQd;
    @Bind(R.id.tv_qd)
    TextView tvQd;
    @Bind(R.id.ll_qd)
    LinearLayout llQd;
    @Bind(R.id.ib_jf)
    ImageButton ibJf;
    @Bind(R.id.tv_jf)
    TextView tvJf;
    @Bind(R.id.ll_jf)
    LinearLayout llJf;
    @Bind(R.id.tv_mine_usemoney)
    TextView tvMineUsemoney;
    @Bind(R.id.btn_tx)
    Button btnTx;
    @Bind(R.id.btn_cz)
    Button btnCz;
    @Bind(R.id.tv_mine_ljsy)
    TextView tvMineLjsy;
    @Bind(R.id.ll_ljsy)
    LinearLayout llLjsy;
    @Bind(R.id.ll_zjgk)
    LinearLayout llZjgk;
    @Bind(R.id.ll_wdtz)
    LinearLayout llWdtz;
    @Bind(R.id.ll_zdtb)
    LinearLayout llZdtb;
    @Bind(R.id.ll_zqzr)
    LinearLayout llZqzr;
    @Bind(R.id.ll_wdjk)
    LinearLayout llWdjk;
    @Bind(R.id.ll_zhxx)
    LinearLayout llZhxx;
    @Bind(R.id.ll_gd)
    LinearLayout llGd;

    public static UserInfo userInfo;
    private Map args = new HashMap();
    private Map argsign = new HashMap();
    private DecimalFormat df = new DecimalFormat("0.00");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);
        rlMineTop.setPadding(10, Integer.parseInt(Config.getInstance(getActivity()).getConfig("statusHeight")), 10, 0);
        return view;
    }

    public void initData() {
        args.put("app_key", Config.getInstance(getActivity()).getConfig("app_key"));
        VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_USERINFO, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (isConnectSuccess) {
                    try {
                        Gson gson = new Gson();
                        userInfo = gson.fromJson(json, UserInfo.class);
                        initUI();
                    } catch (Exception e) {
                        e.printStackTrace();
                        logInfo("GSon解析出错");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        builder.setTitle("温馨提示");
                        builder.setMessage("您的账号已被迫离线，是否重新登录？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), LoginOrRegisterActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
                    }
                } else {
                    showToast("请检查网络设置");
                }
            }

        }, args);
    }

    public void initUI() {
        tvUsername.setText(userInfo.getDetailuser().getUsername());
        if (userInfo.getDetailuser().getReal_status().equals("0")) {
            usertype.setText("尚未实名认证");
        } else {
            usertype.setText(userInfo.getDetailuser().getTypename());
        }

        if (userInfo.getUnreadmsg() == 0) {
            tvMineUnreadmsg.setText("");
            tvMineUnreadmsg.setBackgroundColor(Color.TRANSPARENT);
            tvMineUnreadmsg.setVisibility(View.INVISIBLE);
        } else {
            tvMineUnreadmsg.setText(String.valueOf(userInfo.getUnreadmsg()));
            tvMineUnreadmsg.setBackgroundResource(R.drawable.minefragment_message_count);
            tvMineUnreadmsg.setVisibility(View.VISIBLE);
        }

        tvZcze1.setText(String.valueOf(df.format(userInfo.getAccount().getTotal())));

        if (userInfo.getSigned() == 0) {
            ibQd.setImageResource(R.mipmap.minefragment_qd);
            ibQd.setClickable(true);
        } else {
            ibQd.setImageResource(R.mipmap.minefragment_yqd);
            ibQd.setClickable(false);
        }

        tvMineUsemoney.setText(String.valueOf(df.format(userInfo.getAccount().getUse_money())));

        tvMineLjsy.setText(String.valueOf(df.format(userInfo.getSummary().getTotal_income())));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.ll_ljsy,
            R.id.ll_zcze,
            R.id.iv_headpic,
            R.id.tv_username,
            R.id.usertype,
            R.id.iv_mine_message,
            R.id.ll_tjyj,
            R.id.ib_tjyj,
            R.id.tv_tjyj,
            R.id.ll_qd,
            R.id.ib_qd,
            R.id.tv_qd,
            R.id.ll_jf,
            R.id.ib_jf,
            R.id.tv_jf,
            R.id.btn_tx,
            R.id.btn_cz,
            R.id.ll_zjgk,
            R.id.ll_wdtz,
            R.id.ll_zdtb,
            R.id.ll_zqzr,
            R.id.ll_wdjk,
            R.id.ll_zhxx,
            R.id.ll_gd})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        switch (view.getId()) {
            case R.id.ll_zhxx:
            case R.id.iv_headpic:
            case R.id.tv_username:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZHXX + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                logInfo("chung" + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "账户信息");
                startActivity(intent);
                break;
            case R.id.usertype:
                if (userInfo.getDetailuser().getReal_status().equals("0")) {
                    intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.USERTYPE + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                    intent.putExtra(HTMLInterface.H5_TITLE, "实名认证");
                    startActivity(intent);
                }
                break;
            case R.id.iv_mine_message:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.MESSAGE + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "消息通知");
                startActivity(intent);
                userInfo.setUnreadmsg(0);
                tvMineUnreadmsg.setText("");
                tvMineUnreadmsg.setBackgroundColor(Color.TRANSPARENT);
                break;
            case R.id.ll_tjyj:
            case R.id.ib_tjyj:
            case R.id.tv_tjyj:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.TJYJ + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "推荐有奖");
                startActivity(intent);
                break;
            case R.id.ll_qd:
            case R.id.ib_qd:
            case R.id.tv_qd:
                if (userInfo.getSigned() == 0) {
                    argsign.put("app_key", Config.getInstance(getActivity()).getConfig("app_key"));
                    VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_USERSIGN, new VolleyHttp.JsonResponseListener() {
                        @Override
                        public void getJson(String json, boolean isConnectSuccess) {
                            if (isConnectSuccess) {
                                try {
                                    JSONObject jsonObject = new JSONObject(json);
                                    if (jsonObject.getString("status").equals("1")) {
                                        showToast("签到成功");
                                        userInfo.setSigned(1);
                                    } else {
                                        showToast(jsonObject.getString("rsmsg"));
                                    }
                                } catch (JSONException e) {
                                    logInfo("sign error!");
                                    e.printStackTrace();
                                }
                            } else {
                                showToast("请检查网络设置！");
                            }
                        }
                    }, argsign);
                    ibQd.setImageResource(R.mipmap.minefragment_yqd);
                } else {
                    showToast("您今日已签到，明天再来吧！");
                }
                break;
            case R.id.ll_jf:
            case R.id.ib_jf:
            case R.id.tv_jf:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.JF + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "积分商城");
                startActivity(intent);
                break;
            case R.id.btn_tx:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.TX + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "提现");
                startActivity(intent);
                break;
            case R.id.btn_cz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.CZ + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "充值");
                startActivity(intent);
                break;
            case R.id.ll_ljsy:
            case R.id.ll_zcze:
            case R.id.ll_zjgk:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZJGK + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "资金概况");
                startActivity(intent);
                break;
            case R.id.ll_wdtz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.WDTZ + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "我的投资");
                startActivity(intent);
                break;
            case R.id.ll_zdtb:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZDTB + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "自动投标");
                startActivity(intent);
                break;
            case R.id.ll_zqzr:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZQZR + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "债权转让");
                startActivity(intent);
                break;
            case R.id.ll_wdjk:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.WDJK + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "我的借款");
                startActivity(intent);
                break;
            case R.id.ll_gd:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.GD + "?app_key=" + Config.getInstance(getActivity()).getConfig("app_key"));
                intent.putExtra(HTMLInterface.H5_TITLE, "更多");
                startActivity(intent);
                break;
        }
    }

}
