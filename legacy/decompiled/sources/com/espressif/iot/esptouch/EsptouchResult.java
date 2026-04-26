package com.espressif.iot.esptouch;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class EsptouchResult implements IEsptouchResult {
    private final String mBssid;
    private final InetAddress mInetAddress;
    private AtomicBoolean mIsCancelled = new AtomicBoolean(false);
    private final boolean mIsSuc;

    public EsptouchResult(boolean isSuc, String bssid, InetAddress inetAddress) {
        this.mIsSuc = isSuc;
        this.mBssid = bssid;
        this.mInetAddress = inetAddress;
    }

    @Override // com.espressif.iot.esptouch.IEsptouchResult
    public boolean isSuc() {
        return this.mIsSuc;
    }

    @Override // com.espressif.iot.esptouch.IEsptouchResult
    public String getBssid() {
        return this.mBssid;
    }

    @Override // com.espressif.iot.esptouch.IEsptouchResult
    public boolean isCancelled() {
        return this.mIsCancelled.get();
    }

    public void setIsCancelled(boolean isCancelled) {
        this.mIsCancelled.set(isCancelled);
    }

    @Override // com.espressif.iot.esptouch.IEsptouchResult
    public InetAddress getInetAddress() {
        return this.mInetAddress;
    }

    public String toString() {
        Object[] objArr = new Object[4];
        objArr[0] = this.mBssid;
        InetAddress inetAddress = this.mInetAddress;
        objArr[1] = inetAddress == null ? null : inetAddress.getHostAddress();
        objArr[2] = Boolean.valueOf(this.mIsSuc);
        objArr[3] = Boolean.valueOf(this.mIsCancelled.get());
        return String.format("bssid=%s, address=%s, suc=%b, cancel=%b", objArr);
    }
}
