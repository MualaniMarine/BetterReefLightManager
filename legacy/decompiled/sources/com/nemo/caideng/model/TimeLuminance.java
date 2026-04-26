package com.nemo.caideng.model;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class TimeLuminance {
    private int hour;
    private boolean isEnable;
    private byte[] luminanceValue;
    private int minute;

    public int getHour() {
        return this.hour;
    }

    public void setHour(int i) {
        this.hour = i;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int i) {
        this.minute = i;
    }

    public byte[] getLuminanceValue() {
        return this.luminanceValue;
    }

    public void setLuminanceValue(byte[] bArr) {
        this.luminanceValue = bArr;
    }

    public String getLuminanceStr() {
        String string = Arrays.toString(this.luminanceValue);
        return string.substring(1, string.length() - 1);
    }

    public boolean isEnable() {
        int i = 0;
        for (byte b : this.luminanceValue) {
            i += b;
        }
        return (this.hour == 0 && this.minute == 0 && i == 0) ? false : true;
    }

    public void reSet() {
        this.hour = 0;
        this.minute = 0;
        for (int i = 0; i < 6; i++) {
            this.luminanceValue[i] = 0;
        }
    }
}
