package com.espressif.iot.esptouch.task;

import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface __IEsptouchTask {
    public static final boolean DEBUG = true;

    IEsptouchResult executeForResult() throws RuntimeException;

    List<IEsptouchResult> executeForResults(int expectTaskResultCount) throws RuntimeException;

    void interrupt();

    boolean isCancelled();

    void setEsptouchListener(IEsptouchListener esptouchListener);
}
