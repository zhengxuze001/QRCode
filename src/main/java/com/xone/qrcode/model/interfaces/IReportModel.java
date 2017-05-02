package com.xone.qrcode.model.interfaces;

import com.xone.qrcode.listener.IReportListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IReportModel {
    void getRefreshData(IReportListener listener);

    void getLoadMoreData(IReportListener listener, int skip);
}
