package com.rbu.erp_wms.base;

import android.content.Intent;
import android.device.ScanManager;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/12 16:41
 * @描述 ${常量类}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class Constants {

    public final static Integer RESULT_SCAN = 1;
    public final static Integer REQUEST_SCAN = 2;

    public final static String SCAN_ACTION = ScanManager.ACTION_DECODE;//default action
    public final static String ACTION_RESPONSE_DONE = "com.rbu.intentservice.response_done";



}
