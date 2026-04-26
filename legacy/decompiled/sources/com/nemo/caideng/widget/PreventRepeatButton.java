package com.nemo.caideng.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
public class PreventRepeatButton extends Button {
    private static final short TIME_GAP = 500;
    private long clickTime;

    public PreventRepeatButton(Context context) {
        super(context);
    }

    public PreventRepeatButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PreventRepeatButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public boolean performClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.clickTime < 500) {
            return true;
        }
        this.clickTime = jCurrentTimeMillis;
        return super.performClick();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            getBackground().setColorFilter(Color.parseColor("#99888888"), PorterDuff.Mode.MULTIPLY);
        }
        if (motionEvent.getAction() == 3) {
            getBackground().clearColorFilter();
        }
        if (motionEvent.getAction() == 1) {
            getBackground().clearColorFilter();
        }
        return super.onTouchEvent(motionEvent);
    }
}
