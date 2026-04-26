package com.tencent.bugly.crashreport.crash.anr;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.FileObserver;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.C0594b;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.crash.C0606b;
import com.tencent.bugly.crashreport.crash.C0607c;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.anr.TraceFileHelper;
import com.tencent.bugly.proguard.C0618ab;
import com.tencent.bugly.proguard.C0649p;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0658y;
import com.tencent.bugly.proguard.C0659z;
import com.tencent.bugly.proguard.InterfaceC0619ac;
import com.tencent.bugly.proguard.RunnableC0617aa;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.anr.b */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0605b implements InterfaceC0619ac {

    /* JADX INFO: renamed from: m */
    private static C0605b f382m;

    /* JADX INFO: renamed from: c */
    private final Context f385c;

    /* JADX INFO: renamed from: d */
    private final C0593a f386d;

    /* JADX INFO: renamed from: e */
    private final C0656w f387e;

    /* JADX INFO: renamed from: f */
    private String f388f;

    /* JADX INFO: renamed from: g */
    private final C0606b f389g;

    /* JADX INFO: renamed from: h */
    private FileObserver f390h;

    /* JADX INFO: renamed from: j */
    private C0618ab f392j;

    /* JADX INFO: renamed from: k */
    private int f393k;

    /* JADX INFO: renamed from: a */
    private AtomicInteger f383a = new AtomicInteger(0);

    /* JADX INFO: renamed from: b */
    private long f384b = -1;

    /* JADX INFO: renamed from: i */
    private boolean f391i = true;

    /* JADX INFO: renamed from: l */
    private ActivityManager.ProcessErrorStateInfo f394l = new ActivityManager.ProcessErrorStateInfo();

    /* JADX INFO: renamed from: a */
    static /* synthetic */ boolean m165a(C0605b c0605b, String str) {
        return str.startsWith("bugly_trace_");
    }

    /* JADX INFO: renamed from: a */
    public static C0605b m162a(Context context, C0596a c0596a, C0593a c0593a, C0656w c0656w, C0649p c0649p, C0606b c0606b, BuglyStrategy.C0583a c0583a) {
        if (f382m == null) {
            f382m = new C0605b(context, c0596a, c0593a, c0656w, c0606b);
        }
        return f382m;
    }

    private C0605b(Context context, C0596a c0596a, C0593a c0593a, C0656w c0656w, C0606b c0606b) {
        this.f385c = C0659z.m486a(context);
        this.f388f = context.getDir("bugly", 0).getAbsolutePath();
        this.f386d = c0593a;
        this.f387e = c0656w;
        this.f389g = c0606b;
    }

    /* JADX INFO: renamed from: a */
    private ActivityManager.ProcessErrorStateInfo m160a(Context context, long j) {
        try {
            C0657x.m466c("to find!", new Object[0]);
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int i = 0;
            while (true) {
                C0657x.m466c("waiting!", new Object[0]);
                List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = activityManager.getProcessesInErrorState();
                if (processesInErrorState != null) {
                    for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
                        if (processErrorStateInfo.condition == 2) {
                            C0657x.m466c("found!", new Object[0]);
                            return processErrorStateInfo;
                        }
                    }
                }
                C0659z.m517b(500L);
                int i2 = i + 1;
                if (i >= 40) {
                    C0657x.m466c("end!", new Object[0]);
                    return null;
                }
                i = i2;
            }
        } catch (Exception e) {
            C0657x.m465b(e);
            return null;
        } catch (OutOfMemoryError e2) {
            this.f394l.pid = Process.myPid();
            this.f394l.shortMsg = "bugly sdk waitForAnrProcessStateChanged encount error:" + e2.getMessage();
            return this.f394l;
        }
    }

    /* JADX INFO: renamed from: a */
    private CrashDetailBean m161a(C0604a c0604a) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.f312C = C0594b.m124g();
            crashDetailBean.f313D = C0594b.m120e();
            crashDetailBean.f314E = C0594b.m128i();
            crashDetailBean.f315F = this.f386d.m96l();
            crashDetailBean.f316G = this.f386d.m95k();
            crashDetailBean.f317H = this.f386d.m97m();
            if (!C0594b.m132m()) {
                crashDetailBean.f356w = C0659z.m494a(this.f385c, C0607c.f415e, (String) null);
            }
            crashDetailBean.f335b = 3;
            crashDetailBean.f338e = this.f386d.m91h();
            crashDetailBean.f339f = this.f386d.f252j;
            crashDetailBean.f340g = this.f386d.m102r();
            crashDetailBean.f346m = this.f386d.m89g();
            crashDetailBean.f347n = "ANR_EXCEPTION";
            crashDetailBean.f348o = c0604a.f380f;
            crashDetailBean.f350q = c0604a.f381g;
            crashDetailBean.f325P = new HashMap();
            crashDetailBean.f325P.put("BUGLY_CR_01", c0604a.f379e);
            int iIndexOf = crashDetailBean.f350q != null ? crashDetailBean.f350q.indexOf("\n") : -1;
            crashDetailBean.f349p = iIndexOf > 0 ? crashDetailBean.f350q.substring(0, iIndexOf) : "GET_FAIL";
            crashDetailBean.f351r = c0604a.f377c;
            if (crashDetailBean.f350q != null) {
                crashDetailBean.f354u = C0659z.m499a(crashDetailBean.f350q.getBytes());
            }
            crashDetailBean.f359z = c0604a.f376b;
            crashDetailBean.f310A = c0604a.f375a;
            crashDetailBean.f311B = "main(1)";
            crashDetailBean.f318I = this.f386d.m104t();
            crashDetailBean.f341h = this.f386d.m101q();
            crashDetailBean.f342i = this.f386d.m68C();
            crashDetailBean.f355v = c0604a.f378d;
            crashDetailBean.f321L = this.f386d.f256n;
            crashDetailBean.f322M = this.f386d.f228a;
            crashDetailBean.f323N = this.f386d.m75a();
            if (!C0594b.m132m()) {
                this.f389g.m202d(crashDetailBean);
            }
            crashDetailBean.f326Q = this.f386d.m66A();
            crashDetailBean.f327R = this.f386d.m67B();
            crashDetailBean.f328S = this.f386d.m105u();
            crashDetailBean.f329T = this.f386d.m110z();
            crashDetailBean.f358y = C0658y.m474a();
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    /* JADX INFO: renamed from: a */
    private static boolean m166a(String str, String str2, String str3) throws Throwable {
        Throwable th;
        TraceFileHelper.C0602a targetDumpInfo = TraceFileHelper.readTargetDumpInfo(str3, str, true);
        if (targetDumpInfo == null || targetDumpInfo.f374d == null || targetDumpInfo.f374d.size() <= 0) {
            C0657x.m468e("not found trace dump for %s", str3);
            return false;
        }
        File file = new File(str2);
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            if (!file.exists() || !file.canWrite()) {
                C0657x.m468e("backup file create fail %s", str2);
                return false;
            }
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file, false));
                    try {
                        String[] strArr = targetDumpInfo.f374d.get("main");
                        int i = 3;
                        if (strArr != null && strArr.length >= 3) {
                            String str4 = strArr[0];
                            String str5 = strArr[1];
                            bufferedWriter2.write("\"main\" tid=" + strArr[2] + " :\n" + str4 + "\n" + str5 + "\n\n");
                            bufferedWriter2.flush();
                        }
                        for (Map.Entry<String, String[]> entry : targetDumpInfo.f374d.entrySet()) {
                            if (!entry.getKey().equals("main")) {
                                if (entry.getValue() != null && entry.getValue().length >= i) {
                                    String str6 = entry.getValue()[0];
                                    String str7 = entry.getValue()[1];
                                    bufferedWriter2.write("\"" + entry.getKey() + "\" tid=" + entry.getValue()[2] + " :\n" + str6 + "\n" + str7 + "\n\n");
                                    bufferedWriter2.flush();
                                }
                                i = 3;
                            }
                        }
                        try {
                            bufferedWriter2.close();
                        } catch (IOException e) {
                            if (!C0657x.m462a(e)) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    } catch (IOException e2) {
                        e = e2;
                        bufferedWriter = bufferedWriter2;
                        if (!C0657x.m462a(e)) {
                            e.printStackTrace();
                        }
                        C0657x.m468e("dump trace fail %s", e.getClass().getName() + ":" + e.getMessage());
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException e3) {
                                if (!C0657x.m462a(e3)) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                                throw th;
                            } catch (IOException e4) {
                                if (!C0657x.m462a(e4)) {
                                    e4.printStackTrace();
                                    throw th;
                                }
                                throw th;
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e = e5;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e6) {
            if (!C0657x.m462a(e6)) {
                e6.printStackTrace();
            }
            C0657x.m468e("backup file create error! %s  %s", e6.getClass().getName() + ":" + e6.getMessage(), str2);
            return false;
        }
    }

    /* JADX INFO: renamed from: a */
    public final boolean m179a() {
        return this.f383a.get() != 0;
    }

    /* JADX INFO: renamed from: a */
    private boolean m164a(Context context, String str, ActivityManager.ProcessErrorStateInfo processErrorStateInfo, long j, Map<String, String> map) {
        C0604a c0604a = new C0604a();
        c0604a.f377c = j;
        c0604a.f375a = processErrorStateInfo != null ? processErrorStateInfo.processName : AppInfo.m55a(Process.myPid());
        c0604a.f380f = processErrorStateInfo != null ? processErrorStateInfo.shortMsg : "";
        c0604a.f379e = processErrorStateInfo != null ? processErrorStateInfo.longMsg : "";
        c0604a.f376b = map;
        Thread thread = Looper.getMainLooper().getThread();
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                if (next.startsWith(thread.getName())) {
                    c0604a.f381g = map.get(next);
                    break;
                }
            }
        }
        if (TextUtils.isEmpty(c0604a.f381g)) {
            c0604a.f381g = "main stack is null , some error may be encountered.";
        }
        Object[] objArr = new Object[7];
        objArr[0] = Long.valueOf(c0604a.f377c);
        objArr[1] = c0604a.f378d;
        objArr[2] = c0604a.f375a;
        objArr[3] = c0604a.f381g;
        objArr[4] = c0604a.f380f;
        objArr[5] = c0604a.f379e;
        objArr[6] = Integer.valueOf(c0604a.f376b == null ? 0 : c0604a.f376b.size());
        C0657x.m466c("anr tm:%d\ntr:%s\nproc:%s\nmain stack:%s\nsMsg:%s\n lMsg:%s\n threads:%d", objArr);
        C0657x.m461a("found visiable anr , start to upload!", new Object[0]);
        CrashDetailBean crashDetailBeanM161a = m161a(c0604a);
        if (crashDetailBeanM161a == null) {
            C0657x.m468e("pack anr fail!", new Object[0]);
            return false;
        }
        C0607c.m204a().m211a(crashDetailBeanM161a);
        if (crashDetailBeanM161a.f334a >= 0) {
            C0657x.m461a("backup anr record success!", new Object[0]);
        } else {
            C0657x.m467d("backup anr record fail!", new Object[0]);
        }
        if (str != null && new File(str).exists()) {
            c0604a.f378d = new File(this.f388f, "bugly_trace_" + j + ".txt").getAbsolutePath();
            this.f383a.set(3);
            if (m166a(str, c0604a.f378d, c0604a.f375a)) {
                C0657x.m461a("backup trace success", new Object[0]);
            }
        } else {
            File fileM174h = m174h();
            C0657x.m461a("traceFile is %s", fileM174h);
            if (fileM174h != null) {
                crashDetailBeanM161a.f355v = fileM174h.getAbsolutePath();
            }
        }
        C0606b.m188a("ANR", C0659z.m492a(), c0604a.f375a, "main", c0604a.f381g, crashDetailBeanM161a);
        if (!this.f389g.m199a(crashDetailBeanM161a)) {
            this.f389g.m197a(crashDetailBeanM161a, 3000L, true);
        }
        this.f389g.m201c(crashDetailBeanM161a);
        return true;
    }

    /* JADX INFO: renamed from: a */
    public final void m177a(String str) {
        synchronized (this) {
            if (this.f383a.get() != 0) {
                C0657x.m466c("trace started return ", new Object[0]);
                return;
            }
            this.f383a.set(1);
            try {
                C0657x.m466c("read trace first dump for create time!", new Object[0]);
                TraceFileHelper.C0602a firstDumpInfo = TraceFileHelper.readFirstDumpInfo(str, false);
                long jCurrentTimeMillis = firstDumpInfo != null ? firstDumpInfo.f373c : -1L;
                if (jCurrentTimeMillis == -1) {
                    C0657x.m467d("trace dump fail could not get time!", new Object[0]);
                    jCurrentTimeMillis = System.currentTimeMillis();
                }
                long j = jCurrentTimeMillis;
                if (Math.abs(j - this.f384b) < 10000) {
                    C0657x.m467d("should not process ANR too Fre in %d", 10000);
                } else {
                    this.f384b = j;
                    this.f383a.set(1);
                    try {
                        Map<String, String> mapM502a = C0659z.m502a(C0607c.f416f, false);
                        if (mapM502a == null || mapM502a.size() <= 0) {
                            C0657x.m467d("can't get all thread skip this anr", new Object[0]);
                        } else {
                            ActivityManager.ProcessErrorStateInfo processErrorStateInfoM160a = m160a(this.f385c, 20000L);
                            this.f394l = processErrorStateInfoM160a;
                            if (processErrorStateInfoM160a == null) {
                                C0657x.m466c("proc state is unvisiable!", new Object[0]);
                            } else if (processErrorStateInfoM160a.pid != Process.myPid()) {
                                C0657x.m466c("not mind proc!", this.f394l.processName);
                            } else {
                                C0657x.m461a("found visiable anr , start to process!", new Object[0]);
                                m164a(this.f385c, str, this.f394l, j, mapM502a);
                            }
                        }
                    } catch (Throwable th) {
                        C0657x.m462a(th);
                        C0657x.m468e("get all thread stack fail!", new Object[0]);
                    }
                }
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    /* JADX INFO: renamed from: d */
    private synchronized void m170d() {
        if (m172f()) {
            C0657x.m467d("start when started!", new Object[0]);
            return;
        }
        FileObserver fileObserver = new FileObserver("/data/anr/", 8) { // from class: com.tencent.bugly.crashreport.crash.anr.b.1
            @Override // android.os.FileObserver
            public final void onEvent(int i, String str) {
                if (str == null) {
                    return;
                }
                final String str2 = "/data/anr/" + str;
                C0657x.m467d("watching file %s", str2);
                if (str2.contains("trace")) {
                    C0605b.this.f387e.m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.anr.b.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            C0605b.this.m177a(str2);
                        }
                    });
                } else {
                    C0657x.m467d("not anr file %s", str2);
                }
            }
        };
        this.f390h = fileObserver;
        try {
            fileObserver.startWatching();
            C0657x.m461a("start anr monitor!", new Object[0]);
            this.f387e.m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.anr.b.2
                @Override // java.lang.Runnable
                public final void run() {
                    C0605b.this.m181b();
                }
            });
        } catch (Throwable th) {
            this.f390h = null;
            C0657x.m467d("start anr monitor failed!", new Object[0]);
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: e */
    private synchronized void m171e() {
        if (!m172f()) {
            C0657x.m467d("close when closed!", new Object[0]);
            return;
        }
        try {
            this.f390h.stopWatching();
            this.f390h = null;
            C0657x.m467d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            C0657x.m467d("stop anr monitor failed!", new Object[0]);
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: f */
    private synchronized boolean m172f() {
        return this.f390h != null;
    }

    /* JADX INFO: renamed from: b */
    private synchronized void m168b(boolean z) {
        if (Build.VERSION.SDK_INT <= 19) {
            if (z) {
                m170d();
                return;
            } else {
                m171e();
                return;
            }
        }
        if (z) {
            m175i();
        } else {
            m176j();
        }
    }

    /* JADX INFO: renamed from: g */
    private synchronized boolean m173g() {
        return this.f391i;
    }

    /* JADX INFO: renamed from: c */
    private synchronized void m169c(boolean z) {
        if (this.f391i != z) {
            C0657x.m461a("user change anr %b", Boolean.valueOf(z));
            this.f391i = z;
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m178a(boolean z) {
        m169c(z);
        boolean zM173g = m173g();
        C0596a c0596aM139a = C0596a.m139a();
        if (c0596aM139a != null) {
            zM173g = zM173g && c0596aM139a.m149c().f276e;
        }
        if (zM173g != m172f()) {
            C0657x.m461a("anr changed to %b", Boolean.valueOf(zM173g));
            m168b(zM173g);
        }
    }

    /* JADX INFO: renamed from: b */
    protected final void m181b() {
        int iIndexOf;
        long jM513b = C0659z.m513b() - C0607c.f417g;
        File file = new File(this.f388f);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                    int i = 0;
                    int i2 = 0;
                    for (File file2 : fileArrListFiles) {
                        String name = file2.getName();
                        boolean z = true;
                        if (name.startsWith("bugly_trace_") || name.startsWith("bugly_trace_")) {
                            i2 = 12;
                        } else {
                            z = false;
                        }
                        C0657x.m466c("Number Trace file : " + name, new Object[0]);
                        if (z) {
                            try {
                                iIndexOf = name.indexOf(".txt");
                            } catch (Throwable unused) {
                                C0657x.m466c("Trace file that has invalid format: " + name, new Object[0]);
                            }
                            if (iIndexOf <= 0 || Long.parseLong(name.substring(i2, iIndexOf)) < jM513b) {
                                if (file2.delete()) {
                                    i++;
                                }
                            }
                        }
                    }
                    C0657x.m466c("Number of overdue trace files that has deleted: " + i, new Object[0]);
                }
            } catch (Throwable th) {
                C0657x.m462a(th);
            }
        }
    }

    /* JADX INFO: renamed from: c */
    public final synchronized void m182c() {
        C0657x.m467d("customer decides whether to open or close.", new Object[0]);
    }

    @Override // com.tencent.bugly.proguard.InterfaceC0619ac
    /* JADX INFO: renamed from: a */
    public final boolean mo180a(RunnableC0617aa runnableC0617aa) {
        Map<String, String> map = new HashMap<>();
        if (runnableC0617aa.m298e().equals(Looper.getMainLooper())) {
            try {
                map = C0659z.m502a(200000, false);
            } catch (Throwable th) {
                C0657x.m465b(th);
                map.put("main", th.getMessage());
            }
            Map<String, String> map2 = map;
            C0657x.m466c("onThreadBlock found visiable anr , start to process!", new Object[0]);
            String strM117c = C0594b.m117c(this.f385c);
            if (!TextUtils.isEmpty(strM117c) && (strM117c.contains("XiaoMi") || strM117c.contains("samsung"))) {
                this.f394l = m160a(this.f385c, 20000L);
            }
            m164a(this.f385c, "", this.f394l, System.currentTimeMillis(), map2);
        } else {
            C0657x.m466c("anr handler onThreadBlock only care main thread ,current thread is: %s", runnableC0617aa.m297d());
        }
        return true;
    }

    /* JADX INFO: renamed from: h */
    private File m174h() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        File file = new File(this.f388f);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                    int i = 24;
                    int length = fileArrListFiles.length;
                    int i2 = 0;
                    while (i2 < length) {
                        File file2 = fileArrListFiles[i2];
                        String name = file2.getName();
                        if (name.startsWith("jni_mannual_bugly_trace_")) {
                            try {
                                int iIndexOf = name.indexOf(".txt");
                                if (iIndexOf > 0) {
                                    long j = Long.parseLong(name.substring(i, iIndexOf));
                                    long j2 = (jCurrentTimeMillis - j) / 1000;
                                    C0657x.m466c("current time %d trace time is %d s", Long.valueOf(jCurrentTimeMillis), Long.valueOf(j));
                                    C0657x.m466c("current time minus trace time is %d s", Long.valueOf(j2));
                                    if (j2 < 30) {
                                        return file2;
                                    }
                                } else {
                                    continue;
                                }
                            } catch (Throwable unused) {
                                C0657x.m466c("Trace file that has invalid format: " + name, new Object[0]);
                            }
                        }
                        i2++;
                        i = 24;
                    }
                }
                return null;
            } catch (Throwable th) {
                C0657x.m462a(th);
                return null;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: i */
    private synchronized void m175i() {
        if (m172f()) {
            C0657x.m467d("start when started!", new Object[0]);
            return;
        }
        if (TextUtils.isEmpty(this.f388f)) {
            return;
        }
        if (this.f392j == null || !this.f392j.isAlive()) {
            C0618ab c0618ab = new C0618ab();
            this.f392j = c0618ab;
            StringBuilder sb = new StringBuilder("Bugly-ThreadMonitor");
            int i = this.f393k;
            this.f393k = i + 1;
            sb.append(i);
            c0618ab.setName(sb.toString());
            this.f392j.m301a();
            this.f392j.m302a(this);
            this.f392j.m307d();
            this.f387e.m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.anr.b.3
                @Override // java.lang.Runnable
                public final void run() {
                    C0605b.this.m181b();
                }
            });
        }
        FileObserver fileObserver = new FileObserver(this.f388f, 256) { // from class: com.tencent.bugly.crashreport.crash.anr.b.4
            @Override // android.os.FileObserver
            public final void onEvent(int i2, String str) {
                if (str == null) {
                    return;
                }
                C0657x.m467d("startWatchingPrivateAnrDir %s", str);
                if (C0605b.m165a(C0605b.this, str)) {
                    if (C0605b.this.f392j != null) {
                        C0605b.this.f392j.m303a(true);
                        return;
                    }
                    return;
                }
                C0657x.m466c("trace file not caused by sigquit , ignore ", new Object[0]);
            }
        };
        this.f390h = fileObserver;
        try {
            fileObserver.startWatching();
            C0657x.m461a("startWatchingPrivateAnrDir! dumFilePath is %s", this.f388f);
            this.f387e.m455a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.anr.b.5
                @Override // java.lang.Runnable
                public final void run() {
                    C0605b.this.m181b();
                }
            });
        } catch (Throwable th) {
            this.f390h = null;
            C0657x.m467d("startWatchingPrivateAnrDir failed!", new Object[0]);
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: j */
    private synchronized void m176j() {
        if (!m172f()) {
            C0657x.m467d("close when closed!", new Object[0]);
            return;
        }
        if (this.f392j != null) {
            this.f392j.m306c();
            this.f392j.m304b();
            this.f392j.m305b(this);
            this.f392j = null;
        }
        C0657x.m461a("stopWatchingPrivateAnrDir", new Object[0]);
        try {
            this.f390h.stopWatching();
            this.f390h = null;
            C0657x.m467d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            C0657x.m467d("stop anr monitor failed!", new Object[0]);
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }
}
