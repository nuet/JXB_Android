package com.lenso.jixiangbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by king on 2016/5/10.
 */
public class BaseFragment extends Fragment {
    private String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    protected void showToast(String text){
        Toast.makeText(getActivity().getApplicationContext(),text,Toast.LENGTH_SHORT).show();
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
