package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BubbleChartRenderer extends BarLineScatterCandleBubbleRenderer {
    private float[] _hsvBuffer;
    protected BubbleDataProvider mChart;
    private float[] pointBuffer;
    private float[] sizeBuffer;

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
    }

    public BubbleChartRenderer(BubbleDataProvider bubbleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.sizeBuffer = new float[4];
        this.pointBuffer = new float[2];
        this._hsvBuffer = new float[3];
        this.mChart = bubbleDataProvider;
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5f));
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        for (T t : this.mChart.getBubbleData().getDataSets()) {
            if (t.isVisible()) {
                drawDataSet(canvas, t);
            }
        }
    }

    protected float getShapeSize(float f, float f2, float f3, boolean z) {
        if (z) {
            f = f2 == 0.0f ? 1.0f : (float) Math.sqrt(f / f2);
        }
        return f3 * f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void drawDataSet(Canvas canvas, IBubbleDataSet iBubbleDataSet) {
        Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
        float phaseY = this.mAnimator.getPhaseY();
        this.mXBounds.set(this.mChart, iBubbleDataSet);
        float[] fArr = this.sizeBuffer;
        fArr[0] = 0.0f;
        fArr[2] = 1.0f;
        transformer.pointValuesToPixel(fArr);
        boolean zIsNormalizeSizeEnabled = iBubbleDataSet.isNormalizeSizeEnabled();
        float[] fArr2 = this.sizeBuffer;
        float fMin = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(fArr2[2] - fArr2[0]));
        for (int i = this.mXBounds.min; i <= this.mXBounds.range + this.mXBounds.min; i++) {
            BubbleEntry bubbleEntry = (BubbleEntry) iBubbleDataSet.getEntryForIndex(i);
            this.pointBuffer[0] = bubbleEntry.getX();
            this.pointBuffer[1] = bubbleEntry.getY() * phaseY;
            transformer.pointValuesToPixel(this.pointBuffer);
            float shapeSize = getShapeSize(bubbleEntry.getSize(), iBubbleDataSet.getMaxSize(), fMin, zIsNormalizeSizeEnabled) / 2.0f;
            if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeSize) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeSize) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + shapeSize)) {
                if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - shapeSize)) {
                    return;
                }
                this.mRenderPaint.setColor(iBubbleDataSet.getColor((int) bubbleEntry.getX()));
                float[] fArr3 = this.pointBuffer;
                canvas.drawCircle(fArr3[0], fArr3[1], shapeSize, this.mRenderPaint);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        int i;
        MPPointF mPPointF;
        float f;
        float f2;
        BubbleData bubbleData = this.mChart.getBubbleData();
        if (bubbleData != null && isDrawingValuesAllowed(this.mChart)) {
            List<T> dataSets = bubbleData.getDataSets();
            float fCalcTextHeight = Utils.calcTextHeight(this.mValuePaint, "1");
            for (int i2 = 0; i2 < dataSets.size(); i2++) {
                IBubbleDataSet iBubbleDataSet = (IBubbleDataSet) dataSets.get(i2);
                if (shouldDrawValues(iBubbleDataSet)) {
                    applyValueTextStyle(iBubbleDataSet);
                    float fMax = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
                    float phaseY = this.mAnimator.getPhaseY();
                    this.mXBounds.set(this.mChart, iBubbleDataSet);
                    float[] fArrGenerateTransformedValuesBubble = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency()).generateTransformedValuesBubble(iBubbleDataSet, phaseY, this.mXBounds.min, this.mXBounds.max);
                    float f3 = fMax == 1.0f ? phaseY : fMax;
                    MPPointF mPPointF2 = MPPointF.getInstance(iBubbleDataSet.getIconsOffset());
                    mPPointF2.f76x = Utils.convertDpToPixel(mPPointF2.f76x);
                    mPPointF2.f77y = Utils.convertDpToPixel(mPPointF2.f77y);
                    int i3 = 0;
                    while (i3 < fArrGenerateTransformedValuesBubble.length) {
                        int i4 = i3 / 2;
                        int valueTextColor = iBubbleDataSet.getValueTextColor(this.mXBounds.min + i4);
                        int iArgb = Color.argb(Math.round(255.0f * f3), Color.red(valueTextColor), Color.green(valueTextColor), Color.blue(valueTextColor));
                        float f4 = fArrGenerateTransformedValuesBubble[i3];
                        float f5 = fArrGenerateTransformedValuesBubble[i3 + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(f4)) {
                            break;
                        }
                        if (this.mViewPortHandler.isInBoundsLeft(f4) && this.mViewPortHandler.isInBoundsY(f5)) {
                            BubbleEntry bubbleEntry = (BubbleEntry) iBubbleDataSet.getEntryForIndex(i4 + this.mXBounds.min);
                            if (iBubbleDataSet.isDrawValuesEnabled()) {
                                f = f5;
                                f2 = f4;
                                i = i3;
                                mPPointF = mPPointF2;
                                drawValue(canvas, iBubbleDataSet.getValueFormatter(), bubbleEntry.getSize(), bubbleEntry, i2, f4, f5 + (0.5f * fCalcTextHeight), iArgb);
                            } else {
                                f = f5;
                                f2 = f4;
                                i = i3;
                                mPPointF = mPPointF2;
                            }
                            if (bubbleEntry.getIcon() != null && iBubbleDataSet.isDrawIconsEnabled()) {
                                Drawable icon = bubbleEntry.getIcon();
                                Utils.drawImage(canvas, icon, (int) (f2 + mPPointF.f76x), (int) (f + mPPointF.f77y), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                            }
                        } else {
                            i = i3;
                            mPPointF = mPPointF2;
                        }
                        i3 = i + 2;
                        mPPointF2 = mPPointF;
                    }
                    MPPointF.recycleInstance(mPPointF2);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x013a  */
    @Override // com.github.mikephil.charting.renderer.DataRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawHighlighted(android.graphics.Canvas r18, com.github.mikephil.charting.highlight.Highlight[] r19) {
        /*
            Method dump skipped, instruction units count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.BubbleChartRenderer.drawHighlighted(android.graphics.Canvas, com.github.mikephil.charting.highlight.Highlight[]):void");
    }
}
