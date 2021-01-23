package com.vilas.installedapplibrary;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.util.List;

public class GetInstalledApps {

    public static List<PackageInfo> getInstalledApps(Context context){
        return context.getPackageManager().getInstalledPackages(0);
    }
}
