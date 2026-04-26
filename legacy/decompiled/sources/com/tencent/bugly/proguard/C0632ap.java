package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.ap */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0632ap extends AbstractC0644k implements Cloneable {

    /* JADX INFO: renamed from: n */
    private static Map<String, String> f605n;

    /* JADX INFO: renamed from: o */
    private static /* synthetic */ boolean f606o = !C0632ap.class.desiredAssertionStatus();

    /* JADX INFO: renamed from: m */
    private static C0631ao f604m = new C0631ao();

    /* JADX INFO: renamed from: a */
    public boolean f607a = true;

    /* JADX INFO: renamed from: b */
    public boolean f608b = true;

    /* JADX INFO: renamed from: c */
    public boolean f609c = true;

    /* JADX INFO: renamed from: d */
    public String f610d = "";

    /* JADX INFO: renamed from: e */
    public String f611e = "";

    /* JADX INFO: renamed from: f */
    public C0631ao f612f = null;

    /* JADX INFO: renamed from: g */
    public Map<String, String> f613g = null;

    /* JADX INFO: renamed from: h */
    public long f614h = 0;

    /* JADX INFO: renamed from: j */
    private String f616j = "";

    /* JADX INFO: renamed from: k */
    private String f617k = "";

    /* JADX INFO: renamed from: l */
    private int f618l = 0;

    /* JADX INFO: renamed from: i */
    public int f615i = 0;

    static {
        HashMap map = new HashMap();
        f605n = map;
        map.put("", "");
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        C0632ap c0632ap = (C0632ap) obj;
        return C0645l.m378a(this.f607a, c0632ap.f607a) && C0645l.m378a(this.f608b, c0632ap.f608b) && C0645l.m378a(this.f609c, c0632ap.f609c) && C0645l.m377a(this.f610d, c0632ap.f610d) && C0645l.m377a(this.f611e, c0632ap.f611e) && C0645l.m377a(this.f612f, c0632ap.f612f) && C0645l.m377a(this.f613g, c0632ap.f613g) && C0645l.m376a(this.f614h, c0632ap.f614h) && C0645l.m377a(this.f616j, c0632ap.f616j) && C0645l.m377a(this.f617k, c0632ap.f617k) && C0645l.m375a(this.f618l, c0632ap.f618l) && C0645l.m375a(this.f615i, c0632ap.f615i);
    }

    public final int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f606o) {
                return null;
            }
            throw new AssertionError();
        }
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m372a(this.f607a, 0);
        c0643j.m372a(this.f608b, 1);
        c0643j.m372a(this.f609c, 2);
        String str = this.f610d;
        if (str != null) {
            c0643j.m368a(str, 3);
        }
        String str2 = this.f611e;
        if (str2 != null) {
            c0643j.m368a(str2, 4);
        }
        C0631ao c0631ao = this.f612f;
        if (c0631ao != null) {
            c0643j.m366a((AbstractC0644k) c0631ao, 5);
        }
        Map<String, String> map = this.f613g;
        if (map != null) {
            c0643j.m370a((Map) map, 6);
        }
        c0643j.m365a(this.f614h, 7);
        String str3 = this.f616j;
        if (str3 != null) {
            c0643j.m368a(str3, 8);
        }
        String str4 = this.f617k;
        if (str4 != null) {
            c0643j.m368a(str4, 9);
        }
        c0643j.m364a(this.f618l, 10);
        c0643j.m364a(this.f615i, 11);
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f607a = c0642i.m356a(0, true);
        this.f608b = c0642i.m356a(1, true);
        this.f609c = c0642i.m356a(2, true);
        this.f610d = c0642i.m357b(3, false);
        this.f611e = c0642i.m357b(4, false);
        this.f612f = (C0631ao) c0642i.m351a((AbstractC0644k) f604m, 5, false);
        this.f613g = (Map) c0642i.m352a(f605n, 6, false);
        this.f614h = c0642i.m350a(this.f614h, 7, false);
        this.f616j = c0642i.m357b(8, false);
        this.f617k = c0642i.m357b(9, false);
        this.f618l = c0642i.m348a(this.f618l, 10, false);
        this.f615i = c0642i.m348a(this.f615i, 11, false);
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
        C0641h c0641h = new C0641h(sb, i);
        c0641h.m330a(this.f607a, "enable");
        c0641h.m330a(this.f608b, "enableUserInfo");
        c0641h.m330a(this.f609c, "enableQuery");
        c0641h.m327a(this.f610d, "url");
        c0641h.m327a(this.f611e, "expUrl");
        c0641h.m326a((AbstractC0644k) this.f612f, "security");
        c0641h.m328a((Map) this.f613g, "valueMap");
        c0641h.m325a(this.f614h, "strategylastUpdateTime");
        c0641h.m327a(this.f616j, "httpsUrl");
        c0641h.m327a(this.f617k, "httpsExpUrl");
        c0641h.m324a(this.f618l, "eventRecordCount");
        c0641h.m324a(this.f615i, "eventTimeInterval");
    }
}
