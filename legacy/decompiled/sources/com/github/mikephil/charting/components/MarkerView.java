package com.github.mikephil.charting.components;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class MarkerView extends RelativeLayout implements IMarker {
    private MPPointF mOffset;
    private MPPointF mOffset2;
    private WeakReference<Chart> mWeakChart;

    public MarkerView(Context context, int i) {
        super(context);
        this.mOffset = new MPPointF();
        this.mOffset2 = new MPPointF();
        setupLayoutResource(i);
    }

    private void setupLayoutResource(int i) {
        View viewInflate = LayoutInflater.from(getContext()).inflate(i, this);
        viewInflate.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        viewInflate.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        viewInflate.layout(0, 0, viewInflate.getMeasuredWidth(), viewInflate.getMeasuredHeight());
    }

    public void setOffset(MPPointF mPPointF) {
        this.mOffset = mPPointF;
        if (mPPointF == null) {
            this.mOffset = new MPPointF();
        }
    }

    public void setOffset(float f, float f2) {
        this.mOffset.f76x = f;
        this.mOffset.f77y = f2;
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public MPPointF getOffset() {
        return this.mOffset;
    }

    public void setChartView(Chart chart) {
        this.mWeakChart = new WeakReference<>(chart);
    }

    public Chart getChartView() {
        WeakReference<Chart> weakReference = this.mWeakChart;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public MPPointF getOffsetForDrawingAtPoint(float f, float f2) {
        MPPointF offset = getOffset();
        this.mOffset2.f76x = offset.f76x;
        this.mOffset2.f77y = offset.f77y;
        Chart chartView = getChartView();
        float width = getWidth();
        float height = getHeight();
        if (this.mOffset2.f76x + f < 0.0f) {
            this.mOffset2.f76x = -f;
        } else if (chartView != null && f + width + this.mOffset2.f76x > chartView.getWidth()) {
            this.mOffset2.f76x = (chartView.getWidth() - f) - width;
        }
        if (this.mOffset2.f77y + f2 < 0.0f) {
            this.mOffset2.f77y = -f2;
        } else if (chartView != null && f2 + height + this.mOffset2.f77y > chartView.getHeight()) {
            this.mOffset2.f77y = (chartView.getHeight() - f2) - height;
        }
        return this.mOffset2;
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public void refreshContent(Entry entry, Highlight highlight) {
        measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public void draw(Canvas canvas, float f, float f2) {
        MPPointF offsetForDrawingAtPoint = getOffsetForDrawingAtPoint(f, f2);
        int iSave = canvas.save();
        canvas.translate(f + offsetForDrawingAtPoint.f76x, f2 + offsetForDrawingAtPoint.f77y);
        draw(canvas);
        canvas.restoreToCount(iSave);
    }
}
