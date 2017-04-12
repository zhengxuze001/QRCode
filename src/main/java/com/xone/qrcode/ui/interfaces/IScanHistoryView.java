package com.xone.qrcode.ui.interfaces;

import com.xone.qrcode.model.entities.ScanHistory;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IScanHistoryView extends IBaseView {
    void setData(List<ScanHistory> newData);

    void addData(List<ScanHistory> data);
}
