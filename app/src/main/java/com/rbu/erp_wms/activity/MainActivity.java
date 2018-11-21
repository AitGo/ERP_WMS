package com.rbu.erp_wms.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ZoomButtonsController;

import com.google.gson.Gson;
import com.rbu.erp_wms.R;
import com.rbu.erp_wms.base.Constants;
import com.rbu.erp_wms.diagnose.PrintData;
import com.rbu.erp_wms.interfase.CallBackFunction;
import com.rbu.erp_wms.utils.CustomToast;
import com.rbu.erp_wms.utils.LogUtils;
import com.rbu.erp_wms.utils.SPUtils;
import com.rbu.erp_wms.utils.WebViewUtils;
import com.rbu.erp_wms.widget.MyDialog;
import com.rbu.erp_wms.widget.MyEditDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private WebViewUtils     mWebViewUtils;
    private WebView          mWebView;
    private Button           btn1;
    private Button           btn2;
    private MyEditDialog     dialog;
    private MyDialog.Builder builder;

    private String webLoadUrl = "file:///android_asset/index.html";
//    private String webLoadUrl = "http://172.20.3.224:8089/#/login";
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initWebViewSetting();

    }

    private void initData() {
        String url = (String) SPUtils.getParam(this,"url","");
        if(!url.equals("")) {
            webLoadUrl = url;
        }
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.wv_main);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        mWebViewUtils = new WebViewUtils(mWebView);

        builder = new MyDialog.Builder(this);
    }

    private void initWebViewSetting() {
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //去掉滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        mWebView.addJavascriptInterface(new AndroidToJs(), "rbu");//AndroidtoJS类对象映射到js的test对象

        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = mWebView.getSettings().getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);//利用反射机制去修改设置对象
                if (method != null) {
                    method.invoke(mWebView.getSettings(), true);//修改设置
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 先载入JS代码
        mWebView.loadUrl(webLoadUrl);

        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

//                dialog = builder.setTitle(getString(R.string.prompt))
//                        .setText(message)
//                        .setPositiveButton(getString(R.string.confirm), new MyDialog.ConfirmListener() {
//                            @Override
//                            public void onClick() {
//                                result.confirm();
//                                dialog.dismiss();
//                            }
//                        })
//                        .create();
//                dialog.show();
//                return true;
                return super.onJsAlert(view,url,message,result);
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try{
                    if(url.startsWith("baiduboxapp://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }

                view.loadUrl(url);
                return true;
            }
        });

        // 点击后退按钮,让WebView后退一页
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) { // 表示按返回键
                        // 时的操作
                        mWebView.goBack(); // 后退
                        // webview.goForward();//前进
                        return true; // 已处理
                    }
                }
                return false;
            }
        });

    }

    /**
     * js调用android的函数
     * 函数需要使用@JavascriptInterface注解后js才能调用
     *
     */
    public class AndroidToJs extends Object {

        @JavascriptInterface
        public void test(final String msg) {
            LogUtils.e(msg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebViewUtils.useJsMethodWithoutReturn("androidUseJs2",msg );
                }
            });

        }

        /**
         * 扫描目前只是把扫描结果的string发给js
         */
        @JavascriptInterface
        public void startScanActivity() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,ScanActivity.class);
            startActivityForResult(intent, Constants.REQUEST_SCAN);
        }

        /**
         * 调取pda打印方法，需要js把箱唛数据包装成json发送过来，解析json放入data中
         * @param json
         */
        @JavascriptInterface
        public void startPrintActivity(String json) {
            data.clear();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,PrintActivity.class);

            //json解析出String数据，放入data中
            Gson gson = new Gson();
            PrintData printData = gson.fromJson(json, PrintData.class);
            data.addAll(printData.getData());
            PrintActivity.data = data;
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_SCAN) {
            if(resultCode == Constants.RESULT_SCAN) {
                //扫描结果
                String codeStr = data.getStringExtra("scanData");
                CustomToast.toastShort(codeStr);
                mWebViewUtils.useJsMethodWithoutReturn("androidUseJs2",codeStr );
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mWebViewUtils.useJsMethodWithoutReturn("androidUseJs1","" );
                break;

            case R.id.btn_2:
                mWebViewUtils.useJsMethodWithReturn("androidUseJs2", "111", new CallBackFunction() {
                    @Override
                    public void onCallBack(String value) {
                        LogUtils.e(value);
                    }
                });
                break;
        }
    }

}
