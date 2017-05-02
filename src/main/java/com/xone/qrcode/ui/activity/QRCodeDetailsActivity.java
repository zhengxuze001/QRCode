package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xone.qrcode.R;
import com.xone.qrcode.adapter.CommentAdapter;
import com.xone.qrcode.model.entities.IcpLicensing;
import com.xone.qrcode.model.entities.Report;
import com.xone.qrcode.model.entities.User;
import com.xone.qrcode.model.entities.WebsiteSecurityResponse;
import com.xone.qrcode.ui.dialog.ReportDialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QRCodeDetailsActivity extends BaseActivity implements View.OnClickListener {
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
    private WebsiteSecurityResponse mWebsiteSecurityResponse;
    private TextView mNoCommentTextView;
    private ListView mCommentListView;
    private CommentAdapter mCommentAdapter;
    private ArrayList<Report> mCommentData;
    private View mMoreCommentBtn;

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
        initComment();

        setRightBtn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDialog dialog = new ReportDialog(QRCodeDetailsActivity.this, mUrl);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
    }

    private void initComment() {
        String urlHost = "";
        try {
            URL url = new URL(mUrl);
            urlHost = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BmobQuery<Report> query = new BmobQuery<>();
        query.setLimit(10);
        query.addWhereEqualTo("websiteUrl", urlHost);
        query.include("publisher");
        query.order("-createdAt");
        query.setSkip(0);
        query.findObjects(new FindListener<Report>() {
            @Override
            public void done(List<Report> list, BmobException e) {
                if (null == e) {
                    if (0 == list.size()) {
                        mNoCommentTextView.setVisibility(View.VISIBLE);
                        mCommentListView.setVisibility(View.GONE);
                    } else {
                        mCommentData = new ArrayList<>();
                        mCommentAdapter = new CommentAdapter(QRCodeDetailsActivity.this, mCommentData);
                        mCommentListView.setAdapter(mCommentAdapter);

                        mCommentData.addAll(list);
                        mCommentAdapter.notifyDataSetChanged();
                        mNoCommentTextView.setVisibility(View.GONE);
                        mCommentListView.setVisibility(View.VISIBLE);
                    }
                } else {
                    mNoCommentTextView.setVisibility(View.VISIBLE);
                    mNoCommentTextView.setText("加载评论失败，请重试");
                    mCommentListView.setVisibility(View.GONE);
                }
            }
        });
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
        mErrorView.setOnClickListener(this);
        mSafeOpeningBtn = (TextView) findViewById(R.id.safeOpeningBtn);
        mNoCommentTextView = (TextView) findViewById(R.id.noCommentTextView);
        mNoCommentTextView.setOnClickListener(this);
        mCommentListView = (ListView) findViewById(R.id.commentListView);
        mMoreCommentBtn = findViewById(R.id.moreCommentBtn);
        mMoreCommentBtn.setOnClickListener(this);
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
                mWebsiteSecurityResponse = gson.fromJson(result, WebsiteSecurityResponse.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (1 == mWebsiteSecurityResponse.getStatus()) {
                            mSecurityTextView.setText(mWebsiteSecurityResponse.isSecurity() ? R.string.app_text_secure : R.string.app_text_insecure);
                            Drawable topDrawable = getResources().getDrawable(mWebsiteSecurityResponse.isSecurity() ? R.drawable.ic_secure : R.drawable.ic_insecure);
                            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                            mSecurityTextView.setCompoundDrawables(null, topDrawable, null, null);
                            mSecurityHintTextView.setText(mWebsiteSecurityResponse.isSecurity() ? R.string.app_text_secure_message : R.string.app_text_insecure_message);
                            mSecureLayout.setBackgroundResource(mWebsiteSecurityResponse.isSecurity() ? R.color.app_color_secure : R.color.app_color_insecure);
                            setToolbarColor(mWebsiteSecurityResponse.isSecurity() ? R.color.app_color_secure : R.color.app_color_insecure);
                            mSafeOpeningBtn.setVisibility(mWebsiteSecurityResponse.isSecurity() ? View.VISIBLE : View.GONE);
                            IcpLicensing icpLicensing = mWebsiteSecurityResponse.getIcpLicensing();
                            if (null != icpLicensing) {
                                setTextViewText(mIcpTextView, icpLicensing.getOrganizerName());
                                setTextViewText(mHomeUrlTextView, icpLicensing.getWebsiteHomeUrl());
                            }
                            hideLoading();
                        } else if (0 == mWebsiteSecurityResponse.getStatus()) {
                            showErrorMsg(mWebsiteSecurityResponse.getMsg());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icp_layout:
                if (null != mWebsiteSecurityResponse) {
                    IcpLicensing icpLicensing = mWebsiteSecurityResponse.getIcpLicensing();
                    if (null != icpLicensing) {
                        Intent intent = new Intent(QRCodeDetailsActivity.this, IcpLicensingDetailsActivity.class);
                        intent.putExtra("organizerName", icpLicensing.getOrganizerName());
                        intent.putExtra("organizerType", icpLicensing.getOrganizerType());
                        intent.putExtra("licenseNumber", icpLicensing.getLicenseNumber());
                        intent.putExtra("examineTime", icpLicensing.getExamineTime());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.homeUrl_layout:
                if (null != mWebsiteSecurityResponse) {
                    IcpLicensing icpLicensing = mWebsiteSecurityResponse.getIcpLicensing();
                    if (null != icpLicensing) {
                        Intent intent = new Intent(QRCodeDetailsActivity.this, HomeUrlDetailsActivity.class);
                        intent.putExtra("websiteName", icpLicensing.getWebsiteName());
                        intent.putExtra("websiteHomeUrl", icpLicensing.getWebsiteHomeUrl());
                        intent.putExtra("examineTime", icpLicensing.getExamineTime());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.safeOpeningBtn:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(mUrl);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.error:
                showLoading();
                initSecurity();
                break;
            case R.id.noCommentTextView:
                initComment();
                break;
            case R.id.moreCommentBtn:
                Intent intent2 = new Intent(QRCodeDetailsActivity.this, CommentActivity.class);
                intent2.putExtra("url", mUrl);
                startActivity(intent2);
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
