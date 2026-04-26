package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.j */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0643j {

    /* JADX INFO: renamed from: a */
    private ByteBuffer f661a;

    /* JADX INFO: renamed from: b */
    private String f662b;

    public C0643j(int i) {
        this.f662b = "GBK";
        this.f661a = ByteBuffer.allocate(i);
    }

    public C0643j() {
        this(128);
    }

    /* JADX INFO: renamed from: a */
    public final ByteBuffer m362a() {
        return this.f661a;
    }

    /* JADX INFO: renamed from: b */
    public final byte[] m374b() {
        byte[] bArr = new byte[this.f661a.position()];
        System.arraycopy(this.f661a.array(), 0, bArr, 0, this.f661a.position());
        return bArr;
    }

    /* JADX INFO: renamed from: a */
    private void m359a(int i) {
        if (this.f661a.remaining() < i) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate((this.f661a.capacity() + i) << 1);
            byteBufferAllocate.put(this.f661a.array(), 0, this.f661a.position());
            this.f661a = byteBufferAllocate;
        }
    }

    /* JADX INFO: renamed from: b */
    private void m360b(byte b, int i) {
        if (i < 15) {
            this.f661a.put((byte) (b | (i << 4)));
        } else if (i < 256) {
            this.f661a.put((byte) (b | 240));
            this.f661a.put((byte) i);
        } else {
            throw new C0635b("tag is too large: " + i);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m372a(boolean z, int i) {
        m363a(z ? (byte) 1 : (byte) 0, i);
    }

    /* JADX INFO: renamed from: a */
    public final void m363a(byte b, int i) {
        m359a(3);
        if (b == 0) {
            m360b((byte) 12, i);
        } else {
            m360b((byte) 0, i);
            this.f661a.put(b);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m371a(short s, int i) {
        m359a(4);
        if (s >= -128 && s <= 127) {
            m363a((byte) s, i);
        } else {
            m360b((byte) 1, i);
            this.f661a.putShort(s);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m364a(int i, int i2) {
        m359a(6);
        if (i >= -32768 && i <= 32767) {
            m371a((short) i, i2);
        } else {
            m360b((byte) 2, i2);
            this.f661a.putInt(i);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m365a(long j, int i) {
        m359a(10);
        if (j >= -2147483648L && j <= 2147483647L) {
            m364a((int) j, i);
        } else {
            m360b((byte) 3, i);
            this.f661a.putLong(j);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m368a(String str, int i) {
        byte[] bytes;
        try {
            bytes = str.getBytes(this.f662b);
        } catch (UnsupportedEncodingException unused) {
            bytes = str.getBytes();
        }
        m359a(bytes.length + 10);
        if (bytes.length > 255) {
            m360b((byte) 7, i);
            this.f661a.putInt(bytes.length);
            this.f661a.put(bytes);
        } else {
            m360b((byte) 6, i);
            this.f661a.put((byte) bytes.length);
            this.f661a.put(bytes);
        }
    }

    /* JADX INFO: renamed from: a */
    public final <K, V> void m370a(Map<K, V> map, int i) {
        m359a(8);
        m360b((byte) 8, i);
        m364a(map == null ? 0 : map.size(), 0);
        if (map != null) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                m367a(entry.getKey(), 0);
                m367a(entry.getValue(), 1);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m373a(byte[] bArr, int i) {
        m359a(bArr.length + 8);
        m360b((byte) 13, i);
        m360b((byte) 0, 0);
        m364a(bArr.length, 0);
        this.f661a.put(bArr);
    }

    /* JADX INFO: renamed from: a */
    public final <T> void m369a(Collection<T> collection, int i) {
        m359a(8);
        m360b((byte) 9, i);
        m364a(collection == null ? 0 : collection.size(), 0);
        if (collection != null) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                m367a(it.next(), 0);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m366a(AbstractC0644k abstractC0644k, int i) {
        m359a(2);
        m360b((byte) 10, i);
        abstractC0644k.mo312a(this);
        m359a(2);
        m360b((byte) 11, 0);
    }

    /* JADX INFO: renamed from: a */
    public final void m367a(Object obj, int i) {
        if (obj instanceof Byte) {
            m363a(((Byte) obj).byteValue(), i);
            return;
        }
        if (obj instanceof Boolean) {
            m363a(((Boolean) obj).booleanValue() ? (byte) 1 : (byte) 0, i);
            return;
        }
        if (obj instanceof Short) {
            m371a(((Short) obj).shortValue(), i);
            return;
        }
        if (obj instanceof Integer) {
            m364a(((Integer) obj).intValue(), i);
            return;
        }
        if (obj instanceof Long) {
            m365a(((Long) obj).longValue(), i);
            return;
        }
        if (obj instanceof Float) {
            float fFloatValue = ((Float) obj).floatValue();
            m359a(6);
            m360b((byte) 4, i);
            this.f661a.putFloat(fFloatValue);
            return;
        }
        if (obj instanceof Double) {
            double dDoubleValue = ((Double) obj).doubleValue();
            m359a(10);
            m360b((byte) 5, i);
            this.f661a.putDouble(dDoubleValue);
            return;
        }
        if (obj instanceof String) {
            m368a((String) obj, i);
            return;
        }
        if (obj instanceof Map) {
            m370a((Map) obj, i);
            return;
        }
        if (obj instanceof List) {
            m369a((Collection) obj, i);
            return;
        }
        if (obj instanceof AbstractC0644k) {
            m359a(2);
            m360b((byte) 10, i);
            ((AbstractC0644k) obj).mo312a(this);
            m359a(2);
            m360b((byte) 11, 0);
            return;
        }
        if (obj instanceof byte[]) {
            m373a((byte[]) obj, i);
            return;
        }
        if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            m359a(8);
            m360b((byte) 9, i);
            m364a(zArr.length, 0);
            for (boolean z : zArr) {
                m363a(z ? (byte) 1 : (byte) 0, 0);
            }
            return;
        }
        if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            m359a(8);
            m360b((byte) 9, i);
            m364a(sArr.length, 0);
            for (short s : sArr) {
                m371a(s, 0);
            }
            return;
        }
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            m359a(8);
            m360b((byte) 9, i);
            m364a(iArr.length, 0);
            for (int i2 : iArr) {
                m364a(i2, 0);
            }
            return;
        }
        if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            m359a(8);
            m360b((byte) 9, i);
            m364a(jArr.length, 0);
            for (long j : jArr) {
                m365a(j, 0);
            }
            return;
        }
        if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            m359a(8);
            m360b((byte) 9, i);
            m364a(fArr.length, 0);
            for (float f : fArr) {
                m359a(6);
                m360b((byte) 4, 0);
                this.f661a.putFloat(f);
            }
            return;
        }
        if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            m359a(8);
            m360b((byte) 9, i);
            m364a(dArr.length, 0);
            for (double d : dArr) {
                m359a(10);
                m360b((byte) 5, 0);
                this.f661a.putDouble(d);
            }
            return;
        }
        if (obj.getClass().isArray()) {
            Object[] objArr = (Object[]) obj;
            m359a(8);
            m360b((byte) 9, i);
            m364a(objArr.length, 0);
            for (Object obj2 : objArr) {
                m367a(obj2, 0);
            }
            return;
        }
        if (obj instanceof Collection) {
            m369a((Collection) obj, i);
        } else {
            throw new C0635b("write object error: unsupport type. " + obj.getClass());
        }
    }

    /* JADX INFO: renamed from: a */
    public final int m361a(String str) {
        this.f662b = str;
        return 0;
    }
}
