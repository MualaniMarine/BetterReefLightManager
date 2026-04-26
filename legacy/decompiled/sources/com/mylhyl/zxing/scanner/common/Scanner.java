package com.mylhyl.zxing.scanner.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

/* JADX INFO: loaded from: classes.dex */
public final class Scanner {
    public static final int DECODE = 5;
    public static final int DECODE_FAILED = 2;
    public static final int DECODE_SUCCEEDED = 1;
    public static final int LAUNCH_PRODUCT_QUERY = 4;
    public static final int QUIT = 6;
    public static final int RESTART_PREVIEW = 0;
    public static final int RETURN_SCAN_RESULT = 3;

    public static class Scan {
        public static final String ACTION = "com.mylhyl.zxing.scanner.client.android.SCAN";
        public static final String RESULT = "SCAN_RESULT";
    }

    public static class ScanMode {
        public static final String DATA_MATRIX_MODE = "DATA_MATRIX_MODE";
        public static final String ONE_D_MODE = "ONE_D_MODE";
        public static final String PRODUCT_MODE = "PRODUCT_MODE";
        public static final String QR_CODE_MODE = "QR_CODE";
    }

    public static class color {
        public static final int POSSIBLE_RESULT_POINTS = -1056981727;
        public static final int RESULT_POINTS = -1063662592;
        public static final int RESULT_VIEW = -1342177280;
        public static final int VIEWFINDER_LASER = -16711936;
        public static final int VIEWFINDER_MASK = 1610612736;
    }

    public static ParsedResult parseResult(Result result) {
        if (result == null) {
            return null;
        }
        return ResultParser.parseResult(result);
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }
}
