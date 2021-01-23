package com.vilas.task1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.PackInfo;
import listeners.OnItemClickListener;

public class InstalledAppAdapter extends RecyclerView.Adapter<InstalledAppViewHolder> {

    private ArrayList<PackInfo> appList;
    private OnItemClickListener onItemClickListener;
    private ArrayList<PackInfo> appListFiltered;
    public InstalledAppAdapter(ArrayList<PackInfo> pInfos, OnItemClickListener onItemClickListener){
        this.appList= pInfos;
        this.onItemClickListener =onItemClickListener;
    }
    @NonNull
    @Override
    public InstalledAppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.placeholder_row_layout, viewGroup, false);
        return new InstalledAppViewHolder(v,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InstalledAppViewHolder viewHolder, int position) {
        PackInfo pInfo = appList.get(position);
        viewHolder.imageView.setImageDrawable(pInfo.getIcon());
        viewHolder.appName.setText(pInfo.getAppName());
        viewHolder.packageName.setText(pInfo.getPackageName());
        viewHolder.versionCode.setText(String.valueOf(pInfo.getVersionCode()));
        viewHolder.versionName.setText(pInfo.getVersionName());
        if (pInfo.getActivityClassname().equals("")) {
            viewHolder.activityClassName.setVisibility(View.GONE);
        }else{
            viewHolder.activityClassName.setVisibility(View.VISIBLE);
            viewHolder.activityClassName.setText(pInfo.getActivityClassname());
        }
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public void setFilter(ArrayList<PackInfo> newList){
        appList = new ArrayList<>();
        appList.addAll(newList);
        notifyDataSetChanged();
    }
}
