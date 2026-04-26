package com.espressif.iot.esptouch.udp;

import android.util.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes.dex */
public class UDPSocketClient {
    private static final String TAG = "UDPSocketClient";
    private volatile boolean mIsClosed;
    private volatile boolean mIsStop;
    private DatagramSocket mSocket;

    public UDPSocketClient() {
        try {
            this.mSocket = new DatagramSocket();
            this.mIsStop = false;
            this.mIsClosed = false;
        } catch (SocketException e) {
            Log.w(TAG, "SocketException");
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public void interrupt() {
        Log.i(TAG, "USPSocketClient is interrupt");
        this.mIsStop = true;
    }

    public synchronized void close() {
        if (!this.mIsClosed) {
            this.mSocket.close();
            this.mIsClosed = true;
        }
    }

    public void sendData(byte[][] data, String targetHostName, int targetPort, long interval) {
        sendData(data, 0, data.length, targetHostName, targetPort, interval);
    }

    public void sendData(byte[][] data, int offset, int count, String targetHostName, int targetPort, long interval) {
        if (data == null || data.length <= 0) {
            Log.w(TAG, "sendData(): data == null or length <= 0");
            return;
        }
        for (int i = offset; !this.mIsStop && i < offset + count; i++) {
            if (data[i].length != 0) {
                try {
                    this.mSocket.send(new DatagramPacket(data[i], data[i].length, InetAddress.getByName(targetHostName), targetPort));
                } catch (UnknownHostException e) {
                    Log.w(TAG, "sendData(): UnknownHostException");
                    e.printStackTrace();
                    this.mIsStop = true;
                } catch (IOException unused) {
                    Log.w(TAG, "sendData(): IOException, but just ignore it");
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    Log.w(TAG, "sendData is Interrupted");
                    this.mIsStop = true;
                }
            }
        }
        if (this.mIsStop) {
            close();
        }
    }
}
