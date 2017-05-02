package com.xone.qrcode.model.interfaces;

import com.xone.qrcode.listener.ICommentListener;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface ICommentModel {
    void getRefreshData(ICommentListener listener);

    void getLoadMoreData(ICommentListener listener, int skip);
}
