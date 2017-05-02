package com.xone.qrcode.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.xone.qrcode.R;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class LoadingDialog extends Dialog {
    private TextView textView;
    private String msg;

    public LoadingDialog(Context context) {
        super(context, R.style.app_theme_dialog_normal);
    }

    public LoadingDialog(Context context, String msg) {
        super(context, R.style.app_theme_dialog_normal);
        this.msg = msg;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        textView = (TextView) findViewById(R.id.textView);
        if (null != msg) {
            textView.setText(msg);
        }
    }

}