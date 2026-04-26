package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.an */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0630an extends AbstractC0644k {

    /* JADX INFO: renamed from: i */
    private static byte[] f592i = {0};

    /* JADX INFO: renamed from: j */
    private static Map<String, String> f593j;

    /* JADX INFO: renamed from: a */
    public byte f594a = 0;

    /* JADX INFO: renamed from: b */
    public int f595b = 0;

    /* JADX INFO: renamed from: c */
    public byte[] f596c = null;

    /* JADX INFO: renamed from: f */
    private String f599f = "";

    /* JADX INFO: renamed from: d */
    public long f597d = 0;

    /* JADX INFO: renamed from: g */
    private String f600g = "";

    /* JADX INFO: renamed from: e */
    public String f598e = "";

    /* JADX INFO: renamed from: h */
    private Map<String, String> f601h = null;

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m363a(this.f594a, 0);
        c0643j.m364a(this.f595b, 1);
        byte[] bArr = this.f596c;
        if (bArr != null) {
            c0643j.m373a(bArr, 2);
        }
        String str = this.f599f;
        if (str != null) {
            c0643j.m368a(str, 3);
        }
        c0643j.m365a(this.f597d, 4);
        String str2 = this.f600g;
        if (str2 != null) {
            c0643j.m368a(str2, 5);
        }
        String str3 = this.f598e;
        if (str3 != null) {
            c0643j.m368a(str3, 6);
        }
        Map<String, String> map = this.f601h;
        if (map != null) {
            c0643j.m370a((Map) map, 7);
        }
    }

    static {
        HashMap map = new HashMap();
        f593j = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f594a = c0642i.m347a(this.f594a, 0, true);
        this.f595b = c0642i.m348a(this.f595b, 1, true);
        this.f596c = c0642i.m358c(2, false);
        this.f599f = c0642i.m357b(3, false);
        this.f597d = c0642i.m350a(this.f597d, 4, false);
        this.f600g = c0642i.m357b(5, false);
        this.f598e = c0642i.m357b(6, false);
        this.f601h = (Map) c0642i.m352a(f593j, 7, false);
    }
}
