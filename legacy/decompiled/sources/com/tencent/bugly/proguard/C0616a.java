package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.AbstractC0584a;
import com.tencent.bugly.C0585b;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.C0594b;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.a */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class C0616a {

    /* JADX INFO: renamed from: e */
    private static Proxy f507e;

    /* JADX INFO: renamed from: a */
    protected HashMap<String, HashMap<String, byte[]>> f508a = new HashMap<>();

    /* JADX INFO: renamed from: b */
    protected String f509b;

    /* JADX INFO: renamed from: c */
    C0642i f510c;

    /* JADX INFO: renamed from: d */
    private HashMap<String, Object> f511d;

    C0616a() {
        new HashMap();
        this.f511d = new HashMap<>();
        this.f509b = "GBK";
        this.f510c = new C0642i();
    }

    /* JADX INFO: renamed from: a */
    public static void m282a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            f507e = null;
        } else {
            f507e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i));
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m283a(InetAddress inetAddress, int i) {
        if (inetAddress == null) {
            f507e = null;
        } else {
            f507e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(inetAddress, i));
        }
    }

    /* JADX INFO: renamed from: a */
    public void mo289a(String str) {
        this.f509b = str;
    }

    /* JADX INFO: renamed from: b */
    public static Proxy m288b() {
        return f507e;
    }

    /* JADX INFO: renamed from: a */
    public static C0633aq m278a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        C0633aq c0633aq = new C0633aq();
        c0633aq.f620a = userInfoBean.f155e;
        c0633aq.f624e = userInfoBean.f160j;
        c0633aq.f623d = userInfoBean.f153c;
        c0633aq.f622c = userInfoBean.f154d;
        c0633aq.f626g = userInfoBean.f165o == 1;
        int i = userInfoBean.f152b;
        if (i == 1) {
            c0633aq.f621b = (byte) 1;
        } else if (i == 2) {
            c0633aq.f621b = (byte) 4;
        } else if (i == 3) {
            c0633aq.f621b = (byte) 2;
        } else if (i == 4) {
            c0633aq.f621b = (byte) 3;
        } else {
            if (userInfoBean.f152b < 10 || userInfoBean.f152b >= 20) {
                C0657x.m468e("unknown uinfo type %d ", Integer.valueOf(userInfoBean.f152b));
                return null;
            }
            c0633aq.f621b = (byte) userInfoBean.f152b;
        }
        c0633aq.f625f = new HashMap();
        if (userInfoBean.f166p >= 0) {
            Map<String, String> map = c0633aq.f625f;
            StringBuilder sb = new StringBuilder();
            sb.append(userInfoBean.f166p);
            map.put("C01", sb.toString());
        }
        if (userInfoBean.f167q >= 0) {
            Map<String, String> map2 = c0633aq.f625f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(userInfoBean.f167q);
            map2.put("C02", sb2.toString());
        }
        if (userInfoBean.f168r != null && userInfoBean.f168r.size() > 0) {
            for (Map.Entry<String, String> entry : userInfoBean.f168r.entrySet()) {
                c0633aq.f625f.put("C03_" + entry.getKey(), entry.getValue());
            }
        }
        if (userInfoBean.f169s != null && userInfoBean.f169s.size() > 0) {
            for (Map.Entry<String, String> entry2 : userInfoBean.f169s.entrySet()) {
                c0633aq.f625f.put("C04_" + entry2.getKey(), entry2.getValue());
            }
        }
        Map<String, String> map3 = c0633aq.f625f;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(!userInfoBean.f162l);
        map3.put("A36", sb3.toString());
        Map<String, String> map4 = c0633aq.f625f;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(userInfoBean.f157g);
        map4.put("F02", sb4.toString());
        Map<String, String> map5 = c0633aq.f625f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(userInfoBean.f158h);
        map5.put("F03", sb5.toString());
        c0633aq.f625f.put("F04", userInfoBean.f160j);
        Map<String, String> map6 = c0633aq.f625f;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(userInfoBean.f159i);
        map6.put("F05", sb6.toString());
        c0633aq.f625f.put("F06", userInfoBean.f163m);
        Map<String, String> map7 = c0633aq.f625f;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(userInfoBean.f161k);
        map7.put("F10", sb7.toString());
        C0657x.m466c("summary type %d vm:%d", Byte.valueOf(c0633aq.f621b), Integer.valueOf(c0633aq.f625f.size()));
        return c0633aq;
    }

    /* JADX INFO: renamed from: a */
    public static String m281a(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            String str = "map";
            if (i < arrayList.size()) {
                String str2 = arrayList.get(i);
                if (str2.equals("java.lang.Integer") || str2.equals("int")) {
                    str = "int32";
                } else if (str2.equals("java.lang.Boolean") || str2.equals("boolean")) {
                    str = "bool";
                } else if (str2.equals("java.lang.Byte") || str2.equals("byte")) {
                    str = "char";
                } else if (str2.equals("java.lang.Double") || str2.equals("double")) {
                    str = "double";
                } else if (str2.equals("java.lang.Float") || str2.equals("float")) {
                    str = "float";
                } else if (str2.equals("java.lang.Long") || str2.equals("long")) {
                    str = "int64";
                } else if (str2.equals("java.lang.Short") || str2.equals("short")) {
                    str = "short";
                } else {
                    if (str2.equals("java.lang.Character")) {
                        throw new IllegalArgumentException("can not support java.lang.Character");
                    }
                    if (str2.equals("java.lang.String")) {
                        str = "string";
                    } else if (str2.equals("java.util.List")) {
                        str = "list";
                    } else if (!str2.equals("java.util.Map")) {
                        str = str2;
                    }
                }
                arrayList.set(i, str);
                i++;
            } else {
                Collections.reverse(arrayList);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    String str3 = arrayList.get(i2);
                    if (str3.equals("list")) {
                        int i3 = i2 - 1;
                        arrayList.set(i3, "<" + arrayList.get(i3));
                        arrayList.set(0, arrayList.get(0) + ">");
                    } else if (str3.equals("map")) {
                        int i4 = i2 - 1;
                        arrayList.set(i4, "<" + arrayList.get(i4) + ",");
                        arrayList.set(0, arrayList.get(0) + ">");
                    } else if (str3.equals("Array")) {
                        int i5 = i2 - 1;
                        arrayList.set(i5, "<" + arrayList.get(i5));
                        arrayList.set(0, arrayList.get(0) + ">");
                    }
                }
                Collections.reverse(arrayList);
                Iterator<String> it = arrayList.iterator();
                while (it.hasNext()) {
                    stringBuffer.append(it.next());
                }
                return stringBuffer.toString();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public <T> void mo290a(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        if (t instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        }
        C0643j c0643j = new C0643j();
        c0643j.m361a(this.f509b);
        c0643j.m367a(t, 0);
        byte[] bArrM379a = C0645l.m379a(c0643j.m362a());
        HashMap<String, byte[]> map = new HashMap<>(1);
        ArrayList<String> arrayList = new ArrayList<>(1);
        m284a(arrayList, t);
        map.put(m281a(arrayList), bArrM379a);
        this.f511d.remove(str);
        this.f508a.put(str, map);
    }

    /* JADX INFO: renamed from: a */
    public static C0634ar m279a(List<UserInfoBean> list, int i) {
        C0593a c0593aM65b;
        if (list == null || list.size() == 0 || (c0593aM65b = C0593a.m65b()) == null) {
            return null;
        }
        c0593aM65b.m100p();
        C0634ar c0634ar = new C0634ar();
        c0634ar.f631b = c0593aM65b.f246d;
        c0634ar.f632c = c0593aM65b.m91h();
        ArrayList<C0633aq> arrayList = new ArrayList<>();
        Iterator<UserInfoBean> it = list.iterator();
        while (it.hasNext()) {
            C0633aq c0633aqM278a = m278a(it.next());
            if (c0633aqM278a != null) {
                arrayList.add(c0633aqM278a);
            }
        }
        c0634ar.f633d = arrayList;
        c0634ar.f634e = new HashMap();
        c0634ar.f634e.put("A7", c0593aM65b.f249g);
        c0634ar.f634e.put("A6", c0593aM65b.m99o());
        c0634ar.f634e.put("A5", c0593aM65b.m98n());
        Map<String, String> map = c0634ar.f634e;
        StringBuilder sb = new StringBuilder();
        sb.append(c0593aM65b.m96l());
        map.put("A2", sb.toString());
        Map<String, String> map2 = c0634ar.f634e;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(c0593aM65b.m96l());
        map2.put("A1", sb2.toString());
        c0634ar.f634e.put("A24", c0593aM65b.f250h);
        Map<String, String> map3 = c0634ar.f634e;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(c0593aM65b.m97m());
        map3.put("A17", sb3.toString());
        c0634ar.f634e.put("A15", c0593aM65b.m102r());
        Map<String, String> map4 = c0634ar.f634e;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(c0593aM65b.m103s());
        map4.put("A13", sb4.toString());
        c0634ar.f634e.put("F08", c0593aM65b.f264v);
        c0634ar.f634e.put("F09", c0593aM65b.f265w);
        Map<String, String> mapM110z = c0593aM65b.m110z();
        if (mapM110z != null && mapM110z.size() > 0) {
            for (Map.Entry<String, String> entry : mapM110z.entrySet()) {
                c0634ar.f634e.put("C04_" + entry.getKey(), entry.getValue());
            }
        }
        if (i == 1) {
            c0634ar.f630a = (byte) 1;
        } else {
            if (i != 2) {
                C0657x.m468e("unknown up type %d ", Integer.valueOf(i));
                return null;
            }
            c0634ar.f630a = (byte) 2;
        }
        return c0634ar;
    }

    /* JADX INFO: renamed from: a */
    public static <T extends AbstractC0644k> T m280a(byte[] bArr, Class<T> cls) {
        if (bArr != null && bArr.length > 0) {
            try {
                T tNewInstance = cls.newInstance();
                C0642i c0642i = new C0642i(bArr);
                c0642i.m349a("utf-8");
                tNewInstance.mo311a(c0642i);
                return tNewInstance;
            } catch (Throwable th) {
                if (!C0657x.m465b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public static C0629am m277a(Context context, int i, byte[] bArr) {
        C0593a c0593aM65b = C0593a.m65b();
        StrategyBean strategyBeanM149c = C0596a.m139a().m149c();
        if (c0593aM65b == null || strategyBeanM149c == null) {
            C0657x.m468e("Can not create request pkg for parameters is invalid.", new Object[0]);
            return null;
        }
        try {
            C0629am c0629am = new C0629am();
            synchronized (c0593aM65b) {
                c0629am.f568a = 1;
                c0629am.f569b = c0593aM65b.m87f();
                c0629am.f570c = c0593aM65b.f245c;
                c0629am.f571d = c0593aM65b.f252j;
                c0629am.f572e = c0593aM65b.f254l;
                c0629am.f573f = c0593aM65b.f248f;
                c0629am.f574g = i;
                if (bArr == null) {
                    bArr = "".getBytes();
                }
                c0629am.f575h = bArr;
                c0629am.f576i = c0593aM65b.m93i();
                c0629am.f577j = c0593aM65b.f250h;
                c0629am.f578k = new HashMap();
                c0629am.f579l = c0593aM65b.m85e();
                c0629am.f580m = strategyBeanM149c.f285n;
                c0629am.f582o = c0593aM65b.m91h();
                c0629am.f583p = C0594b.m115b(context);
                c0629am.f584q = System.currentTimeMillis();
                c0629am.f585r = c0593aM65b.m94j();
                c0629am.f586s = c0593aM65b.m91h();
                c0629am.f587t = c0629am.f583p;
                c0593aM65b.getClass();
                c0629am.f581n = "com.tencent.bugly";
                c0629am.f578k.put("A26", c0593aM65b.m104t());
                Map<String, String> map = c0629am.f578k;
                StringBuilder sb = new StringBuilder();
                sb.append(c0593aM65b.m69E());
                map.put("A62", sb.toString());
                Map<String, String> map2 = c0629am.f578k;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(c0593aM65b.m70F());
                map2.put("A63", sb2.toString());
                Map<String, String> map3 = c0629am.f578k;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(c0593aM65b.f203A);
                map3.put("F11", sb3.toString());
                Map<String, String> map4 = c0629am.f578k;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(c0593aM65b.f268z);
                map4.put("F12", sb4.toString());
                c0629am.f578k.put("D3", c0593aM65b.f253k);
                if (C0585b.f144b != null) {
                    for (AbstractC0584a abstractC0584a : C0585b.f144b) {
                        if (abstractC0584a.versionKey != null && abstractC0584a.version != null) {
                            c0629am.f578k.put(abstractC0584a.versionKey, abstractC0584a.version);
                        }
                    }
                }
                c0629am.f578k.put("G15", C0659z.m523c("G15", ""));
                c0629am.f578k.put("G10", C0659z.m523c("G10", ""));
                c0629am.f578k.put("D4", C0659z.m523c("D4", "0"));
            }
            Map<String, String> mapM109y = c0593aM65b.m109y();
            if (mapM109y != null) {
                for (Map.Entry<String, String> entry : mapM109y.entrySet()) {
                    c0629am.f578k.put(entry.getKey(), entry.getValue());
                }
            }
            return c0629am;
        } catch (Throwable th) {
            if (!C0657x.m465b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m284a(ArrayList<String> arrayList, Object obj) {
        if (obj.getClass().isArray()) {
            if (!obj.getClass().getComponentType().toString().equals("byte")) {
                throw new IllegalArgumentException("only byte[] is supported");
            }
            if (Array.getLength(obj) > 0) {
                arrayList.add("java.util.List");
                m284a(arrayList, Array.get(obj, 0));
                return;
            } else {
                arrayList.add("Array");
                arrayList.add("?");
                return;
            }
        }
        if (obj instanceof Array) {
            throw new IllegalArgumentException("can not support Array, please use List");
        }
        if (obj instanceof List) {
            arrayList.add("java.util.List");
            List list = (List) obj;
            if (list.size() > 0) {
                m284a(arrayList, list.get(0));
                return;
            } else {
                arrayList.add("?");
                return;
            }
        }
        if (obj instanceof Map) {
            arrayList.add("java.util.Map");
            Map map = (Map) obj;
            if (map.size() > 0) {
                Object next = map.keySet().iterator().next();
                Object obj2 = map.get(next);
                arrayList.add(next.getClass().getName());
                m284a(arrayList, obj2);
                return;
            }
            arrayList.add("?");
            arrayList.add("?");
            return;
        }
        arrayList.add(obj.getClass().getName());
    }

    /* JADX INFO: renamed from: a */
    public byte[] mo292a() {
        C0643j c0643j = new C0643j(0);
        c0643j.m361a(this.f509b);
        c0643j.m370a((Map) this.f508a, 0);
        return C0645l.m379a(c0643j.m362a());
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m286a(Object obj) {
        try {
            C0637d c0637d = new C0637d();
            c0637d.mo315c();
            c0637d.mo289a("utf-8");
            c0637d.m316a(1);
            c0637d.m317b("RqdServer");
            c0637d.m318c("sync");
            c0637d.mo290a("detail", obj);
            return c0637d.mo292a();
        } catch (Throwable th) {
            if (C0657x.m465b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public void mo291a(byte[] bArr) {
        this.f510c.m355a(bArr);
        this.f510c.m349a(this.f509b);
        HashMap map = new HashMap(1);
        HashMap map2 = new HashMap(1);
        map2.put("", new byte[0]);
        map.put("", map2);
        this.f508a = this.f510c.m353a((Map) map, 0, false);
    }

    /* JADX INFO: renamed from: b */
    public static C0630an m287b(byte[] bArr) {
        if (bArr != null) {
            try {
                C0637d c0637d = new C0637d();
                c0637d.mo315c();
                c0637d.mo289a("utf-8");
                c0637d.mo291a(bArr);
                Object objB = c0637d.m314b("detail", new C0630an());
                if (C0630an.class.isInstance(objB)) {
                    return (C0630an) C0630an.class.cast(objB);
                }
                return null;
            } catch (Throwable th) {
                if (!C0657x.m465b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m285a(AbstractC0644k abstractC0644k) {
        try {
            C0643j c0643j = new C0643j();
            c0643j.m361a("utf-8");
            abstractC0644k.mo312a(c0643j);
            return c0643j.m374b();
        } catch (Throwable th) {
            if (C0657x.m465b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
