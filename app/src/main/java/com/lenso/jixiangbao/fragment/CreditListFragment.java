package com.lenso.jixiangbao.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.adapter.CreditListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import king.dominic.slidingmenu.SlidingMenu;

/**
 * Created by king on 2016/5/19.
 */
public class CreditListFragment extends BaseFragment {


    @Bind(R.id.button_default)
    TextView buttonDefault;
    @Bind(R.id.tv_time_limit)
    TextView tvTimeLimit;
    @Bind(R.id.iv_time_limit)
    ImageView ivTimeLimit;
    @Bind(R.id.button_time_limit)
    RelativeLayout buttonTimeLimit;
    @Bind(R.id.tv_annual_rate)
    TextView tvAnnualRate;
    @Bind(R.id.iv_annual_rate)
    ImageView ivAnnualRate;
    @Bind(R.id.button_annual_rate)
    RelativeLayout buttonAnnualRate;
    @Bind(R.id.button_screen)
    ImageView buttonScreen;
    @Bind(R.id.lv_credit_list)
    ListView lvCreditList;
    private SlidingMenu menu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creditlist, null);
        ButterKnife.bind(this, view);
        initView(inflater);
        return view;
    }

    private void initView(LayoutInflater inflater) {
        lvCreditList.setAdapter(new CreditListAdapter(getActivity(), App.BASE_BEAN.getBorrowList()));
        lvCreditList.addFooterView(inflater.inflate(R.layout.item_safe_footer, null));
        unselected();
        buttonDefault.setSelected(true);
    }

    private void unselected() {
        buttonDefault.setSelected(false);
        buttonAnnualRate.setSelected(false);
        ivAnnualRate.setVisibility(View.INVISIBLE);
        buttonTimeLimit.setSelected(false);
        ivTimeLimit.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @OnClick({R.id.button_default, R.id.button_time_limit, R.id.button_annual_rate, R.id.button_screen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_default:
                unselected();
                view.setSelected(true);
                break;
            case R.id.button_time_limit:
                unselected();
                view.setSelected(true);
                ivTimeLimit.setVisibility(View.VISIBLE);
                break;
            case R.id.button_annual_rate:
                unselected();
                view.setSelected(true);
                ivAnnualRate.setVisibility(View.VISIBLE);
                break;
            case R.id.button_screen:
                if(menu==null){
                    menu=((HomeActivity)getActivity()).getSlidingMenu();
                    if(menu!=null){
                        menu.getContent();
                    }
                }
                if (menu != null)
                    menu.showMenu();
                break;
        }
    }
}
