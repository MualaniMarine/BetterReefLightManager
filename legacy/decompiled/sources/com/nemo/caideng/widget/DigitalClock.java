package com.nemo.caideng.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class DigitalClock extends android.widget.DigitalClock {
    private static final String m12 = "h:mm aa";
    private static final String m24 = "k:mm:ss";
    Calendar mCalendar;
    String mFormat;
    private FormatChangeObserver mFormatChangeObserver;
    private Handler mHandler;
    private Runnable mTicker;
    private boolean mTickerStopped;

    public DigitalClock(Context context) {
        super(context);
        this.mTickerStopped = false;
        initClock(context);
    }

    public DigitalClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTickerStopped = false;
        initClock(context);
    }

    private void initClock(Context context) {
        context.getResources();
        if (this.mCalendar == null) {
            this.mCalendar = Calendar.getInstance();
        }
        this.mFormatChangeObserver = new FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.mFormatChangeObserver);
        setFormat();
    }

    @Override // android.widget.DigitalClock, android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        this.mTickerStopped = false;
        super.onAttachedToWindow();
        this.mHandler = new Handler();
        Runnable runnable = new Runnable() { // from class: com.nemo.caideng.widget.DigitalClock.1
            @Override // java.lang.Runnable
            public void run() {
                if (DigitalClock.this.mTickerStopped) {
                    return;
                }
                DigitalClock.this.mCalendar.setTimeInMillis(System.currentTimeMillis());
                DigitalClock digitalClock = DigitalClock.this;
                digitalClock.setText(DateFormat.format(DigitalClock.m24, digitalClock.mCalendar));
                DigitalClock.this.invalidate();
                long jUptimeMillis = SystemClock.uptimeMillis();
                DigitalClock.this.mHandler.postAtTime(DigitalClock.this.mTicker, jUptimeMillis + (1000 - (jUptimeMillis % 1000)));
            }
        };
        this.mTicker = runnable;
        runnable.run();
    }

    @Override // android.widget.DigitalClock, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTickerStopped = true;
    }

    private boolean get24HourMode() {
        return DateFormat.is24HourFormat(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFormat() {
        if (get24HourMode()) {
            this.mFormat = m24;
        } else {
            this.mFormat = m12;
        }
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            DigitalClock.this.setFormat();
        }
    }

    public int getHour() {
        return this.mCalendar.get(11);
    }

    public int getMinute() {
        return this.mCalendar.get(12);
    }

    public int getSecond() {
        return this.mCalendar.get(13);
    }

    public void setTime(int i, int i2, int i3) {
        this.mCalendar.set(11, i);
        this.mCalendar.set(12, i2);
        this.mCalendar.set(13, i3);
    }
}
