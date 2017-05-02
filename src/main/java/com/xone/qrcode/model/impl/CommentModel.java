package com.xone.qrcode.model.impl;

import com.xone.qrcode.listener.ICommentListener;
import com.xone.qrcode.listener.IReportListener;
import com.xone.qrcode.model.entities.Report;
import com.xone.qrcode.model.entities.User;
import com.xone.qrcode.model.interfaces.ICommentModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class CommentModel implements ICommentModel {
    private String mUrl;

    public CommentModel(String url) {
        mUrl = url;
        try {
            mUrl = new URL(mUrl).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRefreshData(ICommentListener listener) {
        getData(listener, 0);
    }

    @Override
    public void getLoadMoreData(ICommentListener listener, int skip) {
        getData(listener, skip);
    }

    private void getData(final ICommentListener listener, int skip) {
        BmobQuery<Report> query = new BmobQuery<>();
        query.setLimit(10);
        query.addWhereEqualTo("websiteUrl", mUrl);
        query.include("publisher");
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
