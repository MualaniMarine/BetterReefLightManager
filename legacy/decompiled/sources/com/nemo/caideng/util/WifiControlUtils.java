package com.nemo.caideng.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WifiControlUtils {
    public static final int WIFI_CIPHER_NPW = 0;
    public static final int WIFI_CIPHER_WAP = 2;
    public static final int WIFI_CIPHER_WEP = 1;
    private WifiManager.WifiLock mWifiLock;
    private WifiManager mWifiManager;
    private List<WifiConfiguration> wifiConfigurationList;
    private List<ScanResult> wifiList;

    public WifiControlUtils(Context context) {
        this.mWifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
    }

    public void openWifi() {
        if (this.mWifiManager.isWifiEnabled()) {
            return;
        }
        this.mWifiManager.setWifiEnabled(true);
        scanWifi();
    }

    public void closeWifi() {
        if (this.mWifiManager.isWifiEnabled()) {
            this.mWifiManager.setWifiEnabled(false);
        }
    }

    public WifiInfo getWifiInfo() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    public void scanWifi() {
        this.mWifiManager.startScan();
        this.wifiList = this.mWifiManager.getScanResults();
        this.wifiConfigurationList = this.mWifiManager.getConfiguredNetworks();
    }

    public List<WifiConfiguration> getWifiConfigurationList() {
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        this.wifiConfigurationList = configuredNetworks;
        return configuredNetworks;
    }

    public List<ScanResult> getWifiList() {
        List<ScanResult> scanResults = this.mWifiManager.getScanResults();
        this.wifiList = scanResults;
        return scanResults;
    }

    public void createWifiLock() {
        this.mWifiLock = this.mWifiManager.createWifiLock("testLock");
    }

    public void acquireWifiLock() {
        this.mWifiLock.acquire();
    }

    public void releaseWifilock() {
        if (this.mWifiLock.isHeld()) {
            this.mWifiLock.acquire();
        }
    }

    public void addNetWork(String str, String str2, int i) {
        int iAddNetwork;
        if (!removeWifi(str) && getExitsWifiConfig(str) != null) {
            iAddNetwork = getExitsWifiConfig(str).networkId;
        } else {
            iAddNetwork = this.mWifiManager.addNetwork(createWifiInfo(str, str2, i));
        }
        this.mWifiManager.enableNetwork(iAddNetwork, true);
    }

    public WifiConfiguration getExitsWifiConfig(String str) {
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        this.wifiConfigurationList = configuredNetworks;
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.SSID.equals("\"" + str + "\"")) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    public boolean removeWifi(int i) {
        return this.mWifiManager.removeNetwork(i);
    }

    public boolean removeWifi(String str) {
        if (getExitsWifiConfig(str) != null) {
            return removeWifi(getExitsWifiConfig(str).networkId);
        }
        return false;
    }

    public void disconnectWifi(int i) {
        this.mWifiManager.disableNetwork(i);
        this.mWifiManager.disconnect();
    }

    public void disconnectWifi(String str) {
        if (getExitsWifiConfig(str) != null) {
            disconnectWifi(getExitsWifiConfig(str).networkId);
        }
    }

    public WifiConfiguration createWifiInfo(String str, String str2, int i) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + str + "\"";
        WifiConfiguration exitsWifiConfig = getExitsWifiConfig(str);
        if (exitsWifiConfig != null) {
            this.mWifiManager.removeNetwork(exitsWifiConfig.networkId);
            this.mWifiManager.saveConfiguration();
        }
        if (i == 0) {
            wifiConfiguration.allowedKeyManagement.set(0);
        } else if (i == 1) {
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.wepKeys[0] = "\"" + str2 + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        } else if (i == 2) {
            wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.status = 2;
        }
        return wifiConfiguration;
    }
}
