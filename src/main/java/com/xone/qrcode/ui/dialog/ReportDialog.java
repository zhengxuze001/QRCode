package com.xone.qrcode.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.xone.qrcode.R;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class ReportDialog extends Dialog implements View.OnClickListener {
    private View reportBtn;
    private View appealBtn;

    public ReportDialog(Context context) {
        super(context, R.style.app_theme_dialog_push_btm);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        reportBtn = findViewById(R.id.reportBtn);
        appealBtn = findViewById(R.id.appealBtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reportBtn:

                break;
            case R.id.appealBtn:

                break;
            default:
                break;
        }
    }
}
