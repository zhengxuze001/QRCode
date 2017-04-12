package com.xone.qrcode.ui.interfaces;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showErrorMsg(String msg);

    void showToast(String msg, int duration);
}
