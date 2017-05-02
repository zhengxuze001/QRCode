package com.xone.qrcode.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.Report;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class ReportAdapter extends BaseAdapter {
    private List<Report> data;
    private LayoutInflater inflater;

    public ReportAdapter(Context context, List<Report> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Report report = data.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_report, null);
            holder.reportReasonTextView = (TextView) convertView.findViewById(R.id.reportReasonTextView);
            holder.reportTimeTextView = (TextView) convertView.findViewById(R.id.reportTimeTextView);
            holder.reportWebsiteUrlTextView = (TextView) convertView.findViewById(R.id.reportWebsiteUrlTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.reportReasonTextView.setText(TextUtils.isEmpty(report.getReportReason()) ? "" : report.getReportReason());
        holder.reportTimeTextView.setText(TextUtils.isEmpty(report.getCreatedAt()) ? "" : report.getCreatedAt());
        holder.reportWebsiteUrlTextView.setText(TextUtils.isEmpty(report.getWebsiteUrl()) ? "" : report.getWebsiteUrl());
        return convertView;
    }

    private static class ViewHolder {
        TextView reportReasonTextView;
        TextView reportTimeTextView;
        TextView reportWebsiteUrlTextView;
    }
}