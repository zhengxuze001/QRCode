package com.xone.qrcode.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.Report;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class CommentAdapter extends BaseAdapter {
    private List<Report> data;
    private LayoutInflater inflater;
    private Context context;

    public CommentAdapter(Context context, List<Report> data) {
        this.data = data;
        this.context = context;
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
            convertView = inflater.inflate(R.layout.item_comment, null);
            holder.userHeadImageView = (ImageView) convertView.findViewById(R.id.userHeadImageView);
            holder.userNameTextView = (TextView) convertView.findViewById(R.id.userNameTextView);
            holder.createTimeTextView = (TextView) convertView.findViewById(R.id.createTimeTextView);
            holder.commentContentTextView = (TextView) convertView.findViewById(R.id.commentContentTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != report.getPublisher().getHeadImg()) {
            Glide.with(context).load(report.getPublisher().getHeadImg().getUrl()).into(holder.userHeadImageView);
        }
        holder.userNameTextView.setText(TextUtils.isEmpty(report.getPublisher().getUsername()) ? "" : report.getPublisher().getUsername());
        if (TextUtils.isEmpty(report.getReportDetails())) {
            holder.commentContentTextView.setText(TextUtils.isEmpty(report.getReportReason()) ? "" : report.getReportReason());
        } else {
            holder.commentContentTextView.setText(TextUtils.isEmpty(report.getReportDetails()) ? "" : report.getReportDetails());
        }
        holder.createTimeTextView.setText(TextUtils.isEmpty(report.getCreatedAt()) ? "" : report.getCreatedAt());
        return convertView;
    }

    private static class ViewHolder {
        ImageView userHeadImageView;
        TextView userNameTextView;
        TextView createTimeTextView;
        TextView commentContentTextView;
    }
}