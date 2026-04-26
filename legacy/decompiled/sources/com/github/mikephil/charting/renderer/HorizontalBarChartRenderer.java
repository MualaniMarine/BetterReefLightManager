package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HorizontalBarChartRenderer extends BarChartRenderer {
    private RectF mBarShadowRectBuffer;

    public HorizontalBarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
        this.mBarShadowRectBuffer = new RectF();
        this.mValuePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer, com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new HorizontalBarBuffer[barData.getDataSetCount()];
        for (int i = 0; i < this.mBarBuffers.length; i++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i);
            this.mBarBuffers[i] = new HorizontalBarBuffer(iBarDataSet.getEntryCount() * 4 * (iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1), barData.getDataSetCount(), iBarDataSet.isStacked());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
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
                this.mBarShadowRectBuffer.top = x - barWidth;
                this.mBarShadowRectBuffer.bottom = x + barWidth;
                transformer.rectValueToPixel(this.mBarShadowRectBuffer);
                if (this.mViewPortHandler.isInBoundsTop(this.mBarShadowRectBuffer.bottom)) {
                    if (!this.mViewPortHandler.isInBoundsBottom(this.mBarShadowRectBuffer.top)) {
                        break;
                    }
                    this.mBarShadowRectBuffer.left = this.mViewPortHandler.contentLeft();
                    this.mBarShadowRectBuffer.right = this.mViewPortHandler.contentRight();
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
            int i4 = i3 + 3;
            if (!this.mViewPortHandler.isInBoundsTop(barBuffer.buffer[i4])) {
                return;
            }
            int i5 = i3 + 1;
            if (this.mViewPortHandler.isInBoundsBottom(barBuffer.buffer[i5])) {
                if (!z2) {
                    this.mRenderPaint.setColor(iBarDataSet.getColor(i3 / 4));
                }
                int i6 = i3 + 2;
                canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i5], barBuffer.buffer[i6], barBuffer.buffer[i4], this.mRenderPaint);
                if (z) {
                    canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i5], barBuffer.buffer[i6], barBuffer.buffer[i4], this.mBarBorderPaint);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.BarChartRenderer, com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        List list;
        MPPointF mPPointF;
        int i;
        float[] fArr;
        float f;
        int i2;
        float[] fArr2;
        float f2;
        float f3;
        BarEntry barEntry;
        float f4;
        int i3;
        List list2;
        boolean z;
        MPPointF mPPointF2;
        IValueFormatter iValueFormatter;
        float f5;
        BarBuffer barBuffer;
        if (isDrawingValuesAllowed(this.mChart)) {
            List dataSets = this.mChart.getBarData().getDataSets();
            float fConvertDpToPixel = Utils.convertDpToPixel(5.0f);
            boolean zIsDrawValueAboveBarEnabled = this.mChart.isDrawValueAboveBarEnabled();
            int i4 = 0;
            while (i4 < this.mChart.getBarData().getDataSetCount()) {
                IBarDataSet iBarDataSet = (IBarDataSet) dataSets.get(i4);
                if (shouldDrawValues(iBarDataSet)) {
                    boolean zIsInverted = this.mChart.isInverted(iBarDataSet.getAxisDependency());
                    applyValueTextStyle(iBarDataSet);
                    float f6 = 2.0f;
                    float fCalcTextHeight = Utils.calcTextHeight(this.mValuePaint, "10") / 2.0f;
                    IValueFormatter valueFormatter = iBarDataSet.getValueFormatter();
                    BarBuffer barBuffer2 = this.mBarBuffers[i4];
                    float phaseY = this.mAnimator.getPhaseY();
                    MPPointF mPPointF3 = MPPointF.getInstance(iBarDataSet.getIconsOffset());
                    mPPointF3.f76x = Utils.convertDpToPixel(mPPointF3.f76x);
                    mPPointF3.f77y = Utils.convertDpToPixel(mPPointF3.f77y);
                    if (iBarDataSet.isStacked()) {
                        list = dataSets;
                        mPPointF = mPPointF3;
                        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                        int i5 = 0;
                        int length = 0;
                        while (i5 < iBarDataSet.getEntryCount() * this.mAnimator.getPhaseX()) {
                            BarEntry barEntry2 = (BarEntry) iBarDataSet.getEntryForIndex(i5);
                            int valueTextColor = iBarDataSet.getValueTextColor(i5);
                            float[] yVals = barEntry2.getYVals();
                            if (yVals == null) {
                                int i6 = length + 1;
                                if (!this.mViewPortHandler.isInBoundsTop(barBuffer2.buffer[i6])) {
                                    break;
                                }
                                if (this.mViewPortHandler.isInBoundsX(barBuffer2.buffer[length]) && this.mViewPortHandler.isInBoundsBottom(barBuffer2.buffer[i6])) {
                                    String formattedValue = valueFormatter.getFormattedValue(barEntry2.getY(), barEntry2, i4, this.mViewPortHandler);
                                    float fCalcTextWidth = Utils.calcTextWidth(this.mValuePaint, formattedValue);
                                    float f7 = zIsDrawValueAboveBarEnabled ? fConvertDpToPixel : -(fCalcTextWidth + fConvertDpToPixel);
                                    float f8 = zIsDrawValueAboveBarEnabled ? -(fCalcTextWidth + fConvertDpToPixel) : fConvertDpToPixel;
                                    if (zIsInverted) {
                                        f7 = (-f7) - fCalcTextWidth;
                                        f8 = (-f8) - fCalcTextWidth;
                                    }
                                    float f9 = f7;
                                    float f10 = f8;
                                    if (iBarDataSet.isDrawValuesEnabled()) {
                                        i = i5;
                                        fArr = yVals;
                                        barEntry = barEntry2;
                                        drawValue(canvas, formattedValue, barBuffer2.buffer[length + 2] + (barEntry2.getY() >= 0.0f ? f9 : f10), barBuffer2.buffer[i6] + fCalcTextHeight, valueTextColor);
                                    } else {
                                        barEntry = barEntry2;
                                        i = i5;
                                        fArr = yVals;
                                    }
                                    if (barEntry.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                        Drawable icon = barEntry.getIcon();
                                        float f11 = barBuffer2.buffer[length + 2];
                                        if (barEntry.getY() < 0.0f) {
                                            f9 = f10;
                                        }
                                        Utils.drawImage(canvas, icon, (int) (f11 + f9 + mPPointF.f76x), (int) (barBuffer2.buffer[i6] + mPPointF.f77y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                                    }
                                }
                            } else {
                                i = i5;
                                fArr = yVals;
                                int length2 = fArr.length * 2;
                                float[] fArr3 = new float[length2];
                                float f12 = -barEntry2.getNegativeSum();
                                float f13 = 0.0f;
                                int i7 = 0;
                                int i8 = 0;
                                while (i7 < length2) {
                                    float f14 = fArr[i8];
                                    if (f14 == 0.0f && (f13 == 0.0f || f12 == 0.0f)) {
                                        float f15 = f12;
                                        f12 = f14;
                                        f3 = f15;
                                    } else if (f14 >= 0.0f) {
                                        f13 += f14;
                                        f3 = f12;
                                        f12 = f13;
                                    } else {
                                        f3 = f12 - f14;
                                    }
                                    fArr3[i7] = f12 * phaseY;
                                    i7 += 2;
                                    i8++;
                                    f12 = f3;
                                }
                                transformer.pointValuesToPixel(fArr3);
                                int i9 = 0;
                                while (i9 < length2) {
                                    float f16 = fArr[i9 / 2];
                                    String formattedValue2 = valueFormatter.getFormattedValue(f16, barEntry2, i4, this.mViewPortHandler);
                                    float fCalcTextWidth2 = Utils.calcTextWidth(this.mValuePaint, formattedValue2);
                                    float f17 = zIsDrawValueAboveBarEnabled ? fConvertDpToPixel : -(fCalcTextWidth2 + fConvertDpToPixel);
                                    int i10 = length2;
                                    float f18 = zIsDrawValueAboveBarEnabled ? -(fCalcTextWidth2 + fConvertDpToPixel) : fConvertDpToPixel;
                                    if (zIsInverted) {
                                        f17 = (-f17) - fCalcTextWidth2;
                                        f18 = (-f18) - fCalcTextWidth2;
                                    }
                                    boolean z2 = (f16 == 0.0f && f12 == 0.0f && f13 > 0.0f) || f16 < 0.0f;
                                    float f19 = fArr3[i9];
                                    if (z2) {
                                        f17 = f18;
                                    }
                                    float f20 = f19 + f17;
                                    float f21 = (barBuffer2.buffer[length + 1] + barBuffer2.buffer[length + 3]) / 2.0f;
                                    if (!this.mViewPortHandler.isInBoundsTop(f21)) {
                                        break;
                                    }
                                    if (this.mViewPortHandler.isInBoundsX(f20) && this.mViewPortHandler.isInBoundsBottom(f21)) {
                                        if (iBarDataSet.isDrawValuesEnabled()) {
                                            f = f21;
                                            i2 = i9;
                                            fArr2 = fArr3;
                                            f2 = f20;
                                            drawValue(canvas, formattedValue2, f20, f21 + fCalcTextHeight, valueTextColor);
                                        } else {
                                            f = f21;
                                            i2 = i9;
                                            fArr2 = fArr3;
                                            f2 = f20;
                                        }
                                        if (barEntry2.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                            Drawable icon2 = barEntry2.getIcon();
                                            Utils.drawImage(canvas, icon2, (int) (f2 + mPPointF.f76x), (int) (f + mPPointF.f77y), icon2.getIntrinsicWidth(), icon2.getIntrinsicHeight());
                                        }
                                    } else {
                                        i2 = i9;
                                        fArr2 = fArr3;
                                    }
                                    i9 = i2 + 2;
                                    length2 = i10;
                                    fArr3 = fArr2;
                                }
                            }
                            length = fArr == null ? length + 4 : length + (fArr.length * 4);
                            i5 = i + 1;
                        }
                    } else {
                        int i11 = 0;
                        while (i11 < barBuffer2.buffer.length * this.mAnimator.getPhaseX()) {
                            int i12 = i11 + 1;
                            float f22 = (barBuffer2.buffer[i12] + barBuffer2.buffer[i11 + 3]) / f6;
                            if (!this.mViewPortHandler.isInBoundsTop(barBuffer2.buffer[i12])) {
                                break;
                            }
                            if (this.mViewPortHandler.isInBoundsX(barBuffer2.buffer[i11]) && this.mViewPortHandler.isInBoundsBottom(barBuffer2.buffer[i12])) {
                                BarEntry barEntry3 = (BarEntry) iBarDataSet.getEntryForIndex(i11 / 4);
                                float y = barEntry3.getY();
                                String formattedValue3 = valueFormatter.getFormattedValue(y, barEntry3, i4, this.mViewPortHandler);
                                MPPointF mPPointF4 = mPPointF3;
                                float fCalcTextWidth3 = Utils.calcTextWidth(this.mValuePaint, formattedValue3);
                                float f23 = zIsDrawValueAboveBarEnabled ? fConvertDpToPixel : -(fCalcTextWidth3 + fConvertDpToPixel);
                                IValueFormatter iValueFormatter2 = valueFormatter;
                                float f24 = zIsDrawValueAboveBarEnabled ? -(fCalcTextWidth3 + fConvertDpToPixel) : fConvertDpToPixel;
                                if (zIsInverted) {
                                    f23 = (-f23) - fCalcTextWidth3;
                                    f24 = (-f24) - fCalcTextWidth3;
                                }
                                float f25 = f23;
                                float f26 = f24;
                                if (iBarDataSet.isDrawValuesEnabled()) {
                                    f4 = y;
                                    i3 = i11;
                                    list2 = dataSets;
                                    mPPointF2 = mPPointF4;
                                    f5 = fCalcTextHeight;
                                    barBuffer = barBuffer2;
                                    z = zIsInverted;
                                    iValueFormatter = iValueFormatter2;
                                    drawValue(canvas, formattedValue3, (y >= 0.0f ? f25 : f26) + barBuffer2.buffer[i11 + 2], f22 + fCalcTextHeight, iBarDataSet.getValueTextColor(i11 / 2));
                                } else {
                                    f4 = y;
                                    i3 = i11;
                                    list2 = dataSets;
                                    z = zIsInverted;
                                    mPPointF2 = mPPointF4;
                                    iValueFormatter = iValueFormatter2;
                                    f5 = fCalcTextHeight;
                                    barBuffer = barBuffer2;
                                }
                                if (barEntry3.getIcon() != null && iBarDataSet.isDrawIconsEnabled()) {
                                    Drawable icon3 = barEntry3.getIcon();
                                    float f27 = barBuffer.buffer[i3 + 2];
                                    if (f4 < 0.0f) {
                                        f25 = f26;
                                    }
                                    Utils.drawImage(canvas, icon3, (int) (f27 + f25 + mPPointF2.f76x), (int) (f22 + mPPointF2.f77y), icon3.getIntrinsicWidth(), icon3.getIntrinsicHeight());
                                }
                            } else {
                                i3 = i11;
                                list2 = dataSets;
                                z = zIsInverted;
                                f5 = fCalcTextHeight;
                                mPPointF2 = mPPointF3;
                                barBuffer = barBuffer2;
                                iValueFormatter = valueFormatter;
                            }
                            i11 = i3 + 4;
                            mPPointF3 = mPPointF2;
                            valueFormatter = iValueFormatter;
                            barBuffer2 = barBuffer;
                            fCalcTextHeight = f5;
                            dataSets = list2;
                            zIsInverted = z;
                            f6 = 2.0f;
                        }
                        list = dataSets;
                        mPPointF = mPPointF3;
                    }
                    MPPointF.recycleInstance(mPPointF);
                } else {
                    list = dataSets;
                }
                i4++;
                dataSets = list;
            }
        }
    }

    protected void drawValue(Canvas canvas, String str, float f, float f2, int i) {
        this.mValuePaint.setColor(i);
        canvas.drawText(str, f, f2, this.mValuePaint);
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
    protected void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f2, f - f4, f3, f + f4);
        transformer.rectToPixelPhaseHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
    protected void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerY(), rectF.right);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    protected boolean isDrawingValuesAllowed(ChartInterface chartInterface) {
        return ((float) chartInterface.getData().getEntryCount()) < ((float) chartInterface.getMaxVisibleCount()) * this.mViewPortHandler.getScaleY();
    }
}
