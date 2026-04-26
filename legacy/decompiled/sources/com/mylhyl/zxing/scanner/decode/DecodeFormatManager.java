package com.mylhyl.zxing.scanner.decode;

import android.text.TextUtils;
import com.google.zxing.BarcodeFormat;
import com.mylhyl.zxing.scanner.common.Scanner;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class DecodeFormatManager {
    private static final Map<String, Set<BarcodeFormat>> FORMATS_FOR_MODE;
    private static final Set<BarcodeFormat> ONE_D_FORMATS;
    static final Set<BarcodeFormat> QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    static final Set<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    static final Set<BarcodeFormat> PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED);
    static final Set<BarcodeFormat> INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);

    static {
        EnumSet enumSetCopyOf = EnumSet.copyOf((Collection) PRODUCT_FORMATS);
        ONE_D_FORMATS = enumSetCopyOf;
        enumSetCopyOf.addAll(INDUSTRIAL_FORMATS);
        HashMap map = new HashMap();
        FORMATS_FOR_MODE = map;
        map.put(Scanner.ScanMode.ONE_D_MODE, ONE_D_FORMATS);
        FORMATS_FOR_MODE.put(Scanner.ScanMode.PRODUCT_MODE, PRODUCT_FORMATS);
        FORMATS_FOR_MODE.put(Scanner.ScanMode.QR_CODE_MODE, QR_CODE_FORMATS);
        FORMATS_FOR_MODE.put(Scanner.ScanMode.DATA_MATRIX_MODE, DATA_MATRIX_FORMATS);
    }

    private DecodeFormatManager() {
    }

    public static Set<BarcodeFormat> parseDecodeFormats(BarcodeFormat... barcodeFormatArr) {
        if (barcodeFormatArr == null) {
            return null;
        }
        EnumSet enumSetNoneOf = EnumSet.noneOf(BarcodeFormat.class);
        try {
            for (BarcodeFormat barcodeFormat : barcodeFormatArr) {
                enumSetNoneOf.add(barcodeFormat);
            }
            return enumSetNoneOf;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public static Set<BarcodeFormat> parseDecodeFormats(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return FORMATS_FOR_MODE.get(str);
    }
}
