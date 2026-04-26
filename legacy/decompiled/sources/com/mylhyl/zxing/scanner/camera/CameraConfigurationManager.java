package com.mylhyl.zxing.scanner.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.camera.open.CameraFacing;
import com.mylhyl.zxing.scanner.camera.open.OpenCamera;

/* JADX INFO: loaded from: classes.dex */
final class CameraConfigurationManager {
    private static final int FRONT_LIGHT_MODE_OFF = 1;
    private static final int FRONT_LIGHT_MODE_ON = 0;
    private static final String TAG = "CameraConfiguration";
    private Point bestPreviewSize;
    private Point cameraResolution;
    private final Context context;
    private int cwRotationFromDisplayToCamera;
    private ScannerOptions scannerOptions;
    private Point screenResolution;

    CameraConfigurationManager(Context context, ScannerOptions scannerOptions) {
        this.context = context;
        this.scannerOptions = scannerOptions;
    }

    void initFromCameraParameters(OpenCamera openCamera) {
        int i;
        Camera.Parameters parameters = openCamera.getCamera().getParameters();
        Display defaultDisplay = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        int rotation = defaultDisplay.getRotation();
        if (rotation == 0) {
            i = 0;
        } else if (rotation == 1) {
            i = 90;
        } else if (rotation == 2) {
            i = 180;
        } else if (rotation == 3) {
            i = 270;
        } else if (rotation % 90 == 0) {
            i = (rotation + 360) % 360;
        } else {
            throw new IllegalArgumentException("Bad rotation: " + rotation);
        }
        Log.i(TAG, "Display at: " + i);
        int orientation = openCamera.getOrientation();
        Log.i(TAG, "Camera at: " + orientation);
        if (openCamera.getFacing() == CameraFacing.FRONT) {
            orientation = (360 - orientation) % 360;
            Log.i(TAG, "Front camera overriden to: " + orientation);
        }
        this.cwRotationFromDisplayToCamera = ((orientation + 360) - i) % 360;
        Log.i(TAG, "Final display orientation: " + this.cwRotationFromDisplayToCamera);
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.screenResolution = point;
        Log.i(TAG, "Screen resolution in current orientation: " + this.screenResolution);
        Point point2 = new Point();
        point2.x = this.screenResolution.x;
        point2.y = this.screenResolution.y;
        if (this.screenResolution.x < this.screenResolution.y) {
            point2.x = this.screenResolution.y;
            point2.y = this.screenResolution.x;
        }
        this.cameraResolution = CameraConfigurationUtils.findBestPreviewSizeValue(parameters, point2);
        Log.i(TAG, "Camera resolution: " + this.cameraResolution);
        this.bestPreviewSize = CameraConfigurationUtils.findBestPreviewSizeValue(parameters, point2);
        Log.i(TAG, "Best available preview size: " + this.bestPreviewSize);
    }

    void setDesiredCameraParameters(OpenCamera openCamera, boolean z, boolean z2) {
        Camera camera = openCamera.getCamera();
        Camera.Parameters parameters = camera.getParameters();
        if (parameters == null) {
            Log.w(TAG, "Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        if (z) {
            Log.w(TAG, "In camera config safe mode -- most settings will not be honored");
        }
        initializeTorch(parameters, 1, z);
        CameraConfigurationUtils.setFocus(parameters, true, true, z);
        if (!z && z2) {
            CameraConfigurationUtils.setInvertColor(parameters);
        }
        parameters.setPreviewSize(this.bestPreviewSize.x, this.bestPreviewSize.y);
        if (this.scannerOptions.getCameraZoomRatio() > 0.0d) {
            CameraConfigurationUtils.setZoom(parameters, this.scannerOptions.getCameraZoomRatio());
        }
        camera.setParameters(parameters);
        camera.setDisplayOrientation(this.cwRotationFromDisplayToCamera);
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        if (previewSize != null) {
            if (this.bestPreviewSize.x == previewSize.width && this.bestPreviewSize.y == previewSize.height) {
                return;
            }
            this.bestPreviewSize.x = previewSize.width;
            this.bestPreviewSize.y = previewSize.height;
        }
    }

    Point getCameraResolution() {
        return this.cameraResolution;
    }

    Point getScreenResolution() {
        return this.screenResolution;
    }

    boolean getTorchState(Camera camera) {
        Camera.Parameters parameters;
        if (camera == null || (parameters = camera.getParameters()) == null) {
            return false;
        }
        String flashMode = parameters.getFlashMode();
        return "on".equals(flashMode) || "torch".equals(flashMode);
    }

    void setTorch(Camera camera, boolean z) {
        Camera.Parameters parameters = camera.getParameters();
        doSetTorch(parameters, z, false);
        camera.setParameters(parameters);
    }

    private void initializeTorch(Camera.Parameters parameters, int i, boolean z) {
        doSetTorch(parameters, i == 0, z);
    }

    private void doSetTorch(Camera.Parameters parameters, boolean z, boolean z2) {
        CameraConfigurationUtils.setTorch(parameters, z);
    }
}
