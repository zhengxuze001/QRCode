package com.xone.qrcode.model.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class Report extends BmobObject {
    private User publisher;
    private String websiteUrl;
    private String reportReason;
    private String reportDetails;

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
    }
}
