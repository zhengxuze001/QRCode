package com.xone.qrcode.model.entities;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by zhengxuze on 2017/5/2.
 */

public class User extends BmobUser {
    private BmobFile headImg;

    public BmobFile getHeadImg() {
        return headImg;
    }

    public void setHeadImg(BmobFile headImg) {
        this.headImg = headImg;
    }
}
