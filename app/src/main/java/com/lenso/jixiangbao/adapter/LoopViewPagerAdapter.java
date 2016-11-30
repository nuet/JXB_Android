package com.lenso.jixiangbao.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.bean.AppScrollPic;
import com.lenso.jixiangbao.http.Options;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by king on 2016/5/17.
 */
public class LoopViewPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    private Context context;
    private List<AppScrollPic> appScrollPics;

//    public LoopViewPagerAdapter(Context context, String... us) {
//        this.context = context;
//        List<String> pic = new ArrayList<>();
//        for (String u : us) {
//            pic.add(u);
//        }
//        loopViewPagerAdapter(context, pic);
//    }


    public LoopViewPagerAdapter(Context context, List<AppScrollPic> appScrollPics) {
        this.context = context;
        this.appScrollPics = appScrollPics;

        viewList = new ArrayList<>();
        VolleyHttp vh = VolleyHttp.getInstance();
        Options opt = new Options();
//        opt.defImage(R.mipmap.def)
//                .errImage(R.mipmap.def);
        onCreateView(context, vh, appScrollPics.get(appScrollPics.size() - 1), opt);
        for (int i = 0; i < appScrollPics.size(); i++) {
            onCreateView(context, vh, appScrollPics.get(i), opt);
        }
        onCreateView(context, vh, appScrollPics.get(0), opt);
    }

    private void onCreateView(final Context context, VolleyHttp vh, final AppScrollPic appScrollPic, Options opt) {
        ImageView view = new ImageView(context);
        view.setTag(appScrollPic.getPic());
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!TextUtils.isEmpty(appScrollPic.getUrl())) || !(appScrollPic.getUrl().equals("#"))) {
                    if (CommonUtils.isNetworkConnected(context)) {
                        Intent intent = new Intent();
                        intent.setClass(context, WebViewActivity.class);
                        intent.putExtra(HTMLInterface.H5_URL, appScrollPic.getUrl());
                        intent.putExtra(HTMLInterface.H5_TITLE, appScrollPic.getName());
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        vh.imageLoader(appScrollPic.getPic(), view, opt);
        viewList.add(view);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
