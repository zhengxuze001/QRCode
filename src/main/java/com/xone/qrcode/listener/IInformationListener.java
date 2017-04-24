package com.xone.qrcode.listener;

import com.xone.qrcode.model.entities.Information;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IInformationListener {
    void onSuccess(List<Information> data);

    void onError(String msg);
}
