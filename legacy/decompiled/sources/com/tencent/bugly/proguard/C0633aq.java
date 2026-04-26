package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.aq */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0633aq extends AbstractC0644k {

    /* JADX INFO: renamed from: i */
    private static Map<String, String> f619i;

    /* JADX INFO: renamed from: a */
    public long f620a = 0;

    /* JADX INFO: renamed from: b */
    public byte f621b = 0;

    /* JADX INFO: renamed from: c */
    public String f622c = "";

    /* JADX INFO: renamed from: d */
    public String f623d = "";

    /* JADX INFO: renamed from: e */
    public String f624e = "";

    /* JADX INFO: renamed from: f */
    public Map<String, String> f625f = null;

    /* JADX INFO: renamed from: h */
    private String f627h = "";

    /* JADX INFO: renamed from: g */
    public boolean f626g = true;

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m365a(this.f620a, 0);
        c0643j.m363a(this.f621b, 1);
        String str = this.f622c;
        if (str != null) {
            c0643j.m368a(str, 2);
        }
        String str2 = this.f623d;
        if (str2 != null) {
            c0643j.m368a(str2, 3);
        }
        String str3 = this.f624e;
        if (str3 != null) {
            c0643j.m368a(str3, 4);
        }
        Map<String, String> map = this.f625f;
        if (map != null) {
            c0643j.m370a((Map) map, 5);
        }
        String str4 = this.f627h;
        if (str4 != null) {
            c0643j.m368a(str4, 6);
        }
        c0643j.m372a(this.f626g, 7);
    }

    static {
        HashMap map = new HashMap();
        f619i = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f620a = c0642i.m350a(this.f620a, 0, true);
        this.f621b = c0642i.m347a(this.f621b, 1, true);
        this.f622c = c0642i.m357b(2, false);
        this.f623d = c0642i.m357b(3, false);
        this.f624e = c0642i.m357b(4, false);
        this.f625f = (Map) c0642i.m352a(f619i, 5, false);
        this.f627h = c0642i.m357b(6, false);
        this.f626g = c0642i.m356a(7, false);
    }
}
