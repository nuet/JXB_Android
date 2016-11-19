package com.lenso.jixiangbao.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lenso.jixiangbao.App;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.bean.AppScrollPic;
import com.lenso.jixiangbao.bean.BaseBean;
import com.lenso.jixiangbao.bean.InvestList;
import com.lenso.jixiangbao.bean.RightList;
import com.lenso.jixiangbao.http.VolleyHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    //获取系统状态栏高度
    public static int getStatusHeight(Activity activity){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int height = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            height = activity.getResources().getDimensionPixelSize(x);
            Log.i("StatusHeight", String.valueOf(height));
            return height;
        } catch (Exception e1) {
            Log.i("StatusHeight", "get status bar height fail");
            e1.printStackTrace();
            return 75;
        }
    }

    public static void clearGesturePassword(Context context){
        SharedPreferences sp = context.getSharedPreferences("GestureLock", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("GestureLock", "");
        editor.commit();
    }

    // clear the cache before time numDays
    public static int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long clickTime = System.currentTimeMillis();
        if ( clickTime - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = clickTime;
        return false;
    }


    private Gson gson = new Gson();
    private int loadCount = 0;
    private List<AppScrollPic> picList;
    private InvestList investList;
    private RightList rightList;

    public void loadValues() {
        VolleyHttp.getInstance().getJson(ServerInterface.ALL_DATA, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    BaseBean bean = gson.fromJson(json, BaseBean.class);
                    App.BASE_BEAN.setWeburl(bean.getWeburl());//webURL
                    App.BASE_BEAN.setVersionCode(bean.getVersionCode());//版本号
                    App.BASE_BEAN.setAndroid_url(bean.getAndroid_url());//更新地址
                    App.BASE_BEAN.setShare_title(bean.getShare_title());//分享标题
                    App.BASE_BEAN.setShare_desc(bean.getShare_desc());//分享文本
                    App.BASE_BEAN.setNew_experience_apr(bean.getNew_experience_apr());//体验标年利率
                    App.BASE_BEAN.setNew_experience_valid_time(bean.getNew_experience_valid_time());//体验期
                    App.BASE_BEAN.setStatistic_display(bean.getStatistic_display());//统计数据开关
                    App.BASE_BEAN.setNotice_txt(bean.getNotice_txt());//最新通知
                    App.BASE_BEAN.setNotice_url(bean.getNotice_url());//最新通知跳转url
                    App.BASE_BEAN.setFuwutel(bean.getFuwutel());//客服电话
                    loadCount++;
                }
                loadReports();
            }
        });
    }

    private void loadReports() {
        VolleyHttp.getInstance().getJson(ServerInterface.FINANCIAL_REPORTS, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    BaseBean bean = gson.fromJson(json, BaseBean.class);
                    App.BASE_BEAN.setPlatformFinancialReport(bean.getPlatformFinancialReport());
                    loadCount++;
                }
                loadPicList();
            }
        });
    }

    private void loadPicList() {
        VolleyHttp.getInstance().getJson(ServerInterface.ALL_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    BaseBean bean = gson.fromJson(json, BaseBean.class);
                    picList = bean.getAppScrollPic();
                    App.THREE_CHOICE.setThreeChoice(bean.getSanList());
                    loadCount++;
                }
                loadInvestList();
            }
        });
    }

    private void loadInvestList() {
        Map<String, String> args = new HashMap<String, String>();
        args.put("s_type","115");
        VolleyHttp.getInstance().postParamsJson(ServerInterface.INVEST_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    investList = gson.fromJson(json, InvestList.class);
                    loadCount++;
                }
                loadTransferList();
            }
        }, args);
    }

    private void loadTransferList(){
        VolleyHttp.getInstance().getJson(ServerInterface.RIGHT_LIST, new VolleyHttp.JsonResponseListener() {
            @Override
            public void getJson(String json, boolean isConnectSuccess) {
                if (json != null && !json.equals("") && !json.equals("null")) {
                    rightList = gson.fromJson(json, RightList.class);
                    loadCount++;
                }
                setData();
            }
        });
    }

    private void setData() {
        if (loadCount < 5){
            Log.i("load", "数据加载失败");
//            Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }else {
            App.BASE_BEAN.setAppScrollPic(picList);
            App.BASE_BEAN.setInvestList(investList);
            App.BASE_BEAN.setRightList(rightList);
        }
    }

    public static boolean checkPhoneNum(String phoneNum){
        if(phoneNum.startsWith("1") && (phoneNum.length() == 11)){
            return true;
        } else {
            return false;
        }
    }

    public static File downFile(String httpUrl, Context context, Handler handler) {
        String fileName = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);
        File tmpFile = new File("/sdcard/update");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        File file = new File("/sdcard/update/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();

                int contentLength = conn.getContentLength();
                int currentLength = 0;

                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[5120];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                                currentLength += numRead;
                                int progress = (int) Math.floor((100 * currentLength)/contentLength);
                                Message msg = new Message();
                                msg.obj = progress;
                                handler.sendMessage(msg);
                            }
                        } else {
                            break;
                        }
                    }
                }
                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    public static void openFile(File file, Context context) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
