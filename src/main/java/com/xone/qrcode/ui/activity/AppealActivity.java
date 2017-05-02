package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xone.qrcode.R;

public class AppealActivity extends BaseActivity implements View.OnClickListener {
    private TextView appealUrlTextView;
    private View appealBtn;
    private String mUrl;

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

                break;
            default:
                break;
        }
    }
}
