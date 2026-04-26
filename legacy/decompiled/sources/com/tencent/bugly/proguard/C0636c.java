package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.c */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class C0636c extends C0616a {

    /* JADX INFO: renamed from: d */
    protected HashMap<String, byte[]> f635d = null;

    /* JADX INFO: renamed from: e */
    private HashMap<String, Object> f636e = new HashMap<>();

    /* JADX INFO: renamed from: f */
    private C0642i f637f = new C0642i();

    @Override // com.tencent.bugly.proguard.C0616a
    /* JADX INFO: renamed from: a */
    public final /* bridge */ /* synthetic */ void mo289a(String str) {
        super.mo289a(str);
    }

    /* JADX INFO: renamed from: c */
    public void mo315c() {
        this.f635d = new HashMap<>();
    }

    @Override // com.tencent.bugly.proguard.C0616a
    /* JADX INFO: renamed from: a */
    public <T> void mo290a(String str, T t) {
        if (this.f635d == null) {
            super.mo290a(str, t);
            return;
        }
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        if (t instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        }
        C0643j c0643j = new C0643j();
        c0643j.m361a(this.f509b);
        c0643j.m367a(t, 0);
        this.f635d.put(str, C0645l.m379a(c0643j.m362a()));
    }

    /* JADX INFO: renamed from: b */
    public final <T> T m314b(String str, T t) throws C0635b {
        HashMap<String, byte[]> map = this.f635d;
        if (map != null) {
            if (!map.containsKey(str)) {
                return null;
            }
            if (this.f636e.containsKey(str)) {
                return (T) this.f636e.get(str);
            }
            try {
                this.f637f.m355a(this.f635d.get(str));
                this.f637f.m349a(this.f509b);
                T t2 = (T) this.f637f.m352a((Object) t, 0, true);
                if (t2 != null) {
                    this.f636e.put(str, t2);
                }
                return t2;
            } catch (Exception e) {
                throw new C0635b(e);
            }
        }
        if (!this.f508a.containsKey(str)) {
            return null;
        }
        if (this.f636e.containsKey(str)) {
            return (T) this.f636e.get(str);
        }
        byte[] value = new byte[0];
        Iterator<Map.Entry<String, byte[]>> it = this.f508a.get(str).entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry<String, byte[]> next = it.next();
            next.getKey();
            value = next.getValue();
        }
        try {
            this.f637f.m355a(value);
            this.f637f.m349a(this.f509b);
            T t3 = (T) this.f637f.m352a((Object) t, 0, true);
            this.f636e.put(str, t3);
            return t3;
        } catch (Exception e2) {
            throw new C0635b(e2);
        }
    }

    @Override // com.tencent.bugly.proguard.C0616a
    /* JADX INFO: renamed from: a */
    public byte[] mo292a() {
        if (this.f635d != null) {
            C0643j c0643j = new C0643j(0);
            c0643j.m361a(this.f509b);
            c0643j.m370a((Map) this.f635d, 0);
            return C0645l.m379a(c0643j.m362a());
        }
        return super.mo292a();
    }

    @Override // com.tencent.bugly.proguard.C0616a
    /* JADX INFO: renamed from: a */
    public void mo291a(byte[] bArr) {
        try {
            super.mo291a(bArr);
        } catch (Exception unused) {
            this.f637f.m355a(bArr);
            this.f637f.m349a(this.f509b);
            HashMap map = new HashMap(1);
            map.put("", new byte[0]);
            this.f635d = this.f637f.m353a((Map) map, 0, false);
        }
    }
}
