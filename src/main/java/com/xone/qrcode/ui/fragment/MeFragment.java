package com.xone.qrcode.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.User;
import com.xone.qrcode.ui.activity.MainActivity;
import com.xone.qrcode.ui.activity.MyAppealActivity;
import com.xone.qrcode.ui.activity.MyReportActivity;

/**
 * Created by zhengxuze on 2017/4/10.
 */

public class MeFragment extends Fragment implements View.OnClickListener {
    private View mContentView;
    private ImageView mHeadImageView;
    private TextView mUserNameTv;
    private View mMyReportBtn;
    private View mMyAppealBtn;
    private View loginOutBtn;
    private User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_me, null);
            mContentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            initView();
            initClick();
            initUserData();
        }

        return mContentView;
    }

    private void initUserData() {
        if (null == (mUser = User.getCurrentUser(User.class))) {
            return;
        }
        if (null != mUser.getHeadImg() && null != mUser.getHeadImg().getFileUrl()) {
            Glide.with(getActivity()).load(mUser.getHeadImg().getFileUrl()).into(mHeadImageView);
        } else {
            Glide.with(getActivity()).load("http://bmob-cdn-8381.b0.upaiyun.com/2016/12/25/9a47a0f740282288803b84ff269b8242.png").into(mHeadImageView);
        }
        if (!TextUtils.isEmpty(mUser.getUsername())) {
            mUserNameTv.setText(mUser.getUsername());
        }
    }

    private void initClick() {
        loginOutBtn.setOnClickListener(this);
        mMyReportBtn.setOnClickListener(this);
        mMyAppealBtn.setOnClickListener(this);
    }

    private void initView() {
        mHeadImageView = (ImageView) mContentView.findViewById(R.id.my_head_iv);
        mUserNameTv = (TextView) mContentView.findViewById(R.id.my_name_tv);
        loginOutBtn = mContentView.findViewById(R.id.loginOutBtn);
        mMyReportBtn = mContentView.findViewById(R.id.my_report_textView);
        mMyAppealBtn = mContentView.findViewById(R.id.my_appeal_textView);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initUserData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_report_textView:
                startActivity(new Intent(getActivity(), MyReportActivity.class));
                break;
            case R.id.my_appeal_textView:
                startActivity(new Intent(getActivity(), MyAppealActivity.class));
                break;
            case R.id.loginOutBtn:
                User.logOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            default:
                break;
        }
    }
}