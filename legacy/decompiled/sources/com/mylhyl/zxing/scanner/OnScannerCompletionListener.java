package com.mylhyl.zxing.scanner;

import android.graphics.Bitmap;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;

/* JADX INFO: loaded from: classes.dex */
public interface OnScannerCompletionListener {
    void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap);
}
