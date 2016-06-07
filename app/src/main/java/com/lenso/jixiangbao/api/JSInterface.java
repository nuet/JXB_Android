package com.lenso.jixiangbao.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.GestureSettingsActivity;
import com.lenso.jixiangbao.activity.GestureUnlockActivity;
import com.lenso.jixiangbao.activity.HomeActivity;
import com.lenso.jixiangbao.activity.LoginOrRegisterActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by king on 2016/5/17.
 */
public class JSInterface {
    public static final String H5_URL = "h5_url";
    public static final String H5_TITLE = "h5_title";
    public static final int JI_CHE_DAI = 0;
    public static final int CALCULATOR = 1;
    private final Context context;
    private final Activity activity;

    public JSInterface(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    /**
     * 打开网页
     *
     * @param title 标题栏标题
     * @param url   打开的网址
     */
    @JavascriptInterface
    public void open(String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(H5_URL, url);
        intent.putExtra(H5_TITLE, title);
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber 电话号码
     */
    @JavascriptInterface
    public void makeCall(String phoneNumber) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                callPhone(phoneNumber);
            } else {
                showToast("没有权限：请为" + context.getResources().getString(R.string.app_name) + "开启拨打电话权限");
            }
        } else {
            callPhone(phoneNumber);
        }
    }

    void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //noinspection ResourceType
        context.startActivity(intent);
    }

    /**
     * 我的->账户信息->手势密码
     */
    @JavascriptInterface
    public void gestureLock() {
        Log.i("JSInterface", "getstureLock() executed!");
        SharedPreferences sp = context.getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
        String password = sp.getString("GestureLock", "");
        Intent intent = new Intent();
        if (TextUtils.isEmpty(password)) {
            intent.putExtra("gestureTitle", "设置手势密码");
            intent.putExtra("jsFlag", true);
            intent.setClass(context, GestureSettingsActivity.class);
        } else {
            intent.putExtra("gestureTitle", "输入手势密码");
            intent.putExtra("jsFlag", true);
            intent.setClass(context, GestureUnlockActivity.class);
        }
        context.startActivity(intent);
    }

    /**
     * 我的->账户信息->修改头像
     */
    @JavascriptInterface
    public void changeHeadPic() {

//        showToast("修改头像");

        /**
         * 显示popupWindow
         */
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_popupwindow, null);
        View parent = inflater.inflate(R.layout.activity_webview, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(parent.findViewById(R.id.top_menu_bar), Gravity.BOTTOM, 0, 0);

        // 检验popWindow里的button是否可以点击
        Button take = (Button) view.findViewById(R.id.pop_btn_take_photo);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("拍照");
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.png")));
                ((Activity) context).startActivityForResult(intentFromCapture, 1);
                window.dismiss();
            }
        });
        Button choose = (Button) view.findViewById(R.id.pop_btn_choose_photo);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("相册");
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                ((Activity) context).startActivityForResult(intentFromGallery, 2);
                window.dismiss();
            }
        });
        Button cancel = (Button) view.findViewById(R.id.pop_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("取消");
                window.dismiss();
            }
        });

//        //popWindow消失监听方法
//        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                showToast("popWindow消失");
//            }
//        });

    }

    /**
     * 我的->账户信息->退出登录
     */
    @JavascriptInterface
    public void logout() {
        HashMap map = new HashMap();
        map.put("app_key", Config.getInstance(context).getConfig("app_key"));
        Config.getInstance(context).putConfig("app_key", "");
        VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_LOGOUT, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if(isConnectSuccess){
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if(jsonObject.getString("status").equals("1")){
//                            showToast("退出成功");
                        }else{
                            showToast(jsonObject.getString("rsmsg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, map);
        Intent intentLogout = new Intent();
        intentLogout.setClass(context, LoginOrRegisterActivity.class);
//        intentLogout.setFlags(intentLogout.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intentLogout);
        activity.finish();
    }

    /**
     * 显示吐司
     *
     * @param msg 要显示的消息
     */
    @JavascriptInterface
    public void showToast(String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打印日志
     * @param log 日志
     */
    @JavascriptInterface
    public void log(String log){
        Log.i("h5", log);
    }

    /**
     * 存储数据
     * @param key 键
     * @param value 值
     */
    @JavascriptInterface
    public void putConfig(String key, String value){
        Config.getInstance(context).putConfig(key, value);
    }

    /**
     * 获取数据
     * @param key 键
     */
    @JavascriptInterface
    public void getConfig(String key){
        Config.getInstance(context).getConfig(key);
    }

    /**
     * 返回
     */
    @JavascriptInterface
    public void back(){
        activity.finish();
    }
}
