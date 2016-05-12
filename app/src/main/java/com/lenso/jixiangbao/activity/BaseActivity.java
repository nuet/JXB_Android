package com.lenso.jixiangbao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by king on 2016/5/10.
 */
public class BaseActivity extends FragmentActivity{
    protected static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
