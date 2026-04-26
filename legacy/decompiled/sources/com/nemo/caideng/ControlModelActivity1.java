package com.nemo.caideng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import androidx.appcompat.app.ActionBar;
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
import com.nemo.caideng.util.HexStringUtil;
import com.nemo.caideng.util.QRCode;
import com.nemo.caideng.util.SharedPreferencesUtil;
import com.nemo.caideng.util.ToastMaker;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.CorrugationView;
import com.nemo.caideng.widget.TimeWheelView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;

/* JADX INFO: loaded from: classes.dex */
public class ControlModelActivity1 extends BaseActivity implements View.OnClickListener, OnItemClickListener, OnItemChildClickListener, Observer<String>, AllSeekNumView.ValueListener {
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
    private Handler handler = new HandlerC05661(Looper.getMainLooper());

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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        BaseApplication.getInstance().removeBroadcastObserver(this);
        this.handler.removeCallbacksAndMessages(null);
        this.sendHandler.removeCallbacksAndMessages(null);
        this.sendHandlerThread.quit();
        disSocket();
        super.onDestroy();
    }

    public static void startActivity(Activity activity, int i, int i2) {
        Intent intent = new Intent(activity, (Class<?>) ControlModelActivity1.class);
        intent.putExtra("type", i);
        intent.putExtra("model", i2);
        activity.startActivity(intent);
    }

    private void intiView() {
        this.activityControlBinding.rvWifiList.setLayoutManager(new LinearLayoutManager(this));
        this.activityControlBinding.rvWifiList.setAdapter(this.netWorkInfoAdapter);
        this.activityControlBinding.asnvSeek.setEnable(this.type == 1 ? 6 : 3);
        changeViewEnable(false, false);
    }

    private void initListener() {
        this.netWorkInfoAdapter.setOnItemClickListener(this);
        this.activityControlBinding.swAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$SOaGn5cIq9PBTC8fFkdrDGXwmSc
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initListener$0$ControlModelActivity1(compoundButton, z);
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
        this.activityControlBinding.cvProgress.setProgressListener(new CorrugationView.ProgressListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$QhmSeanLfwJhH-r9ccwAUb_tk4c
            @Override // com.nemo.caideng.widget.CorrugationView.ProgressListener
            public final void onFinished() {
                this.f$0.lambda$initListener$1$ControlModelActivity1();
            }
        });
        this.activityControlBinding.btnDemonstration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$EUwaoj_a087trW8xGA4vm8DyUCI
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initListener$2$ControlModelActivity1(compoundButton, z);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$ControlModelActivity1(CompoundButton compoundButton, boolean z) {
        if (this.activityControlBinding.btnDemonstration.isChecked()) {
            ToastMaker.showShortToast(getString(C0575R.string.close_preview_model));
        } else {
            changeViewEnable(true, z);
            sendData(CommandUtil.changeModel(z));
        }
    }

    public /* synthetic */ void lambda$initListener$1$ControlModelActivity1() {
        this.activityControlBinding.btnDemonstration.setChecked(false);
    }

    public /* synthetic */ void lambda$initListener$2$ControlModelActivity1(CompoundButton compoundButton, boolean z) {
        if (z) {
            this.activityControlBinding.cvProgress.startCorrugation();
            this.activityControlBinding.btnDemonstration.setText(getString(C0575R.string.close_preview));
        } else {
            this.activityControlBinding.cvProgress.stopCorrugation();
            this.activityControlBinding.btnDemonstration.setText(getString(C0575R.string.open_preview));
        }
        demonstrationChange(z);
        sendData(CommandUtil.openDemonstration(z));
    }

    private void demonstrationChange(boolean z) {
        if (z) {
            this.activityControlBinding.btnReset.setEnabled(false);
            this.activityControlBinding.btnZero.setEnabled(false);
            this.activityControlBinding.btnSave.setEnabled(false);
            this.activityControlBinding.btnTimeSet.setEnabled(false);
            return;
        }
        this.activityControlBinding.btnReset.setEnabled(true);
        this.activityControlBinding.btnZero.setEnabled(true);
        this.activityControlBinding.btnSave.setEnabled(true);
        this.activityControlBinding.btnTimeSet.setEnabled(true);
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
        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);
        axisRight.setAxisLineWidth(2.0f);
        axisRight.setAxisLineColor(getResources().getColor(C0575R.color.track_track_no_check));
        lineChart.setNoDataText(getString(C0575R.string.no_data));
    }

    /* JADX INFO: renamed from: com.nemo.caideng.ControlModelActivity1$1 */
    class HandlerC05661 extends Handler {
        HandlerC05661(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00ef  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00f4  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x012e  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void dispatchMessage(android.os.Message r14) {
            /*
                Method dump skipped, instruction units count: 598
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nemo.caideng.ControlModelActivity1.HandlerC05661.dispatchMessage(android.os.Message):void");
        }

        public /* synthetic */ void lambda$dispatchMessage$0$ControlModelActivity1$1() {
            ControlModelActivity1.this.syncTime();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncTime() {
        sendData(CommandUtil.syncTime((byte) this.activityControlBinding.dcTime.getHour(), (byte) this.activityControlBinding.dcTime.getMinute(), (byte) this.activityControlBinding.dcTime.getSecond()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void disSocket() {
        if (this.connectThread != null) {
            this.connectThread.close();
        }
        this.connectThread = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void sendData(final byte[] bArr) {
        if (this.connectThread != null) {
            this.sendHandler.post(new Runnable() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$31U-1OwvRnKNceTQzLkyGiUkzj8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendData$3$ControlModelActivity1(bArr);
                }
            });
        }
    }

    public /* synthetic */ void lambda$sendData$3$ControlModelActivity1(byte[] bArr) {
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
            float x = arrayList.get(0).getX();
            float y = arrayList.get(0).getY();
            float x2 = 24.0f - arrayList.get(arrayList.size() - 1).getX();
            float y2 = arrayList.get(arrayList.size() - 1).getY();
            float f = y2 + ((x2 / (x + x2)) * (y - y2));
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new Entry(0.0f, f));
            arrayList2.addAll(arrayList);
            arrayList2.add(new Entry(24.0f, f));
            lineData.addDataSet(generateLineDataBackSet(arrayList2, this.lineColors[i2]));
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
        lineDataSet.setDrawValues(false);
        return lineDataSet;
    }

    private LineDataSet generateLineDataBackSet(List<Entry> list, int i) {
        LineDataSet lineDataSet = new LineDataSet(list, "");
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setColor(i);
        lineDataSet.setLineWidth(2.0f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
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
        SharedPreferencesUtil.getInstance(this).putString(Constant.TEMP, null);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.activityControlBinding.btnDemonstration.isChecked()) {
            ToastMaker.showShortToast(getString(C0575R.string.close_preview_model));
        }
        switch (view.getId()) {
            case C0575R.id.btn_reset /* 2131230818 */:
                if (this.resetDialog == null) {
                    this.resetDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_reset).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$u9OGpIuKX8HuS_El0mPwNj4UUBE
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$7$ControlModelActivity1(layer);
                        }
                    });
                }
                this.resetDialog.show();
                break;
            case C0575R.id.btn_save /* 2131230819 */:
                ToastMaker.showShortToast(getResources().getString(C0575R.string.save_success));
                sendData(CommandUtil.allSet((byte) this.activityControlBinding.dcTime.getHour(), (byte) this.activityControlBinding.dcTime.getMinute(), (byte) this.activityControlBinding.dcTime.getSecond(), this.activityControlBinding.asnvSeek.getValues(), this.timeLuminanceAdapter.getData(), this.activityControlBinding.swAuto.isChecked()));
                updateChart();
                SharedPreferencesUtil.getInstance(this).putString(this.netWorkInfoAdapter.getData().get(0).getSsidName() + "#" + this.type + "#" + this.model, makeLightsValue());
                SharedPreferencesUtil.getInstance(this).putString(this.netWorkInfoAdapter.getData().get(0).getSsidName(), this.type + "#" + this.model);
                break;
            case C0575R.id.btn_sync_time /* 2131230821 */:
                if (this.timePickerDialog == null) {
                    this.timePickerDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_picker).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$2syKaZhU_paD7nu3B3WSnuPfiqE
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$5$ControlModelActivity1(layer);
                        }
                    });
                }
                this.timePickerDialog.show();
                ((TimeWheelView) this.timePickerDialog.getView(C0575R.id.twv_time)).setTime(0, 0, 0);
                break;
            case C0575R.id.btn_time_set /* 2131230822 */:
                if (this.activityControlBinding.swAuto.isChecked()) {
                    if (this.timeLuminanceListDialog == null) {
                        this.timeLuminanceListDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_luminance_list1).cancelableOnTouchOutside(false).cancelableOnClickKeyBack(false).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_close).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$Qb3CiTQ0RB3ByLIQRIRL3BIc-pE
                            @Override // per.goweii.anylayer.Layer.DataBinder
                            public final void bindData(Layer layer) {
                                this.f$0.lambda$onClick$10$ControlModelActivity1(layer);
                            }
                        });
                    }
                    this.timeLuminanceListDialog.show();
                } else {
                    ToastMaker.showShortToast(getString(C0575R.string.cant_set_time_light));
                }
                break;
            case C0575R.id.btn_zero /* 2131230823 */:
                if (this.zeroDialog == null) {
                    this.zeroDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_zero).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(17).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$oecG3r1h0f_pqiSab58VR0LUfPI
                        @Override // per.goweii.anylayer.Layer.DataBinder
                        public final void bindData(Layer layer) {
                            this.f$0.lambda$onClick$9$ControlModelActivity1(layer);
                        }
                    });
                }
                this.zeroDialog.show();
                break;
        }
    }

    public /* synthetic */ void lambda$onClick$5$ControlModelActivity1(Layer layer) {
        final TimeWheelView timeWheelView = (TimeWheelView) layer.getView(C0575R.id.twv_time);
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$THzwl_CRLlm8sz1h3wMsfoyYYws
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$4$ControlModelActivity1(timeWheelView, view);
            }
        });
    }

    public /* synthetic */ void lambda$null$4$ControlModelActivity1(TimeWheelView timeWheelView, View view) {
        Log.i(Constant.TAG, "选择的时间:小时" + timeWheelView.getHour() + "分钟：" + timeWheelView.getMinute() + "秒数:" + timeWheelView.getSecond());
        sendData(CommandUtil.syncTime((byte) timeWheelView.getHour(), (byte) timeWheelView.getMinute(), (byte) timeWheelView.getSecond()));
        this.timePickerDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$7$ControlModelActivity1(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$ySeVMnGHUJJt4QZoKNtGfLTNg88
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$6$ControlModelActivity1(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$6$ControlModelActivity1(View view) {
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

    public /* synthetic */ void lambda$onClick$9$ControlModelActivity1(Layer layer) {
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$zk4PZ2O-H02-QVDfY8gyQUMACkc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$8$ControlModelActivity1(view);
            }
        });
    }

    public /* synthetic */ void lambda$null$8$ControlModelActivity1(View view) {
        zero();
        this.zeroDialog.dismiss();
    }

    public /* synthetic */ void lambda$onClick$10$ControlModelActivity1(Layer layer) {
        ((RecyclerView) layer.getView(C0575R.id.rv_time_luminance_list)).setAdapter(this.timeLuminanceAdapter);
    }

    private String makeLightsValue() {
        String strByteArrayToHex = HexStringUtil.byteArrayToHex(this.activityControlBinding.asnvSeek.getValues());
        StringBuilder sb = new StringBuilder();
        byte[] bArr = new byte[8];
        for (TimeLuminance timeLuminance : this.timeLuminanceAdapter.getData()) {
            bArr[0] = (byte) timeLuminance.getHour();
            bArr[1] = (byte) timeLuminance.getMinute();
            for (int i = 0; i < timeLuminance.getLuminanceValue().length; i++) {
                bArr[i + 2] = timeLuminance.getLuminanceValue()[i];
            }
            sb.append(HexStringUtil.byteArrayToHex(bArr));
        }
        return strByteArrayToHex + "#" + sb.toString();
    }

    private void zero() {
        if (this.activityControlBinding.swAuto.isChecked()) {
            int i = 0;
            for (TimeLuminance timeLuminance : this.timeLuminanceAdapter.getData()) {
                timeLuminance.reSet();
                timeLuminance.setHour(i);
                i++;
            }
            this.timeLuminanceAdapter.notifyDataSetChanged();
            return;
        }
        this.activityControlBinding.asnvSeek.reSet();
    }

    private void configNet() {
        String x4;
        changeViewEnable(false, false);
        this.netWorkInfoAdapter.setList(null);
        if (!this.stateResult.wifiConnected) {
            ToastMaker.showShortToast(this.stateResult.message.toString());
            if (this.timeLuminanceAdapter.getData() == null || this.timeLuminanceAdapter.getData().size() == 0) {
                return;
            }
            SharedPreferencesUtil.getInstance(this).putString(Constant.TEMP, makeLightsValue());
            return;
        }
        if (!ssidEquals(this.stateResult.ssid, BaseApplication.getInstance().getDataStore().getNetName())) {
            ToastMaker.showShortToast(getString(C0575R.string.network_not_same));
            return;
        }
        if (BaseApplication.getInstance().getDataStore().isApModel()) {
            if (this.stateResult.serverAddress != null) {
                Log.i(this.TAG, "HostAddress" + this.stateResult.serverAddress.getHostAddress());
                if (this.stateResult.ssid.toLowerCase().startsWith(this.type == 1 ? "k7_" : "k7m")) {
                    NetWorkInfo netWorkInfo = new NetWorkInfo();
                    netWorkInfo.setSsidName(this.stateResult.ssid);
                    netWorkInfo.setServerAddress(this.stateResult.serverAddress.getHostAddress());
                    this.netWorkInfoAdapter.addData(netWorkInfo);
                    connectServer(netWorkInfo);
                    return;
                }
                return;
            }
            ToastMaker.showShortToast(getString(C0575R.string.please_net_connect));
            return;
        }
        if (this.type == 1) {
            x4 = BaseApplication.getInstance().getDataStore().getK7();
        } else {
            x4 = BaseApplication.getInstance().getDataStore().getX4();
        }
        List list = (List) new Gson().fromJson(x4, new TypeToken<List<NetWorkInfo>>() { // from class: com.nemo.caideng.ControlModelActivity1.2
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

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        if (this.activityControlBinding.btnDemonstration.isChecked()) {
            ToastMaker.showShortToast(getString(C0575R.string.close_preview_model));
        } else {
            if (System.currentTimeMillis() - this.click_time < this.CLICK_INTERVAL) {
                return;
            }
            this.click_time = System.currentTimeMillis();
            connectServer(this.netWorkInfoAdapter.getData().get(i));
        }
    }

    private void connectServer(NetWorkInfo netWorkInfo) {
        String string;
        ActionBar supportActionBar = getSupportActionBar();
        if ("k7m".equals(netWorkInfo.getSsidName().substring(0, 3).toLowerCase())) {
            string = "K7 MINI";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("K7 ");
            int i = this.model;
            sb.append(i == 1 ? "SPS" : i == 2 ? "LPS" : "S/L");
            string = sb.toString();
        }
        supportActionBar.setTitle(string);
        if (netWorkInfo.isConnected()) {
            return;
        }
        showDialog(getString(C0575R.string.connecting));
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
        try {
            if (this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
            }
        } catch (Exception unused) {
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
            this.timeLuminanceDialog = AnyLayer.dialog(this).contentView(C0575R.layout.dialog_time_seek_picker).cancelableOnTouchOutside(true).cancelableOnClickKeyBack(true).backgroundDimAmount(0.3f).gravity(80).onClickToDismiss(C0575R.id.btn_cancel).bindData(new Layer.DataBinder() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$jYak7xKjBx_hFKnFYeTCbc4bLp0
                @Override // per.goweii.anylayer.Layer.DataBinder
                public final void bindData(Layer layer) {
                    this.f$0.lambda$onItemChildClick$14$ControlModelActivity1(layer);
                }
            });
        }
        this.timeLuminanceDialog.show();
        ((TimeWheelView) this.timeLuminanceDialog.getView(C0575R.id.twv_time)).setTime(this.currentTimeLuminance.getHour(), this.currentTimeLuminance.getMinute(), 0);
        ((AllSeekNumView) this.timeLuminanceDialog.getView(C0575R.id.asnv_seek)).setValues(this.currentTimeLuminance.getLuminanceValue());
    }

    public /* synthetic */ void lambda$onItemChildClick$14$ControlModelActivity1(Layer layer) {
        final TimeWheelView timeWheelView = (TimeWheelView) layer.getView(C0575R.id.twv_time);
        timeWheelView.hiddenSecond();
        timeWheelView.disableHour();
        final AllSeekNumView allSeekNumView = (AllSeekNumView) layer.getView(C0575R.id.asnv_seek);
        allSeekNumView.setEnable(this.type == 1 ? 6 : 3);
        Button button = (Button) layer.getView(C0575R.id.btn_preview);
        ((Button) layer.getView(C0575R.id.btn_sure)).setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$Ub7JDChrCjOfkaqFw7dFsw0WrxM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$11$ControlModelActivity1(timeWheelView, allSeekNumView, view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$agBkOZg_VBb7W8iVnP-6hDSyCoU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$null$12$ControlModelActivity1(allSeekNumView, view);
            }
        });
        allSeekNumView.setValueListener(new AllSeekNumView.ValueListener() { // from class: com.nemo.caideng.-$$Lambda$ControlModelActivity1$aRvUGiMrgiO9NtG7FZ7VKt0M39k
            @Override // com.nemo.caideng.widget.AllSeekNumView.ValueListener
            public final void onValueChange(byte[] bArr) {
                this.f$0.lambda$null$13$ControlModelActivity1(allSeekNumView, bArr);
            }
        });
    }

    public /* synthetic */ void lambda$null$11$ControlModelActivity1(TimeWheelView timeWheelView, AllSeekNumView allSeekNumView, View view) {
        Log.i(Constant.TAG, "选择的时间:小时" + timeWheelView.getHour() + "分钟：" + timeWheelView.getMinute() + "秒数:" + timeWheelView.getSecond() + "亮度：" + allSeekNumView.getValues());
        this.currentTimeLuminance.setHour(timeWheelView.getHour());
        this.currentTimeLuminance.setMinute(timeWheelView.getMinute());
        this.currentTimeLuminance.setLuminanceValue(allSeekNumView.getValues());
        this.timeLuminanceAdapter.notifyDataSetChanged();
        this.timeLuminanceDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$12$ControlModelActivity1(AllSeekNumView allSeekNumView, View view) {
        sendData(CommandUtil.previewModelLuminance(allSeekNumView.getValues()));
    }

    public /* synthetic */ void lambda$null$13$ControlModelActivity1(AllSeekNumView allSeekNumView, byte[] bArr) {
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
        this.activityControlBinding.cvProgress.setVisibility(8);
        this.activityControlBinding.btnDemonstration.setVisibility(8);
        if (z) {
            if (z2) {
                this.activityControlBinding.swAuto.setChecked(true);
                this.activityControlBinding.asnvSeek.setVisibility(8);
                this.activityControlBinding.lineChart.setVisibility(0);
                this.activityControlBinding.cvProgress.setVisibility(0);
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

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0575R.menu.data_menu, menu);
        return true;
    }

    @Override // com.nemo.caideng.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (!this.activityControlBinding.btnSave.isEnabled()) {
            return true;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            finish();
        } else if (itemId == C0575R.id.export) {
            String strMakeLightsValue = makeLightsValue();
            int i = this.model;
            String str = this.netWorkInfoAdapter.getData().get(0).getSsidName() + "_" + (i == 1 ? "SPS" : i == 2 ? "LPS" : "S/L");
            MediaStore.Images.Media.insertImage(getContentResolver(), QRCode.createQRCodeWithLogo5(strMakeLightsValue, 400, ((BitmapDrawable) getDrawable(C0575R.mipmap.icon_noo_logo)).getBitmap(), str), str, (String) null);
            ToastMaker.showShortToast(getResources().getString(C0575R.string.export_success));
        } else if (itemId == C0575R.id.input) {
            startActivityForResult(new Intent(this, (Class<?>) OptionsScannerActivity.class), 1);
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.activityControlBinding.btnSave.isEnabled() && i2 == -1 && i == 1) {
            try {
                String stringExtra = intent.getStringExtra("content");
                byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte.class, 24, 8);
                String[] strArrSplit = stringExtra.split("#");
                byte[] bArrHexToByteArray = HexStringUtil.hexToByteArray(strArrSplit[0]);
                byte[] bArrHexToByteArray2 = HexStringUtil.hexToByteArray(strArrSplit[1]);
                for (int i3 = 0; i3 < 24; i3++) {
                    for (int i4 = 0; i4 < 8; i4++) {
                        bArr[i3][i4] = bArrHexToByteArray2[(i3 * 8) + i4];
                    }
                }
                this.activityControlBinding.asnvSeek.setValues(bArrHexToByteArray);
                this.timeLuminanceAdapter.setList(null);
                for (int i5 = 0; i5 < 24; i5++) {
                    TimeLuminance timeLuminance = new TimeLuminance();
                    timeLuminance.setHour(bArr[i5][0]);
                    timeLuminance.setMinute(bArr[i5][1]);
                    byte[] bArr2 = new byte[6];
                    System.arraycopy(bArr[i5], 2, bArr2, 0, 6);
                    timeLuminance.setLuminanceValue(bArr2);
                    this.timeLuminanceAdapter.addData(timeLuminance);
                }
                updateChart();
            } catch (Exception unused) {
                ToastMaker.showShortToast(getString(C0575R.string.data_analysis_fail));
            }
        }
    }
}
