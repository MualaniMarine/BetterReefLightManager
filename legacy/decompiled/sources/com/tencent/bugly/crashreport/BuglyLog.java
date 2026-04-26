package com.tencent.bugly.crashreport;

import android.util.Log;
import com.tencent.bugly.C0585b;
import com.tencent.bugly.proguard.C0658y;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class BuglyLog {
    /* JADX INFO: renamed from: v */
    public static void m20v(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C0585b.f145c) {
            Log.v(str, str2);
        }
        C0658y.m472a("V", str, str2);
    }

    /* JADX INFO: renamed from: d */
    public static void m16d(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C0585b.f145c) {
            Log.d(str, str2);
        }
        C0658y.m472a("D", str, str2);
    }

    /* JADX INFO: renamed from: i */
    public static void m19i(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C0585b.f145c) {
            Log.i(str, str2);
        }
        C0658y.m472a("I", str, str2);
    }

    /* JADX INFO: renamed from: w */
    public static void m21w(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C0585b.f145c) {
            Log.w(str, str2);
        }
        C0658y.m472a("W", str, str2);
    }

    /* JADX INFO: renamed from: e */
    public static void m17e(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C0585b.f145c) {
            Log.e(str, str2);
        }
        C0658y.m472a("E", str, str2);
    }

    /* JADX INFO: renamed from: e */
    public static void m18e(String str, String str2, Throwable th) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C0585b.f145c) {
            Log.e(str, str2, th);
        }
        C0658y.m473a("E", str, th);
    }

    public static void setCache(int i) {
        C0658y.m470a(i);
    }
}
