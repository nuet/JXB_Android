package com.lenso.jixiangbao.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.adapter.CreditListAdapter;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.ChoiceList;
import com.lenso.jixiangbao.bean.InvestList;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.Config;

import java.util.HashMap;
import java.util.Map;

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
    PullToRefreshListView lvCreditList;
    private SlidingMenu menu;
    private CreditListAdapter adapter;

//    private ListView listView;
    private boolean LIMIT_ASC = true; //第一次点击为升序排列
    private boolean RATE_ASC = true; //第一次点击为升序排列
    private Gson gson = new Gson();
    private Map<String, String> args = new HashMap<String, String>();
    public static ProgressDialog progressDialog;
    private static Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creditlist, null);
        ButterKnife.bind(this, view);
        initRefreshList();

        context = getActivity();
        initView();

        return view;
    }

    private void initRefreshList() {
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

//        listView = lvCreditList.getRefreshableView();
        adapter = new CreditListAdapter(getActivity(), App.BASE_BEAN.getInvestList().getBorrowList());
//        listView.setAdapter(adapter);
        lvCreditList.getRefreshableView().setAdapter(adapter);

        lvCreditList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                FinancingFragment.page = String.valueOf(Integer.valueOf(FinancingFragment.page) - 1);
                if (Integer.valueOf(FinancingFragment.page) < 1 || Integer.valueOf(FinancingFragment.page) > App.BASE_BEAN.getInvestList().getP().getPages()) {
                    if(Integer.valueOf(FinancingFragment.page) < 1){
                        FinancingFragment.page = "1";
                    }
                    lvCreditList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lvCreditList.onRefreshComplete();
                            showToast("当前已是第一页");
                        }
                    }, 500);
                }else{
                    reLoadBorrowList();
                }
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                FinancingFragment.page = String.valueOf(Integer.valueOf(FinancingFragment.page) + 1);
                if (Integer.valueOf(FinancingFragment.page) < 1 || Integer.valueOf(FinancingFragment.page) > App.BASE_BEAN.getInvestList().getP().getPages()) {
                    if(Integer.valueOf(FinancingFragment.page) > App.BASE_BEAN.getInvestList().getP().getPages()){
                        FinancingFragment.page = String.valueOf(App.BASE_BEAN.getInvestList().getP().getPages());
                    }
                    lvCreditList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lvCreditList.onRefreshComplete();
                            showToast("当前已是最后一页");
                        }
                    }, 500);
                }else{
                    reLoadBorrowList();
                }
            }
        });

        lvCreditList.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChoiceList item = adapter.getListItem(position - 1);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                String url = HTMLInterface.DETAIL + "?borrowid=" + item.getId() + "&app_key=" + Config.getInstance(getActivity().getApplicationContext()).getConfig("app_key");
                Log.i("H5:", "URL:" + url);
                intent.putExtra(JSInterface.H5_URL, url);
                intent.putExtra(JSInterface.H5_TITLE, item.getName());
                intent.putExtra("apr", item.getApr());
                intent.putExtra("intent", JSInterface.DETAIL);
                getActivity().startActivity(intent);
            }
        });

        unselected();
        buttonDefault.setSelected(true);
        progressDialog = new ProgressDialog(getActivity()); // 获取对象
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置样式为圆形样式
        progressDialog.setIcon(R.mipmap.b);
        progressDialog.setTitle("Reminder"); // 设置进度条的标题信息
        progressDialog.setMessage("正在排序中..."); // 设置进度条的提示信息
        progressDialog.setIndeterminate(false); // 设置进度条是否为不明确
        progressDialog.setCancelable(true); // 设置进度条是否按返回键取消
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
                sortDefault();
                break;
            case R.id.button_time_limit:
                unselected();
                view.setSelected(true);
                ivTimeLimit.setVisibility(View.VISIBLE);
                sortLimit(LIMIT_ASC);
                break;
            case R.id.button_annual_rate:
                unselected();
                view.setSelected(true);
                ivAnnualRate.setVisibility(View.VISIBLE);
                sortRate(RATE_ASC);
                break;
            case R.id.button_screen:
                if (menu == null) {
                    menu = ((HomeActivity) getActivity()).getSlidingMenu();
                    if (menu != null) {
                        menu.getContent();
                    }
                }
                if (menu != null)
                    menu.showMenu();
                break;
        }
    }

    private void sortDefault() {
        progressDialog.show();
        FinancingFragment.order = "0";
        reLoadBorrowList();
    }

    private void sortLimit(boolean ASC) {
        progressDialog.show();
        if (ASC) {
            ivTimeLimit.setImageResource(R.mipmap.sx1);
            FinancingFragment.order = "26";
            reLoadBorrowList();
            LIMIT_ASC = false;
        } else {
            ivTimeLimit.setImageResource(R.mipmap.sx2);
            FinancingFragment.order = "-26";
            reLoadBorrowList();
            LIMIT_ASC = true;
        }
        RATE_ASC = true;
    }

    private void sortRate(boolean ASC) {
        progressDialog.show();
        if (ASC) {
            ivAnnualRate.setImageResource(R.mipmap.sx1);
            FinancingFragment.order = "2";
            reLoadBorrowList();
            RATE_ASC = false;
        } else {
            ivAnnualRate.setImageResource(R.mipmap.sx2);
            FinancingFragment.order = "-2";
            reLoadBorrowList();
            RATE_ASC = true;
        }
        LIMIT_ASC = true;
    }

    public void reLoadBorrowList() {
        args.put("s_status", FinancingFragment.s_status);
        args.put("s_repay_way", FinancingFragment.s_repay_way);
        args.put("s_time_limit", FinancingFragment.s_time_limit);
        args.put("s_account", FinancingFragment.s_account);
        args.put("order", FinancingFragment.order);
        args.put("s_type", FinancingFragment.s_type);
        args.put("page", FinancingFragment.page);

        VolleyHttp.getInstance().postParamsJson(ServerInterface.INVEST_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    InvestList investList = gson.fromJson(json, InvestList.class);
                    App.BASE_BEAN.setInvestList(investList);
                    adapter = new CreditListAdapter(context, App.BASE_BEAN.getInvestList().getBorrowList());
                    lvCreditList.getRefreshableView().setAdapter(adapter);
                    if(lvCreditList.isRefreshing()){
                        lvCreditList.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lvCreditList.onRefreshComplete();
                            }
                        }, 500);
                    }
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                }
            }
        }, args);

    }

}
