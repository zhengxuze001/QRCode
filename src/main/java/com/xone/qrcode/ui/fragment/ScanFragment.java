package com.xone.qrcode.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xone.qrcode.R;

/**
 * Created by zhengxuze on 2017/4/10.
 */

public class ScanFragment extends Fragment {
    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.fragment_scan, null);
            mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        return mContentView;
    }
}
