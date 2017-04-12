package com.xone.qrcode.model.impl;

import com.xone.qrcode.app.MyApplication;
import com.xone.qrcode.db.ScanHistoryManager;
import com.xone.qrcode.listener.IScanHistoryListener;
import com.xone.qrcode.model.entities.ScanHistory;
import com.xone.qrcode.model.interfaces.IScanHistoryModel;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class ScanHistoryModel implements IScanHistoryModel {
    @Override
    public void getRefreshData(IScanHistoryListener listener) {
        getData(listener, 0);
    }

    @Override
    public void getLoadMoreData(IScanHistoryListener listener, int skip) {
        getData(listener, skip);
    }

    private void getData(final IScanHistoryListener ordersListener, int skip) {
        ScanHistoryManager scanHistoryManager = new ScanHistoryManager(MyApplication.getInstance().getApplicationContext());
        List<ScanHistory> scanHistoryList = scanHistoryManager.getScanHistory(String.valueOf(skip));
        if (null == scanHistoryList) {
            ordersListener.onError("暂无数据");
        } else {
            ordersListener.onSuccess(scanHistoryList);
        }
    }
}
