package com.vilas.installedapplibrary;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.util.List;

public class GetInstalledApps {

    private static final GetInstalledApps getInstance = new GetInstalledApps();

    public GetInstalledApps(){}

    //Providing global point of access
    public static GetInstalledApps getSingleTonInstance(){
        return getInstance;
    }

    public List<PackageInfo> getInstalledApps(Context context){
        return context.getPackageManager().getInstalledPackages(0);
    }
}
