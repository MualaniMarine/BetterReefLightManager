package com.tencent.bugly.proguard;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.ah */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0624ah extends AbstractC0644k implements Cloneable {

    /* JADX INFO: renamed from: a */
    public String f523a = "";

    /* JADX INFO: renamed from: d */
    private String f526d = "";

    /* JADX INFO: renamed from: b */
    public String f524b = "";

    /* JADX INFO: renamed from: e */
    private String f527e = "";

    /* JADX INFO: renamed from: c */
    public String f525c = "";

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m368a(this.f523a, 0);
        String str = this.f526d;
        if (str != null) {
            c0643j.m368a(str, 1);
        }
        String str2 = this.f524b;
        if (str2 != null) {
            c0643j.m368a(str2, 2);
        }
        String str3 = this.f527e;
        if (str3 != null) {
            c0643j.m368a(str3, 3);
        }
        String str4 = this.f525c;
        if (str4 != null) {
            c0643j.m368a(str4, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f523a = c0642i.m357b(0, true);
        this.f526d = c0642i.m357b(1, false);
        this.f524b = c0642i.m357b(2, false);
        this.f527e = c0642i.m357b(3, false);
        this.f525c = c0642i.m357b(4, false);
    }
}
