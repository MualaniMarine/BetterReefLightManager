package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.C0594b;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0658y;
import com.tencent.bugly.proguard.C0659z;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.d */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0608d {

    /* JADX INFO: renamed from: a */
    private static C0608d f445a;

    /* JADX INFO: renamed from: b */
    private C0596a f446b;

    /* JADX INFO: renamed from: c */
    private C0593a f447c;

    /* JADX INFO: renamed from: d */
    private C0606b f448d;

    /* JADX INFO: renamed from: e */
    private Context f449e;

    /* JADX INFO: renamed from: a */
    static /* synthetic */ void m237a(C0608d c0608d) {
        C0657x.m466c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            c0608d.f447c.getClass();
            C0659z.m505a(cls, "sdkPackageName", "com.tencent.bugly", null);
            C0657x.m466c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable unused) {
            C0657x.m461a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    /* JADX INFO: renamed from: a */
    static /* synthetic */ void m238a(C0608d c0608d, Thread thread, int i, String str, String str2, String str3, Map map) {
        String str4;
        String str5;
        String str6;
        Thread threadCurrentThread = thread == null ? Thread.currentThread() : thread;
        if (i == 4) {
            str4 = "Unity";
        } else if (i == 5 || i == 6) {
            str4 = "Cocos";
        } else {
            if (i != 8) {
                C0657x.m467d("[ExtraCrashManager] Unknown extra crash type: %d", Integer.valueOf(i));
                return;
            }
            str4 = "H5";
        }
        C0657x.m468e("[ExtraCrashManager] %s Crash Happen", str4);
        try {
            if (!c0608d.f446b.m148b()) {
                C0657x.m467d("[ExtraCrashManager] There is no remote strategy, but still store it.", new Object[0]);
            }
            StrategyBean strategyBeanM149c = c0608d.f446b.m149c();
            if (!strategyBeanM149c.f276e && c0608d.f446b.m148b()) {
                C0657x.m468e("[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                C0606b.m188a(str4, C0659z.m492a(), c0608d.f447c.f246d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, null);
                C0657x.m468e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            if (i == 5 || i == 6) {
                if (!strategyBeanM149c.f281j) {
                    C0657x.m468e("[ExtraCrashManager] %s report is disabled.", str4);
                    C0657x.m468e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                    return;
                }
            } else if (i == 8 && !strategyBeanM149c.f282k) {
                C0657x.m468e("[ExtraCrashManager] %s report is disabled.", str4);
                C0657x.m468e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            int i2 = i != 8 ? i : 5;
            CrashDetailBean crashDetailBean = new CrashDetailBean();
            crashDetailBean.f312C = C0594b.m124g();
            crashDetailBean.f313D = C0594b.m120e();
            crashDetailBean.f314E = C0594b.m128i();
            crashDetailBean.f315F = c0608d.f447c.m96l();
            crashDetailBean.f316G = c0608d.f447c.m95k();
            crashDetailBean.f317H = c0608d.f447c.m97m();
            crashDetailBean.f356w = C0659z.m494a(c0608d.f449e, C0607c.f415e, (String) null);
            crashDetailBean.f335b = i2;
            crashDetailBean.f338e = c0608d.f447c.m91h();
            crashDetailBean.f339f = c0608d.f447c.f252j;
            crashDetailBean.f340g = c0608d.f447c.m102r();
            crashDetailBean.f346m = c0608d.f447c.m89g();
            crashDetailBean.f347n = str;
            crashDetailBean.f348o = str2;
            str5 = "";
            if (str3 != null) {
                String[] strArrSplit = str3.split("\n");
                str5 = strArrSplit.length > 0 ? strArrSplit[0] : "";
                str6 = str3;
            } else {
                str6 = "";
            }
            crashDetailBean.f349p = str5;
            crashDetailBean.f350q = str6;
            crashDetailBean.f351r = System.currentTimeMillis();
            crashDetailBean.f354u = C0659z.m499a(crashDetailBean.f350q.getBytes());
            crashDetailBean.f359z = C0659z.m502a(C0607c.f416f, false);
            crashDetailBean.f310A = c0608d.f447c.f246d;
            crashDetailBean.f311B = threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
            crashDetailBean.f318I = c0608d.f447c.m104t();
            crashDetailBean.f341h = c0608d.f447c.m101q();
            crashDetailBean.f322M = c0608d.f447c.f228a;
            crashDetailBean.f323N = c0608d.f447c.m75a();
            if (!C0607c.m204a().m229p()) {
                c0608d.f448d.m202d(crashDetailBean);
            }
            crashDetailBean.f326Q = c0608d.f447c.m66A();
            crashDetailBean.f327R = c0608d.f447c.m67B();
            crashDetailBean.f328S = c0608d.f447c.m105u();
            crashDetailBean.f329T = c0608d.f447c.m110z();
            crashDetailBean.f358y = C0658y.m474a();
            if (crashDetailBean.f324O == null) {
                crashDetailBean.f324O = new LinkedHashMap();
            }
            if (map != null) {
                crashDetailBean.f324O.putAll(map);
            }
            C0606b.m188a(str4, C0659z.m492a(), c0608d.f447c.f246d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, crashDetailBean);
            if (!c0608d.f448d.m199a(crashDetailBean)) {
                c0608d.f448d.m197a(crashDetailBean, 3000L, false);
            }
            C0657x.m468e("[ExtraCrashManager] Successfully handled.", new Object[0]);
        } catch (Throwable th) {
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                C0657x.m468e("[ExtraCrashManager] Successfully handled.", new Object[0]);
            } catch (Throwable th2) {
                C0657x.m468e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                throw th2;
            }
        }
    }

    private C0608d(Context context) {
        C0607c c0607cM204a = C0607c.m204a();
        if (c0607cM204a == null) {
            return;
        }
        this.f446b = C0596a.m139a();
        this.f447c = C0593a.m64a(context);
        this.f448d = c0607cM204a.f427p;
        this.f449e = context;
        C0656w.m453a().m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.d.1
            @Override // java.lang.Runnable
            public final void run() {
                C0608d.m237a(C0608d.this);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public static C0608d m236a(Context context) {
        if (f445a == null) {
            f445a = new C0608d(context);
        }
        return f445a;
    }

    /* JADX INFO: renamed from: a */
    public static void m239a(final Thread thread, final int i, final String str, final String str2, final String str3, final Map<String, String> map) {
        C0656w.m453a().m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.d.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (C0608d.f445a != null) {
                        C0608d.m238a(C0608d.f445a, thread, i, str, str2, str3, map);
                    } else {
                        C0657x.m468e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    }
                } catch (Throwable th) {
                    if (!C0657x.m465b(th)) {
                        th.printStackTrace();
                    }
                    C0657x.m468e("[ExtraCrashManager] Crash error %s %s %s", str, str2, str3);
                }
            }
        });
    }
}
