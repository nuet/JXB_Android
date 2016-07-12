package com.lenso.jixiangbao.util;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.lenso.jixiangbao.activity.HomeActivity;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Chung on 2016/7/12.
 */
public class JPushSettings {
    private Context context;

    public JPushSettings(Context context) {
        this.context = context;
    }

    public void setAlias(String alias) {
        Log.i("JPush", alias);
        if (TextUtils.isEmpty(alias)) {
            Log.e("JPush", "alias 设置失败，app_key为空");
            return;
        }
        if (!isValidTagAndAlias(alias)) {
            Log.e("JPush", "alias 设置失败");
            return;
        }

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("JPush", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("JPush", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("JPush", logs);
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("JPush", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(context,
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i("JPush", "Unhandled msg:" + msg.what);
            }
        }
    };

    // 校验Tag Alias 只能是数字,英文字母和中文
    private boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA50-9a-zA-Z_@!#$&*+=.|￥¥]+$");//汉字、数字、小写字母、大写字母、下划线、@!#$&*+=.|￥
        Matcher m = p.matcher(s);
        return m.matches();
    }


}
