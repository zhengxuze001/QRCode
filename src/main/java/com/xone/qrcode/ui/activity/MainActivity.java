package com.xone.qrcode.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.xone.qrcode.R;
import com.xone.qrcode.ui.fragment.DiscoverFragment;
import com.xone.qrcode.ui.fragment.HistoryFragment;
import com.xone.qrcode.ui.fragment.MeFragment;
import com.xone.qrcode.ui.fragment.ScanFragment;

public class MainActivity extends BaseFragmentActivity {
    private View[] mTabs;
    private Fragment[] mFragments;
    private ScanFragment mScanFragment;
    private HistoryFragment mHistoryFragment;
    private DiscoverFragment mDiscoverFragment;
    private MeFragment mMeFragment;
    private int mIndex;
    private int mCurrentTabIndex;
    private boolean is2CallBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTab();
    }

    private void initView() {
        mTabs = new View[4];
        mTabs[0] = findViewById(R.id.btn_scan);
        mTabs[1] = findViewById(R.id.btn_history);
        mTabs[2] = findViewById(R.id.btn_discover);
        mTabs[3] = findViewById(R.id.btn_me);
        mTabs[0].setSelected(true);
    }

    private void initTab() {
        mScanFragment = new ScanFragment();
        mHistoryFragment = new HistoryFragment();
        mDiscoverFragment = new DiscoverFragment();
        mMeFragment = new MeFragment();
        mFragments = new Fragment[]{mScanFragment, mHistoryFragment, mDiscoverFragment, mMeFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mScanFragment)
                .show(mScanFragment).commit();
        mCurrentTabIndex = 0;
    }

    public void onTabSelect(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                mIndex = 0;
                break;
            case R.id.btn_history:
                mIndex = 1;
                break;
            case R.id.btn_discover:
                mIndex = 2;
                break;
            case R.id.btn_me:
//                if (QuNaYueHuiApp.getInstance().getUser() == null) {
//                    startActivity(new Intent(context, LoginActivity.class));
//                    return;
//                }
                mIndex = 3;
                break;
        }

        setTabSelection(mIndex);
    }

    private void setTabSelection(int index) {
        if (mCurrentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[mCurrentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.fragment_container, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
        }
        mTabs[mCurrentTabIndex].setSelected(false);
        //把当前tab设为选中状态
        mTabs[index].setSelected(true);
        mCurrentTabIndex = index;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int position = savedInstanceState.getInt("position");
        setTabSelection(position);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", mCurrentTabIndex);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!is2CallBack) {
                is2CallBack = true;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        is2CallBack = false;
                    }
                }, 1500);
            } else {
                finish();
            }
        }
        return true;
    }
}
