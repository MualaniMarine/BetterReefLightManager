package com.tencent.bugly.proguard;

import com.tencent.bugly.C0585b;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.w */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0656w {

    /* JADX INFO: renamed from: a */
    private static final AtomicInteger f749a = new AtomicInteger(1);

    /* JADX INFO: renamed from: b */
    private static C0656w f750b;

    /* JADX INFO: renamed from: c */
    private ScheduledExecutorService f751c;

    protected C0656w() {
        this.f751c = null;
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(3, new ThreadFactory(this) { // from class: com.tencent.bugly.proguard.w.1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("BuglyThread-" + C0656w.f749a.getAndIncrement());
                return thread;
            }
        });
        this.f751c = scheduledExecutorServiceNewScheduledThreadPool;
        if (scheduledExecutorServiceNewScheduledThreadPool == null || scheduledExecutorServiceNewScheduledThreadPool.isShutdown()) {
            C0657x.m467d("[AsyncTaskHandler] ScheduledExecutorService is not valiable!", new Object[0]);
        }
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0656w m453a() {
        if (f750b == null) {
            f750b = new C0656w();
        }
        return f750b;
    }

    /* JADX INFO: renamed from: a */
    public final synchronized boolean m456a(Runnable runnable, long j) {
        if (!m458c()) {
            C0657x.m467d("[AsyncTaskHandler] Async handler was closed, should not post task.", new Object[0]);
            return false;
        }
        if (runnable == null) {
            C0657x.m467d("[AsyncTaskHandler] Task input is null.", new Object[0]);
            return false;
        }
        if (j <= 0) {
            j = 0;
        }
        C0657x.m466c("[AsyncTaskHandler] Post a delay(time: %dms) task: %s", Long.valueOf(j), runnable.getClass().getName());
        try {
            this.f751c.schedule(runnable, j, TimeUnit.MILLISECONDS);
            return true;
        } catch (Throwable th) {
            if (C0585b.f145c) {
                th.printStackTrace();
            }
            return false;
        }
    }

    /* JADX INFO: renamed from: a */
    public final synchronized boolean m455a(Runnable runnable) {
        if (!m458c()) {
            C0657x.m467d("[AsyncTaskHandler] Async handler was closed, should not post task.", new Object[0]);
            return false;
        }
        if (runnable == null) {
            C0657x.m467d("[AsyncTaskHandler] Task input is null.", new Object[0]);
            return false;
        }
        C0657x.m466c("[AsyncTaskHandler] Post a normal task: %s", runnable.getClass().getName());
        try {
            this.f751c.execute(runnable);
            return true;
        } catch (Throwable th) {
            if (C0585b.f145c) {
                th.printStackTrace();
            }
            return false;
        }
    }

    /* JADX INFO: renamed from: b */
    public final synchronized void m457b() {
        if (this.f751c != null && !this.f751c.isShutdown()) {
            C0657x.m466c("[AsyncTaskHandler] Close async handler.", new Object[0]);
            this.f751c.shutdownNow();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0010  */
    /* JADX INFO: renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean m458c() {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.concurrent.ScheduledExecutorService r0 = r1.f751c     // Catch: java.lang.Throwable -> L12
            if (r0 == 0) goto L10
            java.util.concurrent.ScheduledExecutorService r0 = r1.f751c     // Catch: java.lang.Throwable -> L12
            boolean r0 = r0.isShutdown()     // Catch: java.lang.Throwable -> L12
            if (r0 != 0) goto L10
            r0 = 1
        Le:
            monitor-exit(r1)
            return r0
        L10:
            r0 = 0
            goto Le
        L12:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C0656w.m458c():boolean");
    }
}
