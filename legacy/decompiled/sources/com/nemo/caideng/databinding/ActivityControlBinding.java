package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.DigitalClock;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityControlBinding implements ViewBinding {
    public final AllSeekNumView asnvSeek;
    public final Button btnReset;
    public final Button btnSave;
    public final Button btnSyncTime;
    public final Button btnTimeSet;
    public final Button btnZero;
    public final DigitalClock dcTime;
    public final LineChart lineChart;
    private final LinearLayout rootView;
    public final RecyclerView rvWifiList;
    public final Switch swAuto;

    private ActivityControlBinding(LinearLayout linearLayout, AllSeekNumView allSeekNumView, Button button, Button button2, Button button3, Button button4, Button button5, DigitalClock digitalClock, LineChart lineChart, RecyclerView recyclerView, Switch r11) {
        this.rootView = linearLayout;
        this.asnvSeek = allSeekNumView;
        this.btnReset = button;
        this.btnSave = button2;
        this.btnSyncTime = button3;
        this.btnTimeSet = button4;
        this.btnZero = button5;
        this.dcTime = digitalClock;
        this.lineChart = lineChart;
        this.rvWifiList = recyclerView;
        this.swAuto = r11;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityControlBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityControlBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_control, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityControlBinding bind(View view) {
        int i = C0575R.id.asnv_seek;
        AllSeekNumView allSeekNumView = (AllSeekNumView) view.findViewById(C0575R.id.asnv_seek);
        if (allSeekNumView != null) {
            i = C0575R.id.btn_reset;
            Button button = (Button) view.findViewById(C0575R.id.btn_reset);
            if (button != null) {
                i = C0575R.id.btn_save;
                Button button2 = (Button) view.findViewById(C0575R.id.btn_save);
                if (button2 != null) {
                    i = C0575R.id.btn_sync_time;
                    Button button3 = (Button) view.findViewById(C0575R.id.btn_sync_time);
                    if (button3 != null) {
                        i = C0575R.id.btn_time_set;
                        Button button4 = (Button) view.findViewById(C0575R.id.btn_time_set);
                        if (button4 != null) {
                            i = C0575R.id.btn_zero;
                            Button button5 = (Button) view.findViewById(C0575R.id.btn_zero);
                            if (button5 != null) {
                                i = C0575R.id.dc_time;
                                DigitalClock digitalClock = (DigitalClock) view.findViewById(C0575R.id.dc_time);
                                if (digitalClock != null) {
                                    i = C0575R.id.line_chart;
                                    LineChart lineChart = (LineChart) view.findViewById(C0575R.id.line_chart);
                                    if (lineChart != null) {
                                        i = C0575R.id.rv_wifi_list;
                                        RecyclerView recyclerView = (RecyclerView) view.findViewById(C0575R.id.rv_wifi_list);
                                        if (recyclerView != null) {
                                            i = C0575R.id.sw_auto;
                                            Switch r13 = (Switch) view.findViewById(C0575R.id.sw_auto);
                                            if (r13 != null) {
                                                return new ActivityControlBinding((LinearLayout) view, allSeekNumView, button, button2, button3, button4, button5, digitalClock, lineChart, recyclerView, r13);
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
