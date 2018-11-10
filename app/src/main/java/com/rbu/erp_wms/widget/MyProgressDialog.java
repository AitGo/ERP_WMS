package com.rbu.erp_wms.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbu.erp_wms.R;


/**
 * @创建者 liuyang
 * @创建时间 2018/6/8 15:48
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MyProgressDialog extends ProgressDialog {

    private Animation animRotate;
    private ImageView ivProgress;
    private TextView  tvProgress;
    private String    message;
    private boolean isShow = false;

    public MyProgressDialog(Context context, String message) {
        super(context, R.style.Translucent_NoTitle);
        this.message = message;
        animRotate = AnimationUtils.loadAnimation(context, R.anim.progress_dialog);
        setIndeterminate(true);
        setCancelable(false);
    }

    public void SetMessage(Context context, String message) {
        tvProgress.setText(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_progress_dialog);
        ivProgress = (ImageView) findViewById(R.id.ivProgress);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        tvProgress.setText(message);
//        ivProgress.startAnimation(animRotate);
        ivProgress.setAnimation(animRotate);
        animRotate.start();
    }

    @Override
    public void show() {
        if (!isShow) {
            super.show();
            isShow = true;
        }


    }

    @Override
    public void dismiss() {
        if (isShow) {
            super.dismiss();
            isShow = false;
        }

//        animRotate.cancel();
    }
}

