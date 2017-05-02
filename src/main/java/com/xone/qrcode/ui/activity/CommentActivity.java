package com.xone.qrcode.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xone.qrcode.R;
import com.xone.qrcode.adapter.CommentAdapter;
import com.xone.qrcode.model.entities.Report;
import com.xone.qrcode.presenter.impl.CommentPresenter;
import com.xone.qrcode.presenter.interfaces.ICommentPresenter;
import com.xone.qrcode.ui.interfaces.ICommentView;
import com.xone.qrcode.widget.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseActivity implements View.OnClickListener,
        XListView.IXListViewListener, ICommentView {
    private ProgressBar mProgressBar;
    private TextView mErrorView;
    private XListView mListView;
    private List<Report> mData;
    private CommentAdapter mAdapter;
    private ICommentPresenter mPresenter;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setActivityName("所有评论");

        mUrl = getIntent().getStringExtra("url");

        mPresenter = new CommentPresenter(this, mUrl);
        initView();
        initClick();
        showLoading();
        mPresenter.doRefresh();
    }

    private void initView() {
        mListView = (XListView) findViewById(R.id.listView);
        mData = new ArrayList<>();
        mAdapter = new CommentAdapter(this, mData);
        mListView.setAdapter(mAdapter);
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
        Toast.makeText(CommentActivity.this, msg, duration).show();
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
