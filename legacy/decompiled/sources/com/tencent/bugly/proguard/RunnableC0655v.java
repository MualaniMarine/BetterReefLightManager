package com.tencent.bugly.proguard;

import android.content.Context;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.v */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0655v implements Runnable {

    /* JADX INFO: renamed from: a */
    private int f730a;

    /* JADX INFO: renamed from: b */
    private int f731b;

    /* JADX INFO: renamed from: c */
    private final Context f732c;

    /* JADX INFO: renamed from: d */
    private final int f733d;

    /* JADX INFO: renamed from: e */
    private final byte[] f734e;

    /* JADX INFO: renamed from: f */
    private final C0593a f735f;

    /* JADX INFO: renamed from: g */
    private final C0596a f736g;

    /* JADX INFO: renamed from: h */
    private final C0652s f737h;

    /* JADX INFO: renamed from: i */
    private final C0654u f738i;

    /* JADX INFO: renamed from: j */
    private final int f739j;

    /* JADX INFO: renamed from: k */
    private final InterfaceC0653t f740k;

    /* JADX INFO: renamed from: l */
    private final InterfaceC0653t f741l;

    /* JADX INFO: renamed from: m */
    private String f742m;

    /* JADX INFO: renamed from: n */
    private final String f743n;

    /* JADX INFO: renamed from: o */
    private final Map<String, String> f744o;

    /* JADX INFO: renamed from: p */
    private int f745p;

    /* JADX INFO: renamed from: q */
    private long f746q;

    /* JADX INFO: renamed from: r */
    private long f747r;

    /* JADX INFO: renamed from: s */
    private boolean f748s;

    public RunnableC0655v(Context context, int i, int i2, byte[] bArr, String str, String str2, InterfaceC0653t interfaceC0653t, boolean z, boolean z2) {
        this(context, i, i2, bArr, str, str2, interfaceC0653t, 2, 30000, z2, null);
    }

    public RunnableC0655v(Context context, int i, int i2, byte[] bArr, String str, String str2, InterfaceC0653t interfaceC0653t, int i3, int i4, boolean z, Map<String, String> map) {
        this.f730a = 2;
        this.f731b = 30000;
        this.f742m = null;
        this.f745p = 0;
        this.f746q = 0L;
        this.f747r = 0L;
        this.f748s = false;
        this.f732c = context;
        this.f735f = C0593a.m64a(context);
        this.f734e = bArr;
        this.f736g = C0596a.m139a();
        this.f737h = C0652s.m428a(context);
        this.f738i = C0654u.m434a();
        this.f739j = i;
        this.f742m = str;
        this.f743n = str2;
        this.f740k = interfaceC0653t;
        this.f741l = null;
        this.f733d = i2;
        if (i3 > 0) {
            this.f730a = i3;
        }
        if (i4 > 0) {
            this.f731b = i4;
        }
        this.f748s = z;
        this.f744o = map;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001a  */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void m449a(com.tencent.bugly.proguard.C0630an r4, boolean r5, int r6, java.lang.String r7) {
        /*
            r3 = this;
            int r4 = r3.f733d
            r0 = 630(0x276, float:8.83E-43)
            if (r4 == r0) goto L1a
            r0 = 640(0x280, float:8.97E-43)
            if (r4 == r0) goto L17
            r0 = 830(0x33e, float:1.163E-42)
            if (r4 == r0) goto L1a
            r0 = 840(0x348, float:1.177E-42)
            if (r4 == r0) goto L17
            java.lang.String r4 = java.lang.String.valueOf(r4)
            goto L1c
        L17:
            java.lang.String r4 = "userinfo"
            goto L1c
        L1a:
            java.lang.String r4 = "crash"
        L1c:
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L2a
            java.lang.Object[] r6 = new java.lang.Object[r0]
            r6[r1] = r4
            java.lang.String r4 = "[Upload] Success: %s"
            com.tencent.bugly.proguard.C0657x.m461a(r4, r6)
            goto L3d
        L2a:
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r2[r1] = r6
            r2[r0] = r4
            r4 = 2
            r2[r4] = r7
            java.lang.String r4 = "[Upload] Failed to upload(%d) %s: %s"
            com.tencent.bugly.proguard.C0657x.m468e(r4, r2)
        L3d:
            long r6 = r3.f746q
            long r0 = r3.f747r
            long r6 = r6 + r0
            r0 = 0
            int r4 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r4 <= 0) goto L5d
            com.tencent.bugly.proguard.u r4 = r3.f738i
            boolean r6 = r3.f748s
            long r6 = r4.m442a(r6)
            long r0 = r3.f746q
            long r6 = r6 + r0
            long r0 = r3.f747r
            long r6 = r6 + r0
            com.tencent.bugly.proguard.u r4 = r3.f738i
            boolean r0 = r3.f748s
            r4.m446a(r6, r0)
        L5d:
            com.tencent.bugly.proguard.t r4 = r3.f740k
            if (r4 == 0) goto L64
            r4.mo33a(r5)
        L64:
            com.tencent.bugly.proguard.t r4 = r3.f741l
            if (r4 == 0) goto L6b
            r4.mo33a(r5)
        L6b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.RunnableC0655v.m449a(com.tencent.bugly.proguard.an, boolean, int, java.lang.String):void");
    }

    /* JADX INFO: renamed from: a */
    private static boolean m450a(C0630an c0630an, C0593a c0593a, C0596a c0596a) {
        if (c0630an == null) {
            C0657x.m467d("resp == null!", new Object[0]);
            return false;
        }
        if (c0630an.f594a != 0) {
            C0657x.m468e("resp result error %d", Byte.valueOf(c0630an.f594a));
            return false;
        }
        try {
            if (!C0659z.m509a(c0630an.f598e) && !C0593a.m65b().m94j().equals(c0630an.f598e)) {
                C0649p.m402a().m422a(C0596a.f294a, "device", c0630an.f598e.getBytes(ByteUtil.ESPTOUCH_ENCODING_CHARSET), (InterfaceC0648o) null, true);
                c0593a.m88f(c0630an.f598e);
            }
        } catch (Throwable th) {
            C0657x.m462a(th);
        }
        c0593a.f251i = c0630an.f597d;
        if (c0630an.f595b == 510) {
            if (c0630an.f596c == null) {
                C0657x.m468e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(c0630an.f595b));
                return false;
            }
            C0632ap c0632ap = (C0632ap) C0616a.m280a(c0630an.f596c, C0632ap.class);
            if (c0632ap == null) {
                C0657x.m468e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(c0630an.f595b));
                return false;
            }
            c0596a.m147a(c0632ap);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0225 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01be A[Catch: all -> 0x033a, TryCatch #0 {all -> 0x033a, blocks: (B:3:0x0007, B:5:0x001a, B:8:0x0022, B:11:0x0027, B:13:0x003b, B:15:0x003f, B:17:0x0043, B:20:0x0049, B:22:0x0051, B:24:0x0057, B:26:0x0084, B:27:0x0089, B:29:0x00b8, B:32:0x00c0, B:34:0x00c6, B:35:0x00da, B:38:0x00e2, B:40:0x00f9, B:41:0x0106, B:44:0x0149, B:45:0x015c, B:48:0x0164, B:51:0x016b, B:54:0x0173, B:66:0x01be, B:68:0x01ea, B:69:0x01f2, B:71:0x01f8, B:72:0x0219, B:77:0x0254, B:79:0x0267, B:81:0x0278, B:82:0x0280, B:84:0x0286, B:85:0x02a1, B:88:0x02aa, B:90:0x02b1, B:93:0x02b9, B:95:0x02bf, B:97:0x02c6, B:101:0x02dc, B:103:0x02ef, B:105:0x02f6, B:100:0x02d9, B:108:0x02fe, B:56:0x017d, B:58:0x0183, B:59:0x018b, B:61:0x0199, B:62:0x01a5, B:63:0x01b2, B:110:0x0325, B:112:0x032c, B:114:0x0333), top: B:122:0x0007 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            Method dump skipped, instruction units count: 837
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.RunnableC0655v.run():void");
    }

    /* JADX INFO: renamed from: a */
    public final void m451a(long j) {
        this.f745p++;
        this.f746q += j;
    }

    /* JADX INFO: renamed from: b */
    public final void m452b(long j) {
        this.f747r += j;
    }

    /* JADX INFO: renamed from: a */
    private static String m448a(String str) {
        if (C0659z.m509a(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", str, UUID.randomUUID().toString());
        } catch (Throwable th) {
            C0657x.m462a(th);
            return str;
        }
    }
}
