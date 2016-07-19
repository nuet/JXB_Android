package com.lenso.jixiangbao.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.view.iOSAlertDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/7/6.
 */
public class FindFragment extends BaseFragment {
    @Bind(R.id.find_kf)
    TextView findKf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        ButterKnife.bind(this, view);

        findKf.setText(App.BASE_BEAN.getFuwutel());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_find1, R.id.ll_find2, R.id.ll_find3, R.id.ll_find4, R.id.ll_gywm, R.id.ll_xxsz, R.id.ll_kfrx, R.id.ll_aqbz, R.id.ll_tjyj})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        switch (view.getId()) {
            case R.id.ll_find1:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_1);
                intent.putExtra(HTMLInterface.H5_TITLE, "新手起航");
                startActivity(intent);
                break;
            case R.id.ll_find2:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_2);
                intent.putExtra(HTMLInterface.H5_TITLE, "热门活动");
                startActivity(intent);
                break;
            case R.id.ll_find3:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_3);
                intent.putExtra(HTMLInterface.H5_TITLE, "最新动态");
                startActivity(intent);
                break;
            case R.id.ll_find4:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_4);
                intent.putExtra(HTMLInterface.H5_TITLE, "平台介绍");
                startActivity(intent);
                break;
            case R.id.ll_gywm:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_5);
                intent.putExtra(HTMLInterface.H5_TITLE, "关于我们");
                startActivity(intent);
                break;
            case R.id.ll_xxsz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_6);
                intent.putExtra(HTMLInterface.H5_TITLE, "消息设置");
                startActivity(intent);
                break;
            case R.id.ll_kfrx:
                new iOSAlertDialog(getActivity()).builder()
                        .setTitle("温馨提示")
                        .setMsg("您要拨打客服热线吗?")
                        .setCancelable(false)
                        .setPositiveButton("呼叫", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                        callPhone(App.BASE_BEAN.getFuwutel());
                                    } else {
                                        showToast("没有权限：请为" + getResources().getString(R.string.app_name) + "开启拨打电话权限");
                                    }
                                } else {
                                    callPhone(App.BASE_BEAN.getFuwutel());
                                }
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                break;
            case R.id.ll_aqbz:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_8);
                intent.putExtra(HTMLInterface.H5_TITLE, "安全保障");
                startActivity(intent);
                break;
            case R.id.ll_tjyj:
                intent.putExtra(HTMLInterface.H5_URL, HTMLInterface.FIND_9);
                intent.putExtra(HTMLInterface.H5_TITLE, "推荐有奖");
                startActivity(intent);
                break;
        }
    }

    private void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //noinspection ResourceType
        startActivity(intent);
    }

}
