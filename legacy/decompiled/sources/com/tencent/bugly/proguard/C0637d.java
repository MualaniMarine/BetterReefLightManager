package com.tencent.bugly.proguard;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.d */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0637d extends C0636c {

    /* JADX INFO: renamed from: f */
    private static HashMap<String, byte[]> f638f;

    /* JADX INFO: renamed from: g */
    private static HashMap<String, HashMap<String, byte[]>> f639g;

    /* JADX INFO: renamed from: e */
    private C0639f f640e;

    public C0637d() {
        C0639f c0639f = new C0639f();
        this.f640e = c0639f;
        c0639f.f645a = (short) 2;
    }

    @Override // com.tencent.bugly.proguard.C0636c, com.tencent.bugly.proguard.C0616a
    /* JADX INFO: renamed from: a */
    public final <T> void mo290a(String str, T t) {
        if (str.startsWith(".")) {
            throw new IllegalArgumentException("put name can not startwith . , now is " + str);
        }
        super.mo290a(str, t);
    }

    @Override // com.tencent.bugly.proguard.C0636c
    /* JADX INFO: renamed from: c */
    public final void mo315c() {
        super.mo315c();
        this.f640e.f645a = (short) 3;
    }

    @Override // com.tencent.bugly.proguard.C0636c, com.tencent.bugly.proguard.C0616a
    /* JADX INFO: renamed from: a */
    public final byte[] mo292a() {
        if (this.f640e.f645a == 2) {
            if (this.f640e.f647c.equals("")) {
                throw new IllegalArgumentException("servantName can not is null");
            }
            if (this.f640e.f648d.equals("")) {
                throw new IllegalArgumentException("funcName can not is null");
            }
        } else {
            if (this.f640e.f647c == null) {
                this.f640e.f647c = "";
            }
            if (this.f640e.f648d == null) {
                this.f640e.f648d = "";
            }
        }
        C0643j c0643j = new C0643j(0);
        c0643j.m361a(this.f509b);
        if (this.f640e.f645a == 2) {
            c0643j.m370a((Map) this.f508a, 0);
        } else {
            c0643j.m370a((Map) this.f635d, 0);
        }
        this.f640e.f649e = C0645l.m379a(c0643j.m362a());
        C0643j c0643j2 = new C0643j(0);
        c0643j2.m361a(this.f509b);
        this.f640e.mo312a(c0643j2);
        byte[] bArrM379a = C0645l.m379a(c0643j2.m362a());
        int length = bArrM379a.length + 4;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.putInt(length).put(bArrM379a).flip();
        return byteBufferAllocate.array();
    }

    @Override // com.tencent.bugly.proguard.C0636c, com.tencent.bugly.proguard.C0616a
    /* JADX INFO: renamed from: a */
    public final void mo291a(byte[] bArr) {
        if (bArr.length < 4) {
            throw new IllegalArgumentException("decode package must include size head");
        }
        try {
            C0642i c0642i = new C0642i(bArr, 4);
            c0642i.m349a(this.f509b);
            this.f640e.mo311a(c0642i);
            if (this.f640e.f645a == 3) {
                C0642i c0642i2 = new C0642i(this.f640e.f649e);
                c0642i2.m349a(this.f509b);
                if (f638f == null) {
                    HashMap<String, byte[]> map = new HashMap<>();
                    f638f = map;
                    map.put("", new byte[0]);
                }
                this.f635d = c0642i2.m353a((Map) f638f, 0, false);
                return;
            }
            C0642i c0642i3 = new C0642i(this.f640e.f649e);
            c0642i3.m349a(this.f509b);
            if (f639g == null) {
                f639g = new HashMap<>();
                HashMap<String, byte[]> map2 = new HashMap<>();
                map2.put("", new byte[0]);
                f639g.put("", map2);
            }
            this.f508a = c0642i3.m353a((Map) f639g, 0, false);
            new HashMap();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: renamed from: b */
    public final void m317b(String str) {
        this.f640e.f647c = str;
    }

    /* JADX INFO: renamed from: c */
    public final void m318c(String str) {
        this.f640e.f648d = str;
    }

    /* JADX INFO: renamed from: a */
    public final void m316a(int i) {
        this.f640e.f646b = 1;
    }
}
