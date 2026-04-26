package com.mylhyl.zxing.scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.camera.open.CameraFacing;
import com.mylhyl.zxing.scanner.common.Scanner;

/* JADX INFO: loaded from: classes.dex */
public class ScannerView extends RelativeLayout {
    private static final String TAG = ScannerView.class.getSimpleName();
    private BeepManager mBeepManager;
    private OnScannerCompletionListener mScannerCompletionListener;
    private ScannerOptions mScannerOptions;
    private ScannerOptions.Builder mScannerOptionsBuilder;
    private CameraSurfaceView mSurfaceView;
    private ViewfinderView mViewfinderView;

    public ScannerView(Context context) {
        this(context, null);
    }

    public ScannerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScannerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private static void drawLine(Canvas canvas, Paint paint, ResultPoint resultPoint, ResultPoint resultPoint2, float f) {
        if (resultPoint == null || resultPoint2 == null) {
            return;
        }
        canvas.drawLine(f * resultPoint.getX(), f * resultPoint.getY(), f * resultPoint2.getX(), f * resultPoint2.getY(), paint);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        CameraSurfaceView cameraSurfaceView = new CameraSurfaceView(context, this);
        this.mSurfaceView = cameraSurfaceView;
        cameraSurfaceView.setId(android.R.id.list);
        addView(this.mSurfaceView);
        this.mViewfinderView = new ViewfinderView(context, attributeSet);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(context, attributeSet);
        layoutParams.addRule(6, this.mSurfaceView.getId());
        layoutParams.addRule(8, this.mSurfaceView.getId());
        addView(this.mViewfinderView, layoutParams);
        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        this.mScannerOptionsBuilder = builder;
        this.mScannerOptions = builder.build();
    }

    public void onResume() {
        this.mSurfaceView.onResume(this.mScannerOptions);
        this.mViewfinderView.setCameraManager(this.mSurfaceView.getCameraManager());
        this.mViewfinderView.setScannerOptions(this.mScannerOptions);
        this.mViewfinderView.setVisibility(this.mScannerOptions.isViewfinderHide() ? 8 : 0);
        BeepManager beepManager = this.mBeepManager;
        if (beepManager != null) {
            beepManager.updatePrefs();
        }
    }

    public void onPause() {
        this.mSurfaceView.onPause();
        BeepManager beepManager = this.mBeepManager;
        if (beepManager != null) {
            beepManager.close();
        }
        this.mViewfinderView.laserLineBitmapRecycle();
    }

    void handleDecode(Result result, Bitmap bitmap, float f) {
        OnScannerCompletionListener onScannerCompletionListener = this.mScannerCompletionListener;
        if (onScannerCompletionListener != null) {
            onScannerCompletionListener.onScannerCompletion(result, Scanner.parseResult(result), bitmap);
        }
        if (this.mScannerOptions.getMediaResId() != 0) {
            if (this.mBeepManager == null) {
                BeepManager beepManager = new BeepManager(getContext());
                this.mBeepManager = beepManager;
                beepManager.setMediaResId(this.mScannerOptions.getMediaResId());
            }
            this.mBeepManager.playBeepSoundAndVibrate();
        }
        if (bitmap == null || !this.mScannerOptions.isShowQrThumbnail()) {
            return;
        }
        this.mViewfinderView.drawResultBitmap(bitmap);
        drawResultPoints(bitmap, f, result);
    }

    private void drawResultPoints(Bitmap bitmap, float f, Result result) {
        ResultPoint[] resultPoints = result.getResultPoints();
        if (resultPoints == null || resultPoints.length <= 0) {
            return;
        }
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Scanner.color.RESULT_POINTS);
        if (resultPoints.length == 2) {
            paint.setStrokeWidth(4.0f);
            drawLine(canvas, paint, resultPoints[0], resultPoints[1], f);
            return;
        }
        if (resultPoints.length == 4 && (result.getBarcodeFormat() == BarcodeFormat.UPC_A || result.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
            drawLine(canvas, paint, resultPoints[0], resultPoints[1], f);
            drawLine(canvas, paint, resultPoints[2], resultPoints[3], f);
            return;
        }
        paint.setStrokeWidth(10.0f);
        for (ResultPoint resultPoint : resultPoints) {
            if (resultPoint != null) {
                canvas.drawPoint(resultPoint.getX() * f, resultPoint.getY() * f, paint);
            }
        }
    }

    public ScannerView setOnScannerCompletionListener(OnScannerCompletionListener onScannerCompletionListener) {
        this.mScannerCompletionListener = onScannerCompletionListener;
        return this;
    }

    public void setScannerOptions(ScannerOptions scannerOptions) {
        this.mScannerOptions = scannerOptions;
    }

    public ScannerView toggleLight(boolean z) {
        this.mSurfaceView.setTorch(z);
        return this;
    }

    public void restartPreviewAfterDelay(long j) {
        this.mSurfaceView.restartPreviewAfterDelay(j);
    }

    @Deprecated
    public ScannerView setLaserColor(int i) {
        this.mScannerOptionsBuilder.setLaserLine(ScannerOptions.LaserStyle.COLOR_LINE, i);
        return this;
    }

    @Deprecated
    public ScannerView setLaserLineResId(int i) {
        this.mScannerOptionsBuilder.setLaserLine(ScannerOptions.LaserStyle.RES_LINE, i);
        return this;
    }

    @Deprecated
    public ScannerView setLaserGridLineResId(int i) {
        this.mScannerOptionsBuilder.setLaserLine(ScannerOptions.LaserStyle.RES_GRID, i);
        return this;
    }

    @Deprecated
    public ScannerView setLaserLineHeight(int i) {
        this.mScannerOptionsBuilder.setLaserLineHeight(i);
        return this;
    }

    @Deprecated
    public ScannerView setLaserFrameBoundColor(int i) {
        this.mScannerOptionsBuilder.setFrameCornerColor(i);
        return this;
    }

    @Deprecated
    public ScannerView setLaserFrameCornerLength(int i) {
        this.mScannerOptionsBuilder.setFrameCornerLength(i);
        return this;
    }

    @Deprecated
    public ScannerView setLaserFrameCornerWidth(int i) {
        this.mScannerOptionsBuilder.setFrameCornerWidth(i);
        return this;
    }

    @Deprecated
    public ScannerView setDrawTextColor(int i) {
        this.mScannerOptionsBuilder.setTipTextColor(i);
        return this;
    }

    @Deprecated
    public ScannerView setDrawTextSize(int i) {
        this.mScannerOptionsBuilder.setTipTextSize(i);
        return this;
    }

    @Deprecated
    public ScannerView setDrawText(String str, boolean z) {
        this.mScannerOptionsBuilder.setTipText(str);
        this.mScannerOptionsBuilder.setTipTextToFrameTop(!z);
        return this;
    }

    @Deprecated
    public ScannerView setDrawText(String str, boolean z, int i) {
        this.mScannerOptionsBuilder.setTipText(str);
        this.mScannerOptionsBuilder.setTipTextToFrameTop(!z);
        this.mScannerOptionsBuilder.setTipTextToFrameMargin(i);
        return this;
    }

    @Deprecated
    public ScannerView setDrawText(String str, int i, int i2, boolean z, int i3) {
        this.mScannerOptionsBuilder.setTipText(str);
        this.mScannerOptionsBuilder.setTipTextSize(i);
        this.mScannerOptionsBuilder.setTipTextColor(i2);
        this.mScannerOptionsBuilder.setTipTextToFrameTop(!z);
        this.mScannerOptionsBuilder.setTipTextToFrameMargin(i3);
        return this;
    }

    @Deprecated
    public ScannerView setMediaResId(int i) {
        this.mScannerOptionsBuilder.setMediaResId(i);
        return this;
    }

    @Deprecated
    public ScannerView setLaserFrameSize(int i, int i2) {
        this.mScannerOptionsBuilder.setFrameSize(i, i2);
        return this;
    }

    @Deprecated
    public ScannerView setLaserFrameTopMargin(int i) {
        this.mScannerOptionsBuilder.setFrameTopMargin(i);
        return this;
    }

    @Deprecated
    public ScannerView setScanMode(String str) {
        this.mScannerOptionsBuilder.setScanMode(str);
        return this;
    }

    @Deprecated
    public ScannerView setScanMode(BarcodeFormat... barcodeFormatArr) {
        this.mScannerOptionsBuilder.setScanMode(barcodeFormatArr);
        return this;
    }

    @Deprecated
    public ScannerView isShowResThumbnail(boolean z) {
        this.mScannerOptionsBuilder.setCreateQrThumbnail(z);
        return this;
    }

    @Deprecated
    public ScannerView setLaserMoveSpeed(int i) {
        this.mScannerOptionsBuilder.setLaserMoveSpeed(i);
        return this;
    }

    @Deprecated
    public ScannerView setCameraFacing(CameraFacing cameraFacing) {
        this.mScannerOptionsBuilder.setCameraFacing(cameraFacing);
        return this;
    }

    @Deprecated
    public ScannerView isScanFullScreen(boolean z) {
        this.mScannerOptionsBuilder.setScanFullScreen(z);
        return this;
    }

    @Deprecated
    public ScannerView isHideLaserFrame(boolean z) {
        this.mScannerOptionsBuilder.setViewfinderHide(z);
        return this;
    }

    @Deprecated
    public ScannerView isScanInvert(boolean z) {
        this.mScannerOptionsBuilder.setScanInvert(z);
        return this;
    }

    void drawViewfinder() {
        this.mViewfinderView.drawViewfinder();
    }
}
