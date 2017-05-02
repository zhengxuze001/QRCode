package com.xone.qrcode.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.xone.qrcode.R;
import com.xone.qrcode.ui.activity.AppealActivity;
import com.xone.qrcode.ui.activity.ReportActivity;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class ReportDialog extends Dialog implements View.OnClickListener {
    private View reportBtn;
    private View appealBtn;
    private Context mContext;
    private String mUrl;

    public ReportDialog(Context context, String url) {
        super(context, R.style.app_theme_dialog_push_btm);
        mContext = context;
        mUrl = url;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initViews();
        initClicks();
    }

    private void initClicks() {
        reportBtn.setOnClickListener(this);
        appealBtn.setOnClickListener(this);
    }

    private void initViews() {
        reportBtn = findViewById(R.id.reportBtn);
        appealBtn = findViewById(R.id.appealBtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reportBtn:
                Intent intent = new Intent(mContext, ReportActivity.class);
                intent.putExtra("url", mUrl);
                mContext.startActivity(intent);
                break;
            case R.id.appealBtn:
                Intent intent2 = new Intent(mContext, AppealActivity.class);
                intent2.putExtra("url", mUrl);
                mContext.startActivity(intent2);
                break;
            default:
                break;
        }
        dismiss();
    }
}
