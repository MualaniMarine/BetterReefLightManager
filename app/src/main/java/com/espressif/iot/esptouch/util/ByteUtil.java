package com.espressif.iot.esptouch.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class ByteUtil {
    public static final String ESPTOUCH_ENCODING_CHARSET = "UTF-8";

    public static char convertByte2Uint8(byte b) {
        return (char) (b & 255);
    }

    public static void putString2bytes(byte[] destbytes, String srcString, int destOffset, int srcOffset, int count) {
        for (int i = 0; i < count; i++) {
            destbytes[count + i] = srcString.getBytes()[i];
        }
    }

    public static byte convertUint8toByte(char uint8) {
        if (uint8 <= 255) {
            return (byte) uint8;
        }
        throw new RuntimeException("Out of Boundary");
    }

    public static char[] convertBytes2Uint8s(byte[] bytes) {
        int length = bytes.length;
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = convertByte2Uint8(bytes[i]);
        }
        return cArr;
    }

    public static void putbytes2Uint8s(char[] destUint8s, byte[] srcBytes, int destOffset, int srcOffset, int count) {
        for (int i = 0; i < count; i++) {
            destUint8s[destOffset + i] = convertByte2Uint8(srcBytes[srcOffset + i]);
        }
    }

    public static String convertByte2HexString(byte b) {
        return Integer.toHexString(convertByte2Uint8(b));
    }

    public static String convertU8ToHexString(char u8) {
        return Integer.toHexString(u8);
    }

    public static byte[] splitUint8To2bytes(char uint8) {
        byte b;
        byte b2;
        if (uint8 < 0 || uint8 > 255) {
            throw new RuntimeException("Out of Boundary");
        }
        String hexString = Integer.toHexString(uint8);
        if (hexString.length() > 1) {
            b2 = (byte) Integer.parseInt(hexString.substring(0, 1), 16);
            b = (byte) Integer.parseInt(hexString.substring(1, 2), 16);
        } else {
            b = (byte) Integer.parseInt(hexString.substring(0, 1), 16);
            b2 = 0;
        }
        return new byte[]{b2, b};
    }

    public static byte combine2bytesToOne(byte high, byte low) {
        if (high < 0 || high > 15 || low < 0 || low > 15) {
            throw new RuntimeException("Out of Boundary");
        }
        return (byte) ((high << 4) | low);
    }

    public static char combine2bytesToU16(byte high, byte low) {
        return (char) ((convertByte2Uint8(high) << '\b') | convertByte2Uint8(low));
    }

    private static byte randomByte() {
        return (byte) (127 - new Random().nextInt(256));
    }

    public static byte[] randomBytes(char len) {
        byte[] bArr = new byte[len];
        for (int i = 0; i < len; i++) {
            bArr[i] = randomByte();
        }
        return bArr;
    }

    public static byte[] genSpecBytes(char len) {
        byte[] bArr = new byte[len];
        for (int i = 0; i < len; i++) {
            bArr[i] = 49;
        }
        return bArr;
    }

    public static byte[] randomBytes(byte len) {
        return randomBytes(convertByte2Uint8(len));
    }

    public static byte[] genSpecBytes(byte len) {
        return genSpecBytes(convertByte2Uint8(len));
    }

    public static String parseBssid(byte[] bssidBytes, int offset, int count) {
        byte[] bArr = new byte[count];
        System.arraycopy(bssidBytes, offset, bArr, 0, count);
        return parseBssid(bArr);
    }

    public static String parseBssid(byte[] bssidBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bssidBytes) {
            int i = b & 255;
            String hexString = Integer.toHexString(i);
            if (i < 16) {
                hexString = "0" + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static byte[] getBytesByString(String string) {
        try {
            return string.getBytes(ESPTOUCH_ENCODING_CHARSET);
        } catch (UnsupportedEncodingException unused) {
            throw new IllegalArgumentException("the charset is invalid");
        }
    }

    private static void test_splitUint8To2bytes() {
        byte[] bArrSplitUint8To2bytes = splitUint8To2bytes((char) 20);
        if (bArrSplitUint8To2bytes[0] == 1 && bArrSplitUint8To2bytes[1] == 4) {
            System.out.println("test_splitUint8To2bytes(): pass");
        } else {
            System.out.println("test_splitUint8To2bytes(): fail");
        }
    }

    private static void test_combine2bytesToOne() {
        if (combine2bytesToOne((byte) 1, (byte) 4) == 20) {
            System.out.println("test_combine2bytesToOne(): pass");
        } else {
            System.out.println("test_combine2bytesToOne(): fail");
        }
    }

    private static void test_convertChar2Uint8() {
        if (convertByte2Uint8((byte) 97) == 'a' && convertByte2Uint8(ByteCompanionObject.MIN_VALUE) == 128 && convertByte2Uint8((byte) -1) == 255) {
            System.out.println("test_convertChar2Uint8(): pass");
        } else {
            System.out.println("test_convertChar2Uint8(): fail");
        }
    }

    private static void test_convertUint8toByte() {
        if (convertUint8toByte('a') == 97 && convertUint8toByte((char) 128) == -128 && convertUint8toByte((char) 255) == -1) {
            System.out.println("test_convertUint8toByte(): pass");
        } else {
            System.out.println("test_convertUint8toByte(): fail");
        }
    }

    private static void test_parseBssid() {
        if (parseBssid(new byte[]{15, -2, 52, -102, -93, -60}).equals("0ffe349aa3c4")) {
            System.out.println("test_parseBssid(): pass");
        } else {
            System.out.println("test_parseBssid(): fail");
        }
    }

    public static void main(String[] args) {
        test_convertUint8toByte();
        test_convertChar2Uint8();
        test_splitUint8To2bytes();
        test_combine2bytesToOne();
        test_parseBssid();
    }
}
