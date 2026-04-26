package com.espressif.iot.esptouch.util;

import java.util.zip.Checksum;

/* JADX INFO: loaded from: classes.dex */
public class CRC8 implements Checksum {
    private static final short CRC_INITIAL = 0;
    private static final short CRC_POLYNOM = 140;
    private static final short[] crcTable = new short[256];
    private final short init = 0;
    private short value = 0;

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                i2 = (i2 & 1) != 0 ? (i2 >>> 1) ^ 140 : i2 >>> 1;
            }
            crcTable[i] = (short) i2;
        }
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] buffer, int offset, int len) {
        for (int i = 0; i < len; i++) {
            byte b = buffer[offset + i];
            short s = this.value;
            this.value = (short) (crcTable[(b ^ s) & 255] ^ (s << 8));
        }
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] buffer) {
        update(buffer, 0, buffer.length);
    }

    @Override // java.util.zip.Checksum
    public void update(int b) {
        update(new byte[]{(byte) b}, 0, 1);
    }

    @Override // java.util.zip.Checksum
    public long getValue() {
        return this.value & 255;
    }

    @Override // java.util.zip.Checksum
    public void reset() {
        this.value = this.init;
    }
}
