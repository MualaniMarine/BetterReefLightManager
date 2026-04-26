package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.ak */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0627ak extends AbstractC0644k {

    /* JADX INFO: renamed from: A */
    private static ArrayList<C0626aj> f535A;

    /* JADX INFO: renamed from: B */
    private static Map<String, String> f536B;

    /* JADX INFO: renamed from: C */
    private static Map<String, String> f537C;

    /* JADX INFO: renamed from: v */
    private static Map<String, String> f538v;

    /* JADX INFO: renamed from: w */
    private static C0625ai f539w;

    /* JADX INFO: renamed from: x */
    private static C0624ah f540x;

    /* JADX INFO: renamed from: y */
    private static ArrayList<C0624ah> f541y;

    /* JADX INFO: renamed from: z */
    private static ArrayList<C0624ah> f542z;

    /* JADX INFO: renamed from: a */
    public String f543a = "";

    /* JADX INFO: renamed from: b */
    public long f544b = 0;

    /* JADX INFO: renamed from: c */
    public String f545c = "";

    /* JADX INFO: renamed from: d */
    public String f546d = "";

    /* JADX INFO: renamed from: e */
    public String f547e = "";

    /* JADX INFO: renamed from: f */
    public String f548f = "";

    /* JADX INFO: renamed from: g */
    public String f549g = "";

    /* JADX INFO: renamed from: h */
    public Map<String, String> f550h = null;

    /* JADX INFO: renamed from: i */
    public String f551i = "";

    /* JADX INFO: renamed from: j */
    public C0625ai f552j = null;

    /* JADX INFO: renamed from: k */
    public int f553k = 0;

    /* JADX INFO: renamed from: l */
    public String f554l = "";

    /* JADX INFO: renamed from: m */
    public String f555m = "";

    /* JADX INFO: renamed from: n */
    public C0624ah f556n = null;

    /* JADX INFO: renamed from: o */
    public ArrayList<C0624ah> f557o = null;

    /* JADX INFO: renamed from: p */
    public ArrayList<C0624ah> f558p = null;

    /* JADX INFO: renamed from: q */
    public ArrayList<C0626aj> f559q = null;

    /* JADX INFO: renamed from: r */
    public Map<String, String> f560r = null;

    /* JADX INFO: renamed from: s */
    public Map<String, String> f561s = null;

    /* JADX INFO: renamed from: t */
    private String f562t = "";

    /* JADX INFO: renamed from: u */
    private boolean f563u = true;

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m368a(this.f543a, 0);
        c0643j.m365a(this.f544b, 1);
        c0643j.m368a(this.f545c, 2);
        String str = this.f546d;
        if (str != null) {
            c0643j.m368a(str, 3);
        }
        String str2 = this.f547e;
        if (str2 != null) {
            c0643j.m368a(str2, 4);
        }
        String str3 = this.f548f;
        if (str3 != null) {
            c0643j.m368a(str3, 5);
        }
        String str4 = this.f549g;
        if (str4 != null) {
            c0643j.m368a(str4, 6);
        }
        Map<String, String> map = this.f550h;
        if (map != null) {
            c0643j.m370a((Map) map, 7);
        }
        String str5 = this.f551i;
        if (str5 != null) {
            c0643j.m368a(str5, 8);
        }
        C0625ai c0625ai = this.f552j;
        if (c0625ai != null) {
            c0643j.m366a((AbstractC0644k) c0625ai, 9);
        }
        c0643j.m364a(this.f553k, 10);
        String str6 = this.f554l;
        if (str6 != null) {
            c0643j.m368a(str6, 11);
        }
        String str7 = this.f555m;
        if (str7 != null) {
            c0643j.m368a(str7, 12);
        }
        C0624ah c0624ah = this.f556n;
        if (c0624ah != null) {
            c0643j.m366a((AbstractC0644k) c0624ah, 13);
        }
        ArrayList<C0624ah> arrayList = this.f557o;
        if (arrayList != null) {
            c0643j.m369a((Collection) arrayList, 14);
        }
        ArrayList<C0624ah> arrayList2 = this.f558p;
        if (arrayList2 != null) {
            c0643j.m369a((Collection) arrayList2, 15);
        }
        ArrayList<C0626aj> arrayList3 = this.f559q;
        if (arrayList3 != null) {
            c0643j.m369a((Collection) arrayList3, 16);
        }
        Map<String, String> map2 = this.f560r;
        if (map2 != null) {
            c0643j.m370a((Map) map2, 17);
        }
        Map<String, String> map3 = this.f561s;
        if (map3 != null) {
            c0643j.m370a((Map) map3, 18);
        }
        String str8 = this.f562t;
        if (str8 != null) {
            c0643j.m368a(str8, 19);
        }
        c0643j.m372a(this.f563u, 20);
    }

    static {
        HashMap map = new HashMap();
        f538v = map;
        map.put("", "");
        f539w = new C0625ai();
        f540x = new C0624ah();
        f541y = new ArrayList<>();
        f541y.add(new C0624ah());
        f542z = new ArrayList<>();
        f542z.add(new C0624ah());
        f535A = new ArrayList<>();
        f535A.add(new C0626aj());
        HashMap map2 = new HashMap();
        f536B = map2;
        map2.put("", "");
        HashMap map3 = new HashMap();
        f537C = map3;
        map3.put("", "");
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f543a = c0642i.m357b(0, true);
        this.f544b = c0642i.m350a(this.f544b, 1, true);
        this.f545c = c0642i.m357b(2, true);
        this.f546d = c0642i.m357b(3, false);
        this.f547e = c0642i.m357b(4, false);
        this.f548f = c0642i.m357b(5, false);
        this.f549g = c0642i.m357b(6, false);
        this.f550h = (Map) c0642i.m352a(f538v, 7, false);
        this.f551i = c0642i.m357b(8, false);
        this.f552j = (C0625ai) c0642i.m351a((AbstractC0644k) f539w, 9, false);
        this.f553k = c0642i.m348a(this.f553k, 10, false);
        this.f554l = c0642i.m357b(11, false);
        this.f555m = c0642i.m357b(12, false);
        this.f556n = (C0624ah) c0642i.m351a((AbstractC0644k) f540x, 13, false);
        this.f557o = (ArrayList) c0642i.m352a(f541y, 14, false);
        this.f558p = (ArrayList) c0642i.m352a(f542z, 15, false);
        this.f559q = (ArrayList) c0642i.m352a(f535A, 16, false);
        this.f560r = (Map) c0642i.m352a(f536B, 17, false);
        this.f561s = (Map) c0642i.m352a(f537C, 18, false);
        this.f562t = c0642i.m357b(19, false);
        this.f563u = c0642i.m356a(20, false);
    }
}
