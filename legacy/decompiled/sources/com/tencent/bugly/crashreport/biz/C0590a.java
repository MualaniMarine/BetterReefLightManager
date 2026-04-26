package com.tencent.bugly.crashreport.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.proguard.C0649p;
import com.tencent.bugly.proguard.C0656w;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import com.tencent.bugly.proguard.InterfaceC0648o;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.biz.a */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0590a {

    /* JADX INFO: renamed from: a */
    private Context f170a;

    /* JADX INFO: renamed from: b */
    private long f171b;

    /* JADX INFO: renamed from: c */
    private int f172c;

    /* JADX INFO: renamed from: d */
    private boolean f173d;

    /* JADX INFO: renamed from: a */
    static /* synthetic */ void m25a(C0590a c0590a, UserInfoBean userInfoBean, boolean z) {
        List<UserInfoBean> listM29a;
        if (userInfoBean != null) {
            if (!z && userInfoBean.f152b != 1 && (listM29a = c0590a.m29a(C0593a.m64a(c0590a.f170a).f246d)) != null && listM29a.size() >= 20) {
                C0657x.m461a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(listM29a.size()));
                return;
            }
            long jM417a = C0649p.m402a().m417a("t_ui", m22a(userInfoBean), (InterfaceC0648o) null, true);
            if (jM417a >= 0) {
                C0657x.m466c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(jM417a));
                userInfoBean.f151a = jM417a;
            }
        }
    }

    public C0590a(Context context, boolean z) {
        this.f173d = true;
        this.f170a = context;
        this.f173d = z;
    }

    /* JADX INFO: renamed from: a */
    public final void m31a(int i, boolean z, long j) {
        C0596a c0596aM139a = C0596a.m139a();
        if (c0596aM139a != null && !c0596aM139a.m149c().f277f && i != 1 && i != 3) {
            C0657x.m468e("UserInfo is disable", new Object[0]);
            return;
        }
        if (i == 1 || i == 3) {
            this.f172c++;
        }
        C0593a c0593aM64a = C0593a.m64a(this.f170a);
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.f152b = i;
        userInfoBean.f153c = c0593aM64a.f246d;
        userInfoBean.f154d = c0593aM64a.m89g();
        userInfoBean.f155e = System.currentTimeMillis();
        userInfoBean.f156f = -1L;
        userInfoBean.f164n = c0593aM64a.f252j;
        userInfoBean.f165o = i == 1 ? 1 : 0;
        userInfoBean.f162l = c0593aM64a.m75a();
        userInfoBean.f163m = c0593aM64a.f258p;
        userInfoBean.f157g = c0593aM64a.f259q;
        userInfoBean.f158h = c0593aM64a.f260r;
        userInfoBean.f159i = c0593aM64a.f261s;
        userInfoBean.f161k = c0593aM64a.f262t;
        userInfoBean.f168r = c0593aM64a.m105u();
        userInfoBean.f169s = c0593aM64a.m110z();
        userInfoBean.f166p = c0593aM64a.m66A();
        userInfoBean.f167q = c0593aM64a.m67B();
        C0656w.m453a().m456a(new a(userInfoBean, z), 0L);
    }

    /* JADX INFO: renamed from: a */
    public final void m30a() {
        this.f171b = C0659z.m513b() + 86400000;
        C0656w.m453a().m456a(new b(), (this.f171b - System.currentTimeMillis()) + 5000);
    }

    /* JADX INFO: renamed from: com.tencent.bugly.crashreport.biz.a$a */
    /* JADX INFO: compiled from: BUGLY */
    class a implements Runnable {

        /* JADX INFO: renamed from: a */
        private boolean f177a;

        /* JADX INFO: renamed from: b */
        private UserInfoBean f178b;

        public a(UserInfoBean userInfoBean, boolean z) {
            this.f178b = userInfoBean;
            this.f177a = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            C0593a c0593aM65b;
            try {
                if (this.f178b != null) {
                    UserInfoBean userInfoBean = this.f178b;
                    if (userInfoBean != null && (c0593aM65b = C0593a.m65b()) != null) {
                        userInfoBean.f160j = c0593aM65b.m85e();
                    }
                    C0657x.m466c("[UserInfo] Record user info.", new Object[0]);
                    C0590a.m25a(C0590a.this, this.f178b, false);
                }
                if (this.f177a) {
                    C0590a c0590a = C0590a.this;
                    C0656w c0656wM453a = C0656w.m453a();
                    if (c0656wM453a != null) {
                        c0656wM453a.m455a(c0590a.new AnonymousClass2());
                    }
                }
            } catch (Throwable th) {
                if (C0657x.m462a(th)) {
                    return;
                }
                th.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f2 A[Catch: all -> 0x0173, TryCatch #0 {, blocks: (B:3:0x0001, B:7:0x0007, B:11:0x000f, B:15:0x0017, B:17:0x001d, B:21:0x0027, B:23:0x003c, B:26:0x0045, B:28:0x004c, B:29:0x004f, B:31:0x0055, B:33:0x0069, B:34:0x0079, B:38:0x0081, B:39:0x008b, B:40:0x0090, B:42:0x0096, B:44:0x00a4, B:46:0x00b1, B:47:0x00b4, B:49:0x00c2, B:51:0x00c6, B:53:0x00cb, B:55:0x00d0, B:58:0x00d7, B:61:0x00ec, B:63:0x00f2, B:65:0x00f7, B:68:0x00fe, B:72:0x0116, B:74:0x011c, B:77:0x0125, B:79:0x012b, B:82:0x0134, B:84:0x013e, B:87:0x0147, B:91:0x0165, B:94:0x016a, B:59:0x00e6), top: B:100:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x011c A[Catch: all -> 0x0173, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:7:0x0007, B:11:0x000f, B:15:0x0017, B:17:0x001d, B:21:0x0027, B:23:0x003c, B:26:0x0045, B:28:0x004c, B:29:0x004f, B:31:0x0055, B:33:0x0069, B:34:0x0079, B:38:0x0081, B:39:0x008b, B:40:0x0090, B:42:0x0096, B:44:0x00a4, B:46:0x00b1, B:47:0x00b4, B:49:0x00c2, B:51:0x00c6, B:53:0x00cb, B:55:0x00d0, B:58:0x00d7, B:61:0x00ec, B:63:0x00f2, B:65:0x00f7, B:68:0x00fe, B:72:0x0116, B:74:0x011c, B:77:0x0125, B:79:0x012b, B:82:0x0134, B:84:0x013e, B:87:0x0147, B:91:0x0165, B:94:0x016a, B:59:0x00e6), top: B:100:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0125 A[Catch: all -> 0x0173, TRY_ENTER, TryCatch #0 {, blocks: (B:3:0x0001, B:7:0x0007, B:11:0x000f, B:15:0x0017, B:17:0x001d, B:21:0x0027, B:23:0x003c, B:26:0x0045, B:28:0x004c, B:29:0x004f, B:31:0x0055, B:33:0x0069, B:34:0x0079, B:38:0x0081, B:39:0x008b, B:40:0x0090, B:42:0x0096, B:44:0x00a4, B:46:0x00b1, B:47:0x00b4, B:49:0x00c2, B:51:0x00c6, B:53:0x00cb, B:55:0x00d0, B:58:0x00d7, B:61:0x00ec, B:63:0x00f2, B:65:0x00f7, B:68:0x00fe, B:72:0x0116, B:74:0x011c, B:77:0x0125, B:79:0x012b, B:82:0x0134, B:84:0x013e, B:87:0x0147, B:91:0x0165, B:94:0x016a, B:59:0x00e6), top: B:100:0x0001 }] */
    /* JADX INFO: renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void m28c() {
        /*
            Method dump skipped, instruction units count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.biz.C0590a.m28c():void");
    }

    /* JADX INFO: renamed from: b */
    public final void m32b() {
        C0656w c0656wM453a = C0656w.m453a();
        if (c0656wM453a != null) {
            c0656wM453a.m455a(new AnonymousClass2());
        }
    }

    /* JADX INFO: renamed from: com.tencent.bugly.crashreport.biz.a$2, reason: invalid class name */
    /* JADX INFO: compiled from: BUGLY */
    final class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                C0590a.this.m28c();
            } catch (Throwable th) {
                C0657x.m462a(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.tencent.bugly.crashreport.biz.a$b */
    /* JADX INFO: compiled from: BUGLY */
    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis < C0590a.this.f171b) {
                C0656w.m453a().m456a(C0590a.this.new b(), (C0590a.this.f171b - jCurrentTimeMillis) + 5000);
            } else {
                C0590a.this.m31a(3, false, 0L);
                C0590a.this.m30a();
            }
        }
    }

    /* JADX INFO: renamed from: com.tencent.bugly.crashreport.biz.a$c */
    /* JADX INFO: compiled from: BUGLY */
    class c implements Runnable {

        /* JADX INFO: renamed from: a */
        private long f181a;

        public c(long j) {
            this.f181a = 21600000L;
            this.f181a = j;
        }

        @Override // java.lang.Runnable
        public final void run() {
            C0590a c0590a = C0590a.this;
            C0656w c0656wM453a = C0656w.m453a();
            if (c0656wM453a != null) {
                c0656wM453a.m455a(c0590a.new AnonymousClass2());
            }
            C0590a c0590a2 = C0590a.this;
            long j = this.f181a;
            C0656w.m453a().m456a(c0590a2.new c(j), j);
        }
    }

    /* JADX INFO: renamed from: a */
    public final List<UserInfoBean> m29a(String str) {
        Cursor cursorM418a;
        String str2;
        try {
            if (C0659z.m509a(str)) {
                str2 = null;
            } else {
                str2 = "_pc = '" + str + "'";
            }
            cursorM418a = C0649p.m402a().m418a("t_ui", null, str2, null, null, true);
            if (cursorM418a == null) {
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                ArrayList arrayList = new ArrayList();
                while (cursorM418a.moveToNext()) {
                    UserInfoBean userInfoBeanM23a = m23a(cursorM418a);
                    if (userInfoBeanM23a != null) {
                        arrayList.add(userInfoBeanM23a);
                    } else {
                        try {
                            long j = cursorM418a.getLong(cursorM418a.getColumnIndex("_id"));
                            sb.append(" or _id");
                            sb.append(" = ");
                            sb.append(j);
                        } catch (Throwable unused) {
                            C0657x.m467d("[Database] unknown id.", new Object[0]);
                        }
                    }
                }
                String string = sb.toString();
                if (string.length() > 0) {
                    C0657x.m467d("[Database] deleted %s error data %d", "t_ui", Integer.valueOf(C0649p.m402a().m416a("t_ui", string.substring(4), (String[]) null, (InterfaceC0648o) null, true)));
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

    /* JADX INFO: renamed from: a */
    private static void m26a(List<UserInfoBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() && i < 50; i++) {
            UserInfoBean userInfoBean = list.get(i);
            sb.append(" or _id");
            sb.append(" = ");
            sb.append(userInfoBean.f151a);
        }
        String string = sb.toString();
        if (string.length() > 0) {
            string = string.substring(4);
        }
        String str = string;
        sb.setLength(0);
        try {
            C0657x.m466c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(C0649p.m402a().m416a("t_ui", str, (String[]) null, (InterfaceC0648o) null, true)));
        } catch (Throwable th) {
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a */
    private static ContentValues m22a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (userInfoBean.f151a > 0) {
                contentValues.put("_id", Long.valueOf(userInfoBean.f151a));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.f155e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f156f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.f152b));
            contentValues.put("_pc", userInfoBean.f153c);
            contentValues.put("_dt", C0659z.m510a(userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private static UserInfoBean m23a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) C0659z.m491a(blob, UserInfoBean.CREATOR);
            if (userInfoBean != null) {
                userInfoBean.f151a = j;
            }
            return userInfoBean;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
