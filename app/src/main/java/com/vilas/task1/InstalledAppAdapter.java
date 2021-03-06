package com.vilas.task1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vilas.installedapplibrary.GetInstalledApps;

import java.util.ArrayList;

import model.PackInfo;
import listeners.OnItemClickListener;

public class InstalledAppAdapter extends RecyclerView.Adapter<InstalledAppViewHolder> {

    private ArrayList<GetInstalledApps.PackInfo> appList;
    private OnItemClickListener onItemClickListener;
    private Context context;
    public InstalledAppAdapter(ArrayList<GetInstalledApps.PackInfo> pInfos, OnItemClickListener onItemClickListener,Context context){
        this.appList= pInfos;
        this.onItemClickListener =onItemClickListener;
        this.context = context;
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
        GetInstalledApps.PackInfo pInfo = appList.get(position);
        viewHolder.imageView.setImageDrawable(pInfo.getIcon());
        viewHolder.appName.setText(pInfo.getAppName());
        viewHolder.packageName.setText(pInfo.getPackageName());
        viewHolder.versionCode.setText(String.valueOf(pInfo.getVersionCode()));
        viewHolder.versionName.setText(pInfo.getVersionName());
        if (pInfo.getActivityClassName().equals("")) {
            viewHolder.activityClassName.setVisibility(View.GONE);
        }else{
            viewHolder.activityClassName.setVisibility(View.VISIBLE);
            viewHolder.activityClassName.setText(pInfo.getActivityClassName());
        }
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public void setFilter(ArrayList<GetInstalledApps.PackInfo> newList){
        appList = new ArrayList<>();
        appList.addAll(newList);
        notifyDataSetChanged();
    }

    public void setItems(){
        appList.clear();
        appList.addAll(GetInstalledApps.getSingleTonInstance().getInstalledApps(context));
        notifyDataSetChanged();
    }
}
