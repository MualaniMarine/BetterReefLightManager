package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.i */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0642i {

    /* JADX INFO: renamed from: a */
    private ByteBuffer f657a;

    /* JADX INFO: renamed from: b */
    private String f658b = "GBK";

    /* JADX INFO: renamed from: com.tencent.bugly.proguard.i$a */
    /* JADX INFO: compiled from: BUGLY */
    public static class a {

        /* JADX INFO: renamed from: a */
        public byte f659a;

        /* JADX INFO: renamed from: b */
        public int f660b;
    }

    public C0642i() {
    }

    public C0642i(byte[] bArr) {
        this.f657a = ByteBuffer.wrap(bArr);
    }

    public C0642i(byte[] bArr, int i) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        this.f657a = byteBufferWrap;
        byteBufferWrap.position(4);
    }

    /* JADX INFO: renamed from: a */
    public final void m355a(byte[] bArr) {
        ByteBuffer byteBuffer = this.f657a;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        this.f657a = ByteBuffer.wrap(bArr);
    }

    /* JADX INFO: renamed from: a */
    private static int m334a(a aVar, ByteBuffer byteBuffer) {
        byte b = byteBuffer.get();
        aVar.f659a = (byte) (b & 15);
        aVar.f660b = (b & 240) >> 4;
        if (aVar.f660b != 15) {
            return 1;
        }
        aVar.f660b = byteBuffer.get();
        return 2;
    }

    /* JADX INFO: renamed from: a */
    private boolean m338a(int i) {
        a aVar;
        try {
            aVar = new a();
            while (true) {
                int iM334a = m334a(aVar, this.f657a.duplicate());
                if (i <= aVar.f660b || aVar.f659a == 11) {
                    break;
                }
                this.f657a.position(this.f657a.position() + iM334a);
                m337a(aVar.f659a);
            }
        } catch (C0640g | BufferUnderflowException unused) {
        }
        return i == aVar.f660b;
    }

    /* JADX INFO: renamed from: a */
    private void m336a() {
        a aVar = new a();
        do {
            m334a(aVar, this.f657a);
            m337a(aVar.f659a);
        } while (aVar.f659a != 11);
    }

    /* JADX INFO: renamed from: a */
    private void m337a(byte b) {
        int i = 0;
        switch (b) {
            case 0:
                ByteBuffer byteBuffer = this.f657a;
                byteBuffer.position(byteBuffer.position() + 1);
                return;
            case 1:
                ByteBuffer byteBuffer2 = this.f657a;
                byteBuffer2.position(byteBuffer2.position() + 2);
                return;
            case 2:
                ByteBuffer byteBuffer3 = this.f657a;
                byteBuffer3.position(byteBuffer3.position() + 4);
                return;
            case 3:
                ByteBuffer byteBuffer4 = this.f657a;
                byteBuffer4.position(byteBuffer4.position() + 8);
                return;
            case 4:
                ByteBuffer byteBuffer5 = this.f657a;
                byteBuffer5.position(byteBuffer5.position() + 4);
                return;
            case 5:
                ByteBuffer byteBuffer6 = this.f657a;
                byteBuffer6.position(byteBuffer6.position() + 8);
                return;
            case 6:
                int i2 = this.f657a.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                ByteBuffer byteBuffer7 = this.f657a;
                byteBuffer7.position(byteBuffer7.position() + i2);
                return;
            case 7:
                int i3 = this.f657a.getInt();
                ByteBuffer byteBuffer8 = this.f657a;
                byteBuffer8.position(byteBuffer8.position() + i3);
                return;
            case 8:
                int iM348a = m348a(0, 0, true);
                while (i < (iM348a << 1)) {
                    a aVar = new a();
                    m334a(aVar, this.f657a);
                    m337a(aVar.f659a);
                    i++;
                }
                return;
            case 9:
                int iM348a2 = m348a(0, 0, true);
                while (i < iM348a2) {
                    a aVar2 = new a();
                    m334a(aVar2, this.f657a);
                    m337a(aVar2.f659a);
                    i++;
                }
                return;
            case 10:
                m336a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                a aVar3 = new a();
                m334a(aVar3, this.f657a);
                if (aVar3.f659a != 0) {
                    throw new C0640g("skipField with invalid type, type value: " + ((int) b) + ", " + ((int) aVar3.f659a));
                }
                int iM348a3 = m348a(0, 0, true);
                ByteBuffer byteBuffer9 = this.f657a;
                byteBuffer9.position(byteBuffer9.position() + iM348a3);
                return;
            default:
                throw new C0640g("invalid type.");
        }
    }

    /* JADX INFO: renamed from: a */
    public final boolean m356a(int i, boolean z) {
        return m347a((byte) 0, i, z) != 0;
    }

    /* JADX INFO: renamed from: a */
    public final byte m347a(byte b, int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return b;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b2 = aVar.f659a;
        if (b2 == 0) {
            return this.f657a.get();
        }
        if (b2 == 12) {
            return (byte) 0;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public final short m354a(short s, int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return s;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b = aVar.f659a;
        if (b == 0) {
            return this.f657a.get();
        }
        if (b == 1) {
            return this.f657a.getShort();
        }
        if (b == 12) {
            return (short) 0;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public final int m348a(int i, int i2, boolean z) {
        if (!m338a(i2)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return i;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b = aVar.f659a;
        if (b == 0) {
            return this.f657a.get();
        }
        if (b == 1) {
            return this.f657a.getShort();
        }
        if (b == 2) {
            return this.f657a.getInt();
        }
        if (b == 12) {
            return 0;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public final long m350a(long j, int i, boolean z) {
        int i2;
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return j;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b = aVar.f659a;
        if (b == 0) {
            i2 = this.f657a.get();
        } else if (b == 1) {
            i2 = this.f657a.getShort();
        } else {
            if (b != 2) {
                if (b == 3) {
                    return this.f657a.getLong();
                }
                if (b == 12) {
                    return 0L;
                }
                throw new C0640g("type mismatch.");
            }
            i2 = this.f657a.getInt();
        }
        return i2;
    }

    /* JADX INFO: renamed from: a */
    private float m333a(float f, int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return f;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b = aVar.f659a;
        if (b == 4) {
            return this.f657a.getFloat();
        }
        if (b == 12) {
            return 0.0f;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    private double m332a(double d, int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return d;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b = aVar.f659a;
        if (b == 4) {
            return this.f657a.getFloat();
        }
        if (b == 5) {
            return this.f657a.getDouble();
        }
        if (b == 12) {
            return 0.0d;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: b */
    public final String m357b(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b = aVar.f659a;
        if (b == 6) {
            int i2 = this.f657a.get();
            if (i2 < 0) {
                i2 += 256;
            }
            byte[] bArr = new byte[i2];
            this.f657a.get(bArr);
            try {
                return new String(bArr, this.f658b);
            } catch (UnsupportedEncodingException unused) {
                return new String(bArr);
            }
        }
        if (b == 7) {
            int i3 = this.f657a.getInt();
            if (i3 > 104857600 || i3 < 0) {
                throw new C0640g("String too long: " + i3);
            }
            byte[] bArr2 = new byte[i3];
            this.f657a.get(bArr2);
            try {
                return new String(bArr2, this.f658b);
            } catch (UnsupportedEncodingException unused2) {
                return new String(bArr2);
            }
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public final <K, V> HashMap<K, V> m353a(Map<K, V> map, int i, boolean z) {
        return (HashMap) m335a(new HashMap(), map, i, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    private <K, V> Map<K, V> m335a(Map<K, V> map, Map<K, V> map2, int i, boolean z) {
        if (map2 == null || map2.isEmpty()) {
            return new HashMap();
        }
        Map.Entry<K, V> next = map2.entrySet().iterator().next();
        K key = next.getKey();
        V value = next.getValue();
        if (m338a(i)) {
            a aVar = new a();
            m334a(aVar, this.f657a);
            if (aVar.f659a == 8) {
                int iM348a = m348a(0, 0, true);
                if (iM348a < 0) {
                    throw new C0640g("size invalid: " + iM348a);
                }
                for (int i2 = 0; i2 < iM348a; i2++) {
                    map.put(m352a(key, 0, true), m352a(value, 1, true));
                }
            } else {
                throw new C0640g("type mismatch.");
            }
        } else if (z) {
            throw new C0640g("require field not exist.");
        }
        return map;
    }

    /* JADX INFO: renamed from: d */
    private boolean[] m341d(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        if (aVar.f659a == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            boolean[] zArr = new boolean[iM348a];
            for (int i2 = 0; i2 < iM348a; i2++) {
                zArr[i2] = m347a((byte) 0, 0, true) != 0;
            }
            return zArr;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: c */
    public final byte[] m358c(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        byte b = aVar.f659a;
        if (b == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            byte[] bArr = new byte[iM348a];
            for (int i2 = 0; i2 < iM348a; i2++) {
                bArr[i2] = m347a(bArr[0], 0, true);
            }
            return bArr;
        }
        if (b == 13) {
            a aVar2 = new a();
            m334a(aVar2, this.f657a);
            if (aVar2.f659a != 0) {
                throw new C0640g("type mismatch, tag: " + i + ", type: " + ((int) aVar.f659a) + ", " + ((int) aVar2.f659a));
            }
            int iM348a2 = m348a(0, 0, true);
            if (iM348a2 < 0) {
                throw new C0640g("invalid size, tag: " + i + ", type: " + ((int) aVar.f659a) + ", " + ((int) aVar2.f659a) + ", size: " + iM348a2);
            }
            byte[] bArr2 = new byte[iM348a2];
            this.f657a.get(bArr2);
            return bArr2;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: e */
    private short[] m342e(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        if (aVar.f659a == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            short[] sArr = new short[iM348a];
            for (int i2 = 0; i2 < iM348a; i2++) {
                sArr[i2] = m354a(sArr[0], 0, true);
            }
            return sArr;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: f */
    private int[] m343f(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        if (aVar.f659a == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            int[] iArr = new int[iM348a];
            for (int i2 = 0; i2 < iM348a; i2++) {
                iArr[i2] = m348a(iArr[0], 0, true);
            }
            return iArr;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: g */
    private long[] m344g(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        if (aVar.f659a == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            long[] jArr = new long[iM348a];
            for (int i2 = 0; i2 < iM348a; i2++) {
                jArr[i2] = m350a(jArr[0], 0, true);
            }
            return jArr;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: h */
    private float[] m345h(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        if (aVar.f659a == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            float[] fArr = new float[iM348a];
            for (int i2 = 0; i2 < iM348a; i2++) {
                fArr[i2] = m333a(fArr[0], 0, true);
            }
            return fArr;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: i */
    private double[] m346i(int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        if (aVar.f659a == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            double[] dArr = new double[iM348a];
            for (int i2 = 0; i2 < iM348a; i2++) {
                dArr[i2] = m332a(dArr[0], 0, true);
            }
            return dArr;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    private <T> T[] m339a(T[] tArr, int i, boolean z) {
        if (tArr == null || tArr.length == 0) {
            throw new C0640g("unable to get type of key and value.");
        }
        return (T[]) m340b(tArr[0], i, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: b */
    private <T> T[] m340b(T t, int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m334a(aVar, this.f657a);
        if (aVar.f659a == 9) {
            int iM348a = m348a(0, 0, true);
            if (iM348a < 0) {
                throw new C0640g("size invalid: " + iM348a);
            }
            T[] tArr = (T[]) ((Object[]) Array.newInstance(t.getClass(), iM348a));
            for (int i2 = 0; i2 < iM348a; i2++) {
                tArr[i2] = m352a((Object) t, 0, true);
            }
            return tArr;
        }
        throw new C0640g("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public final AbstractC0644k m351a(AbstractC0644k abstractC0644k, int i, boolean z) {
        if (!m338a(i)) {
            if (z) {
                throw new C0640g("require field not exist.");
            }
            return null;
        }
        try {
            AbstractC0644k abstractC0644k2 = (AbstractC0644k) abstractC0644k.getClass().newInstance();
            a aVar = new a();
            m334a(aVar, this.f657a);
            if (aVar.f659a != 10) {
                throw new C0640g("type mismatch.");
            }
            abstractC0644k2.mo311a(this);
            m336a();
            return abstractC0644k2;
        } catch (Exception e) {
            throw new C0640g(e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    public final <T> Object m352a(T t, int i, boolean z) {
        if (t instanceof Byte) {
            return Byte.valueOf(m347a((byte) 0, i, z));
        }
        if (t instanceof Boolean) {
            return Boolean.valueOf(m347a((byte) 0, i, z) != 0);
        }
        if (t instanceof Short) {
            return Short.valueOf(m354a((short) 0, i, z));
        }
        if (t instanceof Integer) {
            return Integer.valueOf(m348a(0, i, z));
        }
        if (t instanceof Long) {
            return Long.valueOf(m350a(0L, i, z));
        }
        if (t instanceof Float) {
            return Float.valueOf(m333a(0.0f, i, z));
        }
        if (t instanceof Double) {
            return Double.valueOf(m332a(0.0d, i, z));
        }
        if (t instanceof String) {
            return String.valueOf(m357b(i, z));
        }
        if (t instanceof Map) {
            return (HashMap) m335a(new HashMap(), (Map) t, i, z);
        }
        if (t instanceof List) {
            List list = (List) t;
            if (list == null || list.isEmpty()) {
                return new ArrayList();
            }
            Object[] objArrM340b = m340b(list.get(0), i, z);
            if (objArrM340b == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : objArrM340b) {
                arrayList.add(obj);
            }
            return arrayList;
        }
        if (t instanceof AbstractC0644k) {
            return m351a((AbstractC0644k) t, i, z);
        }
        if (t.getClass().isArray()) {
            if ((t instanceof byte[]) || (t instanceof Byte[])) {
                return m358c(i, z);
            }
            if (t instanceof boolean[]) {
                return m341d(i, z);
            }
            if (t instanceof short[]) {
                return m342e(i, z);
            }
            if (t instanceof int[]) {
                return m343f(i, z);
            }
            if (t instanceof long[]) {
                return m344g(i, z);
            }
            if (t instanceof float[]) {
                return m345h(i, z);
            }
            if (t instanceof double[]) {
                return m346i(i, z);
            }
            return m339a((Object[]) t, i, z);
        }
        throw new C0640g("read object error: unsupport type.");
    }

    /* JADX INFO: renamed from: a */
    public final int m349a(String str) {
        this.f658b = str;
        return 0;
    }
}
