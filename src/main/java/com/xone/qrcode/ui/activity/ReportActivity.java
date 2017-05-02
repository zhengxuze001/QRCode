package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xone.qrcode.R;

public class ReportActivity extends BaseActivity implements View.OnClickListener {
    private TextView reportUrlTextView;
    private RadioGroup reportRadioGroup;
    private EditText reportEditText;
    private View reportBtn;
    private String mUrl;

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

                break;
            default:
                break;
        }
    }
}
