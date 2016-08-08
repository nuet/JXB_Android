package com.lenso.jixiangbao.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.view.JViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chung on 2016/5/12.
 */
public class FinancingFragment extends BaseFragment {
    //    @Bind(R.id.tv_menu_preferred_finance)
//    TextView tvMenuPreferredFinance;
    @Bind(R.id.tv_menu_credit_list)
    TextView tvMenuCreditList;
    @Bind(R.id.tv_menu_transfer_zone)
    TextView tvMenuTransferZone;
    @Bind(R.id.jvp_finance)
    JViewPager jvpFinance;
    @Bind(R.id.view_line)
    ImageView viewLine;
    private FragmentViewPageAdapter adapter;
    private int width;
//    private PreferredFinanceFragment preferredFinanceFragment;
    private CreditListFragment creditListFragment;
    private TransferListFragment transferListFragment;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_financing, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Point outSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(outSize);
        width = outSize.x / 2;
        ViewGroup.LayoutParams lp = viewLine.getLayoutParams();
        lp.width = width;
        viewLine.setLayoutParams(lp);

//        preferredFinanceFragment = new PreferredFinanceFragment();
        creditListFragment = new CreditListFragment();
        transferListFragment = new TransferListFragment();

        if (adapter == null) {
            List<Fragment> fragmentList = new ArrayList<>();
//            fragmentList.add(preferredFinanceFragment);
            fragmentList.add(creditListFragment);
            fragmentList.add(transferListFragment);
            adapter = new FragmentViewPageAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        }
        jvpFinance.setAdapter(adapter);
        tvMenuCreditList.setSelected(true);
        jvpFinance.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewLine.getLayoutParams();
                lp.leftMargin = (int) (position*width+positionOffset * width);
                viewLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                unSelected();
                switch (position) {
//                    case 0:
//                        tvMenuPreferredFinance.setSelected(true);
//                        break;
                    case 0:
                        tvMenuCreditList.setSelected(true);
                        break;
                    case 1:
                        tvMenuTransferZone.setSelected(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                logDebug(state + "++");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void unSelected() {
        tvMenuCreditList.setSelected(false);
//        tvMenuPreferredFinance.setSelected(false);
        tvMenuTransferZone.setSelected(false);
    }

    @OnClick({ R.id.tv_menu_credit_list, R.id.tv_menu_transfer_zone})
    public void onClick(View view) {
        int position = 0;
        switch (view.getId()) {
//            case R.id.tv_menu_preferred_finance:
//                position = 0;
//                break;
            case R.id.tv_menu_credit_list:
                position = 0;
                break;
            case R.id.tv_menu_transfer_zone:
                position = 1;
                break;
        }
        jvpFinance.setCurrentItem(position);
        unSelected();
        view.setSelected(true);
    }

    public void sortBorrowList(){
        creditListFragment.reLoadBorrowList(false);
    }

    public void sortTransferList(){
        transferListFragment.reLoadTransferList(false);
    }

    public static boolean isVisible;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        logInfo("isVisible"+String.valueOf(isVisible));
    }

}
