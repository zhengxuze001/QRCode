package com.xone.qrcode.presenter.impl;

import android.widget.Toast;

import com.xone.qrcode.listener.IScanHistoryListener;
import com.xone.qrcode.model.entities.ScanHistory;
import com.xone.qrcode.model.impl.ScanHistoryModel;
import com.xone.qrcode.model.interfaces.IScanHistoryModel;
import com.xone.qrcode.presenter.interfaces.IScanHistoryPresenter;
import com.xone.qrcode.ui.interfaces.IScanHistoryView;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class ScanHistoryPresenter implements IScanHistoryPresenter {
    private IScanHistoryView mScanHistoryView;
    private IScanHistoryModel mScanHistoryModel;
    private int skip = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    public ScanHistoryPresenter(IScanHistoryView scanHistoryView) {
        mScanHistoryView = scanHistoryView;
        mScanHistoryModel = new ScanHistoryModel();
    }

    @Override
    public void doRefresh() {
        if (isRefresh || isLoadMore) {
            return;
        }
        isRefresh = true;

        skip = 0;

        mScanHistoryModel.getRefreshData(new IScanHistoryListener() {

            @Override
            public void onSuccess(List<ScanHistory> data) {
                isRefresh = false;
                if (0 == data.size()) {
                    mScanHistoryView.showErrorMsg("暂时没有数据");
                    return;
                }

                mScanHistoryView.setData(data);
                mScanHistoryView.hideLoading();
                skip = data.size();
            }

            @Override
            public void onError(String msg) {
                mScanHistoryView.showErrorMsg(msg);
                isRefresh = false;
            }
        });
    }

    @Override
    public void doLoadMore() {
        if (isLoadMore || isRefresh) {
            return;
        }
        isLoadMore = true;

        mScanHistoryModel.getLoadMoreData(new IScanHistoryListener() {

            @Override
            public void onSuccess(List<ScanHistory> data) {
                isLoadMore = false;
                mScanHistoryView.hideLoading();
                if (0 == data.size()) {
                    mScanHistoryView.showToast("没有更多数据了", Toast.LENGTH_SHORT);
                    return;
                }

                mScanHistoryView.addData(data);
                skip += data.size();
            }

            @Override
            public void onError(String msg) {
                isLoadMore = false;
                mScanHistoryView.showErrorMsg(msg);
            }
        }, skip);
    }

    @Override
    public int getSkip() {
        return skip;
    }
}
