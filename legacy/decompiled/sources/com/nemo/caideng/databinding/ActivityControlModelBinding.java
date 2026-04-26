package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.DigitalClock;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityControlModelBinding implements ViewBinding {
    public final AllSeekNumView asnvSeek;
    public final CheckBox btnDemonstration;
    public final Button btnReset;
    public final Button btnSave;
    public final Button btnSyncTime;
    public final Button btnTimeSet;
    public final Button btnZero;
    public final ConstraintLayout clAutoLayout;
    public final ConstraintLayout clHandLayout;
    public final DigitalClock dcTime;
    public final Guideline line1;
    public final Guideline line2;
    public final Guideline line3;
    public final LineChart lineChart;
    public final LinearLayout llActionLayout;
    public final ConstraintLayout llContentMain;
    private final FrameLayout rootView;
    public final RecyclerView rvWifiList;
    public final Switch swAuto;
    public final TextView tvAuto;
    public final TextView tvAutoExecute;
    public final TextView tvHand;
    public final TextView tvHandExecute;

    private ActivityControlModelBinding(FrameLayout frameLayout, AllSeekNumView allSeekNumView, CheckBox checkBox, Button button, Button button2, Button button3, Button button4, Button button5, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, DigitalClock digitalClock, Guideline guideline, Guideline guideline2, Guideline guideline3, LineChart lineChart, LinearLayout linearLayout, ConstraintLayout constraintLayout3, RecyclerView recyclerView, Switch r21, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        this.rootView = frameLayout;
        this.asnvSeek = allSeekNumView;
        this.btnDemonstration = checkBox;
        this.btnReset = button;
        this.btnSave = button2;
        this.btnSyncTime = button3;
        this.btnTimeSet = button4;
        this.btnZero = button5;
        this.clAutoLayout = constraintLayout;
        this.clHandLayout = constraintLayout2;
        this.dcTime = digitalClock;
        this.line1 = guideline;
        this.line2 = guideline2;
        this.line3 = guideline3;
        this.lineChart = lineChart;
        this.llActionLayout = linearLayout;
        this.llContentMain = constraintLayout3;
        this.rvWifiList = recyclerView;
        this.swAuto = r21;
        this.tvAuto = textView;
        this.tvAutoExecute = textView2;
        this.tvHand = textView3;
        this.tvHandExecute = textView4;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ActivityControlModelBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityControlModelBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_control_model, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityControlModelBinding bind(View view) {
        int i = C0575R.id.asnv_seek;
        AllSeekNumView allSeekNumView = (AllSeekNumView) view.findViewById(C0575R.id.asnv_seek);
        if (allSeekNumView != null) {
            i = C0575R.id.btn_demonstration;
            CheckBox checkBox = (CheckBox) view.findViewById(C0575R.id.btn_demonstration);
            if (checkBox != null) {
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
                                    i = C0575R.id.cl_auto_layout;
                                    ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(C0575R.id.cl_auto_layout);
                                    if (constraintLayout != null) {
                                        i = C0575R.id.cl_hand_layout;
                                        ConstraintLayout constraintLayout2 = (ConstraintLayout) view.findViewById(C0575R.id.cl_hand_layout);
                                        if (constraintLayout2 != null) {
                                            i = C0575R.id.dc_time;
                                            DigitalClock digitalClock = (DigitalClock) view.findViewById(C0575R.id.dc_time);
                                            if (digitalClock != null) {
                                                i = C0575R.id.line1;
                                                Guideline guideline = (Guideline) view.findViewById(C0575R.id.line1);
                                                if (guideline != null) {
                                                    i = C0575R.id.line2;
                                                    Guideline guideline2 = (Guideline) view.findViewById(C0575R.id.line2);
                                                    if (guideline2 != null) {
                                                        i = C0575R.id.line3;
                                                        Guideline guideline3 = (Guideline) view.findViewById(C0575R.id.line3);
                                                        if (guideline3 != null) {
                                                            i = C0575R.id.line_chart;
                                                            LineChart lineChart = (LineChart) view.findViewById(C0575R.id.line_chart);
                                                            if (lineChart != null) {
                                                                i = C0575R.id.ll_action_layout;
                                                                LinearLayout linearLayout = (LinearLayout) view.findViewById(C0575R.id.ll_action_layout);
                                                                if (linearLayout != null) {
                                                                    i = C0575R.id.ll_content_main;
                                                                    ConstraintLayout constraintLayout3 = (ConstraintLayout) view.findViewById(C0575R.id.ll_content_main);
                                                                    if (constraintLayout3 != null) {
                                                                        i = C0575R.id.rv_wifi_list;
                                                                        RecyclerView recyclerView = (RecyclerView) view.findViewById(C0575R.id.rv_wifi_list);
                                                                        if (recyclerView != null) {
                                                                            i = C0575R.id.sw_auto;
                                                                            Switch r22 = (Switch) view.findViewById(C0575R.id.sw_auto);
                                                                            if (r22 != null) {
                                                                                i = C0575R.id.tv_auto;
                                                                                TextView textView = (TextView) view.findViewById(C0575R.id.tv_auto);
                                                                                if (textView != null) {
                                                                                    i = C0575R.id.tv_auto_execute;
                                                                                    TextView textView2 = (TextView) view.findViewById(C0575R.id.tv_auto_execute);
                                                                                    if (textView2 != null) {
                                                                                        i = C0575R.id.tv_hand;
                                                                                        TextView textView3 = (TextView) view.findViewById(C0575R.id.tv_hand);
                                                                                        if (textView3 != null) {
                                                                                            i = C0575R.id.tv_hand_execute;
                                                                                            TextView textView4 = (TextView) view.findViewById(C0575R.id.tv_hand_execute);
                                                                                            if (textView4 != null) {
                                                                                                return new ActivityControlModelBinding((FrameLayout) view, allSeekNumView, checkBox, button, button2, button3, button4, button5, constraintLayout, constraintLayout2, digitalClock, guideline, guideline2, guideline3, lineChart, linearLayout, constraintLayout3, recyclerView, r22, textView, textView2, textView3, textView4);
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
