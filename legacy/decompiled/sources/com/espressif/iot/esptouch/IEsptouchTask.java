package com.espressif.iot.esptouch;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IEsptouchTask {
    public static final String ESPTOUCH_VERSION = "v1.0.0";

    IEsptouchResult executeForResult() throws RuntimeException;

    List<IEsptouchResult> executeForResults(int expectTaskResultCount) throws RuntimeException;

    void interrupt();

    boolean isCancelled();

    void setEsptouchListener(IEsptouchListener esptouchListener);

    void setPackageBroadcast(boolean broadcast);
}
