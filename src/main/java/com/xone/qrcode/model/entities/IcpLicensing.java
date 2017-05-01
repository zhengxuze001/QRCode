package com.xone.qrcode.model.entities;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class IcpLicensing {
    private String organizerName;
    private String organizerType;
    private String licenseNumber;
    private String websiteName;
    private String websiteHomeUrl;
    private String examineTime;

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerType() {
        return organizerType;
    }

    public void setOrganizerType(String organizerType) {
        this.organizerType = organizerType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getWebsiteHomeUrl() {
        return websiteHomeUrl;
    }

    public void setWebsiteHomeUrl(String websiteHomeUrl) {
        this.websiteHomeUrl = websiteHomeUrl;
    }

    public String getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(String examineTime) {
        this.examineTime = examineTime;
    }
}
