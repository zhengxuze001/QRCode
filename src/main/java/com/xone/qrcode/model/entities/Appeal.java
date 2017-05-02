package com.xone.qrcode.model.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class Appeal extends BmobObject {
    private User publisher;
    private String websiteUrl;

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
}
