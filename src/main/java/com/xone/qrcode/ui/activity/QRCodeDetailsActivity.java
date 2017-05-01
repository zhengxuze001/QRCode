package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.IcpLicensing;
import com.xone.qrcode.model.entities.WebsiteSecurityResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QRCodeDetailsActivity extends BaseActivity {
    private String mUrl;
    private View mContentView;
    private View mSecureLayout;
    private TextView mSecurityTextView;
    private TextView mSecurityHintTextView;
    private TextView mIcpTextView;
    private TextView mHomeUrlTextView;
    private ProgressBar mProgressBar;
    private TextView mErrorView;
    private TextView mSafeOpeningBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_details);
        setActivityName("安全识别");

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");

        initViews();
        showLoading();
        initSecurity();
    }

    private void initViews() {
        mContentView = findViewById(R.id.contentView);
        mSecureLayout = findViewById(R.id.secure_layout);
        mSecurityTextView = (TextView) findViewById(R.id.security_textView);
        mSecurityHintTextView = (TextView) findViewById(R.id.security_hint_textView);
        mIcpTextView = (TextView) findViewById(R.id.icp_textView);
        mHomeUrlTextView = (TextView) findViewById(R.id.homeUrl_textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mErrorView = (TextView) findViewById(R.id.error);
        mSafeOpeningBtn = (TextView) findViewById(R.id.safeOpeningBtn);
    }

    private void initSecurity() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("websiteUrl", mUrl)
                .build();
        Request requestPost = new Request.Builder()
                .url("http://119.29.75.211:666")
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorMsg(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final WebsiteSecurityResponse websiteSecurityResponse = gson.fromJson(result, WebsiteSecurityResponse.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (1 == websiteSecurityResponse.getStatus()) {
                            mSecurityTextView.setText(websiteSecurityResponse.isSecurity() ? R.string.app_text_secure : R.string.app_text_insecure);
                            Drawable topDrawable = getResources().getDrawable(websiteSecurityResponse.isSecurity() ? R.drawable.ic_secure : R.drawable.ic_insecure);
                            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                            mSecurityTextView.setCompoundDrawables(null, topDrawable, null, null);
                            mSecurityHintTextView.setText(websiteSecurityResponse.isSecurity() ? R.string.app_text_secure_message : R.string.app_text_insecure_message);
                            mSecureLayout.setBackgroundResource(websiteSecurityResponse.isSecurity() ? R.color.app_color_secure : R.color.app_color_insecure);
                            setToolbarColor(websiteSecurityResponse.isSecurity() ? R.color.app_color_secure : R.color.app_color_insecure);
                            mSafeOpeningBtn.setVisibility(websiteSecurityResponse.isSecurity() ? View.VISIBLE : View.GONE);
                            IcpLicensing icpLicensing = websiteSecurityResponse.getIcpLicensing();
                            if (null != icpLicensing) {
                                setTextViewText(mIcpTextView, icpLicensing.getOrganizerName());
                                setTextViewText(mHomeUrlTextView, icpLicensing.getWebsiteHomeUrl());
                            }
                            hideLoading();
                        } else if (0 == websiteSecurityResponse.getStatus()) {
                            showErrorMsg(websiteSecurityResponse.getMsg());
                        }
                    }
                });
            }
        });
    }

    private void setTextViewText(TextView textView, String str) {
        if (null != str && null != textView) {
            textView.setText(str);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icp_layout:

                break;
            case R.id.homeUrl_layout:

                break;
            case R.id.safeOpeningBtn:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(mUrl);
                intent.setData(content_url);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void showLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
    }

    private void showErrorMsg(String msg) {
        mErrorView.setVisibility(View.VISIBLE);
        mErrorView.setText(msg);
        mProgressBar.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
    }
}
