package com.lenso.jixiangbao.fragment;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.lenso.jixiangbao.R;
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
    @Bind(R.id.btn_tx)
    Button btnTx;
    @Bind(R.id.btn_cz)
    Button btnCz;
    @Bind(R.id.ll_dsze)
    LinearLayout llDsze;
    @Bind(R.id.ll_zjgk)
    LinearLayout llZjgk;
    @Bind(R.id.ll_wdtz)
    LinearLayout llWdtz;
    @Bind(R.id.ll_zqzr)
    LinearLayout llZqzr;
    @Bind(R.id.ll_wdjk)
    LinearLayout llWdjk;
    @Bind(R.id.ll_gd)
    LinearLayout llGd;
    @Bind(R.id.tv_mine_unreadmsg)
    TextView tvMineUnreadmsg;
    @Bind(R.id.tv_mine_usemoney)
    TextView tvMineUsemoney;
    @Bind(R.id.tv_mine_total)
    TextView tvMineTotal;
    @Bind(R.id.tv_mine_origin)
    TextView tvMineOrigin;
    @Bind(R.id.tv_mine_interest)
    TextView tvMineInterest;
    @Bind(R.id.iv_mine_message)
    ImageView ivMineMessage;
    @Bind(R.id.ll_zhxx)
    LinearLayout llZhxx;

    public static UserInfo userInfo;
    private Map args = new HashMap();
    private Map argsign = new HashMap();
    private DecimalFormat df = new DecimalFormat("0.00");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);

        return view;
    }

    public void initData() {
        args.put("app_key", Config.getInstance(getActivity()).getConfig("app_key"));
        VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_USERINFO, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (isConnectSuccess) {
                    Gson gson = new Gson();
                    userInfo = gson.fromJson(json, UserInfo.class);
                    initUI();
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
        } else {
            tvMineUnreadmsg.setText(String.valueOf(userInfo.getUnreadmsg()));
            tvMineUnreadmsg.setBackgroundResource(R.drawable.minefragment_message_count);
        }

        tvMineUsemoney.setText(String.valueOf(df.format(userInfo.getSummary().getAccountUseMoney())));
        tvMineTotal.setText(String.valueOf(df.format(userInfo.getSummary().getCollectTotal())));
        tvMineInterest.setText(String.valueOf(df.format(userInfo.getSummary().getCollectInterest())));
        tvMineOrigin.setText(String.valueOf(df.format(userInfo.getSummary().getCollectTotal() - userInfo.getSummary().getCollectInterest())));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.iv_mine_more,
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
            R.id.ll_dsze,
            R.id.ll_zjgk,
            R.id.ll_wdtz,
            R.id.ll_zqzr,
            R.id.ll_wdjk,
            R.id.ll_zhxx,
            R.id.ll_gd})
    public void onClick(View view) {
        String app_key = Config.getInstance(getActivity()).getConfig("app_key");
        String arg = "?app_key=" + app_key;
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        switch (view.getId()) {
            case R.id.iv_mine_more:
                showToast("more");
                break;
            case R.id.iv_headpic:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZHXX + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "账户信息");
                startActivity(intent);
                break;
            case R.id.tv_username:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZHXX + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "账户信息");
                startActivity(intent);
                break;
            case R.id.usertype:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.USERTYPE + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "实名认证");
                startActivity(intent);
                break;
            case R.id.iv_mine_message:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.MESSAGE + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "消息通知");
                startActivity(intent);
                userInfo.setUnreadmsg(0);
                tvMineUnreadmsg.setText("");
                tvMineUnreadmsg.setBackgroundColor(Color.TRANSPARENT);
                break;
            case R.id.ll_tjyj:
            case R.id.ib_tjyj:
            case R.id.tv_tjyj:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.TJYJ + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "推荐有奖");
                startActivity(intent);
                break;
            case R.id.ll_qd:
            case R.id.ib_qd:
            case R.id.tv_qd:

                argsign.put("app_key", Config.getInstance(getActivity()).getConfig("app_key"));
                VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_USERSIGN, new VolleyHttp.JsonResponseListener() {
                    @Override
                    public void getJson(String json, boolean isConnectSuccess) {
                        if (isConnectSuccess) {
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                if (jsonObject.getString("status").equals("1")) {
                                    showToast("签到成功");
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
                break;
            case R.id.ll_jf:
            case R.id.ib_jf:
            case R.id.tv_jf:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.JF + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "积分商城");
                startActivity(intent);
                break;
            case R.id.btn_tx:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.TX + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "提现");
                startActivity(intent);
                break;
            case R.id.btn_cz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.CZ + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "充值");
                startActivity(intent);
                break;
            case R.id.ll_dsze:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZJGK + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "资金概况");
                startActivity(intent);
                break;
            case R.id.ll_zjgk:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZJGK + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "资金概况");
                startActivity(intent);
                break;
            case R.id.ll_wdtz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.WDTZ + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "我的投资");
                startActivity(intent);
                break;
            case R.id.ll_zqzr:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZQZR + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "债权转让");
                startActivity(intent);
                break;
            case R.id.ll_wdjk:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.WDJK + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "我的借款");
                startActivity(intent);
                break;
            case R.id.ll_zhxx:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZHXX + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "账户信息");
                startActivity(intent);
                break;
            case R.id.ll_gd:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.GD + arg);
                intent.putExtra(HTMLInterface.H5_TITLE, "更多");
                startActivity(intent);
                break;
        }
    }

}
