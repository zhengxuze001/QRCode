package com.xone.qrcode.model.interfaces;

import com.xone.qrcode.listener.IAppealListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IAppealModel {
    void getRefreshData(IAppealListener listener);

    void getLoadMoreData(IAppealListener listener, int skip);
}
