package com.lenso.jixiangbao.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;
import com.lenso.jixiangbao.adapter.LoopViewPagerAdapter;
import com.lenso.jixiangbao.bean.AppScrollPic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 2016/5/17.
 */
public class LoopViewPager extends JViewPager {
    private OnPageChangeListener changeListener;
    private int count;
    private long time;
    private boolean isTouch = false;
    private boolean openTimer;

    public LoopViewPager(Context context) {
        super(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private Runnable action = new Runnable() {
        @Override
        public void run() {
            if (!isTouch)
                setCurrentItem(getCurrentItem() + 1);
            if (openTimer)
                setLoopTimer(time);
        }
    };

    public void setLoopTimer(long time) {
        this.time = time;
        openTimer = true;
        postDelayed(action, time);
    }

    public void openLoopTimer() {
        if (time > 0 && !openTimer)
            setLoopTimer(time);
    }

    public void cancelLoopTimer() {
        this.openTimer = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouch = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                isTouch = true;
                break;
        }
        return super.onTouchEvent(event);
    }

    public void addLoopImageUrl(List<AppScrollPic> appScrollPics) {
        count = appScrollPics.size();
        setAdapter(new LoopViewPagerAdapter(getContext(), appScrollPics));
        pagerChangeListener();
        super.setOffscreenPageLimit(count);
    }

//    public void addLoopImageUrl(String... us) {
//        List<String> urls = new ArrayList<>();
//        for (String u : us) {
//            urls.add(u);
//        }
//        addLoopImageUrl(urls);
//    }


    @Override
    public void setOffscreenPageLimit(int limit) {

    }


    public void setOnLoopPagerChangeListener(OnPageChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    private void pagerChangeListener() {
        setCurrentItem(1);
        addOnPageChangeListener(new OnPageChangeListener() {
            private int index(int i) {
                if (i == 0)
                    i = count - 1;
                else if (i == count + 1)
                    i = 0;
                else
                    i--;
                return i;
            }

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i == 0 && i1 == 0) {
                    setCurrentItem(count, false);
                }
                if (i == getAdapter().getCount() - 1 && i1 == 0) {
                    setCurrentItem(1, false);
                }
                if (changeListener != null)
                    changeListener.onPageScrolled(index(i), v, i1);
            }

            @Override
            public void onPageSelected(int i) {
                if (changeListener != null)
                    changeListener.onPageSelected(index(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (changeListener != null)
                    changeListener.onPageScrollStateChanged(index(i));
            }
        });
    }

    public void addLoopFragment(Fragment... fragments) {
        count = fragments.length - 2;
        setAdapter(new FragmentViewPageAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), fragments));
        super.setOffscreenPageLimit(count);
        pagerChangeListener();
    }
}
