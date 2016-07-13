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
import java.util.LinkedHashMap;
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
    private int i = 0;
    private Map<String, List<String>> data;
    private int type = 0;//列表类型  1为债权列表  2为转让专区

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if(adapter == null){
            adapter = new ScreenListAdapter(getContext(), data);
        }
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

                if(type == 1){
                    CreditListFragment.s_status = String.valueOf(adapter.getArgs().get(0));
                    CreditListFragment.s_repay_way = String.valueOf(adapter.getArgs().get(1));
                    CreditListFragment.s_time_limit = String.valueOf(adapter.getArgs().get(2));
                    CreditListFragment.s_account = String.valueOf(adapter.getArgs().get(3));
                    ((HomeActivity)getActivity()).sortBorrowList();
                }
                if(type == 2){
                    TransferListFragment.s_status = String.valueOf(adapter.getArgs().get(0));
                    TransferListFragment.s_repay_way = String.valueOf(adapter.getArgs().get(1));
                    TransferListFragment.s_time_limit = String.valueOf(adapter.getArgs().get(2));
                    TransferListFragment.s_account = String.valueOf(adapter.getArgs().get(3));
                    ((HomeActivity)getActivity()).sortTransferList();
                }
                break;
        }
    }

    private void cancelShowMenu() {
        ((HomeActivity) getActivity()).getSlidingMenu().showContent();
    }

    public void initData(Map<String, List<String>> data,int type) {
        this.data = data;
        this.type = type;
    }
}
