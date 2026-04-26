package com.mylhyl.zxing.scanner.camera.open;

import android.hardware.Camera;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class OpenCameraInterface {
    public static final int NO_REQUESTED_CAMERA = -1;
    private static final String TAG = OpenCameraInterface.class.getName();

    private OpenCameraInterface() {
    }

    public static OpenCamera open(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras == 0) {
            Log.w(TAG, "No cameras!");
            return null;
        }
        if (i >= numberOfCameras) {
            Log.w(TAG, "Requested camera does not exist: " + i);
            return null;
        }
        if (i <= -1) {
            i = 0;
            while (i < numberOfCameras) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                if (CameraFacing.values()[cameraInfo.facing] == CameraFacing.BACK) {
                    break;
                }
                i++;
            }
            if (i == numberOfCameras) {
                Log.i(TAG, "No camera facing " + CameraFacing.BACK + "; returning camera #0");
                i = 0;
            }
        }
        Log.i(TAG, "Opening camera #" + i);
        Camera.CameraInfo cameraInfo2 = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo2);
        Camera cameraOpen = Camera.open(i);
        if (cameraOpen == null) {
            return null;
        }
        return new OpenCamera(i, cameraOpen, CameraFacing.values()[cameraInfo2.facing], cameraInfo2.orientation);
    }
}
