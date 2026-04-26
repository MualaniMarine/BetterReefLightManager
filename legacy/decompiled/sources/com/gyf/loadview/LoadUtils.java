package com.gyf.loadview;

import android.content.Context;
import android.util.TypedValue;

/* JADX INFO: loaded from: classes.dex */
class LoadUtils {
    LoadUtils() {
    }

    public static int getColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(C0539R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public static int getDarkColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(C0539R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    public static int getDarkColorAccent(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(C0539R.attr.colorAccent, typedValue, true);
        return typedValue.data;
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
