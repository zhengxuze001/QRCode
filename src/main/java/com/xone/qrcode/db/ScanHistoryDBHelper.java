package com.xone.qrcode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScanHistoryDBHelper extends SQLiteOpenHelper {

    private static final int version = 1;
    private static final String name = "ScanHistory.db";

    public ScanHistoryDBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTitle = "create table ScanHistory(objectId integer primary key autoincrement,QRCodeImgPath text,"
                + "QRCodeType text,QRCodeContent text,QRCodeTime integer)";
        db.execSQL(sqlTitle);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists ScanHistory");
        onCreate(db);
    }

}
