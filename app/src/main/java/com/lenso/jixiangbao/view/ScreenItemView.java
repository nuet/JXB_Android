package com.lenso.jixiangbao.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/22.
 */
public class ScreenItemView extends LinearLayout {
    private int contentVisible;
    private int itemTextBackground;
    private ColorStateList itemTextColor;
    private float itemTextSize;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.iv_screen_arrow)
    ImageView ivScreenArrow;
    @Bind(R.id.ll_screen_item_1)
    LinearLayout llScreenItem1;
    @Bind(R.id.ll_screen_item_2)
    LinearLayout llScreenItem2;
    @Bind(R.id.ll_screen_item)
    LinearLayout llScreenItem;
    private int llScreenItemHeight = 0;
    private boolean isUp = true;
    private boolean isAnimatorEnd = true;
    private ObjectAnimator animator;
    private float animationF;
    private final List<TextView> itemTexts;
    private OnScreenItemListener listener;
    private int position;
    private final int itemPaddingL_R;
    private final int itemPaddingT_B;
    private final int itemLayoutMargin;
    private String t;

    public ScreenItemView(Context context) {
        this(context, null);
    }

    public ScreenItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        itemTexts = new ArrayList<>();
        View view = View.inflate(context, R.layout.view_screen_list_item, null);
        addView(view);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(lp);
        ButterKnife.bind(this);

        itemPaddingL_R = (int) getResources().getDimension(R.dimen.dp_10);
        itemPaddingT_B = (int) getResources().getDimension(R.dimen.dp_10);
        itemLayoutMargin = (int) getResources().getDimension(R.dimen.dp_5);

        if (attrs != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScreenItemView);
            int arrowResId = typedArray.getResourceId(R.styleable.ScreenItemView_sv_arrowSrc, 0);
            String title = typedArray.getString(R.styleable.ScreenItemView_sv_title);
            String content = typedArray.getString(R.styleable.ScreenItemView_sv_content);
            float titleSize = typedArray.getDimension(R.styleable.ScreenItemView_sv_titleSize, tvTitle.getTextSize());
            float contentSize = typedArray.getDimension(R.styleable.ScreenItemView_sv_contentSize, tvContent.getTextSize());
            int titleColor = typedArray.getColor(R.styleable.ScreenItemView_sv_titleColor, Color.BLACK);
            int contentColor = typedArray.getColor(R.styleable.ScreenItemView_sv_contentColor, Color.BLACK);
            itemTextSize = typedArray.getDimension(R.styleable.ScreenItemView_sv_itemTextSize, 100);
            itemTextColor = typedArray.getColorStateList(R.styleable.ScreenItemView_sv_itemTextColor);
            itemTextBackground = typedArray.getResourceId(R.styleable.ScreenItemView_sv_itemTextBackground, 0);
            contentVisible = typedArray.getInt(R.styleable.ScreenItemView_sv_contentVisibility, View.VISIBLE);
            typedArray.recycle();
            ivScreenArrow.setImageResource(arrowResId);
            tvTitle.setText(title);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
            tvTitle.setTextColor(titleColor);
            tvContent.setText(content);
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize);
            tvContent.setTextColor(contentColor);
            setContentVisible(contentVisible);
        }
        if (itemTextColor == null)
            itemTextColor = getResources().getColorStateList(R.color.selector_screen_item_text);
        if (itemTextBackground == 0)
            itemTextBackground = R.drawable.selector_screen_item_background;
        if (itemTextSize == 0)
            itemTextSize = getResources().getDimension(R.dimen.sp_18);

        rlTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showItem(v);
            }
        });
    }

    public void setContentVisible(int visible) {
        tvContent.setVisibility(visible);
    }

    public void setTitleSize(float textSize) {
        tvTitle.setTextSize(textSize);
    }

    public void setTitleColor(int textColor) {
        tvTitle.setTextColor(textColor);
    }

    public void setContentSize(float textSize) {
        tvContent.setTextSize(textSize);
    }

    public void setContentColor(int textColor) {
        tvContent.setTextColor(textColor);
    }

    public void setItemTextSize(float textSize) {
        itemTextSize = textSize;
    }

    public void setItemTextColor(int textColor) {
        itemTextColor = getResources().getColorStateList(textColor);
    }

    public void setItemTextBackground(int resId) {
        itemTextBackground = resId;
    }

    public void setScreenArrow(int resId) {
        ivScreenArrow.setImageResource(resId);
    }

    public void setTitleText(String text) {
        tvTitle.setText(text);
    }

    public void setContentText(String content) {
        tvContent.setText(content);
    }

    public List<TextView> addItemViews(int position, List<String> texts, String t) {
        itemTexts.clear();
        llScreenItem1.removeAllViews();
        llScreenItem2.removeAllViews();
        this.t = t;
        for (String text : texts) {
            addItemView(text);
            if (t.equals(text)) {
                itemTexts.get(itemTexts.size() - 1).setSelected(true);
            }
        }
        this.position = position;
        return itemTexts;
    }

    public void setOnScreenItemListener(OnScreenItemListener listener) {
        this.listener = listener;
    }

    public void addItem(int position, List<String> texts, String t, boolean isUp) {
        ViewGroup.LayoutParams lp = llScreenItem.getLayoutParams();
        if (isUp)
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
            lp.height = 0;
        llScreenItem.setLayoutParams(lp);
        addItemViews(position, texts, t);
        this.isUp = isUp;
        setArrowRotation(ivScreenArrow, !isUp);
    }

    public interface OnScreenItemListener {
        void onScreenItem(int position, String text, boolean isUp);
    }

    private TextView addItemView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(itemTextColor);
//        textView.setTextSize(itemTextSize);
        textView.setBackgroundResource(itemTextBackground);
        textView.setPadding(itemPaddingL_R, itemPaddingT_B, itemPaddingL_R, itemPaddingT_B);
        itemTexts.add(textView);
        if (itemTexts.size() % 2 == 1) {
            llScreenItem1.addView(textView);
        } else {
            llScreenItem2.addView(textView);
        }
        LinearLayout.LayoutParams lp = (LayoutParams) textView.getLayoutParams();
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.topMargin = itemLayoutMargin;
        lp.bottomMargin = itemLayoutMargin;
        lp.leftMargin = itemLayoutMargin;
        lp.rightMargin = itemLayoutMargin;
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                unSelected();
                v.setSelected(true);
                t = ((TextView) v).getText().toString();
                listener.onScreenItem(position, t, isUp);
            }
        });
        return textView;
    }

    private void unSelected() {
        for (TextView t : itemTexts) {
            t.setSelected(false);
        }
    }

    private void showItem(View v) {
        if (llScreenItemHeight == 0)
            llScreenItemHeight = llScreenItem.getHeight();
        setArrowRotation(v, isUp);
        itemAnimator(isUp);
        listener.onScreenItem(position, t, isUp);
    }

    private void setArrowRotation(View v, boolean is) {
        if (is) {
            ivScreenArrow.setRotation(180);
            tvContent.setText(t);
            tvContent.setVisibility(View.VISIBLE);
        } else {
            ivScreenArrow.setRotation(0);
            tvContent.setVisibility(View.INVISIBLE);
        }
    }

    private void itemAnimator(boolean up) {
        if (!isAnimatorEnd)
            animator.cancel();
        float startF;
        float endF;
        int duration = 200;
        if (up) {
            if (isAnimatorEnd)
                startF = 1f;
            else
                startF = animationF;
            endF = 0f;
            duration = (int) (duration * (startF - endF));
        } else {
            if (isAnimatorEnd)
                startF = 0f;
            else
                startF = animationF;
            endF = 1f;
            duration = (int) (duration * (endF - startF));
        }
        isUp = !isUp;
        animator = ObjectAnimator.ofFloat(this, "", startF, endF);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationF = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = llScreenItem.getLayoutParams();
                if (llScreenItemHeight == 0) {
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    llScreenItem.setLayoutParams(lp);
                    animationF = 1f;
                    llScreenItem.setAlpha(animationF);
                    animator.cancel();
                } else {
                    lp.height = (int) (llScreenItemHeight * animationF);
                    llScreenItem.setLayoutParams(lp);
                    if (animationF == 0)
                        llScreenItem.setAlpha(1);
                    else
                        llScreenItem.setAlpha(animationF);
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimatorEnd = true;
                super.onAnimationEnd(animation);
            }
        });
        isAnimatorEnd = false;
        animator.start();
    }
}
