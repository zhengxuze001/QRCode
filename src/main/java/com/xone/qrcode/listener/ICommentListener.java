package com.xone.qrcode.listener;

import com.xone.qrcode.model.entities.Report;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface ICommentListener {
    void onSuccess(List<Report> data);

    void onError(String msg);
}
