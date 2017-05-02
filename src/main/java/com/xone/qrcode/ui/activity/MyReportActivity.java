package com.xone.qrcode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xone.qrcode.R;
import com.xone.qrcode.adapter.ReportAdapter;
import com.xone.qrcode.model.entities.Report;
import com.xone.qrcode.presenter.impl.ReportPresenter;
import com.xone.qrcode.presenter.interfaces.IReportPresenter;
import com.xone.qrcode.ui.interfaces.IReportView;
import com.xone.qrcode.widget.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class MyReportActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener,
        XListView.IXListViewListener, IReportView {
    private ProgressBar mProgressBar;
    private TextView mErrorView;
    private XListView mListView;
    private List<Report> mData;
    private ReportAdapter mAdapter;
    private IReportPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);
        setActivityName("我的举报");

        mPresenter = new ReportPresenter(this);
        initView();
        initClick();
        showLoading();
        mPresenter.doRefresh();
    }

    private void initView() {
        mListView = (XListView) findViewById(R.id.listView);
        mData = new ArrayList<>();
        mAdapter = new ReportAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mErrorView = (TextView) findViewById(R.id.error);
    }

    private void initClick() {
        mErrorView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error:
                showLoading();
                if (0 == mPresenter.getSkip()) {
                    mPresenter.doRefresh();
                } else {
                    mPresenter.doLoadMore();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MyReportActivity.this, ReportDetailsActivity.class);
        Report report = mData.get(position - 1);
        intent.putExtra("websiteUrl", report.getWebsiteUrl());
        intent.putExtra("reportReason", report.getReportReason());
        intent.putExtra("reportDetails", report.getReportDetails());
        intent.putExtra("reportTime", report.getCreatedAt());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.doRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.doLoadMore();
    }

    @Override
    public void showLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        onLoad();
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
    }

    @Override
    public void showErrorMsg(String msg) {
        mErrorView.setVisibility(View.VISIBLE);
        mErrorView.setText(msg);
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String msg, int duration) {
        Toast.makeText(MyReportActivity.this, msg, duration).show();
    }

    @Override
    public void setData(List<Report> newData) {
        mData.removeAll(mData);
        mData.addAll(newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List<Report> data) {
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
