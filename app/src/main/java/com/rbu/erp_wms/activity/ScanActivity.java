package com.rbu.erp_wms.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rbu.erp_wms.R;
import com.rbu.erp_wms.base.Constants;
import com.rbu.erp_wms.utils.LogUtils;
import com.rbu.erp_wms.utils.ScanSettingUtil;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/12 10:41
 * @描述 扫描Activity
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ScanActivity extends Activity implements View.OnClickListener {

    private ScanManager mScanManager;
    private boolean   isScaning = false;
    private SoundPool soundpool = null;
    private int       soundid;
    private Vibrator  mVibrator;
    private String    codeStr;
    private ImageView setting;
    private ImageView back;


    private ProgressDialog mProgressDialog;

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.ACTION_RESPONSE_DONE:
                    LogUtils.e("ACTION_RESPONSE_DONE");
                    break;

                case Constants.SCAN_ACTION:
                    isScaning = false;
                    soundpool.play(soundid, 1, 1, 0, 0, 1);
                    mVibrator.vibrate(100);

                    byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
                    int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
                    //                byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
                    //            LogUtils.e(TAG+"debug----codetype--" + temp);
                    codeStr = new String(barcode, 0, barcodelen); //扫描后的数据
                    //扫描后发送数据到MainActivity的onActivityResult,并关闭scanActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("data",codeStr);
                    ScanActivity.this.setResult(Constants.RESULT_SCAN,resultIntent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scan);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        back = (ImageView) findViewById(R.id.scan_back);
        setting = (ImageView) findViewById(R.id.scan_settings);
        back.setOnClickListener(this);
        setting.setOnClickListener(this);

        mProgressDialog = ProgressDialog.show(this, null, "扫描中，请稍候...");
        mProgressDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initScan();
        IntentFilter filter = new IntentFilter();
        int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
        String[] value_buf = mScanManager.getParameterString(idbuf);
        if (value_buf != null && value_buf[0] != null && !value_buf[0].equals("")) {
            filter.addAction(value_buf[0]);
        } else {
            filter.addAction(Constants.SCAN_ACTION);
        }

        filter.addAction(Constants.ACTION_RESPONSE_DONE);
        registerReceiver(mScanReceiver, filter);

        open_scan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScanManager != null) {
            mScanManager.stopDecode();
            isScaning = false;
        }

        close_scan();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScanReceiver);
        mProgressDialog.dismiss();
    }

    /**
     * 初始化扫描
     */
    private void initScan() {
        mScanManager = new ScanManager();
        mScanManager.openScanner();

        mScanManager.switchOutputMode(0);
        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundid = soundpool.load("/etc/Scan_new.ogg", 1);
    }

    /**
     * 打开扫描
     */
    private void open_scan() {
        mScanManager.stopDecode();
        isScaning = true;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mScanManager.startDecode();
    }

    /**
     * 关闭扫描
     */
    private void close_scan() {
        mScanManager.stopDecode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_back:
                finish();
                break;
            case R.id.scan_settings:
                ScanSettingUtil.method_setting(this);
                break;
        }
    }
}
