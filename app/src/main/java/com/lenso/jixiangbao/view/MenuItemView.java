package com.lenso.jixiangbao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lenso.jixiangbao.R;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/11.
 */
public class MenuItemView extends LinearLayout {
    @Bind(R.id.iv_menu_item)
    ImageView ivMenuItem;
    @Bind(R.id.tv_menu_item)
    TextView tvMenuItem;
    private OnClickListener listener;

    public MenuItemView(Context context) {
        this(context, null);
    }

    public void setMenuItemClickListener(OnClickListener listener) {
        this.listener = listener;
    }
    public void setTetxContent(String content){
        if(tvMenuItem!=null)
            tvMenuItem.setText(content);
    }
    public void setImageSrc(int resId){
        if(ivMenuItem!=null)
        ivMenuItem.setImageResource(resId);
    }
    public void setImageSrc(Bitmap bitmap){
        if(ivMenuItem!=null)
        ivMenuItem.setImageBitmap(bitmap);
    }
    public void setImageSrc(Drawable drawable){
        if(ivMenuItem!=null)
            ivMenuItem.setImageDrawable(drawable);
    }
    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs == null)
            return;
        View view = View.inflate(context, R.layout.item_menu_home, null);
        this.addView(view);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        ButterKnife.bind(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuItemView);
        int resId = typedArray.getResourceId(R.styleable.MenuItemView_image_src, R.mipmap.ic_launcher);
        String text = typedArray.getString(R.styleable.MenuItemView_text_content);
        typedArray.recycle();
        ivMenuItem.setImageResource(resId);
        tvMenuItem.setText(text);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onClick(view);
            }
        });
    }
}
