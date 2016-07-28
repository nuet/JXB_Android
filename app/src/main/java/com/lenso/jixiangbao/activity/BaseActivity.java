package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lenso.jixiangbao.util.CommonUtils;

import java.util.List;

/**
 * Created by king on 2016/5/10.
 */
public class BaseActivity extends FragmentActivity {
    private String TAG = getClass().getSimpleName();
    protected int statusHeight = 0;
    private CountDownTimer countDownTimer = new CountDownTimer(180000, 1000) {//总时间3min， 间隔时间1s
        public void onTick(long millisUntilFinished) {
        }

        public void onFinish() {
            logInfo("TimeOut finish");
            SharedPreferences sp = getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
            String gesturePsw = sp.getString("GestureLock", "");
            if (!TextUtils.isEmpty(gesturePsw)) {
                Intent timeOutIntent = new Intent();
                timeOutIntent.setClass(getApplicationContext(), GestureUnlockActivity.class);
                timeOutIntent.putExtra("timeOut", true);
                startActivity(timeOutIntent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            statusHeight = CommonUtils.getStatusHeight(this);
        }
    }

    protected void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void logDebug(String text) {
        Log.d(TAG, text);
    }

    protected void logInfo(String text) {
        Log.i(TAG, text);
    }

    protected void logVerbose(String text) {
        Log.v(TAG, text);
    }

    protected void logWarn(String text) {
        Log.w(TAG, text);
    }

    protected void logError(String text) {
        Log.e(TAG, text);
    }


    /*****************
     * 超时锁
     *************/
    @Override
    protected void onPause() {
        super.onPause();
        if (isAppBackground(getApplicationContext())) {
            logDebug("TimeOut:" + getClass().getSimpleName());
            if (!getClass().getSimpleName().equals("GestureUnlockActivity")) {
                countDownTimer.start();
                logInfo("TimeOut start");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isAppBackground(getApplicationContext())) {
            countDownTimer.cancel();
            logInfo("TimeOut cancel");
        }
    }

    public static boolean isAppBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    /*****************超时锁*************/

}
