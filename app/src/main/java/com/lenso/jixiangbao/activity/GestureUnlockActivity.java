package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.util.DownTimer;
import com.lenso.jixiangbao.view.GestureLockViewGroup;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chung on 2016/5/20.
 */
public class GestureUnlockActivity extends AppCompatActivity {
    @Bind(R.id.top_menu_bar_gesture_unlock)
    TopMenuBar topMenuBarGestureUnlock;
    private boolean jsFlag;
    private GestureLockViewGroup mGestureLockViewGroup;
    private TextView mTextView;
    private DownTimer timer;
    private GestureLockViewGroup.OnGestureLockViewListener mGestureLockViewListener;
    //    runnable去清除gestureLockView
    private final Runnable clearRunnable = new Runnable() {
        @Override
        public void run() {
            mGestureLockViewGroup.clearGestureLockView();
        }
    };

    private static final int CLEAR_MILLS = 1000;

    private boolean isFirstSelect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_unlock);
        ButterKnife.bind(this);

        Intent intent0 = getIntent();
        jsFlag = intent0.getBooleanExtra("jsFlag", false);

        topMenuBarGestureUnlock.setTitleText("输入手势密码");
        topMenuBarGestureUnlock.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup_1);
        mTextView = (TextView) findViewById(R.id.id_textView2);
        timer = new DownTimer();//实例化
        SharedPreferences sp = getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
        String password = sp.getString("GestureLock", "");
        if (TextUtils.isEmpty(password)) {
            Log.e("GestreUnlockActivity", "password  null!!!");
//            mGestureLockViewGroup.setAnswer(new int[]{1, 2, 5, 8});
//            mTextView.setText("默认密码是1，2，5，8");
        } else {
            Log.i("format", Arrays.toString(CommonUtils.toIntArray(password)));
            mGestureLockViewGroup.setAnswer(CommonUtils.toIntArray(password));
        }
        mGestureLockViewListener = new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
//                        Toast.makeText(MainActivity.this, "错误5次...",
//                                Toast.LENGTH_SHORT).show();
//                        mGestureLockViewGroup.setUnMatchExceedBoundary(5);
                saveTime(System.currentTimeMillis());
                mTextView.setText("30s后再试");
                CommonUtils.startShakeAnim(GestureUnlockActivity.this, mTextView);
                mGestureLockViewGroup.setTouchable(false);
                //TODO 倒计时
                setTimer(30 * 1000);
                timer.start();
            }

            @Override
            public void onGestureEvent(boolean matched, int tryTimes) {
                postClearRunnable();
//                        Toast.makeText(MainActivity.this, matched + "",
//                                Toast.LENGTH_SHORT).show();
                if (matched) {
                    mTextView.setText("手势密码正确");
                    mTextView.setTextColor(Color.parseColor("#008000"));
                    if(jsFlag){
                        Intent intent = new Intent();
                        intent.putExtra("gestureTitle","修改手势密码");
                        intent.putExtra("jsFlag",jsFlag);
                        intent.setClass(GestureUnlockActivity.this, GestureSettingsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    mTextView.setText("手势密码不正确");
                    mTextView.setTextColor(Color.parseColor("#FF0000"));
                    CommonUtils.startShakeAnim(GestureUnlockActivity.this, mTextView);
                }
                isFirstSelect = true;
            }

            @Override
            public void onBlockSelected(int cId) {
                if (isFirstSelect) {
                    removeClearRunnable();
                    isFirstSelect = false;
                }
            }
        };
        SharedPreferences spTime = getSharedPreferences("Time", Activity.MODE_PRIVATE);
        long time = spTime.getLong("tryOutTime", -1);
        if (time == -1 || System.currentTimeMillis() - time > 30000) {
            mGestureLockViewGroup.setTouchable(true);
            mGestureLockViewGroup.setUnMatchExceedBoundary(5);
            saveTime(-1);
            mGestureLockViewGroup
                    .setOnGestureLockViewListener(mGestureLockViewListener);
        } else {
            mGestureLockViewGroup.setTouchable(false);
            setTimer(30000 - (System.currentTimeMillis() - time));
            timer.start();
        }

    }

    private void setTimer(long time) {
        timer.setTotalTime(time);//设置毫秒数
        timer.setIntervalTime(1000);//设置间隔数
        timer.setTimerLiener(new DownTimer.TimeListener() {
            @Override
            public void onFinish() {
                mGestureLockViewGroup.setTouchable(true);
                mTextView.setText("绘制手势密码");
                mTextView.setTextColor(Color.parseColor("#959595"));
                mGestureLockViewGroup.setUnMatchExceedBoundary(5);
                mGestureLockViewGroup.setOnGestureLockViewListener(mGestureLockViewListener);
            }

            @Override
            public void onInterval(long remainTime) {
                mTextView.setTextColor(Color.parseColor("#FF0000"));
                mTextView.setText(remainTime / 1000 + "秒后继续");//剩余多少秒
            }
        });
    }

    private void saveTime(long time) {
        SharedPreferences sp = getSharedPreferences("Time", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("tryOutTime", time);
        editor.commit();
    }

    public void postClearRunnable() {
        mGestureLockViewGroup.removeCallbacks(clearRunnable);
        mGestureLockViewGroup.postDelayed(clearRunnable, CLEAR_MILLS);
    }

    public void removeClearRunnable() {
        mGestureLockViewGroup.removeCallbacks(clearRunnable);
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

}
