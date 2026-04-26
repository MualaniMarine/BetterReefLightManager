package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.task.ICodeData;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.CRC8;

/* JADX INFO: loaded from: classes.dex */
public class DataCode implements ICodeData {
    public static final int DATA_CODE_LEN = 6;
    private static final int INDEX_MAX = 127;
    private final byte mCrcHigh;
    private final byte mCrcLow;
    private final byte mDataHigh;
    private final byte mDataLow;
    private final byte mSeqHeader;

    public DataCode(char u8, int index) {
        if (index > INDEX_MAX) {
            throw new RuntimeException("index > INDEX_MAX");
        }
        byte[] bArrSplitUint8To2bytes = ByteUtil.splitUint8To2bytes(u8);
        this.mDataHigh = bArrSplitUint8To2bytes[0];
        this.mDataLow = bArrSplitUint8To2bytes[1];
        CRC8 crc8 = new CRC8();
        crc8.update(ByteUtil.convertUint8toByte(u8));
        crc8.update(index);
        byte[] bArrSplitUint8To2bytes2 = ByteUtil.splitUint8To2bytes((char) (((int) crc8.getValue()) & 255));
        this.mCrcHigh = bArrSplitUint8To2bytes2[0];
        this.mCrcLow = bArrSplitUint8To2bytes2[1];
        this.mSeqHeader = (byte) index;
    }

    @Override // com.espressif.iot.esptouch.task.ICodeData
    public byte[] getBytes() {
        return new byte[]{0, ByteUtil.combine2bytesToOne(this.mCrcHigh, this.mDataHigh), 1, this.mSeqHeader, 0, ByteUtil.combine2bytesToOne(this.mCrcLow, this.mDataLow)};
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = getBytes();
        for (int i = 0; i < 6; i++) {
            String strConvertByte2HexString = ByteUtil.convertByte2HexString(bytes[i]);
            sb.append("0x");
            if (strConvertByte2HexString.length() == 1) {
                sb.append("0");
            }
            sb.append(strConvertByte2HexString);
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override // com.espressif.iot.esptouch.task.ICodeData
    public char[] getU8s() {
        throw new RuntimeException("DataCode don't support getU8s()");
    }
}
