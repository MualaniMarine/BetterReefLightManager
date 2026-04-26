package com.mylhyl.zxing.scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.camera.CameraManager;
import com.mylhyl.zxing.scanner.common.Scanner;

/* JADX INFO: loaded from: classes.dex */
final class ViewfinderView extends View {
    private static final int CURRENT_POINT_OPACITY = 160;
    private static final int POINT_SIZE = 6;
    private int animationDelay;
    private CameraManager cameraManager;
    private int frameCornerLength;
    private int frameCornerWidth;
    private Bitmap laserLineBitmap;
    private int laserLineHeight;
    private int laserLineTop;
    private final Paint paint;
    private Bitmap resultBitmap;
    private int resultColor;
    private ScannerOptions scannerOptions;
    private int tipTextMargin;
    private int tipTextSize;

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animationDelay = 0;
        this.resultColor = Scanner.color.RESULT_VIEW;
        this.paint = new Paint(1);
    }

    void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    void setScannerOptions(ScannerOptions scannerOptions) {
        this.scannerOptions = scannerOptions;
        this.laserLineHeight = dp2px(scannerOptions.getLaserLineHeight());
        this.frameCornerWidth = dp2px(scannerOptions.getFrameCornerWidth());
        this.frameCornerLength = dp2px(scannerOptions.getFrameCornerLength());
        this.tipTextSize = Scanner.sp2px(getContext(), scannerOptions.getTipTextSize());
        this.tipTextMargin = dp2px(scannerOptions.getTipTextToFrameMargin());
    }

    private int dp2px(int i) {
        return Scanner.dp2px(getContext(), i);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        CameraManager cameraManager = this.cameraManager;
        if (cameraManager == null) {
            return;
        }
        Rect framingRect = cameraManager.getFramingRect();
        Rect framingRectInPreview = this.cameraManager.getFramingRectInPreview();
        if (framingRect == null || framingRectInPreview == null) {
            return;
        }
        if (!this.scannerOptions.isScanFullScreen()) {
            drawMask(canvas, framingRect);
        }
        if (this.resultBitmap != null) {
            this.paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(this.resultBitmap, (Rect) null, framingRect, this.paint);
            return;
        }
        if (!this.scannerOptions.isFrameHide()) {
            drawFrame(canvas, framingRect);
        }
        if (!this.scannerOptions.isFrameCornerHide()) {
            drawFrameCorner(canvas, framingRect);
        }
        drawText(canvas, framingRect);
        if (this.scannerOptions.isLaserMoveFullScreen()) {
            moveLaserSpeedFullScreen(this.cameraManager.getScreenResolution());
            drawLaserLineFullScreen(canvas, this.cameraManager.getScreenResolution());
        } else {
            drawLaserLine(canvas, framingRect);
            moveLaserSpeed(framingRect);
        }
        if (this.scannerOptions.getViewfinderCallback() != null) {
            this.scannerOptions.getViewfinderCallback().onDraw(this, canvas, framingRect);
        }
    }

    private void moveLaserSpeed(Rect rect) {
        if (this.laserLineTop == 0) {
            this.laserLineTop = rect.top;
        }
        int laserLineMoveSpeed = this.scannerOptions.getLaserLineMoveSpeed();
        int i = this.laserLineTop + laserLineMoveSpeed;
        this.laserLineTop = i;
        if (i >= rect.bottom) {
            this.laserLineTop = rect.top;
        }
        if (this.animationDelay == 0) {
            this.animationDelay = (int) ((laserLineMoveSpeed * 1000.0f) / (rect.bottom - rect.top));
        }
        postInvalidateDelayed(this.animationDelay, rect.left - 6, rect.top - 6, rect.right + 6, rect.bottom + 6);
    }

    private void moveLaserSpeedFullScreen(Point point) {
        int laserLineMoveSpeed = this.scannerOptions.getLaserLineMoveSpeed();
        int i = this.laserLineTop + laserLineMoveSpeed;
        this.laserLineTop = i;
        if (i >= point.y) {
            this.laserLineTop = 0;
        }
        if (this.animationDelay == 0) {
            this.animationDelay = (int) ((laserLineMoveSpeed * 1000.0f) / point.y);
        }
        postInvalidateDelayed(this.animationDelay);
    }

    private void drawMask(Canvas canvas, Rect rect) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.scannerOptions.getFrameOutsideColor());
        float f = width;
        canvas.drawRect(0.0f, 0.0f, f, rect.top, this.paint);
        canvas.drawRect(0.0f, rect.top, rect.left, rect.bottom + 1, this.paint);
        canvas.drawRect(rect.right + 1, rect.top, f, rect.bottom + 1, this.paint);
        canvas.drawRect(0.0f, rect.bottom + 1, f, height, this.paint);
    }

    private void drawText(Canvas canvas, Rect rect) {
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setFlags(1);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(this.scannerOptions.getTipTextColor());
        textPaint.setTextSize(this.tipTextSize);
        float f = rect.left;
        float f2 = !this.scannerOptions.isTipTextToFrameTop() ? rect.bottom + this.tipTextMargin : rect.top - this.tipTextMargin;
        StaticLayout staticLayout = new StaticLayout(this.scannerOptions.getTipText(), textPaint, rect.width(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(f, f2);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    private void drawFrameCorner(Canvas canvas, Rect rect) {
        this.paint.setColor(this.scannerOptions.getFrameCornerColor());
        this.paint.setStyle(Paint.Style.FILL);
        if (this.scannerOptions.isFrameCornerInside()) {
            canvas.drawRect(rect.left, rect.top, rect.left + this.frameCornerWidth, rect.top + this.frameCornerLength, this.paint);
            canvas.drawRect(rect.left, rect.top, rect.left + this.frameCornerLength, rect.top + this.frameCornerWidth, this.paint);
            canvas.drawRect(rect.right - this.frameCornerWidth, rect.top, rect.right, rect.top + this.frameCornerLength, this.paint);
            canvas.drawRect(rect.right - this.frameCornerLength, rect.top, rect.right, rect.top + this.frameCornerWidth, this.paint);
            canvas.drawRect(rect.left, rect.bottom - this.frameCornerLength, rect.left + this.frameCornerWidth, rect.bottom, this.paint);
            canvas.drawRect(rect.left, rect.bottom - this.frameCornerWidth, rect.left + this.frameCornerLength, rect.bottom, this.paint);
            canvas.drawRect(rect.right - this.frameCornerWidth, rect.bottom - this.frameCornerLength, rect.right, rect.bottom, this.paint);
            canvas.drawRect(rect.right - this.frameCornerLength, rect.bottom - this.frameCornerWidth, rect.right, rect.bottom, this.paint);
            return;
        }
        canvas.drawRect(rect.left - this.frameCornerWidth, rect.top, rect.left, rect.top + this.frameCornerLength, this.paint);
        canvas.drawRect(rect.left - this.frameCornerWidth, rect.top - this.frameCornerWidth, rect.left + this.frameCornerLength, rect.top, this.paint);
        canvas.drawRect(rect.right, rect.top, rect.right + this.frameCornerWidth, rect.top + this.frameCornerLength, this.paint);
        canvas.drawRect(rect.right - this.frameCornerLength, rect.top - this.frameCornerWidth, rect.right + this.frameCornerWidth, rect.top, this.paint);
        canvas.drawRect(rect.left - this.frameCornerWidth, rect.bottom - this.frameCornerLength, rect.left, rect.bottom, this.paint);
        canvas.drawRect(rect.left - this.frameCornerWidth, rect.bottom, rect.left + this.frameCornerLength, rect.bottom + this.frameCornerWidth, this.paint);
        canvas.drawRect(rect.right, rect.bottom - this.frameCornerLength, rect.right + this.frameCornerWidth, rect.bottom, this.paint);
        canvas.drawRect(rect.right - this.frameCornerLength, rect.bottom, rect.right + this.frameCornerWidth, rect.bottom + this.frameCornerWidth, this.paint);
    }

    private void drawFrame(Canvas canvas, Rect rect) {
        this.paint.setColor(this.scannerOptions.getFrameStrokeColor());
        this.paint.setStrokeWidth(this.scannerOptions.getFrameStrokeWidth());
        this.paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect, this.paint);
    }

    private void drawLaserLine(Canvas canvas, Rect rect) {
        ScannerOptions.LaserStyle laserStyle = this.scannerOptions.getLaserStyle();
        if (laserStyle == ScannerOptions.LaserStyle.COLOR_LINE) {
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.scannerOptions.getLaserLineColor());
            canvas.drawRect(rect.left, this.laserLineTop, rect.right, this.laserLineTop + this.laserLineHeight, this.paint);
            return;
        }
        if (this.laserLineBitmap == null) {
            if (laserStyle == ScannerOptions.LaserStyle.DRAWABLE_LINE || laserStyle == ScannerOptions.LaserStyle.DRAWABLE_GRID) {
                this.laserLineBitmap = Scanner.drawableToBitmap(this.scannerOptions.getLaserLineDrawable());
            } else {
                this.laserLineBitmap = BitmapFactory.decodeResource(getResources(), this.scannerOptions.getLaserLineResId());
            }
        }
        int height = this.laserLineBitmap.getHeight();
        if (laserStyle == ScannerOptions.LaserStyle.RES_GRID || laserStyle == ScannerOptions.LaserStyle.DRAWABLE_GRID) {
            RectF rectF = new RectF(rect.left, rect.top, rect.right, this.laserLineTop);
            canvas.drawBitmap(this.laserLineBitmap, new Rect(0, (int) (height - rectF.height()), this.laserLineBitmap.getWidth(), height), rectF, this.paint);
        } else {
            if (this.laserLineHeight == dp2px(2)) {
                this.laserLineHeight = this.laserLineBitmap.getHeight() / 2;
            }
            canvas.drawBitmap(this.laserLineBitmap, (Rect) null, new Rect(rect.left, this.laserLineTop, rect.right, this.laserLineTop + this.laserLineHeight), this.paint);
        }
    }

    private void drawLaserLineFullScreen(Canvas canvas, Point point) {
        ScannerOptions.LaserStyle laserStyle = this.scannerOptions.getLaserStyle();
        if (laserStyle == ScannerOptions.LaserStyle.COLOR_LINE) {
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.scannerOptions.getLaserLineColor());
            canvas.drawRect(0.0f, this.laserLineTop, point.x, this.laserLineTop + this.laserLineHeight, this.paint);
            return;
        }
        if (this.laserLineBitmap == null) {
            if (laserStyle == ScannerOptions.LaserStyle.DRAWABLE_LINE || laserStyle == ScannerOptions.LaserStyle.DRAWABLE_GRID) {
                this.laserLineBitmap = Scanner.drawableToBitmap(this.scannerOptions.getLaserLineDrawable());
            } else {
                this.laserLineBitmap = BitmapFactory.decodeResource(getResources(), this.scannerOptions.getLaserLineResId());
            }
        }
        int height = this.laserLineBitmap.getHeight();
        if (laserStyle == ScannerOptions.LaserStyle.RES_GRID || laserStyle == ScannerOptions.LaserStyle.DRAWABLE_GRID) {
            RectF rectF = new RectF(0.0f, this.laserLineTop >= height ? r0 - height : 0, point.x, this.laserLineTop);
            canvas.drawBitmap(this.laserLineBitmap, new Rect(0, (int) (height - rectF.height()), this.laserLineBitmap.getWidth(), height), rectF, this.paint);
        } else {
            if (this.laserLineHeight == dp2px(2)) {
                this.laserLineHeight = this.laserLineBitmap.getHeight() / 2;
            }
            canvas.drawBitmap(this.laserLineBitmap, (Rect) null, new Rect(0, this.laserLineTop, point.x, this.laserLineTop + this.laserLineHeight), this.paint);
        }
    }

    void drawViewfinder() {
        Bitmap bitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (bitmap != null) {
            bitmap.recycle();
        }
        invalidate();
    }

    void drawResultBitmap(Bitmap bitmap) {
        this.resultBitmap = bitmap;
        invalidate();
    }

    void laserLineBitmapRecycle() {
        Bitmap bitmap = this.laserLineBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.laserLineBitmap = null;
        }
    }
}
