//package com.lenso.jixiangbao.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.lenso.jixiangbao.App;
//import com.lenso.jixiangbao.R;
//import com.lenso.jixiangbao.adapter.PreferredFinanceListAdapter;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by king on 2016/5/13.
// */
//public class PreferredFinanceFragment extends BaseFragment {
//    @Bind(R.id.lv_fragment_preferred_finance)
//    ListView lvFragmentPreferredFinance;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_preferredfinance, null);
//        ButterKnife.bind(this, view);
//        initView(inflater);
//        return view;
//    }
//
//    private void initView(LayoutInflater inflater) {
//        View footer=inflater.inflate(R.layout.item_safe_footer,null);
//        lvFragmentPreferredFinance.addFooterView(footer);
//        PreferredFinanceListAdapter adapter=new PreferredFinanceListAdapter(getActivity(), App.BASE_BEAN);
//        lvFragmentPreferredFinance.setAdapter(adapter);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//}
