package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CombinedChartRenderer extends DataRenderer {
    protected WeakReference<Chart> mChart;
    protected List<Highlight> mHighlightBuffer;
    protected List<DataRenderer> mRenderers;

    public CombinedChartRenderer(CombinedChart combinedChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mRenderers = new ArrayList(5);
        this.mHighlightBuffer = new ArrayList();
        this.mChart = new WeakReference<>(combinedChart);
        createRenderers();
    }

    public void createRenderers() {
        this.mRenderers.clear();
        CombinedChart combinedChart = (CombinedChart) this.mChart.get();
        if (combinedChart == null) {
            return;
        }
        for (CombinedChart.DrawOrder drawOrder : combinedChart.getDrawOrder()) {
            int i = C04371.f69x2dab6d3b[drawOrder.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            if (combinedChart.getCandleData() != null) {
                                this.mRenderers.add(new CandleStickChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                            }
                        } else if (i == 5 && combinedChart.getScatterData() != null) {
                            this.mRenderers.add(new ScatterChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                        }
                    } else if (combinedChart.getLineData() != null) {
                        this.mRenderers.add(new LineChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                    }
                } else if (combinedChart.getBubbleData() != null) {
                    this.mRenderers.add(new BubbleChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                }
            } else if (combinedChart.getBarData() != null) {
                this.mRenderers.add(new BarChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
            }
        }
    }

    /* JADX INFO: renamed from: com.github.mikephil.charting.renderer.CombinedChartRenderer$1 */
    static /* synthetic */ class C04371 {

        /* JADX INFO: renamed from: $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder */
        static final /* synthetic */ int[] f69x2dab6d3b;

        static {
            int[] iArr = new int[CombinedChart.DrawOrder.values().length];
            f69x2dab6d3b = iArr;
            try {
                iArr[CombinedChart.DrawOrder.BAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f69x2dab6d3b[CombinedChart.DrawOrder.BUBBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f69x2dab6d3b[CombinedChart.DrawOrder.LINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f69x2dab6d3b[CombinedChart.DrawOrder.CANDLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f69x2dab6d3b[CombinedChart.DrawOrder.SCATTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void initBuffers() {
        Iterator<DataRenderer> it = this.mRenderers.iterator();
        while (it.hasNext()) {
            it.next().initBuffers();
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawData(Canvas canvas) {
        Iterator<DataRenderer> it = this.mRenderers.iterator();
        while (it.hasNext()) {
            it.next().drawData(canvas);
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawValues(Canvas canvas) {
        Iterator<DataRenderer> it = this.mRenderers.iterator();
        while (it.hasNext()) {
            it.next().drawValues(canvas);
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawExtras(Canvas canvas) {
        Iterator<DataRenderer> it = this.mRenderers.iterator();
        while (it.hasNext()) {
            it.next().drawExtras(canvas);
        }
    }

    @Override // com.github.mikephil.charting.renderer.DataRenderer
    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        Chart chart = this.mChart.get();
        if (chart == null) {
            return;
        }
        for (DataRenderer dataRenderer : this.mRenderers) {
            Object bubbleData = null;
            if (dataRenderer instanceof BarChartRenderer) {
                bubbleData = ((BarChartRenderer) dataRenderer).mChart.getBarData();
            } else if (dataRenderer instanceof LineChartRenderer) {
                bubbleData = ((LineChartRenderer) dataRenderer).mChart.getLineData();
            } else if (dataRenderer instanceof CandleStickChartRenderer) {
                bubbleData = ((CandleStickChartRenderer) dataRenderer).mChart.getCandleData();
            } else if (dataRenderer instanceof ScatterChartRenderer) {
                bubbleData = ((ScatterChartRenderer) dataRenderer).mChart.getScatterData();
            } else if (dataRenderer instanceof BubbleChartRenderer) {
                bubbleData = ((BubbleChartRenderer) dataRenderer).mChart.getBubbleData();
            }
            int iIndexOf = bubbleData == null ? -1 : ((CombinedData) chart.getData()).getAllData().indexOf(bubbleData);
            this.mHighlightBuffer.clear();
            for (Highlight highlight : highlightArr) {
                if (highlight.getDataIndex() == iIndexOf || highlight.getDataIndex() == -1) {
                    this.mHighlightBuffer.add(highlight);
                }
            }
            List<Highlight> list = this.mHighlightBuffer;
            dataRenderer.drawHighlighted(canvas, (Highlight[]) list.toArray(new Highlight[list.size()]));
        }
    }

    public DataRenderer getSubRenderer(int i) {
        if (i >= this.mRenderers.size() || i < 0) {
            return null;
        }
        return this.mRenderers.get(i);
    }

    public List<DataRenderer> getSubRenderers() {
        return this.mRenderers;
    }

    public void setSubRenderers(List<DataRenderer> list) {
        this.mRenderers = list;
    }
}
