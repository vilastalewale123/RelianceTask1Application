package com.vilas.task1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import listeners.OnItemClickListener;

public class InstalledAppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView appName;
    public ImageView imageView;
    public TextView packageName;
    public TextView versionCode;
    public TextView versionName;
    public TextView activityClassName;
    public OnItemClickListener onItemClickListener;

    public InstalledAppViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        appName = (TextView)itemView.findViewById(R.id.app_name);
        imageView=(ImageView)itemView.findViewById(R.id.app_imageView);
        packageName = (TextView) itemView.findViewById(R.id.package_name);
        versionCode = (TextView)itemView.findViewById(R.id.version_code);
        versionName = (TextView)itemView.findViewById(R.id.version_name);
        activityClassName =(TextView)itemView.findViewById(R.id.activity_classname);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getBindingAdapterPosition());
    }

}
