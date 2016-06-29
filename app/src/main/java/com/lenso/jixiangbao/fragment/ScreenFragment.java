package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.adapter.ScreenListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by king on 2016/5/20.
 */
public class ScreenFragment extends BaseFragment {
    @Bind(R.id.lv_screen)
    ListView lvScreen;
    private ScreenListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Map<String, List<String>> data = new HashMap<>();
        List<String> texts1 = new ArrayList<>();
        List<String> texts2 = new ArrayList<>();
        List<String> texts3 = new ArrayList<>();
        List<String> texts4 = new ArrayList<>();
        texts1.add("全部");
        texts1.add("招标中");
        texts1.add("还款中");
        texts1.add("待审核");
        texts1.add("已成功");

        texts2.add("不限");
        texts2.add("等额本息");
        texts2.add("一次性还本付息");
        texts2.add("每月还息到期还本");

        texts3.add("不限");
        texts3.add("1个月以下");
        texts3.add("1-2个月");
        texts3.add("3-4个月");
        texts3.add("5-6个月");
        texts3.add("6个月以上");

        texts4.add("不限");
        texts4.add("5万以下");
        texts4.add("5-10万");
        texts4.add("10-30万");
        texts4.add("30-50万");
        texts4.add("50万以上");

        data.put("借款状态", texts1);
        data.put("还款方式", texts2);
        data.put("借款期限", texts3);
        data.put("借款金额", texts4);

        adapter = new ScreenListAdapter(getContext(), data);
        lvScreen.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_screen_cancel, R.id.tv_screen_reset,R.id.tv_screen_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_screen_cancel:
                cancelShowMenu();
                break;
            case R.id.tv_screen_reset:
                adapter.resetScreenList();
                break;
            case R.id.tv_screen_ok:
                cancelShowMenu();
                break;
        }
    }

    private void cancelShowMenu() {
        ((HomeActivity) getActivity()).getSlidingMenu().showContent();
    }

}
