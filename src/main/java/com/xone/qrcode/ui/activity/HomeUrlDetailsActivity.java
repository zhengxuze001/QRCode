package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xone.qrcode.R;

public class HomeUrlDetailsActivity extends BaseActivity {
    private TextView websiteNameTextView;
    private TextView websiteHomeUrlTextView;
    private TextView examineTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_url_details);
        setActivityName("首页信息");

        initViews();

        Intent intent = getIntent();
        String websiteName = intent.getStringExtra("websiteName");
        String websiteHomeUrl = intent.getStringExtra("websiteHomeUrl");
        String examineTime = intent.getStringExtra("examineTime");

        setTextViewText(websiteNameTextView, websiteName);
        setTextViewText(websiteHomeUrlTextView, websiteHomeUrl);
        setTextViewText(examineTimeTextView, examineTime);
    }

    private void initViews() {
        websiteNameTextView = (TextView) findViewById(R.id.websiteNameTextView);
        websiteHomeUrlTextView = (TextView) findViewById(R.id.websiteHomeUrlTextView);
        examineTimeTextView = (TextView) findViewById(R.id.examineTimeTextView);
    }

    private void setTextViewText(TextView textView, String str) {
        if (null != str && null != textView) {
            textView.setText(str);
        }
    }
}
