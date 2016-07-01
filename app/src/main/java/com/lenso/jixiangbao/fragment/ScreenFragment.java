package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.adapter.ScreenListAdapter;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.http.VolleyHttp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private Map<String, List<String>> data;
//    private List<String> keyList= new ArrayList<>();
//    private List<String> sortArg = new ArrayList<>();
    private Map<String,String> args = new HashMap<String,String>();
    private int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        data = new LinkedHashMap<>();

        List<String> texts = new ArrayList<>();
        texts.add("全部");
        texts.add("招标中");
        texts.add("还款中");
        texts.add("待审核");
        texts.add("已成功");
        data.put("借款状态", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("等额本息");
        texts.add("一次性还本付息");
        texts.add("每月还息到期还本");
        data.put("还款方式", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("1个月以下");
        texts.add("1-2个月");
        texts.add("3-4个月");
        texts.add("5-6个月");
        texts.add("6个月以上");
        data.put("借款期限", texts);

        texts = new ArrayList<>();
        texts.add("不限");
        texts.add("5万以下");
        texts.add("5-10万");
        texts.add("10-30万");
        texts.add("30-50万");
        texts.add("50万以上");
        data.put("借款金额", texts);

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

                args.put("s_status", String.valueOf(adapter.getArgs().get(0)));
                args.put("s_repay_way",String.valueOf(adapter.getArgs().get(1)));
                args.put("s_time_limit",String.valueOf(adapter.getArgs().get(2)));
                args.put("s_account",String.valueOf(adapter.getArgs().get(3)));
                args.put("order",FinancingFragment.order);
                args.put("s_type",FinancingFragment.s_type);

                logInfo("ScreenFragment" + args.toString());

                ((HomeActivity)getActivity()).sortBorrowList(args);
                break;
        }
    }

    private void cancelShowMenu() {
        ((HomeActivity) getActivity()).getSlidingMenu().showContent();
    }

}
