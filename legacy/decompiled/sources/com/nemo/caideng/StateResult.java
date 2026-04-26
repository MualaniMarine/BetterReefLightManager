package com.nemo.caideng;

import java.net.InetAddress;

/* JADX INFO: loaded from: classes.dex */
public class StateResult {
    public CharSequence message = null;
    public boolean permissionGranted = false;
    public boolean locationRequirement = false;
    public boolean wifiConnected = false;
    public boolean is5G = false;
    public InetAddress address = null;
    public InetAddress serverAddress = null;
    public String ssid = null;
    public byte[] ssidBytes = null;
    public String bssid = null;
}
