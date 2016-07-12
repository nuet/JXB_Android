package com.lenso.jixiangbao.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.baofoo.sdk.vip.BaofooPayActivity;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.activity.LoginOrRegisterActivity;
import com.lenso.jixiangbao.activity.VerifyActivity;
import com.lenso.jixiangbao.activity.WebViewActivity;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.CommonUtils;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.util.JPushSettings;
import com.lenso.jixiangbao.view.iOSActionSheetDialog;
import com.lenso.jixiangbao.view.iOSAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by king on 2016/5/17.
 */
public class JSInterface {
    public static final String H5_URL = "h5_url";
    public static final String H5_TITLE = "h5_title";
    //    public static final int JI_CHE_DAI = 0;
    public static final int CALCULATOR = 1;
    public static final int DETAIL = 2;
    public static final int HUA_JI_FEN = 3;
    private final Context context;
    private final Activity activity;

    /*******宝付*******/
    private Map BAO_FOO = new HashMap();
    private String tradeNo = "";
    public final static int REQUEST_CODE_BAOFOO_SDK = 100;

    public static Handler handler;
    private static String buy_money;
    private static String paypassword;
    private static String id;
    /*******宝付*******/


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
        Log.d("open", "---" + title + url);
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
        Intent intent = new Intent();
        intent.putExtra("gestureTitle", "设置手势密码");
        intent.putExtra("jsFlag", true);
        intent.setClass(context, VerifyActivity.class);
        context.startActivity(intent);
    }

    /**
     * 我的->账户信息->修改头像
     */
    @JavascriptInterface
    public void changeHeadPic() {

        iOSActionSheetDialog dialog = new iOSActionSheetDialog(context);
        dialog.builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .show();

    }

    /**
     * 我的->账户信息->退出登录
     */
    @JavascriptInterface
    public void logout() {
        new iOSAlertDialog(context).builder()
                .setTitle("温馨提示")
                .setMsg("您确定要注销登录吗?")
                .setCancelable(false)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap map = new HashMap();
                        map.put("app_key", Config.getInstance(context).getConfig("app_key"));
                        Config.getInstance(context).putConfig("app_key", "");//清空本地app_key
                        VolleyHttp.getInstance().postParamsJson(ServerInterface.SERVER_LOGOUT, new VolleyHttp.JsonResponseListener() {
                            @Override
                            public void getJson(String json, boolean isConnectSuccess) {
                                if (isConnectSuccess) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(json);
                                        if (jsonObject.getString("status").equals("1")) {
                                            CommonUtils.clearGesturePassword(context);//清空手势密码

                                            JPushSettings jPushSettings = new JPushSettings(context);
                                            jPushSettings.setAlias("");//清空JPush别名

                                            Intent intentLogout = new Intent();
                                            intentLogout.setClass(context, LoginOrRegisterActivity.class);
                                            intentLogout.putExtra("jsFlag", true);
                                            context.startActivity(intentLogout);
                                            activity.finish();
                                        } else {
                                            showToast(jsonObject.getString("rsmsg"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, map);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();


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
     *
     * @param log 日志
     */
    @JavascriptInterface
    public void log(String log) {
        Log.i("h5", log);
    }

    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    @JavascriptInterface
    public void putConfig(String key, String value) {
        Config.getInstance(context).putConfig(key, value);
    }

    /**
     * 获取数据
     *
     * @param key 键
     */
    @JavascriptInterface
    public String getConfig(String key) {
        return Config.getInstance(context).getConfig(key);
    }

    /**
     * 返回
     */
    @JavascriptInterface
    public void back() {
        activity.finish();
        ((WebViewActivity) activity).reload();
    }

    /**
     * 分享
     */
    @JavascriptInterface
    public void share() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle("注册有奖");  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText("分享文本分享文本分享文本分享文本分享文本分享文本分享文本分享文本分享文本分享文本分享文本分享文本");  //最多40个字符

        //网络图片的url：所有平台
        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://jixiangbao.net/");   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl("https://jixiangbao.net/");  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(context);
    }

    /**
     * 跳转至注册登录页
     */
    @JavascriptInterface
    public void toLoginOrRegister(){
        Intent intent = new Intent(context, LoginOrRegisterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 设置免打扰时段
     * @param isSelected 开关是否打开
     */
    @JavascriptInterface
    public void noBother(boolean isSelected){
        if(isSelected){
            Set<Integer> days = new HashSet<Integer>();
            for(int i=0;i<7;i++){
                days.add(i);
            }
            JPushInterface.setPushTime(context, days, 8, 22);
        }else{
            JPushInterface.setPushTime(context, null, 0, 23);
        }
    }

    /**
     * 设置活动消息
     * @param isSelected 开关是否打开
     */
    @JavascriptInterface
    public void activityMSG(boolean isSelected){
        if(isSelected){

        }else{

        }
    }

    /**
     * 设置通知消息
     * @param isSelected 开关是否打开
     */
    @JavascriptInterface
    public void notificationMSG(boolean isSelected){
        if(isSelected){

        }else{

        }
    }

    /**
     * 设置新标消息
     * @param isSelected 开关是否打开
     */
    @JavascriptInterface
    public void newTenderMSG(boolean isSelected){
        if(isSelected){

        }else{

        }
    }

    /**
     * 宝付充值接口
     * @param money 需要充值金额
     * @param willBuy 是否是静默购买调用
     */
    @JavascriptInterface
    public void BAO_FOO_RECHARGE(String money, boolean willBuy){
        /*************宝付*********/
        BAO_FOO.put("app_key", Config.getInstance(context).getConfig("app_key"));
        BAO_FOO.put("money", money);
        BAO_FOO.put("type", "1");
        BAO_FOO.put("payment1", "baofoo_vip_sdk_pay");
        Log.i("BAO_FOO", BAO_FOO.toString());
        VolleyHttp.getInstance().postParamsJson(ServerInterface.RECHARGE, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if(isConnectSuccess){
                    try {
//                        Log.i("BAOFOO", json);
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getString("status").equals("1")){
                            tradeNo = jsonObject.getString("tradeNo");
                            Log.i("tradeNo", tradeNo);
                            Intent payintent = new Intent(context, BaofooPayActivity.class);
                            // 通过业务流水请求报文获得的交易号
                            payintent.putExtra(BaofooPayActivity.PAY_TOKEN, tradeNo);
                            // 标记是否为测试，传True为正式环境，不传或者传False则为测试调用
                            payintent.putExtra(BaofooPayActivity.PAY_BUSINESS, false);
                            activity.startActivityForResult(payintent, REQUEST_CODE_BAOFOO_SDK);
                            tradeNo = ""; //清空本次交易号
                            BAO_FOO = new HashMap(); // 清空本次参数
                        }else {
                            showToast("交易失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    showToast("请检查网络设置");
                }
            }
        }, BAO_FOO);

        if(willBuy){
            initHandler(buy_money, paypassword, id);
        }else{
            initHandler("0", null, null);
        }
        /*************宝付*********/
    }

    /**
     * 充值并静默购买接口
     * @param buy_money 需要投标的总额
     * @param recharge_money 需要充值的金额
     * @param paypassword 支付密码
     * @param id id
     */
    @JavascriptInterface
    public void BAO_FOO_RECHARGE_AND_BUY(String buy_money, String recharge_money, String paypassword, String id){
        this.buy_money = buy_money;
        this.paypassword = paypassword;
        this.id = id;
        BAO_FOO_RECHARGE(recharge_money, true);
    }

    private void initHandler(String buy_money, String paypassword, String id){
        final String MONEY = buy_money;
        final String PAY_PASSWORD = paypassword;
        final String ID = id;
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(!MONEY.equals("0")){
                    switch (msg.what){
                        case -1:
                        case 0:
                        case 10:
                            showToast((String) msg.obj);
                            break;
                        case 1:
                            Map args = new HashMap();
                            args.put("app_key", Config.getInstance(context).getConfig("app_key"));
                            args.put("money", MONEY);
                            args.put("paypassword", PAY_PASSWORD);
                            args.put("id", ID);
                            VolleyHttp.getInstance().postParamsJson(ServerInterface.TENDER, new VolleyHttp.JsonResponseListener() {
                                @Override
                                public void getJson(String json, boolean isConnectSuccess) {
                                    if(isConnectSuccess){
                                        try {
                                            JSONObject jsonObject = new JSONObject(json);
                                            if(jsonObject.getString("status").equals("1")){
                                                showToast(jsonObject.getString("rsmsg"));
                                            }else{
                                                showToast("您已充值成功,但" + jsonObject.getString("rsmsg"));
                                                ((WebViewActivity) activity).reload();
//                                                WebViewActivity.reload();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }, args);
                            break;
                    }
                }
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 花积分接口
     * @param title 标题
     * @param url 网址
     */
    @JavascriptInterface
    public void HuaJiFen(String title, String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(H5_URL, url);
        intent.putExtra(H5_TITLE, title);
        intent.putExtra("intent", JSInterface.HUA_JI_FEN);
        context.startActivity(intent);
    }

}
