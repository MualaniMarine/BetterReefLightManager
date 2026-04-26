package com.espressif.iot.esptouch.udp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class UDPSocketServer {
    private static final String TAG = "UDPSocketServer";
    private Context mContext;
    private volatile boolean mIsClosed;
    private WifiManager.MulticastLock mLock;
    private DatagramSocket mServerSocket;

    public UDPSocketServer(int port, int socketTimeout, Context context) {
        this.mContext = context;
        try {
            DatagramSocket datagramSocket = new DatagramSocket((SocketAddress) null);
            this.mServerSocket = datagramSocket;
            datagramSocket.setReuseAddress(true);
            this.mServerSocket.bind(new InetSocketAddress(port));
            this.mServerSocket.setSoTimeout(socketTimeout);
        } catch (IOException e) {
            Log.w(TAG, "IOException");
            e.printStackTrace();
        }
        this.mIsClosed = false;
        this.mLock = ((WifiManager) this.mContext.getApplicationContext().getSystemService("wifi")).createMulticastLock("test wifi");
        Log.d(TAG, "mServerSocket is created, socket read timeout: " + socketTimeout + ", port: " + port);
    }

    private synchronized void acquireLock() {
        if (this.mLock != null && !this.mLock.isHeld()) {
            this.mLock.acquire();
        }
    }

    private synchronized void releaseLock() {
        if (this.mLock != null) {
            if (this.mLock.isHeld()) {
                try {
                    this.mLock.release();
                } catch (Throwable unused) {
                }
            }
        }
    }

    public boolean setSoTimeout(int timeout) {
        try {
            this.mServerSocket.setSoTimeout(timeout);
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte receiveOneByte() {
        Log.d(TAG, "receiveOneByte() entrance");
        try {
            acquireLock();
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1], 1);
            this.mServerSocket.receive(datagramPacket);
            Log.d(TAG, "receive: " + ((int) datagramPacket.getData()[0]));
            return datagramPacket.getData()[0];
        } catch (Exception e) {
            e.printStackTrace();
            return (byte) -1;
        }
    }

    public byte[] receiveSpecLenBytes(int len) {
        Log.d(TAG, "receiveSpecLenBytes() entrance: len = " + len);
        try {
            acquireLock();
            DatagramPacket datagramPacket = new DatagramPacket(new byte[64], 64);
            this.mServerSocket.receive(datagramPacket);
            byte[] bArrCopyOf = Arrays.copyOf(datagramPacket.getData(), datagramPacket.getLength());
            Log.d(TAG, "received len : " + bArrCopyOf.length);
            for (int i = 0; i < bArrCopyOf.length; i++) {
                Log.w(TAG, "recDatas[" + i + "]:" + ((int) bArrCopyOf[i]));
            }
            Log.w(TAG, "receiveSpecLenBytes: " + new String(bArrCopyOf));
            if (bArrCopyOf.length == len) {
                return bArrCopyOf;
            }
            Log.w(TAG, "received len is different from specific len, return null");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void interrupt() {
        Log.i(TAG, "USPSocketServer is interrupt");
        close();
    }

    public synchronized void close() {
        if (!this.mIsClosed) {
            Log.w(TAG, "mServerSocket is closed");
            this.mServerSocket.close();
            releaseLock();
            this.mIsClosed = true;
        }
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
