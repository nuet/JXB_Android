package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
public class GestureSettingsActivity extends AppCompatActivity {
    @Bind(R.id.id_textView)
    TextView idTextView;
    @Bind(R.id.id_gestureLockDisplayViews)
    GestureLockDisplayViews idGestureLockDisplayViews;
    @Bind(R.id.id_gestureLockViewGroup)
    GestureLockViewGroup idGestureLockViewGroup;
    @Bind(R.id.id_button)
    Button idButton;
    @Bind(R.id.top_menu_bar)
    TopMenuBar topMenuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_settings);
        ButterKnife.bind(this);

        topMenuBar.setTitleText("修改手势密码");
        topMenuBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        idGestureLockViewGroup.setInitMode(true);
        idGestureLockViewGroup.setLimitSelect(5);

        idGestureLockViewGroup.setOnGestureLockViewInitModeListener(new GestureLockViewGroup.OnGestureLockViewInitModeListener() {
            @Override
            public void onLimitSelect(int limitSelect, int select) {
                Toast.makeText(GestureSettingsActivity.this, "最少连接" + limitSelect + "个点", Toast.LENGTH_SHORT).show();
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
                idGestureLockDisplayViews.setSelected(firstAnswer);
                idTextView.setText("请再次绘制");
            }

            @Override
            public void onSecondGestureSuccess(int[] secondAnswer) {
                idGestureLockViewGroup.setInitMode(false);
                SharedPreferences sp = getSharedPreferences("GestureLockView", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("GestureLock", Arrays.toString(secondAnswer));
                editor.commit();
                Toast.makeText(GestureSettingsActivity.this, Arrays.toString(secondAnswer), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        idButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idGestureLockViewGroup.reDraw();
                idGestureLockDisplayViews.clearSelect();
                idTextView.setText("请绘制手势密码");
                idTextView.setTextColor(Color.parseColor("#669EFF"));
                startErrorAnim(false);
            }
        });

    }

    private void startErrorAnim(boolean isVisible) {
        if (isVisible) {
            idTextView.setText("两次绘制的图形不一致");
            idTextView.setTextColor(Color.parseColor("#FF0000"));
            CommonUtils.startShakeAnim(GestureSettingsActivity.this, idTextView);
            idButton.setVisibility(View.VISIBLE);
        } else {
            idButton.setVisibility(View.GONE);
        }
    }

}