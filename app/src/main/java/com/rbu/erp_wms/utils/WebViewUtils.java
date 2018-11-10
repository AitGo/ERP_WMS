package com.rbu.erp_wms.utils;

import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.rbu.erp_wms.interfase.CallBackFunction;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/10 14:41
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class WebViewUtils {

    private WebView mWebView;

    public WebViewUtils(WebView view) {
        this.mWebView = view;
    }

    /**
     * android调用js,没有返回值
     * @param methodName
     * @param parameter 无参传空字符串
     */
    public void useJsMethodWithoutReturn(String methodName, String parameter) {
        mWebView.loadUrl("javascript:" + methodName + "(" + "'" + parameter + "'" + ")");
    }

    /**
     * android调用js,有返回值
     * @param methodName
     * @param parameter 无参传空字符串
     */
    public void useJsMethodWithReturn(String methodName, String parameter,final CallBackFunction callBack) {
        mWebView.evaluateJavascript("javascript:" + methodName + "(" + "'" + parameter + "'" + ")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                callBack.onCallBack(value);
            }
        });
    }
}
