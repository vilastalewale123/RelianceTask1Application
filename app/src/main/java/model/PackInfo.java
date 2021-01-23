package model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PackInfo {

    private  String appName = "";
    private  String packageName = "";
    private  String versionName = "";
    private  int versionCode = 0;
    private  String activityClassname= "";
    private  Drawable icon;

    public  void setAppName(String appName) {
        this.appName = appName;
    }

    public  void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public  void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public  void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public  void setActivityClassname(String activityClassname) {
        this.activityClassname = activityClassname;
    }

    public  void setIcon(Drawable icon) {
        this.icon = icon;
    }


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
    public String getActivityClassname() {
        return activityClassname;
    }

    public Drawable getIcon() {
        return icon;
    }

    private void prettyPrint() {
        System.out.println();
        Log.i("*** " + appName + "." + packageName + "():" + versionName + "\n", String.valueOf(versionCode));
    }

    public ArrayList<PackInfo> getPackages(Context context) {
        ArrayList<PackInfo> apps = getInstalledApps(false, context); /* false = no system packages */
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            apps.get(i).prettyPrint();
        }
        sortAscendingOrderList(apps);
        return apps;
    }

    private ArrayList<PackInfo> getInstalledApps(boolean getSysPackages, Context context) {
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
               newInfo.activityClassname = launchApp.getComponent().getClassName();
            }else{
               newInfo.activityClassname = "";
            }
            res.add(newInfo);
        }
        return res;
    }

    public void sortAscendingOrderList(ArrayList<PackInfo> apps) {
        Set<PackInfo> set = new HashSet<>(apps);
        Collections.sort(new ArrayList<>(set), (lhs, rhs) -> lhs.appName.compareTo(rhs.appName));
    }
}