package com.mylhyl.zxing.scanner.camera;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class CameraConfigurationUtils {
    private static final int AREA_PER_1000 = 400;
    private static final double MAX_ASPECT_DISTORTION = 0.15d;
    private static final float MAX_EXPOSURE_COMPENSATION = 1.5f;
    private static final float MIN_EXPOSURE_COMPENSATION = 0.0f;
    private static final int MIN_PREVIEW_PIXELS = 153600;
    private static final String TAG = "CameraConfiguration";

    private CameraConfigurationUtils() {
    }

    public static void setFocus(Camera.Parameters parameters, boolean z, boolean z2, boolean z3) {
        String strFindSettableValue;
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (!z) {
            strFindSettableValue = null;
        } else if (z3 || z2) {
            strFindSettableValue = findSettableValue("focus mode", supportedFocusModes, "auto");
        } else {
            strFindSettableValue = findSettableValue("focus mode", supportedFocusModes, "continuous-picture", "continuous-video", "auto");
        }
        if (!z3 && strFindSettableValue == null) {
            strFindSettableValue = findSettableValue("focus mode", supportedFocusModes, "macro", "edof");
        }
        if (strFindSettableValue != null) {
            if (strFindSettableValue.equals(parameters.getFocusMode())) {
                Log.i(TAG, "Focus mode already set to " + strFindSettableValue);
                return;
            }
            parameters.setFocusMode(strFindSettableValue);
        }
    }

    public static void setTorch(Camera.Parameters parameters, boolean z) {
        String strFindSettableValue;
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (z) {
            strFindSettableValue = findSettableValue("flash mode", supportedFlashModes, "torch", "on");
        } else {
            strFindSettableValue = findSettableValue("flash mode", supportedFlashModes, "off");
        }
        if (strFindSettableValue != null) {
            if (strFindSettableValue.equals(parameters.getFlashMode())) {
                Log.i(TAG, "Flash mode already set to " + strFindSettableValue);
                return;
            }
            Log.i(TAG, "Setting flash mode to " + strFindSettableValue);
            parameters.setFlashMode(strFindSettableValue);
        }
    }

    public static void setBestExposure(Camera.Parameters parameters, boolean z) {
        int minExposureCompensation = parameters.getMinExposureCompensation();
        int maxExposureCompensation = parameters.getMaxExposureCompensation();
        float exposureCompensationStep = parameters.getExposureCompensationStep();
        if (minExposureCompensation != 0 || maxExposureCompensation != 0) {
            if (exposureCompensationStep > 0.0f) {
                int iRound = Math.round((z ? 0.0f : MAX_EXPOSURE_COMPENSATION) / exposureCompensationStep);
                float f = exposureCompensationStep * iRound;
                int iMax = Math.max(Math.min(iRound, maxExposureCompensation), minExposureCompensation);
                if (parameters.getExposureCompensation() == iMax) {
                    Log.i(TAG, "Exposure compensation already set to " + iMax + " / " + f);
                    return;
                }
                Log.i(TAG, "Setting exposure compensation to " + iMax + " / " + f);
                parameters.setExposureCompensation(iMax);
                return;
            }
        }
        Log.i(TAG, "Camera does not support exposure compensation");
    }

    public static void setFocusArea(Camera.Parameters parameters) {
        if (parameters.getMaxNumFocusAreas() > 0) {
            parameters.setFocusAreas(buildMiddleArea(AREA_PER_1000));
        } else {
            Log.i(TAG, "Device does not support focus areas");
        }
    }

    public static void setMetering(Camera.Parameters parameters) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            parameters.setMeteringAreas(buildMiddleArea(AREA_PER_1000));
        } else {
            Log.i(TAG, "Device does not support metering areas");
        }
    }

    private static List<Camera.Area> buildMiddleArea(int i) {
        int i2 = -i;
        return Collections.singletonList(new Camera.Area(new Rect(i2, i2, i, i), 1));
    }

    public static void setVideoStabilization(Camera.Parameters parameters) {
        if (parameters.isVideoStabilizationSupported()) {
            if (parameters.getVideoStabilization()) {
                Log.i(TAG, "Video stabilization already enabled");
                return;
            } else {
                Log.i(TAG, "Enabling video stabilization...");
                parameters.setVideoStabilization(true);
                return;
            }
        }
        Log.i(TAG, "This device does not support video stabilization");
    }

    public static void setBarcodeSceneMode(Camera.Parameters parameters) {
        if ("barcode".equals(parameters.getSceneMode())) {
            Log.i(TAG, "Barcode scene mode already set");
            return;
        }
        String strFindSettableValue = findSettableValue("scene mode", parameters.getSupportedSceneModes(), "barcode");
        if (strFindSettableValue != null) {
            parameters.setSceneMode(strFindSettableValue);
        }
    }

    public static void setZoom(Camera.Parameters parameters, double d) {
        if (parameters.isZoomSupported()) {
            Integer numIndexOfClosestZoom = indexOfClosestZoom(parameters, d);
            if (numIndexOfClosestZoom == null) {
                return;
            }
            if (parameters.getZoom() == numIndexOfClosestZoom.intValue()) {
                Log.i(TAG, "Zoom is already set to " + numIndexOfClosestZoom);
                return;
            }
            Log.i(TAG, "Setting zoom to " + numIndexOfClosestZoom);
            parameters.setZoom(numIndexOfClosestZoom.intValue());
            return;
        }
        Log.i(TAG, "Zoom is not supported");
    }

    private static Integer indexOfClosestZoom(Camera.Parameters parameters, double d) {
        List<Integer> zoomRatios = parameters.getZoomRatios();
        Log.i(TAG, "Zoom ratios: " + zoomRatios);
        int maxZoom = parameters.getMaxZoom();
        if (zoomRatios == null || zoomRatios.isEmpty() || zoomRatios.size() != maxZoom + 1) {
            Log.w(TAG, "Invalid zoom ratios!");
            return null;
        }
        double d2 = d * 100.0d;
        double d3 = Double.POSITIVE_INFINITY;
        int i = 0;
        for (int i2 = 0; i2 < zoomRatios.size(); i2++) {
            double dAbs = Math.abs(((double) zoomRatios.get(i2).intValue()) - d2);
            if (dAbs < d3) {
                i = i2;
                d3 = dAbs;
            }
        }
        Log.i(TAG, "Chose zoom ratio of " + (((double) zoomRatios.get(i).intValue()) / 100.0d));
        return Integer.valueOf(i);
    }

    public static void setInvertColor(Camera.Parameters parameters) {
        if ("negative".equals(parameters.getColorEffect())) {
            Log.i(TAG, "Negative effect already set");
            return;
        }
        String strFindSettableValue = findSettableValue("color effect", parameters.getSupportedColorEffects(), "negative");
        if (strFindSettableValue != null) {
            parameters.setColorEffect(strFindSettableValue);
        }
    }

    public static Point findBestPreviewSizeValue(Camera.Parameters parameters, Point point) {
        int i;
        List<Camera.Size> list;
        String str;
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        String str2 = "Parameters contained no preview size!";
        if (supportedPreviewSizes == null) {
            Log.w(TAG, "Device returned no supported preview sizes; using default");
            Camera.Size previewSize = parameters.getPreviewSize();
            if (previewSize == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            return new Point(previewSize.width, previewSize.height);
        }
        if (Log.isLoggable(TAG, 4)) {
            StringBuilder sb = new StringBuilder();
            for (Camera.Size size : supportedPreviewSizes) {
                sb.append(size.width);
                sb.append('x');
                sb.append(size.height);
                sb.append(' ');
            }
            Log.i(TAG, "Supported preview sizes: " + ((Object) sb));
        }
        double d = ((double) point.x) / ((double) point.y);
        Camera.Size size2 = null;
        int i2 = 0;
        for (Camera.Size size3 : supportedPreviewSizes) {
            int i3 = size3.width;
            int i4 = size3.height;
            int i5 = i3 * i4;
            if (i5 < MIN_PREVIEW_PIXELS) {
                list = supportedPreviewSizes;
                str = str2;
                i = i2;
            } else {
                boolean z = i3 < i4;
                int i6 = z ? i4 : i3;
                i = i2;
                int i7 = z ? i3 : i4;
                list = supportedPreviewSizes;
                str = str2;
                if (Math.abs((((double) i6) / ((double) i7)) - d) <= MAX_ASPECT_DISTORTION) {
                    if (i6 == point.x && i7 == point.y) {
                        Point point2 = new Point(i3, i4);
                        Log.i(TAG, "Found preview size exactly matching screen size: " + point2);
                        return point2;
                    }
                    if (i5 > i) {
                        size2 = size3;
                        i2 = i5;
                    } else {
                        i2 = i;
                    }
                }
                str2 = str;
                supportedPreviewSizes = list;
            }
            i2 = i;
            str2 = str;
            supportedPreviewSizes = list;
        }
        List<Camera.Size> list2 = supportedPreviewSizes;
        String str3 = str2;
        if (size2 != null) {
            Camera.Size sizeFindCloselySize = findCloselySize(size2.width, size2.height, list2);
            Point point3 = new Point(sizeFindCloselySize.width, sizeFindCloselySize.height);
            Log.i(TAG, "Using largest suitable preview size: " + point3);
            return point3;
        }
        Camera.Size previewSize2 = parameters.getPreviewSize();
        if (previewSize2 == null) {
            throw new IllegalStateException(str3);
        }
        Point point4 = new Point(previewSize2.width, previewSize2.height);
        Log.i(TAG, "No suitable preview sizes, using default: " + point4);
        return point4;
    }

    private static String findSettableValue(String str, Collection<String> collection, String... strArr) {
        Log.i(TAG, "Requesting " + str + " value from among: " + Arrays.toString(strArr));
        Log.i(TAG, "Supported " + str + " values: " + collection);
        if (collection != null) {
            for (String str2 : strArr) {
                if (collection.contains(str2)) {
                    Log.i(TAG, "Can set " + str + " to: " + str2);
                    return str2;
                }
            }
        }
        Log.i(TAG, "No supported values match");
        return null;
    }

    protected static Camera.Size findCloselySize(int i, int i2, List<Camera.Size> list) {
        Collections.sort(list, new SizeComparator(i, i2));
        return list.get(0);
    }
}
