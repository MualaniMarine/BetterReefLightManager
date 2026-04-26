package com.nemo.caideng.util;

import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class TouchNetUtil {
    public static boolean is5G(int i) {
        return i > 4900 && i < 5900;
    }

    public static boolean isWifiConnected(WifiManager wifiManager) {
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        return (connectionInfo == null || connectionInfo.getNetworkId() == -1 || "<unknown ssid>".equals(connectionInfo.getSSID())) ? false : true;
    }

    public static byte[] getRawSsidBytes(WifiInfo wifiInfo) {
        try {
            Method method = wifiInfo.getClass().getMethod("getWifiSsid", new Class[0]);
            method.setAccessible(true);
            Object objInvoke = method.invoke(wifiInfo, new Object[0]);
            if (objInvoke == null) {
                return null;
            }
            Method method2 = objInvoke.getClass().getMethod("getOctets", new Class[0]);
            method2.setAccessible(true);
            return (byte[]) method2.invoke(objInvoke, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | NullPointerException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getRawSsidBytesOrElse(WifiInfo wifiInfo, byte[] bArr) {
        byte[] rawSsidBytes = getRawSsidBytes(wifiInfo);
        return rawSsidBytes != null ? rawSsidBytes : bArr;
    }

    public static String getSsidString(WifiInfo wifiInfo) {
        String ssid = wifiInfo.getSSID();
        return (ssid.startsWith("\"") && ssid.endsWith("\"")) ? ssid.substring(1, ssid.length() - 1) : ssid;
    }

    public static InetAddress getBroadcastAddress(WifiManager wifiManager) {
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo != null) {
            int i = (~dhcpInfo.netmask) | (dhcpInfo.ipAddress & dhcpInfo.netmask);
            byte[] bArr = new byte[4];
            for (int i2 = 0; i2 < 4; i2++) {
                bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
            }
            try {
                return InetAddress.getByAddress(bArr);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        try {
            return InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static InetAddress getAddress(int i) {
        try {
            return InetAddress.getByAddress(new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)});
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static InetAddress getAddress(boolean z) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress()) {
                        if (z && (inetAddressNextElement instanceof Inet4Address)) {
                            return inetAddressNextElement;
                        }
                        if (!z && (inetAddressNextElement instanceof Inet6Address)) {
                            return inetAddressNextElement;
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InetAddress getIPv4Address() {
        return getAddress(true);
    }

    public static InetAddress getIPv6Address() {
        return getAddress(false);
    }

    public static byte[] convertBssid2Bytes(String str) {
        String[] strArrSplit = str.split(":");
        if (strArrSplit.length != 6) {
            throw new IllegalArgumentException("Invalid bssid format");
        }
        byte[] bArr = new byte[strArrSplit.length];
        for (int i = 0; i < strArrSplit.length; i++) {
            bArr[i] = (byte) Integer.parseInt(strArrSplit[i], 16);
        }
        return bArr;
    }

    public static DatagramSocket createUdpSocket() {
        for (int i = 23233; i < 65535; i++) {
            try {
                return new DatagramSocket(i);
            } catch (SocketException unused) {
            }
        }
        return null;
    }
}
