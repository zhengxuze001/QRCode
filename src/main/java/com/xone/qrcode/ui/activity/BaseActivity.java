package com.xone.qrcode.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xone.qrcode.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by zhengxuze on 2017/4/10.
 */

public class BaseActivity extends AppCompatActivity {
    private View mBackBtn;
    private TextView mActivityNameTv;
    private TextView mRightTextView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mBackBtn) {
            mBackBtn = findViewById(R.id.back_btn);
            if (null != mBackBtn) {
                mBackBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }

    public void setActivityName(String title) {
        if (null == mActivityNameTv) {
            mActivityNameTv = (TextView) findViewById(R.id.title_tv);
        }
        mActivityNameTv.setText(title);
    }

    public void setRightTextView(String text, View.OnClickListener onClickListener) {
        if (null == mRightTextView) {
            mRightTextView = (TextView) findViewById(R.id.right_tv);
        }
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText(text);
        mRightTextView.setOnClickListener(onClickListener);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
