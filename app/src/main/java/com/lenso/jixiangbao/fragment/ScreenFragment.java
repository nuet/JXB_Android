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
        List<String> texts5 = new ArrayList<>();
        List<String> texts6 = new ArrayList<>();
        texts1.add("hello1");
        texts1.add("hello2");
        texts1.add("hello3");
        texts1.add("hello4");
        texts1.add("hello5");
        texts2.add("hello1");
        texts2.add("hello2");
        texts2.add("hello3");
        texts2.add("hello4");
        texts2.add("hello5");
        texts3.add("hello1");
        texts3.add("hello2");
        texts4.add("hello3");
        texts4.add("hello4");
        texts4.add("hello5");
        texts4.add("hello1");
        texts4.add("hello2");
        texts4.add("hello3");
        texts4.add("hello4");
        texts4.add("hello5");
        texts5.add("hello1");
        texts5.add("hello2");
        texts5.add("hello3");
        texts5.add("hello4");
        texts5.add("hello5");
        texts5.add("hello1");
        texts6.add("hello2");
        texts6.add("hello3");
        texts6.add("hello4");
        texts6.add("hello5");
        data.put("hello", texts1);
        data.put("hello_ooo", texts2);
        data.put("hello_da", texts3);
        data.put("hello_cs", texts4);
        data.put("hello_ud", texts5);
        data.put("hello_dd", texts6);
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
