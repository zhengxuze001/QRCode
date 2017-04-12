package com.xone.qrcode.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class FileUtil {
    private static final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    /**
     * 获取图片缓存文件夹路径
     */
    public static String getPicCacheDirPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            String cacheDirPath = Environment.getExternalStorageDirectory().getPath() +
                    File.separator + "QRCode" + File.separator + ".images";
            File dir = new File(cacheDirPath);
            if (dir.exists() || dir.mkdirs()) {
                return dir.getPath();
            }
        }
        return null;
    }

    /**
     * 根据时间生成图片的文件路径
     */
    private static String getPicFilePath() {
        return getPicCacheDirPath() + File.separator + mSimpleDateFormat.format(new Date()) + ".png";
    }

    /**
     * 保存Bitmap到本地存储
     */
    public static String saveBitmap(Bitmap bitmap) {
        String picPath;
        try {
            picPath = getPicFilePath();
            File file = new File(picPath);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            grantPermissionForOthers(file, false, false, false);
        } catch (IOException e) {
            picPath = null;
        }
        return picPath;
    }

    /**
     * 设置文件的权限
     */
    private static void grantPermissionForOthers(File file, boolean ownerOnlyRead, boolean ownerOnlyWrite, boolean ownerOnlyExecute) {
        if (file.exists()) {
            file.setReadable(true, ownerOnlyRead);
            file.setWritable(true, ownerOnlyWrite);
            file.setExecutable(true, ownerOnlyExecute);
        }
    }
}
