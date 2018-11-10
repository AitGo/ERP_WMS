package com.rbu.erp_wms.utils;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 全局Application
 */

public class ErpApplication extends Application {
    private static Context mContext;
    private static Handler mHandler;
    private static long    mMainThreadId;
//    public JQPrinter        printer   = null;
    public BluetoothAdapter btAdapter = null;
    private List<Activity> mActivityList;//用于存放所有启动的Activity的集合

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
//        new NetInterceptor("LoginActivity***");
        mHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new NetInterceptor("ErpApplication1111***"))//设置拦截器
                .addInterceptor(new LoggerInterceptor("ErpApplication***"))//设置拦截器
                .connectTimeout(25000L, TimeUnit.MILLISECONDS)
                .readTimeout(25000L, TimeUnit.MILLISECONDS)
                //其他配置
                .cookieJar(cookieJar) //持久化cookie
                .build();

        OkHttpUtils.initClient(okHttpClient);

        mActivityList = new ArrayList<Activity>();
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取全局handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * 获取主线程id
     */
    public static long getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 延时提交一个任务
     */
    public static void post(Runnable task){
        mHandler.post(task);
    }

    /**
     * 提交一个任务
     */
    public static void postDelay(Runnable task, long delayTime){
        mHandler.postDelayed(task, delayTime);
    }

    /**
     * 移除一个任务
     */
    public static void remove(Runnable task) {
        mHandler.removeCallbacks(task);
    }

    public static void postTaskSafely(Runnable task) {

        long curThreadId = android.os.Process.myTid();
        long mainThreadId = getMainThreadId();

        if (curThreadId == mainThreadId) {
            task.run();
        } else {
            post(task);
        }
    }


    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!mActivityList.contains(activity)) {
            mActivityList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : mActivityList) {
            activity.finish();
        }
    }


}
