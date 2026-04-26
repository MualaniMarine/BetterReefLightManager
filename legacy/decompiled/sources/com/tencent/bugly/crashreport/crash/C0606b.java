package com.tencent.bugly.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.C0585b;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.AbstractC0644k;
import com.tencent.bugly.proguard.C0616a;
import com.tencent.bugly.proguard.C0624ah;
import com.tencent.bugly.proguard.C0626aj;
import com.tencent.bugly.proguard.C0627ak;
import com.tencent.bugly.proguard.C0628al;
import com.tencent.bugly.proguard.C0629am;
import com.tencent.bugly.proguard.C0649p;
import com.tencent.bugly.proguard.C0651r;
import com.tencent.bugly.proguard.C0654u;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import com.tencent.bugly.proguard.InterfaceC0648o;
import com.tencent.bugly.proguard.InterfaceC0653t;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.b */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0606b {

    /* JADX INFO: renamed from: a */
    private static int f402a;

    /* JADX INFO: renamed from: b */
    private Context f403b;

    /* JADX INFO: renamed from: c */
    private C0654u f404c;

    /* JADX INFO: renamed from: d */
    private C0649p f405d;

    /* JADX INFO: renamed from: e */
    private C0596a f406e;

    /* JADX INFO: renamed from: f */
    private InterfaceC0648o f407f;

    /* JADX INFO: renamed from: g */
    private BuglyStrategy.C0583a f408g;

    public C0606b(int i, Context context, C0654u c0654u, C0649p c0649p, C0596a c0596a, BuglyStrategy.C0583a c0583a, InterfaceC0648o interfaceC0648o) {
        f402a = i;
        this.f403b = context;
        this.f404c = c0654u;
        this.f405d = c0649p;
        this.f406e = c0596a;
        this.f408g = c0583a;
        this.f407f = interfaceC0648o;
    }

    /* JADX INFO: renamed from: a */
    private static List<C0599a> m187a(List<C0599a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (C0599a c0599a : list) {
            if (c0599a.f363d && c0599a.f361b <= jCurrentTimeMillis - 86400000) {
                arrayList.add(c0599a);
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: a */
    private CrashDetailBean m184a(List<C0599a> list, CrashDetailBean crashDetailBean) {
        List<CrashDetailBean> listM192b;
        String[] strArrSplit;
        if (list == null || list.size() == 0) {
            return crashDetailBean;
        }
        CrashDetailBean crashDetailBean2 = null;
        ArrayList arrayList = new ArrayList(10);
        for (C0599a c0599a : list) {
            if (c0599a.f364e) {
                arrayList.add(c0599a);
            }
        }
        if (arrayList.size() > 0 && (listM192b = m192b(arrayList)) != null && listM192b.size() > 0) {
            Collections.sort(listM192b);
            for (int i = 0; i < listM192b.size(); i++) {
                CrashDetailBean crashDetailBean3 = listM192b.get(i);
                if (i == 0) {
                    crashDetailBean2 = crashDetailBean3;
                } else if (crashDetailBean3.f352s != null && (strArrSplit = crashDetailBean3.f352s.split("\n")) != null) {
                    for (String str : strArrSplit) {
                        if (!crashDetailBean2.f352s.contains(str)) {
                            crashDetailBean2.f353t++;
                            crashDetailBean2.f352s += str + "\n";
                        }
                    }
                }
            }
        }
        if (crashDetailBean2 == null) {
            crashDetailBean.f343j = true;
            crashDetailBean.f353t = 0;
            crashDetailBean.f352s = "";
            crashDetailBean2 = crashDetailBean;
        }
        for (C0599a c0599a2 : list) {
            if (!c0599a2.f364e && !c0599a2.f363d) {
                String str2 = crashDetailBean2.f352s;
                StringBuilder sb = new StringBuilder();
                sb.append(c0599a2.f361b);
                if (!str2.contains(sb.toString())) {
                    crashDetailBean2.f353t++;
                    crashDetailBean2.f352s += c0599a2.f361b + "\n";
                }
            }
        }
        if (crashDetailBean2.f351r != crashDetailBean.f351r) {
            String str3 = crashDetailBean2.f352s;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(crashDetailBean.f351r);
            if (!str3.contains(sb2.toString())) {
                crashDetailBean2.f353t++;
                crashDetailBean2.f352s += crashDetailBean.f351r + "\n";
            }
        }
        return crashDetailBean2;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m199a(CrashDetailBean crashDetailBean) {
        return m200b(crashDetailBean);
    }

    /* JADX INFO: renamed from: b */
    public final boolean m200b(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return true;
        }
        if (C0607c.f424n != null && !C0607c.f424n.isEmpty()) {
            C0657x.m466c("Crash filter for crash stack is: %s", C0607c.f424n);
            if (crashDetailBean.f350q.contains(C0607c.f424n)) {
                C0657x.m467d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (C0607c.f425o != null && !C0607c.f425o.isEmpty()) {
            C0657x.m466c("Crash regular filter for crash stack is: %s", C0607c.f425o);
            if (Pattern.compile(C0607c.f425o).matcher(crashDetailBean.f350q).find()) {
                C0657x.m467d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (crashDetailBean.f335b != 2) {
            C0651r c0651r = new C0651r();
            c0651r.f707b = 1;
            c0651r.f708c = crashDetailBean.f310A;
            c0651r.f709d = crashDetailBean.f311B;
            c0651r.f710e = crashDetailBean.f351r;
            this.f405d.m424b(1);
            this.f405d.m423a(c0651r);
            C0657x.m464b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            C0657x.m464b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List<C0599a> listM191b = m191b();
        ArrayList arrayList = null;
        if (listM191b != null && listM191b.size() > 0) {
            arrayList = new ArrayList(10);
            ArrayList arrayList2 = new ArrayList(10);
            arrayList.addAll(m187a(listM191b));
            listM191b.removeAll(arrayList);
            if (listM191b.size() > 20) {
                StringBuilder sb = new StringBuilder();
                sb.append("_id in ");
                sb.append("(");
                sb.append("SELECT _id");
                sb.append(" FROM t_cr");
                sb.append(" order by _id");
                sb.append(" limit 5");
                sb.append(")");
                String string = sb.toString();
                sb.setLength(0);
                try {
                    C0657x.m466c("deleted first record %s data %d", "t_cr", Integer.valueOf(C0649p.m402a().m416a("t_cr", string, (String[]) null, (InterfaceC0648o) null, true)));
                } catch (Throwable th) {
                    if (!C0657x.m462a(th)) {
                        th.printStackTrace();
                    }
                }
            }
            int i = crashDetailBean.f335b;
            if (!C0585b.f145c && (!((i == 3) || (i == 0 || i == 1)) || C0607c.f414d)) {
                boolean z = false;
                for (C0599a c0599a : listM191b) {
                    if (crashDetailBean.f354u.equals(c0599a.f362c)) {
                        if (c0599a.f364e) {
                            z = true;
                        }
                        arrayList2.add(c0599a);
                    }
                }
                if (z || arrayList2.size() >= C0607c.f413c) {
                    C0657x.m461a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean crashDetailBeanM184a = m184a(arrayList2, crashDetailBean);
                    for (C0599a c0599a2 : arrayList2) {
                        if (c0599a2.f360a != crashDetailBeanM184a.f334a) {
                            arrayList.add(c0599a2);
                        }
                    }
                    m203e(crashDetailBeanM184a);
                    m193c(arrayList);
                    C0657x.m464b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
        }
        m203e(crashDetailBean);
        if (arrayList != null && !arrayList.isEmpty()) {
            m193c(arrayList);
        }
        C0657x.m464b("[crash] save crash success", new Object[0]);
        return false;
    }

    /* JADX INFO: renamed from: a */
    public final List<CrashDetailBean> m196a() {
        StrategyBean strategyBeanM149c = C0596a.m139a().m149c();
        if (strategyBeanM149c == null) {
            C0657x.m467d("have not synced remote!", new Object[0]);
            return null;
        }
        if (!strategyBeanM149c.f276e) {
            C0657x.m467d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            C0657x.m464b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jM513b = C0659z.m513b();
        List<C0599a> listM191b = m191b();
        C0657x.m466c("Size of crash list loaded from DB: %s", Integer.valueOf(listM191b.size()));
        if (listM191b == null || listM191b.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(m187a(listM191b));
        listM191b.removeAll(arrayList);
        Iterator<C0599a> it = listM191b.iterator();
        while (it.hasNext()) {
            C0599a next = it.next();
            if (next.f361b < jM513b - C0607c.f417g) {
                it.remove();
                arrayList.add(next);
            } else if (next.f363d) {
                if (next.f361b >= jCurrentTimeMillis - 86400000) {
                    it.remove();
                } else if (!next.f364e) {
                    it.remove();
                    arrayList.add(next);
                }
            } else if (next.f365f >= 3 && next.f361b < jCurrentTimeMillis - 86400000) {
                it.remove();
                arrayList.add(next);
            }
        }
        if (arrayList.size() > 0) {
            m193c(arrayList);
        }
        ArrayList arrayList2 = new ArrayList();
        List<CrashDetailBean> listM192b = m192b(listM191b);
        if (listM192b != null && listM192b.size() > 0) {
            String str = C0593a.m65b().f252j;
            Iterator<CrashDetailBean> it2 = listM192b.iterator();
            while (it2.hasNext()) {
                CrashDetailBean next2 = it2.next();
                if (!str.equals(next2.f339f)) {
                    it2.remove();
                    arrayList2.add(next2);
                }
            }
        }
        if (arrayList2.size() > 0) {
            m194d(arrayList2);
        }
        return listM192b;
    }

    /* JADX INFO: renamed from: c */
    public final void m201c(CrashDetailBean crashDetailBean) {
        int i = crashDetailBean.f335b;
        if (i != 0) {
            if (i != 1) {
                if (i == 3 && !C0607c.m204a().m231r()) {
                    return;
                }
            } else if (!C0607c.m204a().m230q()) {
                return;
            }
        } else if (!C0607c.m204a().m230q()) {
            return;
        }
        if (this.f407f != null) {
            C0657x.m466c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
            int i2 = crashDetailBean.f335b;
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m197a(CrashDetailBean crashDetailBean, long j, boolean z) {
        if (C0607c.f422l) {
            C0657x.m461a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            m198a(arrayList, 3000L, z, crashDetailBean.f335b == 7, z);
            return;
        }
        C0657x.m461a("do not upload spot crash right now, crash would be uploaded when app next start", new Object[0]);
    }

    /* JADX INFO: renamed from: a */
    public final void m198a(final List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        C0628al c0628al;
        if (!C0593a.m64a(this.f403b).f247e) {
            C0657x.m467d("warn: not upload process", new Object[0]);
            return;
        }
        C0654u c0654u = this.f404c;
        if (c0654u == null) {
            C0657x.m467d("warn: upload manager is null", new Object[0]);
            return;
        }
        if (!z3 && !c0654u.m447b(C0607c.f411a)) {
            C0657x.m467d("warn: not crashHappen or not should upload", new Object[0]);
            return;
        }
        StrategyBean strategyBeanM149c = this.f406e.m149c();
        if (!strategyBeanM149c.f276e) {
            C0657x.m467d("remote report is disable!", new Object[0]);
            C0657x.m464b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            return;
        }
        if (list == null || list.size() == 0) {
            C0657x.m467d("warn: crashList is null or crashList num is 0", new Object[0]);
            return;
        }
        try {
            String str = strategyBeanM149c.f288q;
            String str2 = StrategyBean.f273b;
            Context context = this.f403b;
            C0593a c0593aM65b = C0593a.m65b();
            if (context == null || list == null || list.size() == 0 || c0593aM65b == null) {
                C0657x.m467d("enEXPPkg args == null!", new Object[0]);
                c0628al = null;
            } else {
                c0628al = new C0628al();
                c0628al.f565a = new ArrayList<>();
                Iterator<CrashDetailBean> it = list.iterator();
                while (it.hasNext()) {
                    c0628al.f565a.add(m186a(context, it.next(), c0593aM65b));
                }
            }
            if (c0628al == null) {
                C0657x.m467d("create eupPkg fail!", new Object[0]);
                return;
            }
            byte[] bArrM285a = C0616a.m285a((AbstractC0644k) c0628al);
            if (bArrM285a == null) {
                C0657x.m467d("send encode fail!", new Object[0]);
                return;
            }
            C0629am c0629amM277a = C0616a.m277a(this.f403b, 830, bArrM285a);
            if (c0629amM277a == null) {
                C0657x.m467d("request package is null.", new Object[0]);
                return;
            }
            InterfaceC0653t interfaceC0653t = new InterfaceC0653t() { // from class: com.tencent.bugly.crashreport.crash.b.1
                @Override // com.tencent.bugly.proguard.InterfaceC0653t
                /* JADX INFO: renamed from: a */
                public final void mo33a(boolean z4) {
                    C0606b.m189a(z4, (List<CrashDetailBean>) list);
                }
            };
            if (z) {
                this.f404c.m444a(f402a, c0629amM277a, str, str2, interfaceC0653t, j, z2);
            } else {
                this.f404c.m445a(f402a, c0629amM277a, str, str2, interfaceC0653t, false);
            }
        } catch (Throwable th) {
            C0657x.m468e("req cr error %s", th.toString());
            if (C0657x.m465b(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m189a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            C0657x.m466c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean crashDetailBean : list) {
                C0657x.m466c("pre uid:%s uc:%d re:%b me:%b", crashDetailBean.f336c, Integer.valueOf(crashDetailBean.f345l), Boolean.valueOf(crashDetailBean.f337d), Boolean.valueOf(crashDetailBean.f343j));
                crashDetailBean.f345l++;
                crashDetailBean.f337d = z;
                C0657x.m466c("set uid:%s uc:%d re:%b me:%b", crashDetailBean.f336c, Integer.valueOf(crashDetailBean.f345l), Boolean.valueOf(crashDetailBean.f337d), Boolean.valueOf(crashDetailBean.f343j));
            }
            Iterator<CrashDetailBean> it = list.iterator();
            while (it.hasNext()) {
                C0607c.m204a().m211a(it.next());
            }
            C0657x.m466c("update state size %d", Integer.valueOf(list.size()));
        }
        if (z) {
            return;
        }
        C0657x.m464b("[crash] upload fail.", new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00aa  */
    /* JADX INFO: renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m202d(com.tencent.bugly.crashreport.crash.CrashDetailBean r13) {
        /*
            Method dump skipped, instruction units count: 546
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.C0606b.m202d(com.tencent.bugly.crashreport.crash.CrashDetailBean):void");
    }

    /* JADX INFO: renamed from: f */
    private static ContentValues m195f(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.f334a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.f334a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.f351r));
            contentValues.put("_s1", crashDetailBean.f354u);
            int i = 1;
            contentValues.put("_up", Integer.valueOf(crashDetailBean.f337d ? 1 : 0));
            if (!crashDetailBean.f343j) {
                i = 0;
            }
            contentValues.put("_me", Integer.valueOf(i));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.f345l));
            contentValues.put("_dt", C0659z.m510a(crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private static CrashDetailBean m183a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) C0659z.m491a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean != null) {
                crashDetailBean.f334a = j;
            }
            return crashDetailBean;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: e */
    public final void m203e(CrashDetailBean crashDetailBean) {
        ContentValues contentValuesM195f;
        if (crashDetailBean == null || (contentValuesM195f = m195f(crashDetailBean)) == null) {
            return;
        }
        long jM417a = C0649p.m402a().m417a("t_cr", contentValuesM195f, (InterfaceC0648o) null, true);
        if (jM417a >= 0) {
            C0657x.m466c("insert %s success!", "t_cr");
            crashDetailBean.f334a = jM417a;
        }
    }

    /* JADX INFO: renamed from: b */
    private List<CrashDetailBean> m192b(List<C0599a> list) {
        Cursor cursorM418a;
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("_id in ");
        sb.append("(");
        Iterator<C0599a> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().f360a);
            sb.append(",");
        }
        if (sb.toString().contains(",")) {
            sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        }
        sb.append(")");
        String string = sb.toString();
        sb.setLength(0);
        try {
            cursorM418a = C0649p.m402a().m418a("t_cr", null, string, null, null, true);
            if (cursorM418a == null) {
                return null;
            }
            try {
                ArrayList arrayList = new ArrayList();
                sb.append("_id in ");
                sb.append("(");
                int i = 0;
                while (cursorM418a.moveToNext()) {
                    CrashDetailBean crashDetailBeanM183a = m183a(cursorM418a);
                    if (crashDetailBeanM183a != null) {
                        arrayList.add(crashDetailBeanM183a);
                    } else {
                        try {
                            sb.append(cursorM418a.getLong(cursorM418a.getColumnIndex("_id")));
                            sb.append(",");
                            i++;
                        } catch (Throwable unused) {
                            C0657x.m467d("unknown id!", new Object[0]);
                        }
                    }
                }
                if (sb.toString().contains(",")) {
                    sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
                }
                sb.append(")");
                String string2 = sb.toString();
                if (i > 0) {
                    C0657x.m467d("deleted %s illegal data %d", "t_cr", Integer.valueOf(C0649p.m402a().m416a("t_cr", string2, (String[]) null, (InterfaceC0648o) null, true)));
                }
                if (cursorM418a != null) {
                    cursorM418a.close();
                }
                return arrayList;
            } catch (Throwable th) {
                th = th;
                try {
                    if (!C0657x.m462a(th)) {
                        th.printStackTrace();
                    }
                    if (cursorM418a != null) {
                        cursorM418a.close();
                    }
                    return null;
                } finally {
                    if (cursorM418a != null) {
                        cursorM418a.close();
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursorM418a = null;
        }
    }

    /* JADX INFO: renamed from: b */
    private static C0599a m190b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            C0599a c0599a = new C0599a();
            c0599a.f360a = cursor.getLong(cursor.getColumnIndex("_id"));
            c0599a.f361b = cursor.getLong(cursor.getColumnIndex("_tm"));
            c0599a.f362c = cursor.getString(cursor.getColumnIndex("_s1"));
            c0599a.f363d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            c0599a.f364e = cursor.getInt(cursor.getColumnIndex("_me")) == 1;
            c0599a.f365f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return c0599a;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: b */
    private List<C0599a> m191b() {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor cursorM418a = C0649p.m402a().m418a("t_cr", new String[]{"_id", "_tm", "_s1", "_up", "_me", "_uc"}, null, null, null, true);
            if (cursorM418a == null) {
                if (cursorM418a != null) {
                    cursorM418a.close();
                }
                return null;
            }
            try {
                if (cursorM418a.getCount() <= 0) {
                    if (cursorM418a != null) {
                        cursorM418a.close();
                    }
                    return arrayList;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("_id in ");
                sb.append("(");
                int i = 0;
                while (cursorM418a.moveToNext()) {
                    C0599a c0599aM190b = m190b(cursorM418a);
                    if (c0599aM190b != null) {
                        arrayList.add(c0599aM190b);
                    } else {
                        try {
                            sb.append(cursorM418a.getLong(cursorM418a.getColumnIndex("_id")));
                            sb.append(",");
                            i++;
                        } catch (Throwable unused) {
                            C0657x.m467d("unknown id!", new Object[0]);
                        }
                    }
                }
                if (sb.toString().contains(",")) {
                    sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
                }
                sb.append(")");
                String string = sb.toString();
                sb.setLength(0);
                if (i > 0) {
                    C0657x.m467d("deleted %s illegal data %d", "t_cr", Integer.valueOf(C0649p.m402a().m416a("t_cr", string, (String[]) null, (InterfaceC0648o) null, true)));
                }
                if (cursorM418a != null) {
                    cursorM418a.close();
                }
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursor = cursorM418a;
                try {
                    if (!C0657x.m462a(th)) {
                        th.printStackTrace();
                    }
                    return arrayList;
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: renamed from: c */
    private static void m193c(List<C0599a> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("_id in ");
        sb.append("(");
        Iterator<C0599a> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().f360a);
            sb.append(",");
        }
        StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        sb2.append(")");
        String string = sb2.toString();
        sb2.setLength(0);
        try {
            C0657x.m466c("deleted %s data %d", "t_cr", Integer.valueOf(C0649p.m402a().m416a("t_cr", string, (String[]) null, (InterfaceC0648o) null, true)));
        } catch (Throwable th) {
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: d */
    private static void m194d(List<CrashDetailBean> list) {
        if (list != null) {
            try {
                if (list.size() == 0) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (CrashDetailBean crashDetailBean : list) {
                    sb.append(" or _id");
                    sb.append(" = ");
                    sb.append(crashDetailBean.f334a);
                }
                String string = sb.toString();
                if (string.length() > 0) {
                    string = string.substring(4);
                }
                sb.setLength(0);
                C0657x.m466c("deleted %s data %d", "t_cr", Integer.valueOf(C0649p.m402a().m416a("t_cr", string, (String[]) null, (InterfaceC0648o) null, true)));
            } catch (Throwable th) {
                if (C0657x.m462a(th)) {
                    return;
                }
                th.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static C0627ak m186a(Context context, CrashDetailBean crashDetailBean, C0593a c0593a) {
        C0626aj c0626ajM185a;
        C0626aj c0626ajM185a2;
        C0626aj c0626aj;
        if (context == null || crashDetailBean == null || c0593a == null) {
            C0657x.m467d("enExp args == null", new Object[0]);
            return null;
        }
        C0627ak c0627ak = new C0627ak();
        switch (crashDetailBean.f335b) {
            case 0:
                c0627ak.f543a = crashDetailBean.f343j ? "200" : "100";
                break;
            case 1:
                c0627ak.f543a = crashDetailBean.f343j ? "201" : "101";
                break;
            case 2:
                c0627ak.f543a = crashDetailBean.f343j ? "202" : "102";
                break;
            case 3:
                c0627ak.f543a = crashDetailBean.f343j ? "203" : "103";
                break;
            case 4:
                c0627ak.f543a = crashDetailBean.f343j ? "204" : "104";
                break;
            case 5:
                c0627ak.f543a = crashDetailBean.f343j ? "207" : "107";
                break;
            case 6:
                c0627ak.f543a = crashDetailBean.f343j ? "206" : "106";
                break;
            case 7:
                c0627ak.f543a = crashDetailBean.f343j ? "208" : "108";
                break;
            default:
                C0657x.m468e("crash type error! %d", Integer.valueOf(crashDetailBean.f335b));
                break;
        }
        c0627ak.f544b = crashDetailBean.f351r;
        c0627ak.f545c = crashDetailBean.f347n;
        c0627ak.f546d = crashDetailBean.f348o;
        c0627ak.f547e = crashDetailBean.f349p;
        c0627ak.f549g = crashDetailBean.f350q;
        c0627ak.f550h = crashDetailBean.f359z;
        c0627ak.f551i = crashDetailBean.f336c;
        c0627ak.f552j = null;
        c0627ak.f554l = crashDetailBean.f346m;
        c0627ak.f555m = crashDetailBean.f338e;
        c0627ak.f548f = crashDetailBean.f311B;
        c0627ak.f556n = null;
        C0657x.m466c("libInfo %s", c0627ak.f557o);
        if (crashDetailBean.f341h != null && crashDetailBean.f341h.size() > 0) {
            c0627ak.f558p = new ArrayList<>();
            for (Map.Entry<String, PlugInBean> entry : crashDetailBean.f341h.entrySet()) {
                C0624ah c0624ah = new C0624ah();
                c0624ah.f523a = entry.getValue().f199a;
                c0624ah.f524b = entry.getValue().f201c;
                c0624ah.f525c = entry.getValue().f200b;
                c0627ak.f558p.add(c0624ah);
            }
        }
        if (crashDetailBean.f343j) {
            c0627ak.f553k = crashDetailBean.f353t;
            if (crashDetailBean.f352s != null && crashDetailBean.f352s.length() > 0) {
                if (c0627ak.f559q == null) {
                    c0627ak.f559q = new ArrayList<>();
                }
                try {
                    c0627ak.f559q.add(new C0626aj((byte) 1, "alltimes.txt", crashDetailBean.f352s.getBytes("utf-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    c0627ak.f559q = null;
                }
            }
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(c0627ak.f553k);
            objArr[1] = Integer.valueOf(c0627ak.f559q != null ? c0627ak.f559q.size() : 0);
            C0657x.m466c("crashcount:%d sz:%d", objArr);
        }
        if (crashDetailBean.f356w != null) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            try {
                c0627ak.f559q.add(new C0626aj((byte) 1, "log.txt", crashDetailBean.f356w.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                c0627ak.f559q = null;
            }
        }
        if (crashDetailBean.f357x != null) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            try {
                c0627ak.f559q.add(new C0626aj((byte) 1, "jniLog.txt", crashDetailBean.f357x.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                c0627ak.f559q = null;
            }
        }
        if (!C0659z.m509a(crashDetailBean.f331V)) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            try {
                c0626aj = new C0626aj((byte) 1, "crashInfos.txt", crashDetailBean.f331V.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e4) {
                e4.printStackTrace();
                c0626aj = null;
            }
            if (c0626aj != null) {
                C0657x.m466c("attach crash infos", new Object[0]);
                c0627ak.f559q.add(c0626aj);
            }
        }
        if (crashDetailBean.f332W != null) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            C0626aj c0626ajM185a3 = m185a("backupRecord.zip", context, crashDetailBean.f332W);
            if (c0626ajM185a3 != null) {
                C0657x.m466c("attach backup record", new Object[0]);
                c0627ak.f559q.add(c0626ajM185a3);
            }
        }
        if (crashDetailBean.f358y != null && crashDetailBean.f358y.length > 0) {
            C0626aj c0626aj2 = new C0626aj((byte) 2, "buglylog.zip", crashDetailBean.f358y);
            C0657x.m466c("attach user log", new Object[0]);
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            c0627ak.f559q.add(c0626aj2);
        }
        if (crashDetailBean.f335b == 3) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            C0657x.m466c("crashBean.anrMessages:%s", crashDetailBean.f325P);
            if (crashDetailBean.f325P != null && crashDetailBean.f325P.containsKey("BUGLY_CR_01")) {
                try {
                    if (!TextUtils.isEmpty(crashDetailBean.f325P.get("BUGLY_CR_01"))) {
                        c0627ak.f559q.add(new C0626aj((byte) 1, "anrMessage.txt", crashDetailBean.f325P.get("BUGLY_CR_01").getBytes("utf-8")));
                        C0657x.m466c("attach anr message", new Object[0]);
                    }
                } catch (UnsupportedEncodingException e5) {
                    e5.printStackTrace();
                    c0627ak.f559q = null;
                }
                crashDetailBean.f325P.remove("BUGLY_CR_01");
            }
            if (crashDetailBean.f355v != null && NativeCrashHandler.getInstance().isEnableCatchAnrTrace() && (c0626ajM185a2 = m185a("trace.zip", context, crashDetailBean.f355v)) != null) {
                C0657x.m466c("attach traces", new Object[0]);
                c0627ak.f559q.add(c0626ajM185a2);
            }
        }
        if (crashDetailBean.f335b == 1) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            if (crashDetailBean.f355v != null && (c0626ajM185a = m185a("tomb.zip", context, crashDetailBean.f355v)) != null) {
                C0657x.m466c("attach tombs", new Object[0]);
                c0627ak.f559q.add(c0626ajM185a);
            }
        }
        if (c0593a.f205C != null && !c0593a.f205C.isEmpty()) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = c0593a.f205C.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
            }
            try {
                c0627ak.f559q.add(new C0626aj((byte) 1, "martianlog.txt", sb.toString().getBytes("utf-8")));
                C0657x.m466c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e6) {
                e6.printStackTrace();
            }
        }
        if (crashDetailBean.f330U != null && crashDetailBean.f330U.length > 0) {
            if (c0627ak.f559q == null) {
                c0627ak.f559q = new ArrayList<>();
            }
            c0627ak.f559q.add(new C0626aj((byte) 1, "userExtraByteData", crashDetailBean.f330U));
            C0657x.m466c("attach extraData", new Object[0]);
        }
        c0627ak.f560r = new HashMap();
        Map<String, String> map = c0627ak.f560r;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(crashDetailBean.f312C);
        map.put("A9", sb2.toString());
        Map<String, String> map2 = c0627ak.f560r;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(crashDetailBean.f313D);
        map2.put("A11", sb3.toString());
        Map<String, String> map3 = c0627ak.f560r;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(crashDetailBean.f314E);
        map3.put("A10", sb4.toString());
        c0627ak.f560r.put("A23", crashDetailBean.f339f);
        c0627ak.f560r.put("A7", c0593a.f249g);
        c0627ak.f560r.put("A6", c0593a.m99o());
        c0627ak.f560r.put("A5", c0593a.m98n());
        c0627ak.f560r.put("A22", c0593a.m91h());
        Map<String, String> map4 = c0627ak.f560r;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(crashDetailBean.f316G);
        map4.put("A2", sb5.toString());
        Map<String, String> map5 = c0627ak.f560r;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(crashDetailBean.f315F);
        map5.put("A1", sb6.toString());
        c0627ak.f560r.put("A24", c0593a.f250h);
        Map<String, String> map6 = c0627ak.f560r;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(crashDetailBean.f317H);
        map6.put("A17", sb7.toString());
        c0627ak.f560r.put("A25", c0593a.m91h());
        c0627ak.f560r.put("A15", c0593a.m102r());
        Map<String, String> map7 = c0627ak.f560r;
        StringBuilder sb8 = new StringBuilder();
        sb8.append(c0593a.m103s());
        map7.put("A13", sb8.toString());
        c0627ak.f560r.put("A34", crashDetailBean.f310A);
        if (c0593a.f266x != null) {
            c0627ak.f560r.put("productIdentify", c0593a.f266x);
        }
        try {
            c0627ak.f560r.put("A26", URLEncoder.encode(crashDetailBean.f318I, "utf-8"));
        } catch (UnsupportedEncodingException e7) {
            e7.printStackTrace();
        }
        if (crashDetailBean.f335b == 1) {
            c0627ak.f560r.put("A27", crashDetailBean.f320K);
            c0627ak.f560r.put("A28", crashDetailBean.f319J);
            Map<String, String> map8 = c0627ak.f560r;
            StringBuilder sb9 = new StringBuilder();
            sb9.append(crashDetailBean.f344k);
            map8.put("A29", sb9.toString());
        }
        c0627ak.f560r.put("A30", crashDetailBean.f321L);
        Map<String, String> map9 = c0627ak.f560r;
        StringBuilder sb10 = new StringBuilder();
        sb10.append(crashDetailBean.f322M);
        map9.put("A18", sb10.toString());
        Map<String, String> map10 = c0627ak.f560r;
        StringBuilder sb11 = new StringBuilder();
        sb11.append(!crashDetailBean.f323N);
        map10.put("A36", sb11.toString());
        Map<String, String> map11 = c0627ak.f560r;
        StringBuilder sb12 = new StringBuilder();
        sb12.append(c0593a.f259q);
        map11.put("F02", sb12.toString());
        Map<String, String> map12 = c0627ak.f560r;
        StringBuilder sb13 = new StringBuilder();
        sb13.append(c0593a.f260r);
        map12.put("F03", sb13.toString());
        c0627ak.f560r.put("F04", c0593a.m85e());
        Map<String, String> map13 = c0627ak.f560r;
        StringBuilder sb14 = new StringBuilder();
        sb14.append(c0593a.f261s);
        map13.put("F05", sb14.toString());
        c0627ak.f560r.put("F06", c0593a.f258p);
        c0627ak.f560r.put("F08", c0593a.f264v);
        c0627ak.f560r.put("F09", c0593a.f265w);
        Map<String, String> map14 = c0627ak.f560r;
        StringBuilder sb15 = new StringBuilder();
        sb15.append(c0593a.f262t);
        map14.put("F10", sb15.toString());
        if (crashDetailBean.f326Q >= 0) {
            Map<String, String> map15 = c0627ak.f560r;
            StringBuilder sb16 = new StringBuilder();
            sb16.append(crashDetailBean.f326Q);
            map15.put("C01", sb16.toString());
        }
        if (crashDetailBean.f327R >= 0) {
            Map<String, String> map16 = c0627ak.f560r;
            StringBuilder sb17 = new StringBuilder();
            sb17.append(crashDetailBean.f327R);
            map16.put("C02", sb17.toString());
        }
        if (crashDetailBean.f328S != null && crashDetailBean.f328S.size() > 0) {
            for (Map.Entry<String, String> entry2 : crashDetailBean.f328S.entrySet()) {
                c0627ak.f560r.put("C03_" + entry2.getKey(), entry2.getValue());
            }
        }
        if (crashDetailBean.f329T != null && crashDetailBean.f329T.size() > 0) {
            for (Map.Entry<String, String> entry3 : crashDetailBean.f329T.entrySet()) {
                c0627ak.f560r.put("C04_" + entry3.getKey(), entry3.getValue());
            }
        }
        c0627ak.f561s = null;
        if (crashDetailBean.f324O != null && crashDetailBean.f324O.size() > 0) {
            c0627ak.f561s = crashDetailBean.f324O;
            C0657x.m461a("setted message size %d", Integer.valueOf(c0627ak.f561s.size()));
        }
        Object[] objArr2 = new Object[12];
        objArr2[0] = crashDetailBean.f347n;
        objArr2[1] = crashDetailBean.f336c;
        objArr2[2] = c0593a.m85e();
        objArr2[3] = Long.valueOf((crashDetailBean.f351r - crashDetailBean.f322M) / 1000);
        objArr2[4] = Boolean.valueOf(crashDetailBean.f344k);
        objArr2[5] = Boolean.valueOf(crashDetailBean.f323N);
        objArr2[6] = Boolean.valueOf(crashDetailBean.f343j);
        objArr2[7] = Boolean.valueOf(crashDetailBean.f335b == 1);
        objArr2[8] = Integer.valueOf(crashDetailBean.f353t);
        objArr2[9] = crashDetailBean.f352s;
        objArr2[10] = Boolean.valueOf(crashDetailBean.f337d);
        objArr2[11] = Integer.valueOf(c0627ak.f560r.size());
        C0657x.m466c("%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d", objArr2);
        return c0627ak;
    }

    /* JADX INFO: renamed from: a */
    private static C0626aj m185a(String str, Context context, String str2) {
        FileInputStream fileInputStream;
        if (str2 == null || context == null) {
            C0657x.m467d("rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}", new Object[0]);
            return null;
        }
        C0657x.m466c("zip %s", str2);
        File file = new File(str2);
        File file2 = new File(context.getCacheDir(), str);
        if (!C0659z.m507a(file, file2, 5000)) {
            C0657x.m467d("zip fail!", new Object[0]);
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            fileInputStream = new FileInputStream(file2);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
        }
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
                byteArrayOutputStream.flush();
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            C0657x.m466c("read bytes :%d", Integer.valueOf(byteArray.length));
            C0626aj c0626aj = new C0626aj((byte) 2, file2.getName(), byteArray);
            try {
                fileInputStream.close();
            } catch (IOException e) {
                if (!C0657x.m462a(e)) {
                    e.printStackTrace();
                }
            }
            if (file2.exists()) {
                C0657x.m466c("del tmp", new Object[0]);
                file2.delete();
            }
            return c0626aj;
        } catch (Throwable th2) {
            th = th2;
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        if (!C0657x.m462a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                }
                if (file2.exists()) {
                    C0657x.m466c("del tmp", new Object[0]);
                    file2.delete();
                }
                return null;
            } catch (Throwable th3) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                        if (!C0657x.m462a(e3)) {
                            e3.printStackTrace();
                        }
                    }
                }
                if (file2.exists()) {
                    C0657x.m466c("del tmp", new Object[0]);
                    file2.delete();
                }
                throw th3;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m188a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        String str6;
        C0593a c0593aM65b = C0593a.m65b();
        if (c0593aM65b == null) {
            return;
        }
        C0657x.m468e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
        C0657x.m468e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
        C0657x.m468e("# PKG NAME: %s", c0593aM65b.f245c);
        C0657x.m468e("# APP VER: %s", c0593aM65b.f252j);
        C0657x.m468e("# SDK VER: %s", c0593aM65b.f248f);
        C0657x.m468e("# LAUNCH TIME: %s", C0659z.m498a(new Date(C0593a.m65b().f228a)));
        C0657x.m468e("# CRASH TYPE: %s", str);
        C0657x.m468e("# CRASH TIME: %s", str2);
        C0657x.m468e("# CRASH PROCESS: %s", str3);
        C0657x.m468e("# CRASH THREAD: %s", str4);
        if (crashDetailBean != null) {
            C0657x.m468e("# REPORT ID: %s", crashDetailBean.f336c);
            Object[] objArr = new Object[2];
            objArr[0] = c0593aM65b.m93i();
            objArr[1] = c0593aM65b.m103s().booleanValue() ? "ROOTED" : "UNROOT";
            C0657x.m468e("# CRASH DEVICE: %s %s", objArr);
            C0657x.m468e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.f312C), Long.valueOf(crashDetailBean.f313D), Long.valueOf(crashDetailBean.f314E));
            C0657x.m468e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.f315F), Long.valueOf(crashDetailBean.f316G), Long.valueOf(crashDetailBean.f317H));
            if (!C0659z.m509a(crashDetailBean.f320K)) {
                C0657x.m468e("# EXCEPTION FIRED BY %s %s", crashDetailBean.f320K, crashDetailBean.f319J);
            } else if (crashDetailBean.f335b == 3) {
                Object[] objArr2 = new Object[1];
                if (crashDetailBean.f325P == null) {
                    str6 = "null";
                } else {
                    str6 = crashDetailBean.f325P.get("BUGLY_CR_01");
                }
                objArr2[0] = str6;
                C0657x.m468e("# EXCEPTION ANR MESSAGE:\n %s", objArr2);
            }
        }
        if (!C0659z.m509a(str5)) {
            C0657x.m468e("# CRASH STACK: ", new Object[0]);
            C0657x.m468e(str5, new Object[0]);
        }
        C0657x.m468e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
    }
}
