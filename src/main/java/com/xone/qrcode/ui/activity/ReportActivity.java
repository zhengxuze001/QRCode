package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.Report;
import com.xone.qrcode.model.entities.User;
import com.xone.qrcode.ui.dialog.LoadingDialog;

import java.net.MalformedURLException;
import java.net.URL;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ReportActivity extends BaseActivity implements View.OnClickListener {
    private TextView reportUrlTextView;
    private RadioGroup reportRadioGroup;
    private EditText reportEditText;
    private View reportBtn;
    private String mUrl;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setActivityName("举报网站");

        initViews();
        initClicks();
    }

    private void initClicks() {
        reportBtn.setOnClickListener(this);
    }

    private void initViews() {
        reportUrlTextView = (TextView) findViewById(R.id.reportUrlTextView);
        reportRadioGroup = (RadioGroup) findViewById(R.id.reportRadioGroup);
        reportEditText = (EditText) findViewById(R.id.reportEditText);
        reportBtn = findViewById(R.id.reportBtn);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        if (null != mUrl) {
            reportUrlTextView.setText(mUrl);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reportBtn:
                doReport();
                break;
            default:
                break;
        }
    }

    private void doReport() {
        if (null == User.getCurrentUser()) {
            startActivity(new Intent(ReportActivity.this, LoginActivity.class));
            return;
        }
        if (null == mUrl) {
            showToast("举报的网站地址不能为空");
            return;
        }

        mLoadingDialog = new LoadingDialog(ReportActivity.this, "提交中...");
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();

        Report report = new Report();
        report.setPublisher(User.getCurrentUser(User.class));
        try {
            URL url = new URL(mUrl);
            mUrl = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        report.setWebsiteUrl(mUrl);
        RadioButton checkedRadioButton = (RadioButton) reportRadioGroup.findViewById(reportRadioGroup.getCheckedRadioButtonId());
        report.setReportReason(String.valueOf(checkedRadioButton.getText()));
        String reportDetails = reportEditText.getText().toString().trim();
        report.setReportDetails(reportDetails);
        report.save(new SaveListener<String>() {
            @Override
            public void done(String s, final BmobException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null == e) {
                            mLoadingDialog.dismiss();
                            showToast("提交成功，感谢您的举报！");
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
