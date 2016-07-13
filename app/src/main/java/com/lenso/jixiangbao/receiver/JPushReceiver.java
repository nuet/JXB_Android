package com.lenso.jixiangbao.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;


import com.lenso.jixiangbao.activity.BaseActivity;
import com.lenso.jixiangbao.activity.GestureUnlockActivity;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;

/**
 * Created by Chung on 2016/5/24.
 */

/**
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            //打开自定义的Activity
            Intent intentOpen = new Intent(context, GestureUnlockActivity.class);
            intentOpen.putExtras(bundle);
            intentOpen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentOpen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intentOpen);

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

    }


    //处理自定义消息
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        Time time = new Time();
        time.setToNow(); // 取得系统时间
        String flag =  String.valueOf(time.monthDay) + String.valueOf(time.hour) + String.valueOf(time.minute) + String.valueOf(time.second);
        Log.i(TAG, flag);

        String url = "";
        String type = "";
        if (!TextUtils.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (null != extraJson && extraJson.length() > 0) {
                    url = extraJson.getString("url");
                    type = extraJson.getString("type");
                } else {
                    return;
                }
            } catch (JSONException e) {
                Log.e(TAG, "processCustomMessage: error");
            }
        }

        JPushLocalNotification jPushLocalNotification = new JPushLocalNotification();
        jPushLocalNotification.setContent(message);
        jPushLocalNotification.setBuilderId(0);
        jPushLocalNotification.setTitle(title);
        jPushLocalNotification.setExtras(extras);
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("url", url);
//        JSONObject json = new JSONObject(map);
//        jPushLocalNotification.setExtras(json.toString());
        jPushLocalNotification.setBroadcastTime(System.currentTimeMillis());

        if (type.equals("activity")) {
            if (Config.getInstance(context).getConfig("activityMSG").equals("1")) {
                jPushLocalNotification.setNotificationId(Integer.valueOf("1" + flag));
            }
        } else if (type.equals("message")) {
            if (Config.getInstance(context).getConfig("notificationMSG").equals("1")) {
                jPushLocalNotification.setNotificationId(Integer.valueOf("2" + flag));
            }
        } else if (type.equals("newborrow")) {
            if (Config.getInstance(context).getConfig("newTenderMSG").equals("1")) {
                jPushLocalNotification.setNotificationId(Integer.valueOf("3" + flag));
            }
        } else {
            jPushLocalNotification.setNotificationId(Integer.valueOf("4" + flag));/*****************调试语句，记得删掉*****************/
//            return;
        }
        JPushInterface.addLocalNotification(context, jPushLocalNotification);

//        if (BaseActivity.isAppBackground(HomeActivity.HOMECONTEXT)) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent();
//            msgIntent.putExtra("KEY_MESSAGE", message);
//            if (!TextUtils.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (null != extraJson && extraJson.length() > 0) {
//                        msgIntent.putExtra("KEY_EXTRAS", extras);
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, "processCustomMessage: error");
//                }
//            }
//            context.sendBroadcast(msgIntent);
//        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
