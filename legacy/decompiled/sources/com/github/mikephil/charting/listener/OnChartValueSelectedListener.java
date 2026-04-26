package com.github.mikephil.charting.listener;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/* JADX INFO: loaded from: classes.dex */
public interface OnChartValueSelectedListener {
    void onNothingSelected();

    void onValueSelected(Entry entry, Highlight highlight);
}
