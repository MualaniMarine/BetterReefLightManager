package com.tencent.bugly.proguard;

import java.nio.ByteBuffer;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.l */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0645l {
    /* JADX INFO: renamed from: a */
    public static boolean m375a(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m376a(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m378a(boolean z, boolean z2) {
        return z == z2;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m377a(Object obj, Object obj2) {
        return obj.equals(obj2);
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m379a(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        byte[] bArr = new byte[iPosition];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, iPosition);
        return bArr;
    }

    static {
        byte[] bArr = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
        byte[] bArr2 = new byte[256];
        byte[] bArr3 = new byte[256];
        for (int i = 0; i < 256; i++) {
            bArr2[i] = bArr[i >>> 4];
            bArr3[i] = bArr[i & 15];
        }
    }
}
