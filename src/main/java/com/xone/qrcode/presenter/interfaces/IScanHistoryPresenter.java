package com.xone.qrcode.presenter.interfaces;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IScanHistoryPresenter {
    void doRefresh();

    void doLoadMore();

    int getSkip();
}
