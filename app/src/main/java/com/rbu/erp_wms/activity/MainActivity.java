package com.rbu.erp_wms.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.rbu.erp_wms.R;
import com.rbu.erp_wms.interfase.CallBackFunction;
import com.rbu.erp_wms.utils.LogUtils;
import com.rbu.erp_wms.utils.WebViewUtils;
import com.rbu.erp_wms.widget.MyDialog;
import com.rbu.erp_wms.widget.MyEditDialog;

public class MainActivity extends Activity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private WebViewUtils     mWebViewUtils;
    private WebView          mWebView;
    private Button           btn1;
    private Button           btn2;
    private MyEditDialog     dialog;
    private MyDialog.Builder builder;

    private String webLoadUrl = "file:///android_asset/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initWebViewSetting();

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

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        mWebView.addJavascriptInterface(new AndroidToJs(), "rbu");//AndroidtoJS类对象映射到js的test对象

        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
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

    }

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
