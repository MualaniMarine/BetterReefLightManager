package com.mylhyl.zxing.scanner.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.camera.CameraManager;
import com.mylhyl.zxing.scanner.common.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class QRDecode {
    public static final Map<DecodeHintType, Object> HINTS = new EnumMap(DecodeHintType.class);

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BarcodeFormat.QR_CODE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, arrayList);
        HINTS.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }

    private QRDecode() {
    }

    public static void decodeQR(String str, OnScannerCompletionListener onScannerCompletionListener) {
        try {
            decodeQR(loadBitmap(str), onScannerCompletionListener);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void decodeQR(Bitmap bitmap, OnScannerCompletionListener onScannerCompletionListener) {
        Result resultDecode;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[width * height];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new GlobalHistogramBinarizer(new RGBLuminanceSource(width, height, iArr)));
            try {
                resultDecode = new QRCodeReader().decode(binaryBitmap, HINTS);
            } catch (ChecksumException e) {
                e.printStackTrace();
                resultDecode = null;
            } catch (FormatException e2) {
                e2.printStackTrace();
                resultDecode = null;
            } catch (NotFoundException e3) {
                e3.printStackTrace();
                resultDecode = null;
            }
        } else {
            resultDecode = null;
        }
        if (onScannerCompletionListener != null) {
            onScannerCompletionListener.onScannerCompletion(resultDecode, Scanner.parseResult(resultDecode), bitmap);
        }
    }

    private static Bitmap loadBitmap(String str) throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i = options.outWidth;
        int i2 = options.outHeight;
        options.inSampleSize = 1;
        if (i > i2) {
            if (i > 1200) {
                options.inSampleSize = i / CameraManager.MAX_FRAME_WIDTH;
            }
        } else if (i2 > 675) {
            options.inSampleSize = i2 / CameraManager.MAX_FRAME_HEIGHT;
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        if (bitmapDecodeFile != null) {
            return bitmapDecodeFile;
        }
        throw new FileNotFoundException("Couldn't open " + str);
    }
}
