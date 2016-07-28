package com.lenso.jixiangbao.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baofoo.sdk.vip.BaofooPayActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.api.ServerInterface;
import com.lenso.jixiangbao.fragment.MineFragment;
import com.lenso.jixiangbao.http.UploadUtil;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/17.
 */
public class WebViewActivity extends WebBaseActivity {
    @Bind(R.id.top_menu_bar)
    TopMenuBar topMenuBar;

    private static final String TAG = "WebViewActivity";
    private FrameLayout flWeb;

    private File headPIC;
    private WebView webView;
    private boolean calculator;
    private String url;
    private String title;
    private String apr;
    private Context context;

    private KProgressHUD progressDialog;

    private CountDownTimer countDownTimer = new CountDownTimer(180000, 1000) {//总时间3min， 间隔时间1s
        public void onTick(long millisUntilFinished) {
        }

        public void onFinish() {
            Log.d(TAG, "TimeOut finish");
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
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        flWeb = (FrameLayout) findViewById(R.id.fl_web);

//        topMenuBar.setMenuTopPadding(statusHeight);

        Intent intent = getIntent();
        url = intent.getStringExtra(JSInterface.H5_URL);
        title = intent.getStringExtra(JSInterface.H5_TITLE);
        apr = intent.getStringExtra("apr");

        webView = getWebView(url);
        flWeb.addView(webView);
        webView.addJavascriptInterface(new JSInterface(this), "api");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        setWebZoom(webSettings);
        setNetworkCache(webSettings);

        webView.loadUrl(url);
        topMenuBar.setTitleText(title);
        topMenuBar.setTitleSecected(true);
        topMenuBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menuSet(intent);
        calculator = intent.getBooleanExtra("calculator", false);
        if (calculator) {
            topMenuBar.setBackSrc(R.mipmap.close);
        }

        headPIC = new File(WebViewActivity.this.getFilesDir(), "head.png");

        context = WebViewActivity.this;

        progressDialog = KProgressHUD.create(WebViewActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("上传头像中...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

    }

    public void reload() {
        webView.reload();
    }

    private void menuSet(Intent intent) {
        int i = intent.getIntExtra("intent", -1);
        switch (i) {
//            case JSInterface.JI_CHE_DAI:
//                ji_che_dai();
//                break;
            case JSInterface.CALCULATOR:
                calculator();
                break;
            case JSInterface.DETAIL:
                detail();
                break;
            case JSInterface.HUA_JI_FEN:
                HUA_JI_FEN();
                break;
        }
    }

    private void detail() {
        topMenuBar.setMenuSrc(R.mipmap.calculator);
        topMenuBar.setMenuVisibility(View.VISIBLE);
        topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebViewActivity.this, WebViewActivity.class);
                intent.putExtra(JSInterface.H5_TITLE, "计算器");
                intent.putExtra(JSInterface.H5_URL, HTMLInterface.CALCULATOR2 + "?apr=" + apr);
                intent.putExtra("intent", JSInterface.CALCULATOR);
                startActivity(intent);
            }
        });
    }

    private void calculator() {
        topMenuBar.setBackSrc(R.mipmap.close);
    }

    private void HUA_JI_FEN() {
        topMenuBar.setMenuSrc(R.mipmap.guize);
        topMenuBar.setMenuVisibility(View.VISIBLE);
        topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebViewActivity.this, WebViewActivity.class);
                intent.putExtra(JSInterface.H5_TITLE, "规则");
                intent.putExtra(JSInterface.H5_URL, HTMLInterface.JFGZ);
                startActivity(intent);
            }
        });
    }

//    private void ji_che_dai() {
//        topMenuBar.setMenuSrc(R.mipmap.calculator);
//        topMenuBar.setMenuVisibility(View.VISIBLE);
//        topMenuBar.setOnMenuClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WebViewActivity.this, WebViewActivity.class);
//                intent.putExtra(JSInterface.H5_TITLE, "计算器");
//                intent.putExtra(JSInterface.H5_URL, HTMLInterface.CALCULATOR);
//                intent.putExtra("intent", JSInterface.CALCULATOR);
//                startActivity(intent);
//            }
//        });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1:
                    if (hasSDCard()) {
                        File tempFile = new File(Environment.getExternalStorageDirectory(), "head.png");
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    startPhotoZoom(data.getData());
                    break;
                case 0:
                    Log.d(TAG, "TimeOut cancel");
                    countDownTimer.cancel();
                    if (data != null) {
                        saveImage(data);
                    }
                    break;

                /*************宝付*********/
                case JSInterface.REQUEST_CODE_BAOFOO_SDK:
                    Log.d(TAG, "TimeOut cancel");
                    countDownTimer.cancel();
                    String result = "";
                    String msg = "";
                    if (data == null || data.getExtras() == null) {
                        msg = "支付已被取消";
                    } else {
                        result = data.getExtras().getString(BaofooPayActivity.PAY_RESULT);// -1:失败 0:取消 1:成功 10:处理中
                        msg = data.getExtras().getString(BaofooPayActivity.PAY_MESSAGE);
                    }
                    if ((!TextUtils.isEmpty(result)) && (!TextUtils.isEmpty(msg))) {
                        Message message = new Message();
                        message.what = Integer.valueOf(result);
                        message.obj = msg;
                        JSInterface.handler.sendMessage(message);
                    }

//                    iOSAlertDialog dialog = new iOSAlertDialog(WebViewActivity.this) {
//                    };
//                    dialog.setMessage(msg);
//                    dialog.show();
                    break;
//                case requestCodeBank:
//                    if (data != null) {
//                        TextView city_name = (TextView) findViewById(R.id.bank_name);
//                        city_name.setText(data.getStringExtra("bank_name"));
//                        onSaveCode(getActivity().getApplicationContext(), data.getStringExtra("bank_id"));
//                    }
//                    break;
                /*************宝付*********/
            }

        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("WebViewActivity", "The HeadPic uri is not exist");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪

        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 0);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data pic data
     */
    private void saveImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            try {
                FileOutputStream out = new FileOutputStream(headPIC);
                photo.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                Log.i("headPIC", "SAVED");
                Log.i("headPIC--path", headPIC.getPath());

                progressDialog.show();

                MyAsyncTask myTask = new MyAsyncTask();
                myTask.execute();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                Log.i("headPIC", "UNSAVED1");
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.i("headPIC", "UNSAVED2");
                e.printStackTrace();
            }

        }
    }

    /**
     * 判断存储卡是否可用
     */
    public boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            Map<String, String> agrs = new HashMap<String, String>();
            agrs.put("app_key", Config.getInstance(context).getConfig("app_key"));
            Map<String, File> files = new HashMap<String, File>();
            files.put("upload", headPIC);
            UploadUtil.post(ServerInterface.SET_HEAD_PIC, agrs, files);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            webView.reload();
            progressDialog.dismiss();
            setResult(MineFragment.ZHXX, null);
            super.onPostExecute(s);
        }

        public MyAsyncTask() {
            super();
        }
    }


    /*****************
     * 超时锁
     *************/
    @Override
    protected void onPause() {
        super.onPause();
        if (isAppBackground(getApplicationContext())) {
            Log.d(TAG, "TimeOut:" + getClass().getSimpleName());
            if (!getClass().getSimpleName().equals("GestureUnlockActivity")) {
                countDownTimer.start();
                Log.d(TAG, "TimeOut start");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isAppBackground(getApplicationContext())) {
            countDownTimer.cancel();
            Log.d(TAG, "TimeOut cancel");
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
