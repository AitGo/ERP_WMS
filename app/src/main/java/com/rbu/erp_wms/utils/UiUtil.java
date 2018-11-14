package com.rbu.erp_wms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.rbu.erp_wms.base.ErpApplication;


/**
 * ui工具类
 */
public class UiUtil {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return ErpApplication.getContext();
    }

    /**
     * px --> dp
     */
    public static int px2dp(Context context, int px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int            dpi     = metrics.densityDpi;
        // > 公式: 1dp = 1px * 160 / dpi
        return (int) (px * 160f / dpi + 0.5f);
    }

    /**
     * dp ---> px
     */
    public static int dp2px(Context context, int dp) {
        // > 公式: 1px = 1dp * (dpi / 160)

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int            dpi     = metrics.densityDpi;

        return (int) (dp * (dpi / 160f) + 0.5f);

    }

    /**
     * 获取资源文件字符串
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 获取屏幕宽度
     */
    public static Integer getWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return  wm.getDefaultDisplay().getWidth();
    }

    //显示图标
    public static void showIcon(Activity context, String packageName){
        PackageManager p = context.getPackageManager();
//        p.setComponentEnabledSetting(context.getComponentName(),
//                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
        p.setApplicationEnabledSetting(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, 0);
    }

    //隐藏图标
    public static void hideIcon(Activity context, String packageName){
        PackageManager p = context.getPackageManager();
//        p.setComponentEnabledSetting(context.getComponentName(),
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER, PackageManager.DONT_KILL_APP);
        p.setApplicationEnabledSetting(packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER, 0);
    }

    /**
     * 给TextView右边设置图片
     */
    public static void setTextImage(int resId,TextView view) {
        Drawable drawable = UiUtil.getContext().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        view.setCompoundDrawables(null, null, drawable, null);
    }

    //下一页activity跳转
    public static void nextNOfinishActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    //下一页activity跳转
    public static void nextFinishActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

}
