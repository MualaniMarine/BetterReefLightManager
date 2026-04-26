package com.mylhyl.zxing.scanner.decode;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.mylhyl.zxing.scanner.camera.CameraManager;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public final class DecodeThread extends Thread {
    public static final String BARCODE_BITMAP = "barcode_bitmap";
    public static final String BARCODE_SCALED_FACTOR = "barcode_scaled_factor";
    private boolean bundleThumbnail;
    private final CameraManager cameraManager;
    private DecodeHandler handler;
    private final CountDownLatch handlerInitLatch = new CountDownLatch(1);
    private final Map<DecodeHintType, Object> hints = new EnumMap(DecodeHintType.class);
    private final Handler scannerViewHandler;

    public DecodeThread(CameraManager cameraManager, Handler handler, Collection<BarcodeFormat> collection, String str, boolean z) {
        this.bundleThumbnail = false;
        this.cameraManager = cameraManager;
        this.scannerViewHandler = handler;
        this.bundleThumbnail = z;
        if (collection == null || collection.isEmpty()) {
            collection = EnumSet.noneOf(BarcodeFormat.class);
            collection.addAll(DecodeFormatManager.PRODUCT_FORMATS);
            collection.addAll(DecodeFormatManager.INDUSTRIAL_FORMATS);
            collection.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            collection.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        this.hints.put(DecodeHintType.POSSIBLE_FORMATS, collection);
        if (str != null) {
            this.hints.put(DecodeHintType.CHARACTER_SET, str);
        }
        Log.i("DecodeThread", "Hints: " + this.hints);
    }

    public Handler getHandler() {
        try {
            this.handlerInitLatch.await();
        } catch (InterruptedException unused) {
        }
        return this.handler;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Looper.prepare();
        this.handler = new DecodeHandler(this.cameraManager, this.scannerViewHandler, this.hints, this.bundleThumbnail);
        this.handlerInitLatch.countDown();
        Looper.loop();
    }
}
