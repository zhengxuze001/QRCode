package com.xone.qrcode.ui.interfaces;

import com.xone.qrcode.model.entities.Appeal;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/19.
 */

public interface IAppealView extends IBaseView {
    void setData(List<Appeal> newData);

    void addData(List<Appeal> data);
}
