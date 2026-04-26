package com.nemo.caideng.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import com.nemo.caideng.BaseApplication;

/* JADX INFO: loaded from: classes.dex */
public class GpsUtil {
    public static boolean isGPSEnabled() {
        LocationManager locationManager;
        if (BaseApplication.getInstance() == null || (locationManager = (LocationManager) BaseApplication.getInstance().getSystemService("location")) == null) {
            return false;
        }
        return locationManager.isProviderEnabled("gps");
    }

    public static void openGPS(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        intent.addCategory("android.intent.category.ALTERNATIVE");
        intent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(activity, 0, intent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void openGPS2(Activity activity, int i) {
        activity.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), i);
    }
}
