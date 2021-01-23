package data;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;

import com.vilas.installedapplibrary.GetInstalledApps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.PackInfo;

public class DataSource {

    public ArrayList<PackInfo> getPackages(Context context) {
        List<PackageInfo> packs = GetInstalledApps.getSingleTonInstance().getInstalledApps(context);
        ArrayList<PackInfo> apps = getInstalledApps(packs,false, context); /* false = no system packages */
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            apps.get(i);
        }
        sortAscendingOrderList(apps);
        return apps;
    }

    private ArrayList<PackInfo> getInstalledApps(List<PackageInfo> packs,boolean getSysPackages, Context context) {
        ArrayList<PackInfo> res = new ArrayList<>();
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo packageInfo = packs.get(i);
            if ((!getSysPackages) && (packageInfo.versionName == null)) {
                continue;
            }
            PackInfo newInfo = new PackInfo();
            newInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            newInfo.setPackageName(packageInfo.packageName);
            newInfo.setVersionName(packageInfo.versionName);
            newInfo.setVersionCode(packageInfo.versionCode);
            newInfo.setIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
            Intent launchApp = context.getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
            if(launchApp!=null) {
                newInfo.setActivityClassname(launchApp.getComponent().getClassName());
            }else{
                newInfo.setActivityClassname("");
            }
            res.add(newInfo);
        }
        return res;
    }

    public void sortAscendingOrderList(ArrayList<PackInfo> apps) {
        Set<PackInfo> set = new HashSet<>(apps);
        Collections.sort(new ArrayList<>(set), (lhs, rhs) -> lhs.getAppName().compareTo(rhs.getAppName()));
    }
}
