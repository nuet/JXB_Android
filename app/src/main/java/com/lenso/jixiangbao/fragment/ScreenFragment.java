package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.ScreenListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by king on 2016/5/20.
 */
public class ScreenFragment extends BaseFragment {
    @Bind(R.id.lv_screen)
    ListView lvScreen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, null);
        ButterKnife.bind(this, view);
        initView(inflater);
        return view;
    }

    private void initView(LayoutInflater inflater) {
        lvScreen.setAdapter(new ScreenListAdapter(inflater));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_screen_cancel, R.id.tv_screen_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_screen_cancel:
                break;
            case R.id.tv_screen_reset:
                break;
        }
    }
}
