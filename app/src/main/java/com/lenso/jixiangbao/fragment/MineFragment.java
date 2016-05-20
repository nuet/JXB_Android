package com.lenso.jixiangbao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.api.HTMLInterface;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/11.
 */
public class MineFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.iv_headpic,
            R.id.tv_username,
            R.id.usertype,
            R.id.fl_message,
            R.id.ll_ljty,
            R.id.tv_ljty1,
            R.id.tv_ljty2,
            R.id.btn_ljty,
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
            R.id.ll_tyb,
            R.id.ll_zhxx})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        switch (view.getId()) {
            case R.id.iv_headpic:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZHXX);
                intent.putExtra(HTMLInterface.H5_TITLE, "账户信息");
                startActivity(intent);
                break;
            case R.id.tv_username:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZHXX);
                intent.putExtra(HTMLInterface.H5_TITLE, "账户信息");
                startActivity(intent);
                break;
            case R.id.usertype:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.USERTYPE);
                intent.putExtra(HTMLInterface.H5_TITLE, "实名认证");
                startActivity(intent);
                break;
            case R.id.fl_message:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.MESSAGE);
                intent.putExtra(HTMLInterface.H5_TITLE, "消息通知");
                startActivity(intent);
                break;
            case R.id.ll_ljty:
            case R.id.tv_ljty1:
            case R.id.tv_ljty2:
            case R.id.btn_ljty:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.LJTY);
                intent.putExtra(HTMLInterface.H5_TITLE, "体验宝");
                startActivity(intent);
                break;
            case R.id.ll_tjyj:
            case R.id.ib_tjyj:
            case R.id.tv_tjyj:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.TJYJ);
                intent.putExtra(HTMLInterface.H5_TITLE, "推荐有奖");
                startActivity(intent);
                break;
            case R.id.ll_qd:
            case R.id.ib_qd:
            case R.id.tv_qd:
                showToast("签到成功");
                break;
            case R.id.ll_jf:
            case R.id.ib_jf:
            case R.id.tv_jf:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.JF);
                intent.putExtra(HTMLInterface.H5_TITLE, "积分商城");
                startActivity(intent);
                break;
            case R.id.btn_tx:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.TX);
                intent.putExtra(HTMLInterface.H5_TITLE, "提现");
                startActivity(intent);
                break;
            case R.id.btn_cz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.CZ);
                intent.putExtra(HTMLInterface.H5_TITLE, "充值");
                startActivity(intent);
                break;
            case R.id.ll_dsze:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZJGK);
                intent.putExtra(HTMLInterface.H5_TITLE, "资金概况");
                startActivity(intent);
                break;
            case R.id.ll_zjgk:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZJGK);
                intent.putExtra(HTMLInterface.H5_TITLE, "资金概况");
                startActivity(intent);
                break;
            case R.id.ll_wdtz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.WDTZ);
                intent.putExtra(HTMLInterface.H5_TITLE, "我的投资");
                startActivity(intent);
                break;
            case R.id.ll_zqzr:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZQZR);
                intent.putExtra(HTMLInterface.H5_TITLE, "债权转让");
                startActivity(intent);
                break;
            case R.id.ll_wdjk:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.WDJK);
                intent.putExtra(HTMLInterface.H5_TITLE, "我的借款");
                startActivity(intent);
                break;
            case R.id.ll_tyb:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.TYB);
                intent.putExtra(HTMLInterface.H5_TITLE, "体验宝");
                startActivity(intent);
                break;
            case R.id.ll_zhxx:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.ZHXX);
                intent.putExtra(HTMLInterface.H5_TITLE, "账户信息");
                startActivity(intent);
                break;
        }
    }
}
