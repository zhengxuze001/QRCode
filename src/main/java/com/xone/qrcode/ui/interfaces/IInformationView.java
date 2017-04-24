package com.xone.qrcode.ui.interfaces;

import com.xone.qrcode.model.entities.Information;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/19.
 */

public interface IInformationView extends IBaseView {
    void setData(List<Information> newData);

    void addData(List<Information> data);
}
