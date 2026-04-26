package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.CorrugationView;
import com.nemo.caideng.widget.DigitalClock;
import com.nemo.caideng.widget.PreventRepeatButton;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityControlModel1Binding implements ViewBinding {
    public final AllSeekNumView asnvSeek;
    public final CheckBox btnDemonstration;
    public final PreventRepeatButton btnReset;
    public final PreventRepeatButton btnSave;
    public final PreventRepeatButton btnSyncTime;
    public final PreventRepeatButton btnTimeSet;
    public final PreventRepeatButton btnZero;
    public final CorrugationView cvProgress;
    public final DigitalClock dcTime;
    public final LineChart lineChart;
    public final LinearLayout llContentMain;
    private final FrameLayout rootView;
    public final RecyclerView rvWifiList;
    public final Switch swAuto;

    private ActivityControlModel1Binding(FrameLayout frameLayout, AllSeekNumView allSeekNumView, CheckBox checkBox, PreventRepeatButton preventRepeatButton, PreventRepeatButton preventRepeatButton2, PreventRepeatButton preventRepeatButton3, PreventRepeatButton preventRepeatButton4, PreventRepeatButton preventRepeatButton5, CorrugationView corrugationView, DigitalClock digitalClock, LineChart lineChart, LinearLayout linearLayout, RecyclerView recyclerView, Switch r14) {
        this.rootView = frameLayout;
        this.asnvSeek = allSeekNumView;
        this.btnDemonstration = checkBox;
        this.btnReset = preventRepeatButton;
        this.btnSave = preventRepeatButton2;
        this.btnSyncTime = preventRepeatButton3;
        this.btnTimeSet = preventRepeatButton4;
        this.btnZero = preventRepeatButton5;
        this.cvProgress = corrugationView;
        this.dcTime = digitalClock;
        this.lineChart = lineChart;
        this.llContentMain = linearLayout;
        this.rvWifiList = recyclerView;
        this.swAuto = r14;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ActivityControlModel1Binding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityControlModel1Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_control_model1, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityControlModel1Binding bind(View view) {
        int i = C0575R.id.asnv_seek;
        AllSeekNumView allSeekNumView = (AllSeekNumView) view.findViewById(C0575R.id.asnv_seek);
        if (allSeekNumView != null) {
            i = C0575R.id.btn_demonstration;
            CheckBox checkBox = (CheckBox) view.findViewById(C0575R.id.btn_demonstration);
            if (checkBox != null) {
                i = C0575R.id.btn_reset;
                PreventRepeatButton preventRepeatButton = (PreventRepeatButton) view.findViewById(C0575R.id.btn_reset);
                if (preventRepeatButton != null) {
                    i = C0575R.id.btn_save;
                    PreventRepeatButton preventRepeatButton2 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_save);
                    if (preventRepeatButton2 != null) {
                        i = C0575R.id.btn_sync_time;
                        PreventRepeatButton preventRepeatButton3 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_sync_time);
                        if (preventRepeatButton3 != null) {
                            i = C0575R.id.btn_time_set;
                            PreventRepeatButton preventRepeatButton4 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_time_set);
                            if (preventRepeatButton4 != null) {
                                i = C0575R.id.btn_zero;
                                PreventRepeatButton preventRepeatButton5 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_zero);
                                if (preventRepeatButton5 != null) {
                                    i = C0575R.id.cv_progress;
                                    CorrugationView corrugationView = (CorrugationView) view.findViewById(C0575R.id.cv_progress);
                                    if (corrugationView != null) {
                                        i = C0575R.id.dc_time;
                                        DigitalClock digitalClock = (DigitalClock) view.findViewById(C0575R.id.dc_time);
                                        if (digitalClock != null) {
                                            i = C0575R.id.line_chart;
                                            LineChart lineChart = (LineChart) view.findViewById(C0575R.id.line_chart);
                                            if (lineChart != null) {
                                                i = C0575R.id.ll_content_main;
                                                LinearLayout linearLayout = (LinearLayout) view.findViewById(C0575R.id.ll_content_main);
                                                if (linearLayout != null) {
                                                    i = C0575R.id.rv_wifi_list;
                                                    RecyclerView recyclerView = (RecyclerView) view.findViewById(C0575R.id.rv_wifi_list);
                                                    if (recyclerView != null) {
                                                        i = C0575R.id.sw_auto;
                                                        Switch r17 = (Switch) view.findViewById(C0575R.id.sw_auto);
                                                        if (r17 != null) {
                                                            return new ActivityControlModel1Binding((FrameLayout) view, allSeekNumView, checkBox, preventRepeatButton, preventRepeatButton2, preventRepeatButton3, preventRepeatButton4, preventRepeatButton5, corrugationView, digitalClock, lineChart, linearLayout, recyclerView, r17);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
