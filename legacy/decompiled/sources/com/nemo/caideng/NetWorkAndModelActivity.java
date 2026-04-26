package com.nemo.caideng;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.TouchNetUtil;
import com.google.gson.Gson;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.nemo.caideng.databinding.ActivityNetworkAndModelBinding;
import com.nemo.caideng.model.NetWorkInfo;
import com.nemo.caideng.util.CommandUtil;
import com.nemo.caideng.util.GpsUtil;
import com.nemo.caideng.util.StringUtils;
import com.nemo.caideng.util.ToastMaker;
import com.nemo.caideng.util.WifiUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: classes.dex */
public class NetWorkAndModelActivity extends BaseActivity implements Observer<String>, RadioGroup.OnCheckedChangeListener, View.OnClickListener, OnPermissionCallback {
    private static final int PORT = 8266;
    ActivityNetworkAndModelBinding activityNetworkAndModelBinding;
    private boolean changeNet;
    private ConnectThread connectThread;
    private NetWorkInfo connectingNet;
    private AlertDialog mResultDialog;
    private EsptouchAsyncTask4 mTask;
    private Handler sendHandler;
    private HandlerThread sendHandlerThread;
    StateResult stateResult;
    private boolean hiddenPass = true;
    private Queue<NetWorkInfo> netWorkInfos = new ConcurrentLinkedQueue();
    private List<NetWorkInfo> k7ConfigInfos = new ArrayList();
    private List<NetWorkInfo> x4ConfigInfos = new ArrayList();
    private String[] permissions = {Permission.ACCESS_FINE_LOCATION};
    private Handler handler = new HandlerC05721();
    Handler timeOutHandler = new Handler(Looper.getMainLooper());
    boolean settingNet = false;

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, (Class<?>) NetWorkAndModelActivity.class));
    }

    /* JADX INFO: renamed from: com.nemo.caideng.NetWorkAndModelActivity$1 */
    class HandlerC05721 extends Handler {
        HandlerC05721() {
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                if (TextUtils.equals((String) message.obj, NetWorkAndModelActivity.this.connectingNet.getServerAddress())) {
                    NetWorkAndModelActivity.this.sendData(CommandUtil.allRead());
                    return;
                }
                return;
            }
            if (i == 2) {
                if (NetWorkAndModelActivity.this.isFinishing() || !TextUtils.equals((String) message.obj, NetWorkAndModelActivity.this.connectingNet.getServerAddress())) {
                    return;
                }
                NetWorkAndModelActivity.this.disSocket();
                NetWorkAndModelActivity.this.activityNetworkAndModelBinding.testResult.append("\n" + NetWorkAndModelActivity.this.getString(C0575R.string.set_success_read_fail));
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    NetWorkAndModelActivity.this.sendData((byte[]) message.obj);
                    return;
                } else {
                    NetWorkAndModelActivity.this.connectingNet = (NetWorkInfo) message.obj;
                    NetWorkAndModelActivity.this.disSocket();
                    NetWorkAndModelActivity.this.connectThread = new ConnectThread(NetWorkAndModelActivity.this.connectingNet.getServerAddress(), NetWorkAndModelActivity.PORT, NetWorkAndModelActivity.this.handler);
                    NetWorkAndModelActivity.this.connectThread.start();
                    return;
                }
            }
            byte[] bArr = (byte[]) message.obj;
            byte[] bArr2 = new byte[11];
            if (bArr.length <= 214) {
                NetWorkAndModelActivity.this.showProgress(false);
                NetWorkAndModelActivity netWorkAndModelActivity = NetWorkAndModelActivity.this;
                netWorkAndModelActivity.mResultDialog = new AlertDialog.Builder(netWorkAndModelActivity).setMessage(NetWorkAndModelActivity.this.getString(C0575R.string.set_success_read_num_fail2)).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
                NetWorkAndModelActivity.this.mResultDialog.setCanceledOnTouchOutside(false);
                return;
            }
            System.arraycopy(bArr, 204, bArr2, 0, 11);
            String str = "";
            for (int i2 = 0; i2 < 11; i2++) {
                str = str + ((char) bArr2[i2]);
            }
            NetWorkAndModelActivity.this.connectingNet.setSsidName(str);
            if (str.toLowerCase().startsWith("k7_")) {
                NetWorkAndModelActivity.this.k7ConfigInfos.add(NetWorkAndModelActivity.this.connectingNet);
            }
            if (str.toLowerCase().startsWith("k7m")) {
                NetWorkAndModelActivity.this.x4ConfigInfos.add(NetWorkAndModelActivity.this.connectingNet);
            }
            NetWorkInfo netWorkInfo = (NetWorkInfo) NetWorkAndModelActivity.this.netWorkInfos.poll();
            if (netWorkInfo != null) {
                NetWorkAndModelActivity.this.handler.obtainMessage(4, netWorkInfo).sendToTarget();
                return;
            }
            ArrayList arrayList = new ArrayList(NetWorkAndModelActivity.this.k7ConfigInfos.size() + NetWorkAndModelActivity.this.x4ConfigInfos.size());
            for (NetWorkInfo netWorkInfo2 : NetWorkAndModelActivity.this.k7ConfigInfos) {
                arrayList.add(NetWorkAndModelActivity.this.getString(C0575R.string.esptouch1_configure_result_success_item, new Object[]{netWorkInfo2.getSsidName(), netWorkInfo2.getServerAddress()}));
            }
            for (NetWorkInfo netWorkInfo3 : NetWorkAndModelActivity.this.x4ConfigInfos) {
                arrayList.add(NetWorkAndModelActivity.this.getString(C0575R.string.esptouch1_configure_result_success_item, new Object[]{netWorkInfo3.getSsidName(), netWorkInfo3.getServerAddress()}));
            }
            BaseApplication.getInstance().getDataStore().setK7(null);
            BaseApplication.getInstance().getDataStore().setX4(null);
            Gson gson = new Gson();
            BaseApplication.getInstance().getDataStore().setK7(gson.toJson(NetWorkAndModelActivity.this.k7ConfigInfos));
            BaseApplication.getInstance().getDataStore().setX4(gson.toJson(NetWorkAndModelActivity.this.x4ConfigInfos));
            BaseApplication.getInstance().getDataStore().setNetNamePass(NetWorkAndModelActivity.this.stateResult.ssid, NetWorkAndModelActivity.this.activityNetworkAndModelBinding.editRoutePass.getText().toString());
            BaseApplication.getInstance().getDataStore().setApModel(false);
            NetWorkInfo netWorkInfo4 = NetWorkAndModelActivity.this.k7ConfigInfos.size() > 0 ? (NetWorkInfo) NetWorkAndModelActivity.this.k7ConfigInfos.get(0) : NetWorkAndModelActivity.this.x4ConfigInfos.size() > 0 ? (NetWorkInfo) NetWorkAndModelActivity.this.x4ConfigInfos.get(0) : null;
            if (netWorkInfo4 != null) {
                BaseApplication.getInstance().getDataStore().settLastAddress(netWorkInfo4.getServerAddress());
            }
            NetWorkAndModelActivity.this.showProgress(false);
            int size = arrayList.size();
            CharSequence[] charSequenceArr = new CharSequence[size];
            if (size == 0) {
                NetWorkAndModelActivity netWorkAndModelActivity2 = NetWorkAndModelActivity.this;
                netWorkAndModelActivity2.mResultDialog = new AlertDialog.Builder(netWorkAndModelActivity2).setMessage(NetWorkAndModelActivity.this.getString(C0575R.string.set_success_read_num_fail2)).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
            } else {
                NetWorkAndModelActivity netWorkAndModelActivity3 = NetWorkAndModelActivity.this;
                netWorkAndModelActivity3.mResultDialog = new AlertDialog.Builder(netWorkAndModelActivity3).setTitle(C0575R.string.esptouch1_configure_result_success).setItems((CharSequence[]) arrayList.toArray(charSequenceArr), (DialogInterface.OnClickListener) null).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$NetWorkAndModelActivity$1$UZUX4sZIBdWYuoBT_pM9K118EOg
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        this.f$0.lambda$dispatchMessage$0$NetWorkAndModelActivity$1(dialogInterface, i3);
                    }
                }).show();
            }
            NetWorkAndModelActivity.this.mResultDialog.setCanceledOnTouchOutside(false);
        }

        public /* synthetic */ void lambda$dispatchMessage$0$NetWorkAndModelActivity$1(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            NetWorkAndModelActivity.this.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disSocket() {
        ConnectThread connectThread = this.connectThread;
        if (connectThread != null) {
            connectThread.close();
        }
        this.connectThread = null;
    }

    @Override // com.nemo.caideng.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityNetworkAndModelBinding activityNetworkAndModelBindingInflate = ActivityNetworkAndModelBinding.inflate(getLayoutInflater());
        this.activityNetworkAndModelBinding = activityNetworkAndModelBindingInflate;
        setContentView(activityNetworkAndModelBindingInflate.getRoot());
        HandlerThread handlerThread = new HandlerThread("send_thread");
        this.sendHandlerThread = handlerThread;
        handlerThread.start();
        this.sendHandler = new Handler(this.sendHandlerThread.getLooper());
        initView();
        BaseApplication.getInstance().observeBroadcast(this, this);
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(String str) {
        this.stateResult = check();
        updateView();
        EsptouchAsyncTask4 esptouchAsyncTask4 = this.mTask;
        if (esptouchAsyncTask4 != null) {
            esptouchAsyncTask4.cancelEsptouch();
            this.mTask = null;
            new AlertDialog.Builder(this).setMessage(C0575R.string.esptouch1_configure_wifi_change_message).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show();
        }
    }

    private void updateView() {
        if (this.stateResult != null) {
            this.activityNetworkAndModelBinding.tvRouteName.setText(this.stateResult.ssid);
            this.activityNetworkAndModelBinding.tvRemarkInfo.setText(this.stateResult.message);
            this.activityNetworkAndModelBinding.btnSave.setEnabled(this.stateResult.wifiConnected);
            if (this.stateResult.wifiConnected) {
                if (this.activityNetworkAndModelBinding.rgGroup.getCheckedRadioButtonId() == C0575R.id.rb_ap_net) {
                    if (BaseApplication.getInstance().getDataStore().isSet() && !TextUtils.equals(BaseApplication.getInstance().getDataStore().getNetName(), this.stateResult.ssid)) {
                        this.activityNetworkAndModelBinding.tvRemarkInfo.setText(getString(C0575R.string.ap_cover, new Object[]{BaseApplication.getInstance().getDataStore().getNetName()}));
                    }
                } else if (BaseApplication.getInstance().getDataStore().isSet() && !TextUtils.equals(BaseApplication.getInstance().getDataStore().getNetName(), this.stateResult.ssid)) {
                    this.activityNetworkAndModelBinding.editRoutePass.setText("");
                    this.activityNetworkAndModelBinding.tvRemarkInfo.setText(getString(C0575R.string.route_cover, new Object[]{BaseApplication.getInstance().getDataStore().getNetName()}));
                }
                String lastAddress = BaseApplication.getInstance().getDataStore().getLastAddress();
                if (TextUtils.isEmpty(lastAddress)) {
                    this.activityNetworkAndModelBinding.editIp.setText(this.stateResult.address.getHostAddress().substring(0, this.stateResult.address.getHostAddress().lastIndexOf(".") + 1));
                } else if (TextUtils.isEmpty(this.activityNetworkAndModelBinding.editIp.getText())) {
                    this.activityNetworkAndModelBinding.editIp.setText(lastAddress);
                }
            }
        }
    }

    private void initView() {
        this.activityNetworkAndModelBinding.rgGroup.setOnCheckedChangeListener(this);
        this.activityNetworkAndModelBinding.btnSave.setOnClickListener(this);
        this.activityNetworkAndModelBinding.ivShowHidePass.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$NetWorkAndModelActivity$g__jYldcaO6E1lWpS-4_A1oyveo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$0$NetWorkAndModelActivity(view);
            }
        });
        this.activityNetworkAndModelBinding.btnChangeNet.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$NetWorkAndModelActivity$WOqQaI4TwVXO243ctbu-sjunTo8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$1$NetWorkAndModelActivity(view);
            }
        });
        if (BaseApplication.getInstance().getDataStore().isApModel()) {
            this.activityNetworkAndModelBinding.rgGroup.check(C0575R.id.rb_ap_net);
        } else {
            this.activityNetworkAndModelBinding.rgGroup.check(C0575R.id.rb_lan_net);
        }
        this.activityNetworkAndModelBinding.cancelButton.setOnClickListener(this);
        this.activityNetworkAndModelBinding.tvRemarkInfo.setMovementMethod(LinkMovementMethod.getInstance());
        this.activityNetworkAndModelBinding.tvIssue.setOnClickListener(this);
        this.activityNetworkAndModelBinding.editRoutePass.addTextChangedListener(new TextWatcher() { // from class: com.nemo.caideng.NetWorkAndModelActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    NetWorkAndModelActivity.this.activityNetworkAndModelBinding.editIp.setEnabled(true);
                } else {
                    NetWorkAndModelActivity.this.activityNetworkAndModelBinding.editIp.setEnabled(false);
                }
            }
        });
        this.activityNetworkAndModelBinding.editIp.addTextChangedListener(new TextWatcher() { // from class: com.nemo.caideng.NetWorkAndModelActivity.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable) && StringUtils.ipVerify(editable.toString())) {
                    NetWorkAndModelActivity.this.activityNetworkAndModelBinding.editRoutePass.setEnabled(false);
                } else {
                    NetWorkAndModelActivity.this.activityNetworkAndModelBinding.editRoutePass.setEnabled(true);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$NetWorkAndModelActivity(View view) {
        if (this.hiddenPass) {
            this.activityNetworkAndModelBinding.ivShowHidePass.setImageResource(C0575R.mipmap.icon_show_pass);
            this.activityNetworkAndModelBinding.editRoutePass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            this.activityNetworkAndModelBinding.ivShowHidePass.setImageResource(C0575R.mipmap.icon_hidden_pass);
            this.activityNetworkAndModelBinding.editRoutePass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        this.hiddenPass = !this.hiddenPass;
    }

    public /* synthetic */ void lambda$initView$1$NetWorkAndModelActivity(View view) {
        WifiUtil.openWifi(this);
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case C0575R.id.rb_ap_net /* 2131230938 */:
                this.activityNetworkAndModelBinding.tvRoutePassTitle.setVisibility(8);
                this.activityNetworkAndModelBinding.llRoutePass.setVisibility(8);
                this.activityNetworkAndModelBinding.tvIpTitle.setVisibility(8);
                this.activityNetworkAndModelBinding.editIp.setVisibility(8);
                break;
            case C0575R.id.rb_lan_net /* 2131230939 */:
                this.activityNetworkAndModelBinding.tvRoutePassTitle.setVisibility(0);
                this.activityNetworkAndModelBinding.llRoutePass.setVisibility(0);
                this.activityNetworkAndModelBinding.tvIpTitle.setVisibility(0);
                this.activityNetworkAndModelBinding.editIp.setVisibility(0);
                break;
        }
        updateView();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id != C0575R.id.btn_save) {
            if (id == C0575R.id.cancel_button) {
                showProgress(false);
                EsptouchAsyncTask4 esptouchAsyncTask4 = this.mTask;
                if (esptouchAsyncTask4 != null) {
                    esptouchAsyncTask4.cancelEsptouch();
                    return;
                }
                return;
            }
            if (id != C0575R.id.tv_issue) {
                return;
            }
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(C0575R.string.noo_official_website))));
            return;
        }
        switch (this.activityNetworkAndModelBinding.rgGroup.getCheckedRadioButtonId()) {
            case C0575R.id.rb_ap_net /* 2131230938 */:
                String str = this.stateResult.ssid;
                if (str.toLowerCase().startsWith("k7") || str.toLowerCase().startsWith("x4")) {
                    BaseApplication.getInstance().getDataStore().setNetName(this.stateResult.ssid);
                    BaseApplication.getInstance().getDataStore().setApModel(true);
                    finish();
                } else {
                    ToastMaker.showShortToast(getString(C0575R.string.set_real_ap));
                }
                break;
            case C0575R.id.rb_lan_net /* 2131230939 */:
                if (!this.stateResult.permissionGranted) {
                    XXPermissions.with(this).permission(this.permissions).request(this);
                } else if (this.stateResult.locationRequirement) {
                    ToastMaker.showLongToast(getString(C0575R.string.esptouch_message_location));
                    GpsUtil.openGPS2(this, 0);
                } else {
                    Editable text = this.activityNetworkAndModelBinding.editIp.getText();
                    if (!TextUtils.isEmpty(text) && StringUtils.ipVerify(text.toString())) {
                        NetWorkInfo netWorkInfo = new NetWorkInfo();
                        netWorkInfo.setSsidName(getString(C0575R.string.unknown_mac));
                        netWorkInfo.setServerAddress(text.toString());
                        this.netWorkInfos.offer(netWorkInfo);
                        this.activityNetworkAndModelBinding.testResult.setText("");
                        this.activityNetworkAndModelBinding.testResult.append(String.format("%s,%s\n", netWorkInfo.getServerAddress(), netWorkInfo.getSsidName()));
                        this.activityNetworkAndModelBinding.testResult.append("\n" + getString(C0575R.string.reading_device_set));
                        showProgress(true);
                        this.handler.obtainMessage(4, this.netWorkInfos.poll()).sendToTarget();
                    } else {
                        Editable text2 = this.activityNetworkAndModelBinding.editRoutePass.getText();
                        if (TextUtils.isEmpty(text2)) {
                            ToastMaker.showShortToast(getString(C0575R.string.input_route_pass));
                        } else if (this.stateResult.is5G) {
                            ToastMaker.showShortToast(this.stateResult.message.toString());
                        } else {
                            this.netWorkInfos.clear();
                            this.x4ConfigInfos.clear();
                            this.k7ConfigInfos.clear();
                            executeEsptouch(this.stateResult.ssid, this.stateResult.ssidBytes, this.stateResult.bssid, text2.toString());
                        }
                    }
                }
                break;
        }
    }

    private void executeEsptouch(String str, byte[] bArr, String str2, String str3) {
        if (bArr == null) {
            bArr = ByteUtil.getBytesByString(str);
        }
        byte[] bytesByString = ByteUtil.getBytesByString(str3);
        byte[] bssid2bytes = TouchNetUtil.parseBssid2bytes(str2);
        byte[] bytes = "1".getBytes();
        byte[] bArr2 = {1};
        EsptouchAsyncTask4 esptouchAsyncTask4 = this.mTask;
        if (esptouchAsyncTask4 != null) {
            esptouchAsyncTask4.cancelEsptouch();
        }
        this.timeOutHandler.removeCallbacksAndMessages(null);
        this.timeOutHandler.postDelayed(new Runnable() { // from class: com.nemo.caideng.-$$Lambda$NetWorkAndModelActivity$_J2w25xBaE-EHufeklMD5LfzZBA
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$executeEsptouch$2$NetWorkAndModelActivity();
            }
        }, 40000L);
        EsptouchAsyncTask4 esptouchAsyncTask42 = new EsptouchAsyncTask4(this);
        this.mTask = esptouchAsyncTask42;
        esptouchAsyncTask42.execute(bArr, bssid2bytes, bytesByString, bytes, bArr2);
    }

    public /* synthetic */ void lambda$executeEsptouch$2$NetWorkAndModelActivity() {
        if (isFinishing() || !this.settingNet) {
            return;
        }
        AlertDialog alertDialogShow = new AlertDialog.Builder(this).setMessage(C0575R.string.esptouch1_configure_result_time_out_failed).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
        this.mResultDialog = alertDialogShow;
        alertDialogShow.setCanceledOnTouchOutside(false);
        this.activityNetworkAndModelBinding.cancelButton.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgress(boolean z) {
        if (z) {
            this.activityNetworkAndModelBinding.content.setVisibility(4);
            this.activityNetworkAndModelBinding.progressView.setVisibility(0);
        } else {
            this.activityNetworkAndModelBinding.content.setVisibility(0);
            this.activityNetworkAndModelBinding.progressView.setVisibility(8);
        }
    }

    @Override // com.nemo.caideng.BaseActivity, com.hjq.permissions.OnPermissionCallback
    public void onGranted(List<String> list, boolean z) {
        if (z) {
            ToastMaker.showShortToast(getString(C0575R.string.location_request_success));
            onChanged("");
        }
    }

    @Override // com.nemo.caideng.BaseActivity, com.hjq.permissions.OnPermissionCallback
    public void onDenied(List<String> list, boolean z) {
        if (z) {
            ToastMaker.showShortToast(getString(C0575R.string.location_request_refuse));
            XXPermissions.startPermissionActivity((Activity) this, list);
        } else {
            ToastMaker.showShortToast(getString(C0575R.string.location_request_failed));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EsptouchAsyncTask4 extends AsyncTask<byte[], IEsptouchResult, List<IEsptouchResult>> {
        private final WeakReference<NetWorkAndModelActivity> mActivity;
        private IEsptouchTask mEsptouchTask;
        private final Object mLock = new Object();
        private AlertDialog mResultDialog;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(IEsptouchResult... iEsptouchResultArr) {
        }

        EsptouchAsyncTask4(NetWorkAndModelActivity netWorkAndModelActivity) {
            this.mActivity = new WeakReference<>(netWorkAndModelActivity);
        }

        void cancelEsptouch() {
            cancel(true);
            NetWorkAndModelActivity netWorkAndModelActivity = this.mActivity.get();
            if (netWorkAndModelActivity != null) {
                netWorkAndModelActivity.showProgress(false);
            }
            AlertDialog alertDialog = this.mResultDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            IEsptouchTask iEsptouchTask = this.mEsptouchTask;
            if (iEsptouchTask != null) {
                iEsptouchTask.interrupt();
            }
            netWorkAndModelActivity.settingNet = false;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            NetWorkAndModelActivity netWorkAndModelActivity = this.mActivity.get();
            netWorkAndModelActivity.activityNetworkAndModelBinding.testResult.setText("");
            netWorkAndModelActivity.showProgress(true);
            netWorkAndModelActivity.settingNet = true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public List<IEsptouchResult> doInBackground(byte[]... bArr) {
            int i;
            NetWorkAndModelActivity netWorkAndModelActivity = this.mActivity.get();
            synchronized (this.mLock) {
                byte[] bArr2 = bArr[0];
                byte[] bArr3 = bArr[1];
                byte[] bArr4 = bArr[2];
                byte[] bArr5 = bArr[3];
                byte[] bArr6 = bArr[4];
                i = bArr5.length == 0 ? -1 : Integer.parseInt(new String(bArr5));
                EsptouchTask esptouchTask = new EsptouchTask(bArr2, bArr3, bArr4, netWorkAndModelActivity.getApplicationContext());
                this.mEsptouchTask = esptouchTask;
                esptouchTask.setPackageBroadcast(bArr6[0] == 1);
                this.mEsptouchTask.setEsptouchListener(new IEsptouchListener() { // from class: com.nemo.caideng.-$$Lambda$NetWorkAndModelActivity$EsptouchAsyncTask4$Ozrs3qqS_vkr2VswX8V4xFFm2Uo
                    @Override // com.espressif.iot.esptouch.IEsptouchListener
                    public final void onEsptouchResultAdded(IEsptouchResult iEsptouchResult) {
                        this.f$0.publishProgress(iEsptouchResult);
                    }
                });
            }
            return this.mEsptouchTask.executeForResults(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(List<IEsptouchResult> list) {
            NetWorkAndModelActivity netWorkAndModelActivity = this.mActivity.get();
            if (netWorkAndModelActivity == null || netWorkAndModelActivity.isFinishing()) {
                return;
            }
            netWorkAndModelActivity.mTask = null;
            netWorkAndModelActivity.showProgress(false);
            if (list == null) {
                AlertDialog alertDialogShow = new AlertDialog.Builder(netWorkAndModelActivity).setMessage(C0575R.string.esptouch1_configure_result_failed_port).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
                this.mResultDialog = alertDialogShow;
                alertDialogShow.setCanceledOnTouchOutside(false);
                return;
            }
            IEsptouchResult iEsptouchResult = list.get(0);
            if (iEsptouchResult.isCancelled()) {
                return;
            }
            if (!iEsptouchResult.isSuc()) {
                AlertDialog alertDialogShow2 = new AlertDialog.Builder(netWorkAndModelActivity).setMessage(C0575R.string.esptouch1_configure_result_failed).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show();
                this.mResultDialog = alertDialogShow2;
                alertDialogShow2.setCanceledOnTouchOutside(false);
                return;
            }
            netWorkAndModelActivity.settingNet = false;
            for (IEsptouchResult iEsptouchResult2 : list) {
                NetWorkInfo netWorkInfo = new NetWorkInfo();
                netWorkInfo.setSsidName(iEsptouchResult2.getBssid());
                netWorkInfo.setServerAddress(iEsptouchResult2.getInetAddress().getHostAddress());
                netWorkAndModelActivity.netWorkInfos.offer(netWorkInfo);
                netWorkAndModelActivity.activityNetworkAndModelBinding.testResult.append(String.format(Locale.ENGLISH, "%s,%s\n", iEsptouchResult2.getInetAddress().getHostAddress(), iEsptouchResult2.getBssid()));
            }
            netWorkAndModelActivity.activityNetworkAndModelBinding.testResult.append("\n" + netWorkAndModelActivity.getString(C0575R.string.reading_device_set));
            netWorkAndModelActivity.showProgress(true);
            netWorkAndModelActivity.handler.obtainMessage(4, netWorkAndModelActivity.netWorkInfos.poll()).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendData(final byte[] bArr) {
        if (this.connectThread != null) {
            this.sendHandler.postDelayed(new Runnable() { // from class: com.nemo.caideng.-$$Lambda$NetWorkAndModelActivity$pjfwuYpcdJV8F75M4ZVKakVKXcA
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendData$3$NetWorkAndModelActivity(bArr);
                }
            }, 100L);
        }
    }

    public /* synthetic */ void lambda$sendData$3$NetWorkAndModelActivity(byte[] bArr) {
        this.connectThread.sendData(bArr);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        BaseApplication.getInstance().removeBroadcastObserver(this);
        this.handler.removeCallbacksAndMessages(null);
        this.sendHandler.removeCallbacksAndMessages(null);
        this.timeOutHandler.removeCallbacksAndMessages(null);
        this.sendHandlerThread.quit();
        disSocket();
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1025 && XXPermissions.isGranted(this, this.permissions)) {
            onChanged("");
        }
    }
}
