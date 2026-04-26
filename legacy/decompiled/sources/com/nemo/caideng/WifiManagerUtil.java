package com.nemo.caideng;

import android.net.wifi.WifiConfiguration;

/* JADX INFO: loaded from: classes.dex */
public class WifiManagerUtil {
    private WifiConfiguration createWifiInfo(String str, String str2, String str3, int i) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + str + "\"";
        wifiConfiguration.status = 2;
        String[] strArrSplit = str3.substring(1, str3.indexOf("]")).split("-");
        String str4 = strArrSplit[0];
        String str5 = strArrSplit.length > 1 ? strArrSplit[1] : "";
        String str6 = strArrSplit.length > 2 ? strArrSplit[2] : "";
        if (str4.contains("EAP")) {
            wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(2);
        } else if (str4.contains("WPA")) {
            wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(0);
        } else if (str4.contains("WEP")) {
            wifiConfiguration.wepKeys[0] = "\"" + str2 + "\"";
            wifiConfiguration.wepTxKeyIndex = 0;
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(0);
        }
        if (str4.contains("WPA2")) {
            wifiConfiguration.allowedProtocols.set(1);
        } else if (str4.contains("WPA")) {
            wifiConfiguration.allowedProtocols.set(0);
        }
        if (!str5.equals("")) {
            if (str5.contains("IEEE802.1X")) {
                wifiConfiguration.allowedKeyManagement.set(3);
            } else if (str4.contains("WPA") && str5.contains("EAP")) {
                wifiConfiguration.allowedKeyManagement.set(2);
            } else if (str4.contains("WPA") && str5.contains("PSK")) {
                wifiConfiguration.allowedKeyManagement.set(1);
            }
        } else {
            wifiConfiguration.allowedKeyManagement.set(0);
        }
        if (!str6.equals("")) {
            if (str6.contains("CCMP")) {
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
            }
            if (str6.contains("TKIP")) {
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
            }
        }
        return wifiConfiguration;
    }
}
