package com.xone.qrcode.listener;

import com.xone.qrcode.model.entities.ScanHistory;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IScanHistoryListener {
    void onSuccess(List<ScanHistory> data);

    void onError(String msg);
}
