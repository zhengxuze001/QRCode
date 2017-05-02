package com.xone.qrcode.presenter.impl;

import android.widget.Toast;

import com.xone.qrcode.listener.IAppealListener;
import com.xone.qrcode.model.entities.Appeal;
import com.xone.qrcode.model.impl.AppealModel;
import com.xone.qrcode.model.interfaces.IAppealModel;
import com.xone.qrcode.presenter.interfaces.IAppealPresenter;
import com.xone.qrcode.ui.interfaces.IAppealView;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class AppealPresenter implements IAppealPresenter {
    private IAppealView mView;
    private IAppealModel mModel;
    private int skip = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    public AppealPresenter(IAppealView view) {
        mView = view;
        mModel = new AppealModel();
    }

    @Override
    public void doRefresh() {
        if (isRefresh || isLoadMore) {
            return;
        }
        isRefresh = true;

        skip = 0;

        mModel.getRefreshData(new IAppealListener() {

            @Override
            public void onSuccess(List<Appeal> data) {
                isRefresh = false;
                if (0 == data.size()) {
                    mView.showErrorMsg("暂时没有数据");
                    return;
                }

                mView.setData(data);
                mView.hideLoading();
                skip = data.size();
            }

            @Override
            public void onError(String msg) {
                mView.showErrorMsg(msg);
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

        mModel.getLoadMoreData(new IAppealListener() {

            @Override
            public void onSuccess(List<Appeal> data) {
                isLoadMore = false;
                mView.hideLoading();
                if (0 == data.size()) {
                    mView.showToast("没有更多数据了", Toast.LENGTH_SHORT);
                    return;
                }

                mView.addData(data);
                skip += data.size();
            }

            @Override
            public void onError(String msg) {
                isLoadMore = false;
                mView.showErrorMsg(msg);
            }
        }, skip);
    }

    @Override
    public int getSkip() {
        return skip;
    }
}
