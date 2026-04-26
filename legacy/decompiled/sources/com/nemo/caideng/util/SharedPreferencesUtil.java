package com.nemo.caideng.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class SharedPreferencesUtil {
    static SharedPreferences dateStore;
    static SharedPreferencesUtil util;
    private String NET_NAME = "ssid";
    private String NET_PASS = "ssid_pass";
    private String K7_TAG = "k7";
    private String X4_TAG = "x4";
    private String MODEL = "model";
    private String LAST_ADDRESS = "last_address";

    private SharedPreferencesUtil(Context context) {
        dateStore = context.getSharedPreferences("data", 0);
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (util == null) {
            util = new SharedPreferencesUtil(context);
        }
        return util;
    }

    public void putString(String str, String str2) {
        SharedPreferences.Editor editorEdit = dateStore.edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }

    public String getString(String str) {
        return dateStore.getString(str, null);
    }

    private void puBoolean(String str, boolean z) {
        SharedPreferences.Editor editorEdit = dateStore.edit();
        editorEdit.putBoolean(str, z);
        editorEdit.commit();
    }

    private boolean getBoolean(String str) {
        return dateStore.getBoolean(str, true);
    }

    public void setNetName(String str) {
        putString(this.NET_NAME, str);
    }

    public void setNetNamePass(String str, String str2) {
        putString(this.NET_NAME, str);
        putString(this.NET_PASS, str2);
    }

    public void setApModel(boolean z) {
        puBoolean(this.MODEL, z);
    }

    public void setK7(String str) {
        putString(this.K7_TAG, str);
    }

    public void setX4(String str) {
        putString(this.X4_TAG, str);
    }

    public String getK7() {
        return getString(this.K7_TAG);
    }

    public String getX4() {
        return getString(this.X4_TAG);
    }

    public String getNetName() {
        return getString(this.NET_NAME);
    }

    public String getLastAddress() {
        return getString(this.LAST_ADDRESS);
    }

    public void settLastAddress(String str) {
        putString(this.LAST_ADDRESS, str);
    }

    public String getNetPass() {
        return getString(this.NET_PASS);
    }

    public boolean isApModel() {
        return getBoolean(this.MODEL);
    }

    public boolean isSet() {
        return !TextUtils.isEmpty(getNetName());
    }
}
