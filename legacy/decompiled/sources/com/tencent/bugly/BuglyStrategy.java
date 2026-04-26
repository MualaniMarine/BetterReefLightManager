package com.tencent.bugly;

import com.tencent.bugly.crashreport.common.info.C0593a;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class BuglyStrategy {

    /* JADX INFO: renamed from: c */
    private String f118c;

    /* JADX INFO: renamed from: d */
    private String f119d;

    /* JADX INFO: renamed from: e */
    private String f120e;

    /* JADX INFO: renamed from: f */
    private long f121f;

    /* JADX INFO: renamed from: g */
    private String f122g;

    /* JADX INFO: renamed from: h */
    private String f123h;

    /* JADX INFO: renamed from: i */
    private String f124i;

    /* JADX INFO: renamed from: t */
    private C0583a f135t;

    /* JADX INFO: renamed from: j */
    private boolean f125j = true;

    /* JADX INFO: renamed from: k */
    private boolean f126k = true;

    /* JADX INFO: renamed from: l */
    private boolean f127l = false;

    /* JADX INFO: renamed from: m */
    private boolean f128m = true;

    /* JADX INFO: renamed from: n */
    private Class<?> f129n = null;

    /* JADX INFO: renamed from: o */
    private boolean f130o = true;

    /* JADX INFO: renamed from: p */
    private boolean f131p = true;

    /* JADX INFO: renamed from: q */
    private boolean f132q = true;

    /* JADX INFO: renamed from: r */
    private boolean f133r = true;

    /* JADX INFO: renamed from: s */
    private boolean f134s = false;

    /* JADX INFO: renamed from: a */
    protected int f116a = 31;

    /* JADX INFO: renamed from: b */
    protected boolean f117b = false;

    /* JADX INFO: renamed from: u */
    private boolean f136u = true;

    public synchronized BuglyStrategy setBuglyLogUpload(boolean z) {
        this.f130o = z;
        return this;
    }

    public synchronized BuglyStrategy setRecordUserInfoOnceADay(boolean z) {
        this.f134s = z;
        return this;
    }

    public synchronized BuglyStrategy setUploadProcess(boolean z) {
        this.f132q = z;
        return this;
    }

    public synchronized boolean isUploadProcess() {
        return this.f132q;
    }

    public synchronized boolean isBuglyLogUpload() {
        return this.f130o;
    }

    public synchronized boolean recordUserInfoOnceADay() {
        return this.f134s;
    }

    public boolean isReplaceOldChannel() {
        return this.f131p;
    }

    public void setReplaceOldChannel(boolean z) {
        this.f131p = z;
    }

    public synchronized String getAppVersion() {
        if (this.f118c == null) {
            return C0593a.m65b().f252j;
        }
        return this.f118c;
    }

    public synchronized BuglyStrategy setAppVersion(String str) {
        this.f118c = str;
        return this;
    }

    public synchronized BuglyStrategy setUserInfoActivity(Class<?> cls) {
        this.f129n = cls;
        return this;
    }

    public synchronized Class<?> getUserInfoActivity() {
        return this.f129n;
    }

    public synchronized String getAppChannel() {
        if (this.f119d == null) {
            return C0593a.m65b().f254l;
        }
        return this.f119d;
    }

    public synchronized BuglyStrategy setAppChannel(String str) {
        this.f119d = str;
        return this;
    }

    public synchronized String getAppPackageName() {
        if (this.f120e == null) {
            return C0593a.m65b().f245c;
        }
        return this.f120e;
    }

    public synchronized BuglyStrategy setAppPackageName(String str) {
        this.f120e = str;
        return this;
    }

    public synchronized long getAppReportDelay() {
        return this.f121f;
    }

    public synchronized BuglyStrategy setAppReportDelay(long j) {
        this.f121f = j;
        return this;
    }

    public synchronized String getLibBuglySOFilePath() {
        return this.f122g;
    }

    public synchronized BuglyStrategy setLibBuglySOFilePath(String str) {
        this.f122g = str;
        return this;
    }

    public synchronized String getDeviceID() {
        return this.f123h;
    }

    public synchronized BuglyStrategy setDeviceID(String str) {
        this.f123h = str;
        return this;
    }

    public synchronized String getDeviceModel() {
        return this.f124i;
    }

    public synchronized BuglyStrategy setDeviceModel(String str) {
        this.f124i = str;
        return this;
    }

    public synchronized boolean isEnableNativeCrashMonitor() {
        return this.f125j;
    }

    public synchronized BuglyStrategy setEnableNativeCrashMonitor(boolean z) {
        this.f125j = z;
        return this;
    }

    public synchronized BuglyStrategy setEnableUserInfo(boolean z) {
        this.f128m = z;
        return this;
    }

    public synchronized boolean isEnableUserInfo() {
        return this.f128m;
    }

    public synchronized boolean isEnableCatchAnrTrace() {
        return this.f127l;
    }

    public void setEnableCatchAnrTrace(boolean z) {
        this.f127l = z;
    }

    public synchronized boolean isEnableANRCrashMonitor() {
        return this.f126k;
    }

    public synchronized BuglyStrategy setEnableANRCrashMonitor(boolean z) {
        this.f126k = z;
        return this;
    }

    public synchronized C0583a getCrashHandleCallback() {
        return this.f135t;
    }

    public synchronized BuglyStrategy setCrashHandleCallback(C0583a c0583a) {
        this.f135t = c0583a;
        return this;
    }

    public synchronized void setCallBackType(int i) {
        this.f116a = i;
    }

    public synchronized int getCallBackType() {
        return this.f116a;
    }

    public synchronized void setCloseErrorCallback(boolean z) {
        this.f117b = z;
    }

    public synchronized boolean getCloseErrorCallback() {
        return this.f117b;
    }

    public boolean isMerged() {
        return this.f136u;
    }

    public void setMerged(boolean z) {
        this.f136u = z;
    }

    public synchronized void setUploadSpotCrash(boolean z) {
        this.f133r = z;
    }

    public synchronized boolean isUploadSpotCrash() {
        return this.f133r;
    }

    /* JADX INFO: renamed from: com.tencent.bugly.BuglyStrategy$a */
    /* JADX INFO: compiled from: BUGLY */
    public static class C0583a {
        public static final int CRASHTYPE_ANR = 4;
        public static final int CRASHTYPE_BLOCK = 7;
        public static final int CRASHTYPE_COCOS2DX_JS = 5;
        public static final int CRASHTYPE_COCOS2DX_LUA = 6;
        public static final int CRASHTYPE_JAVA_CATCH = 1;
        public static final int CRASHTYPE_JAVA_CRASH = 0;
        public static final int CRASHTYPE_NATIVE = 2;
        public static final int CRASHTYPE_U3D = 3;
        public static final int MAX_USERDATA_KEY_LENGTH = 100;
        public static final int MAX_USERDATA_VALUE_LENGTH = 100000;

        public synchronized Map<String, String> onCrashHandleStart(int i, String str, String str2, String str3) {
            return null;
        }

        public synchronized byte[] onCrashHandleStart2GetExtraDatas(int i, String str, String str2, String str3) {
            return null;
        }
    }
}
