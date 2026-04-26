package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.C0594b;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.crash.C0606b;
import com.tencent.bugly.crashreport.crash.C0607c;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0658y;
import com.tencent.bugly.proguard.C0659z;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.jni.a */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0613a implements NativeExceptionHandler {

    /* JADX INFO: renamed from: a */
    private final Context f502a;

    /* JADX INFO: renamed from: b */
    private final C0606b f503b;

    /* JADX INFO: renamed from: c */
    private final C0593a f504c;

    /* JADX INFO: renamed from: d */
    private final C0596a f505d;

    public C0613a(Context context, C0593a c0593a, C0606b c0606b, C0596a c0596a) {
        this.f502a = context;
        this.f503b = c0606b;
        this.f504c = c0593a;
        this.f505d = c0596a;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final CrashDetailBean packageCrashDatas(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z, boolean z2) {
        int i;
        String str12;
        int iIndexOf;
        boolean zM226m = C0607c.m204a().m226m();
        if (zM226m) {
            C0657x.m468e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.f335b = 1;
        crashDetailBean.f338e = this.f504c.m91h();
        crashDetailBean.f339f = this.f504c.f252j;
        crashDetailBean.f340g = this.f504c.m102r();
        crashDetailBean.f346m = this.f504c.m89g();
        crashDetailBean.f347n = str3;
        crashDetailBean.f348o = zM226m ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.f349p = str4;
        crashDetailBean.f350q = str5 != null ? str5 : "";
        crashDetailBean.f351r = j;
        crashDetailBean.f354u = C0659z.m499a(crashDetailBean.f350q.getBytes());
        crashDetailBean.f310A = str;
        crashDetailBean.f311B = str2;
        crashDetailBean.f318I = this.f504c.m104t();
        crashDetailBean.f341h = this.f504c.m101q();
        crashDetailBean.f342i = this.f504c.m68C();
        crashDetailBean.f355v = str8;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        String dumpFilePath = nativeCrashHandler != null ? nativeCrashHandler.getDumpFilePath() : null;
        String strM270a = C0614b.m270a(dumpFilePath, str8);
        if (!C0659z.m509a(strM270a)) {
            crashDetailBean.f331V = strM270a;
        }
        crashDetailBean.f332W = C0614b.m272b(dumpFilePath);
        crashDetailBean.f356w = C0614b.m269a(str9, C0607c.f415e, null, false);
        crashDetailBean.f357x = C0614b.m269a(str10, C0607c.f415e, null, true);
        crashDetailBean.f319J = str7;
        crashDetailBean.f320K = str6;
        crashDetailBean.f321L = str11;
        crashDetailBean.f315F = this.f504c.m96l();
        crashDetailBean.f316G = this.f504c.m95k();
        crashDetailBean.f317H = this.f504c.m97m();
        if (z) {
            crashDetailBean.f312C = C0594b.m124g();
            crashDetailBean.f313D = C0594b.m120e();
            crashDetailBean.f314E = C0594b.m128i();
            if (crashDetailBean.f356w == null) {
                crashDetailBean.f356w = C0659z.m494a(this.f502a, C0607c.f415e, (String) null);
            }
            crashDetailBean.f358y = C0658y.m474a();
            crashDetailBean.f322M = this.f504c.f228a;
            crashDetailBean.f323N = this.f504c.m75a();
            crashDetailBean.f359z = C0659z.m502a(C0607c.f416f, false);
            int iIndexOf2 = crashDetailBean.f350q.indexOf("java:\n");
            if (iIndexOf2 > 0 && (i = iIndexOf2 + 6) < crashDetailBean.f350q.length()) {
                String strSubstring = crashDetailBean.f350q.substring(i, crashDetailBean.f350q.length() - 1);
                if (strSubstring.length() > 0 && crashDetailBean.f359z.containsKey(crashDetailBean.f311B) && (iIndexOf = (str12 = crashDetailBean.f359z.get(crashDetailBean.f311B)).indexOf(strSubstring)) > 0) {
                    String strSubstring2 = str12.substring(iIndexOf);
                    crashDetailBean.f359z.put(crashDetailBean.f311B, strSubstring2);
                    crashDetailBean.f350q = crashDetailBean.f350q.substring(0, i);
                    crashDetailBean.f350q += strSubstring2;
                }
            }
            if (str == null) {
                crashDetailBean.f310A = this.f504c.f246d;
            }
            this.f503b.m202d(crashDetailBean);
            crashDetailBean.f326Q = this.f504c.m66A();
            crashDetailBean.f327R = this.f504c.m67B();
            crashDetailBean.f328S = this.f504c.m105u();
            crashDetailBean.f329T = this.f504c.m110z();
        } else {
            crashDetailBean.f312C = -1L;
            crashDetailBean.f313D = -1L;
            crashDetailBean.f314E = -1L;
            if (crashDetailBean.f356w == null) {
                crashDetailBean.f356w = "this crash is occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.f322M = -1L;
            crashDetailBean.f326Q = -1;
            crashDetailBean.f327R = -1;
            crashDetailBean.f328S = map;
            crashDetailBean.f329T = this.f504c.m110z();
            crashDetailBean.f359z = null;
            if (str == null) {
                crashDetailBean.f310A = "unknown(record)";
            }
            if (bArr != null) {
                crashDetailBean.f358y = bArr;
            }
        }
        return crashDetailBean;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final void handleNativeException(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7) {
        C0657x.m461a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i, i2, j, j2, str, str2, str3, str4, i3, str5, i4, i5, i6, str6, str7, null);
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final void handleNativeException2(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7, String[] strArr) {
        String str8;
        String str9;
        String str10;
        boolean z;
        boolean z2;
        C0657x.m461a("Native Crash Happen v2", new Object[0]);
        try {
            String strM268a = C0614b.m268a(str3);
            if (i3 > 0) {
                str9 = str + "(" + str5 + ")";
                str8 = "UNKNOWN";
                str10 = "KERNEL";
            } else {
                String strM55a = i4 > 0 ? AppInfo.m55a(i4) : "UNKNOWN";
                str8 = strM55a.equals(String.valueOf(i4)) ? strM55a : strM55a + "(" + i4 + ")";
                str9 = str;
                str10 = str5;
            }
            HashMap map = new HashMap();
            if (strArr != null) {
                for (int i7 = 0; i7 < strArr.length; i7++) {
                    String str11 = strArr[i7];
                    if (str11 != null) {
                        C0657x.m461a("Extra message[%d]: %s", Integer.valueOf(i7), str11);
                        String[] strArrSplit = str11.split("=");
                        if (strArrSplit.length == 2) {
                            map.put(strArrSplit[0], strArrSplit[1]);
                        } else {
                            C0657x.m467d("bad extraMsg %s", str11);
                        }
                    }
                }
            } else {
                C0657x.m466c("not found extraMsg", new Object[0]);
            }
            String str12 = (String) map.get("HasPendingException");
            if (str12 == null || !str12.equals("true")) {
                z = false;
            } else {
                C0657x.m461a("Native crash happened with a Java pending exception.", new Object[0]);
                z = true;
            }
            String str13 = (String) map.get("ExceptionProcessName");
            if (str13 == null || str13.length() == 0) {
                str13 = this.f504c.f246d;
            } else {
                C0657x.m466c("Name of crash process: %s", str13);
            }
            String str14 = str13;
            String str15 = (String) map.get("ExceptionThreadName");
            if (str15 == null || str15.length() == 0) {
                Thread threadCurrentThread = Thread.currentThread();
                str15 = threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
            } else {
                C0657x.m466c("Name of crash thread: %s", str15);
                Iterator<Thread> it = Thread.getAllStackTraces().keySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    Thread next = it.next();
                    if (next.getName().equals(str15)) {
                        str15 = str15 + "(" + next.getId() + ")";
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    str15 = str15 + "(" + i2 + ")";
                }
            }
            String str16 = str15;
            long j3 = (j * 1000) + (j2 / 1000);
            String str17 = (String) map.get("SysLogPath");
            String str18 = (String) map.get("JniLogPath");
            if (!this.f505d.m148b()) {
                C0657x.m467d("no remote but still store!", new Object[0]);
            }
            if (!this.f505d.m149c().f276e && this.f505d.m148b()) {
                C0657x.m468e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                C0606b.m188a("NATIVE_CRASH", C0659z.m492a(), str14, str16, str9 + "\n" + str2 + "\n" + strM268a, null);
                C0659z.m519b(str4);
                return;
            }
            String str19 = str9;
            try {
                CrashDetailBean crashDetailBeanPackageCrashDatas = packageCrashDatas(str14, str16, j3, str9, str2, strM268a, str10, str8, str4, str17, str18, str7, null, null, true, z);
                if (crashDetailBeanPackageCrashDatas == null) {
                    C0657x.m468e("pkg crash datas fail!", new Object[0]);
                    return;
                }
                C0606b.m188a("NATIVE_CRASH", C0659z.m492a(), str14, str16, str19 + "\n" + str2 + "\n" + strM268a, crashDetailBeanPackageCrashDatas);
                try {
                    boolean z3 = !this.f503b.m200b(crashDetailBeanPackageCrashDatas);
                    NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
                    C0614b.m271a(true, nativeCrashHandler != null ? nativeCrashHandler.getDumpFilePath() : null);
                    if (z3) {
                        this.f503b.m197a(crashDetailBeanPackageCrashDatas, 3000L, true);
                    }
                    this.f503b.m201c(crashDetailBeanPackageCrashDatas);
                    C0607c.m204a().m218e();
                    return;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        if (C0657x.m462a(th)) {
            return;
        }
        th.printStackTrace();
    }
}
