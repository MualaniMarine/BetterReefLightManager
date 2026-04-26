package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.aa */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0617aa implements Runnable {

    /* JADX INFO: renamed from: a */
    private final Handler f512a;

    /* JADX INFO: renamed from: b */
    private final String f513b;

    /* JADX INFO: renamed from: c */
    private long f514c;

    /* JADX INFO: renamed from: d */
    private final long f515d;

    /* JADX INFO: renamed from: e */
    private boolean f516e = true;

    /* JADX INFO: renamed from: f */
    private long f517f;

    RunnableC0617aa(Handler handler, String str, long j) {
        this.f512a = handler;
        this.f513b = str;
        this.f514c = j;
        this.f515d = j;
    }

    /* JADX INFO: renamed from: a */
    public final void m293a() {
        if (this.f516e) {
            this.f516e = false;
            this.f517f = SystemClock.uptimeMillis();
            this.f512a.post(this);
        }
    }

    /* JADX INFO: renamed from: b */
    public final boolean m295b() {
        return !this.f516e && SystemClock.uptimeMillis() > this.f517f + this.f514c;
    }

    /* JADX INFO: renamed from: c */
    public final int m296c() {
        if (this.f516e) {
            return 0;
        }
        return SystemClock.uptimeMillis() - this.f517f < this.f514c ? 1 : 3;
    }

    /* JADX INFO: renamed from: d */
    public final String m297d() {
        return this.f513b;
    }

    /* JADX INFO: renamed from: e */
    public final Looper m298e() {
        return this.f512a.getLooper();
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f516e = true;
        this.f514c = this.f515d;
    }

    /* JADX INFO: renamed from: a */
    public final void m294a(long j) {
        this.f514c = LongCompanionObject.MAX_VALUE;
    }
}
