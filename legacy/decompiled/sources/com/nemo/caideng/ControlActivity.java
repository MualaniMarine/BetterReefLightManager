package com.nemo.caideng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nemo.caideng.Constant;
import com.nemo.caideng.adapter.LocalWifiInfoAdapter;
import com.nemo.caideng.adapter.TimeLuminanceAdapter;
import com.nemo.caideng.databinding.ActivityControlBinding;
import com.nemo.caideng.model.LocalWifiInfo;
import com.nemo.caideng.model.TimeLuminance;
import com.nemo.caideng.util.CommandUtil;
import com.nemo.caideng.util.ToastMaker;
import com.nemo.caideng.util.WifiControlUtils;
import com.nemo.caideng.util.WifiUtil;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.TimeWheelView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;

/* JADX INFO: loaded from: classes.dex */
public class ControlActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnItemChildClickListener {
    private static final int PORT = 8266;
    ActivityControlBinding activityControlBinding;
    private long click_time;
    private WifiConfiguration config;
    private ConnectThread connectThread;
    private String connectWifiName;
    private TimeLuminance currentTimeLuminance;
    private int model;
    private boolean netReceivered;
    private ConnectivityManager.NetworkCallback networkCallback;
    private ProgressDialog progressDialog;
    private Layer resetDialog;
    private Handler sendHandler;
    private HandlerThread sendHandlerThread;
    private boolean socketConnected;
    private Layer timeLuminanceDialog;
    private Layer timeLuminanceListDialog;
    private Layer timePickerDialog;
    private int type;
    private String typeStr;
    private WifiControlUtils wifiControlUtils;
    private WifiManager wifiManager;
    private int wifiPassType;
    private Layer zeroDialog;
    private String TAG = Constant.TAG;
    private List<LocalWifiInfo> localWifiInfos = new ArrayList();
    private LocalWifiInfoAdapter wifiInfoAdapter = new LocalWifiInfoAdapter();
    private TimeLuminanceAdapter timeLuminanceAdapter = new TimeLuminanceAdapter();
    private int[] lineColors = new int[6];
    private int CONNECT_INTERVAL = 1000;
    private int CLICK_INTERVAL = 5000;
    private Handler handler = new Handler() { // from class: com.nemo.caideng.ControlActivity.1
        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ControlActivity.this.dismissDialog();
                ControlActivity.this.sendData(CommandUtil.allRead());
                ControlActivity.this.socketConnected = true;
                ControlActivity.this.updateConnectWifi();
                ControlActivity.this.changeViewEnable(true, false);
                ToastMaker.showLongToast("数据连接可用");
                return;
            }
            if (i == 2) {
                ControlActivity.this.dismissDialog();
                ControlActivity.this.socketConnected = false;
                ControlActivity.this.updateConnectWifi();
                ControlActivity.this.changeViewEnable(false, false);
                ToastMaker.showLongToast("数据连接不可用");
                ControlActivity.this.disSocket();
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    ControlActivity.this.sendData((byte[]) message.obj);
                    return;
                }
                String strIntToIp = ControlActivity.this.intToIp(ControlActivity.this.wifiManager.getDhcpInfo().serverAddress);
                Log.i(ControlActivity.this.TAG, "连接到socket" + strIntToIp);
                ControlActivity.this.connectThread = new ConnectThread(strIntToIp, ControlActivity.PORT, ControlActivity.this.handler);
                ControlActivity.this.connectThread.start();
                return;
            }
            ControlActivity.this.timeLuminanceAdapter.getData().clear();
            ControlActivity.this.timeLuminanceAdapter.notifyDataSetChanged();
            byte[] bArr = (byte[]) message.obj;
            byte[] bArr2 = new byte[6];
            System.arraycopy(bArr, 4, bArr2, 0, 6);
            ControlActivity.this.activityControlBinding.asnvSeek.setValues(bArr2);
            int i2 = 11;
            int i3 = 0;
            while (i3 < 24) {
                TimeLuminance timeLuminance = new TimeLuminance();
                timeLuminance.setHour(bArr[i2]);
                timeLuminance.setMinute(bArr[i2 + 1]);
                byte[] bArr3 = new byte[6];
                System.arraycopy(bArr, i2 + 2, bArr3, 0, 6);
                timeLuminance.setLuminanceValue(bArr3);
                ControlActivity.this.timeLuminanceAdapter.addData(timeLuminance);
                i3++;
                i2 += 8;
            }
            ControlActivity.this.updateChart();
            if (bArr[202] == 1) {
                ControlActivity.this.changeViewEnable(true, true);
            }
            ControlActivity.this.syncTime();
        }
    };
    private BroadcastReceiver resultReceiver = new BroadcastReceiver() { // from class: com.nemo.caideng.ControlActivity.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                Log.i(ControlActivity.this.TAG, "获取到wifi扫描的结果");
                ControlActivity.this.generateWifiInfo(ControlActivity.this.wifiManager.getScanResults());
                ControlActivity.this.wifiInfoAdapter.setList(ControlActivity.this.localWifiInfos);
                ControlActivity.this.dismissDialog();
                ControlActivity controlActivity = ControlActivity.this;
                controlActivity.unregisterReceiver(controlActivity.resultReceiver);
                ControlActivity.this.initReceiver();
            }
        }
    };
    private BroadcastReceiver netStateReceiver = new BroadcastReceiver() { // from class: com.nemo.caideng.ControlActivity.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.STATE_CHANGE") && ((NetworkInfo) intent.getParcelableExtra("networkInfo")).getState().equals(NetworkInfo.State.CONNECTED)) {
                WifiInfo connectionInfo = ControlActivity.this.wifiManager.getConnectionInfo();
                Log.i(ControlActivity.this.TAG, "已连接到wifi网络" + connectionInfo.getSSID());
                String ssid = connectionInfo.getSSID();
                ControlActivity controlActivity = ControlActivity.this;
                if (controlActivity.ssidStartWith(ssid, controlActivity.typeStr)) {
                    if (ControlActivity.this.click_time == 0) {
                        ControlActivity.this.click_time = System.currentTimeMillis();
                    }
                    ControlActivity.this.connectWifiName = ssid;
                    ((ConnectivityManager) ControlActivity.this.getSystemService("connectivity")).getActiveNetwork();
                    ControlActivity.this.showDialog("连接建立中...");
                    ControlActivity.this.handler.sendEmptyMessageDelayed(4, ControlActivity.this.CONNECT_INTERVAL);
                    return;
                }
                ControlActivity.this.connectWifiName = null;
                ControlActivity.this.updateConnectWifi();
            }
        }
    };

    public static void startActivity(Activity activity, int i, int i2) {
        Intent intent = new Intent(activity, (Class<?>) ControlActivity.class);
        intent.putExtra("type", i);
        intent.putExtra("model", i2);
        activity.startActivity(intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityControlBinding activityControlBindingInflate = ActivityControlBinding.inflate(getLayoutInflater());
        this.activityControlBinding = activityControlBindingInflate;
        setContentView(activityControlBindingInflate.getRoot());
        this.progressDialog = new ProgressDialog(this);
        HandlerThread handlerThread = new HandlerThread("send_thread");
        this.sendHandlerThread = handlerThread;
        handlerThread.start();
        this.sendHandler = new Handler(this.sendHandlerThread.getLooper());
        initData();
        intiView();
        initListener();
        initWifiInfo();
        initLineChart();
        this.wifiControlUtils = new WifiControlUtils(this);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0575R.menu.item_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == C0575R.id.search) {
            search();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void intiView() {
        this.activityControlBinding.rvWifiList.setLayoutManager(new LinearLayoutManager(this));
        this.activityControlBinding.rvWifiList.setAdapter(this.wifiInfoAdapter);
        this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
        changeViewEnable(false, false);
    }

    private void initListener() {
        this.wifiInfoAdapter.setOnItemClickListener(this);
        this.activityControlBinding.swAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$Va6bFyYDy516RdfGMaa8jagl-vw
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initListener$0$ControlActivity(compoundButton, z);
            }
        });
        this.timeLuminanceAdapter.addChildClickViewIds(C0575R.id.btn_edit);
        this.timeLuminanceAdapter.setOnItemChildClickListener(this);
        this.activityControlBinding.btnSave.setOnClickListener(this);
        this.activityControlBinding.btnTimeSet.setOnClickListener(this);
        this.activityControlBinding.btnSyncTime.setOnClickListener(this);
        this.activityControlBinding.btnReset.setOnClickListener(this);
        this.activityControlBinding.btnZero.setOnClickListener(this);
    }

    public /* synthetic */ void lambda$initListener$0$ControlActivity(CompoundButton compoundButton, boolean z) {
        changeViewEnable(true, z);
        sendData(CommandUtil.changeModel(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(this.netStateReceiver, intentFilter);
        this.netReceivered = true;
    }

    private void initWifiInfo() {
        this.wifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
    }

    private void initLineChart() {
        LineChart lineChart = this.activityControlBinding.lineChart;
        lineChart.setTouchEnabled(false);
        lineChart.setBackgroundColor(-1);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(getResources().getColor(C0575R.color.track_track_no_check));
        xAxis.setAxisLineWidth(2.0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(24.0f);
        xAxis.setAxisMinimum(0.0f);
        xAxis.setLabelCount(12, false);
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setDrawGridLines(true);
        axisLeft.setAxisLineColor(getResources().getColor(C0575R.color.track_track_no_check));
        axisLeft.setAxisLineWidth(2.0f);
        axisLeft.setAxisMaximum(100.0f);
        axisLeft.setAxisMinimum(0.0f);
        axisLeft.setLabelCount(10, false);
        lineChart.getAxisRight().setEnabled(true);
        lineChart.setNoDataText("没有数据");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncTime() {
        sendData(CommandUtil.syncTime((byte) this.activityControlBinding.dcTime.getHour(), (byte) this.activityControlBinding.dcTime.getMinute(), (byte) this.activityControlBinding.dcTime.getSecond()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disSocket() {
        ConnectThread connectThread = this.connectThread;
        if (connectThread != null) {
            connectThread.close();
        }
        this.connectThread = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendData(final byte[] bArr) {
        if (this.connectThread != null) {
            this.sendHandler.post(new Runnable() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$jLiRnNHaTHOfZgNTIJK05NzIsd0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendData$1$ControlActivity(bArr);
                }
            });
        }
    }

    public /* synthetic */ void lambda$sendData$1$ControlActivity(byte[] bArr) {
        this.connectThread.sendData(bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChart() {
        int i = this.type == 1 ? 6 : 3;
        List<TimeLuminance> data = this.timeLuminanceAdapter.getData();
        LineData lineData = new LineData();
        for (int i2 = 0; i2 < i; i2++) {
            ArrayList arrayList = new ArrayList();
            for (TimeLuminance timeLuminance : data) {
                arrayList.add(new Entry(timeLuminance.getHour() + (timeLuminance.getMinute() / 60.0f), timeLuminance.getLuminanceValue()[i2]));
            }
            lineData.addDataSet(generateLineDataSet(arrayList, this.lineColors[i2]));
        }
        this.activityControlBinding.lineChart.setData(lineData);
        this.activityControlBinding.lineChart.invalidate();
    }

    private LineDataSet generateLineDataSet(List<Entry> list, int i) {
        LineDataSet lineDataSet = new LineDataSet(list, "");
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setColor(i);
        lineDataSet.setLineWidth(2.0f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleHoleRadius(3.0f);
        lineDataSet.setCircleColor(i);
        lineDataSet.setCircleRadius(3.0f);
        return lineDataSet;
    }

    private void initData() {
        this.type = getIntent().getIntExtra("type", 0);
        this.model = getIntent().getIntExtra("model", 0);
        this.typeStr = this.type == 1 ? "k7" : "x4";
        this.lineColors[0] = getResources().getColor(C0575R.color.color_progress_1);
        this.lineColors[1] = getResources().getColor(C0575R.color.color_progress_2);
        this.lineColors[2] = getResources().getColor(C0575R.color.color_progress_3);
        this.lineColors[3] = getResources().getColor(C0575R.color.color_progress_4);
        this.lineColors[4] = getResources().getColor(C0575R.color.color_progress_5);
        this.lineColors[5] = getResources().getColor(C0575R.color.color_progress_6);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case C0575R.id.btn_reset /* 2131230818 */:
                if (this.resetDialog == null) {
                    this.resetDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_reset).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$C2voO9rNH7xmTAsJtX-T6SX8hxw
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$5$ControlActivity(layer);
                        }
                    });
                }
                this.resetDialog.show();
                break;
            case C0575R.id.btn_save /* 2131230819 */:
                if (this.activityControlBinding.swAuto.isChecked()) {
                    sendData(CommandUtil.allSet((byte) this.activityControlBinding.dcTime.getHour(), (byte) this.activityControlBinding.dcTime.getMinute(), (byte) this.activityControlBinding.dcTime.getSecond(), this.activityControlBinding.asnvSeek.getValues(), this.timeLuminanceAdapter.getData(), true));
                    updateChart();
                } else {
                    sendData(CommandUtil.handModelLuminance(this.activityControlBinding.asnvSeek.getValues()));
                }
                break;
            case C0575R.id.btn_sync_time /* 2131230821 */:
                if (this.timePickerDialog == null) {
                    this.timePickerDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_picker).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$iAtg4yvVNtxUPaJ7Hm2PC0-zG1E
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$3$ControlActivity(layer);
                        }
                    });
                }
                this.timePickerDialog.show();
                ((TimeWheelView) this.timePickerDialog.getView(C0575R.id.twv_time)).setTime(0, 0, 0);
                break;
            case C0575R.id.btn_time_set /* 2131230822 */:
                if (this.activityControlBinding.swAuto.isChecked()) {
                    if (this.timeLuminanceListDialog == null) {
                        this.timeLuminanceListDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_luminance_list).cancelableOnTouchOutside(false).cancelableOnClickKeyBack(false).backgroundDimAmount(0.3f).gravity(17).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$6ADFa7zhZaYReJjM_AzNYixln_8
                            @Override // per.goweii.anylayer.Layer.DataBinder
                            public final void bindData(Layer layer) {
                                this.f$0.lambda$onClick$8$ControlActivity(layer);
                            }
                        });
                    }
                    this.timeLuminanceListDialog.show();
                } else {
                    ToastMaker.showShortToast("手动模式下无法设置时间点亮度");
                }
                break;
            case C0575R.id.btn_zero /* 2131230823 */:
                if (this.zeroDialog == null) {
                    this.zeroDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_zero).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$fR16_xgeNqDcP3hlKJPE4COT1KI
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$7$ControlActivity(layer);
                        }
                    });
                }
                this.zeroDialog.show();
                break;
        }
    }

    public /* synthetic */ void lambda$onClick$3$ControlActivity(Layer layer) {
        final TimeWheelView timeWheelView = (TimeWheelView) layer.getView(C0575R.id.twv_time);
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$F_-SYmP4T_MPX1tcgK3VuZCOWh8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$2$ControlActivity(timeWheelView, view);
            }
        });
    }

    public /* synthetic */ void lambda$null$2$ControlActivity(TimeWheelView timeWheelView, View view) {
        Log.i(Constant.TAG, "选择的时间:小时" + timeWheelView.getHour() + "分钟：" + timeWheelView.getMinute() + "秒数:" + timeWheelView.getSecond());
        sendData(CommandUtil.syncTime((byte) timeWheelView.getHour(), (byte) timeWheelView.getMinute(), (byte) timeWheelView.getSecond()));
        this.timePickerDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$5$ControlActivity(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$t2XJ_ZC_iPbHsnKgHWRE6AMab1Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$4$ControlActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$4$ControlActivity(View view) {
        zero();
        byte[][] bArr = new byte[8][];
        if (this.type == 1) {
            int i = this.model;
            if (i == 1) {
                bArr = Constant.InterfaceC0558K7.SPS_DEFAULT;
            } else if (i == 2) {
                bArr = Constant.InterfaceC0558K7.LPS_DEFAULT;
            } else if (i == 3) {
                bArr = Constant.InterfaceC0558K7.SL_DEFAULT;
            }
        } else {
            int i2 = this.model;
            if (i2 == 1) {
                bArr = Constant.InterfaceC0559X4.SPS_DEFAULT;
            } else if (i2 == 2) {
                bArr = Constant.InterfaceC0559X4.LPS_DEFAULT;
            } else if (i2 == 3) {
                bArr = Constant.InterfaceC0559X4.SL_DEFAULT;
            }
        }
        List<TimeLuminance> data = this.timeLuminanceAdapter.getData();
        for (int i3 = 0; i3 < bArr.length; i3++) {
            TimeLuminance timeLuminance = data.get(i3);
            timeLuminance.setHour(bArr[i3][0]);
            timeLuminance.setMinute(bArr[i3][1]);
            byte[] bArr2 = new byte[6];
            System.arraycopy(bArr[i3], 2, bArr2, 0, 6);
            timeLuminance.setLuminanceValue(bArr2);
        }
        this.timeLuminanceAdapter.notifyDataSetChanged();
        this.resetDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$7$ControlActivity(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$wH5gvuK2kVV27tH-_X6nfyAoPJ8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$6$ControlActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$6$ControlActivity(View view) {
        zero();
        this.zeroDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$8$ControlActivity(Layer layer) {
        ((RecyclerView) layer.getView(C0575R.id.rv_time_luminance_list)).setAdapter(this.timeLuminanceAdapter);
    }

    private void zero() {
        if (this.activityControlBinding.swAuto.isChecked()) {
            Iterator<TimeLuminance> it = this.timeLuminanceAdapter.getData().iterator();
            while (it.hasNext()) {
                it.next().reSet();
            }
            this.timeLuminanceAdapter.notifyDataSetChanged();
            return;
        }
        this.activityControlBinding.asnvSeek.reSet();
    }

    private void search() {
        showDialog("扫描中...");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        registerReceiver(this.resultReceiver, intentFilter);
        if (WifiUtil.isWifiEnable()) {
            this.wifiManager.startScan();
        } else {
            WifiUtil.openWifi(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    private String intToIpServer(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + ".1";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generateWifiInfo(List<ScanResult> list) {
        this.localWifiInfos.clear();
        for (ScanResult scanResult : list) {
            if (ssidStartWith(scanResult.SSID, this.typeStr)) {
                LocalWifiInfo localWifiInfo = new LocalWifiInfo();
                localWifiInfo.setScanResult(scanResult);
                if (ssidEquals(scanResult.SSID, this.connectWifiName) && this.socketConnected) {
                    localWifiInfo.setChecked(true);
                }
                this.localWifiInfos.add(localWifiInfo);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConnectWifi() {
        List<LocalWifiInfo> data = this.wifiInfoAdapter.getData();
        if (data.size() > 0) {
            for (LocalWifiInfo localWifiInfo : data) {
                localWifiInfo.setChecked(false);
                if (ssidEquals(localWifiInfo.getScanResult().SSID, this.connectWifiName) && this.socketConnected) {
                    localWifiInfo.setChecked(true);
                }
            }
            this.wifiInfoAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        if (this.netReceivered) {
            unregisterReceiver(this.netStateReceiver);
        }
        this.handler.removeCallbacksAndMessages(null);
        this.sendHandler.removeCallbacksAndMessages(null);
        this.sendHandlerThread.quit();
        disSocket();
        super.onDestroy();
    }

    private void connectWifi(LocalWifiInfo localWifiInfo) {
        showDialog("连接网络中...");
        ScanResult scanResult = localWifiInfo.getScanResult();
        if (Build.VERSION.SDK_INT >= 29) {
            NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addTransportType(1).removeCapability(12).setNetworkSpecifier(new WifiNetworkSpecifier.Builder().setSsid(scanResult.SSID).setWpa2Passphrase("12345678").build()).build();
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
            if (this.networkCallback == null) {
                this.networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.nemo.caideng.ControlActivity.4
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onUnavailable() {
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onAvailable(Network network) {
                        ConnectivityManager connectivityManager2 = (ConnectivityManager) ControlActivity.this.getSystemService("connectivity");
                        connectivityManager2.getActiveNetwork();
                        Log.i(ControlActivity.this.TAG, "onAvailable" + network.toString());
                        if (Build.VERSION.SDK_INT >= 23) {
                            connectivityManager2.bindProcessToNetwork(network);
                        } else {
                            ConnectivityManager.setProcessDefaultNetwork(network);
                        }
                    }
                };
            }
            connectivityManager.requestNetwork(networkRequestBuild, this.networkCallback);
            return;
        }
        String str = scanResult.capabilities;
        this.wifiPassType = 1;
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("WPA") || str.contains("wpa")) {
                this.wifiPassType = 1;
            } else if (str.contains("WEP") || str.contains("wep")) {
                this.wifiPassType = 2;
            } else {
                this.wifiPassType = 3;
            }
        }
        WifiConfiguration wifiConfigurationIsExsits = isExsits(scanResult.SSID);
        this.config = wifiConfigurationIsExsits;
        if (wifiConfigurationIsExsits == null) {
            if (this.wifiPassType != 3) {
                this.wifiControlUtils.addNetWork(scanResult.SSID, "12345678", 2);
                return;
            } else {
                this.wifiControlUtils.addNetWork(scanResult.SSID, "", 0);
                return;
            }
        }
        connectConfig(wifiConfigurationIsExsits);
    }

    private void connectConfig(WifiConfiguration wifiConfiguration) {
        this.wifiManager.enableNetwork(wifiConfiguration.networkId, true);
    }

    public WifiConfiguration createWifiInfo(String str, String str2, int i) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + str + "\"";
        WifiConfiguration wifiConfigurationIsExsits = isExsits(str);
        if (wifiConfigurationIsExsits != null) {
            this.wifiManager.removeNetwork(wifiConfigurationIsExsits.networkId);
        }
        if (i == 3) {
            wifiConfiguration.wepKeys[0] = "";
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        }
        if (i == 2) {
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.wepKeys[0] = "\"" + str2 + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        }
        if (i == 1) {
            wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.status = 2;
        }
        return wifiConfiguration;
    }

    private WifiConfiguration isExsits(String str) {
        for (WifiConfiguration wifiConfiguration : this.wifiManager.getConfiguredNetworks()) {
            if (ssidEquals(wifiConfiguration.SSID, str)) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    private void disConnectWifi() {
        this.wifiManager.disconnect();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        if (System.currentTimeMillis() - this.click_time < this.CLICK_INTERVAL) {
            return;
        }
        this.click_time = System.currentTimeMillis();
        if (!ssidEquals(this.connectWifiName, this.localWifiInfos.get(i).getScanResult().SSID)) {
            connectWifi(this.localWifiInfos.get(i));
        } else {
            if (this.socketConnected) {
                return;
            }
            showDialog("连接建立中...");
            this.handler.sendEmptyMessageDelayed(4, this.CONNECT_INTERVAL);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialog(String str) {
        this.progressDialog.setMessage(str);
        if (this.progressDialog.isShowing()) {
            return;
        }
        this.progressDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        if (this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

    private boolean ssidEquals(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return TextUtils.equals(str.replace("\"", ""), str2.replace("\"", ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean ssidStartWith(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return str.toLowerCase().replace("\"", "").startsWith(str2);
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.currentTimeLuminance = this.timeLuminanceAdapter.getData().get(i);
        if (i != 0 && !this.timeLuminanceAdapter.getData().get(i - 1).isEnable()) {
            ToastMaker.showShortToast("请选择之前项编辑");
            return;
        }
        if (this.timeLuminanceDialog == null) {
            this.timeLuminanceDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_seek_picker).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(80).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$ly_EFZ4p_gs5HO_s1nnsq5-p35c
                @Override // per.goweii.anylayer.Layer.DataBinder
                public final void bindData(Layer layer) {
                    this.f$0.lambda$onItemChildClick$11$ControlActivity(layer);
                }
            });
        }
        this.timeLuminanceDialog.show();
        ((TimeWheelView) this.timeLuminanceDialog.getView(C0575R.id.twv_time)).setTime(this.currentTimeLuminance.getHour(), this.currentTimeLuminance.getMinute(), 0);
        ((AllSeekNumView) this.timeLuminanceDialog.getView(C0575R.id.asnv_seek)).setValues(this.currentTimeLuminance.getLuminanceValue());
    }

    public /* synthetic */ void lambda$onItemChildClick$11$ControlActivity(Layer layer) {
        final TimeWheelView timeWheelView = (TimeWheelView) layer.getView(C0575R.id.twv_time);
        timeWheelView.hiddenSecond();
        final AllSeekNumView allSeekNumView = (AllSeekNumView) layer.getView(C0575R.id.asnv_seek);
        allSeekNumView.setEnable(this.type == 1 ? 6 : 3);
        Button button = (Button) layer.getView(C0575R.id.btn_preview);
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$0e3MdY2wWONunTSOUkkTkU6u6pw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$9$ControlActivity(timeWheelView, allSeekNumView, view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlActivity$_RvAP6AHSpcQcPjhCINeeGbcA3k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$10$ControlActivity(allSeekNumView, view);
            }
        });
    }

    public /* synthetic */ void lambda$null$9$ControlActivity(TimeWheelView timeWheelView, AllSeekNumView allSeekNumView, View view) {
        Log.i(Constant.TAG, "选择的时间:小时" + timeWheelView.getHour() + "分钟：" + timeWheelView.getMinute() + "秒数:" + timeWheelView.getSecond() + "亮度：" + allSeekNumView.getValues());
        this.currentTimeLuminance.setHour(timeWheelView.getHour());
        this.currentTimeLuminance.setMinute(timeWheelView.getMinute());
        this.currentTimeLuminance.setLuminanceValue(allSeekNumView.getValues());
        this.timeLuminanceAdapter.notifyDataSetChanged();
        this.timeLuminanceDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$10$ControlActivity(AllSeekNumView allSeekNumView, View view) {
        sendData(CommandUtil.previewModelLuminance(allSeekNumView.getValues()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeViewEnable(boolean z, boolean z2) {
        this.activityControlBinding.btnSyncTime.setEnabled(false);
        this.activityControlBinding.btnTimeSet.setEnabled(false);
        this.activityControlBinding.btnSave.setEnabled(false);
        this.activityControlBinding.swAuto.setEnabled(false);
        this.activityControlBinding.swAuto.setChecked(false);
        this.activityControlBinding.btnZero.setEnabled(false);
        this.activityControlBinding.btnReset.setEnabled(false);
        this.activityControlBinding.asnvSeek.setEnable(0);
        this.activityControlBinding.asnvSeek.setVisibility(0);
        this.activityControlBinding.lineChart.setVisibility(8);
        if (z) {
            if (z2) {
                this.activityControlBinding.swAuto.setChecked(true);
                this.activityControlBinding.btnTimeSet.setEnabled(true);
                this.activityControlBinding.btnReset.setEnabled(true);
                this.activityControlBinding.asnvSeek.setVisibility(8);
                this.activityControlBinding.lineChart.setVisibility(0);
            }
            this.activityControlBinding.btnSave.setEnabled(true);
            this.activityControlBinding.btnZero.setEnabled(true);
            this.activityControlBinding.swAuto.setEnabled(true);
            this.activityControlBinding.btnSyncTime.setEnabled(true);
            this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
        }
    }
}
