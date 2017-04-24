package com.xone.qrcode.model.interfaces;

import com.xone.qrcode.listener.IInformationListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IInformationModel {
    void getRefreshData(IInformationListener listener);

    void getLoadMoreData(IInformationListener listener, int skip);
}
