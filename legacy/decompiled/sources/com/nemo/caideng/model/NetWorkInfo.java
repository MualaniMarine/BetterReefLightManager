package com.nemo.caideng.model;

/* JADX INFO: loaded from: classes.dex */
public class NetWorkInfo {
    private boolean isConnected;
    private String serverAddress;
    private String ssidName;

    public String getSsidName() {
        return this.ssidName;
    }

    public void setSsidName(String str) {
        this.ssidName = str;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String str) {
        this.serverAddress = str;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setConnected(boolean z) {
        this.isConnected = z;
    }
}
