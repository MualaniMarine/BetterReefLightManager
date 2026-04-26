package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.ab */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0618ab extends Thread {

    /* JADX INFO: renamed from: a */
    private boolean f518a = false;

    /* JADX INFO: renamed from: b */
    private boolean f519b = false;

    /* JADX INFO: renamed from: c */
    private List<RunnableC0617aa> f520c = new ArrayList();

    /* JADX INFO: renamed from: d */
    private List<InterfaceC0619ac> f521d = new ArrayList();

    /* JADX INFO: renamed from: e */
    private ArrayList<RunnableC0617aa> f522e = new ArrayList<>();

    /* JADX INFO: renamed from: a */
    public final void m301a() {
        m299a(new Handler(Looper.getMainLooper()), 5000L);
    }

    /* JADX INFO: renamed from: b */
    public final void m304b() {
        for (int i = 0; i < this.f520c.size(); i++) {
            try {
                if (this.f520c.get(i).m297d().equals(Looper.getMainLooper().getThread().getName())) {
                    C0657x.m466c("remove handler::%s", this.f520c.get(i));
                    this.f520c.remove(i);
                }
            } catch (Exception e) {
                C0657x.m465b(e);
                return;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private void m299a(Handler handler, long j) {
        if (handler == null) {
            C0657x.m468e("addThread handler should not be null", new Object[0]);
            return;
        }
        String name = handler.getLooper().getThread().getName();
        for (int i = 0; i < this.f520c.size(); i++) {
            try {
                if (this.f520c.get(i).m297d().equals(handler.getLooper().getThread().getName())) {
                    C0657x.m468e("addThread fail ,this thread has been added in monitor queue", new Object[0]);
                    return;
                }
            } catch (Exception e) {
                C0657x.m465b(e);
            }
            this.f520c.add(new RunnableC0617aa(handler, name, 5000L));
        }
        this.f520c.add(new RunnableC0617aa(handler, name, 5000L));
    }

    /* JADX INFO: renamed from: c */
    public final boolean m306c() {
        this.f518a = true;
        if (!isAlive()) {
            return false;
        }
        try {
            interrupt();
        } catch (Exception e) {
            C0657x.m465b(e);
        }
        return true;
    }

    /* JADX INFO: renamed from: d */
    public final boolean m307d() {
        if (isAlive()) {
            return false;
        }
        try {
            start();
            return true;
        } catch (Exception e) {
            C0657x.m465b(e);
            return false;
        }
    }

    /* JADX INFO: renamed from: e */
    private int m300e() {
        int iMax = 0;
        for (int i = 0; i < this.f520c.size(); i++) {
            try {
                iMax = Math.max(iMax, this.f520c.get(i).m296c());
            } catch (Exception e) {
                C0657x.m465b(e);
            }
        }
        return iMax;
    }

    /* JADX INFO: renamed from: a */
    public final void m302a(InterfaceC0619ac interfaceC0619ac) {
        if (this.f521d.contains(interfaceC0619ac)) {
            C0657x.m466c("addThreadMonitorListeners fail ,this threadMonitorListener has been added in monitor queue", new Object[0]);
        } else {
            this.f521d.add(interfaceC0619ac);
        }
    }

    /* JADX INFO: renamed from: b */
    public final void m305b(InterfaceC0619ac interfaceC0619ac) {
        this.f521d.remove(interfaceC0619ac);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        while (!this.f518a) {
            for (int i = 0; i < this.f520c.size(); i++) {
                try {
                    this.f520c.get(i).m293a();
                } catch (Exception e) {
                    C0657x.m465b(e);
                } catch (OutOfMemoryError e2) {
                    C0657x.m465b(e2);
                }
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            for (long jUptimeMillis2 = 2000; jUptimeMillis2 > 0 && !isInterrupted(); jUptimeMillis2 = 2000 - (SystemClock.uptimeMillis() - jUptimeMillis)) {
                sleep(jUptimeMillis2);
            }
            int iM300e = m300e();
            if (iM300e != 0 && iM300e != 1) {
                this.f522e.clear();
                for (int i2 = 0; i2 < this.f520c.size(); i2++) {
                    RunnableC0617aa runnableC0617aa = this.f520c.get(i2);
                    if (runnableC0617aa.m295b()) {
                        this.f522e.add(runnableC0617aa);
                        runnableC0617aa.m294a(LongCompanionObject.MAX_VALUE);
                    }
                }
                NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
                if (nativeCrashHandler != null && nativeCrashHandler.isEnableCatchAnrTrace()) {
                    nativeCrashHandler.dumpAnrNativeStack();
                    C0657x.m466c("jni mannual dump anr trace", new Object[0]);
                } else {
                    C0657x.m466c("do not enable jni mannual dump anr trace", new Object[0]);
                }
                int i3 = 0;
                while (true) {
                    if (this.f519b) {
                        break;
                    }
                    C0657x.m466c("do not enable anr continue check", new Object[0]);
                    sleep(2000L);
                    i3++;
                    if (i3 == 15) {
                        this.f522e.clear();
                        break;
                    }
                }
                for (int i4 = 0; i4 < this.f522e.size(); i4++) {
                    RunnableC0617aa runnableC0617aa2 = this.f522e.get(i4);
                    for (int i5 = 0; i5 < this.f521d.size(); i5++) {
                        C0657x.m468e("main thread blocked,now begin to upload anr stack", new Object[0]);
                        this.f521d.get(i5).mo180a(runnableC0617aa2);
                        this.f519b = false;
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m303a(boolean z) {
        this.f519b = true;
    }
}
