package com.tencent.bugly.proguard;

import android.util.Log;
import java.util.Locale;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.x */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0657x {

    /* JADX INFO: renamed from: a */
    public static String f752a = "CrashReport";

    /* JADX INFO: renamed from: b */
    public static boolean f753b = false;

    /* JADX INFO: renamed from: c */
    private static String f754c = "CrashReportInfo";

    /* JADX INFO: renamed from: a */
    private static boolean m459a(int i, String str, Object... objArr) {
        if (!f753b) {
            return false;
        }
        if (str == null) {
            str = "null";
        } else if (objArr != null && objArr.length != 0) {
            str = String.format(Locale.US, str, objArr);
        }
        if (i == 0) {
            Log.i(f752a, str);
            return true;
        }
        if (i == 1) {
            Log.d(f752a, str);
            return true;
        }
        if (i == 2) {
            Log.w(f752a, str);
            return true;
        }
        if (i == 3) {
            Log.e(f752a, str);
            return true;
        }
        if (i != 5) {
            return false;
        }
        Log.i(f754c, str);
        return true;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m461a(String str, Object... objArr) {
        return m459a(0, str, objArr);
    }

    /* JADX INFO: renamed from: a */
    public static boolean m460a(Class cls, String str, Object... objArr) {
        return m459a(0, String.format(Locale.US, "[%s] %s", cls.getSimpleName(), str), objArr);
    }

    /* JADX INFO: renamed from: b */
    public static boolean m464b(String str, Object... objArr) {
        return m459a(5, str, objArr);
    }

    /* JADX INFO: renamed from: c */
    public static boolean m466c(String str, Object... objArr) {
        return m459a(1, str, objArr);
    }

    /* JADX INFO: renamed from: b */
    public static boolean m463b(Class cls, String str, Object... objArr) {
        return m459a(1, String.format(Locale.US, "[%s] %s", cls.getSimpleName(), str), objArr);
    }

    /* JADX INFO: renamed from: d */
    public static boolean m467d(String str, Object... objArr) {
        return m459a(2, str, objArr);
    }

    /* JADX INFO: renamed from: a */
    public static boolean m462a(Throwable th) {
        if (f753b) {
            return m459a(2, C0659z.m497a(th), new Object[0]);
        }
        return false;
    }

    /* JADX INFO: renamed from: e */
    public static boolean m468e(String str, Object... objArr) {
        return m459a(3, str, objArr);
    }

    /* JADX INFO: renamed from: b */
    public static boolean m465b(Throwable th) {
        if (f753b) {
            return m459a(3, C0659z.m497a(th), new Object[0]);
        }
        return false;
    }
}
