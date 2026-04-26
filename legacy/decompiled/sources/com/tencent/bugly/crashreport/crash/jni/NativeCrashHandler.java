package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import android.os.Build;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.InterfaceC0588a;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.crash.C0606b;
import com.tencent.bugly.crashreport.crash.C0607c;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import java.io.File;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class NativeCrashHandler implements InterfaceC0588a {

    /* JADX INFO: renamed from: a */
    private static NativeCrashHandler f485a = null;

    /* JADX INFO: renamed from: b */
    private static int f486b = 1;

    /* JADX INFO: renamed from: m */
    private static boolean f487m = false;

    /* JADX INFO: renamed from: n */
    private static boolean f488n = false;

    /* JADX INFO: renamed from: p */
    private static boolean f489p = true;

    /* JADX INFO: renamed from: c */
    private final Context f490c;

    /* JADX INFO: renamed from: d */
    private final C0593a f491d;

    /* JADX INFO: renamed from: e */
    private final C0656w f492e;

    /* JADX INFO: renamed from: f */
    private NativeExceptionHandler f493f;

    /* JADX INFO: renamed from: g */
    private String f494g;

    /* JADX INFO: renamed from: h */
    private final boolean f495h;

    /* JADX INFO: renamed from: i */
    private boolean f496i = false;

    /* JADX INFO: renamed from: j */
    private boolean f497j = false;

    /* JADX INFO: renamed from: k */
    private boolean f498k = false;

    /* JADX INFO: renamed from: l */
    private boolean f499l = false;

    /* JADX INFO: renamed from: o */
    private C0606b f500o;

    protected native boolean appendNativeLog(String str, String str2, String str3);

    protected native boolean appendWholeNativeLog(String str);

    protected native String getNativeKeyValueList();

    protected native String getNativeLog();

    protected native boolean putNativeKeyValue(String str, String str2);

    protected native String regist(String str, boolean z, int i);

    protected native String removeNativeKeyValue(String str);

    protected native void setNativeInfo(int i, String str);

    protected native void testCrash();

    protected native String unregist();

    /* JADX INFO: renamed from: a */
    static /* synthetic */ boolean m255a(NativeCrashHandler nativeCrashHandler, int i, String str) {
        return nativeCrashHandler.m254a(999, str);
    }

    private NativeCrashHandler(Context context, C0593a c0593a, C0606b c0606b, C0656w c0656w, boolean z, String str) {
        this.f490c = C0659z.m486a(context);
        try {
            if (C0659z.m509a(str)) {
                str = context.getDir("bugly", 0).getAbsolutePath();
            }
        } catch (Throwable unused) {
            str = "/data/data/" + C0593a.m64a(context).f245c + "/app_bugly";
        }
        this.f500o = c0606b;
        this.f494g = str;
        this.f491d = c0593a;
        this.f492e = c0656w;
        this.f495h = z;
        this.f493f = new C0613a(context, c0593a, c0606b, C0596a.m139a());
    }

    public static synchronized NativeCrashHandler getInstance(Context context, C0593a c0593a, C0606b c0606b, C0596a c0596a, C0656w c0656w, boolean z, String str) {
        if (f485a == null) {
            f485a = new NativeCrashHandler(context, c0593a, c0606b, c0656w, z, str);
        }
        return f485a;
    }

    public static synchronized NativeCrashHandler getInstance() {
        return f485a;
    }

    public synchronized String getDumpFilePath() {
        return this.f494g;
    }

    public synchronized void setDumpFilePath(String str) {
        this.f494g = str;
    }

    public static void setShouldHandleInJava(boolean z) {
        f489p = z;
        NativeCrashHandler nativeCrashHandler = f485a;
        if (nativeCrashHandler != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(z);
            nativeCrashHandler.m254a(999, sb.toString());
        }
    }

    public static boolean isShouldHandleInJava() {
        return f489p;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:13|(1:15)(16:17|(1:19)|74|21|(1:23)|24|(1:26)|27|(1:29)(1:30)|31|(1:33)(1:34)|35|(1:37)|38|39|40)|16|74|21|(0)|24|(0)|27|(0)(0)|31|(0)(0)|35|(0)|38|39|40) */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007e A[Catch: all -> 0x008c, TryCatch #3 {all -> 0x008c, blocks: (B:21:0x0074, B:23:0x007e, B:24:0x0080, B:26:0x008a), top: B:74:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008a A[Catch: all -> 0x008c, TRY_LEAVE, TryCatch #3 {all -> 0x008c, blocks: (B:21:0x0074, B:23:0x007e, B:24:0x0080, B:26:0x008a), top: B:74:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0090 A[Catch: all -> 0x00f1, TryCatch #0 {all -> 0x00f1, blocks: (B:11:0x0015, B:13:0x001f, B:15:0x0051, B:16:0x005b, B:27:0x008c, B:29:0x0090, B:31:0x009f, B:33:0x00a3, B:35:0x00b2, B:37:0x00ca, B:38:0x00e0, B:34:0x00ab, B:30:0x0098, B:17:0x0063, B:19:0x0069), top: B:69:0x0015, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0098 A[Catch: all -> 0x00f1, TryCatch #0 {all -> 0x00f1, blocks: (B:11:0x0015, B:13:0x001f, B:15:0x0051, B:16:0x005b, B:27:0x008c, B:29:0x0090, B:31:0x009f, B:33:0x00a3, B:35:0x00b2, B:37:0x00ca, B:38:0x00e0, B:34:0x00ab, B:30:0x0098, B:17:0x0063, B:19:0x0069), top: B:69:0x0015, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a3 A[Catch: all -> 0x00f1, TryCatch #0 {all -> 0x00f1, blocks: (B:11:0x0015, B:13:0x001f, B:15:0x0051, B:16:0x005b, B:27:0x008c, B:29:0x0090, B:31:0x009f, B:33:0x00a3, B:35:0x00b2, B:37:0x00ca, B:38:0x00e0, B:34:0x00ab, B:30:0x0098, B:17:0x0063, B:19:0x0069), top: B:69:0x0015, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ab A[Catch: all -> 0x00f1, TryCatch #0 {all -> 0x00f1, blocks: (B:11:0x0015, B:13:0x001f, B:15:0x0051, B:16:0x005b, B:27:0x008c, B:29:0x0090, B:31:0x009f, B:33:0x00a3, B:35:0x00b2, B:37:0x00ca, B:38:0x00e0, B:34:0x00ab, B:30:0x0098, B:17:0x0063, B:19:0x0069), top: B:69:0x0015, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ca A[Catch: all -> 0x00f1, TryCatch #0 {all -> 0x00f1, blocks: (B:11:0x0015, B:13:0x001f, B:15:0x0051, B:16:0x005b, B:27:0x008c, B:29:0x0090, B:31:0x009f, B:33:0x00a3, B:35:0x00b2, B:37:0x00ca, B:38:0x00e0, B:34:0x00ab, B:30:0x0098, B:17:0x0063, B:19:0x0069), top: B:69:0x0015, outer: #2 }] */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void m253a(boolean r11) {
        /*
            Method dump skipped, instruction units count: 462
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.m253a(boolean):void");
    }

    public synchronized void startNativeMonitor() {
        if (!this.f497j && !this.f496i) {
            String str = "Bugly";
            boolean z = !C0659z.m509a(this.f491d.f255m);
            String str2 = this.f491d.f255m;
            if (z) {
                str = str2;
            } else {
                this.f491d.getClass();
            }
            boolean zM256a = m256a(str, z);
            this.f497j = zM256a;
            if (zM256a || this.f496i) {
                m253a(this.f495h);
                if (f487m) {
                    setNativeAppVersion(this.f491d.f252j);
                    setNativeAppChannel(this.f491d.f254l);
                    setNativeAppPackage(this.f491d.f245c);
                    setNativeUserId(this.f491d.m89g());
                    setNativeIsAppForeground(this.f491d.m75a());
                    setNativeLaunchTime(this.f491d.f228a);
                }
                return;
            }
            return;
        }
        m253a(this.f495h);
    }

    public void checkUploadRecordCrash() {
        this.f492e.m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.1
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                if (C0659z.m506a(NativeCrashHandler.this.f490c, "native_record_lock", 10000L)) {
                    if (!NativeCrashHandler.f489p) {
                        NativeCrashHandler.m255a(NativeCrashHandler.this, 999, Bugly.SDK_IS_DEV);
                    }
                    CrashDetailBean crashDetailBeanM265a = C0614b.m265a(NativeCrashHandler.this.f490c, NativeCrashHandler.this.f494g, NativeCrashHandler.this.f493f);
                    if (crashDetailBeanM265a != null) {
                        C0657x.m461a("[Native] Get crash from native record.", new Object[0]);
                        if (!NativeCrashHandler.this.f500o.m199a(crashDetailBeanM265a)) {
                            NativeCrashHandler.this.f500o.m197a(crashDetailBeanM265a, 3000L, false);
                        }
                        C0614b.m271a(false, NativeCrashHandler.this.f494g);
                    }
                    NativeCrashHandler.this.m264a();
                    C0659z.m521b(NativeCrashHandler.this.f490c, "native_record_lock");
                    return;
                }
                C0657x.m461a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    private static boolean m256a(String str, boolean z) {
        boolean z2;
        try {
            C0657x.m461a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                C0657x.m461a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th) {
                th = th;
                z2 = true;
                C0657x.m467d(th.getMessage(), new Object[0]);
                C0657x.m467d("[Native] Failed to load so: %s", str);
                return z2;
            }
        } catch (Throwable th2) {
            th = th2;
            z2 = false;
        }
    }

    /* JADX INFO: renamed from: c */
    private synchronized void m261c() {
        if (!this.f498k) {
            C0657x.m467d("[Native] Native crash report has already unregistered.", new Object[0]);
            return;
        }
        try {
        } catch (Throwable unused) {
            C0657x.m466c("[Native] Failed to close native crash report.", new Object[0]);
        }
        if (unregist() != null) {
            C0657x.m461a("[Native] Successfully closed native crash report.", new Object[0]);
            this.f498k = false;
            return;
        }
        try {
            C0659z.m490a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", null, new Class[]{Boolean.TYPE}, new Object[]{false});
            this.f498k = false;
            C0657x.m461a("[Native] Successfully closed native crash report.", new Object[0]);
            return;
        } catch (Throwable unused2) {
            C0657x.m466c("[Native] Failed to close native crash report.", new Object[0]);
            this.f497j = false;
            this.f496i = false;
            return;
        }
    }

    public void testNativeCrash() {
        if (!this.f497j) {
            C0657x.m467d("[Native] Bugly SO file has not been load.", new Object[0]);
        } else {
            testCrash();
        }
    }

    public void testNativeCrash(boolean z, boolean z2, boolean z3) {
        StringBuilder sb = new StringBuilder();
        sb.append(z);
        m254a(16, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(z2);
        m254a(17, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(z3);
        m254a(18, sb3.toString());
        testNativeCrash();
    }

    public void dumpAnrNativeStack() {
        m254a(19, "1");
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.f493f;
    }

    /* JADX INFO: renamed from: a */
    protected final void m264a() {
        long jM513b = C0659z.m513b() - C0607c.f417g;
        long jM513b2 = C0659z.m513b() + 86400000;
        File file = new File(this.f494g);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                    int i = 0;
                    int i2 = 0;
                    for (File file2 : fileArrListFiles) {
                        long jLastModified = file2.lastModified();
                        if (jLastModified < jM513b || jLastModified >= jM513b2) {
                            C0657x.m461a("[Native] Delete record file: %s", file2.getAbsolutePath());
                            i++;
                            if (file2.delete()) {
                                i2++;
                            }
                        }
                    }
                    C0657x.m466c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i), Integer.valueOf(i2));
                }
            } catch (Throwable th) {
                C0657x.m462a(th);
            }
        }
    }

    public void removeEmptyNativeRecordFiles() {
        C0614b.m275c(this.f494g);
    }

    /* JADX INFO: renamed from: b */
    private synchronized void m258b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            m261c();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.f499l;
    }

    /* JADX INFO: renamed from: c */
    private synchronized void m262c(boolean z) {
        if (this.f499l != z) {
            C0657x.m461a("user change native %b", Boolean.valueOf(z));
            this.f499l = z;
        }
    }

    public synchronized void setUserOpened(boolean z) {
        m262c(z);
        boolean zIsUserOpened = isUserOpened();
        C0596a c0596aM139a = C0596a.m139a();
        if (c0596aM139a != null) {
            zIsUserOpened = zIsUserOpened && c0596aM139a.m149c().f276e;
        }
        if (zIsUserOpened != this.f498k) {
            C0657x.m461a("native changed to %b", Boolean.valueOf(zIsUserOpened));
            m258b(zIsUserOpened);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0031 A[Catch: all -> 0x0043, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:7:0x000b, B:8:0x001a, B:10:0x0026, B:14:0x002d, B:16:0x0031), top: B:22:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void onStrategyChanged(com.tencent.bugly.crashreport.common.strategy.StrategyBean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L1a
            boolean r2 = r5.f276e     // Catch: java.lang.Throwable -> L43
            boolean r3 = r4.f498k     // Catch: java.lang.Throwable -> L43
            if (r2 == r3) goto L1a
            java.lang.String r2 = "server native changed to %b"
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L43
            boolean r5 = r5.f276e     // Catch: java.lang.Throwable -> L43
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch: java.lang.Throwable -> L43
            r3[r1] = r5     // Catch: java.lang.Throwable -> L43
            com.tencent.bugly.proguard.C0657x.m467d(r2, r3)     // Catch: java.lang.Throwable -> L43
        L1a:
            com.tencent.bugly.crashreport.common.strategy.a r5 = com.tencent.bugly.crashreport.common.strategy.C0596a.m139a()     // Catch: java.lang.Throwable -> L43
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r5 = r5.m149c()     // Catch: java.lang.Throwable -> L43
            boolean r5 = r5.f276e     // Catch: java.lang.Throwable -> L43
            if (r5 == 0) goto L2c
            boolean r5 = r4.f499l     // Catch: java.lang.Throwable -> L43
            if (r5 == 0) goto L2c
            r5 = r0
            goto L2d
        L2c:
            r5 = r1
        L2d:
            boolean r2 = r4.f498k     // Catch: java.lang.Throwable -> L43
            if (r5 == r2) goto L41
            java.lang.String r2 = "native changed to %b"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L43
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r5)     // Catch: java.lang.Throwable -> L43
            r0[r1] = r3     // Catch: java.lang.Throwable -> L43
            com.tencent.bugly.proguard.C0657x.m461a(r2, r0)     // Catch: java.lang.Throwable -> L43
            r4.m258b(r5)     // Catch: java.lang.Throwable -> L43
        L41:
            monitor-exit(r4)
            return
        L43:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.onStrategyChanged(com.tencent.bugly.crashreport.common.strategy.StrategyBean):void");
    }

    public boolean appendLogToNative(String str, String str2, String str3) {
        if ((this.f496i || this.f497j) && f487m && str != null && str2 != null && str3 != null) {
            try {
                if (this.f497j) {
                    return appendNativeLog(str, str2, str3);
                }
                Boolean bool = (Boolean) C0659z.m490a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", null, new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
                f487m = false;
            } catch (Throwable th) {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    public String getLogFromNative() {
        if ((!this.f496i && !this.f497j) || !f487m) {
            return null;
        }
        try {
            if (this.f497j) {
                return getNativeLog();
            }
            return (String) C0659z.m490a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", null, null, null);
        } catch (UnsatisfiedLinkError unused) {
            f487m = false;
            return null;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public boolean putKeyValueToNative(String str, String str2) {
        if ((this.f496i || this.f497j) && f487m && str != null && str2 != null) {
            try {
                if (this.f497j) {
                    return putNativeKeyValue(str, str2);
                }
                Boolean bool = (Boolean) C0659z.m490a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", null, new Class[]{String.class, String.class}, new Object[]{str, str2});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
                f487m = false;
            } catch (Throwable th) {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: a */
    private boolean m254a(int i, String str) {
        if (this.f497j && f488n) {
            try {
                setNativeInfo(i, str);
                return true;
            } catch (UnsatisfiedLinkError unused) {
                f488n = false;
            } catch (Throwable th) {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    public boolean filterSigabrtSysLog() {
        return m254a(998, "true");
    }

    public boolean setNativeAppVersion(String str) {
        return m254a(10, str);
    }

    public boolean setNativeAppChannel(String str) {
        return m254a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return m254a(13, str);
    }

    public boolean setNativeUserId(String str) {
        return m254a(11, str);
    }

    @Override // com.tencent.bugly.crashreport.InterfaceC0588a
    public boolean setNativeIsAppForeground(boolean z) {
        return m254a(14, z ? "true" : Bugly.SDK_IS_DEV);
    }

    public boolean setNativeLaunchTime(long j) {
        try {
            return m254a(15, String.valueOf(j));
        } catch (NumberFormatException e) {
            if (C0657x.m462a(e)) {
                return false;
            }
            e.printStackTrace();
            return false;
        }
    }

    public void enableCatchAnrTrace() {
        if (Build.VERSION.SDK_INT > 30 || Build.VERSION.SDK_INT < 23) {
            return;
        }
        f486b |= 2;
    }

    public boolean isEnableCatchAnrTrace() {
        return (f486b & 2) == 2;
    }
}
