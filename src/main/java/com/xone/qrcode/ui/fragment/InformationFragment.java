package com.xone.qrcode.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xone.qrcode.R;
import com.xone.qrcode.adapter.InformationAdapter;
import com.xone.qrcode.model.entities.Information;
import com.xone.qrcode.presenter.impl.InformationPresenter;
import com.xone.qrcode.presenter.interfaces.IInformationPresenter;
import com.xone.qrcode.ui.interfaces.IInformationView;
import com.xone.qrcode.widget.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxuze on 2017/4/19.
 */

public class InformationFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener,
        XListView.IXListViewListener, IInformationView {
    private View mContentView;
    private ProgressBar mProgressBar;
    private TextView mErrorView;
    private XListView mListView;
    private List<Information> mData;
    private InformationAdapter mAdapter;
    private IInformationPresenter mPresenter;
    private int type;

    public static InformationFragment newInstance(int type) {
        InformationFragment f = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.fragment_information, null);
            mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            initBundle();
            mPresenter = new InformationPresenter(this, type);
            initView();
            initClick();
            showLoading();
            mPresenter.doRefresh();
        }
        return mContentView;
    }

    private void initBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        type = bundle.getInt("type");
    }

    private void initView() {
        mListView = (XListView) mContentView.findViewById(R.id.listView);
        mData = new ArrayList<>();
        mAdapter = new InformationAdapter(getActivity(), mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        mProgressBar = (ProgressBar) mContentView.findViewById(R.id.progressbar);
        mErrorView = (TextView) mContentView.findViewById(R.id.error);
    }

    private void initClick() {
        mErrorView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error:
                showLoading();
                if (0 == mPresenter.getSkip()) {
                    mPresenter.doRefresh();
                } else {
                    mPresenter.doLoadMore();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {
        mPresenter.doRefresh();
    }

    @Override
    public void onLoadMore() {
        mPresenter.doLoadMore();
    }

    @Override
    public void showLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        onLoad();
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
    }

    @Override
    public void showErrorMsg(String msg) {
        mErrorView.setVisibility(View.VISIBLE);
        mErrorView.setText(msg);
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String msg, int duration) {
        Toast.makeText(getActivity(), msg, duration).show();
    }

    @Override
    public void setData(List<Information> newData) {
        mData.removeAll(mData);
        mData.addAll(newData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List<Information> data) {
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
