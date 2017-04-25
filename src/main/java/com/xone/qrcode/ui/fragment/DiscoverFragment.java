package com.xone.qrcode.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.xone.qrcode.R;
import com.xone.qrcode.util.DisplayUtil;

/**
 * Created by zhengxuze on 2017/4/10.
 */

public class DiscoverFragment extends Fragment {
    private View mContentView;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private static String mTypeNames[] = new String[]{"推荐", "资讯", "漏洞", "恶意软件"};
    private static int mTypes[] = new int[]{0, 1, 2, 4};
    private Fragment[] fragments;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.fragment_discover, null);
            mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initView();
            initViewPager();
        }
        return mContentView;
    }

    private void initView() {
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) mContentView.findViewById(R.id.pagerSlidingTabStrip);
        mViewPager = (ViewPager) mContentView.findViewById(R.id.viewPager);

        int length = mTypeNames.length;
        fragments = new InformationFragment[length];
        for (int i = 0; i < length; i++) {
            fragments[i] = InformationFragment.newInstance(mTypes[i]);
        }
    }

    private void initViewPager() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
        Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyFont.ttf");
        mPagerSlidingTabStrip.setTypeface(Typeface.create(myFont, Typeface.NORMAL), Typeface.NORMAL);
        int textSize = DisplayUtil.sp2px(getActivity(), 14);
        mPagerSlidingTabStrip.setTextSize(textSize);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int length = fragments.length;
            if (position > length) {
                return null;
            }
            if (fragments[position] == null) {
                return null;
            }

            return fragments[position];
        }

        @Override
        public int getCount() {
            return mTypeNames.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTypeNames[position];
        }
    }
}
