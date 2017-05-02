package com.xone.qrcode.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xone.qrcode.R;
import com.xone.qrcode.model.entities.Appeal;

import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class AppealAdapter extends BaseAdapter {
    private List<Appeal> data;
    private LayoutInflater inflater;

    public AppealAdapter(Context context, List<Appeal> data) {
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
        Appeal appeal = data.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_appeal, null);
            holder.appealTimeTextView = (TextView) convertView.findViewById(R.id.appealTimeTextView);
            holder.appealWebsiteUrlTextView = (TextView) convertView.findViewById(R.id.appealWebsiteUrlTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.appealWebsiteUrlTextView.setText(TextUtils.isEmpty(appeal.getWebsiteUrl()) ? "" : appeal.getWebsiteUrl());
        holder.appealTimeTextView.setText(TextUtils.isEmpty(appeal.getCreatedAt()) ? "" : appeal.getCreatedAt());
        return convertView;
    }

    private static class ViewHolder {
        TextView appealTimeTextView;
        TextView appealWebsiteUrlTextView;
    }
}