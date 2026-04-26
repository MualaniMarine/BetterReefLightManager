package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver;
import com.tencent.bugly.crashreport.crash.C0607c;
import com.tencent.bugly.crashreport.crash.C0608d;
import com.tencent.bugly.proguard.C0647n;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.InterfaceC0648o;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class CrashModule extends AbstractC0584a {
    public static final int MODULE_ID = 1004;

    /* JADX INFO: renamed from: c */
    private static int f137c;

    /* JADX INFO: renamed from: e */
    private static CrashModule f138e = new CrashModule();

    /* JADX INFO: renamed from: a */
    private long f139a;

    /* JADX INFO: renamed from: b */
    private BuglyStrategy.C0583a f140b;

    /* JADX INFO: renamed from: d */
    private boolean f141d = false;

    public static CrashModule getInstance() {
        f138e.f142id = 1004;
        return f138e;
    }

    public synchronized boolean hasInitialized() {
        return this.f141d;
    }

    @Override // com.tencent.bugly.AbstractC0584a
    public synchronized void init(Context context, boolean z, BuglyStrategy buglyStrategy) {
        if (context != null) {
            if (!this.f141d) {
                C0657x.m461a("Initializing crash module.", new Object[0]);
                C0647n c0647nM380a = C0647n.m380a();
                int i = f137c + 1;
                f137c = i;
                c0647nM380a.m391a(1004, i);
                this.f141d = true;
                CrashReport.setContext(context);
                m10a(context, buglyStrategy);
                C0607c c0607cM205a = C0607c.m205a(1004, context, z, this.f140b, (InterfaceC0648o) null, (String) null);
                c0607cM205a.m219f();
                if (buglyStrategy != null) {
                    c0607cM205a.m208a(buglyStrategy.getCallBackType());
                    c0607cM205a.m213a(buglyStrategy.getCloseErrorCallback());
                    C0607c.f422l = buglyStrategy.isUploadSpotCrash();
                }
                if (buglyStrategy != null && buglyStrategy.isEnableCatchAnrTrace()) {
                    c0607cM205a.m224k();
                }
                c0607cM205a.m228o();
                if (buglyStrategy == null || buglyStrategy.isEnableNativeCrashMonitor()) {
                    c0607cM205a.m221h();
                } else {
                    C0657x.m461a("[crash] Closed native crash monitor!", new Object[0]);
                    c0607cM205a.m220g();
                }
                if (buglyStrategy == null || buglyStrategy.isEnableANRCrashMonitor()) {
                    c0607cM205a.m222i();
                } else {
                    C0657x.m461a("[crash] Closed ANR monitor!", new Object[0]);
                    c0607cM205a.m223j();
                }
                if (buglyStrategy != null) {
                    C0607c.f414d = buglyStrategy.isMerged();
                }
                c0607cM205a.m209a(buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0L);
                c0607cM205a.m227n();
                C0608d.m236a(context);
                BuglyBroadcastReceiver buglyBroadcastReceiver = BuglyBroadcastReceiver.getInstance();
                buglyBroadcastReceiver.addFilter("android.net.conn.CONNECTIVITY_CHANGE");
                buglyBroadcastReceiver.register(context);
                C0647n c0647nM380a2 = C0647n.m380a();
                int i2 = f137c - 1;
                f137c = i2;
                c0647nM380a2.m391a(1004, i2);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private synchronized void m10a(Context context, BuglyStrategy buglyStrategy) {
        if (buglyStrategy == null) {
            return;
        }
        String libBuglySOFilePath = buglyStrategy.getLibBuglySOFilePath();
        if (!TextUtils.isEmpty(libBuglySOFilePath)) {
            C0593a.m64a(context).f255m = libBuglySOFilePath;
            C0657x.m461a("setted libBugly.so file path :%s", libBuglySOFilePath);
        }
        if (buglyStrategy.getCrashHandleCallback() != null) {
            this.f140b = buglyStrategy.getCrashHandleCallback();
            C0657x.m461a("setted CrashHanldeCallback", new Object[0]);
        }
        if (buglyStrategy.getAppReportDelay() > 0) {
            long appReportDelay = buglyStrategy.getAppReportDelay();
            this.f139a = appReportDelay;
            C0657x.m461a("setted delay: %d", Long.valueOf(appReportDelay));
        }
    }

    @Override // com.tencent.bugly.AbstractC0584a
    public void onServerStrategyChanged(StrategyBean strategyBean) {
        C0607c c0607cM204a;
        if (strategyBean == null || (c0607cM204a = C0607c.m204a()) == null) {
            return;
        }
        c0607cM204a.m210a(strategyBean);
    }

    @Override // com.tencent.bugly.AbstractC0584a
    public String[] getTables() {
        return new String[]{"t_cr"};
    }
}
