package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.security.ITouchEncryptor;
import com.espressif.iot.esptouch.task.IEsptouchGenerator;
import com.espressif.iot.esptouch.util.ByteUtil;
import java.net.InetAddress;

/* JADX INFO: loaded from: classes.dex */
public class EsptouchGenerator implements IEsptouchGenerator {
    private final byte[][] mDcBytes2;
    private final byte[][] mGcBytes2;

    public EsptouchGenerator(byte[] apSsid, byte[] apBssid, byte[] apPassword, InetAddress inetAddress, ITouchEncryptor encryptor) {
        char[] u8s = new GuideCode().getU8s();
        this.mGcBytes2 = new byte[u8s.length][];
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[][] bArr = this.mGcBytes2;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = ByteUtil.genSpecBytes(u8s[i2]);
            i2++;
        }
        char[] u8s2 = new DatumCode(apSsid, apBssid, apPassword, inetAddress, encryptor).getU8s();
        this.mDcBytes2 = new byte[u8s2.length][];
        while (true) {
            byte[][] bArr2 = this.mDcBytes2;
            if (i >= bArr2.length) {
                return;
            }
            bArr2[i] = ByteUtil.genSpecBytes(u8s2[i]);
            i++;
        }
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchGenerator
    public byte[][] getGCBytes2() {
        return this.mGcBytes2;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchGenerator
    public byte[][] getDCBytes2() {
        return this.mDcBytes2;
    }
}
