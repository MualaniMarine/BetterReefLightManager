package com.tencent.bugly.proguard;

import java.io.Serializable;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.k */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0644k implements Serializable {
    /* JADX INFO: renamed from: a */
    public abstract void mo311a(C0642i c0642i);

    /* JADX INFO: renamed from: a */
    public abstract void mo312a(C0643j c0643j);

    /* JADX INFO: renamed from: a */
    public abstract void mo313a(StringBuilder sb, int i);

    public String toString() {
        StringBuilder sb = new StringBuilder();
        mo313a(sb, 0);
        return sb.toString();
    }
}
