package com.tencent.bugly.proguard;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.ao */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0631ao extends AbstractC0644k implements Cloneable {

    /* JADX INFO: renamed from: a */
    public String f602a = "";

    /* JADX INFO: renamed from: b */
    private String f603b = "";

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m368a(this.f602a, 0);
        c0643j.m368a(this.f603b, 1);
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f602a = c0642i.m357b(0, true);
        this.f603b = c0642i.m357b(1, true);
    }
}
