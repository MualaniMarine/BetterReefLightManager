package com.nemo.caideng.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.databinding.ViewTimeWheelBinding;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TimeWheelView extends FrameLayout {
    private DecimalFormat decimalFormat;
    private List<String> hoursList;
    private List<String> minuteList;
    private List<String> secondList;
    private ViewTimeWheelBinding viewTimeWheelBinding;

    public TimeWheelView(Context context) {
        super(context);
        this.decimalFormat = new DecimalFormat("00");
    }

    public TimeWheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.decimalFormat = new DecimalFormat("00");
        init();
    }

    public void setTime(int i, int i2, int i3) {
        this.viewTimeWheelBinding.wvHours.setCurrentItem(i);
        this.viewTimeWheelBinding.wvMinute.setCurrentItem(i2);
        this.viewTimeWheelBinding.wvSecond.setCurrentItem(i3);
    }

    public int getHour() {
        return this.viewTimeWheelBinding.wvHours.getCurrentItem();
    }

    public int getMinute() {
        return this.viewTimeWheelBinding.wvMinute.getCurrentItem();
    }

    public int getSecond() {
        return this.viewTimeWheelBinding.wvSecond.getCurrentItem();
    }

    private void init() {
        initData();
        ViewTimeWheelBinding viewTimeWheelBindingInflate = ViewTimeWheelBinding.inflate(LayoutInflater.from(getContext()));
        this.viewTimeWheelBinding = viewTimeWheelBindingInflate;
        addView(viewTimeWheelBindingInflate.getRoot());
        this.viewTimeWheelBinding.wvHours.setCyclic(false);
        this.viewTimeWheelBinding.wvHours.setItemsVisibleCount(7);
        this.viewTimeWheelBinding.wvHours.setDividerColor(getResources().getColor(C0575R.color.color_F3F3F7));
        this.viewTimeWheelBinding.wvHours.setCurrentItem(0);
        this.viewTimeWheelBinding.wvHours.setTextColorCenter(getResources().getColor(C0575R.color.color_5D5D5D));
        this.viewTimeWheelBinding.wvHours.setTextColorOut(getResources().getColor(C0575R.color.color_DCDCDC));
        this.viewTimeWheelBinding.wvHours.setTextSize(20.0f);
        this.viewTimeWheelBinding.wvHours.setAdapter(new ArrayWheelAdapter(this.hoursList));
        this.viewTimeWheelBinding.wvMinute.setCyclic(false);
        this.viewTimeWheelBinding.wvMinute.setItemsVisibleCount(7);
        this.viewTimeWheelBinding.wvMinute.setDividerColor(getResources().getColor(C0575R.color.color_F3F3F7));
        this.viewTimeWheelBinding.wvMinute.setCurrentItem(0);
        this.viewTimeWheelBinding.wvMinute.setTextColorCenter(getResources().getColor(C0575R.color.color_5D5D5D));
        this.viewTimeWheelBinding.wvMinute.setTextColorOut(getResources().getColor(C0575R.color.color_DCDCDC));
        this.viewTimeWheelBinding.wvMinute.setTextSize(20.0f);
        this.viewTimeWheelBinding.wvMinute.setAdapter(new ArrayWheelAdapter(this.minuteList));
        this.viewTimeWheelBinding.wvSecond.setCyclic(false);
        this.viewTimeWheelBinding.wvSecond.setItemsVisibleCount(7);
        this.viewTimeWheelBinding.wvSecond.setDividerColor(getResources().getColor(C0575R.color.color_F3F3F7));
        this.viewTimeWheelBinding.wvSecond.setCurrentItem(0);
        this.viewTimeWheelBinding.wvSecond.setTextColorCenter(getResources().getColor(C0575R.color.color_5D5D5D));
        this.viewTimeWheelBinding.wvSecond.setTextColorOut(getResources().getColor(C0575R.color.color_DCDCDC));
        this.viewTimeWheelBinding.wvSecond.setTextSize(20.0f);
        this.viewTimeWheelBinding.wvSecond.setAdapter(new ArrayWheelAdapter(this.secondList));
    }

    private void initData() {
        generateHours();
        generateMinute();
        generateSecond();
    }

    public void hiddenSecond() {
        this.viewTimeWheelBinding.wvSecond.setVisibility(8);
        this.viewTimeWheelBinding.tvSecond.setVisibility(8);
    }

    public void disableHour() {
        this.viewTimeWheelBinding.wvHours.setEnabled(false);
    }

    private void generateHours() {
        this.hoursList = generateNumList(0, 23);
    }

    private void generateMinute() {
        this.minuteList = generateNumList(0, 59);
    }

    private void generateSecond() {
        this.secondList = generateNumList(0, 59);
    }

    private List<String> generateNumList(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        while (i <= i2) {
            arrayList.add(this.decimalFormat.format(i));
            i++;
        }
        return arrayList;
    }
}
