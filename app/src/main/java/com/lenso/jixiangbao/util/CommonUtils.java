package com.lenso.jixiangbao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.lenso.jixiangbao.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by king on 2016/5/10.
 */
public class CommonUtils {
    /**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
    /**
     * MD5加密
     * @param msg
     * @return
     */
    public static String md5Encrypt(String msg) {
        // 获取加密方法
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] bytes = digest.digest(msg.getBytes());
            StringBuilder builder = new StringBuilder();
            // byte数组转换成字符串
            for (byte b : bytes) {
                int v = b & 255;
                String str = Integer.toHexString(v);
                if (str.length() == 1)
                    builder.append("0");

                builder.append(str);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 抖动动画
     *
     * @param context
     * @param view
     */
    public static void startShakeAnim(Context context, View view) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

    public static int[] toIntArray(String string) {
        string = string.substring(1, string.length() - 1);
        String[] strings = string.split(", ");
        int[] intArray = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            intArray[i] = Integer.parseInt(strings[i]);
        }
        return intArray;
    }

    public static void ShowKeyboard(View v)
    {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(v, 0);
    }

}
