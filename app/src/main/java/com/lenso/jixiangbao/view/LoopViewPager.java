package com.lenso.jixiangbao.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;

import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.adapter.LoopFragmentPagerAdapter;

import java.util.List;

/**
 * Created by king on 2016/5/17.
 */
public class LoopViewPager extends JViewPager {
    private OnPageChangeListener changeListener;
    private int count;

    public LoopViewPager(Context context) {
        super(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addLoopViews(View... views) {
        this.count = views.length;

    }

    public void addLoopViews(Fragment... fragments) {
        this.count = fragments.length;
        if (count > 1)
            setAdapter(new LoopFragmentPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), this, fragments));
        else
            setAdapter(new FragmentViewPageAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), fragments));
        pagerChangeListener();
    }

    @Override
    public void setOffscreenPageLimit(int limit) {

    }

    public void addLoopViews(List<Object> objectList) {
        Object[] objects = objectList.toArray();
        if (objects instanceof View[])
            addLoopViews((View[]) objects);
        if (objects instanceof Fragment[])
            addLoopViews((Fragment[]) objects);
    }

    public void setOnLoopPagerChangeListener(OnPageChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    private void pagerChangeListener() {
//        setCurrentItem(1);
        setOnPageChangeListener(new OnPageChangeListener() {
//            private int index(int i) {
//                if (i == 0)
//                    i = count - 1;
//                else if (i == count + 1)
//                    i = 0;
//                else
//                    i--;
//                return i;
//            }

            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                if (i == 0 && i1 == 0) {
//                    setCurrentItem(count, false);
//                }
//                if (i == getAdapter().getCount()-1 && i1 == 0) {
//                    setCurrentItem(1, false);
//                }
//                if(changeListener!=null)
//                changeListener.onPageScrolled(index(i),v,i1);
            }

            @Override
            public void onPageSelected(int i) {
                if (changeListener != null)
                    changeListener.onPageSelected(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (changeListener != null)
                    changeListener.onPageScrollStateChanged(i);
            }
        });
    }
}
