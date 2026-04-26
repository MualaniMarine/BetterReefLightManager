package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.security.ITouchEncryptor;
import com.espressif.iot.esptouch.task.ICodeData;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.CRC8;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class DatumCode implements ICodeData {
    private static final int EXTRA_HEAD_LEN = 5;
    private static final int EXTRA_LEN = 40;
    private final LinkedList<DataCode> mDataCodes;

    public DatumCode(byte[] apSsid, byte[] apBssid, byte[] apPassword, InetAddress ipAddress, ITouchEncryptor encryptor) {
        char length = (char) apPassword.length;
        CRC8 crc8 = new CRC8();
        crc8.update(apSsid);
        char value = (char) crc8.getValue();
        crc8.reset();
        crc8.update(apBssid);
        char value2 = (char) crc8.getValue();
        char length2 = (char) apSsid.length;
        byte[] address = ipAddress.getAddress();
        int length3 = address.length;
        char c = (char) (length3 + 5 + length + length2);
        LinkedList<DataCode> linkedList = new LinkedList<>();
        this.mDataCodes = linkedList;
        linkedList.add(new DataCode(c, 0));
        this.mDataCodes.add(new DataCode(length, 1));
        this.mDataCodes.add(new DataCode(value, 2));
        this.mDataCodes.add(new DataCode(value2, 3));
        char c2 = (char) (value2 ^ ((char) (value ^ ((char) (((char) (c ^ 0)) ^ length)))));
        for (int i = 0; i < length3; i++) {
            char cConvertByte2Uint8 = ByteUtil.convertByte2Uint8(address[i]);
            c2 = (char) (c2 ^ cConvertByte2Uint8);
            this.mDataCodes.add(new DataCode(cConvertByte2Uint8, i + 5));
        }
        for (int i2 = 0; i2 < apPassword.length; i2++) {
            char cConvertByte2Uint82 = ByteUtil.convertByte2Uint8(apPassword[i2]);
            c2 = (char) (c2 ^ cConvertByte2Uint82);
            this.mDataCodes.add(new DataCode(cConvertByte2Uint82, i2 + 5 + length3));
        }
        for (int i3 = 0; i3 < apSsid.length; i3++) {
            char cConvertByte2Uint83 = ByteUtil.convertByte2Uint8(apSsid[i3]);
            c2 = (char) (c2 ^ cConvertByte2Uint83);
            this.mDataCodes.add(new DataCode(cConvertByte2Uint83, i3 + 5 + length3 + length));
        }
        this.mDataCodes.add(4, new DataCode(c2, 4));
        int i4 = 5;
        for (int i5 = 0; i5 < apBssid.length; i5++) {
            DataCode dataCode = new DataCode(ByteUtil.convertByte2Uint8(apBssid[i5]), c + i5);
            if (i4 >= this.mDataCodes.size()) {
                this.mDataCodes.add(dataCode);
            } else {
                this.mDataCodes.add(i4, dataCode);
            }
            i4 += 4;
        }
    }

    @Override // com.espressif.iot.esptouch.task.ICodeData
    public byte[] getBytes() {
        byte[] bArr = new byte[this.mDataCodes.size() * 6];
        Iterator<DataCode> it = this.mDataCodes.iterator();
        int i = 0;
        while (it.hasNext()) {
            byte[] bytes = it.next().getBytes();
            int length = bytes.length;
            int i2 = 0;
            while (i2 < length) {
                bArr[i] = bytes[i2];
                i2++;
                i++;
            }
        }
        return bArr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : getBytes()) {
            String strConvertByte2HexString = ByteUtil.convertByte2HexString(b);
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
        byte[] bytes = getBytes();
        int length = bytes.length / 2;
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            cArr[i] = (char) (ByteUtil.combine2bytesToU16(bytes[i2], bytes[i2 + 1]) + '(');
        }
        return cArr;
    }
}
