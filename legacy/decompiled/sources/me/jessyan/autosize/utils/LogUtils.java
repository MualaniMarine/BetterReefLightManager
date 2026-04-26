package me.jessyan.autosize.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class LogUtils {
    private static final String TAG = "AndroidAutoSize";
    private static boolean debug;

    private LogUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean z) {
        debug = z;
    }

    /* JADX INFO: renamed from: d */
    public static void m555d(String str) {
        if (debug) {
            Log.d(TAG, str);
        }
    }

    /* JADX INFO: renamed from: w */
    public static void m557w(String str) {
        if (debug) {
            Log.w(TAG, str);
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m556e(String str) {
        if (debug) {
            Log.e(TAG, str);
        }
    }
}
