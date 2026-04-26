package com.tencent.bugly.crashreport.common.strategy;

import android.content.Context;
import com.tencent.bugly.AbstractC0584a;
import com.tencent.bugly.crashreport.biz.C0591b;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.proguard.C0632ap;
import com.tencent.bugly.proguard.C0649p;
import com.tencent.bugly.proguard.C0651r;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import com.tencent.bugly.proguard.InterfaceC0648o;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.common.strategy.a */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0596a {

    /* JADX INFO: renamed from: a */
    public static int f294a = 1000;

    /* JADX INFO: renamed from: b */
    private static C0596a f295b;

    /* JADX INFO: renamed from: h */
    private static String f296h;

    /* JADX INFO: renamed from: c */
    private final List<AbstractC0584a> f297c;

    /* JADX INFO: renamed from: d */
    private final C0656w f298d;

    /* JADX INFO: renamed from: e */
    private final StrategyBean f299e;

    /* JADX INFO: renamed from: f */
    private StrategyBean f300f = null;

    /* JADX INFO: renamed from: g */
    private Context f301g;

    private C0596a(Context context, List<AbstractC0584a> list) {
        String str;
        this.f301g = context;
        if (C0593a.m64a(context) != null) {
            String str2 = C0593a.m64a(context).f267y;
            if (!"oversea".equals(str2)) {
                str = "na_https".equals(str2) ? "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async" : "https://astat.bugly.qcloud.com/rqd/async";
            }
            StrategyBean.f272a = str;
            StrategyBean.f273b = str;
        }
        this.f299e = new StrategyBean();
        this.f297c = list;
        this.f298d = C0656w.m453a();
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0596a m140a(Context context, List<AbstractC0584a> list) {
        if (f295b == null) {
            f295b = new C0596a(context, list);
        }
        return f295b;
    }

    /* JADX INFO: renamed from: a */
    public final void m145a(long j) {
        this.f298d.m456a(new Thread() { // from class: com.tencent.bugly.crashreport.common.strategy.a.1
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                try {
                    Map<String, byte[]> mapM420a = C0649p.m402a().m420a(C0596a.f294a, (InterfaceC0648o) null, true);
                    if (mapM420a != null) {
                        byte[] bArr = mapM420a.get("device");
                        byte[] bArr2 = mapM420a.get("gateway");
                        if (bArr != null) {
                            C0593a.m64a(C0596a.this.f301g).m88f(new String(bArr));
                        }
                        if (bArr2 != null) {
                            C0593a.m64a(C0596a.this.f301g).m86e(new String(bArr2));
                        }
                    }
                    C0596a.this.f300f = C0596a.m143d();
                    if (C0596a.this.f300f != null) {
                        if (C0659z.m509a(C0596a.f296h) || !C0659z.m524c(C0596a.f296h)) {
                            C0596a.this.f300f.f287p = StrategyBean.f272a;
                            C0596a.this.f300f.f288q = StrategyBean.f273b;
                        } else {
                            C0596a.this.f300f.f287p = C0596a.f296h;
                            C0596a.this.f300f.f288q = C0596a.f296h;
                        }
                    }
                } catch (Throwable th) {
                    if (!C0657x.m462a(th)) {
                        th.printStackTrace();
                    }
                }
                C0596a c0596a = C0596a.this;
                c0596a.m146a(c0596a.f300f, false);
            }
        }, j);
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0596a m139a() {
        return f295b;
    }

    /* JADX INFO: renamed from: b */
    public final synchronized boolean m148b() {
        return this.f300f != null;
    }

    /* JADX INFO: renamed from: c */
    public final StrategyBean m149c() {
        StrategyBean strategyBean = this.f300f;
        if (strategyBean != null) {
            if (!C0659z.m524c(strategyBean.f287p)) {
                this.f300f.f287p = StrategyBean.f272a;
            }
            if (!C0659z.m524c(this.f300f.f288q)) {
                this.f300f.f288q = StrategyBean.f273b;
            }
            return this.f300f;
        }
        if (!C0659z.m509a(f296h) && C0659z.m524c(f296h)) {
            this.f299e.f287p = f296h;
            this.f299e.f288q = f296h;
        }
        return this.f299e;
    }

    /* JADX INFO: renamed from: a */
    protected final void m146a(StrategyBean strategyBean, boolean z) {
        C0657x.m466c("[Strategy] Notify %s", C0591b.class.getName());
        C0591b.m39a(strategyBean, z);
        for (AbstractC0584a abstractC0584a : this.f297c) {
            try {
                C0657x.m466c("[Strategy] Notify %s", abstractC0584a.getClass().getName());
                abstractC0584a.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m141a(String str) {
        if (C0659z.m509a(str) || !C0659z.m524c(str)) {
            C0657x.m467d("URL user set is invalid.", new Object[0]);
        } else {
            f296h = str;
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m147a(C0632ap c0632ap) {
        if (c0632ap == null) {
            return;
        }
        if (this.f300f == null || c0632ap.f614h != this.f300f.f285n) {
            StrategyBean strategyBean = new StrategyBean();
            strategyBean.f276e = c0632ap.f607a;
            strategyBean.f278g = c0632ap.f609c;
            strategyBean.f277f = c0632ap.f608b;
            if (C0659z.m509a(f296h) || !C0659z.m524c(f296h)) {
                if (C0659z.m524c(c0632ap.f610d)) {
                    C0657x.m466c("[Strategy] Upload url changes to %s", c0632ap.f610d);
                    strategyBean.f287p = c0632ap.f610d;
                }
                if (C0659z.m524c(c0632ap.f611e)) {
                    C0657x.m466c("[Strategy] Exception upload url changes to %s", c0632ap.f611e);
                    strategyBean.f288q = c0632ap.f611e;
                }
            }
            if (c0632ap.f612f != null && !C0659z.m509a(c0632ap.f612f.f602a)) {
                strategyBean.f289r = c0632ap.f612f.f602a;
            }
            if (c0632ap.f614h != 0) {
                strategyBean.f285n = c0632ap.f614h;
            }
            if (c0632ap.f613g != null && c0632ap.f613g.size() > 0) {
                strategyBean.f290s = c0632ap.f613g;
                String str = c0632ap.f613g.get("B11");
                if (str != null && str.equals("1")) {
                    strategyBean.f279h = true;
                } else {
                    strategyBean.f279h = false;
                }
                String str2 = c0632ap.f613g.get("B3");
                if (str2 != null) {
                    strategyBean.f293v = Long.valueOf(str2).longValue();
                }
                strategyBean.f286o = c0632ap.f615i;
                strategyBean.f292u = c0632ap.f615i;
                String str3 = c0632ap.f613g.get("B27");
                if (str3 != null && str3.length() > 0) {
                    try {
                        int i = Integer.parseInt(str3);
                        if (i > 0) {
                            strategyBean.f291t = i;
                        }
                    } catch (Exception e) {
                        if (!C0657x.m462a(e)) {
                            e.printStackTrace();
                        }
                    }
                }
                String str4 = c0632ap.f613g.get("B25");
                if (str4 != null && str4.equals("1")) {
                    strategyBean.f281j = true;
                } else {
                    strategyBean.f281j = false;
                }
            }
            C0657x.m461a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean.f276e), Boolean.valueOf(strategyBean.f278g), Boolean.valueOf(strategyBean.f277f), Boolean.valueOf(strategyBean.f279h), Boolean.valueOf(strategyBean.f280i), Boolean.valueOf(strategyBean.f283l), Boolean.valueOf(strategyBean.f284m), Long.valueOf(strategyBean.f286o), Boolean.valueOf(strategyBean.f281j), Long.valueOf(strategyBean.f285n));
            this.f300f = strategyBean;
            if (!C0659z.m524c(c0632ap.f610d)) {
                C0657x.m466c("[Strategy] download url is null", new Object[0]);
                this.f300f.f287p = "";
            }
            if (!C0659z.m524c(c0632ap.f611e)) {
                C0657x.m466c("[Strategy] download crashurl is null", new Object[0]);
                this.f300f.f288q = "";
            }
            C0649p.m402a().m424b(2);
            C0651r c0651r = new C0651r();
            c0651r.f707b = 2;
            c0651r.f706a = strategyBean.f274c;
            c0651r.f710e = strategyBean.f275d;
            c0651r.f712g = C0659z.m510a(strategyBean);
            C0649p.m402a().m423a(c0651r);
            m146a(strategyBean, true);
        }
    }

    /* JADX INFO: renamed from: d */
    public static StrategyBean m143d() {
        List<C0651r> listM419a = C0649p.m402a().m419a(2);
        if (listM419a == null || listM419a.size() <= 0) {
            return null;
        }
        C0651r c0651r = listM419a.get(0);
        if (c0651r.f712g != null) {
            return (StrategyBean) C0659z.m491a(c0651r.f712g, StrategyBean.CREATOR);
        }
        return null;
    }
}
