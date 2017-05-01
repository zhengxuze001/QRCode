package com.xone.qrcode.ui.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.URIParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.result.AddressBookResult;
import com.xone.qrcode.R;
import com.xone.qrcode.app.MyApplication;
import com.xone.qrcode.db.ScanHistoryManager;
import com.xone.qrcode.ui.activity.OtherQRCodeDetailsActivity;
import com.xone.qrcode.ui.activity.QRCodeDetailsActivity;
import com.xone.qrcode.util.FileUtil;

/**
 * Created by zhengxuze on 2017/4/10.
 */

public class ScanFragment extends Fragment implements OnScannerCompletionListener {
    private View mContentView;
    private ScannerView mScannerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.fragment_scan, null);
            mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initViews();
        }
        return mContentView;
    }

    private void initViews() {
        mScannerView = (ScannerView) mContentView.findViewById(R.id.scanner_view);
//        mScannerView.setDrawText("将二维码放入框内，即可自动扫描", 16, Color.WHITE, true, 20);
        mScannerView.setOnScannerCompletionListener(this);
    }

    @Override
    public void onResume() {
        if (null != mScannerView) {
            mScannerView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (null != mScannerView) {
            mScannerView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mScannerView.onPause();
        } else {
            mScannerView.onResume();
        }
    }

    @Override
    public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        String picPath = FileUtil.saveBitmap(barcode);
        ParsedResultType type = parsedResult.getType();
        saveScanHistory(type.toString(), rawResult.getText(), picPath);

        switch (type) {
            case URI:
                URIParsedResult uriParsedResult = (URIParsedResult) parsedResult;
                Intent intent = new Intent(getActivity(), QRCodeDetailsActivity.class);
                intent.putExtra("url", uriParsedResult.getURI());
                startActivity(intent);
                break;
            default:
                Intent intent2 = new Intent(getActivity(), OtherQRCodeDetailsActivity.class);
                intent2.putExtra("content", parsedResult.getDisplayResult());
                startActivity(intent2);
                break;
        }
    }

    private void saveScanHistory(String type, String content, String imagePath) {
        ScanHistoryManager scanHistoryManager = new ScanHistoryManager(MyApplication.getInstance().getApplicationContext());
        ContentValues values = new ContentValues();
        values.put("QRCodeImgPath", imagePath);
        values.put("QRCodeContent", content);
        values.put("QRCodeType", type);
        values.put("QRCodeTime", System.currentTimeMillis());
        if (scanHistoryManager.save("ScanHistory", values) < 1) {
            Toast.makeText(getActivity(), "存储扫描历史失败", Toast.LENGTH_SHORT);
        }
    }
}
