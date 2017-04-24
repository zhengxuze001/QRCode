package com.xone.qrcode.model.entities;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by zhengxuze on 2017/4/19.
 */

public class Information {
    private Integer type;
    private String title;
    private String publisher;
    private String content;
    private Boolean isHeadlines;
    private BmobFile image;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getHeadlines() {
        return isHeadlines;
    }

    public void setHeadlines(Boolean headlines) {
        isHeadlines = headlines;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
