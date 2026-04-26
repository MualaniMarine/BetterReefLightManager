package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import android.os.Process;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.C0594b;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0658y;
import com.tencent.bugly.proguard.C0659z;
import java.lang.Thread;
import java.util.HashMap;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.e */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0609e implements Thread.UncaughtExceptionHandler {

    /* JADX INFO: renamed from: h */
    private static String f457h;

    /* JADX INFO: renamed from: i */
    private static final Object f458i = new Object();

    /* JADX INFO: renamed from: a */
    private Context f459a;

    /* JADX INFO: renamed from: b */
    private C0606b f460b;

    /* JADX INFO: renamed from: c */
    private C0596a f461c;

    /* JADX INFO: renamed from: d */
    private C0593a f462d;

    /* JADX INFO: renamed from: e */
    private Thread.UncaughtExceptionHandler f463e;

    /* JADX INFO: renamed from: f */
    private Thread.UncaughtExceptionHandler f464f;

    /* JADX INFO: renamed from: g */
    private boolean f465g = false;

    /* JADX INFO: renamed from: j */
    private int f466j;

    public C0609e(Context context, C0606b c0606b, C0596a c0596a, C0593a c0593a) {
        this.f459a = context;
        this.f460b = c0606b;
        this.f461c = c0596a;
        this.f462d = c0593a;
    }

    /* JADX INFO: renamed from: a */
    public final synchronized void m245a() {
        if (this.f466j >= 10) {
            C0657x.m461a("java crash handler over %d, no need set.", 10);
            return;
        }
        this.f465g = true;
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            if (getClass().getName().equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                return;
            }
            if ("com.android.internal.os.RuntimeInit$UncaughtHandler".equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                C0657x.m461a("backup system java handler: %s", defaultUncaughtExceptionHandler.toString());
                this.f464f = defaultUncaughtExceptionHandler;
                this.f463e = defaultUncaughtExceptionHandler;
            } else {
                C0657x.m461a("backup java handler: %s", defaultUncaughtExceptionHandler.toString());
                this.f463e = defaultUncaughtExceptionHandler;
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.f466j++;
        C0657x.m461a("registered java monitor: %s", toString());
    }

    /* JADX INFO: renamed from: b */
    public final synchronized void m248b() {
        this.f465g = false;
        C0657x.m461a("close java monitor!", new Object[0]);
        if ("bugly".equals(Thread.getDefaultUncaughtExceptionHandler().getClass().getName())) {
            C0657x.m461a("Java monitor to unregister: %s", toString());
            Thread.setDefaultUncaughtExceptionHandler(this.f463e);
            this.f466j--;
        }
    }

    /* JADX INFO: renamed from: b */
    private CrashDetailBean m243b(Thread thread, Throwable th, boolean z, String str, byte[] bArr) {
        String strM240a;
        if (th == null) {
            C0657x.m467d("We can do nothing with a null throwable.", new Object[0]);
            return null;
        }
        boolean zM226m = C0607c.m204a().m226m();
        String str2 = (zM226m && z) ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        if (zM226m && z) {
            C0657x.m468e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.f312C = C0594b.m124g();
        crashDetailBean.f313D = C0594b.m120e();
        crashDetailBean.f314E = C0594b.m128i();
        crashDetailBean.f315F = this.f462d.m96l();
        crashDetailBean.f316G = this.f462d.m95k();
        crashDetailBean.f317H = this.f462d.m97m();
        crashDetailBean.f356w = C0659z.m494a(this.f459a, C0607c.f415e, (String) null);
        crashDetailBean.f358y = C0658y.m474a();
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(crashDetailBean.f358y == null ? 0 : crashDetailBean.f358y.length);
        C0657x.m461a("user log size:%d", objArr);
        crashDetailBean.f335b = z ? 0 : 2;
        crashDetailBean.f338e = this.f462d.m91h();
        crashDetailBean.f339f = this.f462d.f252j;
        crashDetailBean.f340g = this.f462d.m102r();
        crashDetailBean.f346m = this.f462d.m89g();
        String name = th.getClass().getName();
        String strM244b = m244b(th, 1000);
        if (strM244b == null) {
            strM244b = "";
        }
        Object[] objArr2 = new Object[2];
        objArr2[0] = Integer.valueOf(th.getStackTrace().length);
        objArr2[1] = Boolean.valueOf(th.getCause() != null);
        C0657x.m468e("stack frame :%d, has cause %b", objArr2);
        String string = th.getStackTrace().length > 0 ? th.getStackTrace()[0].toString() : "";
        Throwable cause = th;
        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }
        if (cause != null && cause != th) {
            crashDetailBean.f347n = cause.getClass().getName();
            crashDetailBean.f348o = m244b(cause, 1000);
            if (crashDetailBean.f348o == null) {
                crashDetailBean.f348o = "";
            }
            if (cause.getStackTrace().length > 0) {
                crashDetailBean.f349p = cause.getStackTrace()[0].toString();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(":");
            sb.append(strM244b);
            sb.append("\n");
            sb.append(string);
            sb.append("\n......");
            sb.append("\nCaused by:\n");
            sb.append(crashDetailBean.f347n);
            sb.append(":");
            sb.append(crashDetailBean.f348o);
            sb.append("\n");
            strM240a = m240a(cause, C0607c.f416f);
            sb.append(strM240a);
            crashDetailBean.f350q = sb.toString();
        } else {
            crashDetailBean.f347n = name;
            crashDetailBean.f348o = strM244b + str2;
            if (crashDetailBean.f348o == null) {
                crashDetailBean.f348o = "";
            }
            crashDetailBean.f349p = string;
            strM240a = m240a(th, C0607c.f416f);
            crashDetailBean.f350q = strM240a;
        }
        crashDetailBean.f351r = System.currentTimeMillis();
        crashDetailBean.f354u = C0659z.m499a(crashDetailBean.f350q.getBytes());
        try {
            crashDetailBean.f359z = C0659z.m502a(C0607c.f416f, false);
            crashDetailBean.f310A = this.f462d.f246d;
            crashDetailBean.f311B = thread.getName() + "(" + thread.getId() + ")";
            crashDetailBean.f359z.put(crashDetailBean.f311B, strM240a);
            crashDetailBean.f318I = this.f462d.m104t();
            crashDetailBean.f341h = this.f462d.m101q();
            crashDetailBean.f342i = this.f462d.m68C();
            crashDetailBean.f322M = this.f462d.f228a;
            crashDetailBean.f323N = this.f462d.m75a();
            if (z) {
                this.f460b.m202d(crashDetailBean);
            } else {
                boolean z2 = str != null && str.length() > 0;
                boolean z3 = bArr != null && bArr.length > 0;
                if (z2) {
                    crashDetailBean.f324O = new HashMap(1);
                    crashDetailBean.f324O.put("UserData", str);
                }
                if (z3) {
                    crashDetailBean.f330U = bArr;
                }
            }
            crashDetailBean.f326Q = this.f462d.m66A();
            crashDetailBean.f327R = this.f462d.m67B();
            crashDetailBean.f328S = this.f462d.m105u();
            crashDetailBean.f329T = this.f462d.m110z();
        } catch (Throwable th2) {
            C0657x.m468e("handle crash error %s", th2.toString());
        }
        return crashDetailBean;
    }

    /* JADX INFO: renamed from: a */
    private static boolean m242a(Thread thread) {
        synchronized (f458i) {
            if (f457h != null && thread.getName().equals(f457h)) {
                return true;
            }
            f457h = thread.getName();
            return false;
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m247a(Thread thread, Throwable th, boolean z, String str, byte[] bArr) {
        if (z) {
            C0657x.m468e("Java Crash Happen cause by %s(%d)", thread.getName(), Long.valueOf(thread.getId()));
            if (m242a(thread)) {
                C0657x.m461a("this class has handled this exception", new Object[0]);
                if (this.f464f != null) {
                    C0657x.m461a("call system handler", new Object[0]);
                    this.f464f.uncaughtException(thread, th);
                } else {
                    C0657x.m468e("current process die", new Object[0]);
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                }
            }
        } else {
            C0657x.m468e("Java Catch Happen", new Object[0]);
        }
        try {
            if (!this.f465g) {
                C0657x.m466c("Java crash handler is disable. Just return.", new Object[0]);
                if (z) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f463e;
                    if (uncaughtExceptionHandler != null && m241a(uncaughtExceptionHandler)) {
                        C0657x.m468e("sys default last handle start!", new Object[0]);
                        this.f463e.uncaughtException(thread, th);
                        C0657x.m468e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f464f != null) {
                        C0657x.m468e("system handle start!", new Object[0]);
                        this.f464f.uncaughtException(thread, th);
                        C0657x.m468e("system handle end!", new Object[0]);
                        return;
                    } else {
                        C0657x.m468e("crashreport last handle start!", new Object[0]);
                        C0657x.m468e("current process die", new Object[0]);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                        C0657x.m468e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            if (!this.f461c.m148b()) {
                C0657x.m467d("no remote but still store!", new Object[0]);
            }
            if (!this.f461c.m149c().f276e && this.f461c.m148b()) {
                C0657x.m468e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                C0606b.m188a(z ? "JAVA_CRASH" : "JAVA_CATCH", C0659z.m492a(), this.f462d.f246d, thread.getName(), C0659z.m497a(th), null);
                if (z) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = this.f463e;
                    if (uncaughtExceptionHandler2 != null && m241a(uncaughtExceptionHandler2)) {
                        C0657x.m468e("sys default last handle start!", new Object[0]);
                        this.f463e.uncaughtException(thread, th);
                        C0657x.m468e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f464f != null) {
                        C0657x.m468e("system handle start!", new Object[0]);
                        this.f464f.uncaughtException(thread, th);
                        C0657x.m468e("system handle end!", new Object[0]);
                        return;
                    } else {
                        C0657x.m468e("crashreport last handle start!", new Object[0]);
                        C0657x.m468e("current process die", new Object[0]);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                        C0657x.m468e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            CrashDetailBean crashDetailBeanM243b = m243b(thread, th, z, str, bArr);
            if (crashDetailBeanM243b == null) {
                C0657x.m468e("pkg crash datas fail!", new Object[0]);
                if (z) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = this.f463e;
                    if (uncaughtExceptionHandler3 != null && m241a(uncaughtExceptionHandler3)) {
                        C0657x.m468e("sys default last handle start!", new Object[0]);
                        this.f463e.uncaughtException(thread, th);
                        C0657x.m468e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f464f != null) {
                        C0657x.m468e("system handle start!", new Object[0]);
                        this.f464f.uncaughtException(thread, th);
                        C0657x.m468e("system handle end!", new Object[0]);
                        return;
                    } else {
                        C0657x.m468e("crashreport last handle start!", new Object[0]);
                        C0657x.m468e("current process die", new Object[0]);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                        C0657x.m468e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            C0606b.m188a(z ? "JAVA_CRASH" : "JAVA_CATCH", C0659z.m492a(), this.f462d.f246d, thread.getName(), C0659z.m497a(th), crashDetailBeanM243b);
            if (!this.f460b.m199a(crashDetailBeanM243b)) {
                this.f460b.m197a(crashDetailBeanM243b, 3000L, z);
            }
            if (z) {
                this.f460b.m201c(crashDetailBeanM243b);
            }
            if (z) {
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler4 = this.f463e;
                if (uncaughtExceptionHandler4 != null && m241a(uncaughtExceptionHandler4)) {
                    C0657x.m468e("sys default last handle start!", new Object[0]);
                    this.f463e.uncaughtException(thread, th);
                    C0657x.m468e("sys default last handle end!", new Object[0]);
                } else if (this.f464f != null) {
                    C0657x.m468e("system handle start!", new Object[0]);
                    this.f464f.uncaughtException(thread, th);
                    C0657x.m468e("system handle end!", new Object[0]);
                } else {
                    C0657x.m468e("crashreport last handle start!", new Object[0]);
                    C0657x.m468e("current process die", new Object[0]);
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                    C0657x.m468e("crashreport last handle end!", new Object[0]);
                }
            }
        } catch (Throwable th2) {
            try {
                if (!C0657x.m462a(th2)) {
                    th2.printStackTrace();
                }
                if (z) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler5 = this.f463e;
                    if (uncaughtExceptionHandler5 != null && m241a(uncaughtExceptionHandler5)) {
                        C0657x.m468e("sys default last handle start!", new Object[0]);
                        this.f463e.uncaughtException(thread, th);
                        C0657x.m468e("sys default last handle end!", new Object[0]);
                    } else if (this.f464f != null) {
                        C0657x.m468e("system handle start!", new Object[0]);
                        this.f464f.uncaughtException(thread, th);
                        C0657x.m468e("system handle end!", new Object[0]);
                    } else {
                        C0657x.m468e("crashreport last handle start!", new Object[0]);
                        C0657x.m468e("current process die", new Object[0]);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                        C0657x.m468e("crashreport last handle end!", new Object[0]);
                    }
                }
            } catch (Throwable th3) {
                if (z) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler6 = this.f463e;
                    if (uncaughtExceptionHandler6 != null && m241a(uncaughtExceptionHandler6)) {
                        C0657x.m468e("sys default last handle start!", new Object[0]);
                        this.f463e.uncaughtException(thread, th);
                        C0657x.m468e("sys default last handle end!", new Object[0]);
                    } else if (this.f464f != null) {
                        C0657x.m468e("system handle start!", new Object[0]);
                        this.f464f.uncaughtException(thread, th);
                        C0657x.m468e("system handle end!", new Object[0]);
                    } else {
                        C0657x.m468e("crashreport last handle start!", new Object[0]);
                        C0657x.m468e("current process die", new Object[0]);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                        C0657x.m468e("crashreport last handle end!", new Object[0]);
                    }
                }
                throw th3;
            }
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        synchronized (f458i) {
            m247a(thread, th, true, null, null);
        }
    }

    /* JADX INFO: renamed from: a */
    private static boolean m241a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        if (uncaughtExceptionHandler == null) {
            return true;
        }
        String name = uncaughtExceptionHandler.getClass().getName();
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            if (name.equals(className) && "uncaughtException".equals(methodName)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: a */
    public final synchronized void m246a(StrategyBean strategyBean) {
        if (strategyBean != null) {
            if (strategyBean.f276e != this.f465g) {
                C0657x.m461a("java changed to %b", Boolean.valueOf(strategyBean.f276e));
                if (strategyBean.f276e) {
                    m245a();
                    return;
                }
                m248b();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static String m240a(Throwable th, int i) {
        if (th == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (th.getStackTrace() != null) {
                for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                    if (i > 0 && sb.length() >= i) {
                        sb.append("\n[Stack over limit size :" + i + " , has been cutted !]");
                        return sb.toString();
                    }
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
        } catch (Throwable th2) {
            C0657x.m468e("gen stack error %s", th2.toString());
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: b */
    private static String m244b(Throwable th, int i) {
        if (th.getMessage() == null) {
            return "";
        }
        if (th.getMessage().length() <= 1000) {
            return th.getMessage();
        }
        return th.getMessage().substring(0, 1000) + "\n[Message over limit size:1000, has been cutted!]";
    }
}
