package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xone.qrcode.R;

public class IcpLicensingDetailsActivity extends BaseActivity {
    private TextView organizerNameTextView;
    private TextView organizerTypeTextView;
    private TextView licenseNumberTextView;
    private TextView examineTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icp_licensing_details);
        setActivityName("备案信息");

        initViews();

        Intent intent = getIntent();
        String organizerName = intent.getStringExtra("organizerName");
        String organizerType = intent.getStringExtra("organizerType");
        String licenseNumber = intent.getStringExtra("licenseNumber");
        String examineTime = intent.getStringExtra("examineTime");

        setTextViewText(organizerNameTextView, organizerName);
        setTextViewText(organizerTypeTextView, organizerType);
        setTextViewText(licenseNumberTextView, licenseNumber);
        setTextViewText(examineTimeTextView, examineTime);
    }

    private void initViews() {
        organizerNameTextView = (TextView) findViewById(R.id.organizerNameTextView);
        organizerTypeTextView = (TextView) findViewById(R.id.organizerTypeTextView);
        licenseNumberTextView = (TextView) findViewById(R.id.licenseNumberTextView);
        examineTimeTextView = (TextView) findViewById(R.id.examineTimeTextView);
    }

    private void setTextViewText(TextView textView, String str) {
        if (null != str && null != textView) {
            textView.setText(str);
        }
    }
}
