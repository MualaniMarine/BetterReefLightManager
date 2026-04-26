package com.espressif.iot.esptouch.protocol;

import com.espressif.iot.esptouch.util.ByteUtil;

/* JADX INFO: loaded from: classes.dex */
public class TouchData {
    private final byte[] mData;

    public TouchData(String string) {
        this.mData = ByteUtil.getBytesByString(string);
    }

    public TouchData(byte[] data) {
        if (data == null) {
            throw new NullPointerException("data can't be null");
        }
        this.mData = data;
    }

    public byte[] getData() {
        return this.mData;
    }
}
