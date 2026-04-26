package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import androidx.core.view.ViewCompat;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PieChartRenderer extends DataRenderer {
    protected Canvas mBitmapCanvas;
    private RectF mCenterTextLastBounds;
    private CharSequence mCenterTextLastValue;
    private StaticLayout mCenterTextLayout;
    private TextPaint mCenterTextPaint;
    protected PieChart mChart;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mDrawCenterTextPathBuffer;
    protected RectF mDrawHighlightedRectF;
    private Paint mEntryLabelsPaint;
    private Path mHoleCirclePath;
    protected Paint mHolePaint;
    private RectF mInnerRectBuffer;
    private Path mPathBuffer;
    private RectF[] mRectBuffer;
    protected Paint mTransparentCirclePaint;
    protected Paint mValueLinePaint;

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
    }

    public PieChartRenderer(PieChart pieChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mCenterTextLastBounds = new RectF();
        this.mRectBuffer = new RectF[]{new RectF(), new RectF(), new RectF()};
        this.mPathBuffer = new Path();
        this.mInnerRectBuffer = new RectF();
        this.mHoleCirclePath = new Path();
        this.mDrawCenterTextPathBuffer = new Path();
        this.mDrawHighlightedRectF = new RectF();
        this.mChart = pieChart;
        Paint paint = new Paint(1);
        this.mHolePaint = paint;
        paint.setColor(-1);
        this.mHolePaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mTransparentCirclePaint = paint2;
        paint2.setColor(-1);
        this.mTransparentCirclePaint.setStyle(Paint.Style.FILL);
        this.mTransparentCirclePaint.setAlpha(105);
        TextPaint textPaint = new TextPaint(1);
        this.mCenterTextPaint = textPaint;
        textPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValuePaint.setColor(-1);
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        Paint paint3 = new Paint(1);
        this.mEntryLabelsPaint = paint3;
        paint3.setColor(-1);
        this.mEntryLabelsPaint.setTextAlign(Paint.Align.CENTER);
        this.mEntryLabelsPaint.setTextSize(Utils.convertDpToPixel(13.0f));
        Paint paint4 = new Paint(1);
        this.mValueLinePaint = paint4;
        paint4.setStyle(Paint.Style.STROKE);
    }

    public Paint getPaintHole() {
        return this.mHolePaint;
    }

    public Paint getPaintTransparentCircle() {
        return this.mTransparentCirclePaint;
    }

    public TextPaint getPaintCenterText() {
        return this.mCenterTextPaint;
    }

    public Paint getPaintEntryLabels() {
        return this.mEntryLabelsPaint;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        int chartWidth = (int) this.mViewPortHandler.getChartWidth();
        int chartHeight = (int) this.mViewPortHandler.getChartHeight();
        WeakReference<Bitmap> weakReference = this.mDrawBitmap;
        if (weakReference == null || weakReference.get().getWidth() != chartWidth || this.mDrawBitmap.get().getHeight() != chartHeight) {
            if (chartWidth <= 0 || chartHeight <= 0) {
                return;
            }
            this.mDrawBitmap = new WeakReference<>(Bitmap.createBitmap(chartWidth, chartHeight, Bitmap.Config.ARGB_4444));
            this.mBitmapCanvas = new Canvas(this.mDrawBitmap.get());
        }
        this.mDrawBitmap.get().eraseColor(0);
        for (IPieDataSet iPieDataSet : ((PieData) this.mChart.getData()).getDataSets()) {
            if (iPieDataSet.isVisible() && iPieDataSet.getEntryCount() > 0) {
                drawDataSet(canvas, iPieDataSet);
            }
        }
    }

    protected float calculateMinimumRadiusForSpacedSlice(MPPointF mPPointF, float f, float f2, float f3, float f4, float f5, float f6) {
        double d = (f5 + f6) * 0.017453292f;
        float fCos = mPPointF.f76x + (((float) Math.cos(d)) * f);
        float fSin = mPPointF.f77y + (((float) Math.sin(d)) * f);
        double d2 = (f5 + (f6 / 2.0f)) * 0.017453292f;
        return (float) (((double) (f - ((float) ((Math.sqrt(Math.pow(fCos - f3, 2.0d) + Math.pow(fSin - f4, 2.0d)) / 2.0d) * Math.tan(((180.0d - ((double) f2)) / 2.0d) * 0.017453292519943295d))))) - Math.sqrt(Math.pow((mPPointF.f76x + (((float) Math.cos(d2)) * f)) - ((fCos + f3) / 2.0f), 2.0d) + Math.pow((mPPointF.f77y + (((float) Math.sin(d2)) * f)) - ((fSin + f4) / 2.0f), 2.0d)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected float getSliceSpace(IPieDataSet iPieDataSet) {
        if (!iPieDataSet.isAutomaticallyDisableSliceSpacingEnabled()) {
            return iPieDataSet.getSliceSpace();
        }
        if (iPieDataSet.getSliceSpace() / this.mViewPortHandler.getSmallestContentExtension() > (iPieDataSet.getYMin() / ((PieData) this.mChart.getData()).getYValueSum()) * 2.0f) {
            return 0.0f;
        }
        return iPieDataSet.getSliceSpace();
    }

    protected void drawDataSet(Canvas canvas, IPieDataSet iPieDataSet) {
        int i;
        int i2;
        float f;
        float f2;
        float f3;
        RectF rectF;
        int i3;
        float[] fArr;
        int i4;
        float f4;
        MPPointF mPPointF;
        float f5;
        float f6;
        MPPointF mPPointF2;
        float f7;
        int i5;
        PieChartRenderer pieChartRenderer = this;
        IPieDataSet iPieDataSet2 = iPieDataSet;
        float rotationAngle = pieChartRenderer.mChart.getRotationAngle();
        float phaseX = pieChartRenderer.mAnimator.getPhaseX();
        float phaseY = pieChartRenderer.mAnimator.getPhaseY();
        RectF circleBox = pieChartRenderer.mChart.getCircleBox();
        int entryCount = iPieDataSet.getEntryCount();
        float[] drawAngles = pieChartRenderer.mChart.getDrawAngles();
        MPPointF centerCircleBox = pieChartRenderer.mChart.getCenterCircleBox();
        float radius = pieChartRenderer.mChart.getRadius();
        int i6 = 1;
        boolean z = pieChartRenderer.mChart.isDrawHoleEnabled() && !pieChartRenderer.mChart.isDrawSlicesUnderHoleEnabled();
        float holeRadius = z ? (pieChartRenderer.mChart.getHoleRadius() / 100.0f) * radius : 0.0f;
        int i7 = 0;
        for (int i8 = 0; i8 < entryCount; i8++) {
            if (Math.abs(iPieDataSet2.getEntryForIndex(i8).getY()) > Utils.FLOAT_EPSILON) {
                i7++;
            }
        }
        float sliceSpace = i7 <= 1 ? 0.0f : pieChartRenderer.getSliceSpace(iPieDataSet2);
        int i9 = 0;
        float f8 = 0.0f;
        while (i9 < entryCount) {
            float f9 = drawAngles[i9];
            if (Math.abs(iPieDataSet2.getEntryForIndex(i9).getY()) <= Utils.FLOAT_EPSILON || pieChartRenderer.mChart.needsHighlight(i9)) {
                i = i9;
                i2 = i6;
                f = radius;
                f2 = rotationAngle;
                f3 = phaseX;
                rectF = circleBox;
                i3 = entryCount;
                fArr = drawAngles;
                i4 = i7;
                f4 = holeRadius;
                mPPointF = centerCircleBox;
            } else {
                int i10 = (sliceSpace <= 0.0f || f9 > 180.0f) ? 0 : i6;
                pieChartRenderer.mRenderPaint.setColor(iPieDataSet2.getColor(i9));
                float f10 = i7 == 1 ? 0.0f : sliceSpace / (radius * 0.017453292f);
                float f11 = rotationAngle + ((f8 + (f10 / 2.0f)) * phaseY);
                float f12 = (f9 - f10) * phaseY;
                if (f12 < 0.0f) {
                    f12 = 0.0f;
                }
                pieChartRenderer.mPathBuffer.reset();
                int i11 = i9;
                int i12 = i7;
                double d = f11 * 0.017453292f;
                i3 = entryCount;
                fArr = drawAngles;
                float fCos = centerCircleBox.f76x + (((float) Math.cos(d)) * radius);
                float fSin = centerCircleBox.f77y + (((float) Math.sin(d)) * radius);
                if (f12 >= 360.0f && f12 % 360.0f <= Utils.FLOAT_EPSILON) {
                    f3 = phaseX;
                    pieChartRenderer.mPathBuffer.addCircle(centerCircleBox.f76x, centerCircleBox.f77y, radius, Path.Direction.CW);
                } else {
                    f3 = phaseX;
                    pieChartRenderer.mPathBuffer.moveTo(fCos, fSin);
                    pieChartRenderer.mPathBuffer.arcTo(circleBox, f11, f12);
                }
                float f13 = f12;
                pieChartRenderer.mInnerRectBuffer.set(centerCircleBox.f76x - holeRadius, centerCircleBox.f77y - holeRadius, centerCircleBox.f76x + holeRadius, centerCircleBox.f77y + holeRadius);
                if (!z) {
                    f4 = holeRadius;
                    f2 = rotationAngle;
                    f5 = f13;
                    i2 = 1;
                    f = radius;
                    mPPointF = centerCircleBox;
                    rectF = circleBox;
                    i4 = i12;
                    i = i11;
                    f6 = 360.0f;
                } else if (holeRadius > 0.0f || i10 != 0) {
                    if (i10 != 0) {
                        f7 = f13;
                        rectF = circleBox;
                        i4 = i12;
                        i = i11;
                        f4 = holeRadius;
                        i5 = 1;
                        f = radius;
                        mPPointF2 = centerCircleBox;
                        float fCalculateMinimumRadiusForSpacedSlice = calculateMinimumRadiusForSpacedSlice(centerCircleBox, radius, f9 * phaseY, fCos, fSin, f11, f7);
                        if (fCalculateMinimumRadiusForSpacedSlice < 0.0f) {
                            fCalculateMinimumRadiusForSpacedSlice = -fCalculateMinimumRadiusForSpacedSlice;
                        }
                        holeRadius = Math.max(f4, fCalculateMinimumRadiusForSpacedSlice);
                    } else {
                        f4 = holeRadius;
                        mPPointF2 = centerCircleBox;
                        f7 = f13;
                        i5 = 1;
                        f = radius;
                        rectF = circleBox;
                        i4 = i12;
                        i = i11;
                    }
                    float f14 = (i4 == i5 || holeRadius == 0.0f) ? 0.0f : sliceSpace / (holeRadius * 0.017453292f);
                    float f15 = ((f8 + (f14 / 2.0f)) * phaseY) + rotationAngle;
                    float f16 = (f9 - f14) * phaseY;
                    if (f16 < 0.0f) {
                        f16 = 0.0f;
                    }
                    float f17 = f15 + f16;
                    if (f12 >= 360.0f && f7 % 360.0f <= Utils.FLOAT_EPSILON) {
                        i2 = i5;
                        pieChartRenderer = this;
                        pieChartRenderer.mPathBuffer.addCircle(mPPointF2.f76x, mPPointF2.f77y, holeRadius, Path.Direction.CCW);
                        f2 = rotationAngle;
                    } else {
                        i2 = i5;
                        pieChartRenderer = this;
                        double d2 = f17 * 0.017453292f;
                        f2 = rotationAngle;
                        pieChartRenderer.mPathBuffer.lineTo(mPPointF2.f76x + (((float) Math.cos(d2)) * holeRadius), mPPointF2.f77y + (holeRadius * ((float) Math.sin(d2))));
                        pieChartRenderer.mPathBuffer.arcTo(pieChartRenderer.mInnerRectBuffer, f17, -f16);
                    }
                    mPPointF = mPPointF2;
                    pieChartRenderer.mPathBuffer.close();
                    pieChartRenderer.mBitmapCanvas.drawPath(pieChartRenderer.mPathBuffer, pieChartRenderer.mRenderPaint);
                } else {
                    f4 = holeRadius;
                    f2 = rotationAngle;
                    f5 = f13;
                    f6 = 360.0f;
                    i2 = 1;
                    f = radius;
                    mPPointF = centerCircleBox;
                    rectF = circleBox;
                    i4 = i12;
                    i = i11;
                }
                if (f5 % f6 > Utils.FLOAT_EPSILON) {
                    if (i10 != 0) {
                        float fCalculateMinimumRadiusForSpacedSlice2 = calculateMinimumRadiusForSpacedSlice(mPPointF, f, f9 * phaseY, fCos, fSin, f11, f5);
                        double d3 = (f11 + (f5 / 2.0f)) * 0.017453292f;
                        pieChartRenderer.mPathBuffer.lineTo(mPPointF.f76x + (((float) Math.cos(d3)) * fCalculateMinimumRadiusForSpacedSlice2), mPPointF.f77y + (fCalculateMinimumRadiusForSpacedSlice2 * ((float) Math.sin(d3))));
                    } else {
                        pieChartRenderer.mPathBuffer.lineTo(mPPointF.f76x, mPPointF.f77y);
                    }
                }
                pieChartRenderer.mPathBuffer.close();
                pieChartRenderer.mBitmapCanvas.drawPath(pieChartRenderer.mPathBuffer, pieChartRenderer.mRenderPaint);
            }
            f8 += f9 * f3;
            i9 = i + 1;
            iPieDataSet2 = iPieDataSet;
            centerCircleBox = mPPointF;
            i7 = i4;
            holeRadius = f4;
            circleBox = rectF;
            entryCount = i3;
            drawAngles = fArr;
            i6 = i2;
            phaseX = f3;
            radius = f;
            rotationAngle = f2;
        }
        MPPointF.recycleInstance(centerCircleBox);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        int i;
        List<IPieDataSet> list;
        float f;
        float f2;
        float[] fArr;
        float[] fArr2;
        PieDataSet.ValuePosition valuePosition;
        float f3;
        PieDataSet.ValuePosition valuePosition2;
        float f4;
        float f5;
        float f6;
        float f7;
        MPPointF mPPointF;
        int i2;
        float[] fArr3;
        int i3;
        IPieDataSet iPieDataSet;
        float f8;
        IPieDataSet iPieDataSet2;
        float f9;
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        float rotationAngle = this.mChart.getRotationAngle();
        float[] drawAngles = this.mChart.getDrawAngles();
        float[] absoluteAngles = this.mChart.getAbsoluteAngles();
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float holeRadius = this.mChart.getHoleRadius() / 100.0f;
        float f10 = (radius / 10.0f) * 3.6f;
        if (this.mChart.isDrawHoleEnabled()) {
            f10 = (radius - (radius * holeRadius)) / 2.0f;
        }
        float f11 = radius - f10;
        PieData pieData = (PieData) this.mChart.getData();
        List<IPieDataSet> dataSets = pieData.getDataSets();
        float yValueSum = pieData.getYValueSum();
        boolean zIsDrawEntryLabelsEnabled = this.mChart.isDrawEntryLabelsEnabled();
        canvas.save();
        float fConvertDpToPixel = Utils.convertDpToPixel(5.0f);
        int i4 = 0;
        int i5 = 0;
        while (i5 < dataSets.size()) {
            IPieDataSet iPieDataSet3 = dataSets.get(i5);
            boolean zIsDrawValuesEnabled = iPieDataSet3.isDrawValuesEnabled();
            if (zIsDrawValuesEnabled || zIsDrawEntryLabelsEnabled) {
                PieDataSet.ValuePosition xValuePosition = iPieDataSet3.getXValuePosition();
                PieDataSet.ValuePosition yValuePosition = iPieDataSet3.getYValuePosition();
                applyValueTextStyle(iPieDataSet3);
                float fCalcTextHeight = Utils.calcTextHeight(this.mValuePaint, "Q") + Utils.convertDpToPixel(4.0f);
                IValueFormatter valueFormatter = iPieDataSet3.getValueFormatter();
                int entryCount = iPieDataSet3.getEntryCount();
                this.mValueLinePaint.setColor(iPieDataSet3.getValueLineColor());
                this.mValueLinePaint.setStrokeWidth(Utils.convertDpToPixel(iPieDataSet3.getValueLineWidth()));
                float sliceSpace = getSliceSpace(iPieDataSet3);
                MPPointF mPPointF2 = MPPointF.getInstance(iPieDataSet3.getIconsOffset());
                mPPointF2.f76x = Utils.convertDpToPixel(mPPointF2.f76x);
                mPPointF2.f77y = Utils.convertDpToPixel(mPPointF2.f77y);
                int i6 = i4;
                int i7 = 0;
                while (i7 < entryCount) {
                    PieEntry entryForIndex = iPieDataSet3.getEntryForIndex(i7);
                    float f12 = (((i6 == 0 ? 0.0f : absoluteAngles[i6 - 1] * phaseX) + ((drawAngles[i6] - ((sliceSpace / (f11 * 0.017453292f)) / 2.0f)) / 2.0f)) * phaseY) + rotationAngle;
                    int i8 = i7;
                    float y = this.mChart.isUsePercentValuesEnabled() ? (entryForIndex.getY() / yValueSum) * 100.0f : entryForIndex.getY();
                    MPPointF mPPointF3 = mPPointF2;
                    double d = f12 * 0.017453292f;
                    int i9 = i5;
                    List<IPieDataSet> list2 = dataSets;
                    float fCos = (float) Math.cos(d);
                    float f13 = rotationAngle;
                    float[] fArr4 = drawAngles;
                    float fSin = (float) Math.sin(d);
                    boolean z = zIsDrawEntryLabelsEnabled && xValuePosition == PieDataSet.ValuePosition.OUTSIDE_SLICE;
                    boolean z2 = zIsDrawValuesEnabled && yValuePosition == PieDataSet.ValuePosition.OUTSIDE_SLICE;
                    int i10 = entryCount;
                    boolean z3 = zIsDrawEntryLabelsEnabled && xValuePosition == PieDataSet.ValuePosition.INSIDE_SLICE;
                    boolean z4 = zIsDrawValuesEnabled && yValuePosition == PieDataSet.ValuePosition.INSIDE_SLICE;
                    if (z || z2) {
                        float valueLinePart1Length = iPieDataSet3.getValueLinePart1Length();
                        float valueLinePart2Length = iPieDataSet3.getValueLinePart2Length();
                        float valueLinePart1OffsetPercentage = iPieDataSet3.getValueLinePart1OffsetPercentage() / 100.0f;
                        valuePosition = yValuePosition;
                        if (this.mChart.isDrawHoleEnabled()) {
                            float f14 = radius * holeRadius;
                            f3 = ((radius - f14) * valueLinePart1OffsetPercentage) + f14;
                        } else {
                            f3 = radius * valueLinePart1OffsetPercentage;
                        }
                        float fAbs = iPieDataSet3.isValueLineVariableLength() ? valueLinePart2Length * f11 * ((float) Math.abs(Math.sin(d))) : valueLinePart2Length * f11;
                        float f15 = (f3 * fCos) + centerCircleBox.f76x;
                        float f16 = (f3 * fSin) + centerCircleBox.f77y;
                        float f17 = (valueLinePart1Length + 1.0f) * f11;
                        valuePosition2 = xValuePosition;
                        float f18 = (f17 * fCos) + centerCircleBox.f76x;
                        float f19 = (f17 * fSin) + centerCircleBox.f77y;
                        double d2 = ((double) f12) % 360.0d;
                        if (d2 >= 90.0d && d2 <= 270.0d) {
                            float f20 = f18 - fAbs;
                            this.mValuePaint.setTextAlign(Paint.Align.RIGHT);
                            if (z) {
                                this.mEntryLabelsPaint.setTextAlign(Paint.Align.RIGHT);
                            }
                            f4 = f20;
                            f5 = f20 - fConvertDpToPixel;
                        } else {
                            f4 = f18 + fAbs;
                            this.mValuePaint.setTextAlign(Paint.Align.LEFT);
                            if (z) {
                                this.mEntryLabelsPaint.setTextAlign(Paint.Align.LEFT);
                            }
                            f5 = f4 + fConvertDpToPixel;
                        }
                        if (iPieDataSet3.getValueLineColor() != 1122867) {
                            f7 = radius;
                            mPPointF = mPPointF3;
                            i2 = i10;
                            fArr3 = absoluteAngles;
                            i3 = i8;
                            f6 = f5;
                            canvas.drawLine(f15, f16, f18, f19, this.mValueLinePaint);
                            canvas.drawLine(f18, f19, f4, f19, this.mValueLinePaint);
                        } else {
                            f6 = f5;
                            f7 = radius;
                            mPPointF = mPPointF3;
                            i2 = i10;
                            fArr3 = absoluteAngles;
                            i3 = i8;
                        }
                        if (z && z2) {
                            iPieDataSet = iPieDataSet3;
                            f8 = fCos;
                            drawValue(canvas, valueFormatter, y, entryForIndex, 0, f6, f19, iPieDataSet3.getValueTextColor(i3));
                            if (i3 < pieData.getEntryCount() && entryForIndex.getLabel() != null) {
                                drawEntryLabel(canvas, entryForIndex.getLabel(), f6, f19 + fCalcTextHeight);
                            }
                        } else {
                            iPieDataSet = iPieDataSet3;
                            f8 = fCos;
                            float f21 = f6;
                            if (z) {
                                if (i3 < pieData.getEntryCount() && entryForIndex.getLabel() != null) {
                                    drawEntryLabel(canvas, entryForIndex.getLabel(), f21, f19 + (fCalcTextHeight / 2.0f));
                                }
                            } else if (z2) {
                                iPieDataSet2 = iPieDataSet;
                                drawValue(canvas, valueFormatter, y, entryForIndex, 0, f21, f19 + (fCalcTextHeight / 2.0f), iPieDataSet2.getValueTextColor(i3));
                            }
                        }
                        iPieDataSet2 = iPieDataSet;
                    } else {
                        valuePosition = yValuePosition;
                        valuePosition2 = xValuePosition;
                        iPieDataSet2 = iPieDataSet3;
                        f8 = fCos;
                        f7 = radius;
                        mPPointF = mPPointF3;
                        i2 = i10;
                        fArr3 = absoluteAngles;
                        i3 = i8;
                    }
                    if (z3 || z4) {
                        float f22 = (f11 * f8) + centerCircleBox.f76x;
                        float f23 = (f11 * fSin) + centerCircleBox.f77y;
                        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
                        if (z3 && z4) {
                            f9 = fSin;
                            drawValue(canvas, valueFormatter, y, entryForIndex, 0, f22, f23, iPieDataSet2.getValueTextColor(i3));
                            if (i3 < pieData.getEntryCount() && entryForIndex.getLabel() != null) {
                                drawEntryLabel(canvas, entryForIndex.getLabel(), f22, f23 + fCalcTextHeight);
                            }
                        } else {
                            f9 = fSin;
                            if (z3) {
                                if (i3 < pieData.getEntryCount() && entryForIndex.getLabel() != null) {
                                    drawEntryLabel(canvas, entryForIndex.getLabel(), f22, f23 + (fCalcTextHeight / 2.0f));
                                }
                            } else if (z4) {
                                drawValue(canvas, valueFormatter, y, entryForIndex, 0, f22, f23 + (fCalcTextHeight / 2.0f), iPieDataSet2.getValueTextColor(i3));
                            }
                        }
                    } else {
                        f9 = fSin;
                    }
                    if (entryForIndex.getIcon() != null && iPieDataSet2.isDrawIconsEnabled()) {
                        Drawable icon = entryForIndex.getIcon();
                        Utils.drawImage(canvas, icon, (int) (((f11 + mPPointF.f77y) * f8) + centerCircleBox.f76x), (int) (((f11 + mPPointF.f77y) * f9) + centerCircleBox.f77y + mPPointF.f76x), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                    }
                    i6++;
                    i7 = i3 + 1;
                    mPPointF2 = mPPointF;
                    iPieDataSet3 = iPieDataSet2;
                    radius = f7;
                    entryCount = i2;
                    dataSets = list2;
                    i5 = i9;
                    rotationAngle = f13;
                    drawAngles = fArr4;
                    absoluteAngles = fArr3;
                    xValuePosition = valuePosition2;
                    yValuePosition = valuePosition;
                }
                i = i5;
                list = dataSets;
                f = radius;
                f2 = rotationAngle;
                fArr = drawAngles;
                fArr2 = absoluteAngles;
                MPPointF.recycleInstance(mPPointF2);
                i4 = i6;
            } else {
                i = i5;
                list = dataSets;
                f = radius;
                f2 = rotationAngle;
                fArr = drawAngles;
                fArr2 = absoluteAngles;
            }
            i5 = i + 1;
            radius = f;
            dataSets = list;
            rotationAngle = f2;
            drawAngles = fArr;
            absoluteAngles = fArr2;
        }
        MPPointF.recycleInstance(centerCircleBox);
        canvas.restore();
    }

    protected void drawEntryLabel(Canvas canvas, String str, float f, float f2) {
        canvas.drawText(str, f, f2, this.mEntryLabelsPaint);
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
        drawHole(canvas);
        canvas.drawBitmap(this.mDrawBitmap.get(), 0.0f, 0.0f, (Paint) null);
        drawCenterText(canvas);
    }

    protected void drawHole(Canvas canvas) {
        if (!this.mChart.isDrawHoleEnabled() || this.mBitmapCanvas == null) {
            return;
        }
        float radius = this.mChart.getRadius();
        float holeRadius = (this.mChart.getHoleRadius() / 100.0f) * radius;
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        if (Color.alpha(this.mHolePaint.getColor()) > 0) {
            this.mBitmapCanvas.drawCircle(centerCircleBox.f76x, centerCircleBox.f77y, holeRadius, this.mHolePaint);
        }
        if (Color.alpha(this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
            int alpha = this.mTransparentCirclePaint.getAlpha();
            float transparentCircleRadius = radius * (this.mChart.getTransparentCircleRadius() / 100.0f);
            this.mTransparentCirclePaint.setAlpha((int) (alpha * this.mAnimator.getPhaseX() * this.mAnimator.getPhaseY()));
            this.mHoleCirclePath.reset();
            this.mHoleCirclePath.addCircle(centerCircleBox.f76x, centerCircleBox.f77y, transparentCircleRadius, Path.Direction.CW);
            this.mHoleCirclePath.addCircle(centerCircleBox.f76x, centerCircleBox.f77y, holeRadius, Path.Direction.CCW);
            this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
            this.mTransparentCirclePaint.setAlpha(alpha);
        }
        MPPointF.recycleInstance(centerCircleBox);
    }

    protected void drawCenterText(Canvas canvas) {
        float radius;
        MPPointF mPPointF;
        CharSequence centerText = this.mChart.getCenterText();
        if (!this.mChart.isDrawCenterTextEnabled() || centerText == null) {
            return;
        }
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        MPPointF centerTextOffset = this.mChart.getCenterTextOffset();
        float f = centerCircleBox.f76x + centerTextOffset.f76x;
        float f2 = centerCircleBox.f77y + centerTextOffset.f77y;
        if (this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled()) {
            radius = this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0f);
        } else {
            radius = this.mChart.getRadius();
        }
        RectF rectF = this.mRectBuffer[0];
        rectF.left = f - radius;
        rectF.top = f2 - radius;
        rectF.right = f + radius;
        rectF.bottom = f2 + radius;
        RectF rectF2 = this.mRectBuffer[1];
        rectF2.set(rectF);
        float centerTextRadiusPercent = this.mChart.getCenterTextRadiusPercent() / 100.0f;
        if (centerTextRadiusPercent > 0.0d) {
            rectF2.inset((rectF2.width() - (rectF2.width() * centerTextRadiusPercent)) / 2.0f, (rectF2.height() - (rectF2.height() * centerTextRadiusPercent)) / 2.0f);
        }
        if (centerText.equals(this.mCenterTextLastValue) && rectF2.equals(this.mCenterTextLastBounds)) {
            mPPointF = centerTextOffset;
        } else {
            this.mCenterTextLastBounds.set(rectF2);
            this.mCenterTextLastValue = centerText;
            mPPointF = centerTextOffset;
            this.mCenterTextLayout = new StaticLayout(centerText, 0, centerText.length(), this.mCenterTextPaint, (int) Math.max(Math.ceil(this.mCenterTextLastBounds.width()), 1.0d), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        }
        float height = this.mCenterTextLayout.getHeight();
        canvas.save();
        if (Build.VERSION.SDK_INT >= 18) {
            Path path = this.mDrawCenterTextPathBuffer;
            path.reset();
            path.addOval(rectF, Path.Direction.CW);
            canvas.clipPath(path);
        }
        canvas.translate(rectF2.left, rectF2.top + ((rectF2.height() - height) / 2.0f));
        this.mCenterTextLayout.draw(canvas);
        canvas.restore();
        MPPointF.recycleInstance(centerCircleBox);
        MPPointF.recycleInstance(mPPointF);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        int i;
        RectF rectF;
        float f;
        float f2;
        float f3;
        float[] fArr;
        float[] fArr2;
        IPieDataSet dataSetByIndex;
        float f4;
        int i2;
        float f5;
        float f6;
        int i3;
        int i4;
        float fCalculateMinimumRadiusForSpacedSlice;
        float fMax;
        Highlight[] highlightArr2 = highlightArr;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float rotationAngle = this.mChart.getRotationAngle();
        float[] drawAngles = this.mChart.getDrawAngles();
        float[] absoluteAngles = this.mChart.getAbsoluteAngles();
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        boolean z = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float f7 = 0.0f;
        float holeRadius = z ? (this.mChart.getHoleRadius() / 100.0f) * radius : 0.0f;
        RectF rectF2 = this.mDrawHighlightedRectF;
        rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
        int i5 = 0;
        while (i5 < highlightArr2.length) {
            int x = (int) highlightArr2[i5].getX();
            if (x < drawAngles.length && (dataSetByIndex = ((PieData) this.mChart.getData()).getDataSetByIndex(highlightArr2[i5].getDataSetIndex())) != null && dataSetByIndex.isHighlightEnabled()) {
                int entryCount = dataSetByIndex.getEntryCount();
                int i6 = 0;
                for (int i7 = 0; i7 < entryCount; i7++) {
                    if (Math.abs(dataSetByIndex.getEntryForIndex(i7).getY()) > Utils.FLOAT_EPSILON) {
                        i6++;
                    }
                }
                if (x == 0) {
                    i2 = 1;
                    f4 = 0.0f;
                } else {
                    f4 = absoluteAngles[x - 1] * phaseX;
                    i2 = 1;
                }
                float sliceSpace = i6 <= i2 ? 0.0f : dataSetByIndex.getSliceSpace();
                float f8 = drawAngles[x];
                float selectionShift = dataSetByIndex.getSelectionShift();
                float f9 = radius + selectionShift;
                int i8 = i5;
                rectF2.set(this.mChart.getCircleBox());
                float f10 = -selectionShift;
                rectF2.inset(f10, f10);
                boolean z2 = sliceSpace > 0.0f && f8 <= 180.0f;
                this.mRenderPaint.setColor(dataSetByIndex.getColor(x));
                float f11 = i6 == 1 ? 0.0f : sliceSpace / (radius * 0.017453292f);
                float f12 = i6 == 1 ? 0.0f : sliceSpace / (f9 * 0.017453292f);
                float f13 = rotationAngle + ((f4 + (f11 / 2.0f)) * phaseY);
                float f14 = (f8 - f11) * phaseY;
                float f15 = f14 < 0.0f ? 0.0f : f14;
                float f16 = ((f4 + (f12 / 2.0f)) * phaseY) + rotationAngle;
                float f17 = (f8 - f12) * phaseY;
                if (f17 < 0.0f) {
                    f17 = 0.0f;
                }
                this.mPathBuffer.reset();
                if (f15 >= 360.0f && f15 % 360.0f <= Utils.FLOAT_EPSILON) {
                    this.mPathBuffer.addCircle(centerCircleBox.f76x, centerCircleBox.f77y, f9, Path.Direction.CW);
                    f5 = holeRadius;
                    f3 = phaseX;
                    fArr = drawAngles;
                    fArr2 = absoluteAngles;
                } else {
                    f5 = holeRadius;
                    f3 = phaseX;
                    double d = f16 * 0.017453292f;
                    fArr = drawAngles;
                    fArr2 = absoluteAngles;
                    this.mPathBuffer.moveTo(centerCircleBox.f76x + (((float) Math.cos(d)) * f9), centerCircleBox.f77y + (f9 * ((float) Math.sin(d))));
                    this.mPathBuffer.arcTo(rectF2, f16, f17);
                }
                if (z2) {
                    double d2 = f13 * 0.017453292f;
                    i = i8;
                    f6 = f5;
                    f2 = 0.0f;
                    rectF = rectF2;
                    i4 = 1;
                    i3 = i6;
                    fCalculateMinimumRadiusForSpacedSlice = calculateMinimumRadiusForSpacedSlice(centerCircleBox, radius, f8 * phaseY, (((float) Math.cos(d2)) * radius) + centerCircleBox.f76x, centerCircleBox.f77y + (((float) Math.sin(d2)) * radius), f13, f15);
                } else {
                    f6 = f5;
                    rectF = rectF2;
                    i3 = i6;
                    i = i8;
                    f2 = 0.0f;
                    i4 = 1;
                    fCalculateMinimumRadiusForSpacedSlice = 0.0f;
                }
                this.mInnerRectBuffer.set(centerCircleBox.f76x - f6, centerCircleBox.f77y - f6, centerCircleBox.f76x + f6, centerCircleBox.f77y + f6);
                if (!z || (f6 <= f2 && !z2)) {
                    f = f6;
                    if (f15 % 360.0f > Utils.FLOAT_EPSILON) {
                        if (z2) {
                            double d3 = (f13 + (f15 / 2.0f)) * 0.017453292f;
                            this.mPathBuffer.lineTo(centerCircleBox.f76x + (((float) Math.cos(d3)) * fCalculateMinimumRadiusForSpacedSlice), centerCircleBox.f77y + (fCalculateMinimumRadiusForSpacedSlice * ((float) Math.sin(d3))));
                        } else {
                            this.mPathBuffer.lineTo(centerCircleBox.f76x, centerCircleBox.f77y);
                        }
                    }
                } else {
                    if (z2) {
                        if (fCalculateMinimumRadiusForSpacedSlice < f2) {
                            fCalculateMinimumRadiusForSpacedSlice = -fCalculateMinimumRadiusForSpacedSlice;
                        }
                        fMax = Math.max(f6, fCalculateMinimumRadiusForSpacedSlice);
                    } else {
                        fMax = f6;
                    }
                    float f18 = (i3 == i4 || fMax == f2) ? f2 : sliceSpace / (fMax * 0.017453292f);
                    float f19 = rotationAngle + ((f4 + (f18 / 2.0f)) * phaseY);
                    float f20 = (f8 - f18) * phaseY;
                    if (f20 < f2) {
                        f20 = f2;
                    }
                    float f21 = f19 + f20;
                    if (f15 >= 360.0f && f15 % 360.0f <= Utils.FLOAT_EPSILON) {
                        this.mPathBuffer.addCircle(centerCircleBox.f76x, centerCircleBox.f77y, fMax, Path.Direction.CCW);
                        f = f6;
                    } else {
                        double d4 = f21 * 0.017453292f;
                        f = f6;
                        this.mPathBuffer.lineTo(centerCircleBox.f76x + (((float) Math.cos(d4)) * fMax), centerCircleBox.f77y + (fMax * ((float) Math.sin(d4))));
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f21, -f20);
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            } else {
                i = i5;
                rectF = rectF2;
                f = holeRadius;
                f2 = f7;
                f3 = phaseX;
                fArr = drawAngles;
                fArr2 = absoluteAngles;
            }
            i5 = i + 1;
            rectF2 = rectF;
            highlightArr2 = highlightArr;
            holeRadius = f;
            f7 = f2;
            phaseX = f3;
            drawAngles = fArr;
            absoluteAngles = fArr2;
        }
        MPPointF.recycleInstance(centerCircleBox);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void drawRoundedSlices(Canvas canvas) {
        float f;
        float[] fArr;
        float f2;
        if (this.mChart.isDrawRoundedSlicesEnabled()) {
            IPieDataSet dataSet = ((PieData) this.mChart.getData()).getDataSet();
            if (dataSet.isVisible()) {
                float phaseX = this.mAnimator.getPhaseX();
                float phaseY = this.mAnimator.getPhaseY();
                MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
                float radius = this.mChart.getRadius();
                float holeRadius = (radius - ((this.mChart.getHoleRadius() * radius) / 100.0f)) / 2.0f;
                float[] drawAngles = this.mChart.getDrawAngles();
                float rotationAngle = this.mChart.getRotationAngle();
                int i = 0;
                while (i < dataSet.getEntryCount()) {
                    float f3 = drawAngles[i];
                    if (Math.abs(dataSet.getEntryForIndex(i).getY()) > Utils.FLOAT_EPSILON) {
                        double d = radius - holeRadius;
                        double d2 = (rotationAngle + f3) * phaseY;
                        f = phaseY;
                        fArr = drawAngles;
                        f2 = rotationAngle;
                        float fCos = (float) (((double) centerCircleBox.f76x) + (Math.cos(Math.toRadians(d2)) * d));
                        float fSin = (float) ((d * Math.sin(Math.toRadians(d2))) + ((double) centerCircleBox.f77y));
                        this.mRenderPaint.setColor(dataSet.getColor(i));
                        this.mBitmapCanvas.drawCircle(fCos, fSin, holeRadius, this.mRenderPaint);
                    } else {
                        f = phaseY;
                        fArr = drawAngles;
                        f2 = rotationAngle;
                    }
                    rotationAngle = f2 + (f3 * phaseX);
                    i++;
                    phaseY = f;
                    drawAngles = fArr;
                }
                MPPointF.recycleInstance(centerCircleBox);
            }
        }
    }

    public void releaseBitmap() {
        Canvas canvas = this.mBitmapCanvas;
        if (canvas != null) {
            canvas.setBitmap(null);
            this.mBitmapCanvas = null;
        }
        WeakReference<Bitmap> weakReference = this.mDrawBitmap;
        if (weakReference != null) {
            weakReference.get().recycle();
            this.mDrawBitmap.clear();
            this.mDrawBitmap = null;
        }
    }
}
