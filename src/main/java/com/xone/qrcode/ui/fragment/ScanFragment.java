package com.xone.qrcode.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.xone.qrcode.R;

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
            mScannerView.onResume();
        }
        super.onPause();
    }

    @Override
    public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {

    }
}
