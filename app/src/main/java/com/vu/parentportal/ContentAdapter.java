package com.vu.parentportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.vu.parentportal.models.Content;

import java.util.List;

public class ContentAdapter extends ArrayAdapter<Content> {
    private final Context context;
    private final List<Content> contentList;
    public ContentAdapter(@NonNull Context context, @NonNull List<Content> contentList) {
        super(context, R.layout.list_item_content, contentList);
        this.context = context;
        this.contentList = contentList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_content, parent, false);
        }
        Content content = contentList.get(position);
        TextView titleTextView = convertView.findViewById(R.id.tv_content_title);
        TextView detailTextView = convertView.findViewById(R.id.tv_content_detail);
        titleTextView.setText(content.getContentTitle());
        detailTextView.setText(content.getContentDetail());
        return convertView;
    }
}