package com.lenso.jixiangbao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.adapter.CreditListAdapter;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.bean.ChoiceList;
import com.lenso.jixiangbao.util.Config;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import king.dominic.slidingmenu.SlidingMenu;

/**
 * Created by king on 2016/5/19.
 *
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
    PullToRefreshListView lvCreditList;
    private SlidingMenu menu;
    private CreditListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creditlist, null);
        ButterKnife.bind(this, view);
        initRefreshList();
        initView();
        return view;
    }
    private void initRefreshList(){
        LoadingLayoutProxy top = (LoadingLayoutProxy) lvCreditList.getLoadingLayoutProxy(true, false);
        top.setPullLabel("下拉即可刷新...");
        top.setRefreshingLabel("刷新中...");
        top.setReleaseLabel("释放即可刷新...");
        top.setLoadingDrawable(getActivity().getResources().getDrawable(R.mipmap.sx2));
        LoadingLayoutProxy bottom = (LoadingLayoutProxy) lvCreditList.getLoadingLayoutProxy(false, true);
        bottom.setPullLabel("上拉即可加载...");
        bottom.setRefreshingLabel("加载中...");
        bottom.setReleaseLabel("释放即可加载...");
        bottom.setLoadingDrawable(getActivity().getResources().getDrawable(R.mipmap.sx2));
    }
    private void initView() {
        ListView listView = lvCreditList.getRefreshableView();
        this.adapter=new CreditListAdapter(getActivity(), App.BASE_BEAN.getBorrowList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChoiceList item = adapter.getListItem(position);
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                String url = HTMLInterface.DETAIL + "?borrowid=" + item.getId() + "&app_key=" + Config.getInstance(getActivity().getApplicationContext()).getConfig("app_key");
                Log.i("H5:", "URL:"+url);
                intent.putExtra(JSInterface.H5_URL, url);
                intent.putExtra(JSInterface.H5_TITLE, item.getName());
                intent.putExtra("intent",JSInterface.DETAIL);
                getActivity().startActivity(intent);
            }
        });
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
