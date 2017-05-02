package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.Appeal;
import com.xone.qrcode.model.entities.User;
import com.xone.qrcode.ui.dialog.LoadingDialog;

import java.net.MalformedURLException;
import java.net.URL;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AppealActivity extends BaseActivity implements View.OnClickListener {
    private TextView appealUrlTextView;
    private View appealBtn;
    private String mUrl;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal);
        setActivityName("网站申诉");

        initViews();
        initClicks();
    }

    private void initViews() {
        appealUrlTextView = (TextView) findViewById(R.id.appealUrlTextView);
        appealBtn = findViewById(R.id.appealBtn);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        if (null != mUrl) {
            appealUrlTextView.setText(mUrl);
        }
    }

    private void initClicks() {
        appealBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appealBtn:
                doAppeal();
                break;
            default:
                break;
        }
    }

    private void doAppeal() {
        if (null == User.getCurrentUser()) {
            startActivity(new Intent(AppealActivity.this, LoginActivity.class));
            return;
        }
        if (null == mUrl) {
            showToast("申诉的网站地址不能为空");
            return;
        }

        mLoadingDialog = new LoadingDialog(AppealActivity.this, "提交中...");
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();

        Appeal appeal = new Appeal();
        appeal.setPublisher(User.getCurrentUser(User.class));
        try {
            URL url = new URL(mUrl);
            mUrl = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        appeal.setWebsiteUrl(mUrl);
        appeal.save(new SaveListener<String>() {
            @Override
            public void done(String s, final BmobException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null == e) {
                            mLoadingDialog.dismiss();
                            showToast("提交成功，我们会尽快核实！");
                            finish();
                        } else {
                            mLoadingDialog.dismiss();
                            showToast(e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
