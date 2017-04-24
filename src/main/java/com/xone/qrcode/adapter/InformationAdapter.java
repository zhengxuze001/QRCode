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
import com.xone.qrcode.model.entities.Information;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhengxuze on 2017/4/11.
 */

public class InformationAdapter extends BaseAdapter {
    private List<Information> data;
    private LayoutInflater inflater;
    private Context context;
    private SimpleDateFormat mSimpleDateFormat;

    public InformationAdapter(Context context, List<Information> data) {
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
    public int getItemViewType(int position) {
        return data.get(position).getHeadlines() ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Information information = data.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (information.getHeadlines()) {
                convertView = inflater.inflate(R.layout.item_information_headlines, null);
            } else {
                convertView = inflater.inflate(R.layout.item_information, null);
            }
            holder.imageView = (ImageView) convertView.findViewById(R.id.information_imageView);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.information_title_textView);
            holder.publisherTextView = (TextView) convertView.findViewById(R.id.information_publisher_textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != information.getImage()) {
            Glide.with(context).load(information.getImage().getUrl()).into(holder.imageView);
        }
        holder.titleTextView.setText(TextUtils.isEmpty(information.getTitle()) ? "" : information.getTitle());
        holder.publisherTextView.setText(TextUtils.isEmpty(information.getPublisher()) ? "" : information.getPublisher());
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView publisherTextView;
    }
}