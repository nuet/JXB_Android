package com.lenso.jixiangbao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.api.HTMLInterface;
import com.lenso.jixiangbao.api.JSInterface;
import com.lenso.jixiangbao.http.VolleyHttp;
import com.lenso.jixiangbao.util.Config;
import com.lenso.jixiangbao.view.ProgressWheel;
import com.lenso.jixiangbao.view.TopMenuBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/17.
 */
public class WebViewActivity extends WebBaseActivity {
    @Bind(R.id.top_menu_bar)
    TopMenuBar topMenuBar;
    @Bind(R.id.fl_web)
    FrameLayout flWeb;

    private File headPIC;
    private static WebView webView;
    private static boolean calculator;
    private String url;
    private String title;
    private String apr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        topMenuBar.setMenuTopPadding(statusHeight);

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

    }

    public static void reload() {
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
                        showToast("未找到存储卡，无法存储照片！");
                    }
                    break;
                case 2:
                    startPhotoZoom(data.getData());
                    break;
                case 0:
                    if (data != null) {
                        saveImage(data);
                    }
                    break;
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



            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                Log.i("headPIC", "UNSAVED1");
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.i("headPIC", "UNSAVED2");
                e.printStackTrace();
            }

            Log.i("headPIC--path", headPIC.getPath());

        }
    }

    /**
     * 判断存储卡是否可用
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
