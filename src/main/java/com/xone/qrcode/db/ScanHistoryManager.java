package com.xone.qrcode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xone.qrcode.model.entities.ScanHistory;

import java.util.ArrayList;
import java.util.List;

public class ScanHistoryManager {

    private ScanHistoryDBHelper dbHelper;

    public ScanHistoryManager(Context context) {
        dbHelper = new ScanHistoryDBHelper(context);
    }

    public int save(String table, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Long id = db.insert(table, null, values);
        db.close();
        return id.intValue();
    }

    public int delete(int id) {
        int effectNum = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sqlDelete = "delete from ScanHistory where objectId=" + id;
        db.execSQL(sqlDelete);
        return effectNum;
    }

    public List<ScanHistory> getScanHistory(String skip) {
        List<ScanHistory> scanHistoryList = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("ScanHistory", null, null, null, null, null, "QRCodeTime desc", skip + ", 10");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ScanHistory scanHistory = new ScanHistory();
                scanHistory.setQRCodeImgPath(cursor.getString(cursor.getColumnIndex("QRCodeImgPath")));
                scanHistory.setQRCodeContent(cursor.getString(cursor.getColumnIndex("QRCodeContent")));
                scanHistory.setQRCodeType(cursor.getString(cursor.getColumnIndex("QRCodeType")));
                scanHistory.setQRCodeTime(cursor.getLong(cursor.getColumnIndex("QRCodeTime")));
                scanHistoryList.add(scanHistory);
            }
        }

        db.close();
        cursor.close();
        return scanHistoryList;
    }
}
