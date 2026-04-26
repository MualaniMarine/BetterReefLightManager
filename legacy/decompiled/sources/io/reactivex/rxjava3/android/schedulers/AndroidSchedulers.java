package io.reactivex.rxjava3.android.schedulers;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class AndroidSchedulers {
    private static final Scheduler MAIN_THREAD = RxAndroidPlugins.initMainThreadScheduler(new Callable() { // from class: io.reactivex.rxjava3.android.schedulers.-$$Lambda$AndroidSchedulers$2crO82bOkOgbwToxlZgj1sEtdZQ
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return AndroidSchedulers.MainHolder.DEFAULT;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    static final class MainHolder {
        static final Scheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()), true);

        private MainHolder() {
        }
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(MAIN_THREAD);
    }

    public static Scheduler from(Looper looper) {
        return from(looper, true);
    }

    public static Scheduler from(Looper looper, boolean z) {
        if (looper == null) {
            throw new NullPointerException("looper == null");
        }
        if (Build.VERSION.SDK_INT < 16) {
            z = false;
        } else if (z && Build.VERSION.SDK_INT < 22) {
            Message messageObtain = Message.obtain();
            try {
                messageObtain.setAsynchronous(true);
            } catch (NoSuchMethodError unused) {
                z = false;
            }
            messageObtain.recycle();
        }
        return new HandlerScheduler(new Handler(looper), z);
    }

    private AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }
}
