package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class LegendRenderer extends Renderer {
    protected List<LegendEntry> computedEntries;
    protected Paint.FontMetrics legendFontMetrics;
    protected Legend mLegend;
    protected Paint mLegendFormPaint;
    protected Paint mLegendLabelPaint;
    private Path mLineFormPath;

    public LegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler);
        this.computedEntries = new ArrayList(16);
        this.legendFontMetrics = new Paint.FontMetrics();
        this.mLineFormPath = new Path();
        this.mLegend = legend;
        Paint paint = new Paint(1);
        this.mLegendLabelPaint = paint;
        paint.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mLegendLabelPaint.setTextAlign(Paint.Align.LEFT);
        Paint paint2 = new Paint(1);
        this.mLegendFormPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
    }

    public Paint getLabelPaint() {
        return this.mLegendLabelPaint;
    }

    public Paint getFormPaint() {
        return this.mLegendFormPaint;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0153  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void computeLegend(com.github.mikephil.charting.data.ChartData<?> r19) {
        /*
            Method dump skipped, instruction units count: 494
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LegendRenderer.computeLegend(com.github.mikephil.charting.data.ChartData):void");
    }

    public void renderLegend(Canvas canvas) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        List<Boolean> list;
        float f7;
        List<FSize> list2;
        Canvas canvas2;
        int i;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        float f13;
        float fContentTop;
        float f14;
        float f15;
        Legend.LegendDirection legendDirection;
        LegendEntry legendEntry;
        float fCalcTextWidth;
        float fContentBottom;
        float fContentRight;
        float fContentLeft;
        double d;
        if (this.mLegend.isEnabled()) {
            Typeface typeface = this.mLegend.getTypeface();
            if (typeface != null) {
                this.mLegendLabelPaint.setTypeface(typeface);
            }
            this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
            this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
            float lineHeight = Utils.getLineHeight(this.mLegendLabelPaint, this.legendFontMetrics);
            float lineSpacing = Utils.getLineSpacing(this.mLegendLabelPaint, this.legendFontMetrics) + Utils.convertDpToPixel(this.mLegend.getYEntrySpace());
            float fCalcTextHeight = lineHeight - (Utils.calcTextHeight(this.mLegendLabelPaint, "ABC") / 2.0f);
            LegendEntry[] entries = this.mLegend.getEntries();
            float fConvertDpToPixel = Utils.convertDpToPixel(this.mLegend.getFormToTextSpace());
            float fConvertDpToPixel2 = Utils.convertDpToPixel(this.mLegend.getXEntrySpace());
            Legend.LegendOrientation orientation = this.mLegend.getOrientation();
            Legend.LegendHorizontalAlignment horizontalAlignment = this.mLegend.getHorizontalAlignment();
            Legend.LegendVerticalAlignment verticalAlignment = this.mLegend.getVerticalAlignment();
            Legend.LegendDirection direction = this.mLegend.getDirection();
            float fConvertDpToPixel3 = Utils.convertDpToPixel(this.mLegend.getFormSize());
            float fConvertDpToPixel4 = Utils.convertDpToPixel(this.mLegend.getStackSpace());
            float yOffset = this.mLegend.getYOffset();
            float xOffset = this.mLegend.getXOffset();
            int i2 = C04381.f71x2787f53e[horizontalAlignment.ordinal()];
            float f16 = fConvertDpToPixel4;
            float f17 = fConvertDpToPixel2;
            if (i2 != 1) {
                if (i2 == 2) {
                    f = lineHeight;
                    f2 = lineSpacing;
                    if (orientation == Legend.LegendOrientation.VERTICAL) {
                        fContentRight = this.mViewPortHandler.getChartWidth();
                    } else {
                        fContentRight = this.mViewPortHandler.contentRight();
                    }
                    f4 = fContentRight - xOffset;
                    if (direction == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        f4 -= this.mLegend.mNeededWidth;
                    }
                } else if (i2 != 3) {
                    f = lineHeight;
                    f2 = lineSpacing;
                    f3 = 0.0f;
                } else {
                    if (orientation == Legend.LegendOrientation.VERTICAL) {
                        fContentLeft = this.mViewPortHandler.getChartWidth() / 2.0f;
                    } else {
                        fContentLeft = this.mViewPortHandler.contentLeft() + (this.mViewPortHandler.contentWidth() / 2.0f);
                    }
                    f4 = fContentLeft + (direction == Legend.LegendDirection.LEFT_TO_RIGHT ? xOffset : -xOffset);
                    if (orientation == Legend.LegendOrientation.VERTICAL) {
                        f2 = lineSpacing;
                        double d2 = f4;
                        if (direction == Legend.LegendDirection.LEFT_TO_RIGHT) {
                            f = lineHeight;
                            d = (((double) (-this.mLegend.mNeededWidth)) / 2.0d) + ((double) xOffset);
                        } else {
                            f = lineHeight;
                            d = (((double) this.mLegend.mNeededWidth) / 2.0d) - ((double) xOffset);
                        }
                        f4 = (float) (d2 + d);
                    } else {
                        f = lineHeight;
                        f2 = lineSpacing;
                    }
                }
                f3 = f4;
            } else {
                f = lineHeight;
                f2 = lineSpacing;
                if (orientation != Legend.LegendOrientation.VERTICAL) {
                    xOffset += this.mViewPortHandler.contentLeft();
                }
                if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                    f4 = this.mLegend.mNeededWidth + xOffset;
                    f3 = f4;
                } else {
                    f3 = xOffset;
                }
            }
            int i3 = C04381.f72x9c9dbef[orientation.ordinal()];
            if (i3 != 1) {
                if (i3 != 2) {
                    return;
                }
                int i4 = C04381.f73xc926f1ec[verticalAlignment.ordinal()];
                if (i4 == 1) {
                    fContentTop = (horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER ? 0.0f : this.mViewPortHandler.contentTop()) + yOffset;
                } else if (i4 == 2) {
                    if (horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER) {
                        fContentBottom = this.mViewPortHandler.getChartHeight();
                    } else {
                        fContentBottom = this.mViewPortHandler.contentBottom();
                    }
                    fContentTop = fContentBottom - (this.mLegend.mNeededHeight + yOffset);
                } else {
                    fContentTop = i4 != 3 ? 0.0f : ((this.mViewPortHandler.getChartHeight() / 2.0f) - (this.mLegend.mNeededHeight / 2.0f)) + this.mLegend.getYOffset();
                }
                float f18 = fContentTop;
                float f19 = 0.0f;
                boolean z = false;
                int i5 = 0;
                while (i5 < entries.length) {
                    LegendEntry legendEntry2 = entries[i5];
                    boolean z2 = legendEntry2.form != Legend.LegendForm.NONE;
                    float fConvertDpToPixel5 = Float.isNaN(legendEntry2.formSize) ? fConvertDpToPixel3 : Utils.convertDpToPixel(legendEntry2.formSize);
                    if (z2) {
                        fCalcTextWidth = direction == Legend.LegendDirection.LEFT_TO_RIGHT ? f3 + f19 : f3 - (fConvertDpToPixel5 - f19);
                        f15 = f16;
                        f14 = fCalcTextHeight;
                        legendDirection = direction;
                        drawForm(canvas, fCalcTextWidth, f18 + fCalcTextHeight, legendEntry2, this.mLegend);
                        if (legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT) {
                            fCalcTextWidth += fConvertDpToPixel5;
                        }
                        legendEntry = legendEntry2;
                    } else {
                        f14 = fCalcTextHeight;
                        f15 = f16;
                        legendDirection = direction;
                        legendEntry = legendEntry2;
                        fCalcTextWidth = f3;
                    }
                    if (legendEntry.label != null) {
                        if (z2 && !z) {
                            fCalcTextWidth += legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? fConvertDpToPixel : -fConvertDpToPixel;
                        } else if (z) {
                            fCalcTextWidth = f3;
                        }
                        if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                            fCalcTextWidth -= Utils.calcTextWidth(this.mLegendLabelPaint, legendEntry.label);
                        }
                        float f20 = fCalcTextWidth;
                        if (!z) {
                            drawLabel(canvas, f20, f18 + f, legendEntry.label);
                        } else {
                            f18 += f + f2;
                            drawLabel(canvas, f20, f18 + f, legendEntry.label);
                        }
                        f18 += f + f2;
                        f19 = 0.0f;
                    } else {
                        f19 += fConvertDpToPixel5 + f15;
                        z = true;
                    }
                    i5++;
                    f16 = f15;
                    direction = legendDirection;
                    fCalcTextHeight = f14;
                }
                return;
            }
            float f21 = f16;
            List<FSize> calculatedLineSizes = this.mLegend.getCalculatedLineSizes();
            List<FSize> calculatedLabelSizes = this.mLegend.getCalculatedLabelSizes();
            List<Boolean> calculatedLabelBreakPoints = this.mLegend.getCalculatedLabelBreakPoints();
            int i6 = C04381.f73xc926f1ec[verticalAlignment.ordinal()];
            if (i6 != 1) {
                if (i6 == 2) {
                    yOffset = (this.mViewPortHandler.getChartHeight() - yOffset) - this.mLegend.mNeededHeight;
                } else {
                    yOffset = i6 != 3 ? 0.0f : yOffset + ((this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0f);
                }
            }
            int length = entries.length;
            float f22 = f3;
            int i7 = 0;
            int i8 = 0;
            while (i7 < length) {
                float f23 = f21;
                LegendEntry legendEntry3 = entries[i7];
                int i9 = length;
                boolean z3 = legendEntry3.form != Legend.LegendForm.NONE;
                float fConvertDpToPixel6 = Float.isNaN(legendEntry3.formSize) ? fConvertDpToPixel3 : Utils.convertDpToPixel(legendEntry3.formSize);
                if (i7 >= calculatedLabelBreakPoints.size() || !calculatedLabelBreakPoints.get(i7).booleanValue()) {
                    f5 = f22;
                    f6 = yOffset;
                } else {
                    f6 = yOffset + f + f2;
                    f5 = f3;
                }
                if (f5 == f3 && horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER && i8 < calculatedLineSizes.size()) {
                    if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f13 = calculatedLineSizes.get(i8).width;
                    } else {
                        f13 = -calculatedLineSizes.get(i8).width;
                    }
                    f5 += f13 / 2.0f;
                    i8++;
                }
                int i10 = i8;
                boolean z4 = legendEntry3.label == null;
                if (z3) {
                    if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f5 -= fConvertDpToPixel6;
                    }
                    float f24 = f5;
                    f7 = f3;
                    i = i7;
                    list = calculatedLabelBreakPoints;
                    list2 = calculatedLineSizes;
                    canvas2 = canvas;
                    drawForm(canvas, f24, f6 + fCalcTextHeight, legendEntry3, this.mLegend);
                    f5 = direction == Legend.LegendDirection.LEFT_TO_RIGHT ? f24 + fConvertDpToPixel6 : f24;
                } else {
                    list = calculatedLabelBreakPoints;
                    f7 = f3;
                    list2 = calculatedLineSizes;
                    canvas2 = canvas;
                    i = i7;
                }
                if (!z4) {
                    if (z3) {
                        f5 += direction == Legend.LegendDirection.RIGHT_TO_LEFT ? -fConvertDpToPixel : fConvertDpToPixel;
                    }
                    if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f5 -= calculatedLabelSizes.get(i).width;
                    }
                    drawLabel(canvas2, f5, f6 + f, legendEntry3.label);
                    if (direction == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        f5 += calculatedLabelSizes.get(i).width;
                    }
                    if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f8 = f17;
                        f12 = -f8;
                    } else {
                        f8 = f17;
                        f12 = f8;
                    }
                    f11 = f5 + f12;
                    f9 = f23;
                } else {
                    f8 = f17;
                    if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f9 = f23;
                        f10 = -f9;
                    } else {
                        f9 = f23;
                        f10 = f9;
                    }
                    f11 = f5 + f10;
                }
                f17 = f8;
                f21 = f9;
                i7 = i + 1;
                yOffset = f6;
                i8 = i10;
                f3 = f7;
                calculatedLabelBreakPoints = list;
                calculatedLineSizes = list2;
                f22 = f11;
                length = i9;
            }
        }
    }

    protected void drawForm(Canvas canvas, float f, float f2, LegendEntry legendEntry, Legend legend) {
        if (legendEntry.formColor == 1122868 || legendEntry.formColor == 1122867 || legendEntry.formColor == 0) {
            return;
        }
        int iSave = canvas.save();
        Legend.LegendForm form = legendEntry.form;
        if (form == Legend.LegendForm.DEFAULT) {
            form = legend.getForm();
        }
        this.mLegendFormPaint.setColor(legendEntry.formColor);
        float fConvertDpToPixel = Utils.convertDpToPixel(Float.isNaN(legendEntry.formSize) ? legend.getFormSize() : legendEntry.formSize);
        float f3 = fConvertDpToPixel / 2.0f;
        int i = C04381.f70xfbec3b85[form.ordinal()];
        if (i == 3 || i == 4) {
            this.mLegendFormPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(f + f3, f2, f3, this.mLegendFormPaint);
        } else if (i == 5) {
            this.mLegendFormPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(f, f2 - f3, f + fConvertDpToPixel, f2 + f3, this.mLegendFormPaint);
        } else if (i == 6) {
            float fConvertDpToPixel2 = Utils.convertDpToPixel(Float.isNaN(legendEntry.formLineWidth) ? legend.getFormLineWidth() : legendEntry.formLineWidth);
            DashPathEffect formLineDashEffect = legendEntry.formLineDashEffect == null ? legend.getFormLineDashEffect() : legendEntry.formLineDashEffect;
            this.mLegendFormPaint.setStyle(Paint.Style.STROKE);
            this.mLegendFormPaint.setStrokeWidth(fConvertDpToPixel2);
            this.mLegendFormPaint.setPathEffect(formLineDashEffect);
            this.mLineFormPath.reset();
            this.mLineFormPath.moveTo(f, f2);
            this.mLineFormPath.lineTo(f + fConvertDpToPixel, f2);
            canvas.drawPath(this.mLineFormPath, this.mLegendFormPaint);
        }
        canvas.restoreToCount(iSave);
    }

    /* JADX INFO: renamed from: com.github.mikephil.charting.renderer.LegendRenderer$1 */
    static /* synthetic */ class C04381 {

        /* JADX INFO: renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm */
        static final /* synthetic */ int[] f70xfbec3b85;

        /* JADX INFO: renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment */
        static final /* synthetic */ int[] f71x2787f53e;

        /* JADX INFO: renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation */
        static final /* synthetic */ int[] f72x9c9dbef;

        /* JADX INFO: renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment */
        static final /* synthetic */ int[] f73xc926f1ec;

        static {
            int[] iArr = new int[Legend.LegendForm.values().length];
            f70xfbec3b85 = iArr;
            try {
                iArr[Legend.LegendForm.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f70xfbec3b85[Legend.LegendForm.EMPTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f70xfbec3b85[Legend.LegendForm.DEFAULT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f70xfbec3b85[Legend.LegendForm.CIRCLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f70xfbec3b85[Legend.LegendForm.SQUARE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f70xfbec3b85[Legend.LegendForm.LINE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[Legend.LegendOrientation.values().length];
            f72x9c9dbef = iArr2;
            try {
                iArr2[Legend.LegendOrientation.HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f72x9c9dbef[Legend.LegendOrientation.VERTICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[Legend.LegendVerticalAlignment.values().length];
            f73xc926f1ec = iArr3;
            try {
                iArr3[Legend.LegendVerticalAlignment.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f73xc926f1ec[Legend.LegendVerticalAlignment.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f73xc926f1ec[Legend.LegendVerticalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr4 = new int[Legend.LegendHorizontalAlignment.values().length];
            f71x2787f53e = iArr4;
            try {
                iArr4[Legend.LegendHorizontalAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f71x2787f53e[Legend.LegendHorizontalAlignment.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f71x2787f53e[Legend.LegendHorizontalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    protected void drawLabel(Canvas canvas, float f, float f2, String str) {
        canvas.drawText(str, f, f2, this.mLegendLabelPaint);
    }
}
