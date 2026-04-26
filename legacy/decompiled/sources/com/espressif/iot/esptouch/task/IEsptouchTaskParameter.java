package com.espressif.iot.esptouch.task;

/* JADX INFO: loaded from: classes.dex */
public interface IEsptouchTaskParameter {
    int getEsptouchResultIpLen();

    int getEsptouchResultMacLen();

    int getEsptouchResultOneLen();

    int getEsptouchResultTotalLen();

    int getExpectTaskResultCount();

    long getIntervalDataCodeMillisecond();

    long getIntervalGuideCodeMillisecond();

    int getPortListening();

    String getTargetHostname();

    int getTargetPort();

    int getThresholdSucBroadcastCount();

    long getTimeoutDataCodeMillisecond();

    long getTimeoutGuideCodeMillisecond();

    long getTimeoutTotalCodeMillisecond();

    int getTotalRepeatTime();

    int getWaitUdpReceivingMillisecond();

    int getWaitUdpSendingMillisecond();

    int getWaitUdpTotalMillisecond();

    void setBroadcast(boolean broadcast);

    void setExpectTaskResultCount(int expectTaskResultCount);

    void setWaitUdpTotalMillisecond(int waitUdpTotalMillisecond);
}
