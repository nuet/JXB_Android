package com.lenso.jixiangbao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.util.Config;

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
    @Bind(R.id.tv_choice_apr)
    TextView tvChoiceApr;
    private View view;
    private boolean isFirst = true;
    private int position;
    private String apr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_choice, null);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isFirst && view.getHeight() > 0) {
                    isFirst = false;
                    int h = view.getHeight();
                    ViewGroup.LayoutParams lp = circle.getLayoutParams();
                    lp.height = lp.width = h / 2;
                    circle.setLayoutParams(lp);
                }
            }
        });
        ButterKnife.bind(this, view);

        tvChoiceApr.setText(apr);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                String url = HTMLInterface.DETAIL + "?borrowid=" + App.THREE_CHOICE.getThreeChoice().get(position).getId() + "&app_key=" + Config.getInstance(getContext().getApplicationContext()).getConfig("app_key");
                Log.i("H5:", "URL:" + url);
                intent.putExtra(JSInterface.H5_URL, url);
                intent.putExtra(JSInterface.H5_TITLE, App.THREE_CHOICE.getThreeChoice().get(position).getName());
                intent.putExtra("apr", App.THREE_CHOICE.getThreeChoice().get(position).getApr());
                intent.putExtra("intent", JSInterface.DETAIL);
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    public void setAPR(String apr) {
        this.apr = apr;
    }

    public void setOnClickEvent(int position) {
        this.position = position;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
