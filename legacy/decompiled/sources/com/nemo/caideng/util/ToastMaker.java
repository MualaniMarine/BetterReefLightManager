package com.nemo.caideng.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.nemo.caideng.BaseApplication;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public class ToastMaker {
    private static TextView sContentTv;
    private static Toast sToast;

    public static void showShortToast(String str) {
        showCustomToast(BaseApplication.getInstance(), str, 0);
    }

    public static void showShortToast(int i) {
        showCustomToast(BaseApplication.getInstance(), i, 0);
    }

    public static void showLongToast(String str) {
        showCustomToast(BaseApplication.getInstance(), str, 1);
    }

    public static void showLongToast(int i) {
        showCustomToast(BaseApplication.getInstance(), i, 1);
    }

    public static void showToastInUiThread(final Activity activity, final String str) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.nemo.caideng.util.ToastMaker.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastMaker.showCustomToast(activity, str);
                }
            });
        }
    }

    public static void showToastInUiThread(final Activity activity, final int i) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.nemo.caideng.util.ToastMaker.2
                @Override // java.lang.Runnable
                public void run() {
                    ToastMaker.showCustomToast(activity, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showCustomToast(Context context, int i) {
        showCustomToast(context, context.getResources().getString(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showCustomToast(Context context, String str) {
        showCustomToast(context, str, 0);
    }

    private static void showCustomToast(Context context, int i, int i2) {
        showCustomToast(context, context.getResources().getString(i), i2);
    }

    private static void showCustomToast(final Context context, final String str, final int i) {
        if (context == null) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(context, str, i);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.nemo.caideng.util.ToastMaker.3
                @Override // java.lang.Runnable
                public void run() {
                    ToastMaker.showToast(context, str, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showToast(Context context, String str, int i) {
        if (context != null) {
            try {
                if (sToast == null) {
                    sToast = new Toast(context);
                    View viewInflate = LayoutInflater.from(context).inflate(C0575R.layout.toast, (ViewGroup) null);
                    TextView textView = (TextView) viewInflate.findViewById(C0575R.id.tv_toast_content);
                    sContentTv = textView;
                    textView.setText(str);
                    sToast.setGravity(17, 0, BaseApplication.getInstance().screenH / 6);
                    sToast.setDuration(i);
                    sToast.setView(viewInflate);
                } else {
                    sContentTv.setText(str);
                }
                sToast.show();
            } catch (Exception unused) {
            }
        }
    }
}
