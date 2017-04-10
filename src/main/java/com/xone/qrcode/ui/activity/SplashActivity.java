package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.xone.qrcode.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        // ignore onBackPressed event
    }
}
