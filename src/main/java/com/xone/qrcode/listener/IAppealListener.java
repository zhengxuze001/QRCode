package com.xone.qrcode.listener;

import com.xone.qrcode.model.entities.Appeal;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IAppealListener {
    void onSuccess(List<Appeal> data);

    void onError(String msg);
}
