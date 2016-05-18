package com.lenso.jixiangbao.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lenso.jixiangbao.adapter.FragmentViewPageAdapter;

/**
 * Created by king on 2016/5/12.
 */
public class JViewPager extends ViewPager {
    private boolean isCanScroll = true;
    private int previousX;
    private int previousY;
    private boolean isClick;
    private DisplayMode mode = DisplayMode.DEFAULT;

    public JViewPager(Context context) {
        super(context);
    }

    public JViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 是否能滑动
     *
     * @param isCanScroll
     */
    public void setScrollable(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isCanScroll) {
            return onTouch(event);
        } else {
            return false;
        }

    }

    public void setDisplayMode(DisplayMode mode) {
        this.mode = mode;
    }

    public enum DisplayMode {
        DISPLAY_BY_FIRST_ONE, DISPLAY_BY_EVERY_ONE, DEFAULT
    }

    private boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                isClick = true;
                previousX = (int) event.getX();
                previousY = (int) event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                isClick = false;
                int currentX = (int) event.getX();
                int currentY = (int) event.getY();
                int absX = Math.abs(currentX - previousX);
                int absY = Math.abs(currentY - previousY);
                if (absX >= absY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                previousX = currentX;
                previousY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                if (isClick)
                    onSingleTouch();
                break;
        }

        return super.onTouchEvent(event);

    }

    /**
     * 单击
     */
    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * 单击事件监听器
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    private OnSingleTouchListener onSingleTouchListener;

    /**
     * 设置单击监听器
     *
     * @param onSingleTouchListener
     */
    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("jgm", MeasureSpec.getSize(heightMeasureSpec) + "<<");
        switch (mode) {
            case DISPLAY_BY_FIRST_ONE:
//                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//                getChildAt(0).measure(widthMeasureSpec,heightMeasureSpec);
//                setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
                break;
            case DISPLAY_BY_EVERY_ONE:
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                onRemeasureEveryOne(widthMeasureSpec, heightMeasureSpec);
                break;
            case DEFAULT:
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
        }
    }

    private int measureWidth(int widthMeasureSpec, View v) {
        int result = 0;
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureSize = MeasureSpec.getSize(widthMeasureSpec);
        if (measureMode == MeasureSpec.EXACTLY)
            return measureSize;
        if (v != null)
            result = v.getMeasuredWidth();
        if (measureMode == MeasureSpec.AT_MOST)
            result = Math.min(result, measureSize);
        return result;
    }

    private int measureHeight(int heightMeasureSpec, View v) {
        int result = 0;
        int measureMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureSize = MeasureSpec.getSize(heightMeasureSpec);
        if (measureMode == MeasureSpec.EXACTLY)
            return measureSize;
        if (v != null)
            result = v.getMeasuredHeight();
        if (measureMode == MeasureSpec.AT_MOST)
            result = Math.min(result, measureSize);
        return result;
    }

    private int onRemeasureFirstOne(int widthMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        Log.d("jgm", MeasureSpec.getSize(widthMeasureSpec) + "---" + height);
        return MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
    }

    private void onRemeasureEveryOne(int widthMeasureSpec, int heightMeasureSpec) {
        if (getAdapter() == null)
            return;
        Object object = getAdapter().instantiateItem(this, getCurrentItem());
        View view = null;
        if (object instanceof Fragment)
            view = ((Fragment) object).getView();
        if (object instanceof View)
            view = (View) object;
        if (view != null) {
            // measure the first child view with the specified measure spec
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(measureWidth(widthMeasureSpec, view), measureHeight(heightMeasureSpec, view));
    }

//    /**
//     * Determines the height of this view
//     *
//     * @param measureSpec A measureSpec packed into an int
//     * @param view        the base view with already measured height
//     * @return The height of the view, honoring constraints from measureSpec
//     */
//    private int measureHeight(int measureSpec, View view) {
//        int result = 0;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        } else {
//            // set the height from the base view if available
//            if (view != null) {
//                result = view.getMeasuredHeight();
//            }
//            if (specMode == MeasureSpec.AT_MOST) {
//                result = Math.min(result, specSize);
//            }
//        }
//        return result;
//    }
}
