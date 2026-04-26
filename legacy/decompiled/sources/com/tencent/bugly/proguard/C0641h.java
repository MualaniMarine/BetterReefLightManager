package com.tencent.bugly.proguard;

import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.h */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0641h {

    /* JADX INFO: renamed from: a */
    private StringBuilder f655a;

    /* JADX INFO: renamed from: b */
    private int f656b;

    /* JADX INFO: renamed from: a */
    private void m322a(String str) {
        for (int i = 0; i < this.f656b; i++) {
            this.f655a.append('\t');
        }
        if (str != null) {
            StringBuilder sb = this.f655a;
            sb.append(str);
            sb.append(": ");
        }
    }

    public C0641h(StringBuilder sb, int i) {
        this.f656b = 0;
        this.f655a = sb;
        this.f656b = i;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m330a(boolean z, String str) {
        m322a(str);
        StringBuilder sb = this.f655a;
        sb.append(z ? 'T' : 'F');
        sb.append('\n');
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m323a(byte b, String str) {
        m322a(str);
        StringBuilder sb = this.f655a;
        sb.append((int) b);
        sb.append('\n');
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m329a(short s, String str) {
        m322a(str);
        StringBuilder sb = this.f655a;
        sb.append((int) s);
        sb.append('\n');
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m324a(int i, String str) {
        m322a(str);
        StringBuilder sb = this.f655a;
        sb.append(i);
        sb.append('\n');
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m325a(long j, String str) {
        m322a(str);
        StringBuilder sb = this.f655a;
        sb.append(j);
        sb.append('\n');
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m327a(String str, String str2) {
        m322a(str2);
        if (str == null) {
            this.f655a.append("null\n");
        } else {
            StringBuilder sb = this.f655a;
            sb.append(str);
            sb.append('\n');
        }
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m331a(byte[] bArr, String str) {
        m322a(str);
        if (bArr == null) {
            this.f655a.append("null\n");
            return this;
        }
        if (bArr.length == 0) {
            StringBuilder sb = this.f655a;
            sb.append(bArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f655a;
        sb2.append(bArr.length);
        sb2.append(", [\n");
        C0641h c0641h = new C0641h(this.f655a, this.f656b + 1);
        for (byte b : bArr) {
            c0641h.m322a(null);
            StringBuilder sb3 = c0641h.f655a;
            sb3.append((int) b);
            sb3.append('\n');
        }
        m322a(null);
        StringBuilder sb4 = this.f655a;
        sb4.append(']');
        sb4.append('\n');
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final <K, V> C0641h m328a(Map<K, V> map, String str) {
        m322a(str);
        if (map == null) {
            this.f655a.append("null\n");
            return this;
        }
        if (map.isEmpty()) {
            StringBuilder sb = this.f655a;
            sb.append(map.size());
            sb.append(", {}\n");
            return this;
        }
        StringBuilder sb2 = this.f655a;
        sb2.append(map.size());
        sb2.append(", {\n");
        C0641h c0641h = new C0641h(this.f655a, this.f656b + 1);
        C0641h c0641h2 = new C0641h(this.f655a, this.f656b + 2);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            c0641h.m322a(null);
            StringBuilder sb3 = c0641h.f655a;
            sb3.append('(');
            sb3.append('\n');
            c0641h2.m320a(entry.getKey(), (String) null);
            c0641h2.m320a(entry.getValue(), (String) null);
            c0641h.m322a(null);
            StringBuilder sb4 = c0641h.f655a;
            sb4.append(')');
            sb4.append('\n');
        }
        m322a(null);
        StringBuilder sb5 = this.f655a;
        sb5.append('}');
        sb5.append('\n');
        return this;
    }

    /* JADX INFO: renamed from: a */
    private <T> C0641h m321a(T[] tArr, String str) {
        m322a(str);
        if (tArr == null) {
            this.f655a.append("null\n");
            return this;
        }
        if (tArr.length == 0) {
            StringBuilder sb = this.f655a;
            sb.append(tArr.length);
            sb.append(", []\n");
            return this;
        }
        StringBuilder sb2 = this.f655a;
        sb2.append(tArr.length);
        sb2.append(", [\n");
        C0641h c0641h = new C0641h(this.f655a, this.f656b + 1);
        for (T t : tArr) {
            c0641h.m320a(t, (String) null);
        }
        m322a(null);
        StringBuilder sb3 = this.f655a;
        sb3.append(']');
        sb3.append('\n');
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    private <T> C0641h m320a(T t, String str) {
        if (t == 0) {
            this.f655a.append("null\n");
        } else if (t instanceof Byte) {
            byte bByteValue = ((Byte) t).byteValue();
            m322a(str);
            StringBuilder sb = this.f655a;
            sb.append((int) bByteValue);
            sb.append('\n');
        } else if (t instanceof Boolean) {
            boolean zBooleanValue = ((Boolean) t).booleanValue();
            m322a(str);
            StringBuilder sb2 = this.f655a;
            sb2.append(zBooleanValue ? 'T' : 'F');
            sb2.append('\n');
        } else if (t instanceof Short) {
            short sShortValue = ((Short) t).shortValue();
            m322a(str);
            StringBuilder sb3 = this.f655a;
            sb3.append((int) sShortValue);
            sb3.append('\n');
        } else if (t instanceof Integer) {
            int iIntValue = ((Integer) t).intValue();
            m322a(str);
            StringBuilder sb4 = this.f655a;
            sb4.append(iIntValue);
            sb4.append('\n');
        } else if (t instanceof Long) {
            long jLongValue = ((Long) t).longValue();
            m322a(str);
            StringBuilder sb5 = this.f655a;
            sb5.append(jLongValue);
            sb5.append('\n');
        } else if (t instanceof Float) {
            float fFloatValue = ((Float) t).floatValue();
            m322a(str);
            StringBuilder sb6 = this.f655a;
            sb6.append(fFloatValue);
            sb6.append('\n');
        } else if (t instanceof Double) {
            double dDoubleValue = ((Double) t).doubleValue();
            m322a(str);
            StringBuilder sb7 = this.f655a;
            sb7.append(dDoubleValue);
            sb7.append('\n');
        } else if (t instanceof String) {
            m327a((String) t, str);
        } else if (t instanceof Map) {
            m328a((Map) t, str);
        } else if (t instanceof List) {
            List list = (List) t;
            if (list == null) {
                m322a(str);
                this.f655a.append("null\t");
            } else {
                m321a(list.toArray(), str);
            }
        } else if (t instanceof AbstractC0644k) {
            m326a((AbstractC0644k) t, str);
        } else if (t instanceof byte[]) {
            m331a((byte[]) t, str);
        } else if (t instanceof boolean[]) {
            m320a((boolean[]) t, str);
        } else {
            int i = 0;
            if (t instanceof short[]) {
                short[] sArr = (short[]) t;
                m322a(str);
                if (sArr == null) {
                    this.f655a.append("null\n");
                } else if (sArr.length == 0) {
                    StringBuilder sb8 = this.f655a;
                    sb8.append(sArr.length);
                    sb8.append(", []\n");
                } else {
                    StringBuilder sb9 = this.f655a;
                    sb9.append(sArr.length);
                    sb9.append(", [\n");
                    C0641h c0641h = new C0641h(this.f655a, this.f656b + 1);
                    int length = sArr.length;
                    while (i < length) {
                        short s = sArr[i];
                        c0641h.m322a(null);
                        StringBuilder sb10 = c0641h.f655a;
                        sb10.append((int) s);
                        sb10.append('\n');
                        i++;
                    }
                    m322a(null);
                    StringBuilder sb11 = this.f655a;
                    sb11.append(']');
                    sb11.append('\n');
                }
            } else if (t instanceof int[]) {
                int[] iArr = (int[]) t;
                m322a(str);
                if (iArr == null) {
                    this.f655a.append("null\n");
                } else if (iArr.length == 0) {
                    StringBuilder sb12 = this.f655a;
                    sb12.append(iArr.length);
                    sb12.append(", []\n");
                } else {
                    StringBuilder sb13 = this.f655a;
                    sb13.append(iArr.length);
                    sb13.append(", [\n");
                    C0641h c0641h2 = new C0641h(this.f655a, this.f656b + 1);
                    int length2 = iArr.length;
                    while (i < length2) {
                        int i2 = iArr[i];
                        c0641h2.m322a(null);
                        StringBuilder sb14 = c0641h2.f655a;
                        sb14.append(i2);
                        sb14.append('\n');
                        i++;
                    }
                    m322a(null);
                    StringBuilder sb15 = this.f655a;
                    sb15.append(']');
                    sb15.append('\n');
                }
            } else if (t instanceof long[]) {
                long[] jArr = (long[]) t;
                m322a(str);
                if (jArr == null) {
                    this.f655a.append("null\n");
                } else if (jArr.length == 0) {
                    StringBuilder sb16 = this.f655a;
                    sb16.append(jArr.length);
                    sb16.append(", []\n");
                } else {
                    StringBuilder sb17 = this.f655a;
                    sb17.append(jArr.length);
                    sb17.append(", [\n");
                    C0641h c0641h3 = new C0641h(this.f655a, this.f656b + 1);
                    int length3 = jArr.length;
                    while (i < length3) {
                        long j = jArr[i];
                        c0641h3.m322a(null);
                        StringBuilder sb18 = c0641h3.f655a;
                        sb18.append(j);
                        sb18.append('\n');
                        i++;
                    }
                    m322a(null);
                    StringBuilder sb19 = this.f655a;
                    sb19.append(']');
                    sb19.append('\n');
                }
            } else if (t instanceof float[]) {
                float[] fArr = (float[]) t;
                m322a(str);
                if (fArr == null) {
                    this.f655a.append("null\n");
                } else if (fArr.length == 0) {
                    StringBuilder sb20 = this.f655a;
                    sb20.append(fArr.length);
                    sb20.append(", []\n");
                } else {
                    StringBuilder sb21 = this.f655a;
                    sb21.append(fArr.length);
                    sb21.append(", [\n");
                    C0641h c0641h4 = new C0641h(this.f655a, this.f656b + 1);
                    int length4 = fArr.length;
                    while (i < length4) {
                        float f = fArr[i];
                        c0641h4.m322a(null);
                        StringBuilder sb22 = c0641h4.f655a;
                        sb22.append(f);
                        sb22.append('\n');
                        i++;
                    }
                    m322a(null);
                    StringBuilder sb23 = this.f655a;
                    sb23.append(']');
                    sb23.append('\n');
                }
            } else if (t instanceof double[]) {
                double[] dArr = (double[]) t;
                m322a(str);
                if (dArr == null) {
                    this.f655a.append("null\n");
                } else if (dArr.length == 0) {
                    StringBuilder sb24 = this.f655a;
                    sb24.append(dArr.length);
                    sb24.append(", []\n");
                } else {
                    StringBuilder sb25 = this.f655a;
                    sb25.append(dArr.length);
                    sb25.append(", [\n");
                    C0641h c0641h5 = new C0641h(this.f655a, this.f656b + 1);
                    int length5 = dArr.length;
                    while (i < length5) {
                        double d = dArr[i];
                        c0641h5.m322a(null);
                        StringBuilder sb26 = c0641h5.f655a;
                        sb26.append(d);
                        sb26.append('\n');
                        i++;
                    }
                    m322a(null);
                    StringBuilder sb27 = this.f655a;
                    sb27.append(']');
                    sb27.append('\n');
                }
            } else if (t.getClass().isArray()) {
                m321a((Object[]) t, str);
            } else {
                throw new C0635b("write object error: unsupport type.");
            }
        }
        return this;
    }

    /* JADX INFO: renamed from: a */
    public final C0641h m326a(AbstractC0644k abstractC0644k, String str) {
        m322a(str);
        StringBuilder sb = this.f655a;
        sb.append('{');
        sb.append('\n');
        if (abstractC0644k == null) {
            StringBuilder sb2 = this.f655a;
            sb2.append('\t');
            sb2.append("null");
        } else {
            abstractC0644k.mo313a(this.f655a, this.f656b + 1);
        }
        m322a(null);
        StringBuilder sb3 = this.f655a;
        sb3.append('}');
        sb3.append('\n');
        return this;
    }
}
