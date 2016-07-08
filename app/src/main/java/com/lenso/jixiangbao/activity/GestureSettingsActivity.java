package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.view.GestureLockDisplayViews;
import com.lenso.jixiangbao.view.GestureLockViewGroup;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chung on 2016/5/20.
 */
public class GestureSettingsActivity extends BaseActivity {
    @Bind(R.id.id_textView)
    TextView idTextView;
    @Bind(R.id.id_gestureLockDisplayViews)
    GestureLockDisplayViews idGestureLockDisplayViews;
    @Bind(R.id.id_gestureLockViewGroup)
    GestureLockViewGroup idGestureLockViewGroup;
    @Bind(R.id.id_button)
    TextView idButton;
    @Bind(R.id.top_menu_bar_gesture)
    TopMenuBar topMenuBarGesture;

    private boolean jsFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_settings);
        ButterKnife.bind(this);
        Intent intent0 = getIntent();
        String gestureTitle = intent0.getStringExtra("gestureTitle");
        jsFlag = intent0.getBooleanExtra("jsFlag", false);

        topMenuBarGesture.setMenuTopPadding(statusHeight);

        topMenuBarGesture.setTitleText(gestureTitle);
        topMenuBarGesture.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        idGestureLockViewGroup.bindDisplayView(idGestureLockDisplayViews);

        idGestureLockViewGroup.setInitMode(true);
        idGestureLockViewGroup.setLimitSelect(4);

        idGestureLockViewGroup.setOnGestureLockViewInitModeListener(new GestureLockViewGroup.OnGestureLockViewInitModeListener() {
            @Override
            public void onLimitSelect(int limitSelect, int select) {
                Toast.makeText(GestureSettingsActivity.this, "最少连接" + limitSelect + "个点", Toast.LENGTH_SHORT).show();
                idGestureLockDisplayViews.clearSelect();
                idGestureLockViewGroup.clearGestureLockView();
            }

            @Override
            public void onInitModeGestureEvent(boolean matched) {
                Log.i("init mode gesture event", "matched :" + matched);
                if (!matched) {
                    startErrorAnim(true);
                    idGestureLockDisplayViews.clearSelect();
                }
            }

            @Override
            public void onFirstGestureSuccess(int[] firstAnswer) {
//                idGestureLockDisplayViews.setSelected(firstAnswer);
                idGestureLockDisplayViews.clearSelect();
                idTextView.setText("请再次绘制");
            }

            @Override
            public void onSecondGestureSuccess(int[] secondAnswer) {
                idGestureLockViewGroup.setInitMode(false);
                SharedPreferences sp = getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("GestureLock", Arrays.toString(secondAnswer));
                editor.commit();
                if(jsFlag){
                    finish();
                }else{
                    Intent intent = new Intent();
                    intent.setClass(GestureSettingsActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
//                showToast("手势密码设置成功");
            }

        });

        idButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idGestureLockViewGroup.reDraw();
                idGestureLockDisplayViews.clearSelect();
                idTextView.setText("请绘制手势密码图案");
                idTextView.setTextColor(Color.parseColor("#ffffffff"));
                startErrorAnim(false);
            }
        });

    }

    private void startErrorAnim(boolean isVisible) {
        if (isVisible) {
            idTextView.setText("两次绘制的图形不一致");
            idTextView.setTextColor(Color.parseColor("#FF0000"));
            CommonUtils.startShakeAnim(GestureSettingsActivity.this, idTextView);
            CommonUtils.startShakeAnim(GestureSettingsActivity.this, idGestureLockDisplayViews);
            CommonUtils.startShakeAnim(GestureSettingsActivity.this, idGestureLockViewGroup);
            idGestureLockViewGroup.clearGestureLockView();
            idButton.setVisibility(View.VISIBLE);
        } else {
            idButton.setVisibility(View.GONE);
        }
    }

}
