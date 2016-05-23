package com.lenso.jixiangbao.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by king on 2016/5/10.
 */
public class BaseActivity extends FragmentActivity{
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    protected void showToast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
    protected void logDebug(String text){
        Log.d(TAG,text);
    }
    protected void logInfo(String text){
        Log.i(TAG,text);
    }
    protected void logVerbose(String text){
        Log.v(TAG,text);
    }
    protected void logWarn(String text){
        Log.w(TAG,text);
    }
    protected void logError(String text){
        Log.e(TAG,text);
    }
}
