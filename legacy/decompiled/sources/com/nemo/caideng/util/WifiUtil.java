package com.nemo.caideng.util;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import com.nemo.caideng.BaseApplication;

/* JADX INFO: loaded from: classes.dex */
public class WifiUtil {
    public static boolean isWifiEnable() {
        return ((WifiManager) BaseApplication.getInstance().getApplicationContext().getSystemService("wifi")).isWifiEnabled();
    }

    public static void openWifi(Context context) {
        context.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
    }
}
