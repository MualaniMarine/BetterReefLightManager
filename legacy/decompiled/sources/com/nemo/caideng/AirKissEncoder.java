package com.nemo.caideng;

import java.util.Arrays;
import java.util.Random;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class AirKissEncoder {
    private int[] mEncodedData = new int[65536];
    private int mLength = 0;
    private char mRandomChar = (char) new Random().nextInt(127);

    public AirKissEncoder(String str, String str2) {
        int i = 10;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            leadingPart();
            magicCode(str, str2);
            for (int i3 = 0; i3 < 15; i3++) {
                prefixCode(str2);
                String str3 = str2 + this.mRandomChar + str;
                byte[] bArr = new byte[4];
                int i4 = 0;
                while (i4 < str3.length() / 4) {
                    System.arraycopy(str3.getBytes(), i4 * 4, bArr, 0, 4);
                    sequence(i4, bArr);
                    i4++;
                }
                if (str3.length() % 4 != 0) {
                    int length = str3.length() % 4;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(str3.getBytes(), i4 * 4, bArr2, 0, length);
                    sequence(i4, bArr2);
                }
            }
            i = i2;
        }
    }

    public int[] getEncodedData() {
        return Arrays.copyOf(this.mEncodedData, this.mLength);
    }

    public char getRandomChar() {
        return this.mRandomChar;
    }

    private void appendEncodedData(int i) {
        int[] iArr = this.mEncodedData;
        int i2 = this.mLength;
        this.mLength = i2 + 1;
        iArr[i2] = i;
    }

    private int CRC8(byte[] bArr) {
        int length = bArr.length;
        byte b = 0;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return b & UByte.MAX_VALUE;
            }
            int i3 = i + 1;
            byte b2 = bArr[i];
            for (byte b3 = 8; b3 != 0; b3 = (byte) (b3 - 1)) {
                int i4 = b & UByte.MAX_VALUE;
                int i5 = b2 & UByte.MAX_VALUE;
                byte b4 = (byte) (((byte) (i4 ^ i5)) & UByte.MAX_VALUE & 1);
                b = (byte) (i4 >>> 1);
                if (b4 != 0) {
                    b = (byte) ((b & UByte.MAX_VALUE) ^ 140);
                }
                b2 = (byte) (i5 >>> 1);
            }
            i = i3;
            length = i2;
        }
    }

    private int CRC8(String str) {
        return CRC8(str.getBytes());
    }

    private void leadingPart() {
        for (int i = 0; i < 50; i++) {
            for (int i2 = 1; i2 <= 4; i2++) {
                appendEncodedData(i2);
            }
        }
    }

    private void magicCode(String str, String str2) {
        int length = str.length() + str2.length() + 1;
        int[] iArr = new int[4];
        iArr[0] = ((length >>> 4) & 15) | 0;
        if (iArr[0] == 0) {
            iArr[0] = 8;
        }
        iArr[1] = (length & 15) | 16;
        int iCRC8 = CRC8(str);
        iArr[2] = ((iCRC8 >>> 4) & 15) | 32;
        iArr[3] = (iCRC8 & 15) | 48;
        for (int i = 0; i < 20; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                appendEncodedData(iArr[i2]);
            }
        }
    }

    private void prefixCode(String str) {
        int length = str.length();
        int iCRC8 = CRC8(new byte[]{(byte) length});
        int[] iArr = {((length >>> 4) & 15) | 64, (length & 15) | 80, ((iCRC8 >>> 4) & 15) | 96, (iCRC8 & 15) | 112};
        for (int i = 0; i < 4; i++) {
            appendEncodedData(iArr[i]);
        }
    }

    private void sequence(int i, byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 1];
        bArr2[0] = (byte) (i & 255);
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        appendEncodedData(CRC8(bArr2) | 128);
        appendEncodedData(i | 128);
        for (byte b : bArr) {
            appendEncodedData(b | UByte.MIN_VALUE);
        }
    }
}
