package com.lenso.jixiangbao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenso.jixiangbao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/12.
 */
public class TopMenuBar extends LinearLayout {

    private final View view;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.menu)
    ImageView menu;
    private OnClickListener backClickListener;
    private OnClickListener menuClickListener;
    private OnClickListener textClickListener;

    public TopMenuBar(Context context) {
        this(context, null);
    }

    public TopMenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = View.inflate(context, R.layout.view_top_menu_bar, null);
        this.addView(view);
        ViewGroup.LayoutParams viewParams = view.getLayoutParams();
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(viewParams);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopMenuBar);
            String text = typedArray.getString(R.styleable.TopMenuBar_tmb_text);
            float textSize = typedArray.getDimension(R.styleable.TopMenuBar_tmb_textSize, getResources().getDimension(R.dimen.menu_title_size));
            int textColor = typedArray.getColor(R.styleable.TopMenuBar_tmb_textColor, Color.WHITE);
            int background = typedArray.getResourceId(R.styleable.TopMenuBar_tmb_backgroundColor, 0);
            int backSrc = typedArray.getResourceId(R.styleable.TopMenuBar_tmb_backSrc, 0);
            int menuSrc = typedArray.getResourceId(R.styleable.TopMenuBar_tmb_menuSrc, 0);
            boolean backVisibility = typedArray.getBoolean(R.styleable.TopMenuBar_tmb_backVisibility, true);
            boolean menuVisibility = typedArray.getBoolean(R.styleable.TopMenuBar_tmb_menuVisibility, true);
            boolean textVisibility = typedArray.getBoolean(R.styleable.TopMenuBar_tmb_textVisibility, true);
            int padding = (int) typedArray.getDimension(R.styleable.TopMenuBar_tmb_padding, 0);
            int paddingTop = (int) typedArray.getDimension(R.styleable.TopMenuBar_tmb_paddingTop, 0);
            typedArray.recycle();
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + padding + paddingTop, view.getPaddingRight(), view.getPaddingBottom() + padding);
            back.setImageResource(backSrc);
            title.setText(text);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            title.setTextColor(textColor);
            menu.setImageResource(menuSrc);
            view.setBackgroundResource(background);

            if (!backVisibility)
                back.setVisibility(View.INVISIBLE);
            if (!menuVisibility)
                menu.setVisibility(View.INVISIBLE);
            if (!textVisibility)
                title.setVisibility(View.INVISIBLE);
        }
            back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (backClickListener != null)
                        backClickListener.onClick(v);
                }
            });
            menu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuClickListener != null)
                        menuClickListener.onClick(v);
                }
            });
            title.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textClickListener != null)
                        textClickListener.onClick(v);
                }
            });
    }
    public void setBackVisibility(int visibility){
        back.setVisibility(visibility);
    }
    public void setMenuVisibility(int visibility){
        menu.setVisibility(visibility);
    }
    public void setTitleVisibility(int visibility){
        title.setVisibility(visibility);
    }
    public void setBackSrc(int resId) {
        if (back != null)
            back.setImageResource(resId);
    }
    public void setMenuTopPadding(int padding){
        view.setPadding(view.getPaddingLeft(),view.getPaddingTop()+padding,view.getPaddingRight(),view.getPaddingBottom());
    }
    public void setBackSrc(Drawable drawable) {
        if (back != null)
            back.setImageDrawable(drawable);
    }

    public void setBackSrc(Bitmap bitmap) {
        if (back != null)
            back.setImageBitmap(bitmap);
    }

    public void setMenuSrc(int resId) {
        if (menu != null)
            menu.setImageResource(resId);
    }

    public void setMenuSrc(Drawable drawable) {
        if (menu != null)
            menu.setImageDrawable(drawable);
    }

    public void setMenuSrc(Bitmap bitmap) {
        if (menu != null)
            menu.setImageBitmap(bitmap);
    }

    public void setTitleText(String textTitle) {
        if (title != null)
            title.setText(textTitle);
    }

    public void setTitleColor(int color) {
        if (title != null)
            title.setTextColor(color);
    }

    public void setTitleSize(float size) {
        if (title != null)
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setTitleSecected(boolean flag){
        title.setSelected(flag);
    }

    public void setOnBackClickListener(OnClickListener backClickListener) {
        this.backClickListener = backClickListener;
    }

    public void setOnTextClickListener(OnClickListener textClickListener) {
        this.textClickListener = textClickListener;
    }

    public void setOnMenuClickListener(OnClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }
}
