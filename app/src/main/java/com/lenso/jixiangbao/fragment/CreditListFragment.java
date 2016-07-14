package com.lenso.jixiangbao.fragment;

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
import com.kaopiz.kprogresshud.KProgressHUD;
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

    public static String s_status = "0";
    public static String s_repay_way = "0";
    public static String s_time_limit = "0";
    public static String s_account = "0";
    public static String order = "0";
    public static String s_type = "115";
    public static String pageNum = "1";

    private SlidingMenu menu;
    private CreditListAdapter adapter;

    private boolean LIMIT_ASC = true; //第一次点击为升序排列
    private boolean RATE_ASC = true; //第一次点击为升序排列
    private Gson gson = new Gson();
    private Map<String, String> args = new HashMap<String, String>();
    private static KProgressHUD progressDialog;
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
        if (App.BASE_BEAN == null || App.BASE_BEAN.getInvestList() == null) {
            return;
        } else {
            adapter = new CreditListAdapter(getActivity(), App.BASE_BEAN.getInvestList().getBorrowList());
            lvCreditList.getRefreshableView().setAdapter(adapter);

            lvCreditList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    CreditListFragment.pageNum = String.valueOf(Integer.valueOf(CreditListFragment.pageNum) - 1);
                    if (Integer.valueOf(CreditListFragment.pageNum) < 1 || Integer.valueOf(CreditListFragment.pageNum) > App.BASE_BEAN.getInvestList().getP().getPages()) {
                        if (Integer.valueOf(CreditListFragment.pageNum) < 1) {
                            CreditListFragment.pageNum = "1";
                        }
                        lvCreditList.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lvCreditList.onRefreshComplete();
                                showToast("当前已是第一页");
                            }
                        }, 500);
                    } else {
                        reLoadBorrowList();
                    }
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    CreditListFragment.pageNum = String.valueOf(Integer.valueOf(CreditListFragment.pageNum) + 1);
                    if (Integer.valueOf(CreditListFragment.pageNum) < 1 || Integer.valueOf(CreditListFragment.pageNum) > App.BASE_BEAN.getInvestList().getP().getPages()) {
                        if (Integer.valueOf(CreditListFragment.pageNum) > App.BASE_BEAN.getInvestList().getP().getPages()) {
                            CreditListFragment.pageNum = String.valueOf(App.BASE_BEAN.getInvestList().getP().getPages());
                        }
                        lvCreditList.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lvCreditList.onRefreshComplete();
                                showToast("当前已是最后一页");
                            }
                        }, 500);
                    } else {
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

            progressDialog = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("正在加载中...")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
        }

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
                if (menu != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_sliding_menu, ((HomeActivity) getActivity()).screenFragment1).commit();
                    menu.showMenu();
                }
                break;
        }
    }

    private void sortDefault() {
        CreditListFragment.order = "0";
        reLoadBorrowList();
    }

    private void sortLimit(boolean ASC) {
        if (ASC) {
            ivTimeLimit.setImageResource(R.mipmap.sx1);
            CreditListFragment.order = "26";
            reLoadBorrowList();
            LIMIT_ASC = false;
        } else {
            ivTimeLimit.setImageResource(R.mipmap.sx2);
            CreditListFragment.order = "-26";
            reLoadBorrowList();
            LIMIT_ASC = true;
        }
        RATE_ASC = true;
    }

    private void sortRate(boolean ASC) {
        if (ASC) {
            ivAnnualRate.setImageResource(R.mipmap.sx1);
            CreditListFragment.order = "2";
            reLoadBorrowList();
            RATE_ASC = false;
        } else {
            ivAnnualRate.setImageResource(R.mipmap.sx2);
            CreditListFragment.order = "-2";
            reLoadBorrowList();
            RATE_ASC = true;
        }
        LIMIT_ASC = true;
    }

    public void reLoadBorrowList() {
        if (App.BASE_BEAN == null || App.BASE_BEAN.getInvestList() == null) {
            return;
        } else {
            args.put("s_status", CreditListFragment.s_status);
            args.put("s_repay_way", CreditListFragment.s_repay_way);
            args.put("s_time_limit", CreditListFragment.s_time_limit);
            args.put("s_account", CreditListFragment.s_account);
            args.put("order", CreditListFragment.order);
            args.put("s_type", CreditListFragment.s_type);
            args.put("pageNum", CreditListFragment.pageNum);
            progressDialog.show();
            VolleyHttp.getInstance().postParamsJson(ServerInterface.INVEST_LIST, new VolleyHttp.JsonResponseListener() {
                @Override
                public void getJson(String json, boolean isConnectSuccess) {
                    if (json != null && !json.equals("") && !json.equals("null")) {
                        InvestList investList = gson.fromJson(json, InvestList.class);
                        App.BASE_BEAN.setInvestList(investList);
                        adapter = new CreditListAdapter(context, App.BASE_BEAN.getInvestList().getBorrowList());
                        lvCreditList.getRefreshableView().setAdapter(adapter);
                        if (lvCreditList.isRefreshing()) {
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

}
