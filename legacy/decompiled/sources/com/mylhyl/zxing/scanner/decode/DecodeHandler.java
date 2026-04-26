package com.mylhyl.zxing.scanner.decode;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.mylhyl.zxing.scanner.camera.CameraManager;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class DecodeHandler extends Handler {
    private boolean bundleThumbnail;
    private final CameraManager cameraManager;
    private final MultiFormatReader multiFormatReader;
    private boolean running = true;
    private final Handler scannerViewHandler;

    DecodeHandler(CameraManager cameraManager, Handler handler, Map<DecodeHintType, Object> map, boolean z) {
        this.cameraManager = cameraManager;
        this.scannerViewHandler = handler;
        this.bundleThumbnail = z;
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        this.multiFormatReader = multiFormatReader;
        multiFormatReader.setHints(map);
    }

    private static void bundleThumbnail(PlanarYUVLuminanceSource planarYUVLuminanceSource, Bundle bundle) {
        int[] iArrRenderThumbnail = planarYUVLuminanceSource.renderThumbnail();
        int thumbnailWidth = planarYUVLuminanceSource.getThumbnailWidth();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iArrRenderThumbnail, 0, thumbnailWidth, thumbnailWidth, planarYUVLuminanceSource.getThumbnailHeight(), Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        bundle.putByteArray(DecodeThread.BARCODE_BITMAP, byteArrayOutputStream.toByteArray());
        bundle.putFloat(DecodeThread.BARCODE_SCALED_FACTOR, thumbnailWidth / planarYUVLuminanceSource.getWidth());
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message == null || !this.running) {
            return;
        }
        int i = message.what;
        if (i == 5) {
            decode((byte[]) message.obj, message.arg1, message.arg2);
        } else {
            if (i != 6) {
                return;
            }
            this.running = false;
            Looper.myLooper().quit();
        }
    }

    private void decode(byte[] bArr, int i, int i2) {
        if (this.cameraManager.isPortrait()) {
            byte[] bArr2 = new byte[bArr.length];
            for (int i3 = 0; i3 < i2; i3++) {
                for (int i4 = 0; i4 < i; i4++) {
                    bArr2[(((i4 * i2) + i2) - i3) - 1] = bArr[(i3 * i) + i4];
                }
            }
            bArr = bArr2;
            i2 = i;
            i = i2;
        }
        Result result = null;
        PlanarYUVLuminanceSource planarYUVLuminanceSourceBuildLuminanceSource = this.cameraManager.buildLuminanceSource(bArr, i, i2);
        if (planarYUVLuminanceSourceBuildLuminanceSource != null) {
            Result resultDecodeWithState = decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(planarYUVLuminanceSourceBuildLuminanceSource)));
            if (resultDecodeWithState == null) {
                resultDecodeWithState = decodeWithState(new BinaryBitmap(new HybridBinarizer(planarYUVLuminanceSourceBuildLuminanceSource)));
            }
            if (resultDecodeWithState == null) {
                resultDecodeWithState = decodeWithState(new BinaryBitmap(new HybridBinarizer(new InvertedLuminanceSource(planarYUVLuminanceSourceBuildLuminanceSource))));
            }
            result = resultDecodeWithState;
            this.multiFormatReader.reset();
        }
        Handler handler = this.scannerViewHandler;
        if (result == null) {
            if (handler != null) {
                Message.obtain(handler, 2).sendToTarget();
                return;
            }
            return;
        }
        ResultPoint[] resultPoints = result.getResultPoints();
        PointF[] pointFArr = new PointF[resultPoints.length];
        int i5 = 0;
        for (ResultPoint resultPoint : resultPoints) {
            pointFArr[i5] = new PointF(resultPoint.getX(), resultPoint.getY());
            i5++;
        }
        if (handler != null) {
            Message messageObtain = Message.obtain(handler, 1, result);
            Bundle bundle = new Bundle();
            if (this.bundleThumbnail) {
                bundleThumbnail(planarYUVLuminanceSourceBuildLuminanceSource, bundle);
            }
            messageObtain.setData(bundle);
            messageObtain.sendToTarget();
        }
    }

    private Result decodeWithState(BinaryBitmap binaryBitmap) {
        try {
            return this.multiFormatReader.decodeWithState(binaryBitmap);
        } catch (NotFoundException unused) {
            return null;
        }
    }
}
