package com.mylhyl.zxing.scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.google.zxing.Result;
import com.mylhyl.zxing.scanner.ScannerViewHandler;
import com.mylhyl.zxing.scanner.camera.CameraManager;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, ScannerViewHandler.HandleDecodeListener {
    private static final String TAG = CameraSurfaceView.class.getSimpleName();
    private boolean hasSurface;
    private boolean lightMode;
    private CameraManager mCameraManager;
    private ScannerOptions mScannerOptions;
    private ScannerView mScannerView;
    private ScannerViewHandler mScannerViewHandler;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    CameraSurfaceView(Context context, ScannerView scannerView) {
        super(context);
        this.lightMode = false;
        this.mScannerView = scannerView;
        this.hasSurface = false;
    }

    void onResume(ScannerOptions scannerOptions) {
        this.mScannerOptions = scannerOptions;
        this.mCameraManager = new CameraManager(getContext(), this.mScannerOptions);
        this.mScannerViewHandler = null;
        SurfaceHolder holder = getHolder();
        if (this.hasSurface) {
            initCamera(holder);
        } else {
            holder.addCallback(this);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (this.mCameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            this.mCameraManager.openDriver(surfaceHolder);
            requestLayout();
            this.mCameraManager.setTorch(this.lightMode);
            if (this.mScannerViewHandler == null) {
                this.mScannerViewHandler = new ScannerViewHandler(this.mScannerOptions, this.mCameraManager, this);
            }
        } catch (IOException e) {
            Log.w(TAG, e);
        } catch (RuntimeException e2) {
            Log.w(TAG, "Unexpected error initializing camera", e2);
        }
    }

    void onPause() {
        ScannerViewHandler scannerViewHandler = this.mScannerViewHandler;
        if (scannerViewHandler != null) {
            scannerViewHandler.quitSynchronously();
            this.mScannerViewHandler = null;
        }
        this.mCameraManager.closeDriver();
    }

    void setTorch(boolean z) {
        this.lightMode = z;
        CameraManager cameraManager = this.mCameraManager;
        if (cameraManager != null) {
            cameraManager.setTorch(z);
        }
    }

    void restartPreviewAfterDelay(long j) {
        ScannerViewHandler scannerViewHandler = this.mScannerViewHandler;
        if (scannerViewHandler != null) {
            scannerViewHandler.sendEmptyMessageDelayed(0, j);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (this.hasSurface) {
            return;
        }
        this.hasSurface = true;
        initCamera(surfaceHolder);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.hasSurface = false;
        if (0 != 0 || surfaceHolder == null) {
            return;
        }
        surfaceHolder.removeCallback(this);
    }

    CameraManager getCameraManager() {
        return this.mCameraManager;
    }

    @Override // com.mylhyl.zxing.scanner.ScannerViewHandler.HandleDecodeListener
    public void restartPreview() {
        this.mScannerView.drawViewfinder();
    }

    @Override // com.mylhyl.zxing.scanner.ScannerViewHandler.HandleDecodeListener
    public void decodeSucceeded(Result result, Bitmap bitmap, float f) {
        this.mScannerView.handleDecode(result, bitmap, f);
    }

    @Override // android.view.SurfaceView, android.view.View
    public void onMeasure(int i, int i2) {
        boolean zIsPortrait;
        int defaultSize = getDefaultSize(getSuggestedMinimumWidth(), i);
        int defaultSize2 = getDefaultSize(getSuggestedMinimumHeight(), i2);
        CameraManager cameraManager = this.mCameraManager;
        if (cameraManager != null) {
            zIsPortrait = cameraManager.isPortrait();
            if (zIsPortrait && this.mCameraManager.getCameraResolution() != null) {
                Point cameraResolution = this.mCameraManager.getCameraResolution();
                float f = defaultSize;
                float f2 = defaultSize2;
                float f3 = (f * 1.0f) / f2;
                float f4 = cameraResolution.y;
                float f5 = cameraResolution.x;
                float f6 = (f4 * 1.0f) / f5;
                if (f3 < f6) {
                    defaultSize = (int) ((f2 / ((f5 * 1.0f) / f4)) + 0.5f);
                } else {
                    defaultSize2 = (int) ((f / f6) + 0.5f);
                }
            }
        } else {
            zIsPortrait = true;
        }
        if (zIsPortrait) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(defaultSize, 1073741824), View.MeasureSpec.makeMeasureSpec(defaultSize2, 1073741824));
        } else {
            super.onMeasure(i, i2);
        }
    }
}
