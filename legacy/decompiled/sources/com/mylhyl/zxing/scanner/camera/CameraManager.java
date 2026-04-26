package com.mylhyl.zxing.scanner.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.camera.open.CameraFacing;
import com.mylhyl.zxing.scanner.camera.open.OpenCamera;
import com.mylhyl.zxing.scanner.camera.open.OpenCameraInterface;
import com.mylhyl.zxing.scanner.common.Scanner;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class CameraManager {
    public static final int MAX_FRAME_HEIGHT = 675;
    public static final int MAX_FRAME_WIDTH = 1200;
    private static final int MIN_FRAME_HEIGHT = 240;
    private static final int MIN_FRAME_WIDTH = 240;
    private static final String TAG = CameraManager.class.getSimpleName();
    private AutoFocusManager autoFocusManager;
    private OpenCamera camera;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private int laserFrameTopMargin;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private int requestedFramingRectHeight;
    private int requestedFramingRectWidth;
    private ScannerOptions scannerOptions;
    private int requestedCameraId = -1;
    private final int statusBarHeight = getStatusBarHeight();

    public CameraManager(Context context, ScannerOptions scannerOptions) {
        this.context = context;
        this.configManager = new CameraConfigurationManager(context, scannerOptions);
        this.previewCallback = new PreviewCallback(this.configManager);
        this.scannerOptions = scannerOptions;
        this.requestedFramingRectWidth = dp2px(scannerOptions.getFrameWidth());
        this.requestedFramingRectHeight = dp2px(scannerOptions.getFrameHeight());
        this.laserFrameTopMargin = dp2px(scannerOptions.getFrameTopMargin());
        setManualCameraId(scannerOptions.getCameraFacing() == CameraFacing.BACK ? 0 : 1);
    }

    public synchronized void openDriver(SurfaceHolder surfaceHolder) throws IOException {
        OpenCamera openCameraOpen = this.camera;
        if (openCameraOpen == null) {
            openCameraOpen = OpenCameraInterface.open(this.requestedCameraId);
            if (openCameraOpen == null) {
                throw new IOException("Camera.open() failed to return object from driver");
            }
            this.camera = openCameraOpen;
        }
        if (!this.initialized) {
            this.initialized = true;
            this.configManager.initFromCameraParameters(openCameraOpen);
            if (this.requestedFramingRectWidth > 0 && this.requestedFramingRectHeight > 0) {
                setManualFramingRect(this.requestedFramingRectWidth, this.requestedFramingRectHeight);
                this.requestedFramingRectWidth = 0;
                this.requestedFramingRectHeight = 0;
            }
        }
        Camera camera = openCameraOpen.getCamera();
        Camera.Parameters parameters = camera.getParameters();
        String strFlatten = parameters == null ? null : parameters.flatten();
        try {
            this.configManager.setDesiredCameraParameters(openCameraOpen, false, this.scannerOptions.isScanInvert());
        } catch (RuntimeException unused) {
            Log.w(TAG, "Camera rejected parameters. Setting only minimal safe-mode parameters");
            Log.i(TAG, "Resetting to saved camera params: " + strFlatten);
            if (strFlatten != null) {
                Camera.Parameters parameters2 = camera.getParameters();
                parameters2.unflatten(strFlatten);
                try {
                    camera.setParameters(parameters2);
                    this.configManager.setDesiredCameraParameters(openCameraOpen, true, this.scannerOptions.isScanInvert());
                } catch (RuntimeException unused2) {
                    Log.w(TAG, "Camera rejected even safe-mode parameters! No configuration");
                }
            }
        }
        camera.setPreviewDisplay(surfaceHolder);
    }

    public synchronized boolean isOpen() {
        return this.camera != null;
    }

    public synchronized void closeDriver() {
        if (this.camera != null) {
            this.camera.getCamera().release();
            this.camera = null;
            this.framingRect = null;
            this.framingRectInPreview = null;
        }
    }

    public synchronized void startPreview() {
        OpenCamera openCamera = this.camera;
        if (openCamera != null && !this.previewing) {
            openCamera.getCamera().startPreview();
            this.previewing = true;
            this.autoFocusManager = new AutoFocusManager(openCamera.getCamera());
        }
    }

    public synchronized void stopPreview() {
        if (this.autoFocusManager != null) {
            this.autoFocusManager.stop();
            this.autoFocusManager = null;
        }
        if (this.camera != null && this.previewing) {
            this.camera.getCamera().stopPreview();
            this.previewCallback.setHandler(null, 0);
            this.previewing = false;
        }
    }

    public synchronized void setTorch(boolean z) {
        OpenCamera openCamera = this.camera;
        if (openCamera != null && z != this.configManager.getTorchState(openCamera.getCamera())) {
            boolean z2 = this.autoFocusManager != null;
            if (z2) {
                this.autoFocusManager.stop();
                this.autoFocusManager = null;
            }
            this.configManager.setTorch(openCamera.getCamera(), z);
            if (z2) {
                AutoFocusManager autoFocusManager = new AutoFocusManager(openCamera.getCamera());
                this.autoFocusManager = autoFocusManager;
                autoFocusManager.start();
            }
        }
    }

    public synchronized void requestPreviewFrame(Handler handler, int i) {
        OpenCamera openCamera = this.camera;
        if (openCamera != null && this.previewing) {
            this.previewCallback.setHandler(handler, i);
            openCamera.getCamera().setOneShotPreviewCallback(this.previewCallback);
        }
    }

    public synchronized Rect getFramingRect() {
        if (this.framingRect == null) {
            if (this.camera == null) {
                return null;
            }
            Point screenResolution = this.configManager.getScreenResolution();
            if (screenResolution == null) {
                return null;
            }
            int iFindDesiredDimensionInRange = findDesiredDimensionInRange(screenResolution.x, 240, MAX_FRAME_WIDTH);
            createFramingRect(iFindDesiredDimensionInRange, isPortrait() ? iFindDesiredDimensionInRange : findDesiredDimensionInRange(screenResolution.y, 240, MAX_FRAME_HEIGHT), screenResolution);
        }
        return this.framingRect;
    }

    private static int findDesiredDimensionInRange(int i, int i2, int i3) {
        int i4 = (i * 5) / 8;
        return i4 < i2 ? i2 : i4 > i3 ? i3 : i4;
    }

    public synchronized Rect getFramingRectInPreview() {
        if (this.framingRectInPreview == null) {
            Rect framingRect = getFramingRect();
            if (framingRect == null) {
                return null;
            }
            Rect rect = new Rect(framingRect);
            Point cameraResolution = this.configManager.getCameraResolution();
            Point screenResolution = this.configManager.getScreenResolution();
            if (cameraResolution != null && screenResolution != null) {
                if (isPortrait()) {
                    rect.left = (rect.left * cameraResolution.y) / screenResolution.x;
                    rect.right = (rect.right * cameraResolution.y) / screenResolution.x;
                    rect.top = (rect.top * cameraResolution.x) / screenResolution.y;
                    rect.bottom = (rect.bottom * cameraResolution.x) / screenResolution.y;
                } else {
                    rect.left = (rect.left * cameraResolution.x) / screenResolution.x;
                    rect.right = (rect.right * cameraResolution.x) / screenResolution.x;
                    rect.top = (rect.top * cameraResolution.y) / screenResolution.y;
                    rect.bottom = (rect.bottom * cameraResolution.y) / screenResolution.y;
                }
                this.framingRectInPreview = rect;
            }
            return null;
        }
        Log.d(TAG, "framing Rect In Preview rect: " + this.framingRectInPreview);
        return this.framingRectInPreview;
    }

    public synchronized void setManualCameraId(int i) {
        this.requestedCameraId = i;
    }

    public synchronized void setManualFramingRect(int i, int i2) {
        if (this.initialized) {
            Point screenResolution = this.configManager.getScreenResolution();
            if (i > screenResolution.x) {
                i = screenResolution.x;
            }
            if (i2 > screenResolution.y) {
                i2 = screenResolution.y;
            }
            createFramingRect(i, i2, screenResolution);
            this.framingRectInPreview = null;
        } else {
            this.requestedFramingRectWidth = i;
            this.requestedFramingRectHeight = i2;
        }
    }

    private void createFramingRect(int i, int i2, Point point) {
        int i3;
        int i4 = (point.x - i) / 2;
        int i5 = (point.y - i2) / 2;
        int i6 = this.laserFrameTopMargin;
        if (i6 == 0) {
            i3 = i5 - this.statusBarHeight;
        } else {
            i3 = this.statusBarHeight + i6;
        }
        this.framingRect = new Rect(i4, i3, i + i4, i2 + i3);
        Log.d(TAG, "Calculated framing rect: " + this.framingRect);
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] bArr, int i, int i2) {
        if (this.scannerOptions.isScanFullScreen()) {
            return new PlanarYUVLuminanceSource(bArr, i, i2, 0, 0, i, i2, false);
        }
        Rect framingRectInPreview = getFramingRectInPreview();
        if (framingRectInPreview == null) {
            return null;
        }
        return new PlanarYUVLuminanceSource(bArr, i, i2, framingRectInPreview.left, framingRectInPreview.top, framingRectInPreview.width(), framingRectInPreview.height(), false);
    }

    private int getStatusBarHeight() {
        int identifier = this.context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return this.context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private int dp2px(int i) {
        return Scanner.dp2px(this.context, i);
    }

    public boolean isPortrait() {
        return this.context.getResources().getConfiguration().orientation == 1;
    }

    public Point getScreenResolution() {
        return this.configManager.getScreenResolution();
    }

    public Point getCameraResolution() {
        return this.configManager.getCameraResolution();
    }
}
