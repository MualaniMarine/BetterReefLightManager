package com.espressif.iot.esptouch.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes.dex */
public class TouchNetUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static InetAddress getLocalInetAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null || wifiManager.getConnectionInfo() == null) {
                return null;
            }
            int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
            if (ipAddress == 0) {
                return null;
            }
            return InetAddress.getByName(__formatString(ipAddress));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String __formatString(int value) {
        return (value & 255) + "." + ((value >> 8) & 255) + "." + ((value >> 16) & 255) + "." + ((value >> 24) & 255);
    }

    public static InetAddress parseInetAddr(byte[] inetAddrBytes, int offset, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(inetAddrBytes[offset + i] & 255);
            if (i != count - 1) {
                sb.append('.');
            }
        }
        try {
            return InetAddress.getByName(sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] parseBssid2bytes(String bssid) {
        String[] strArrSplit = bssid.split(":");
        byte[] bArr = new byte[strArrSplit.length];
        for (int i = 0; i < strArrSplit.length; i++) {
            bArr[i] = (byte) Integer.parseInt(strArrSplit[i], 16);
        }
        return bArr;
    }

    public static byte[] getOriginalSsidBytes(WifiInfo info) {
        try {
            Method method = info.getClass().getMethod("getWifiSsid", new Class[0]);
            method.setAccessible(true);
            Object objInvoke = method.invoke(info, new Object[0]);
            if (objInvoke == null) {
                return null;
            }
            Method method2 = objInvoke.getClass().getMethod("getOctets", new Class[0]);
            method2.setAccessible(true);
            return (byte[]) method2.invoke(objInvoke, new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }
}
