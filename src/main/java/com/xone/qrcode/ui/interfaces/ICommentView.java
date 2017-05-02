package com.xone.qrcode.ui.interfaces;

import com.xone.qrcode.model.entities.Report;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/19.
 */

public interface ICommentView extends IBaseView {
    void setData(List<Report> newData);

    void addData(List<Report> data);
}
