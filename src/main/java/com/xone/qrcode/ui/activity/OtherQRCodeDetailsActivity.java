package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xone.qrcode.R;

public class OtherQRCodeDetailsActivity extends BaseActivity {
    private TextView mContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_qrcode_details);
        setActivityName("识别结果");

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");

        mContentTextView = (TextView) findViewById(R.id.content_textView);
        if (null != content) {
            mContentTextView.setText(content);
        } else {
            mContentTextView.setText("暂无内容");
        }
    }
}
