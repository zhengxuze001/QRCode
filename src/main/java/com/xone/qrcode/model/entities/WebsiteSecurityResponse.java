package com.xone.qrcode.model.entities;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class WebsiteSecurityResponse {
    private int status;
    private String msg;
    private boolean security;
    private IcpLicensing icpLicensing;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }

    public IcpLicensing getIcpLicensing() {
        return icpLicensing;
    }

    public void setIcpLicensing(IcpLicensing icpLicensing) {
        this.icpLicensing = icpLicensing;
    }
}
