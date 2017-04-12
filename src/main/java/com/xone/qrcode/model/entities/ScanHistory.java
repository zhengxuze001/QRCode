package com.xone.qrcode.model.entities;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class ScanHistory {
    private String QRCodeImgPath;
    private String QRCodeType;
    private String QRCodeContent;
    private long QRCodeTime;

    public String getQRCodeImgPath() {
        return QRCodeImgPath;
    }

    public void setQRCodeImgPath(String QRCodeImgPath) {
        this.QRCodeImgPath = QRCodeImgPath;
    }

    public String getQRCodeType() {
        return QRCodeType;
    }

    public void setQRCodeType(String QRCodeType) {
        this.QRCodeType = QRCodeType;
    }

    public String getQRCodeContent() {
        return QRCodeContent;
    }

    public void setQRCodeContent(String QRCodeContent) {
        this.QRCodeContent = QRCodeContent;
    }

    public long getQRCodeTime() {
        return QRCodeTime;
    }

    public void setQRCodeTime(long QRCodeTime) {
        this.QRCodeTime = QRCodeTime;
    }
}
