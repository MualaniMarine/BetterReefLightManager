package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.tencent.bugly.C0585b;
import com.tencent.bugly.crashreport.InterfaceC0588a;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.common.info.a */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0593a {

    /* JADX INFO: renamed from: Z */
    private static C0593a f202Z;

    /* JADX INFO: renamed from: A */
    public boolean f203A;

    /* JADX INFO: renamed from: E */
    public SharedPreferences f207E;

    /* JADX INFO: renamed from: F */
    private final Context f208F;

    /* JADX INFO: renamed from: G */
    private String f209G;

    /* JADX INFO: renamed from: H */
    private String f210H;

    /* JADX INFO: renamed from: I */
    private String f211I;

    /* JADX INFO: renamed from: U */
    private String f223U;

    /* JADX INFO: renamed from: c */
    public String f245c;

    /* JADX INFO: renamed from: d */
    public final String f246d;

    /* JADX INFO: renamed from: g */
    public final String f249g;

    /* JADX INFO: renamed from: h */
    public final String f250h;

    /* JADX INFO: renamed from: i */
    public long f251i;

    /* JADX INFO: renamed from: j */
    public String f252j;

    /* JADX INFO: renamed from: k */
    public String f253k;

    /* JADX INFO: renamed from: l */
    public String f254l;

    /* JADX INFO: renamed from: o */
    public List<String> f257o;

    /* JADX INFO: renamed from: u */
    public boolean f263u;

    /* JADX INFO: renamed from: v */
    public String f264v;

    /* JADX INFO: renamed from: w */
    public String f265w;

    /* JADX INFO: renamed from: x */
    public String f266x;

    /* JADX INFO: renamed from: y */
    public String f267y;

    /* JADX INFO: renamed from: e */
    public boolean f247e = true;

    /* JADX INFO: renamed from: f */
    public String f248f = "3.4.4";

    /* JADX INFO: renamed from: J */
    private String f212J = EnvironmentCompat.MEDIA_UNKNOWN;

    /* JADX INFO: renamed from: K */
    private String f213K = "";

    /* JADX INFO: renamed from: L */
    private String f214L = null;

    /* JADX INFO: renamed from: M */
    private long f215M = -1;

    /* JADX INFO: renamed from: N */
    private long f216N = -1;

    /* JADX INFO: renamed from: O */
    private long f217O = -1;

    /* JADX INFO: renamed from: P */
    private String f218P = null;

    /* JADX INFO: renamed from: Q */
    private String f219Q = null;

    /* JADX INFO: renamed from: R */
    private Map<String, PlugInBean> f220R = null;

    /* JADX INFO: renamed from: S */
    private boolean f221S = false;

    /* JADX INFO: renamed from: T */
    private String f222T = null;

    /* JADX INFO: renamed from: V */
    private Boolean f224V = null;

    /* JADX INFO: renamed from: W */
    private String f225W = null;

    /* JADX INFO: renamed from: m */
    public String f255m = null;

    /* JADX INFO: renamed from: n */
    public String f256n = null;

    /* JADX INFO: renamed from: X */
    private Map<String, PlugInBean> f226X = null;

    /* JADX INFO: renamed from: Y */
    private Map<String, PlugInBean> f227Y = null;

    /* JADX INFO: renamed from: aa */
    private int f229aa = -1;

    /* JADX INFO: renamed from: ab */
    private int f230ab = -1;

    /* JADX INFO: renamed from: ac */
    private Map<String, String> f231ac = new HashMap();

    /* JADX INFO: renamed from: ad */
    private Map<String, String> f232ad = new HashMap();

    /* JADX INFO: renamed from: ae */
    private Map<String, String> f233ae = new HashMap();

    /* JADX INFO: renamed from: p */
    public String f258p = EnvironmentCompat.MEDIA_UNKNOWN;

    /* JADX INFO: renamed from: q */
    public long f259q = 0;

    /* JADX INFO: renamed from: r */
    public long f260r = 0;

    /* JADX INFO: renamed from: s */
    public long f261s = 0;

    /* JADX INFO: renamed from: t */
    public long f262t = 0;

    /* JADX INFO: renamed from: z */
    public boolean f268z = false;

    /* JADX INFO: renamed from: af */
    private Boolean f234af = null;

    /* JADX INFO: renamed from: ag */
    private Boolean f235ag = null;

    /* JADX INFO: renamed from: B */
    public HashMap<String, String> f204B = new HashMap<>();

    /* JADX INFO: renamed from: C */
    public List<String> f205C = new ArrayList();

    /* JADX INFO: renamed from: D */
    public InterfaceC0588a f206D = null;

    /* JADX INFO: renamed from: ah */
    private final Object f236ah = new Object();

    /* JADX INFO: renamed from: ai */
    private final Object f237ai = new Object();

    /* JADX INFO: renamed from: aj */
    private final Object f238aj = new Object();

    /* JADX INFO: renamed from: ak */
    private final Object f239ak = new Object();

    /* JADX INFO: renamed from: al */
    private final Object f240al = new Object();

    /* JADX INFO: renamed from: am */
    private final Object f241am = new Object();

    /* JADX INFO: renamed from: an */
    private final Object f242an = new Object();

    /* JADX INFO: renamed from: ao */
    private int f243ao = 0;

    /* JADX INFO: renamed from: a */
    public final long f228a = System.currentTimeMillis();

    /* JADX INFO: renamed from: b */
    public final byte f244b = 1;

    private C0593a(Context context) {
        this.f252j = null;
        this.f253k = null;
        this.f223U = null;
        this.f254l = null;
        this.f257o = null;
        this.f263u = false;
        this.f264v = null;
        this.f265w = null;
        this.f266x = null;
        this.f267y = "";
        this.f203A = false;
        this.f208F = C0659z.m486a(context);
        PackageInfo packageInfoM59b = AppInfo.m59b(context);
        if (packageInfoM59b != null) {
            try {
                String str = packageInfoM59b.versionName;
                this.f252j = str;
                this.f264v = str;
                this.f265w = Integer.toString(packageInfoM59b.versionCode);
            } catch (Throwable th) {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
            }
        }
        this.f245c = AppInfo.m56a(context);
        this.f246d = AppInfo.m55a(Process.myPid());
        this.f249g = C0594b.m130k();
        this.f253k = AppInfo.m60c(context);
        this.f250h = "Android " + C0594b.m114b() + ",level " + C0594b.m116c();
        Map<String, String> mapM61d = AppInfo.m61d(context);
        if (mapM61d != null) {
            try {
                this.f257o = AppInfo.m57a(mapM61d);
                String str2 = mapM61d.get("BUGLY_APPID");
                if (str2 != null) {
                    this.f223U = str2;
                    m82c("APP_ID", str2);
                }
                String str3 = mapM61d.get("BUGLY_APP_VERSION");
                if (str3 != null) {
                    this.f252j = str3;
                }
                String str4 = mapM61d.get("BUGLY_APP_CHANNEL");
                if (str4 != null) {
                    this.f254l = str4;
                }
                String str5 = mapM61d.get("BUGLY_ENABLE_DEBUG");
                if (str5 != null) {
                    this.f263u = str5.equalsIgnoreCase("true");
                }
                String str6 = mapM61d.get("com.tencent.rdm.uuid");
                if (str6 != null) {
                    this.f266x = str6;
                }
                String str7 = mapM61d.get("BUGLY_APP_BUILD_NO");
                if (!TextUtils.isEmpty(str7)) {
                    Integer.parseInt(str7);
                }
                String str8 = mapM61d.get("BUGLY_AREA");
                if (str8 != null) {
                    this.f267y = str8;
                }
            } catch (Throwable th2) {
                if (!C0657x.m462a(th2)) {
                    th2.printStackTrace();
                }
            }
        }
        try {
            if (!context.getDatabasePath("bugly_db_").exists()) {
                this.f203A = true;
                C0657x.m466c("App is first time to be installed on the device.", new Object[0]);
            }
        } catch (Throwable th3) {
            if (C0585b.f145c) {
                th3.printStackTrace();
            }
        }
        this.f207E = C0659z.m487a("BUGLY_COMMON_VALUES", context);
        C0657x.m466c("com info create end", new Object[0]);
    }

    /* JADX INFO: renamed from: a */
    public final boolean m75a() {
        return this.f243ao > 0;
    }

    /* JADX INFO: renamed from: a */
    public final void m74a(boolean z) {
        if (z) {
            this.f243ao++;
        } else {
            this.f243ao--;
        }
        InterfaceC0588a interfaceC0588a = this.f206D;
        if (interfaceC0588a != null) {
            interfaceC0588a.setNativeIsAppForeground(this.f243ao > 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0593a m64a(Context context) {
        if (f202Z == null) {
            f202Z = new C0593a(context);
        }
        return f202Z;
    }

    /* JADX INFO: renamed from: b */
    public static synchronized C0593a m65b() {
        return f202Z;
    }

    /* JADX INFO: renamed from: c */
    public final String m80c() {
        return this.f248f;
    }

    /* JADX INFO: renamed from: d */
    public final void m83d() {
        synchronized (this.f236ah) {
            this.f209G = UUID.randomUUID().toString();
        }
    }

    /* JADX INFO: renamed from: e */
    public final String m85e() {
        String str;
        synchronized (this.f236ah) {
            if (this.f209G == null) {
                synchronized (this.f236ah) {
                    this.f209G = UUID.randomUUID().toString();
                }
            }
            str = this.f209G;
        }
        return str;
    }

    /* JADX INFO: renamed from: f */
    public final String m87f() {
        if (C0659z.m509a((String) null)) {
            return this.f223U;
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public final void m72a(String str) {
        this.f223U = str;
        m82c("APP_ID", str);
    }

    /* JADX INFO: renamed from: g */
    public final String m89g() {
        String str;
        synchronized (this.f241am) {
            str = this.f212J;
        }
        return str;
    }

    /* JADX INFO: renamed from: b */
    public final void m77b(String str) {
        synchronized (this.f241am) {
            if (str == null) {
                str = "10000";
            }
            this.f212J = str;
        }
    }

    /* JADX INFO: renamed from: b */
    public final void m79b(boolean z) {
        this.f221S = z;
    }

    /* JADX INFO: renamed from: h */
    public final String m91h() {
        String str = this.f211I;
        if (str != null) {
            return str;
        }
        String str2 = null;
        String strM523c = C0659z.m523c("deviceId", null);
        this.f211I = strM523c;
        if (strM523c != null) {
            return strM523c;
        }
        if (TextUtils.isEmpty(this.f214L)) {
            this.f214L = C0659z.m523c("androidid", null);
        }
        if (!TextUtils.isEmpty(this.f214L)) {
            str2 = this.f214L;
        } else if (this.f221S) {
            String strM112a = C0594b.m112a(this.f208F);
            this.f214L = strM112a;
            if (!TextUtils.isEmpty(strM112a)) {
                C0659z.m520b("androidid", this.f214L);
            }
            str2 = this.f214L;
        }
        this.f211I = str2;
        if (TextUtils.isEmpty(str2)) {
            String string = UUID.randomUUID().toString();
            if (!C0659z.m509a(string)) {
                string = string.replaceAll("-", "");
            }
            this.f211I = string;
        }
        String str3 = this.f211I;
        if (str3 == null) {
            return "";
        }
        C0659z.m520b("deviceId", str3);
        return this.f211I;
    }

    /* JADX INFO: renamed from: c */
    public final void m81c(String str) {
        this.f211I = str;
        if (!TextUtils.isEmpty(str)) {
            C0659z.m520b("deviceId", str);
        }
        synchronized (this.f242an) {
            this.f232ad.put("E8", str);
        }
    }

    /* JADX INFO: renamed from: i */
    public final String m93i() {
        String str = this.f210H;
        if (str != null) {
            return str;
        }
        String strM523c = C0659z.m523c("deviceModel", null);
        this.f210H = strM523c;
        if (strM523c != null) {
            return strM523c;
        }
        String strM111a = C0594b.m111a();
        this.f210H = strM111a;
        C0659z.m520b("deviceModel", strM111a);
        return this.f210H;
    }

    /* JADX INFO: renamed from: d */
    public final void m84d(String str) {
        C0657x.m461a("change deviceModel，old:%s new:%s", this.f210H, str);
        this.f210H = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        C0659z.m520b("deviceModel", str);
    }

    /* JADX INFO: renamed from: e */
    public final synchronized void m86e(String str) {
    }

    /* JADX INFO: renamed from: j */
    public final synchronized String m94j() {
        return this.f213K;
    }

    /* JADX INFO: renamed from: f */
    public final synchronized void m88f(String str) {
        this.f213K = str;
    }

    /* JADX INFO: renamed from: k */
    public final long m95k() {
        if (this.f215M <= 0) {
            this.f215M = C0594b.m118d();
        }
        return this.f215M;
    }

    /* JADX INFO: renamed from: l */
    public final long m96l() {
        if (this.f216N <= 0) {
            this.f216N = C0594b.m122f();
        }
        return this.f216N;
    }

    /* JADX INFO: renamed from: m */
    public final long m97m() {
        if (this.f217O <= 0) {
            this.f217O = C0594b.m127h();
        }
        return this.f217O;
    }

    /* JADX INFO: renamed from: n */
    public final String m98n() {
        if (this.f218P == null) {
            this.f218P = C0594b.m113a(this.f208F, true);
        }
        return this.f218P;
    }

    /* JADX INFO: renamed from: o */
    public final String m99o() {
        if (this.f219Q == null) {
            this.f219Q = C0594b.m119d(this.f208F);
        }
        return this.f219Q;
    }

    /* JADX INFO: renamed from: a */
    public final void m73a(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        synchronized (this.f237ai) {
            this.f204B.put(str, str2);
        }
    }

    /* JADX INFO: renamed from: p */
    public final String m100p() {
        try {
            Map<String, ?> all = this.f208F.getSharedPreferences("BuglySdkInfos", 0).getAll();
            if (!all.isEmpty()) {
                synchronized (this.f237ai) {
                    for (Map.Entry<String, ?> entry : all.entrySet()) {
                        try {
                            this.f204B.put(entry.getKey(), entry.getValue().toString());
                        } catch (Throwable th) {
                            C0657x.m462a(th);
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            C0657x.m462a(th2);
        }
        if (!this.f204B.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry2 : this.f204B.entrySet()) {
                sb.append("[");
                sb.append(entry2.getKey());
                sb.append(",");
                sb.append(entry2.getValue());
                sb.append("] ");
            }
            C0657x.m466c("SDK_INFO = %s", sb.toString());
            m82c("SDK_INFO", sb.toString());
            return sb.toString();
        }
        C0657x.m466c("SDK_INFO is empty", new Object[0]);
        return null;
    }

    /* JADX INFO: renamed from: q */
    public final synchronized Map<String, PlugInBean> m101q() {
        return null;
    }

    /* JADX INFO: renamed from: r */
    public final String m102r() {
        if (this.f222T == null) {
            this.f222T = C0594b.m129j();
        }
        return this.f222T;
    }

    /* JADX INFO: renamed from: s */
    public final Boolean m103s() {
        if (this.f224V == null) {
            this.f224V = Boolean.valueOf(C0594b.m131l());
        }
        return this.f224V;
    }

    /* JADX INFO: renamed from: t */
    public final String m104t() {
        if (this.f225W == null) {
            String str = C0594b.m117c(this.f208F);
            this.f225W = str;
            C0657x.m461a("ROM ID: %s", str);
        }
        return this.f225W;
    }

    /* JADX INFO: renamed from: u */
    public final Map<String, String> m105u() {
        synchronized (this.f238aj) {
            if (this.f231ac.size() <= 0) {
                return null;
            }
            return new HashMap(this.f231ac);
        }
    }

    /* JADX INFO: renamed from: g */
    public final String m90g(String str) {
        String strRemove;
        if (C0659z.m509a(str)) {
            C0657x.m467d("key should not be empty %s", str);
            return null;
        }
        synchronized (this.f238aj) {
            strRemove = this.f231ac.remove(str);
        }
        return strRemove;
    }

    /* JADX INFO: renamed from: v */
    public final void m106v() {
        synchronized (this.f238aj) {
            this.f231ac.clear();
        }
    }

    /* JADX INFO: renamed from: h */
    public final String m92h(String str) {
        String str2;
        if (C0659z.m509a(str)) {
            C0657x.m467d("key should not be empty %s", str);
            return null;
        }
        synchronized (this.f238aj) {
            str2 = this.f231ac.get(str);
        }
        return str2;
    }

    /* JADX INFO: renamed from: b */
    public final void m78b(String str, String str2) {
        if (C0659z.m509a(str) || C0659z.m509a(str2)) {
            C0657x.m467d("key&value should not be empty %s %s", str, str2);
            return;
        }
        synchronized (this.f238aj) {
            this.f231ac.put(str, str2);
        }
    }

    /* JADX INFO: renamed from: w */
    public final int m107w() {
        int size;
        synchronized (this.f238aj) {
            size = this.f231ac.size();
        }
        return size;
    }

    /* JADX INFO: renamed from: x */
    public final Set<String> m108x() {
        Set<String> setKeySet;
        synchronized (this.f238aj) {
            setKeySet = this.f231ac.keySet();
        }
        return setKeySet;
    }

    /* JADX INFO: renamed from: y */
    public final Map<String, String> m109y() {
        synchronized (this.f242an) {
            if (this.f232ad.size() <= 0) {
                return null;
            }
            return new HashMap(this.f232ad);
        }
    }

    /* JADX INFO: renamed from: c */
    public final void m82c(String str, String str2) {
        if (C0659z.m509a(str) || C0659z.m509a(str2)) {
            C0657x.m467d("server key&value should not be empty %s %s", str, str2);
            return;
        }
        synchronized (this.f239ak) {
            this.f233ae.put(str, str2);
        }
    }

    /* JADX INFO: renamed from: z */
    public final Map<String, String> m110z() {
        synchronized (this.f239ak) {
            if (this.f233ae.size() <= 0) {
                return null;
            }
            return new HashMap(this.f233ae);
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m71a(int i) {
        synchronized (this.f240al) {
            int i2 = this.f229aa;
            if (i2 != i) {
                this.f229aa = i;
                C0657x.m461a("user scene tag %d changed to tag %d", Integer.valueOf(i2), Integer.valueOf(this.f229aa));
            }
        }
    }

    /* JADX INFO: renamed from: A */
    public final int m66A() {
        int i;
        synchronized (this.f240al) {
            i = this.f229aa;
        }
        return i;
    }

    /* JADX INFO: renamed from: b */
    public final void m76b(int i) {
        int i2 = this.f230ab;
        if (i2 != 24096) {
            this.f230ab = 24096;
            C0657x.m461a("server scene tag %d changed to tag %d", Integer.valueOf(i2), Integer.valueOf(this.f230ab));
        }
    }

    /* JADX INFO: renamed from: B */
    public final int m67B() {
        return this.f230ab;
    }

    /* JADX INFO: renamed from: C */
    public final synchronized Map<String, PlugInBean> m68C() {
        return null;
    }

    /* JADX INFO: renamed from: D */
    public static int m63D() {
        return C0594b.m116c();
    }

    /* JADX INFO: renamed from: E */
    public final boolean m69E() {
        if (this.f234af == null) {
            this.f234af = Boolean.valueOf(C0594b.m121e(this.f208F));
            C0657x.m461a("Is it a virtual machine? " + this.f234af, new Object[0]);
        }
        return this.f234af.booleanValue();
    }

    /* JADX INFO: renamed from: F */
    public final boolean m70F() {
        if (this.f235ag == null) {
            this.f235ag = Boolean.valueOf(C0594b.m123f(this.f208F));
            C0657x.m461a("Does it has hook frame? " + this.f235ag, new Object[0]);
        }
        return this.f235ag.booleanValue();
    }
}
