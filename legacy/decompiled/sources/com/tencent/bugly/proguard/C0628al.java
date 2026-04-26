package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.al */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0628al extends AbstractC0644k implements Cloneable {

    /* JADX INFO: renamed from: b */
    private static ArrayList<C0627ak> f564b;

    /* JADX INFO: renamed from: a */
    public ArrayList<C0627ak> f565a = null;

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m369a((Collection) this.f565a, 0);
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        if (f564b == null) {
            f564b = new ArrayList<>();
            f564b.add(new C0627ak());
        }
        this.f565a = (ArrayList) c0642i.m352a(f564b, 0, true);
    }
}
