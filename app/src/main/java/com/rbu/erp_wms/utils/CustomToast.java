package com.rbu.erp_wms.utils;

import android.content.Context;
import android.widget.Toast;

import com.rbu.erp_wms.base.ErpApplication;

/**
 * 吐司工具类
 */
public class CustomToast {

    private static Context context = null;
    private static Toast   toast   = null;
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return ErpApplication.getContext();
    }

    public static void getShortToast(Context context, int retId){
        if (toast == null) {
            toast = Toast.makeText(context, retId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(retId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }


    public static void getShortToastByString(Context context, String hint){
        if (toast == null) {
            toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
        } else {
            toast.setText(hint);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }


    public static void getLongToast(Context context, int retId){
        if (toast == null) {
            toast = Toast.makeText(context, retId, Toast.LENGTH_LONG);
        } else {
            toast.setText(retId);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }


    public static void getLongToastByString(Context context, String hint){
        if (toast == null) {
            toast = Toast.makeText(context, hint, Toast.LENGTH_LONG);
        } else {
            toast.setText(hint);
            toast.setDuration(Toast.LENGTH_LONG);
        }

        toast.show();
    }

    public static void toastLong(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
    }

    public static void toastShort(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }
}
