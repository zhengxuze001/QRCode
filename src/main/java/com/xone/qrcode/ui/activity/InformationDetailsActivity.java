package com.xone.qrcode.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.Information;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class InformationDetailsActivity extends BaseActivity implements View.OnClickListener {
    private WebView mWebView;
    private TextView mErrorView;
    private ProgressBar mProgressBar;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details);
        setActivityName("资讯正文");

        objectId = getIntent().getStringExtra("objectId");
        if (null == objectId) {
            return;
        }

        initView();
        initClick();
        initData();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mErrorView = (TextView) findViewById(R.id.error);
    }

    private void initClick() {
        mErrorView.setOnClickListener(this);
    }

    private void initData() {
        showLoading();
        BmobQuery<Information> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<Information>() {

            @Override
            public void done(Information information, BmobException e) {
                if (null == e) {
                    mWebView.loadData(information.getContent(), "text/html; charset=utf-8", "utf-8");
                    hideLoading();
                } else {
                    showErrorMsg(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error:
                initData();
                break;
            default:
                break;
        }
    }

    public void showLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.GONE);
    }

    public void hideLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
    }

    public void showErrorMsg(String msg) {
        mErrorView.setVisibility(View.VISIBLE);
        mErrorView.setText(msg);
        mProgressBar.setVisibility(View.GONE);
        mWebView.setVisibility(View.GONE);
    }
}
