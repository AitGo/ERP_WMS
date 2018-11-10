package com.rbu.erp_wms.utils;



import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/10 18:45
 * @描述 ${扫描相关的工具类}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class ScanSettingUtil {
    //设置方法
    public static void method_setting(Context context) {
        try{
            Intent intent = new Intent("android.intent.action.SCANNER_SETTINGS");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
