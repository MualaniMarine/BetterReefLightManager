package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.am */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0629am extends AbstractC0644k {

    /* JADX INFO: renamed from: y */
    private static byte[] f566y = {0};

    /* JADX INFO: renamed from: z */
    private static Map<String, String> f567z;

    /* JADX INFO: renamed from: a */
    public int f568a = 0;

    /* JADX INFO: renamed from: b */
    public String f569b = "";

    /* JADX INFO: renamed from: c */
    public String f570c = "";

    /* JADX INFO: renamed from: d */
    public String f571d = "";

    /* JADX INFO: renamed from: e */
    public String f572e = "";

    /* JADX INFO: renamed from: f */
    public String f573f = "";

    /* JADX INFO: renamed from: g */
    public int f574g = 0;

    /* JADX INFO: renamed from: h */
    public byte[] f575h = null;

    /* JADX INFO: renamed from: i */
    public String f576i = "";

    /* JADX INFO: renamed from: j */
    public String f577j = "";

    /* JADX INFO: renamed from: k */
    public Map<String, String> f578k = null;

    /* JADX INFO: renamed from: l */
    public String f579l = "";

    /* JADX INFO: renamed from: m */
    public long f580m = 0;

    /* JADX INFO: renamed from: n */
    public String f581n = "";

    /* JADX INFO: renamed from: o */
    public String f582o = "";

    /* JADX INFO: renamed from: p */
    public String f583p = "";

    /* JADX INFO: renamed from: q */
    public long f584q = 0;

    /* JADX INFO: renamed from: u */
    private String f588u = "";

    /* JADX INFO: renamed from: r */
    public String f585r = "";

    /* JADX INFO: renamed from: v */
    private String f589v = "";

    /* JADX INFO: renamed from: w */
    private String f590w = "";

    /* JADX INFO: renamed from: s */
    public String f586s = "";

    /* JADX INFO: renamed from: t */
    public String f587t = "";

    /* JADX INFO: renamed from: x */
    private String f591x = "";

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m364a(this.f568a, 0);
        c0643j.m368a(this.f569b, 1);
        c0643j.m368a(this.f570c, 2);
        c0643j.m368a(this.f571d, 3);
        String str = this.f572e;
        if (str != null) {
            c0643j.m368a(str, 4);
        }
        c0643j.m368a(this.f573f, 5);
        c0643j.m364a(this.f574g, 6);
        c0643j.m373a(this.f575h, 7);
        String str2 = this.f576i;
        if (str2 != null) {
            c0643j.m368a(str2, 8);
        }
        String str3 = this.f577j;
        if (str3 != null) {
            c0643j.m368a(str3, 9);
        }
        Map<String, String> map = this.f578k;
        if (map != null) {
            c0643j.m370a((Map) map, 10);
        }
        String str4 = this.f579l;
        if (str4 != null) {
            c0643j.m368a(str4, 11);
        }
        c0643j.m365a(this.f580m, 12);
        String str5 = this.f581n;
        if (str5 != null) {
            c0643j.m368a(str5, 13);
        }
        String str6 = this.f582o;
        if (str6 != null) {
            c0643j.m368a(str6, 14);
        }
        String str7 = this.f583p;
        if (str7 != null) {
            c0643j.m368a(str7, 15);
        }
        c0643j.m365a(this.f584q, 16);
        String str8 = this.f588u;
        if (str8 != null) {
            c0643j.m368a(str8, 17);
        }
        String str9 = this.f585r;
        if (str9 != null) {
            c0643j.m368a(str9, 18);
        }
        String str10 = this.f589v;
        if (str10 != null) {
            c0643j.m368a(str10, 19);
        }
        String str11 = this.f590w;
        if (str11 != null) {
            c0643j.m368a(str11, 20);
        }
        String str12 = this.f586s;
        if (str12 != null) {
            c0643j.m368a(str12, 21);
        }
        String str13 = this.f587t;
        if (str13 != null) {
            c0643j.m368a(str13, 22);
        }
        String str14 = this.f591x;
        if (str14 != null) {
            c0643j.m368a(str14, 23);
        }
    }

    static {
        HashMap map = new HashMap();
        f567z = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f568a = c0642i.m348a(this.f568a, 0, true);
        this.f569b = c0642i.m357b(1, true);
        this.f570c = c0642i.m357b(2, true);
        this.f571d = c0642i.m357b(3, true);
        this.f572e = c0642i.m357b(4, false);
        this.f573f = c0642i.m357b(5, true);
        this.f574g = c0642i.m348a(this.f574g, 6, true);
        this.f575h = c0642i.m358c(7, true);
        this.f576i = c0642i.m357b(8, false);
        this.f577j = c0642i.m357b(9, false);
        this.f578k = (Map) c0642i.m352a(f567z, 10, false);
        this.f579l = c0642i.m357b(11, false);
        this.f580m = c0642i.m350a(this.f580m, 12, false);
        this.f581n = c0642i.m357b(13, false);
        this.f582o = c0642i.m357b(14, false);
        this.f583p = c0642i.m357b(15, false);
        this.f584q = c0642i.m350a(this.f584q, 16, false);
        this.f588u = c0642i.m357b(17, false);
        this.f585r = c0642i.m357b(18, false);
        this.f589v = c0642i.m357b(19, false);
        this.f590w = c0642i.m357b(20, false);
        this.f586s = c0642i.m357b(21, false);
        this.f587t = c0642i.m357b(22, false);
        this.f591x = c0642i.m357b(23, false);
    }
}
