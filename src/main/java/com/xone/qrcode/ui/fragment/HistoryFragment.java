package com.xone.qrcode.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xone.qrcode.R;
import com.xone.qrcode.adapter.ScanHistoryAdapter;
import com.xone.qrcode.model.entities.ScanHistory;
import com.xone.qrcode.presenter.impl.ScanHistoryPresenter;
import com.xone.qrcode.presenter.interfaces.IScanHistoryPresenter;
import com.xone.qrcode.ui.activity.OtherQRCodeDetailsActivity;
import com.xone.qrcode.ui.activity.QRCodeDetailsActivity;
import com.xone.qrcode.ui.interfaces.IScanHistoryView;
import com.xone.qrcode.widget.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxuze on 2017/4/10.
 */

public class HistoryFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener,
        XListView.IXListViewListener, IScanHistoryView {
    private View mContentView;
    private ProgressBar mProgressBar;
    private TextView mErrorView;
    private XListView mListView;
    private List<ScanHistory> mData;
    private ScanHistoryAdapter mAdapter;
    private IScanHistoryPresenter mScanHistoryPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.fragment_history, null);
            mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            mScanHistoryPresenter = new ScanHistoryPresenter(this);
            initView();
            initClick();
            showLoading();
            mScanHistoryPresenter.doRefresh();
        }
        return mContentView;
    }

    private void initView() {
        mListView = (XListView) mContentView.findViewById(R.id.listView);
        mData = new ArrayList<>();
        mAdapter = new ScanHistoryAdapter(getActivity(), mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        mProgressBar = (ProgressBar) mContentView.findViewById(R.id.progressbar);
        mErrorView = (TextView) mContentView.findViewById(R.id.error);
    }

    private void initClick() {
        mErrorView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error:
                showLoading();
                if (0 == mScanHistoryPresenter.getSkip()) {
                    mScanHistoryPresenter.doRefresh();
                } else {
                    mScanHistoryPresenter.doLoadMore();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ScanHistory scanHistory = mData.get(position - 1);
        if ("URI".equals(scanHistory.getQRCodeType())) {
            Intent intent = new Intent(getActivity(), QRCodeDetailsActivity.class);
            intent.putExtra("url", scanHistory.getQRCodeContent());
            startActivity(intent);
        } else {
            Intent intent2 = new Intent(getActivity(), OtherQRCodeDetailsActivity.class);
            intent2.putExtra("content", scanHistory.getQRCodeContent());
            startActivity(intent2);
        }
    }

    @Override
    public void onRefresh() {
        mScanHistoryPresenter.doRefresh();
    }

    @Override
    public void onLoadMore() {
        mScanHistoryPresenter.doLoadMore();
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
        Toast.makeText(getActivity(), msg, duration).show();
    }

    @Override
    public void setData(List<ScanHistory> newData) {
        mData.removeAll(mData);
        mData.addAll(newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List<ScanHistory> data) {
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
