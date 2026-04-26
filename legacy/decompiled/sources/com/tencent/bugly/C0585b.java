package com.tencent.bugly;

import android.content.Context;
import android.util.Log;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.proguard.C0649p;
import com.tencent.bugly.proguard.C0657x;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.tencent.bugly.b */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0585b {

    /* JADX INFO: renamed from: a */
    public static boolean f143a = true;

    /* JADX INFO: renamed from: b */
    public static List<AbstractC0584a> f144b = new ArrayList();

    /* JADX INFO: renamed from: c */
    public static boolean f145c;

    /* JADX INFO: renamed from: d */
    private static C0649p f146d;

    /* JADX INFO: renamed from: e */
    private static boolean f147e;

    /* JADX INFO: renamed from: a */
    private static boolean m15a(C0593a c0593a) {
        List<String> list = c0593a.f257o;
        c0593a.getClass();
        return list != null && list.contains("bugly");
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m11a(Context context) {
        m12a(context, null);
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m12a(Context context, BuglyStrategy buglyStrategy) {
        if (f147e) {
            C0657x.m467d("[init] initial Multi-times, ignore this.", new Object[0]);
            return;
        }
        if (context == null) {
            Log.w(C0657x.f752a, "[init] context of init() is null, check it.");
            return;
        }
        C0593a c0593aM64a = C0593a.m64a(context);
        if (m15a(c0593aM64a)) {
            f143a = false;
            return;
        }
        String strM87f = c0593aM64a.m87f();
        if (strM87f == null) {
            Log.e(C0657x.f752a, "[init] meta data of BUGLY_APPID in AndroidManifest.xml should be set.");
        } else {
            m13a(context, strM87f, c0593aM64a.f263u, buglyStrategy);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x0223 A[Catch: all -> 0x0236, TryCatch #1 {, blocks: (B:4:0x0009, B:6:0x000e, B:10:0x0019, B:14:0x0024, B:18:0x002e, B:20:0x0032, B:21:0x006e, B:23:0x00b0, B:26:0x00b4, B:28:0x00c2, B:30:0x00d0, B:32:0x00d6, B:33:0x00ec, B:34:0x00fb, B:36:0x0101, B:38:0x010b, B:40:0x0111, B:41:0x0127, B:47:0x0157, B:53:0x016b, B:55:0x0175, B:57:0x017b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01ac, B:64:0x01c2, B:65:0x01ce, B:67:0x01d4, B:68:0x01e0, B:42:0x013b, B:44:0x0146, B:46:0x0150, B:50:0x0164, B:52:0x0168, B:70:0x01ed, B:80:0x021b, B:81:0x021e, B:83:0x0223, B:85:0x022a, B:77:0x0212, B:79:0x0218, B:72:0x01f5, B:74:0x0205), top: B:93:0x0009, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01f5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void m13a(android.content.Context r20, java.lang.String r21, boolean r22, com.tencent.bugly.BuglyStrategy r23) {
        /*
            Method dump skipped, instruction units count: 569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.C0585b.m13a(android.content.Context, java.lang.String, boolean, com.tencent.bugly.BuglyStrategy):void");
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m14a(AbstractC0584a abstractC0584a) {
        if (!f144b.contains(abstractC0584a)) {
            f144b.add(abstractC0584a);
        }
    }
}
