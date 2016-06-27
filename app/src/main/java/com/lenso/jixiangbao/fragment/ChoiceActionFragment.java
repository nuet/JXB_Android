package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/24.
 */
public class ChoiceActionFragment extends BaseFragment {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title2)
    TextView tvTitle2;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.circle)
    LinearLayout circle;
    private View view;
    private boolean isFirst=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_choice, null);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(isFirst && view.getHeight()>0) {
                    isFirst=false;
                    int h = view.getHeight();
                    ViewGroup.LayoutParams lp=  circle.getLayoutParams();
                    lp.height=lp.width= h/2;
                    circle.setLayoutParams(lp);
                }
            }
        });
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
