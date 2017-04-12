package com.xone.qrcode.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.URIParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.result.AddressBookResult;
import com.xone.qrcode.R;
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

        Intent intent = new Intent(getActivity(), QRCodeDetailsActivity.class);
        intent.putExtra("type", type.toString());
        intent.putExtra("content", rawResult.getText());
        intent.putExtra("imgPath", picPath);
        startActivity(intent);

        Log.i("test", rawResult.getText());
        switch (type) {
            case URI:
//                URIParsedResult uriParsedResult = (URIParsedResult) parsedResult;
//                bundle.putString(Intents.Scan.RESULT, uriParsedResult.getURI());
                break;
            case TEXT:
//                bundle.putString(Intents.Scan.RESULT, rawResult.getText());
                break;
            case ADDRESSBOOK:

                break;
            case EMAIL_ADDRESS:

                break;
            case PRODUCT:

                break;
            case GEO:

                break;
            case TEL:

                break;
            case SMS:

                break;
            case CALENDAR:

                break;
            case WIFI:

                break;
            case ISBN:

                break;
            case VIN:

                break;
            default:
                break;
        }
    }
}
