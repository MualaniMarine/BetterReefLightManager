package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.anr.C0605b;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.C0649p;
import com.tencent.bugly.proguard.C0651r;
import com.tencent.bugly.proguard.C0654u;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import com.tencent.bugly.proguard.InterfaceC0648o;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.c */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0607c {

    /* JADX INFO: renamed from: a */
    public static int f411a = 0;

    /* JADX INFO: renamed from: b */
    public static boolean f412b = false;

    /* JADX INFO: renamed from: c */
    public static int f413c = 2;

    /* JADX INFO: renamed from: d */
    public static boolean f414d = true;

    /* JADX INFO: renamed from: e */
    public static int f415e = 20480;

    /* JADX INFO: renamed from: f */
    public static int f416f = 20480;

    /* JADX INFO: renamed from: g */
    public static long f417g = 604800000;

    /* JADX INFO: renamed from: h */
    public static String f418h = null;

    /* JADX INFO: renamed from: i */
    public static boolean f419i = false;

    /* JADX INFO: renamed from: j */
    public static String f420j = null;

    /* JADX INFO: renamed from: k */
    public static int f421k = 5000;

    /* JADX INFO: renamed from: l */
    public static boolean f422l = true;

    /* JADX INFO: renamed from: m */
    public static boolean f423m = false;

    /* JADX INFO: renamed from: n */
    public static String f424n;

    /* JADX INFO: renamed from: o */
    public static String f425o;

    /* JADX INFO: renamed from: r */
    private static C0607c f426r;

    /* JADX INFO: renamed from: p */
    public final C0606b f427p;

    /* JADX INFO: renamed from: q */
    private final Context f428q;

    /* JADX INFO: renamed from: s */
    private final C0609e f429s;

    /* JADX INFO: renamed from: t */
    private final NativeCrashHandler f430t;

    /* JADX INFO: renamed from: u */
    private C0596a f431u;

    /* JADX INFO: renamed from: v */
    private C0656w f432v;

    /* JADX INFO: renamed from: w */
    private final C0605b f433w;

    /* JADX INFO: renamed from: x */
    private Boolean f434x;

    /* JADX INFO: renamed from: y */
    private int f435y = 31;

    /* JADX INFO: renamed from: z */
    private boolean f436z = false;

    private C0607c(int i, Context context, C0656w c0656w, boolean z, BuglyStrategy.C0583a c0583a, InterfaceC0648o interfaceC0648o, String str) {
        f411a = i;
        Context contextM486a = C0659z.m486a(context);
        this.f428q = contextM486a;
        this.f431u = C0596a.m139a();
        this.f432v = c0656w;
        C0654u c0654uM434a = C0654u.m434a();
        C0649p c0649pM402a = C0649p.m402a();
        this.f427p = new C0606b(i, contextM486a, c0654uM434a, c0649pM402a, this.f431u, c0583a, interfaceC0648o);
        C0593a c0593aM64a = C0593a.m64a(contextM486a);
        this.f429s = new C0609e(contextM486a, this.f427p, this.f431u, c0593aM64a);
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance(contextM486a, c0593aM64a, this.f427p, this.f431u, c0656w, z, str);
        this.f430t = nativeCrashHandler;
        c0593aM64a.f206D = nativeCrashHandler;
        this.f433w = C0605b.m162a(contextM486a, this.f431u, c0593aM64a, c0656w, c0649pM402a, this.f427p, c0583a);
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0607c m205a(int i, Context context, boolean z, BuglyStrategy.C0583a c0583a, InterfaceC0648o interfaceC0648o, String str) {
        if (f426r == null) {
            f426r = new C0607c(1004, context, C0656w.m453a(), z, c0583a, null, null);
        }
        return f426r;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0607c m204a() {
        return f426r;
    }

    /* JADX INFO: renamed from: a */
    public final void m210a(StrategyBean strategyBean) {
        this.f429s.m246a(strategyBean);
        this.f430t.onStrategyChanged(strategyBean);
        this.f433w.m182c();
        C0656w.m453a().m456a(new AnonymousClass2(), 3000L);
    }

    /* JADX INFO: renamed from: b */
    public final boolean m215b() {
        Boolean bool = this.f434x;
        if (bool != null) {
            return bool.booleanValue();
        }
        String str = C0593a.m65b().f246d;
        List<C0651r> listM419a = C0649p.m402a().m419a(1);
        ArrayList arrayList = new ArrayList();
        if (listM419a != null && listM419a.size() > 0) {
            for (C0651r c0651r : listM419a) {
                if (str.equals(c0651r.f708c)) {
                    this.f434x = true;
                    arrayList.add(c0651r);
                }
            }
            if (arrayList.size() > 0) {
                C0649p.m402a().m421a(arrayList);
            }
            return true;
        }
        this.f434x = false;
        return false;
    }

    /* JADX INFO: renamed from: c */
    public final synchronized void m216c() {
        this.f429s.m245a();
        this.f430t.setUserOpened(true);
        this.f433w.m178a(true);
    }

    /* JADX INFO: renamed from: d */
    public final synchronized void m217d() {
        this.f429s.m248b();
        this.f430t.setUserOpened(false);
        this.f433w.m178a(false);
    }

    /* JADX INFO: renamed from: e */
    public final void m218e() {
        this.f429s.m248b();
    }

    /* JADX INFO: renamed from: f */
    public final void m219f() {
        this.f429s.m245a();
    }

    /* JADX INFO: renamed from: g */
    public final void m220g() {
        this.f430t.setUserOpened(false);
    }

    /* JADX INFO: renamed from: h */
    public final void m221h() {
        this.f430t.setUserOpened(true);
    }

    /* JADX INFO: renamed from: i */
    public final void m222i() {
        this.f433w.m178a(true);
    }

    /* JADX INFO: renamed from: j */
    public final void m223j() {
        this.f433w.m178a(false);
    }

    /* JADX INFO: renamed from: k */
    public final void m224k() {
        this.f430t.enableCatchAnrTrace();
    }

    /* JADX INFO: renamed from: a */
    public final synchronized void m214a(boolean z, boolean z2, boolean z3) {
        this.f430t.testNativeCrash(z, z2, z3);
    }

    /* JADX INFO: renamed from: l */
    public final synchronized void m225l() {
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i < 30) {
                try {
                    C0657x.m461a("try main sleep for make a test anr! try:%d/30 , kill it if you don't want to wait!", Integer.valueOf(i2));
                    C0659z.m517b(5000L);
                    i = i2;
                } catch (Throwable th) {
                    if (C0657x.m462a(th)) {
                        return;
                    }
                    th.printStackTrace();
                    return;
                }
            }
        }
    }

    /* JADX INFO: renamed from: m */
    public final boolean m226m() {
        return this.f433w.m179a();
    }

    /* JADX INFO: renamed from: a */
    public final void m212a(final Thread thread, final Throwable th, boolean z, String str, byte[] bArr, final boolean z2) {
        final boolean z3 = false;
        final String str2 = null;
        final byte[] bArr2 = null;
        this.f432v.m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.c.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    C0657x.m466c("post a throwable %b", Boolean.valueOf(z3));
                    C0607c.this.f429s.m247a(thread, th, false, str2, bArr2);
                    if (z2) {
                        C0657x.m461a("clear user datas", new Object[0]);
                        C0593a.m64a(C0607c.this.f428q).m106v();
                    }
                } catch (Throwable th2) {
                    if (!C0657x.m465b(th2)) {
                        th2.printStackTrace();
                    }
                    C0657x.m468e("java catch error: %s", th.toString());
                }
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public final void m211a(CrashDetailBean crashDetailBean) {
        this.f427p.m203e(crashDetailBean);
    }

    /* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.c$2, reason: invalid class name */
    /* JADX INFO: compiled from: BUGLY */
    final class AnonymousClass2 extends Thread {
        AnonymousClass2() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            List<CrashDetailBean> list;
            if (!C0659z.m506a(C0607c.this.f428q, "local_crash_lock", 10000L)) {
                C0657x.m466c("Failed to lock file for uploading local crash.", new Object[0]);
                return;
            }
            List<CrashDetailBean> listM196a = C0607c.this.f427p.m196a();
            if (listM196a != null && listM196a.size() > 0) {
                C0657x.m466c("Size of crash list: %s", Integer.valueOf(listM196a.size()));
                int size = listM196a.size();
                if (size > 20) {
                    ArrayList arrayList = new ArrayList();
                    Collections.sort(listM196a);
                    for (int i = 0; i < 20; i++) {
                        arrayList.add(listM196a.get((size - 1) - i));
                    }
                    list = arrayList;
                } else {
                    list = listM196a;
                }
                C0607c.this.f427p.m198a(list, 0L, false, false, false);
            } else {
                C0657x.m466c("no crash need to be uploaded at this start", new Object[0]);
            }
            C0659z.m521b(C0607c.this.f428q, "local_crash_lock");
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m209a(long j) {
        C0656w.m453a().m456a(new AnonymousClass2(), j);
    }

    /* JADX INFO: renamed from: n */
    public final void m227n() {
        this.f430t.checkUploadRecordCrash();
    }

    /* JADX INFO: renamed from: o */
    public final void m228o() {
        if (C0593a.m65b().f246d.equals(AppInfo.m56a(this.f428q))) {
            this.f430t.removeEmptyNativeRecordFiles();
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m208a(int i) {
        this.f435y = i;
    }

    /* JADX INFO: renamed from: a */
    public final void m213a(boolean z) {
        this.f436z = z;
    }

    /* JADX INFO: renamed from: p */
    public final boolean m229p() {
        return this.f436z;
    }

    /* JADX INFO: renamed from: q */
    public final boolean m230q() {
        return (this.f435y & 16) > 0;
    }

    /* JADX INFO: renamed from: r */
    public final boolean m231r() {
        return (this.f435y & 8) > 0;
    }

    /* JADX INFO: renamed from: s */
    public final boolean m232s() {
        return (this.f435y & 4) > 0;
    }

    /* JADX INFO: renamed from: t */
    public final boolean m233t() {
        return (this.f435y & 2) > 0;
    }

    /* JADX INFO: renamed from: u */
    public final boolean m234u() {
        return (this.f435y & 1) > 0;
    }
}
