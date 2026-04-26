package com.espressif.iot.esptouch.task;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import com.espressif.iot.esptouch.EsptouchResult;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.protocol.EsptouchGenerator;
import com.espressif.iot.esptouch.protocol.TouchData;
import com.espressif.iot.esptouch.security.ITouchEncryptor;
import com.espressif.iot.esptouch.udp.UDPSocketClient;
import com.espressif.iot.esptouch.udp.UDPSocketServer;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.TouchNetUtil;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class __EsptouchTask implements __IEsptouchTask {
    private static final int ONE_DATA_LEN = 3;
    private static final String TAG = "__EsptouchTask";
    private final byte[] mApBssid;
    private final byte[] mApPassword;
    private final byte[] mApSsid;
    private volatile Map<String, Integer> mBssidTaskSucCountMap;
    private final Context mContext;
    private final ITouchEncryptor mEncryptor;
    private IEsptouchListener mEsptouchListener;
    private final List<IEsptouchResult> mEsptouchResultList;
    private AtomicBoolean mIsCancelled;
    private IEsptouchTaskParameter mParameter;
    private final UDPSocketClient mSocketClient;
    private final UDPSocketServer mSocketServer;
    private Thread mTask;
    private volatile boolean mIsSuc = false;
    private volatile boolean mIsInterrupt = false;
    private volatile boolean mIsExecuted = false;

    public __EsptouchTask(Context context, TouchData apSsid, TouchData apBssid, TouchData apPassword, ITouchEncryptor encryptor, IEsptouchTaskParameter parameter) {
        Log.i(TAG, "Welcome Esptouch v1.0.0");
        this.mContext = context;
        this.mEncryptor = encryptor;
        this.mApSsid = apSsid.getData();
        this.mApPassword = apPassword.getData();
        this.mApBssid = apBssid.getData();
        this.mIsCancelled = new AtomicBoolean(false);
        this.mSocketClient = new UDPSocketClient();
        this.mParameter = parameter;
        this.mSocketServer = new UDPSocketServer(parameter.getPortListening(), this.mParameter.getWaitUdpTotalMillisecond(), context);
        this.mEsptouchResultList = new ArrayList();
        this.mBssidTaskSucCountMap = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void __putEsptouchResult(boolean isSuc, String bssid, InetAddress inetAddress) {
        synchronized (this.mEsptouchResultList) {
            Integer num = this.mBssidTaskSucCountMap.get(bssid);
            boolean z = false;
            if (num == null) {
                num = 0;
            }
            Integer numValueOf = Integer.valueOf(num.intValue() + 1);
            Log.d(TAG, "__putEsptouchResult(): count = " + numValueOf);
            this.mBssidTaskSucCountMap.put(bssid, numValueOf);
            if (!(numValueOf.intValue() >= this.mParameter.getThresholdSucBroadcastCount())) {
                Log.d(TAG, "__putEsptouchResult(): count = " + numValueOf + ", isn't enough");
                return;
            }
            Iterator<IEsptouchResult> it = this.mEsptouchResultList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getBssid().equals(bssid)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                Log.d(TAG, "__putEsptouchResult(): put one more result bssid = " + bssid + " , address = " + inetAddress);
                EsptouchResult esptouchResult = new EsptouchResult(isSuc, bssid, inetAddress);
                this.mEsptouchResultList.add(esptouchResult);
                if (this.mEsptouchListener != null) {
                    this.mEsptouchListener.onEsptouchResultAdded(esptouchResult);
                }
            }
        }
    }

    private List<IEsptouchResult> __getEsptouchResultList() {
        List<IEsptouchResult> list;
        synchronized (this.mEsptouchResultList) {
            if (this.mEsptouchResultList.isEmpty()) {
                EsptouchResult esptouchResult = new EsptouchResult(false, null, null);
                esptouchResult.setIsCancelled(this.mIsCancelled.get());
                this.mEsptouchResultList.add(esptouchResult);
            }
            list = this.mEsptouchResultList;
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void __interrupt() {
        if (!this.mIsInterrupt) {
            this.mIsInterrupt = true;
            this.mSocketClient.interrupt();
            this.mSocketServer.interrupt();
            if (this.mTask != null) {
                this.mTask.interrupt();
                this.mTask = null;
            }
        }
    }

    @Override // com.espressif.iot.esptouch.task.__IEsptouchTask
    public void interrupt() {
        Log.d(TAG, "interrupt()");
        this.mIsCancelled.set(true);
        __interrupt();
    }

    private void __listenAsyn(final int expectDataLen) {
        Thread thread = new Thread() { // from class: com.espressif.iot.esptouch.task.__EsptouchTask.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.d(__EsptouchTask.TAG, "__listenAsyn() start");
                long jCurrentTimeMillis = System.currentTimeMillis();
                byte length = (byte) (__EsptouchTask.this.mApSsid.length + __EsptouchTask.this.mApPassword.length + 9);
                Log.i(__EsptouchTask.TAG, "expectOneByte: " + ((int) length));
                while (true) {
                    if (__EsptouchTask.this.mEsptouchResultList.size() >= __EsptouchTask.this.mParameter.getExpectTaskResultCount() || __EsptouchTask.this.mIsInterrupt) {
                        break;
                    }
                    byte[] bArrReceiveSpecLenBytes = __EsptouchTask.this.mSocketServer.receiveSpecLenBytes(expectDataLen);
                    if ((bArrReceiveSpecLenBytes != null ? bArrReceiveSpecLenBytes[0] : (byte) -1) == length) {
                        Log.i(__EsptouchTask.TAG, "receive correct broadcast");
                        int waitUdpTotalMillisecond = (int) (((long) __EsptouchTask.this.mParameter.getWaitUdpTotalMillisecond()) - (System.currentTimeMillis() - jCurrentTimeMillis));
                        if (waitUdpTotalMillisecond < 0) {
                            Log.i(__EsptouchTask.TAG, "esptouch timeout");
                            break;
                        }
                        Log.i(__EsptouchTask.TAG, "mSocketServer's new timeout is " + waitUdpTotalMillisecond + " milliseconds");
                        __EsptouchTask.this.mSocketServer.setSoTimeout(waitUdpTotalMillisecond);
                        Log.i(__EsptouchTask.TAG, "receive correct broadcast");
                        if (bArrReceiveSpecLenBytes != null) {
                            __EsptouchTask.this.__putEsptouchResult(true, ByteUtil.parseBssid(bArrReceiveSpecLenBytes, __EsptouchTask.this.mParameter.getEsptouchResultOneLen(), __EsptouchTask.this.mParameter.getEsptouchResultMacLen()), TouchNetUtil.parseInetAddr(bArrReceiveSpecLenBytes, __EsptouchTask.this.mParameter.getEsptouchResultOneLen() + __EsptouchTask.this.mParameter.getEsptouchResultMacLen(), __EsptouchTask.this.mParameter.getEsptouchResultIpLen()));
                        }
                    } else {
                        Log.i(__EsptouchTask.TAG, "receive rubbish message, just ignore");
                    }
                }
                __EsptouchTask __esptouchtask = __EsptouchTask.this;
                __esptouchtask.mIsSuc = __esptouchtask.mEsptouchResultList.size() >= __EsptouchTask.this.mParameter.getExpectTaskResultCount();
                __EsptouchTask.this.__interrupt();
                Log.d(__EsptouchTask.TAG, "__listenAsyn() finish");
            }
        };
        this.mTask = thread;
        thread.start();
    }

    private boolean __execute(IEsptouchGenerator generator) {
        byte[][] bArr;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long timeoutTotalCodeMillisecond = jCurrentTimeMillis - this.mParameter.getTimeoutTotalCodeMillisecond();
        byte[][] gCBytes2 = generator.getGCBytes2();
        byte[][] dCBytes2 = generator.getDCBytes2();
        long jCurrentTimeMillis2 = jCurrentTimeMillis;
        int length = 0;
        while (!this.mIsInterrupt) {
            if (jCurrentTimeMillis2 - timeoutTotalCodeMillisecond >= this.mParameter.getTimeoutTotalCodeMillisecond()) {
                Log.d(TAG, "send gc code ");
                while (!this.mIsInterrupt && System.currentTimeMillis() - jCurrentTimeMillis2 < this.mParameter.getTimeoutGuideCodeMillisecond()) {
                    this.mSocketClient.sendData(gCBytes2, this.mParameter.getTargetHostname(), this.mParameter.getTargetPort(), this.mParameter.getIntervalGuideCodeMillisecond());
                    if (System.currentTimeMillis() - jCurrentTimeMillis > this.mParameter.getWaitUdpSendingMillisecond()) {
                        break;
                    }
                }
                timeoutTotalCodeMillisecond = jCurrentTimeMillis2;
                bArr = dCBytes2;
            } else {
                bArr = dCBytes2;
                this.mSocketClient.sendData(dCBytes2, length, 3, this.mParameter.getTargetHostname(), this.mParameter.getTargetPort(), this.mParameter.getIntervalDataCodeMillisecond());
                length = (length + 3) % bArr.length;
            }
            jCurrentTimeMillis2 = System.currentTimeMillis();
            if (jCurrentTimeMillis2 - jCurrentTimeMillis > this.mParameter.getWaitUdpSendingMillisecond()) {
                break;
            }
            dCBytes2 = bArr;
        }
        return this.mIsSuc;
    }

    private void __checkTaskValid() {
        if (this.mIsExecuted) {
            throw new IllegalStateException("the Esptouch task could be executed only once");
        }
        this.mIsExecuted = true;
    }

    @Override // com.espressif.iot.esptouch.task.__IEsptouchTask
    public IEsptouchResult executeForResult() throws RuntimeException {
        return executeForResults(1).get(0);
    }

    @Override // com.espressif.iot.esptouch.task.__IEsptouchTask
    public boolean isCancelled() {
        return this.mIsCancelled.get();
    }

    @Override // com.espressif.iot.esptouch.task.__IEsptouchTask
    public List<IEsptouchResult> executeForResults(int expectTaskResultCount) throws RuntimeException {
        __checkTaskValid();
        this.mParameter.setExpectTaskResultCount(expectTaskResultCount);
        Log.d(TAG, "execute()");
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Don't call the esptouch Task at Main(UI) thread directly.");
        }
        InetAddress localInetAddress = TouchNetUtil.getLocalInetAddress(this.mContext);
        Log.i(TAG, "localInetAddress: " + localInetAddress);
        EsptouchGenerator esptouchGenerator = new EsptouchGenerator(this.mApSsid, this.mApBssid, this.mApPassword, localInetAddress, this.mEncryptor);
        __listenAsyn(this.mParameter.getEsptouchResultTotalLen());
        for (int i = 0; i < this.mParameter.getTotalRepeatTime(); i++) {
            if (__execute(esptouchGenerator)) {
                return __getEsptouchResultList();
            }
        }
        if (!this.mIsInterrupt) {
            try {
                Thread.sleep(this.mParameter.getWaitUdpReceivingMillisecond());
                __interrupt();
            } catch (InterruptedException unused) {
                if (this.mIsSuc) {
                    return __getEsptouchResultList();
                }
                __interrupt();
                return __getEsptouchResultList();
            }
        }
        return __getEsptouchResultList();
    }

    @Override // com.espressif.iot.esptouch.task.__IEsptouchTask
    public void setEsptouchListener(IEsptouchListener esptouchListener) {
        this.mEsptouchListener = esptouchListener;
    }
}
