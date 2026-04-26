package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.proguard.C0649p;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import com.tencent.bugly.proguard.InterfaceC0648o;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class Bugly {
    public static final String SDK_IS_DEV = "false";

    /* JADX INFO: renamed from: a */
    private static boolean f113a = false;
    public static Context applicationContext = null;

    /* JADX INFO: renamed from: b */
    private static String[] f114b = {"BuglyCrashModule", "BuglyRqdModule", "BuglyBetaModule"};

    /* JADX INFO: renamed from: c */
    private static String[] f115c = {"BuglyRqdModule", "BuglyCrashModule", "BuglyBetaModule"};
    public static boolean enable = true;
    public static Boolean isDev;

    public static void init(Context context, String str, boolean z) {
        init(context, str, z, null);
    }

    public static synchronized void init(Context context, String str, boolean z, BuglyStrategy buglyStrategy) {
        if (f113a) {
            return;
        }
        f113a = true;
        Context contextM486a = C0659z.m486a(context);
        applicationContext = contextM486a;
        if (contextM486a == null) {
            Log.e(C0657x.f752a, "init arg 'context' should not be null!");
            return;
        }
        if (isDev()) {
            f114b = f115c;
        }
        for (String str2 : f114b) {
            try {
                if (str2.equals("BuglyCrashModule")) {
                    C0585b.m14a(CrashModule.getInstance());
                } else if (!str2.equals("BuglyBetaModule") && !str2.equals("BuglyRqdModule")) {
                    str2.equals("BuglyFeedbackModule");
                }
            } catch (Throwable th) {
                C0657x.m465b(th);
            }
        }
        C0585b.f143a = enable;
        C0585b.m13a(applicationContext, str, z, buglyStrategy);
    }

    public static synchronized String getAppChannel() {
        byte[] bArr;
        C0593a c0593aM65b = C0593a.m65b();
        if (c0593aM65b == null) {
            return null;
        }
        if (TextUtils.isEmpty(c0593aM65b.f254l)) {
            C0649p c0649pM402a = C0649p.m402a();
            if (c0649pM402a == null) {
                return c0593aM65b.f254l;
            }
            Map<String, byte[]> mapM420a = c0649pM402a.m420a(556, (InterfaceC0648o) null, true);
            if (mapM420a != null && (bArr = mapM420a.get("app_channel")) != null) {
                return new String(bArr);
            }
        }
        return c0593aM65b.f254l;
    }

    public static boolean isDev() {
        if (isDev == null) {
            isDev = Boolean.valueOf(Boolean.parseBoolean(SDK_IS_DEV.replace("@", "")));
        }
        return isDev.booleanValue();
    }
}
