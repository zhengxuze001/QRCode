package com.xone.qrcode.model.impl;

import com.xone.qrcode.listener.IAppealListener;
import com.xone.qrcode.model.entities.Appeal;
import com.xone.qrcode.model.entities.User;
import com.xone.qrcode.model.interfaces.IAppealModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class AppealModel implements IAppealModel {

    @Override
    public void getRefreshData(IAppealListener listener) {
        getData(listener, 0);
    }

    @Override
    public void getLoadMoreData(IAppealListener listener, int skip) {
        getData(listener, skip);
    }

    private void getData(final IAppealListener listener, int skip) {
        BmobQuery<Appeal> query = new BmobQuery<>();
        query.setLimit(10);
        query.addWhereEqualTo("publisher", User.getCurrentUser(User.class));
        query.order("-createdAt");
        query.setSkip(skip);
        query.findObjects(new FindListener<Appeal>() {
            @Override
            public void done(List<Appeal> list, BmobException e) {
                if (null == e) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(e.getMessage());
                }
            }
        });
    }
}
