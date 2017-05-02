package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.xone.qrcode.R;

public class ReportDetailsActivity extends BaseActivity {
    private TextView websiteUrlTextView;
    private TextView reportReasonTextView;
    private TextView reportTimeTextView;
    private TextView reportDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);
        setActivityName("举报详情");

        initViews();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        setTextViewText(websiteUrlTextView, intent.getStringExtra("websiteUrl"));
        setTextViewText(reportReasonTextView, intent.getStringExtra("reportReason"));
        setTextViewText(reportTimeTextView, intent.getStringExtra("reportTime"));
        setTextViewText(reportDetailsTextView, intent.getStringExtra("reportDetails"));
    }

    private void initViews() {
        websiteUrlTextView = (TextView) findViewById(R.id.websiteUrlTextView);
        reportReasonTextView = (TextView) findViewById(R.id.reportReasonTextView);
        reportTimeTextView = (TextView) findViewById(R.id.reportTimeTextView);
        reportDetailsTextView = (TextView) findViewById(R.id.reportDetailsTextView);
    }

    private void setTextViewText(TextView textView, String str) {
        if (!TextUtils.isEmpty(str) && null != textView) {
            textView.setText(str);
        }
    }
}
