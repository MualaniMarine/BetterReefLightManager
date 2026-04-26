package com.github.mikephil.charting.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class MarkerImage implements IMarker {
    private Context mContext;
    private Drawable mDrawable;
    private WeakReference<Chart> mWeakChart;
    private MPPointF mOffset = new MPPointF();
    private MPPointF mOffset2 = new MPPointF();
    private FSize mSize = new FSize();
    private Rect mDrawableBoundsCache = new Rect();

    @Override // com.github.mikephil.charting.components.IMarker
    public void refreshContent(Entry entry, Highlight highlight) {
    }

    public MarkerImage(Context context, int i) {
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= 21) {
            this.mDrawable = this.mContext.getResources().getDrawable(i, null);
        } else {
            this.mDrawable = this.mContext.getResources().getDrawable(i);
        }
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

    public void setSize(FSize fSize) {
        this.mSize = fSize;
        if (fSize == null) {
            this.mSize = new FSize();
        }
    }

    public FSize getSize() {
        return this.mSize;
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
        Drawable drawable;
        Drawable drawable2;
        MPPointF offset = getOffset();
        this.mOffset2.f76x = offset.f76x;
        this.mOffset2.f77y = offset.f77y;
        Chart chartView = getChartView();
        float intrinsicWidth = this.mSize.width;
        float intrinsicHeight = this.mSize.height;
        if (intrinsicWidth == 0.0f && (drawable2 = this.mDrawable) != null) {
            intrinsicWidth = drawable2.getIntrinsicWidth();
        }
        if (intrinsicHeight == 0.0f && (drawable = this.mDrawable) != null) {
            intrinsicHeight = drawable.getIntrinsicHeight();
        }
        if (this.mOffset2.f76x + f < 0.0f) {
            this.mOffset2.f76x = -f;
        } else if (chartView != null && f + intrinsicWidth + this.mOffset2.f76x > chartView.getWidth()) {
            this.mOffset2.f76x = (chartView.getWidth() - f) - intrinsicWidth;
        }
        if (this.mOffset2.f77y + f2 < 0.0f) {
            this.mOffset2.f77y = -f2;
        } else if (chartView != null && f2 + intrinsicHeight + this.mOffset2.f77y > chartView.getHeight()) {
            this.mOffset2.f77y = (chartView.getHeight() - f2) - intrinsicHeight;
        }
        return this.mOffset2;
    }

    @Override // com.github.mikephil.charting.components.IMarker
    public void draw(Canvas canvas, float f, float f2) {
        Drawable drawable;
        Drawable drawable2;
        if (this.mDrawable == null) {
            return;
        }
        MPPointF offsetForDrawingAtPoint = getOffsetForDrawingAtPoint(f, f2);
        float intrinsicWidth = this.mSize.width;
        float intrinsicHeight = this.mSize.height;
        if (intrinsicWidth == 0.0f && (drawable2 = this.mDrawable) != null) {
            intrinsicWidth = drawable2.getIntrinsicWidth();
        }
        if (intrinsicHeight == 0.0f && (drawable = this.mDrawable) != null) {
            intrinsicHeight = drawable.getIntrinsicHeight();
        }
        this.mDrawable.copyBounds(this.mDrawableBoundsCache);
        this.mDrawable.setBounds(this.mDrawableBoundsCache.left, this.mDrawableBoundsCache.top, this.mDrawableBoundsCache.left + ((int) intrinsicWidth), this.mDrawableBoundsCache.top + ((int) intrinsicHeight));
        int iSave = canvas.save();
        canvas.translate(f + offsetForDrawingAtPoint.f76x, f2 + offsetForDrawingAtPoint.f77y);
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(iSave);
        this.mDrawable.setBounds(this.mDrawableBoundsCache);
    }
}
