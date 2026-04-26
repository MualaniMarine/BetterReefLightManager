package com.mylhyl.zxing.scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.zxing.Result;
import com.mylhyl.zxing.scanner.camera.CameraManager;
import com.mylhyl.zxing.scanner.decode.DecodeThread;

/* JADX INFO: loaded from: classes.dex */
final class ScannerViewHandler extends Handler {
    private final CameraManager cameraManager;
    private final DecodeThread decodeThread;
    private HandleDecodeListener handleDecodeListener;
    private State state;

    public interface HandleDecodeListener {
        void decodeSucceeded(Result result, Bitmap bitmap, float f);

        void restartPreview();
    }

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    ScannerViewHandler(ScannerOptions scannerOptions, CameraManager cameraManager, HandleDecodeListener handleDecodeListener) {
        this.cameraManager = cameraManager;
        this.handleDecodeListener = handleDecodeListener;
        DecodeThread decodeThread = new DecodeThread(cameraManager, this, scannerOptions.getDecodeFormats(), scannerOptions.getCharacterSet(), scannerOptions.isCreateQrThumbnail());
        this.decodeThread = decodeThread;
        decodeThread.start();
        this.state = State.SUCCESS;
        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            restartPreviewAndDecode();
            return;
        }
        if (i != 1) {
            if (i != 2) {
                return;
            }
            this.state = State.PREVIEW;
            this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), 5);
            return;
        }
        this.state = State.SUCCESS;
        Bundle data = message.getData();
        float f = 1.0f;
        Bitmap bitmapCopy = null;
        if (data != null) {
            byte[] byteArray = data.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (byteArray != null && byteArray.length > 0) {
                bitmapCopy = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, null).copy(Bitmap.Config.ARGB_8888, true);
            }
            f = data.getFloat(DecodeThread.BARCODE_SCALED_FACTOR);
        }
        HandleDecodeListener handleDecodeListener = this.handleDecodeListener;
        if (handleDecodeListener != null) {
            handleDecodeListener.decodeSucceeded((Result) message.obj, bitmapCopy, f);
        }
    }

    public void quitSynchronously() {
        this.state = State.DONE;
        this.cameraManager.stopPreview();
        Message.obtain(this.decodeThread.getHandler(), 6).sendToTarget();
        try {
            this.decodeThread.join(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        removeMessages(1);
        removeMessages(2);
    }

    private void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), 5);
            HandleDecodeListener handleDecodeListener = this.handleDecodeListener;
            if (handleDecodeListener != null) {
                handleDecodeListener.restartPreview();
            }
        }
    }
}
