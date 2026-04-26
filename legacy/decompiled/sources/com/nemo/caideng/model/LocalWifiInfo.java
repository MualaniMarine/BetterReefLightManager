package com.nemo.caideng.model;

import android.net.wifi.ScanResult;

/* JADX INFO: loaded from: classes.dex */
public class LocalWifiInfo {
    private boolean checked;
    private ScanResult scanResult;

    public ScanResult getScanResult() {
        return this.scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean z) {
        this.checked = z;
    }
}
