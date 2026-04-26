package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class BackpressureHelper {
    public static long addCap(long a, long b) {
        long j = a + b;
        return j < 0 ? LongCompanionObject.MAX_VALUE : j;
    }

    private BackpressureHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static long multiplyCap(long a, long b) {
        long j = a * b;
        return (((a | b) >>> 31) == 0 || j / a == b) ? j : LongCompanionObject.MAX_VALUE;
    }

    public static long add(AtomicLong requested, long n) {
        long j;
        do {
            j = requested.get();
            if (j == LongCompanionObject.MAX_VALUE) {
                return LongCompanionObject.MAX_VALUE;
            }
        } while (!requested.compareAndSet(j, addCap(j, n)));
        return j;
    }

    public static long addCancel(AtomicLong requested, long n) {
        long j;
        do {
            j = requested.get();
            if (j == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (j == LongCompanionObject.MAX_VALUE) {
                return LongCompanionObject.MAX_VALUE;
            }
        } while (!requested.compareAndSet(j, addCap(j, n)));
        return j;
    }

    public static long produced(AtomicLong requested, long n) {
        long j;
        long j2;
        do {
            j = requested.get();
            if (j == LongCompanionObject.MAX_VALUE) {
                return LongCompanionObject.MAX_VALUE;
            }
            j2 = j - n;
            if (j2 < 0) {
                RxJavaPlugins.onError(new IllegalStateException("More produced than requested: " + j2));
                j2 = 0L;
            }
        } while (!requested.compareAndSet(j, j2));
        return j2;
    }

    public static long producedCancel(AtomicLong requested, long n) {
        long j;
        long j2;
        do {
            j = requested.get();
            if (j == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (j == LongCompanionObject.MAX_VALUE) {
                return LongCompanionObject.MAX_VALUE;
            }
            j2 = j - n;
            if (j2 < 0) {
                RxJavaPlugins.onError(new IllegalStateException("More produced than requested: " + j2));
                j2 = 0L;
            }
        } while (!requested.compareAndSet(j, j2));
        return j2;
    }
}
