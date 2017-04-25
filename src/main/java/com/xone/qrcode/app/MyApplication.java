package com.xone.qrcode.app;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
           佛祖镇楼      永无BUG
*/

import android.app.Application;

import com.xone.qrcode.R;

import cn.bmob.v3.Bmob;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by zhengxuze on 2017/4/12.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "24a3a138ff76887a74b08f5d68a70d43");
        mInstance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MyFont.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
