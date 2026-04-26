package com.nemo.caideng;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.nemo.caideng.util.CommandUtil;
import com.nemo.caideng.util.HexStringUtil;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: classes.dex */
public class ConnectThread extends Thread {
    private Handler handler;
    private InputStream inputStream;
    private String ipAddress;
    private OutputStream outputStream;
    private int port;
    private Socket socket;
    private int timeout = 60000;
    private Queue<byte[]> commandQueue = new ConcurrentLinkedQueue();

    public ConnectThread(String str, int i, Handler handler) {
        setName("ConnectThread");
        this.ipAddress = str;
        this.port = i;
        this.handler = handler;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            Thread.sleep(1000L);
            this.socket = new Socket(this.ipAddress, this.port);
            Log.i(Constant.TAG, "创建连接成功");
            BuglyLog.m19i(Constant.TAG, "创建连接成功");
            this.handler.obtainMessage(1, this.ipAddress).sendToTarget();
            this.inputStream = this.socket.getInputStream();
            this.outputStream = this.socket.getOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i = this.inputStream.read(bArr);
                if (i > 0) {
                    byte[] bArr2 = new byte[i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    String lowerCase = HexStringUtil.byteArrayToHex(bArr2).toLowerCase();
                    BuglyLog.m19i(Constant.TAG, "收到的回复" + lowerCase);
                    if (!TextUtils.equals(CommandUtil.MODEL_RESULT.toLowerCase(), lowerCase) && !this.commandQueue.isEmpty()) {
                        if (i > 214) {
                            if (lowerCase.startsWith(CommandUtil.RECEIVE_START_TAG.toLowerCase())) {
                                this.commandQueue.poll();
                                this.handler.obtainMessage(3, bArr2).sendToTarget();
                            } else {
                                this.handler.obtainMessage(5, this.commandQueue.poll()).sendToTarget();
                            }
                        } else {
                            this.handler.obtainMessage(5, this.commandQueue.poll()).sendToTarget();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e = e;
            e.printStackTrace();
            Log.i("cai_dengT连接", e.toString());
            BuglyLog.m19i(Constant.TAG, "创建连接异常IOException");
            CrashReport.postCatchedException(e);
            this.handler.obtainMessage(2, this.ipAddress).sendToTarget();
        } catch (InterruptedException e2) {
            e = e2;
            e.printStackTrace();
            Log.i("cai_dengT连接", e.toString());
            BuglyLog.m19i(Constant.TAG, "创建连接异常IOException");
            CrashReport.postCatchedException(e);
            this.handler.obtainMessage(2, this.ipAddress).sendToTarget();
        } catch (Exception e3) {
            BuglyLog.m19i(Constant.TAG, "创建连接异常");
            CrashReport.postCatchedException(e3);
            this.handler.obtainMessage(2, this.ipAddress).sendToTarget();
        }
    }

    public void sendData(byte[] bArr) {
        if (this.outputStream != null) {
            try {
                String lowerCase = HexStringUtil.byteArrayToHex(bArr).toLowerCase();
                Log.i("cai_dengT发出的指令", lowerCase);
                if (TextUtils.equals(CommandUtil.READ_ALL.toLowerCase(), lowerCase)) {
                    this.commandQueue.offer(bArr);
                }
                this.outputStream.write(bArr);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("cai_dengT发出指令失败", e.toString());
                BuglyLog.m19i(Constant.TAG, "发送数据异常IOException");
                CrashReport.postCatchedException(e);
                this.handler.obtainMessage(2, this.ipAddress).sendToTarget();
            } catch (Exception e2) {
                BuglyLog.m19i(Constant.TAG, "发送数据异常");
                CrashReport.postCatchedException(e2);
                this.handler.obtainMessage(2, this.ipAddress).sendToTarget();
            }
        }
    }

    public void close() {
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            if (this.outputStream != null) {
                this.outputStream.close();
            }
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
