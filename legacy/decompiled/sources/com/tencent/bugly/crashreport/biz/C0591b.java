package com.tencent.bugly.crashreport.biz;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.biz.C0590a.AnonymousClass2;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.biz.b */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class C0591b {

    /* JADX INFO: renamed from: a */
    public static C0590a f183a = null;

    /* JADX INFO: renamed from: b */
    private static boolean f184b = false;

    /* JADX INFO: renamed from: c */
    private static int f185c = 10;

    /* JADX INFO: renamed from: d */
    private static long f186d = 300000;

    /* JADX INFO: renamed from: e */
    private static long f187e = 30000;

    /* JADX INFO: renamed from: f */
    private static long f188f = 0;

    /* JADX INFO: renamed from: g */
    private static int f189g = 0;

    /* JADX INFO: renamed from: h */
    private static long f190h = 0;

    /* JADX INFO: renamed from: i */
    private static long f191i = 0;

    /* JADX INFO: renamed from: j */
    private static long f192j = 0;

    /* JADX INFO: renamed from: k */
    private static Application.ActivityLifecycleCallbacks f193k = null;

    /* JADX INFO: renamed from: l */
    private static Class<?> f194l = null;

    /* JADX INFO: renamed from: m */
    private static boolean f195m = true;

    /* JADX INFO: renamed from: a */
    static /* synthetic */ String m34a(String str, String str2) {
        return C0659z.m492a() + "  " + str + "  " + str2 + "\n";
    }

    /* JADX INFO: renamed from: g */
    static /* synthetic */ int m49g() {
        int i = f189g;
        f189g = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0068 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0069  */
    /* JADX INFO: renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m45c(android.content.Context r14, com.tencent.bugly.BuglyStrategy r15) {
        /*
            Method dump skipped, instruction units count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.biz.C0591b.m45c(android.content.Context, com.tencent.bugly.BuglyStrategy):void");
    }

    /* JADX INFO: renamed from: a */
    public static void m38a(final Context context, final BuglyStrategy buglyStrategy) {
        long appReportDelay;
        if (f184b) {
            return;
        }
        boolean z = C0593a.m64a(context).f247e;
        f195m = z;
        f183a = new C0590a(context, z);
        f184b = true;
        if (buglyStrategy != null) {
            f194l = buglyStrategy.getUserInfoActivity();
            appReportDelay = buglyStrategy.getAppReportDelay();
        } else {
            appReportDelay = 0;
        }
        if (appReportDelay <= 0) {
            m45c(context, buglyStrategy);
        } else {
            C0656w.m453a().m456a(new Runnable() { // from class: com.tencent.bugly.crashreport.biz.b.1
                @Override // java.lang.Runnable
                public final void run() {
                    C0591b.m45c(context, buglyStrategy);
                }
            }, appReportDelay);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m36a(long j) {
        if (j < 0) {
            j = C0596a.m139a().m149c().f286o;
        }
        f188f = j;
    }

    /* JADX INFO: renamed from: a */
    public static void m39a(StrategyBean strategyBean, boolean z) {
        C0656w c0656wM453a;
        C0590a c0590a = f183a;
        if (c0590a != null && !z && (c0656wM453a = C0656w.m453a()) != null) {
            c0656wM453a.m455a(c0590a.new AnonymousClass2());
        }
        if (strategyBean == null) {
            return;
        }
        if (strategyBean.f286o > 0) {
            f187e = strategyBean.f286o;
        }
        if (strategyBean.f291t > 0) {
            f185c = strategyBean.f291t;
        }
        if (strategyBean.f292u > 0) {
            f186d = strategyBean.f292u;
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m35a() {
        C0590a c0590a = f183a;
        if (c0590a != null) {
            c0590a.m31a(2, false, 0L);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m37a(Context context) {
        if (!f184b || context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            Application application = context.getApplicationContext() instanceof Application ? (Application) context.getApplicationContext() : null;
            if (application != null) {
                try {
                    if (f193k != null) {
                        application.unregisterActivityLifecycleCallbacks(f193k);
                    }
                } catch (Exception e) {
                    if (!C0657x.m462a(e)) {
                        e.printStackTrace();
                    }
                }
            }
        }
        f184b = false;
    }
}
