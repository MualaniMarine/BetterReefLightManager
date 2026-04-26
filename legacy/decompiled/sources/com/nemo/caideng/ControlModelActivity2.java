package com.nemo.caideng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nemo.caideng.Constant;
import com.nemo.caideng.adapter.NetWorkInfoAdapter;
import com.nemo.caideng.adapter.TimeLuminanceAdapter;
import com.nemo.caideng.databinding.ActivityControlModel1Binding;
import com.nemo.caideng.model.NetWorkInfo;
import com.nemo.caideng.model.TimeLuminance;
import com.nemo.caideng.util.CommandUtil;
import com.nemo.caideng.util.ToastMaker;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.TimeWheelView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;

/* JADX INFO: loaded from: classes.dex */
public class ControlModelActivity2 extends BaseActivity implements View.OnClickListener, OnItemClickListener, OnItemChildClickListener, Observer<String>, AllSeekNumView.ValueListener {
    private static final int PORT = 8266;
    ActivityControlModel1Binding activityControlBinding;
    private long click_time;
    private ConnectThread connectThread;
    private TimeLuminance currentTimeLuminance;
    private int model;
    private ProgressDialog progressDialog;
    private Layer resetDialog;
    private Handler sendHandler;
    private HandlerThread sendHandlerThread;
    StateResult stateResult;
    private Layer timeLuminanceDialog;
    private Layer timeLuminanceListDialog;
    private Layer timePickerDialog;
    private int type;
    private Layer zeroDialog;
    private String TAG = Constant.TAG;
    private NetWorkInfoAdapter netWorkInfoAdapter = new NetWorkInfoAdapter();
    private TimeLuminanceAdapter timeLuminanceAdapter = new TimeLuminanceAdapter();
    private int[] lineColors = new int[6];
    private int CLICK_INTERVAL = 5000;
    private Handler handler = new Handler() { // from class: com.nemo.caideng.ControlModelActivity2.1
        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ControlModelActivity2.this.dismissDialog();
                ControlModelActivity2.this.sendData(CommandUtil.allRead());
                ControlModelActivity2.this.updateConnectWifi((String) message.obj, true);
                ControlModelActivity2.this.changeViewEnable(true, false);
                ToastMaker.showLongToast("数据连接可用");
                return;
            }
            if (i == 2) {
                ControlModelActivity2.this.dismissDialog();
                ControlModelActivity2.this.updateConnectWifi((String) message.obj, false);
                ControlModelActivity2.this.changeViewEnable(false, false);
                ToastMaker.showLongToast("数据连接不可用");
                ControlModelActivity2.this.disSocket();
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    ControlModelActivity2.this.sendData((byte[]) message.obj);
                    return;
                } else {
                    NetWorkInfo netWorkInfo = (NetWorkInfo) message.obj;
                    ControlModelActivity2.this.disSocket();
                    ControlModelActivity2.this.connectThread = new ConnectThread(netWorkInfo.getServerAddress(), ControlModelActivity2.PORT, ControlModelActivity2.this.handler);
                    ControlModelActivity2.this.connectThread.start();
                    return;
                }
            }
            ControlModelActivity2.this.timeLuminanceAdapter.setList(null);
            byte[] bArr = (byte[]) message.obj;
            byte[] bArr2 = new byte[6];
            System.arraycopy(bArr, 4, bArr2, 0, 6);
            ControlModelActivity2.this.activityControlBinding.asnvSeek.setValues(bArr2);
            int i2 = 11;
            int i3 = 0;
            while (i3 < 24) {
                TimeLuminance timeLuminance = new TimeLuminance();
                timeLuminance.setHour(bArr[i2]);
                timeLuminance.setMinute(bArr[i2 + 1]);
                byte[] bArr3 = new byte[6];
                System.arraycopy(bArr, i2 + 2, bArr3, 0, 6);
                timeLuminance.setLuminanceValue(bArr3);
                ControlModelActivity2.this.timeLuminanceAdapter.addData(timeLuminance);
                i3++;
                i2 += 8;
            }
            ControlModelActivity2.this.updateChart();
            if (bArr[202] == 1) {
                ControlModelActivity2.this.changeViewEnable(true, true);
            }
            ControlModelActivity2.this.syncTime();
        }
    };

    public static void startActivity(Activity activity, int i, int i2) {
        Intent intent = new Intent(activity, (Class<?>) ControlModelActivity2.class);
        intent.putExtra("type", i);
        intent.putExtra("model", i2);
        activity.startActivity(intent);
    }

    @Override // com.nemo.caideng.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityControlModel1Binding activityControlModel1BindingInflate = ActivityControlModel1Binding.inflate(getLayoutInflater());
        this.activityControlBinding = activityControlModel1BindingInflate;
        setContentView(activityControlModel1BindingInflate.getRoot());
        this.progressDialog = new ProgressDialog(this);
        HandlerThread handlerThread = new HandlerThread("send_thread");
        this.sendHandlerThread = handlerThread;
        handlerThread.start();
        this.sendHandler = new Handler(this.sendHandlerThread.getLooper());
        initData();
        intiView();
        initListener();
        initLineChart();
        BaseApplication.getInstance().observeBroadcast(this, this);
    }

    private void intiView() {
        this.activityControlBinding.rvWifiList.setLayoutManager(new LinearLayoutManager(this));
        this.activityControlBinding.rvWifiList.setAdapter(this.netWorkInfoAdapter);
        this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
        changeViewEnable(false, false);
    }

    private void initListener() {
        this.netWorkInfoAdapter.setOnItemClickListener(this);
        this.activityControlBinding.swAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$S9inY30W4igGyduChKchMOGA69E
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initListener$0$ControlModelActivity2(compoundButton, z);
            }
        });
        this.timeLuminanceAdapter.addChildClickViewIds(C0575R.id.btn_edit);
        this.timeLuminanceAdapter.setOnItemChildClickListener(this);
        this.activityControlBinding.btnSave.setOnClickListener(this);
        this.activityControlBinding.btnTimeSet.setOnClickListener(this);
        this.activityControlBinding.btnSyncTime.setOnClickListener(this);
        this.activityControlBinding.btnReset.setOnClickListener(this);
        this.activityControlBinding.btnZero.setOnClickListener(this);
        this.activityControlBinding.asnvSeek.setValueListener(this);
        this.activityControlBinding.btnDemonstration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$5rnmWMYhar5n1NH4GJjhS4LYmbc
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initListener$1$ControlModelActivity2(compoundButton, z);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$ControlModelActivity2(CompoundButton compoundButton, boolean z) {
        changeViewEnable(true, z);
        sendData(CommandUtil.changeModel(z));
    }

    public /* synthetic */ void lambda$initListener$1$ControlModelActivity2(CompoundButton compoundButton, boolean z) {
        if (z) {
            this.activityControlBinding.btnDemonstration.setText("关闭演示");
        } else {
            this.activityControlBinding.btnDemonstration.setText("演示");
        }
        sendData(CommandUtil.openDemonstration(z));
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
        lineChart.getAxisRight().setEnabled(false);
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
            this.sendHandler.post(new Runnable() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$sWTPd7ryhOCjtAkY1-9gf8YLoes
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendData$2$ControlModelActivity2(bArr);
                }
            });
        }
    }

    public /* synthetic */ void lambda$sendData$2$ControlModelActivity2(byte[] bArr) {
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
        this.lineColors[0] = getResources().getColor(C0575R.color.color_progress_1);
        this.lineColors[1] = getResources().getColor(C0575R.color.color_progress_3);
        this.lineColors[2] = getResources().getColor(C0575R.color.color_progress_6);
        this.lineColors[3] = getResources().getColor(C0575R.color.color_progress_4);
        this.lineColors[4] = getResources().getColor(C0575R.color.color_progress_2);
        this.lineColors[5] = getResources().getColor(C0575R.color.color_progress_5);
        if (this.type == 2) {
            this.lineColors[2] = getResources().getColor(C0575R.color.color_progress_4);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case C0575R.id.btn_reset /* 2131230818 */:
                if (this.resetDialog == null) {
                    this.resetDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_reset).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$t4vXQLkeTX8ccxZSILeGyvpp1X4
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$6$ControlModelActivity2(layer);
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
                    this.timePickerDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_picker).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$lWREKisYzbhtosjJwaxU89PYnzs
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$4$ControlModelActivity2(layer);
                        }
                    });
                }
                this.timePickerDialog.show();
                ((TimeWheelView) this.timePickerDialog.getView(C0575R.id.twv_time)).setTime(0, 0, 0);
                break;
            case C0575R.id.btn_time_set /* 2131230822 */:
                if (this.activityControlBinding.swAuto.isChecked()) {
                    if (this.timeLuminanceListDialog == null) {
                        this.timeLuminanceListDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_luminance_list1).cancelableOnTouchOutside(false).cancelableOnClickKeyBack(false).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_close).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$AwQO-D3F15k9JHHrdiUrf4pXng8
                            @Override // per.goweii.anylayer.Layer.DataBinder
                            public final void bindData(Layer layer) {
                                this.f$0.lambda$onClick$9$ControlModelActivity2(layer);
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
                    this.zeroDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_zero).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$ogpS_Sy8RuiXivWGn-FvmIqxj-A
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$8$ControlModelActivity2(layer);
                        }
                    });
                }
                this.zeroDialog.show();
                break;
        }
    }

    public /* synthetic */ void lambda$onClick$4$ControlModelActivity2(Layer layer) {
        final TimeWheelView timeWheelView = (TimeWheelView) layer.getView(C0575R.id.twv_time);
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$QelvRrlvcbckeJukxeC1sPT9pMY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$3$ControlModelActivity2(timeWheelView, view);
            }
        });
    }

    public /* synthetic */ void lambda$null$3$ControlModelActivity2(TimeWheelView timeWheelView, View view) {
        Log.i(Constant.TAG, "选择的时间:小时" + timeWheelView.getHour() + "分钟：" + timeWheelView.getMinute() + "秒数:" + timeWheelView.getSecond());
        sendData(CommandUtil.syncTime((byte) timeWheelView.getHour(), (byte) timeWheelView.getMinute(), (byte) timeWheelView.getSecond()));
        this.timePickerDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$6$ControlModelActivity2(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$RelAL7u3ZX0uUKT9u-Cjsj5Dxxk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$5$ControlModelActivity2(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$5$ControlModelActivity2(View view) {
        if (this.type == 1) {
            this.activityControlBinding.asnvSeek.setValues(Constant.Hand.K7_DEFAULT);
        } else {
            this.activityControlBinding.asnvSeek.setValues(Constant.Hand.X4_DEFAULT);
        }
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte.class, 24, 8);
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

    public /* synthetic */ void lambda$onClick$8$ControlModelActivity2(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$thX-E4Ufdul6Unf3pJFaRMDZj0E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$7$ControlModelActivity2(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$7$ControlModelActivity2(View view) {
        zero();
        this.zeroDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$9$ControlModelActivity2(Layer layer) {
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

    private void configNet() {
        String x4;
        if (!this.stateResult.wifiConnected) {
            ToastMaker.showShortToast(this.stateResult.message.toString());
            this.netWorkInfoAdapter.setList(null);
            return;
        }
        if (!ssidEquals(this.stateResult.ssid, BaseApplication.getInstance().getDataStore().getNetName())) {
            ToastMaker.showShortToast("配置的路由与连接的路由不一致");
            this.netWorkInfoAdapter.setList(null);
            return;
        }
        if (BaseApplication.getInstance().getDataStore().isApModel()) {
            Log.i(this.TAG, "HostAddress" + this.stateResult.serverAddress.getHostAddress());
            if (this.stateResult.ssid.toLowerCase().startsWith(this.type == 1 ? "k7" : "x4")) {
                NetWorkInfo netWorkInfo = new NetWorkInfo();
                netWorkInfo.setSsidName(this.stateResult.ssid);
                netWorkInfo.setServerAddress(this.stateResult.serverAddress.getHostAddress());
                this.netWorkInfoAdapter.addData(netWorkInfo);
                connectServer(netWorkInfo);
                return;
            }
            return;
        }
        if (this.type == 1) {
            x4 = BaseApplication.getInstance().getDataStore().getK7();
        } else {
            x4 = BaseApplication.getInstance().getDataStore().getX4();
        }
        List list = (List) new Gson().fromJson(x4, new TypeToken<List<NetWorkInfo>>() { // from class: com.nemo.caideng.ControlModelActivity2.2
        }.getType());
        this.netWorkInfoAdapter.setList(list);
        if (list.size() > 0) {
            connectServer((NetWorkInfo) list.get(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConnectWifi(String str, boolean z) {
        for (NetWorkInfo netWorkInfo : this.netWorkInfoAdapter.getData()) {
            netWorkInfo.setConnected(false);
            if (TextUtils.equals(netWorkInfo.getServerAddress(), str) && z) {
                netWorkInfo.setConnected(true);
            }
        }
        this.netWorkInfoAdapter.notifyDataSetChanged();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        BaseApplication.getInstance().removeBroadcastObserver(this);
        this.handler.removeCallbacksAndMessages(null);
        this.sendHandler.removeCallbacksAndMessages(null);
        this.sendHandlerThread.quit();
        disSocket();
        super.onDestroy();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        if (System.currentTimeMillis() - this.click_time < this.CLICK_INTERVAL) {
            return;
        }
        this.click_time = System.currentTimeMillis();
        connectServer(this.netWorkInfoAdapter.getData().get(i));
    }

    private void connectServer(NetWorkInfo netWorkInfo) {
        if (netWorkInfo.isConnected()) {
            return;
        }
        showDialog("连接建立中...");
        this.handler.obtainMessage(4, netWorkInfo).sendToTarget();
    }

    private void showDialog(String str) {
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

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.currentTimeLuminance = this.timeLuminanceAdapter.getData().get(i);
        if (this.timeLuminanceDialog == null) {
            this.timeLuminanceDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_seek_picker).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(80).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$HXNFeXhqkMtpgvlhiKePzSzF7_0
                @Override // per.goweii.anylayer.Layer.DataBinder
                public final void bindData(Layer layer) {
                    this.f$0.lambda$onItemChildClick$12$ControlModelActivity2(layer);
                }
            });
        }
        this.timeLuminanceDialog.show();
        ((TimeWheelView) this.timeLuminanceDialog.getView(C0575R.id.twv_time)).setTime(this.currentTimeLuminance.getHour(), this.currentTimeLuminance.getMinute(), 0);
        ((AllSeekNumView) this.timeLuminanceDialog.getView(C0575R.id.asnv_seek)).setValues(this.currentTimeLuminance.getLuminanceValue());
    }

    public /* synthetic */ void lambda$onItemChildClick$12$ControlModelActivity2(Layer layer) {
        final TimeWheelView timeWheelView = (TimeWheelView) layer.getView(C0575R.id.twv_time);
        timeWheelView.hiddenSecond();
        timeWheelView.disableHour();
        final AllSeekNumView allSeekNumView = (AllSeekNumView) layer.getView(C0575R.id.asnv_seek);
        allSeekNumView.setEnable(this.type == 1 ? 6 : 3);
        Button button = (Button) layer.getView(C0575R.id.btn_preview);
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$rhT-KMVnbukF84Om85RGI4lKBB4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$10$ControlModelActivity2(timeWheelView, allSeekNumView, view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity2$WB_Z_fGxKgdjwReEkhzTMREL9J0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$11$ControlModelActivity2(allSeekNumView, view);
            }
        });
    }

    public /* synthetic */ void lambda$null$10$ControlModelActivity2(TimeWheelView timeWheelView, AllSeekNumView allSeekNumView, View view) {
        Log.i(Constant.TAG, "选择的时间:小时" + timeWheelView.getHour() + "分钟：" + timeWheelView.getMinute() + "秒数:" + timeWheelView.getSecond() + "亮度：" + allSeekNumView.getValues());
        this.currentTimeLuminance.setHour(timeWheelView.getHour());
        this.currentTimeLuminance.setMinute(timeWheelView.getMinute());
        this.currentTimeLuminance.setLuminanceValue(allSeekNumView.getValues());
        this.timeLuminanceAdapter.notifyDataSetChanged();
        this.timeLuminanceDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$11$ControlModelActivity2(AllSeekNumView allSeekNumView, View view) {
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
        this.activityControlBinding.btnDemonstration.setVisibility(8);
        if (z) {
            if (z2) {
                this.activityControlBinding.swAuto.setChecked(true);
                this.activityControlBinding.asnvSeek.setVisibility(8);
                this.activityControlBinding.lineChart.setVisibility(0);
                this.activityControlBinding.btnDemonstration.setVisibility(0);
            }
            this.activityControlBinding.btnTimeSet.setEnabled(true);
            this.activityControlBinding.btnSave.setEnabled(true);
            this.activityControlBinding.btnReset.setEnabled(true);
            this.activityControlBinding.btnZero.setEnabled(true);
            this.activityControlBinding.swAuto.setEnabled(true);
            this.activityControlBinding.btnSyncTime.setEnabled(true);
            this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
        }
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(String str) {
        this.stateResult = check();
        configNet();
    }

    @Override // com.nemo.caideng.widget.AllSeekNumView.ValueListener
    public void onValueChange(byte[] bArr) {
        sendData(CommandUtil.handModelLuminance(bArr));
    }
}
