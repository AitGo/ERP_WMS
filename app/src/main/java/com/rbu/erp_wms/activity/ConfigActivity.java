package com.rbu.erp_wms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rbu.erp_wms.R;
import com.rbu.erp_wms.utils.CustomToast;
import com.rbu.erp_wms.utils.LogUtils;
import com.rbu.erp_wms.utils.SPUtils;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/15 10:36
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class ConfigActivity extends Activity implements View.OnClickListener {

    private TextView tv_version;
    private EditText et_url;
    private Button btn_confirm;
    private String url = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_config);
        tv_version = (TextView) findViewById(R.id.tv_config_version);
        et_url = (EditText) findViewById(R.id.et_config_url);
        btn_confirm = (Button) findViewById(R.id.btn_config_confirm);

        btn_confirm.setOnClickListener(this);

        initData();
    }

    private void initData() {
        try {
            url = (String) SPUtils.getParam(getApplication(),"url","");
            LogUtils.e(url);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }

        if(url != null) {
            et_url.setText(url);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_config_confirm:
                String url = et_url.getText().toString().trim();
                if(url != null && !url.equals("")) {
                    SPUtils.setParam(this,"url",url);
                    Intent intent = new Intent(ConfigActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    CustomToast.toastLong("请输入url地址！");
                }
                break;
        }
    }
}
