package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.ar */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0634ar extends AbstractC0644k implements Cloneable {

    /* JADX INFO: renamed from: f */
    private static ArrayList<C0633aq> f628f;

    /* JADX INFO: renamed from: g */
    private static Map<String, String> f629g;

    /* JADX INFO: renamed from: a */
    public byte f630a = 0;

    /* JADX INFO: renamed from: b */
    public String f631b = "";

    /* JADX INFO: renamed from: c */
    public String f632c = "";

    /* JADX INFO: renamed from: d */
    public ArrayList<C0633aq> f633d = null;

    /* JADX INFO: renamed from: e */
    public Map<String, String> f634e = null;

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m363a(this.f630a, 0);
        String str = this.f631b;
        if (str != null) {
            c0643j.m368a(str, 1);
        }
        String str2 = this.f632c;
        if (str2 != null) {
            c0643j.m368a(str2, 2);
        }
        ArrayList<C0633aq> arrayList = this.f633d;
        if (arrayList != null) {
            c0643j.m369a((Collection) arrayList, 3);
        }
        Map<String, String> map = this.f634e;
        if (map != null) {
            c0643j.m370a((Map) map, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f630a = c0642i.m347a(this.f630a, 0, true);
        this.f631b = c0642i.m357b(1, false);
        this.f632c = c0642i.m357b(2, false);
        if (f628f == null) {
            f628f = new ArrayList<>();
            f628f.add(new C0633aq());
        }
        this.f633d = (ArrayList) c0642i.m352a(f628f, 3, false);
        if (f629g == null) {
            HashMap map = new HashMap();
            f629g = map;
            map.put("", "");
        }
        this.f634e = (Map) c0642i.m352a(f629g, 4, false);
    }
}
