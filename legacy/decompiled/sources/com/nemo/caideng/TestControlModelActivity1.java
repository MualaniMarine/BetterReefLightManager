package com.nemo.caideng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import com.nemo.caideng.databinding.ActivityTestControlModel1Binding;
import com.nemo.caideng.util.CommandUtil;
import com.nemo.caideng.widget.AllSeekNumView;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/* JADX INFO: loaded from: classes.dex */
public class TestControlModelActivity1 extends BaseActivity implements AllSeekNumView.ValueListener, View.OnClickListener {
    ActivityTestControlModel1Binding activityControlBinding;
    private int model;
    private Handler sendHandler;
    private HandlerThread sendHandlerThread;
    private int type;

    public static void startActivity(Activity activity, int i, int i2) {
        Intent intent = new Intent(activity, (Class<?>) TestControlModelActivity1.class);
        intent.putExtra("type", i);
        intent.putExtra("model", i2);
        activity.startActivity(intent);
    }

    @Override // com.nemo.caideng.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityTestControlModel1Binding activityTestControlModel1BindingInflate = ActivityTestControlModel1Binding.inflate(getLayoutInflater());
        this.activityControlBinding = activityTestControlModel1BindingInflate;
        setContentView(activityTestControlModel1BindingInflate.getRoot());
        HandlerThread handlerThread = new HandlerThread("send_thread");
        this.sendHandlerThread = handlerThread;
        handlerThread.start();
        this.sendHandler = new Handler(this.sendHandlerThread.getLooper());
        initData();
        intiView();
        initListener();
    }

    private void intiView() {
        this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
        changeViewEnable(true);
    }

    private void initListener() {
        this.activityControlBinding.btnSave.setOnClickListener(this);
        this.activityControlBinding.asnvSeek.setValueListener(this);
    }

    private void sendData(final byte[] bArr) {
        this.sendHandler.post(new Runnable() { // from class: com.nemo.caideng.-$$Lambda$TestControlModelActivity1$PGvIncB7dwRg3VzdBA8lTmQ2Znk
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                TestControlModelActivity1.lambda$sendData$0(bArr);
            }
        });
    }

    static /* synthetic */ void lambda$sendData$0(byte[] bArr) throws Throwable {
        DatagramSocket datagramSocket = null;
        try {
            try {
                DatagramSocket datagramSocket2 = new DatagramSocket();
                try {
                    datagramSocket2.setBroadcast(true);
                    for (int i = 0; i < 5; i++) {
                        datagramSocket2.send(new DatagramPacket(bArr, bArr.length, InetAddress.getByName("255.255.255.255"), 8266));
                    }
                    datagramSocket2.close();
                    datagramSocket2.disconnect();
                } catch (Exception e) {
                    e = e;
                    datagramSocket = datagramSocket2;
                    e.printStackTrace();
                    datagramSocket.close();
                    datagramSocket.disconnect();
                } catch (Throwable th) {
                    th = th;
                    datagramSocket = datagramSocket2;
                    datagramSocket.close();
                    datagramSocket.disconnect();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private void initData() {
        this.type = getIntent().getIntExtra("type", 0);
        this.model = getIntent().getIntExtra("model", 0);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != C0575R.id.btn_save) {
            return;
        }
        sendData(CommandUtil.handModelLuminance(this.activityControlBinding.asnvSeek.getValues()));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.sendHandler.removeCallbacksAndMessages(null);
        this.sendHandlerThread.quit();
        super.onDestroy();
    }

    private void changeViewEnable(boolean z) {
        this.activityControlBinding.btnSave.setEnabled(false);
        this.activityControlBinding.asnvSeek.setEnable(0);
        if (z) {
            this.activityControlBinding.btnSave.setEnabled(true);
            this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
        }
    }

    @Override // com.nemo.caideng.widget.AllSeekNumView.ValueListener
    public void onValueChange(byte[] bArr) {
        sendData(CommandUtil.handModelLuminance(bArr));
    }
}
