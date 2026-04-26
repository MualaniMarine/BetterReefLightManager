package com.espressif.iot.esptouch;

import java.net.InetAddress;

/* JADX INFO: loaded from: classes.dex */
public interface IEsptouchResult {
    String getBssid();

    InetAddress getInetAddress();

    boolean isCancelled();

    boolean isSuc();
}
