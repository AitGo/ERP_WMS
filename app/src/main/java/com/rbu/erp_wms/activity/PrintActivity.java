package com.rbu.erp_wms.activity;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.rbu.erp_wms.R;
import com.rbu.erp_wms.base.Constants;

import bluetoothprinter.ui.BasePrintActivity;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/13 11:40
 * @描述 打印Activity
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class PrintActivity extends BasePrintActivity implements View.OnClickListener {

    private ImageView iv_close;
    private ListView lv_devices;
    private Button btn_print;
    private Button btn_setting;
    private final static int TASK_TYPE_PRINT = 1;//打印

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_print);
        iv_close = (ImageView) findViewById(R.id.iv_print_close);
        lv_devices = (ListView) findViewById(R.id.lv_print_devices);
        btn_print = (Button) findViewById(R.id.btn_print);
        btn_setting = (Button) findViewById(R.id.btn_goto_setting);

        iv_close.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_print.setOnClickListener(this);
    }

    @Override
    public void onConnected(BluetoothSocket socket, int taskType) {
        switch (taskType) {
            case TASK_TYPE_PRINT:

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_print_close:
                finish();
                break;
            case R.id.btn_print:

                break;
            case R.id.btn_goto_setting:

                break;
        }
    }
}
