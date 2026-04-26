package com.tencent.bugly.crashreport;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.core.os.EnvironmentCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.C0585b;
import com.tencent.bugly.CrashModule;
import com.tencent.bugly.crashreport.biz.C0591b;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver;
import com.tencent.bugly.crashreport.crash.C0607c;
import com.tencent.bugly.crashreport.crash.C0608d;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.crashreport.crash.p002h5.C0611b;
import com.tencent.bugly.crashreport.crash.p002h5.H5JavaScriptInterface;
import com.tencent.bugly.proguard.C0616a;
import com.tencent.bugly.proguard.C0650q;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import java.net.InetAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class CrashReport {

    /* JADX INFO: renamed from: a */
    private static Context f148a;

    /* JADX INFO: compiled from: BUGLY */
    public static class CrashHandleCallback extends BuglyStrategy.C0583a {
    }

    /* JADX INFO: compiled from: BUGLY */
    public interface WebViewInterface {
        void addJavascriptInterface(H5JavaScriptInterface h5JavaScriptInterface, String str);

        CharSequence getContentDescription();

        String getUrl();

        void loadUrl(String str);

        void setJavaScriptEnabled(boolean z);
    }

    public static void enableBugly(boolean z) {
        C0585b.f143a = z;
    }

    public static void initCrashReport(Context context) {
        if (context == null) {
            return;
        }
        f148a = context;
        C0585b.m14a(CrashModule.getInstance());
        C0585b.m11a(context);
    }

    public static void initCrashReport(Context context, UserStrategy userStrategy) {
        if (context == null) {
            return;
        }
        f148a = context;
        C0585b.m14a(CrashModule.getInstance());
        C0585b.m12a(context, userStrategy);
    }

    public static void initCrashReport(Context context, String str, boolean z) {
        if (context != null) {
            f148a = context;
            C0585b.m14a(CrashModule.getInstance());
            C0585b.m13a(context, str, z, null);
        }
    }

    public static void initCrashReport(Context context, String str, boolean z, UserStrategy userStrategy) {
        if (context == null) {
            return;
        }
        f148a = context;
        C0585b.m14a(CrashModule.getInstance());
        C0585b.m13a(context, str, z, userStrategy);
    }

    public static String getBuglyVersion(Context context) {
        if (context == null) {
            C0657x.m467d("Please call with context.", new Object[0]);
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return C0593a.m64a(context).m80c();
    }

    public static void testJavaCrash() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not test Java crash because bugly is disable.");
        } else {
            if (!CrashModule.getInstance().hasInitialized()) {
                Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
                return;
            }
            C0593a c0593aM65b = C0593a.m65b();
            if (c0593aM65b != null) {
                c0593aM65b.m76b(24096);
            }
            throw new RuntimeException("This Crash create for Test! You can go to Bugly see more detail!");
        }
    }

    public static void testNativeCrash() {
        testNativeCrash(false, false, false);
    }

    public static void testNativeCrash(boolean z, boolean z2, boolean z3) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not test native crash because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C0657x.m461a("start to create a native crash for test!", new Object[0]);
            C0607c.m204a().m214a(z, z2, z3);
        }
    }

    public static void testANRCrash() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not test ANR crash because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C0657x.m461a("start to create a anr crash for test!", new Object[0]);
            C0607c.m204a().m225l();
        }
    }

    public static void postException(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not post crash caught because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C0608d.m239a(thread, i, str, str2, str3, map);
        }
    }

    public static void postException(int i, String str, String str2, String str3, Map<String, String> map) {
        postException(Thread.currentThread(), i, str, str2, str3, map);
    }

    public static void postCatchedException(Throwable th) {
        postCatchedException(th, Thread.currentThread(), false);
    }

    public static void postCatchedException(Throwable th, Thread thread) {
        postCatchedException(th, thread, false);
    }

    public static void postCatchedException(Throwable th, Thread thread, boolean z) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not post crash caught because bugly is disable.");
            return;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return;
        }
        if (th == null) {
            C0657x.m467d("throwable is null, just return", new Object[0]);
            return;
        }
        if (thread == null) {
            thread = Thread.currentThread();
        }
        C0607c.m204a().m212a(thread, th, false, (String) null, (byte[]) null, z);
    }

    public static void closeNativeReport() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not close native report because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C0607c.m204a().m220g();
        }
    }

    public static void startCrashReport() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not start crash report because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.w(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C0607c.m204a().m216c();
        }
    }

    public static void closeCrashReport() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not close crash report because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.w(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C0607c.m204a().m217d();
        }
    }

    public static void closeBugly() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not close bugly because bugly is disable.");
            return;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.w(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return;
        }
        if (f148a == null) {
            return;
        }
        BuglyBroadcastReceiver buglyBroadcastReceiver = BuglyBroadcastReceiver.getInstance();
        if (buglyBroadcastReceiver != null) {
            buglyBroadcastReceiver.unregister(f148a);
        }
        closeCrashReport();
        C0591b.m37a(f148a);
        C0656w c0656wM453a = C0656w.m453a();
        if (c0656wM453a != null) {
            c0656wM453a.m457b();
        }
    }

    public static void setUserSceneTag(Context context, int i) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set tag caught because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.e(C0657x.f752a, "setTag args context should not be null");
            return;
        }
        if (i <= 0) {
            C0657x.m467d("setTag args tagId should > 0", new Object[0]);
        }
        C0593a.m64a(context).m71a(i);
        C0657x.m464b("[param] set user scene tag: %d", Integer.valueOf(i));
    }

    public static int getUserSceneTagId(Context context) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get user scene tag because bugly is disable.");
            return -1;
        }
        if (context == null) {
            Log.e(C0657x.f752a, "getUserSceneTagId args context should not be null");
            return -1;
        }
        return C0593a.m64a(context).m66A();
    }

    public static String getUserData(Context context, String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get user data because bugly is disable.");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (context == null) {
            Log.e(C0657x.f752a, "getUserDataValue args context should not be null");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (C0659z.m509a(str)) {
            return null;
        }
        return C0593a.m64a(context).m92h(str);
    }

    public static void putUserData(Context context, String str, String str2) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not put user data because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(C0657x.f752a, "putUserData args context should not be null");
            return;
        }
        if (str == null) {
            C0657x.m467d("putUserData args key should not be null or empty", new Object[0]);
            return;
        }
        if (str2 == null) {
            C0657x.m467d("putUserData args value should not be null", new Object[0]);
            return;
        }
        if (str2.length() > 200) {
            C0657x.m467d("user data value length over limit %d, it will be cutted!", Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            str2 = str2.substring(0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        }
        C0593a c0593aM64a = C0593a.m64a(context);
        if (c0593aM64a.m108x().contains(str)) {
            NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
            if (nativeCrashHandler != null) {
                nativeCrashHandler.putKeyValueToNative(str, str2);
            }
            C0593a.m64a(context).m78b(str, str2);
            C0657x.m466c("replace KV %s %s", str, str2);
            return;
        }
        if (c0593aM64a.m107w() >= 50) {
            C0657x.m467d("user data size is over limit %d, it will be cutted!", 50);
            return;
        }
        if (str.length() > 50) {
            C0657x.m467d("user data key length over limit %d , will drop this new key %s", 50, str);
            str = str.substring(0, 50);
        }
        NativeCrashHandler nativeCrashHandler2 = NativeCrashHandler.getInstance();
        if (nativeCrashHandler2 != null) {
            nativeCrashHandler2.putKeyValueToNative(str, str2);
        }
        C0593a.m64a(context).m78b(str, str2);
        C0657x.m464b("[param] set user data: %s - %s", str, str2);
    }

    public static String removeUserData(Context context, String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not remove user data because bugly is disable.");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (context == null) {
            Log.e(C0657x.f752a, "removeUserData args context should not be null");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (C0659z.m509a(str)) {
            return null;
        }
        C0657x.m464b("[param] remove user data: %s", str);
        return C0593a.m64a(context).m90g(str);
    }

    public static Set<String> getAllUserDataKeys(Context context) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get all keys of user data because bugly is disable.");
            return new HashSet();
        }
        if (context == null) {
            Log.e(C0657x.f752a, "getAllUserDataKeys args context should not be null");
            return new HashSet();
        }
        return C0593a.m64a(context).m108x();
    }

    public static int getUserDatasSize(Context context) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get size of user data because bugly is disable.");
            return -1;
        }
        if (context == null) {
            Log.e(C0657x.f752a, "getUserDatasSize args context should not be null");
            return -1;
        }
        return C0593a.m64a(context).m107w();
    }

    public static String getAppID() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get App ID because bugly is disable.");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return C0593a.m64a(f148a).m87f();
    }

    public static void setUserId(String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set user ID because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            setUserId(f148a, str);
        }
    }

    public static void setUserId(Context context, String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set user ID because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.e(C0657x.f752a, "Context should not be null when bugly has not been initialed!");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            C0657x.m467d("userId should not be null", new Object[0]);
            return;
        }
        if (str.length() > 100) {
            String strSubstring = str.substring(0, 100);
            C0657x.m467d("userId %s length is over limit %d substring to %s", str, 100, strSubstring);
            str = strSubstring;
        }
        if (str.equals(C0593a.m64a(context).m89g())) {
            return;
        }
        C0593a.m64a(context).m77b(str);
        C0657x.m464b("[user] set userId : %s", str);
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeUserId(str);
        }
        if (CrashModule.getInstance().hasInitialized()) {
            C0591b.m35a();
        }
    }

    public static String getUserId() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get user ID because bugly is disable.");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return C0593a.m64a(f148a).m89g();
    }

    public static void setDeviceId(Context context, String str) {
        if (str != null) {
            C0593a.m64a(context).m81c(str);
        }
    }

    public static void setDeviceModel(Context context, String str) {
        if (str != null) {
            C0593a.m64a(context).m84d(str);
        }
    }

    public static String getDeviceID(Context context) {
        return C0593a.m64a(context).m91h();
    }

    public static String getAppVer() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get app version because bugly is disable.");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return C0593a.m64a(f148a).f252j;
    }

    public static String getAppChannel() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get App channel because bugly is disable.");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return C0593a.m64a(f148a).f254l;
    }

    public static void setContext(Context context) {
        f148a = context;
    }

    public static boolean isLastSessionCrash() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "The info 'isLastSessionCrash' is not accurate because bugly is disable.");
            return false;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return false;
        }
        return C0607c.m204a().m215b();
    }

    public static void setSdkExtraData(Context context, String str, String str2) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not put SDK extra data because bugly is disable.");
        } else {
            if (context == null || C0659z.m509a(str) || C0659z.m509a(str2)) {
                return;
            }
            C0593a.m64a(context).m73a(str, str2);
        }
    }

    public static Map<String, String> getSdkExtraData() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C0657x.f752a, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return null;
        }
        return C0593a.m64a(f148a).f204B;
    }

    public static Map<String, String> getSdkExtraData(Context context) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        }
        if (context == null) {
            C0657x.m467d("Context should not be null.", new Object[0]);
            return null;
        }
        return C0593a.m64a(context).f204B;
    }

    private static void putSdkData(Context context, String str, String str2) {
        if (context == null || C0659z.m509a(str) || C0659z.m509a(str2)) {
            return;
        }
        String strReplace = str.replace("[a-zA-Z[0-9]]+", "");
        if (strReplace.length() > 100) {
            Log.w(C0657x.f752a, String.format("putSdkData key length over limit %d, will be cutted.", 50));
            strReplace = strReplace.substring(0, 50);
        }
        if (str2.length() > 500) {
            Log.w(C0657x.f752a, String.format("putSdkData value length over limit %d, will be cutted!", Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION)));
            str2 = str2.substring(0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        }
        C0593a.m64a(context).m82c(strReplace, str2);
        C0657x.m464b(String.format("[param] putSdkData data: %s - %s", strReplace, str2), new Object[0]);
    }

    public static void setIsAppForeground(Context context, boolean z) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set 'isAppForeground' because bugly is disable.");
            return;
        }
        if (context == null) {
            C0657x.m467d("Context should not be null.", new Object[0]);
            return;
        }
        if (z) {
            C0657x.m466c("App is in foreground.", new Object[0]);
        } else {
            C0657x.m466c("App is in background.", new Object[0]);
        }
        C0593a.m64a(context).m74a(z);
    }

    public static void setIsDevelopmentDevice(Context context, boolean z) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set 'isDevelopmentDevice' because bugly is disable.");
            return;
        }
        if (context == null) {
            C0657x.m467d("Context should not be null.", new Object[0]);
            return;
        }
        if (z) {
            C0657x.m466c("This is a development device.", new Object[0]);
        } else {
            C0657x.m466c("This is not a development device.", new Object[0]);
        }
        C0593a.m64a(context).f268z = z;
    }

    public static void setSessionIntervalMills(long j) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set 'SessionIntervalMills' because bugly is disable.");
        } else {
            C0591b.m36a(j);
        }
    }

    public static void setAppVersion(Context context, String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set App version because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(C0657x.f752a, "setAppVersion args context should not be null");
            return;
        }
        if (str == null) {
            Log.w(C0657x.f752a, "App version is null, will not set");
            return;
        }
        C0593a.m64a(context).f252j = str;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeAppVersion(str);
        }
    }

    public static void setAppChannel(Context context, String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set App channel because Bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(C0657x.f752a, "setAppChannel args context should not be null");
            return;
        }
        if (str == null) {
            Log.w(C0657x.f752a, "App channel is null, will not set");
            return;
        }
        C0593a.m64a(context).f254l = str;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeAppChannel(str);
        }
    }

    public static void setAppPackage(Context context, String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set App package because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(C0657x.f752a, "setAppPackage args context should not be null");
            return;
        }
        if (str == null) {
            Log.w(C0657x.f752a, "App package is null, will not set");
            return;
        }
        C0593a.m64a(context).f245c = str;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler != null) {
            nativeCrashHandler.setNativeAppPackage(str);
        }
    }

    public static void setCrashFilter(String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(C0657x.f752a, "Set crash stack filter: " + str);
        C0607c.f424n = str;
    }

    public static void setCrashRegularFilter(String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(C0657x.f752a, "Set crash stack filter: " + str);
        C0607c.f425o = str;
    }

    public static void setHandleNativeCrashInJava(boolean z) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(C0657x.f752a, "Should handle native crash in Java profile after handled in native profile: " + z);
        NativeCrashHandler.setShouldHandleInJava(z);
    }

    public static void setBuglyDbName(String str) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set DB name because bugly is disable.");
            return;
        }
        Log.i(C0657x.f752a, "Set Bugly DB name: " + str);
        C0650q.f702a = str;
    }

    public static void enableObtainId(Context context, boolean z) {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set DB name because bugly is disable.");
            return;
        }
        if (context == null) {
            Log.w(C0657x.f752a, "enableObtainId args context should not be null");
            return;
        }
        Log.i(C0657x.f752a, "Enable identification obtaining? " + z);
        C0593a.m64a(context).m79b(z);
    }

    public static void setServerUrl(String str) {
        if (C0659z.m509a(str) || !C0659z.m524c(str)) {
            Log.i(C0657x.f752a, "URL is invalid.");
            return;
        }
        C0596a.m141a(str);
        StrategyBean.f272a = str;
        StrategyBean.f273b = str;
    }

    public static void uploadUserInfo() {
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not upload user info because bugly is disable.");
        } else if (C0591b.f183a == null) {
            Log.w(C0657x.f752a, "Can not upload user info because bugly is not init.");
        } else {
            C0591b.f183a.m32b();
        }
    }

    public static boolean setJavascriptMonitor(WebView webView, boolean z) {
        return setJavascriptMonitor(webView, z, false);
    }

    public static boolean setJavascriptMonitor(final WebView webView, boolean z, boolean z2) {
        if (webView == null) {
            Log.w(C0657x.f752a, "WebView is null.");
            return false;
        }
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setAllowFileAccess(false);
        return setJavascriptMonitor(new WebViewInterface() { // from class: com.tencent.bugly.crashreport.CrashReport.1
            @Override // com.tencent.bugly.crashreport.CrashReport.WebViewInterface
            public final String getUrl() {
                return webView.getUrl();
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.WebViewInterface
            public final void setJavaScriptEnabled(boolean z3) {
                WebSettings settings = webView.getSettings();
                if (settings.getJavaScriptEnabled()) {
                    return;
                }
                settings.setJavaScriptEnabled(true);
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.WebViewInterface
            public final void loadUrl(String str) {
                webView.loadUrl(str);
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.WebViewInterface
            public final void addJavascriptInterface(H5JavaScriptInterface h5JavaScriptInterface, String str) {
                webView.addJavascriptInterface(h5JavaScriptInterface, str);
            }

            @Override // com.tencent.bugly.crashreport.CrashReport.WebViewInterface
            public final CharSequence getContentDescription() {
                return webView.getContentDescription();
            }
        }, z, z2);
    }

    public static boolean setJavascriptMonitor(WebViewInterface webViewInterface, boolean z) {
        return setJavascriptMonitor(webViewInterface, z, false);
    }

    public static boolean setJavascriptMonitor(WebViewInterface webViewInterface, boolean z, boolean z2) {
        if (webViewInterface == null) {
            Log.w(C0657x.f752a, "WebViewInterface is null.");
            return false;
        }
        if (!CrashModule.getInstance().hasInitialized()) {
            C0657x.m468e("CrashReport has not been initialed! please to call method 'initCrashReport' first!", new Object[0]);
            return false;
        }
        C0657x.m461a("Set Javascript exception monitor of webview.", new Object[0]);
        if (!C0585b.f143a) {
            Log.w(C0657x.f752a, "Can not set JavaScript monitor because bugly is disable.");
            return false;
        }
        C0657x.m466c("URL of webview is %s", webViewInterface.getUrl());
        if (!z2 && Build.VERSION.SDK_INT < 19) {
            C0657x.m468e("This interface is only available for Android 4.4 or later.", new Object[0]);
            return false;
        }
        C0657x.m461a("Enable the javascript needed by webview monitor.", new Object[0]);
        webViewInterface.setJavaScriptEnabled(true);
        H5JavaScriptInterface h5JavaScriptInterface = H5JavaScriptInterface.getInstance(webViewInterface);
        if (h5JavaScriptInterface != null) {
            C0657x.m461a("Add a secure javascript interface to the webview.", new Object[0]);
            webViewInterface.addJavascriptInterface(h5JavaScriptInterface, "exceptionUploader");
        }
        if (z) {
            C0657x.m461a("Inject bugly.js(v%s) to the webview.", C0611b.m251b());
            String strM250a = C0611b.m250a();
            if (strM250a == null) {
                C0657x.m468e("Failed to inject Bugly.js.", C0611b.m251b());
                return false;
            }
            webViewInterface.loadUrl("javascript:" + strM250a);
        }
        return true;
    }

    public static void setHttpProxy(String str, int i) {
        C0616a.m282a(str, i);
    }

    public static void setHttpProxy(InetAddress inetAddress, int i) {
        C0616a.m283a(inetAddress, i);
    }

    public static Proxy getHttpProxy() {
        return C0616a.m288b();
    }

    /* JADX INFO: compiled from: BUGLY */
    public static class UserStrategy extends BuglyStrategy {

        /* JADX INFO: renamed from: c */
        private CrashHandleCallback f150c;

        public UserStrategy(Context context) {
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized void setCallBackType(int i) {
            this.f116a = i;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized int getCallBackType() {
            return this.f116a;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized void setCloseErrorCallback(boolean z) {
            this.f117b = z;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized boolean getCloseErrorCallback() {
            return this.f117b;
        }

        @Override // com.tencent.bugly.BuglyStrategy
        public synchronized CrashHandleCallback getCrashHandleCallback() {
            return this.f150c;
        }

        public synchronized void setCrashHandleCallback(CrashHandleCallback crashHandleCallback) {
            this.f150c = crashHandleCallback;
        }
    }
}
