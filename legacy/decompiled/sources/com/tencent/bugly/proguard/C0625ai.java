package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.ai */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0625ai extends AbstractC0644k implements Cloneable {

    /* JADX INFO: renamed from: c */
    private static ArrayList<String> f528c;

    /* JADX INFO: renamed from: a */
    private String f529a = "";

    /* JADX INFO: renamed from: b */
    private ArrayList<String> f530b = null;

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m368a(this.f529a, 0);
        ArrayList<String> arrayList = this.f530b;
        if (arrayList != null) {
            c0643j.m369a((Collection) arrayList, 1);
        }
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f529a = c0642i.m357b(0, true);
        if (f528c == null) {
            ArrayList<String> arrayList = new ArrayList<>();
            f528c = arrayList;
            arrayList.add("");
        }
        this.f530b = (ArrayList) c0642i.m352a(f528c, 1, false);
    }
}
