package com.tencent.bugly.proguard;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.aj */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0626aj extends AbstractC0644k implements Cloneable {

    /* JADX INFO: renamed from: d */
    private static byte[] f531d;

    /* JADX INFO: renamed from: a */
    private byte f532a;

    /* JADX INFO: renamed from: b */
    private String f533b;

    /* JADX INFO: renamed from: c */
    private byte[] f534c;

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
    }

    public C0626aj() {
        this.f532a = (byte) 0;
        this.f533b = "";
        this.f534c = null;
    }

    public C0626aj(byte b, String str, byte[] bArr) {
        this.f532a = (byte) 0;
        this.f533b = "";
        this.f534c = null;
        this.f532a = b;
        this.f533b = str;
        this.f534c = bArr;
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m363a(this.f532a, 0);
        c0643j.m368a(this.f533b, 1);
        byte[] bArr = this.f534c;
        if (bArr != null) {
            c0643j.m373a(bArr, 2);
        }
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        this.f532a = c0642i.m347a(this.f532a, 0, true);
        this.f533b = c0642i.m357b(1, true);
        if (f531d == null) {
            f531d = new byte[]{0};
        }
        this.f534c = c0642i.m358c(2, false);
    }
}
