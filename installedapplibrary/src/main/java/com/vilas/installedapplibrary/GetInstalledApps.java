package com.vilas.installedapplibrary;

//List of Installed Apps
//Sort List ascending order.

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetInstalledApps {

    private static GetInstalledApps getInstance;

    public GetInstalledApps() {
    }

    //Providing global point of access
    public static GetInstalledApps getSingleTonInstance() {
        if (getInstance == null) {
            getInstance = new GetInstalledApps();
        }
        return getInstance;
    }

    public ArrayList<PackInfo> getInstalledApps(Context context) {
        ArrayList<PackInfo> apps = getPackaInfo(false, context); /* false = no system packages */
        Collections.sort(apps, (lhs, rhs) -> lhs.appName.compareTo(rhs.appName));
        return apps;
    }

    private ArrayList<PackInfo> getPackaInfo(boolean getSysPackages, Context context) {

        ArrayList<PackInfo> res = new ArrayList<>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo packageInfo = packs.get(i);
            if ((!getSysPackages) && (packageInfo.versionName == null)) {
                continue;
            }
            PackInfo newInfo = new PackInfo();
            newInfo.appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            newInfo.packageName = packageInfo.packageName;
            newInfo.versionName = packageInfo.versionName;
            newInfo.versionCode = packageInfo.versionCode;
            newInfo.icon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
            Intent launchApp = context.getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
            if(launchApp!=null) {
                newInfo.activityClassName = (launchApp.getComponent().getClassName());
            }else{
                newInfo.activityClassName = "";
            }
            res.add(newInfo);
        }
        return res;
    }

    public class PackInfo{

        private  String appName = "";
        private  String packageName = "";
        private  String versionName = "";
        private  int versionCode = 0;
        private  String activityClassName= "";
        private  Drawable icon;

        public String getAppName() {
            return appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getVersionName() {
            return versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public String getActivityClassName() {
            return activityClassName;
        }

        public Drawable getIcon() {
            return icon;
        }
    }
}
