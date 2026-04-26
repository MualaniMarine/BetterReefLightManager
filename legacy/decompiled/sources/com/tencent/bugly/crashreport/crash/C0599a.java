package com.tencent.bugly.crashreport.crash;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.a */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0599a implements Comparable<C0599a> {

    /* JADX INFO: renamed from: a */
    public long f360a = -1;

    /* JADX INFO: renamed from: b */
    public long f361b = -1;

    /* JADX INFO: renamed from: c */
    public String f362c = null;

    /* JADX INFO: renamed from: d */
    public boolean f363d = false;

    /* JADX INFO: renamed from: e */
    public boolean f364e = false;

    /* JADX INFO: renamed from: f */
    public int f365f = 0;

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(C0599a c0599a) {
        C0599a c0599a2 = c0599a;
        if (c0599a2 == null) {
            return 1;
        }
        long j = this.f361b - c0599a2.f361b;
        if (j <= 0) {
            return j < 0 ? -1 : 0;
        }
        return 1;
    }
}
