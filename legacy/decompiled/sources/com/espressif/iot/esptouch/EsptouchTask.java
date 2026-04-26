package com.espressif.iot.esptouch;

import android.content.Context;
import android.text.TextUtils;
import com.espressif.iot.esptouch.protocol.TouchData;
import com.espressif.iot.esptouch.security.ITouchEncryptor;
import com.espressif.iot.esptouch.task.EsptouchTaskParameter;
import com.espressif.iot.esptouch.task.__EsptouchTask;
import com.espressif.iot.esptouch.util.TouchNetUtil;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EsptouchTask implements IEsptouchTask {
    private __EsptouchTask _mEsptouchTask;
    private EsptouchTaskParameter _mParameter;

    public EsptouchTask(String apSsid, String apBssid, String apPassword, Context context) {
        this(apSsid, apBssid, apPassword, (ITouchEncryptor) null, context);
    }

    public EsptouchTask(byte[] apSsid, byte[] apBssid, byte[] apPassword, Context context) {
        this(apSsid, apBssid, apPassword, (ITouchEncryptor) null, context);
    }

    private EsptouchTask(String apSsid, String apBssid, String apPassword, ITouchEncryptor encryptor, Context context) {
        if (TextUtils.isEmpty(apSsid)) {
            throw new NullPointerException("SSID can't be empty");
        }
        if (TextUtils.isEmpty(apBssid)) {
            throw new NullPointerException("BSSID can't be empty");
        }
        apPassword = apPassword == null ? "" : apPassword;
        TouchData touchData = new TouchData(apSsid);
        TouchData touchData2 = new TouchData(TouchNetUtil.parseBssid2bytes(apBssid));
        if (touchData2.getData().length != 6) {
            throw new IllegalArgumentException("Bssid format must be aa:bb:cc:dd:ee:ff");
        }
        init(context, touchData, touchData2, new TouchData(apPassword), encryptor);
    }

    private EsptouchTask(byte[] apSsid, byte[] apBssid, byte[] apPassword, ITouchEncryptor encryptor, Context context) {
        if (apSsid == null || apSsid.length == 0) {
            throw new NullPointerException("SSID can't be empty");
        }
        if (apBssid == null || apBssid.length != 6) {
            throw new NullPointerException("BSSID is empty or length is not 6");
        }
        init(context, new TouchData(apSsid), new TouchData(apBssid), new TouchData(apPassword == null ? new byte[0] : apPassword), encryptor);
    }

    private void init(Context context, TouchData ssid, TouchData bssid, TouchData password, ITouchEncryptor encryptor) {
        EsptouchTaskParameter esptouchTaskParameter = new EsptouchTaskParameter();
        this._mParameter = esptouchTaskParameter;
        this._mEsptouchTask = new __EsptouchTask(context, ssid, bssid, password, encryptor, esptouchTaskParameter);
    }

    @Override // com.espressif.iot.esptouch.IEsptouchTask
    public void interrupt() {
        this._mEsptouchTask.interrupt();
    }

    @Override // com.espressif.iot.esptouch.IEsptouchTask
    public IEsptouchResult executeForResult() throws RuntimeException {
        return this._mEsptouchTask.executeForResult();
    }

    @Override // com.espressif.iot.esptouch.IEsptouchTask
    public boolean isCancelled() {
        return this._mEsptouchTask.isCancelled();
    }

    @Override // com.espressif.iot.esptouch.IEsptouchTask
    public List<IEsptouchResult> executeForResults(int expectTaskResultCount) throws RuntimeException {
        if (expectTaskResultCount <= 0) {
            expectTaskResultCount = Integer.MAX_VALUE;
        }
        return this._mEsptouchTask.executeForResults(expectTaskResultCount);
    }

    @Override // com.espressif.iot.esptouch.IEsptouchTask
    public void setEsptouchListener(IEsptouchListener esptouchListener) {
        this._mEsptouchTask.setEsptouchListener(esptouchListener);
    }

    @Override // com.espressif.iot.esptouch.IEsptouchTask
    public void setPackageBroadcast(boolean broadcast) {
        this._mParameter.setBroadcast(broadcast);
    }
}
