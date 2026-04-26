package com.nemo.caideng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
import com.nemo.caideng.databinding.ActivityControlModelBinding;
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
public class ControlModelActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener, OnItemChildClickListener, Observer<String>, AllSeekNumView.ValueListener {
    private static final int PORT = 8266;
    ActivityControlModelBinding activityControlBinding;
    private long click_time;
    private ConnectThread connectThread;
    private TimeLuminance currentTimeLuminance;
    private Drawable executeDrawable;
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
    private Handler handler = new Handler() { // from class: com.nemo.caideng.ControlModelActivity.1
        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ControlModelActivity.this.dismissDialog();
                ControlModelActivity.this.sendData(CommandUtil.allRead());
                ControlModelActivity.this.updateConnectWifi((String) message.obj, true);
                ControlModelActivity.this.changeViewEnable(true, false);
                ToastMaker.showLongToast("数据连接可用");
                return;
            }
            if (i == 2) {
                ControlModelActivity.this.dismissDialog();
                ControlModelActivity.this.updateConnectWifi((String) message.obj, false);
                ControlModelActivity.this.changeViewEnable(false, false);
                ToastMaker.showLongToast("数据连接不可用");
                ControlModelActivity.this.disSocket();
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    ControlModelActivity.this.sendData((byte[]) message.obj);
                    return;
                } else {
                    NetWorkInfo netWorkInfo = (NetWorkInfo) message.obj;
                    ControlModelActivity.this.disSocket();
                    ControlModelActivity.this.connectThread = new ConnectThread(netWorkInfo.getServerAddress(), ControlModelActivity.PORT, ControlModelActivity.this.handler);
                    ControlModelActivity.this.connectThread.start();
                    return;
                }
            }
            ControlModelActivity.this.timeLuminanceAdapter.setList(null);
            byte[] bArr = (byte[]) message.obj;
            byte[] bArr2 = new byte[6];
            System.arraycopy(bArr, 4, bArr2, 0, 6);
            ControlModelActivity.this.activityControlBinding.asnvSeek.setValues(bArr2);
            int i2 = 11;
            int i3 = 0;
            while (i3 < 24) {
                TimeLuminance timeLuminance = new TimeLuminance();
                timeLuminance.setHour(bArr[i2]);
                timeLuminance.setMinute(bArr[i2 + 1]);
                byte[] bArr3 = new byte[6];
                System.arraycopy(bArr, i2 + 2, bArr3, 0, 6);
                timeLuminance.setLuminanceValue(bArr3);
                ControlModelActivity.this.timeLuminanceAdapter.addData(timeLuminance);
                i3++;
                i2 += 8;
            }
            ControlModelActivity.this.updateChart();
            if (bArr[202] == 1) {
                ControlModelActivity.this.changeViewEnable(true, true);
            }
            ControlModelActivity.this.syncTime();
        }
    };

    public static void startActivity(Activity activity, int i, int i2) {
        Intent intent = new Intent(activity, (Class<?>) ControlModelActivity.class);
        intent.putExtra("type", i);
        intent.putExtra("model", i2);
        activity.startActivity(intent);
    }

    @Override // com.nemo.caideng.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityControlModelBinding activityControlModelBindingInflate = ActivityControlModelBinding.inflate(getLayoutInflater());
        this.activityControlBinding = activityControlModelBindingInflate;
        setContentView(activityControlModelBindingInflate.getRoot());
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
        this.executeDrawable = getDrawable(C0575R.drawable.shape_execute);
    }

    private void initListener() {
        this.netWorkInfoAdapter.setOnItemClickListener(this);
        this.activityControlBinding.swAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$ImKnvVXyZPMMgBJAD-2LwNGn94k
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initListener$0$ControlModelActivity(compoundButton, z);
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
        this.activityControlBinding.tvHand.setOnClickListener(this);
        this.activityControlBinding.tvAuto.setOnClickListener(this);
        this.activityControlBinding.tvHandExecute.setOnClickListener(this);
        this.activityControlBinding.tvAutoExecute.setOnClickListener(this);
        this.activityControlBinding.btnDemonstration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$0imF7MOno7cVc3YgT3UEm8ZERXY
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initListener$1$ControlModelActivity(compoundButton, z);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$ControlModelActivity(CompoundButton compoundButton, boolean z) {
        if (z) {
            this.activityControlBinding.tvAutoExecute.setBackgroundResource(C0575R.drawable.shape_execute);
            this.activityControlBinding.tvHandExecute.setBackground(null);
        } else {
            this.activityControlBinding.tvHandExecute.setBackgroundResource(C0575R.drawable.shape_execute);
            this.activityControlBinding.tvAutoExecute.setBackground(null);
        }
        sendData(CommandUtil.changeModel(z));
    }

    public /* synthetic */ void lambda$initListener$1$ControlModelActivity(CompoundButton compoundButton, boolean z) {
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
            this.sendHandler.post(new Runnable() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$SSJCd9Lyk-zwn5ezSxCXF4K7uSo
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendData$2$ControlModelActivity(bArr);
                }
            });
        }
    }

    public /* synthetic */ void lambda$sendData$2$ControlModelActivity(byte[] bArr) {
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
        int id = view.getId();
        switch (id) {
            case C0575R.id.btn_reset /* 2131230818 */:
                if (this.resetDialog == null) {
                    this.resetDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_reset).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$s0401Iyv4wD9tEpjnfA48ULIKfc
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$4$ControlModelActivity(layer);
                        }
                    });
                }
                this.resetDialog.show();
                break;
            case C0575R.id.btn_save /* 2131230819 */:
                if (this.activityControlBinding.clAutoLayout.getVisibility() == 0) {
                    sendData(CommandUtil.allSet((byte) this.activityControlBinding.dcTime.getHour(), (byte) this.activityControlBinding.dcTime.getMinute(), (byte) this.activityControlBinding.dcTime.getSecond(), this.activityControlBinding.asnvSeek.getValues(), this.timeLuminanceAdapter.getData(), this.activityControlBinding.swAuto.isChecked()));
                    updateChart();
                } else {
                    sendData(CommandUtil.handModelLuminance(this.activityControlBinding.asnvSeek.getValues()));
                }
                showMainContent();
                break;
            default:
                switch (id) {
                    case C0575R.id.btn_sync_time /* 2131230821 */:
                        syncTime();
                        break;
                    case C0575R.id.btn_time_set /* 2131230822 */:
                        if (this.timeLuminanceListDialog == null) {
                            this.timeLuminanceListDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_luminance_list).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClick(new Layer.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$VthRmcw-ZJwZZCTKm78uoqKhNLM
                                @Override // per.goweii.anylayer.Layer.OnClickListener
                                public final void onClick(Layer layer, View view2) {
                                    this.f$0.lambda$onClick$7$ControlModelActivity(layer, view2);
                                }
                            }, C0575R.id.btn_save).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$bN9dB6fXeHKVXRcoav0zFCBiiyo
                                @Override // per.goweii.anylayer.Layer.DataBinder
                                public final void bindData(Layer layer) {
                                    this.f$0.lambda$onClick$8$ControlModelActivity(layer);
                                }
                            });
                        }
                        this.timeLuminanceListDialog.show();
                        break;
                    case C0575R.id.btn_zero /* 2131230823 */:
                        if (this.zeroDialog == null) {
                            this.zeroDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_zero).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$QzjnJFM_QxetJGIYFpLZ4iCeA1Q
                                @Override // per.goweii.anylayer.Layer.DataBinder
                                public final void bindData(Layer layer) {
                                    this.f$0.lambda$onClick$6$ControlModelActivity(layer);
                                }
                            });
                        }
                        this.zeroDialog.show();
                        break;
                    default:
                        switch (id) {
                            case C0575R.id.tv_auto /* 2131231018 */:
                                this.activityControlBinding.clAutoLayout.setVisibility(0);
                                this.activityControlBinding.llActionLayout.setVisibility(0);
                                break;
                            case C0575R.id.tv_auto_execute /* 2131231019 */:
                                this.activityControlBinding.swAuto.setChecked(true);
                                break;
                            case C0575R.id.tv_hand /* 2131231020 */:
                                this.activityControlBinding.clHandLayout.setVisibility(0);
                                this.activityControlBinding.llActionLayout.setVisibility(8);
                                break;
                            case C0575R.id.tv_hand_execute /* 2131231021 */:
                                this.activityControlBinding.swAuto.setChecked(false);
                                break;
                        }
                        break;
                }
                break;
        }
    }

    public /* synthetic */ void lambda$onClick$4$ControlModelActivity(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$IrzbGOZW_GzU7kl4iHvO9lczHdE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$3$ControlModelActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$3$ControlModelActivity(View view) {
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

    public /* synthetic */ void lambda$onClick$6$ControlModelActivity(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$BDcg5DHeWlgmmqTLQFm1CUc90JM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$5$ControlModelActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$5$ControlModelActivity(View view) {
        zero();
        this.zeroDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$7$ControlModelActivity(Layer layer, View view) {
        layer.dismiss();
        this.activityControlBinding.btnSave.callOnClick();
    }

    public /* synthetic */ void lambda$onClick$8$ControlModelActivity(Layer layer) {
        ((RecyclerView) layer.getView(C0575R.id.rv_time_luminance_list)).setAdapter(this.timeLuminanceAdapter);
    }

    private void zero() {
        if (this.activityControlBinding.clAutoLayout.getVisibility() == 0) {
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
        List list = (List) new Gson().fromJson(x4, new TypeToken<List<NetWorkInfo>>() { // from class: com.nemo.caideng.ControlModelActivity.2
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
            this.timeLuminanceDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_seek_picker).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(80).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$lQJDqYFmAMrFmgUxCiT5ZOJkLso
                @Override // per.goweii.anylayer.Layer.DataBinder
                public final void bindData(Layer layer) {
                    this.f$0.lambda$onItemChildClick$11$ControlModelActivity(layer);
                }
            });
        }
        this.timeLuminanceDialog.show();
        ((TimeWheelView) this.timeLuminanceDialog.getView(C0575R.id.twv_time)).setTime(this.currentTimeLuminance.getHour(), this.currentTimeLuminance.getMinute(), 0);
        ((AllSeekNumView) this.timeLuminanceDialog.getView(C0575R.id.asnv_seek)).setValues(this.currentTimeLuminance.getLuminanceValue());
    }

    public /* synthetic */ void lambda$onItemChildClick$11$ControlModelActivity(Layer layer) {
        final TimeWheelView timeWheelView = (TimeWheelView) layer.getView(C0575R.id.twv_time);
        timeWheelView.hiddenSecond();
        timeWheelView.disableHour();
        final AllSeekNumView allSeekNumView = (AllSeekNumView) layer.getView(C0575R.id.asnv_seek);
        allSeekNumView.setEnable(this.type == 1 ? 6 : 3);
        Button button = (Button) layer.getView(C0575R.id.btn_preview);
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$Bh-rBvo7Mi2vgRY6-cEt7i5t8DU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$9$ControlModelActivity(timeWheelView, allSeekNumView, view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity$O0R_xD6A0QzauNUO8u_q4TZqTrc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$10$ControlModelActivity(allSeekNumView, view);
            }
        });
    }

    public /* synthetic */ void lambda$null$9$ControlModelActivity(TimeWheelView timeWheelView, AllSeekNumView allSeekNumView, View view) {
        Log.i(Constant.TAG, "选择的时间:小时" + timeWheelView.getHour() + "分钟：" + timeWheelView.getMinute() + "秒数:" + timeWheelView.getSecond() + "亮度：" + allSeekNumView.getValues());
        this.currentTimeLuminance.setHour(timeWheelView.getHour());
        this.currentTimeLuminance.setMinute(timeWheelView.getMinute());
        this.currentTimeLuminance.setLuminanceValue(allSeekNumView.getValues());
        this.timeLuminanceAdapter.notifyDataSetChanged();
        this.timeLuminanceDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$10$ControlModelActivity(AllSeekNumView allSeekNumView, View view) {
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
        this.activityControlBinding.tvHand.setEnabled(false);
        this.activityControlBinding.tvHandExecute.setEnabled(false);
        this.activityControlBinding.tvAuto.setEnabled(false);
        this.activityControlBinding.tvAutoExecute.setEnabled(false);
        this.activityControlBinding.tvAutoExecute.setBackground(null);
        this.activityControlBinding.tvHandExecute.setBackground(null);
        this.activityControlBinding.btnDemonstration.setEnabled(false);
        this.activityControlBinding.clHandLayout.setVisibility(8);
        this.activityControlBinding.clAutoLayout.setVisibility(8);
        this.activityControlBinding.llActionLayout.setVisibility(8);
        this.activityControlBinding.asnvSeek.setEnable(0);
        if (z) {
            this.activityControlBinding.btnSyncTime.setEnabled(true);
            this.activityControlBinding.btnTimeSet.setEnabled(true);
            this.activityControlBinding.btnSave.setEnabled(true);
            this.activityControlBinding.btnReset.setEnabled(true);
            this.activityControlBinding.btnZero.setEnabled(true);
            this.activityControlBinding.swAuto.setEnabled(true);
            this.activityControlBinding.tvHand.setEnabled(true);
            this.activityControlBinding.tvHandExecute.setEnabled(true);
            this.activityControlBinding.tvAuto.setEnabled(true);
            this.activityControlBinding.tvAutoExecute.setEnabled(true);
            this.activityControlBinding.btnDemonstration.setEnabled(true);
            this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
            if (z2) {
                this.activityControlBinding.swAuto.setChecked(true);
                this.activityControlBinding.tvAutoExecute.setBackgroundResource(C0575R.drawable.shape_execute);
                this.activityControlBinding.tvHandExecute.setBackground(null);
            } else {
                this.activityControlBinding.swAuto.setChecked(false);
                this.activityControlBinding.tvAutoExecute.setBackground(null);
                this.activityControlBinding.tvHandExecute.setBackgroundResource(C0575R.drawable.shape_execute);
            }
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

    private void showMainContent() {
        this.activityControlBinding.clAutoLayout.setVisibility(8);
        this.activityControlBinding.clHandLayout.setVisibility(8);
        this.activityControlBinding.llActionLayout.setVisibility(8);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && (this.activityControlBinding.clHandLayout.getVisibility() == 0 || this.activityControlBinding.clAutoLayout.getVisibility() == 0)) {
            showMainContent();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.nemo.caideng.BaseActivity
    public void back() {
        if (this.activityControlBinding.clHandLayout.getVisibility() == 0 || this.activityControlBinding.clAutoLayout.getVisibility() == 0) {
            showMainContent();
        } else {
            super.back();
        }
    }
}
