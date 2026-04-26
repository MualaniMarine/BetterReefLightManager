package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class YAxisRendererRadarChart extends YAxisRenderer {
    private RadarChart mChart;
    private Path mRenderLimitLinesPathBuffer;

    public YAxisRendererRadarChart(ViewPortHandler viewPortHandler, YAxis yAxis, RadarChart radarChart) {
        super(viewPortHandler, yAxis, null);
        this.mRenderLimitLinesPathBuffer = new Path();
        this.mChart = radarChart;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v2, types: [int] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    @Override // com.github.mikephil.charting.renderer.AxisRenderer
    protected void computeAxisValues(float f, float f2) {
        ?? r5;
        float f3 = f;
        int labelCount = this.mAxis.getLabelCount();
        double dAbs = Math.abs(f2 - f3);
        if (labelCount == 0 || dAbs <= 0.0d || Double.isInfinite(dAbs)) {
            this.mAxis.mEntries = new float[0];
            this.mAxis.mCenteredEntries = new float[0];
            this.mAxis.mEntryCount = 0;
            return;
        }
        double dRoundToNextSignificant = Utils.roundToNextSignificant(dAbs / ((double) labelCount));
        if (this.mAxis.isGranularityEnabled() && dRoundToNextSignificant < this.mAxis.getGranularity()) {
            dRoundToNextSignificant = this.mAxis.getGranularity();
        }
        double dRoundToNextSignificant2 = Utils.roundToNextSignificant(Math.pow(10.0d, (int) Math.log10(dRoundToNextSignificant)));
        if (((int) (dRoundToNextSignificant / dRoundToNextSignificant2)) > 5) {
            dRoundToNextSignificant = Math.floor(dRoundToNextSignificant2 * 10.0d);
        }
        boolean zIsCenterAxisLabelsEnabled = this.mAxis.isCenterAxisLabelsEnabled();
        if (this.mAxis.isForceLabelsEnabled()) {
            float f4 = ((float) dAbs) / (labelCount - 1);
            this.mAxis.mEntryCount = labelCount;
            if (this.mAxis.mEntries.length < labelCount) {
                this.mAxis.mEntries = new float[labelCount];
            }
            for (int i = 0; i < labelCount; i++) {
                this.mAxis.mEntries[i] = f3;
                f3 += f4;
            }
        } else {
            double dCeil = dRoundToNextSignificant == 0.0d ? 0.0d : Math.ceil(((double) f3) / dRoundToNextSignificant) * dRoundToNextSignificant;
            if (zIsCenterAxisLabelsEnabled) {
                dCeil -= dRoundToNextSignificant;
            }
            double dNextUp = dRoundToNextSignificant == 0.0d ? 0.0d : Utils.nextUp(Math.floor(((double) f2) / dRoundToNextSignificant) * dRoundToNextSignificant);
            if (dRoundToNextSignificant != 0.0d) {
                double d = dCeil;
                r5 = zIsCenterAxisLabelsEnabled;
                while (d <= dNextUp) {
                    d += dRoundToNextSignificant;
                    r5 = (r5 == true ? 1 : 0) + 1;
                }
            } else {
                r5 = zIsCenterAxisLabelsEnabled;
            }
            int i2 = r5 + 1;
            this.mAxis.mEntryCount = i2;
            if (this.mAxis.mEntries.length < i2) {
                this.mAxis.mEntries = new float[i2];
            }
            for (int i3 = 0; i3 < i2; i3++) {
                if (dCeil == 0.0d) {
                    dCeil = 0.0d;
                }
                this.mAxis.mEntries[i3] = (float) dCeil;
                dCeil += dRoundToNextSignificant;
            }
            labelCount = i2;
        }
        if (dRoundToNextSignificant < 1.0d) {
            this.mAxis.mDecimals = (int) Math.ceil(-Math.log10(dRoundToNextSignificant));
        } else {
            this.mAxis.mDecimals = 0;
        }
        if (zIsCenterAxisLabelsEnabled) {
            if (this.mAxis.mCenteredEntries.length < labelCount) {
                this.mAxis.mCenteredEntries = new float[labelCount];
            }
            float f5 = (this.mAxis.mEntries[1] - this.mAxis.mEntries[0]) / 2.0f;
            for (int i4 = 0; i4 < labelCount; i4++) {
                this.mAxis.mCenteredEntries[i4] = this.mAxis.mEntries[i4] + f5;
            }
        }
        this.mAxis.mAxisMinimum = this.mAxis.mEntries[0];
        this.mAxis.mAxisMaximum = this.mAxis.mEntries[labelCount - 1];
        this.mAxis.mAxisRange = Math.abs(this.mAxis.mAxisMaximum - this.mAxis.mAxisMinimum);
    }

    @Override // com.github.mikephil.charting.renderer.YAxisRenderer, com.github.mikephil.charting.renderer.AxisRenderer
    public void renderAxisLabels(Canvas canvas) {
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            MPPointF centerOffsets = this.mChart.getCenterOffsets();
            MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
            float factor = this.mChart.getFactor();
            int i = this.mYAxis.isDrawTopYLabelEntryEnabled() ? this.mYAxis.mEntryCount : this.mYAxis.mEntryCount - 1;
            for (int i2 = !this.mYAxis.isDrawBottomYLabelEntryEnabled() ? 1 : 0; i2 < i; i2++) {
                Utils.getPosition(centerOffsets, (this.mYAxis.mEntries[i2] - this.mYAxis.mAxisMinimum) * factor, this.mChart.getRotationAngle(), mPPointF);
                canvas.drawText(this.mYAxis.getFormattedLabel(i2), mPPointF.f76x + 10.0f, mPPointF.f77y, this.mAxisLabelPaint);
            }
            MPPointF.recycleInstance(centerOffsets);
            MPPointF.recycleInstance(mPPointF);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.YAxisRenderer, com.github.mikephil.charting.renderer.AxisRenderer
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines == null) {
            return;
        }
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        MPPointF centerOffsets = this.mChart.getCenterOffsets();
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        for (int i = 0; i < limitLines.size(); i++) {
            LimitLine limitLine = limitLines.get(i);
            if (limitLine.isEnabled()) {
                this.mLimitLinePaint.setColor(limitLine.getLineColor());
                this.mLimitLinePaint.setPathEffect(limitLine.getDashPathEffect());
                this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
                float limit = (limitLine.getLimit() - this.mChart.getYChartMin()) * factor;
                Path path = this.mRenderLimitLinesPathBuffer;
                path.reset();
                for (int i2 = 0; i2 < ((RadarData) this.mChart.getData()).getMaxEntryCountSet().getEntryCount(); i2++) {
                    Utils.getPosition(centerOffsets, limit, (i2 * sliceAngle) + this.mChart.getRotationAngle(), mPPointF);
                    if (i2 == 0) {
                        path.moveTo(mPPointF.f76x, mPPointF.f77y);
                    } else {
                        path.lineTo(mPPointF.f76x, mPPointF.f77y);
                    }
                }
                path.close();
                canvas.drawPath(path, this.mLimitLinePaint);
            }
        }
        MPPointF.recycleInstance(centerOffsets);
        MPPointF.recycleInstance(mPPointF);
    }
}
