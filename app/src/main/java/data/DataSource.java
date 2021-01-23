package data;

import android.content.Context;

import com.vilas.installedapplibrary.GetInstalledApps;

import java.util.ArrayList;

public class DataSource {

    public ArrayList<GetInstalledApps.PackInfo> getAppInfo(Context context) {
        return GetInstalledApps.getSingleTonInstance().getInstalledApps(context);
    }
}
