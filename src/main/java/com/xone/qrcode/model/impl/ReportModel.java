package com.xone.qrcode.model.impl;

import com.xone.qrcode.listener.IReportListener;
import com.xone.qrcode.model.entities.Report;
import com.xone.qrcode.model.entities.User;
import com.xone.qrcode.model.interfaces.IReportModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class ReportModel implements IReportModel {

    @Override
    public void getRefreshData(IReportListener listener) {
        getData(listener, 0);
    }

    @Override
    public void getLoadMoreData(IReportListener listener, int skip) {
        getData(listener, skip);
    }

    private void getData(final IReportListener listener, int skip) {
        BmobQuery<Report> query = new BmobQuery<>();
        query.setLimit(10);
        query.addWhereEqualTo("publisher", User.getCurrentUser(User.class));
        query.order("-createdAt");
        query.setSkip(skip);
        query.findObjects(new FindListener<Report>() {
            @Override
            public void done(List<Report> list, BmobException e) {
                if (null == e) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(e.getMessage());
                }
            }
        });
    }
}
