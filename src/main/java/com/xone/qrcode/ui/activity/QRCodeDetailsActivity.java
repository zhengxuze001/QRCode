package com.xone.qrcode.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xone.qrcode.R;
import com.xone.qrcode.app.MyApplication;
import com.xone.qrcode.db.ScanHistoryManager;

public class QRCodeDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_details);


        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String content = intent.getStringExtra("content");
        String imagePath = intent.getStringExtra("imgPath");

        ((TextView) findViewById(R.id.temp)).setText("type = " + type + " \n content = " + content);

        Glide.with(this).load(imagePath).into((ImageView) findViewById(R.id.tempImageView));

        saveScanHistory(type, content, imagePath);
    }

    private void saveScanHistory(String type, String content, String imagePath) {
        ScanHistoryManager scanHistoryManager = new ScanHistoryManager(MyApplication.getInstance().getApplicationContext());
        ContentValues values = new ContentValues();
        values.put("QRCodeImgPath", imagePath);
        values.put("QRCodeContent", content);
        values.put("QRCodeType", type);
        values.put("QRCodeTime", System.currentTimeMillis());
        if (scanHistoryManager.save("ScanHistory", values) < 1) {
            showToast("存储扫描历史失败");
        }
    }
}
