package com.xone.qrcode.presenter.impl;

import android.widget.Toast;

import com.xone.qrcode.listener.IInformationListener;
import com.xone.qrcode.model.entities.Information;
import com.xone.qrcode.model.impl.InformationModel;
import com.xone.qrcode.model.interfaces.IInformationModel;
import com.xone.qrcode.presenter.interfaces.IInformationPresenter;
import com.xone.qrcode.ui.interfaces.IInformationView;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class InformationPresenter implements IInformationPresenter {
    private IInformationView mView;
    private IInformationModel mModel;
    private int skip = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    public InformationPresenter(IInformationView view, int type) {
        mView = view;
        mModel = new InformationModel(type);
    }

    @Override
    public void doRefresh() {
        if (isRefresh || isLoadMore) {
            return;
        }
        isRefresh = true;

        skip = 0;

        mModel.getRefreshData(new IInformationListener() {

            @Override
            public void onSuccess(List<Information> data) {
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

        mModel.getLoadMoreData(new IInformationListener() {

            @Override
            public void onSuccess(List<Information> data) {
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
