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
    @Bind(R.id.id_textView1)
    TextView idTextView1;
    @Bind(R.id.id_gestureLockViewGroup)
    GestureLockViewGroup idGestureLockViewGroup;
    @Bind(R.id.id_button)
    Button idButton;
    @Bind(R.id.ll_show)
    LinearLayout llShow;
//    private GestureLockViewGroup mGestureLockViewGroup;
//    private GestureLockDisplayViews mGestureLockDisplayViews;
//    private TextView mTextView;
//    private TextView mTextView1;
//    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_settings);
        ButterKnife.bind(this);

//        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
//        mGestureLockDisplayViews = (GestureLockDisplayViews) findViewById(R.id.id_gestureLockDisplayViews);
//        mTextView = (TextView) findViewById(R.id.id_textView);
//        mTextView1 = (TextView) findViewById(R.id.id_textView1);
//        mButton = (Button) findViewById(R.id.id_button);
        idGestureLockViewGroup.setInitMode(true);
        idGestureLockViewGroup.setLimitSelect(5);

        idGestureLockViewGroup.setOnGestureLockViewInitModeListener(new GestureLockViewGroup.OnGestureLockViewInitModeListener() {
            @Override
            public void onLimitSelect(int limitSelect, int select) {
                Toast.makeText(GestureSettingsActivity.this, "最少连接" + limitSelect + "个", Toast.LENGTH_SHORT).show();
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
                editor.putString("GestureLockView", Arrays.toString(secondAnswer));
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
                startErrorAnim(false);
            }
        });

    }

    private void startErrorAnim(boolean isVisible) {
        if (isVisible) {
            idTextView1.setVisibility(View.VISIBLE);
            idTextView1.setText("两次绘制的图形不一致");
            idTextView1.setTextColor(Color.parseColor("#FF0000"));
            CommonUtils.startShakeAnim(GestureSettingsActivity.this, idTextView1);
            idButton.setVisibility(View.VISIBLE);
        } else {
            idTextView1.setVisibility(View.GONE);
            idButton.setVisibility(View.GONE);
        }
    }

}
