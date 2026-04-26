package com.nemo.caideng.util;

/* JADX INFO: loaded from: classes.dex */
public class HexStringUtil {
    public static byte[] hexToByteArray(String str) {
        byte[] bArr = new byte[str.length() / 2];
        int length = str.length();
        int i = 0;
        while (i <= length - 1) {
            int i2 = i + 2;
            bArr[i / 2] = (byte) Integer.parseInt(str.substring(i, i2), 16);
            i = i2;
        }
        return bArr;
    }

    public static String byteArrayToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        for (int i = 0; i <= length - 1; i++) {
            char cForDigit = Character.forDigit((bArr[i] >> 4) & 15, 16);
            char cForDigit2 = Character.forDigit(bArr[i] & 15, 16);
            sb.append(cForDigit);
            sb.append(cForDigit2);
        }
        return sb.toString();
    }
}
