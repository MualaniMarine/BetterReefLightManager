package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BarChartRenderer extends BarLineScatterCandleBubbleRenderer {
    protected Paint mBarBorderPaint;
    protected BarBuffer[] mBarBuffers;
    protected RectF mBarRect;
    private RectF mBarShadowRectBuffer;
    protected BarDataProvider mChart;
    protected Paint mShadowPaint;

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
    }

    public BarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mBarRect = new RectF();
        this.mBarShadowRectBuffer = new RectF();
        this.mChart = barDataProvider;
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setColor(Color.rgb(0, 0, 0));
        this.mHighlightPaint.setAlpha(120);
        Paint paint = new Paint(1);
        this.mShadowPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mBarBorderPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new BarBuffer[barData.getDataSetCount()];
        for (int i = 0; i < this.mBarBuffers.length; i++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i);
            this.mBarBuffers[i] = new BarBuffer(iBarDataSet.getEntryCount() * 4 * (iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1), barData.getDataSetCount(), iBarDataSet.isStacked());
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        BarData barData = this.mChart.getBarData();
        for (int i = 0; i < barData.getDataSetCount(); i++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i);
            if (iBarDataSet.isVisible()) {
                drawDataSet(canvas, iBarDataSet, i);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int i) {
        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        boolean z = iBarDataSet.getBarBorderWidth() > 0.0f;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        if (this.mChart.isDrawBarShadowEnabled()) {
            this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
            float barWidth = this.mChart.getBarData().getBarWidth() / 2.0f;
            int iMin = Math.min((int) Math.ceil(iBarDataSet.getEntryCount() * phaseX), iBarDataSet.getEntryCount());
            for (int i2 = 0; i2 < iMin; i2++) {
                float x = ((BarEntry) iBarDataSet.getEntryForIndex(i2)).getX();
                this.mBarShadowRectBuffer.left = x - barWidth;
                this.mBarShadowRectBuffer.right = x + barWidth;
                transformer.rectValueToPixel(this.mBarShadowRectBuffer);
                if (this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) {
                    if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) {
                        break;
                    }
                    this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
                    this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
                    canvas.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
                }
            }
        }
        BarBuffer barBuffer = this.mBarBuffers[i];
        barBuffer.setPhases(phaseX, phaseY);
        barBuffer.setDataSet(i);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        barBuffer.feed(iBarDataSet);
        transformer.pointValuesToPixel(barBuffer.buffer);
        boolean z2 = iBarDataSet.getColors().size() == 1;
        if (z2) {
            this.mRenderPaint.setColor(iBarDataSet.getColor());
        }
        for (int i3 = 0; i3 < barBuffer.size(); i3 += 4) {
            int i4 = i3 + 2;
            if (this.mViewPortHandler.isInBoundsLeft(barBuffer.buffer[i4])) {
                if (!this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[i3])) {
                    return;
                }
                if (!z2) {
                    this.mRenderPaint.setColor(iBarDataSet.getColor(i3 / 4));
                }
                int i5 = i3 + 1;
                int i6 = i3 + 3;
                canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i5], barBuffer.buffer[i4], barBuffer.buffer[i6], this.mRenderPaint);
                if (z) {
                    canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i5], barBuffer.buffer[i4], barBuffer.buffer[i6], this.mBarBorderPaint);
                }
            }
        }
    }

    protected void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f - f4, f2, f + f4, f3);
        transformer.rectToPixelPhase(this.mBarRect, this.mAnimator.getPhaseY());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        MPPointF mPPointF;
        List list;
        int i;
        float f;
        boolean z;
        float[] fArr;
        Transformer transformer;
        int i2;
        float[] fArr2;
        int i3;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        boolean z2;
        int i4;
        MPPointF mPPointF2;
        List list2;
        BarBuffer barBuffer;
        float f7;
        if (isDrawingValuesAllowed(this.mChart)) {
            List dataSets = this.mChart.getBarData().getDataSets();
            float fConvertDpToPixel = Utils.convertDpToPixel(4.5f);
            boolean zIsDrawValueAboveBarEnabled = this.mChart.isDrawValueAboveBarEnabled();
            int i5 = 0;
            while (i5 < this.mChart.getBarData().getDataSetCount()) {
                IBarDataSet iBarDataSet = (IBarDataSet) dataSets.get(i5);
                if (shouldDrawValues(iBarDataSet)) {
                    applyValueTextStyle(iBarDataSet);
                    boolean zIsInverted = this.mChart.isInverted(iBarDataSet.getAxisDependency());
                    float fCalcTextHeight = Utils.calcTextHeight(this.mValuePaint, "8");
                    float f8 = zIsDrawValueAboveBarEnabled ? -fConvertDpToPixel : fCalcTextHeight + fConvertDpToPixel;
                    float f9 = zIsDrawValueAboveBarEnabled ? fCalcTextHeight + fConvertDpToPixel : -fConvertDpToPixel;
                    if (zIsInverted) {
                        f8 = (-f8) - fCalcTextHeight;
                        f9 = (-f9) - fCalcTextHeight;
                    }
                    float f10 = f8;
                    float f11 = f9;
                    BarBuffer barBuffer2 = this.mBarBuffers[i5];
                    float phaseY = this.mAnimator.getPhaseY();
                    MPPointF mPPointF3 = MPPointF.getInstance(iBarDataSet.getIconsOffset());
                    mPPointF3.f76x = Utils.convertDpToPixel(mPPointF3.f76x);
                    mPPointF3.f77y = Utils.convertDpToPixel(mPPointF3.f77y);
                    if (iBarDataSet.isStacked()) {
                        mPPointF = mPPointF3;
                        list = dataSets;
                        Transformer transformer2 = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                        int i6 = 0;
                        int length = 0;
                        while (i6 < iBarDataSet.getEntryCount() * this.mAnimator.getPhaseX()) {
                            BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForIndex(i6);
                            float[] yVals = barEntry.getYVals();
                            float f12 = (barBuffer2.buffer[length] + barBuffer2.buffer[length + 2]) / 2.0f;
                            int valueTextColor = iBarDataSet.getValueTextColor(i6);
                            if (yVals == null) {
                                if (!this.mViewPortHandler.isInBoundsRight(f12)) {
                                    break;
                                }
                                int i7 = length + 1;
                                if (this.mViewPortHandler.isInBoundsY(barBuffer2.buffer[i7]) && this.mViewPortHandler.isInBoundsLeft(f12)) {
                                    if (iBarDataSet.isDrawValuesEnabled()) {
                                        f5 = f12;
                                        f = fConvertDpToPixel;
                                        fArr = yVals;
                                        i = i6;
                                        z = zIsDrawValueAboveBarEnabled;
                                        transformer = transformer2;
                                        drawValue(canvas, iBarDataSet.getValueFormatter(), barEntry.getY(), barEntry, i5, f5, barBuffer2.buffer[i7] + (barEntry.getY() >= 0.0f ? f10 : f11), valueTextColor);
                                    } else {
                                        f5 = f12;
                                        i = i6;
                                        f = fConvertDpToPixel;
                                        z = zIsDrawValueAboveBarEnabled;
                                        fArr = yVals;
                                        transformer = transformer2;
                                    }
                                    if (barEntry.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                        Drawable icon = barEntry.getIcon();
                                        Utils.drawImage(canvas, icon, (int) (f5 + mPPointF.f76x), (int) (barBuffer2.buffer[i7] + (barEntry.getY() >= 0.0f ? f10 : f11) + mPPointF.f77y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                                    }
                                } else {
                                    transformer2 = transformer2;
                                    zIsDrawValueAboveBarEnabled = zIsDrawValueAboveBarEnabled;
                                    fConvertDpToPixel = fConvertDpToPixel;
                                    i6 = i6;
                                }
                            } else {
                                i = i6;
                                f = fConvertDpToPixel;
                                z = zIsDrawValueAboveBarEnabled;
                                fArr = yVals;
                                transformer = transformer2;
                                float f13 = f12;
                                int length2 = fArr.length * 2;
                                float[] fArr3 = new float[length2];
                                float f14 = -barEntry.getNegativeSum();
                                float f15 = 0.0f;
                                int i8 = 0;
                                int i9 = 0;
                                while (i8 < length2) {
                                    float f16 = fArr[i9];
                                    if (f16 == 0.0f && (f15 == 0.0f || f14 == 0.0f)) {
                                        float f17 = f14;
                                        f14 = f16;
                                        f4 = f17;
                                    } else if (f16 >= 0.0f) {
                                        f15 += f16;
                                        f4 = f14;
                                        f14 = f15;
                                    } else {
                                        f4 = f14 - f16;
                                    }
                                    fArr3[i8 + 1] = f14 * phaseY;
                                    i8 += 2;
                                    i9++;
                                    f14 = f4;
                                }
                                transformer.pointValuesToPixel(fArr3);
                                int i10 = 0;
                                while (i10 < length2) {
                                    int i11 = i10 / 2;
                                    float f18 = fArr[i11];
                                    float f19 = fArr3[i10 + 1] + (((f18 > 0.0f ? 1 : (f18 == 0.0f ? 0 : -1)) == 0 && (f14 > 0.0f ? 1 : (f14 == 0.0f ? 0 : -1)) == 0 && (f15 > 0.0f ? 1 : (f15 == 0.0f ? 0 : -1)) > 0) || (f18 > 0.0f ? 1 : (f18 == 0.0f ? 0 : -1)) < 0 ? f11 : f10);
                                    if (!this.mViewPortHandler.isInBoundsRight(f13)) {
                                        break;
                                    }
                                    if (this.mViewPortHandler.isInBoundsY(f19) && this.mViewPortHandler.isInBoundsLeft(f13)) {
                                        if (iBarDataSet.isDrawValuesEnabled()) {
                                            f3 = f19;
                                            i2 = i10;
                                            fArr2 = fArr3;
                                            i3 = length2;
                                            f2 = f13;
                                            drawValue(canvas, iBarDataSet.getValueFormatter(), fArr[i11], barEntry, i5, f13, f3, valueTextColor);
                                        } else {
                                            f3 = f19;
                                            i2 = i10;
                                            fArr2 = fArr3;
                                            i3 = length2;
                                            f2 = f13;
                                        }
                                        if (barEntry.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                            Drawable icon2 = barEntry.getIcon();
                                            Utils.drawImage(canvas, icon2, (int) (f2 + mPPointF.f76x), (int) (f3 + mPPointF.f77y), icon2.getIntrinsicWidth(), icon2.getIntrinsicHeight());
                                        }
                                    } else {
                                        i2 = i10;
                                        fArr2 = fArr3;
                                        i3 = length2;
                                        f2 = f13;
                                    }
                                    i10 = i2 + 2;
                                    fArr3 = fArr2;
                                    length2 = i3;
                                    f13 = f2;
                                }
                            }
                            length = fArr == null ? length + 4 : length + (fArr.length * 4);
                            i6 = i + 1;
                            transformer2 = transformer;
                            zIsDrawValueAboveBarEnabled = z;
                            fConvertDpToPixel = f;
                        }
                    } else {
                        int i12 = 0;
                        while (i12 < barBuffer2.buffer.length * this.mAnimator.getPhaseX()) {
                            float f20 = (barBuffer2.buffer[i12] + barBuffer2.buffer[i12 + 2]) / 2.0f;
                            if (!this.mViewPortHandler.isInBoundsRight(f20)) {
                                break;
                            }
                            int i13 = i12 + 1;
                            if (this.mViewPortHandler.isInBoundsY(barBuffer2.buffer[i13]) && this.mViewPortHandler.isInBoundsLeft(f20)) {
                                int i14 = i12 / 4;
                                Entry entry = (BarEntry) iBarDataSet.getEntryForIndex(i14);
                                float y = entry.getY();
                                if (iBarDataSet.isDrawValuesEnabled()) {
                                    f7 = f20;
                                    i4 = i12;
                                    mPPointF2 = mPPointF3;
                                    list2 = dataSets;
                                    barBuffer = barBuffer2;
                                    drawValue(canvas, iBarDataSet.getValueFormatter(), y, entry, i5, f7, y >= 0.0f ? barBuffer2.buffer[i13] + f10 : barBuffer2.buffer[i12 + 3] + f11, iBarDataSet.getValueTextColor(i14));
                                } else {
                                    f7 = f20;
                                    i4 = i12;
                                    mPPointF2 = mPPointF3;
                                    list2 = dataSets;
                                    barBuffer = barBuffer2;
                                }
                                if (entry.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                    Drawable icon3 = entry.getIcon();
                                    Utils.drawImage(canvas, icon3, (int) (f7 + mPPointF2.f76x), (int) ((y >= 0.0f ? barBuffer.buffer[i13] + f10 : barBuffer.buffer[i4 + 3] + f11) + mPPointF2.f77y), icon3.getIntrinsicWidth(), icon3.getIntrinsicHeight());
                                }
                            } else {
                                i4 = i12;
                                mPPointF2 = mPPointF3;
                                list2 = dataSets;
                                barBuffer = barBuffer2;
                            }
                            i12 = i4 + 4;
                            barBuffer2 = barBuffer;
                            mPPointF3 = mPPointF2;
                            dataSets = list2;
                        }
                        mPPointF = mPPointF3;
                        list = dataSets;
                    }
                    f6 = fConvertDpToPixel;
                    z2 = zIsDrawValueAboveBarEnabled;
                    MPPointF.recycleInstance(mPPointF);
                } else {
                    list = dataSets;
                    f6 = fConvertDpToPixel;
                    z2 = zIsDrawValueAboveBarEnabled;
                }
                i5++;
                dataSets = list;
                zIsDrawValueAboveBarEnabled = z2;
                fConvertDpToPixel = f6;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        float y;
        float f;
        float f2;
        float f3;
        BarData barData = this.mChart.getBarData();
        for (Highlight highlight : highlightArr) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iBarDataSet != null && iBarDataSet.isHighlightEnabled()) {
                BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForXValue(highlight.getX(), highlight.getY());
                if (isInBoundsX(barEntry, iBarDataSet)) {
                    Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                    this.mHighlightPaint.setColor(iBarDataSet.getHighLightColor());
                    this.mHighlightPaint.setAlpha(iBarDataSet.getHighLightAlpha());
                    if (highlight.getStackIndex() >= 0 && barEntry.isStacked()) {
                        if (this.mChart.isHighlightFullBarEnabled()) {
                            y = barEntry.getPositiveSum();
                            f = -barEntry.getNegativeSum();
                        } else {
                            Range range = barEntry.getRanges()[highlight.getStackIndex()];
                            f3 = range.from;
                            f2 = range.f65to;
                            prepareBarHighlight(barEntry.getX(), f3, f2, barData.getBarWidth() / 2.0f, transformer);
                            setHighlightDrawPos(highlight, this.mBarRect);
                            canvas.drawRect(this.mBarRect, this.mHighlightPaint);
                        }
                    } else {
                        y = barEntry.getY();
                        f = 0.0f;
                    }
                    f2 = f;
                    f3 = y;
                    prepareBarHighlight(barEntry.getX(), f3, f2, barData.getBarWidth() / 2.0f, transformer);
                    setHighlightDrawPos(highlight, this.mBarRect);
                    canvas.drawRect(this.mBarRect, this.mHighlightPaint);
                }
            }
        }
    }

    protected void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerX(), rectF.top);
    }
}
