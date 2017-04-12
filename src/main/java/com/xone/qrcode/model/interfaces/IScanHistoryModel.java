package com.xone.qrcode.model.interfaces;

import com.xone.qrcode.listener.IScanHistoryListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IScanHistoryModel {
    void getRefreshData(IScanHistoryListener listener);

    void getLoadMoreData(IScanHistoryListener listener, int skip);
}
