package com.xone.qrcode.model.impl;

import com.xone.qrcode.listener.IInformationListener;
import com.xone.qrcode.model.entities.Information;
import com.xone.qrcode.model.interfaces.IInformationModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class InformationModel implements IInformationModel {
    private Integer type;

    public InformationModel(int type) {
        this.type = type;
    }

    @Override
    public void getRefreshData(IInformationListener listener) {
        getData(listener, 0);
    }

    @Override
    public void getLoadMoreData(IInformationListener listener, int skip) {
        getData(listener, skip);
    }

    private void getData(final IInformationListener listener, int skip) {
        BmobQuery<Information> query = new BmobQuery<>();
        query.setLimit(10);
        query.addWhereEqualTo("type", type);
        query.order("-createdAt");
        query.setSkip(skip);
        query.findObjects(new FindListener<Information>() {
            @Override
            public void done(List<Information> list, BmobException e) {
                if (null == e) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(e.getMessage());
                }
            }
        });
    }
}
