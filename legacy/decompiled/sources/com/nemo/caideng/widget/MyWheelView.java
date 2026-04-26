package com.nemo.caideng.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.contrarywind.view.WheelView;

/* JADX INFO: loaded from: classes.dex */
public class MyWheelView extends WheelView {
    public MyWheelView(Context context) {
        super(context);
    }

    public MyWheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.contrarywind.view.WheelView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }
}
