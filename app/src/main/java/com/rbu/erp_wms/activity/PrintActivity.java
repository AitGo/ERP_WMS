package com.rbu.erp_wms.activity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.rbu.erp_wms.R;
import com.rbu.erp_wms.adapter.DeviceListAdapter;
import com.rbu.erp_wms.base.Constants;
import com.rbu.erp_wms.utils.CustomToast;
import com.rbu.erp_wms.utils.LogUtils;
import com.rbu.erp_wms.utils.PicassoUtil;

import java.util.ArrayList;
import java.util.List;

import bluetoothprinter.ui.BasePrintActivity;
import bluetoothprinter.utils.BluetoothUtil;
import bluetoothprinter.utils.PrintContract;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/13 11:40
 * @描述 打印Activity
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class PrintActivity extends BasePrintActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final static int TASK_TYPE_PRINT = 1;//打印
    private ImageView iv_close;
    private ListView lv_devices;
    private Button btn_print;
    private Button btn_setting;
    private DeviceListAdapter mAdapter;
    private boolean isChecked = false;
    private int mSelectedPosition = -1;
    public static ArrayList<String> data;
    private List<String> printDatas = new ArrayList<>();

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

        mAdapter = new DeviceListAdapter(this);
        lv_devices.setAdapter(mAdapter);
        lv_devices.setOnItemClickListener(this);

        //打印数据赋值
        printDatas = data;

    }

    @Override
    public void onConnected(BluetoothSocket socket, int taskType) {
        switch (taskType) {
            case TASK_TYPE_PRINT:
                if (socket == null || !socket.isConnected()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomToast.toastShort("连接打印机失败");
                        }
                    });

                } else {
                    if (printDatas != null) {
                        if (printDatas.size() == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomToast.toastShort("没有数据打印");
                                    return;
                                }
                            });
                        }
                        for (String s : printDatas) {
                            Bitmap bitmap = PicassoUtil.convertStringToIcon(s);
                            PrintContract.printPdfBitmap(bitmap, socket);
                        }

                    }
                    finish();
                }
                break;
        }
    }

    private void connectDevice(int taskType) {
        if (mSelectedPosition >= 0) {
            BluetoothDevice device = mAdapter.getItem(mSelectedPosition);
            if (device != null)
                super.connectDevice(device, taskType);
        } else {
            Toast.makeText(this, "还未选择打印设备", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillAdapter();
    }

    /**
     * 从所有已配对设备中找出打印设备并显示
     */
    private void fillAdapter() {
        //推荐使用 BluetoothUtil.getPairedPrinterDevices()
        List<BluetoothDevice> printerDevices = BluetoothUtil.getPairedDevices();
        mAdapter.clear();
        mAdapter.addAll(printerDevices);
        if (printerDevices.size() > 0) {
            btn_setting.setText("配对更多设备");
        } else {
            btn_setting.setText("还未配对打印机，去设置");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_print_close:
                finish();
                break;
            case R.id.btn_print:
                connectDevice(TASK_TYPE_PRINT);
                break;
            case R.id.btn_goto_setting:
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isChecked = !isChecked;
        if (isChecked) {
            mSelectedPosition = position;
        } else {
            mSelectedPosition = -1;
        }
        mAdapter.setChecked(isChecked);
        mAdapter.notifyDataSetChanged();
    }
}
