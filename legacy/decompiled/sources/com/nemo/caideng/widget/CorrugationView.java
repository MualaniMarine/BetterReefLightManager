package com.nemo.caideng.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class CorrugationView extends View {
    private int[] colors;
    private CountDownTimer countDownTimer;
    private int height;
    private Paint linePaint;
    private Paint paint;
    private float progress;
    private ProgressListener progressListener;
    private long totalTime;
    private int width;

    public interface ProgressListener {
        void onFinished();
    }

    public CorrugationView(Context context) {
        super(context);
        this.totalTime = 240000L;
    }

    public CorrugationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.totalTime = 240000L;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setARGB(100, 63, 81, 181);
        this.paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.linePaint = paint2;
        paint2.setARGB(255, 63, 81, 181);
        this.linePaint.setStyle(Paint.Style.STROKE);
    }

    private void initPaint() {
        this.height = getMeasuredHeight();
        this.width = getMeasuredWidth();
        if (this.colors != null) {
            int i = this.height;
            this.paint.setShader(new LinearGradient(0.0f, i, this.width, i, this.colors, (float[]) null, Shader.TileMode.CLAMP));
            return;
        }
        this.paint.setShader(null);
    }

    public void startCorrugation() {
        this.progress = 0.0f;
        if (this.countDownTimer == null) {
            this.countDownTimer = new CountDownTimer(this.totalTime, 200L) { // from class: com.nemo.caideng.widget.CorrugationView.1
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    CorrugationView.this.progress = (r0.totalTime - j) / (CorrugationView.this.totalTime * 1.0f);
                    CorrugationView.this.invalidate();
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    CorrugationView.this.stopCorrugation();
                    if (CorrugationView.this.progressListener != null) {
                        CorrugationView.this.progressListener.onFinished();
                    }
                }
            };
        }
        this.countDownTimer.start();
    }

    public void stopCorrugation() {
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.countDownTimer = null;
        }
        this.colors = null;
        this.progress = 0.0f;
        invalidate();
    }

    public void setColors(int[] iArr) {
        this.colors = iArr;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.height = getMeasuredHeight();
        this.width = getMeasuredWidth();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Log.i("绘制", "绘制进度:" + this.progress);
        canvas.drawRect(0.0f, 0.0f, this.progress * ((float) this.width), (float) this.height, this.paint);
    }

    public ProgressListener getProgressListener() {
        return this.progressListener;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }
}
