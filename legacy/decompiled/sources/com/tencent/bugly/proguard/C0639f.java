package com.tencent.bugly.proguard;

import androidx.core.app.NotificationCompat;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.f */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0639f extends AbstractC0644k {

    /* JADX INFO: renamed from: e */
    public byte[] f649e;

    /* JADX INFO: renamed from: i */
    private Map<String, String> f653i;

    /* JADX INFO: renamed from: j */
    private Map<String, String> f654j;

    /* JADX INFO: renamed from: m */
    private static /* synthetic */ boolean f644m = !C0639f.class.desiredAssertionStatus();

    /* JADX INFO: renamed from: k */
    private static byte[] f642k = null;

    /* JADX INFO: renamed from: l */
    private static Map<String, String> f643l = null;

    /* JADX INFO: renamed from: a */
    public short f645a = 0;

    /* JADX INFO: renamed from: f */
    private byte f650f = 0;

    /* JADX INFO: renamed from: g */
    private int f651g = 0;

    /* JADX INFO: renamed from: b */
    public int f646b = 0;

    /* JADX INFO: renamed from: c */
    public String f647c = null;

    /* JADX INFO: renamed from: d */
    public String f648d = null;

    /* JADX INFO: renamed from: h */
    private int f652h = 0;

    public final boolean equals(Object obj) {
        C0639f c0639f = (C0639f) obj;
        return C0645l.m375a(1, (int) c0639f.f645a) && C0645l.m375a(1, (int) c0639f.f650f) && C0645l.m375a(1, c0639f.f651g) && C0645l.m375a(1, c0639f.f646b) && C0645l.m377a((Object) 1, (Object) c0639f.f647c) && C0645l.m377a((Object) 1, (Object) c0639f.f648d) && C0645l.m377a((Object) 1, (Object) c0639f.f649e) && C0645l.m375a(1, c0639f.f652h) && C0645l.m377a((Object) 1, (Object) c0639f.f653i) && C0645l.m377a((Object) 1, (Object) c0639f.f654j);
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f644m) {
                return null;
            }
            throw new AssertionError();
        }
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo312a(C0643j c0643j) {
        c0643j.m371a(this.f645a, 1);
        c0643j.m363a(this.f650f, 2);
        c0643j.m364a(this.f651g, 3);
        c0643j.m364a(this.f646b, 4);
        c0643j.m368a(this.f647c, 5);
        c0643j.m368a(this.f648d, 6);
        c0643j.m373a(this.f649e, 7);
        c0643j.m364a(this.f652h, 8);
        c0643j.m370a((Map) this.f653i, 9);
        c0643j.m370a((Map) this.f654j, 10);
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo311a(C0642i c0642i) {
        try {
            this.f645a = c0642i.m354a(this.f645a, 1, true);
            this.f650f = c0642i.m347a(this.f650f, 2, true);
            this.f651g = c0642i.m348a(this.f651g, 3, true);
            this.f646b = c0642i.m348a(this.f646b, 4, true);
            this.f647c = c0642i.m357b(5, true);
            this.f648d = c0642i.m357b(6, true);
            if (f642k == null) {
                f642k = new byte[]{0};
            }
            this.f649e = c0642i.m358c(7, true);
            this.f652h = c0642i.m348a(this.f652h, 8, true);
            if (f643l == null) {
                HashMap map = new HashMap();
                f643l = map;
                map.put("", "");
            }
            this.f653i = (Map) c0642i.m352a(f643l, 9, true);
            if (f643l == null) {
                HashMap map2 = new HashMap();
                f643l = map2;
                map2.put("", "");
            }
            this.f654j = (Map) c0642i.m352a(f643l, 10, true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RequestPacket decode error " + C0638e.m319a(this.f649e));
            throw new RuntimeException(e);
        }
    }

    @Override // com.tencent.bugly.proguard.AbstractC0644k
    /* JADX INFO: renamed from: a */
    public final void mo313a(StringBuilder sb, int i) {
        C0641h c0641h = new C0641h(sb, i);
        c0641h.m329a(this.f645a, "iVersion");
        c0641h.m323a(this.f650f, "cPacketType");
        c0641h.m324a(this.f651g, "iMessageType");
        c0641h.m324a(this.f646b, "iRequestId");
        c0641h.m327a(this.f647c, "sServantName");
        c0641h.m327a(this.f648d, "sFuncName");
        c0641h.m331a(this.f649e, "sBuffer");
        c0641h.m324a(this.f652h, "iTimeout");
        c0641h.m328a((Map) this.f653i, "context");
        c0641h.m328a((Map) this.f654j, NotificationCompat.CATEGORY_STATUS);
    }
}
