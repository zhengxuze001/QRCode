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
import com.xone.qrcode.model.entities.ScanHistory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class ScanHistoryAdapter extends BaseAdapter {
    private List<ScanHistory> data;
    private LayoutInflater inflater;
    private Context context;
    private SimpleDateFormat mSimpleDateFormat;

    public ScanHistoryAdapter(Context context, List<ScanHistory> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_scan_history, null);
            holder.QRCodeImg = (ImageView) convertView.findViewById(R.id.QRCode_imageView);
            holder.QRCodeType = (TextView) convertView.findViewById(R.id.QRCode_type);
            holder.QRCodeContent = (TextView) convertView.findViewById(R.id.QRCode_content);
            holder.QRCodeTime = (TextView) convertView.findViewById(R.id.QRCode_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ScanHistory scanHistory = data.get(position);
        if (!TextUtils.isEmpty(scanHistory.getQRCodeImgPath())) {
            Glide.with(context).load(scanHistory.getQRCodeImgPath()).into(holder.QRCodeImg);
        }
        holder.QRCodeType.setText(TextUtils.isEmpty(scanHistory.getQRCodeType()) ? "" : scanHistory.getQRCodeType());
        holder.QRCodeContent.setText(TextUtils.isEmpty(scanHistory.getQRCodeContent()) ? "" : scanHistory.getQRCodeContent());
        holder.QRCodeTime.setText(mSimpleDateFormat.format(new Date(scanHistory.getQRCodeTime())));
        return convertView;
    }

    private static class ViewHolder {
        ImageView QRCodeImg;
        TextView QRCodeType;
        TextView QRCodeContent;
        TextView QRCodeTime;
    }
}