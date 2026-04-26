package com.espressif.iot.esptouch.task;

/* JADX INFO: loaded from: classes.dex */
public class EsptouchTaskParameter implements IEsptouchTaskParameter {
    private static int _datagramCount;
    private boolean mBroadcast = true;
    private long mIntervalGuideCodeMillisecond = 8;
    private long mIntervalDataCodeMillisecond = 8;
    private long mTimeoutGuideCodeMillisecond = 2000;
    private long mTimeoutDataCodeMillisecond = 4000;
    private int mTotalRepeatTime = 1;
    private int mEsptouchResultOneLen = 1;
    private int mEsptouchResultMacLen = 6;
    private int mEsptouchResultIpLen = 4;
    private int mEsptouchResultTotalLen = 11;
    private int mPortListening = 18266;
    private int mTargetPort = 7001;
    private int mWaitUdpReceivingMilliseond = 15000;
    private int mWaitUdpSendingMillisecond = 45000;
    private int mThresholdSucBroadcastCount = 1;
    private int mExpectTaskResultCount = 1;

    private static int __getNextDatagramCount() {
        int i = _datagramCount;
        _datagramCount = i + 1;
        return (i % 100) + 1;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public long getIntervalGuideCodeMillisecond() {
        return this.mIntervalGuideCodeMillisecond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public long getIntervalDataCodeMillisecond() {
        return this.mIntervalDataCodeMillisecond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public long getTimeoutGuideCodeMillisecond() {
        return this.mTimeoutGuideCodeMillisecond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public long getTimeoutDataCodeMillisecond() {
        return this.mTimeoutDataCodeMillisecond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public long getTimeoutTotalCodeMillisecond() {
        return this.mTimeoutGuideCodeMillisecond + this.mTimeoutDataCodeMillisecond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getTotalRepeatTime() {
        return this.mTotalRepeatTime;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getEsptouchResultOneLen() {
        return this.mEsptouchResultOneLen;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getEsptouchResultMacLen() {
        return this.mEsptouchResultMacLen;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getEsptouchResultIpLen() {
        return this.mEsptouchResultIpLen;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getEsptouchResultTotalLen() {
        return this.mEsptouchResultTotalLen;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getPortListening() {
        return this.mPortListening;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public String getTargetHostname() {
        if (this.mBroadcast) {
            return "255.255.255.255";
        }
        int i__getNextDatagramCount = __getNextDatagramCount();
        return "234." + i__getNextDatagramCount + "." + i__getNextDatagramCount + "." + i__getNextDatagramCount;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getTargetPort() {
        return this.mTargetPort;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getWaitUdpReceivingMillisecond() {
        return this.mWaitUdpReceivingMilliseond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getWaitUdpSendingMillisecond() {
        return this.mWaitUdpSendingMillisecond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getWaitUdpTotalMillisecond() {
        return this.mWaitUdpReceivingMilliseond + this.mWaitUdpSendingMillisecond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public void setWaitUdpTotalMillisecond(int waitUdpTotalMillisecond) {
        if (waitUdpTotalMillisecond < ((long) this.mWaitUdpReceivingMilliseond) + getTimeoutTotalCodeMillisecond()) {
            throw new IllegalArgumentException("waitUdpTotalMillisecod is invalid, it is less than mWaitUdpReceivingMilliseond + getTimeoutTotalCodeMillisecond()");
        }
        this.mWaitUdpSendingMillisecond = waitUdpTotalMillisecond - this.mWaitUdpReceivingMilliseond;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getThresholdSucBroadcastCount() {
        return this.mThresholdSucBroadcastCount;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public int getExpectTaskResultCount() {
        return this.mExpectTaskResultCount;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public void setExpectTaskResultCount(int expectTaskResultCount) {
        this.mExpectTaskResultCount = expectTaskResultCount;
    }

    @Override // com.espressif.iot.esptouch.task.IEsptouchTaskParameter
    public void setBroadcast(boolean broadcast) {
        this.mBroadcast = broadcast;
    }
}
