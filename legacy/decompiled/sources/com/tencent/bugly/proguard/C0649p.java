package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.bugly.AbstractC0584a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.p */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0649p {

    /* JADX INFO: renamed from: a */
    private static C0649p f681a = null;

    /* JADX INFO: renamed from: b */
    private static C0650q f682b = null;

    /* JADX INFO: renamed from: c */
    private static boolean f683c = false;

    private C0649p(Context context, List<AbstractC0584a> list) {
        f682b = new C0650q(context, list);
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0649p m403a(Context context, List<AbstractC0584a> list) {
        if (f681a == null) {
            f681a = new C0649p(context, list);
        }
        return f681a;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0649p m402a() {
        return f681a;
    }

    /* JADX INFO: renamed from: a */
    public final long m417a(String str, ContentValues contentValues, InterfaceC0648o interfaceC0648o, boolean z) {
        return m399a(str, contentValues, (InterfaceC0648o) null);
    }

    /* JADX INFO: renamed from: a */
    public final Cursor m418a(String str, String[] strArr, String str2, String[] strArr2, InterfaceC0648o interfaceC0648o, boolean z) {
        return m401a(false, str, strArr, str2, null, null, null, null, null, null);
    }

    /* JADX INFO: renamed from: a */
    public final int m416a(String str, String str2, String[] strArr, InterfaceC0648o interfaceC0648o, boolean z) {
        return m397a(str, str2, (String[]) null, (InterfaceC0648o) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public synchronized long m399a(String str, ContentValues contentValues, InterfaceC0648o interfaceC0648o) {
        long j;
        j = 0;
        try {
            SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
            if (writableDatabase != null && contentValues != null) {
                long jReplace = writableDatabase.replace(str, "_id", contentValues);
                if (jReplace >= 0) {
                    C0657x.m466c("[Database] insert %s success.", str);
                } else {
                    C0657x.m467d("[Database] replace %s error.", str);
                }
                j = jReplace;
            }
        } catch (Throwable th) {
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                if (interfaceC0648o != null) {
                }
            } finally {
                if (interfaceC0648o != null) {
                    Long.valueOf(0L);
                }
            }
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public synchronized Cursor m401a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, InterfaceC0648o interfaceC0648o) {
        Cursor cursorQuery;
        cursorQuery = null;
        try {
            SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
            if (writableDatabase != null) {
                cursorQuery = writableDatabase.query(z, str, strArr, str2, strArr2, str3, str4, str5, str6);
            }
        } finally {
        }
        return cursorQuery;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public synchronized int m397a(String str, String str2, String[] strArr, InterfaceC0648o interfaceC0648o) {
        int iDelete;
        try {
            SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
            iDelete = writableDatabase != null ? writableDatabase.delete(str, str2, strArr) : 0;
        } catch (Throwable th) {
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                if (interfaceC0648o != null) {
                }
            } finally {
                if (interfaceC0648o != null) {
                    Integer.valueOf(0);
                }
            }
        }
        return iDelete;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m422a(int i, String str, byte[] bArr, InterfaceC0648o interfaceC0648o, boolean z) {
        if (!z) {
            a aVar = new a(4, null);
            aVar.m425a(i, str, bArr);
            C0656w.m453a().m455a(aVar);
            return true;
        }
        return m408a(i, str, bArr, (InterfaceC0648o) null);
    }

    /* JADX INFO: renamed from: a */
    public final Map<String, byte[]> m420a(int i, InterfaceC0648o interfaceC0648o, boolean z) {
        return m405a(i, (InterfaceC0648o) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public boolean m408a(int i, String str, byte[] bArr, InterfaceC0648o interfaceC0648o) {
        boolean zM412b = false;
        try {
            C0651r c0651r = new C0651r();
            c0651r.f706a = i;
            c0651r.f711f = str;
            c0651r.f710e = System.currentTimeMillis();
            c0651r.f712g = bArr;
            zM412b = m412b(c0651r);
        } catch (Throwable th) {
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                if (interfaceC0648o != null) {
                }
            } finally {
                if (interfaceC0648o != null) {
                    Boolean.valueOf(false);
                }
            }
        }
        return zM412b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public Map<String, byte[]> m405a(int i, InterfaceC0648o interfaceC0648o) {
        HashMap map = null;
        try {
            List<C0651r> listM414c = m414c(i);
            if (listM414c == null) {
                return null;
            }
            HashMap map2 = new HashMap();
            try {
                for (C0651r c0651r : listM414c) {
                    byte[] bArr = c0651r.f712g;
                    if (bArr != null) {
                        map2.put(c0651r.f711f, bArr);
                    }
                }
                return map2;
            } catch (Throwable th) {
                th = th;
                map = map2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        if (C0657x.m462a(th)) {
            return map;
        }
        th.printStackTrace();
        return map;
    }

    /* JADX INFO: renamed from: a */
    public final synchronized boolean m423a(C0651r c0651r) {
        ContentValues contentValuesM413c;
        if (c0651r == null) {
            return false;
        }
        try {
            SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
            if (writableDatabase == null || (contentValuesM413c = m413c(c0651r)) == null) {
                return false;
            }
            long jReplace = writableDatabase.replace("t_lr", "_id", contentValuesM413c);
            if (jReplace < 0) {
                return false;
            }
            C0657x.m466c("[Database] insert %s success.", "t_lr");
            c0651r.f706a = jReplace;
            return true;
        } catch (Throwable th) {
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                return false;
            } finally {
            }
        }
    }

    /* JADX INFO: renamed from: b */
    private synchronized boolean m412b(C0651r c0651r) {
        ContentValues contentValuesM415d;
        if (c0651r == null) {
            return false;
        }
        try {
            SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
            if (writableDatabase == null || (contentValuesM415d = m415d(c0651r)) == null) {
                return false;
            }
            long jReplace = writableDatabase.replace("t_pf", "_id", contentValuesM415d);
            if (jReplace < 0) {
                return false;
            }
            C0657x.m466c("[Database] insert %s success.", "t_pf");
            c0651r.f706a = jReplace;
            return true;
        } catch (Throwable th) {
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                return false;
            } finally {
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00aa A[Catch: all -> 0x00b3, TRY_LEAVE, TryCatch #1 {all -> 0x00b3, blocks: (B:36:0x00a4, B:38:0x00aa), top: B:52:0x00a4, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00af A[Catch: all -> 0x00bc, TRY_ENTER, TryCatch #4 {, blocks: (B:3:0x0001, B:14:0x0031, B:31:0x009b, B:40:0x00af, B:43:0x00b6, B:44:0x00b9, B:36:0x00a4, B:38:0x00aa), top: B:58:0x0001, inners: #1 }] */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.util.List<com.tencent.bugly.proguard.C0651r> m419a(int r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            com.tencent.bugly.proguard.q r0 = com.tencent.bugly.proguard.C0649p.f682b     // Catch: java.lang.Throwable -> Lbc
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch: java.lang.Throwable -> Lbc
            r9 = 0
            if (r0 == 0) goto Lba
            if (r12 < 0) goto L20
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1c
            java.lang.String r2 = "_tp = "
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L1c
            r1.append(r12)     // Catch: java.lang.Throwable -> L1c
            java.lang.String r12 = r1.toString()     // Catch: java.lang.Throwable -> L1c
            r4 = r12
            goto L21
        L1c:
            r12 = move-exception
            r0 = r9
            goto La4
        L20:
            r4 = r9
        L21:
            java.lang.String r2 = "t_lr"
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r0
            android.database.Cursor r12 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L1c
            if (r12 != 0) goto L36
            if (r12 == 0) goto L34
            r12.close()     // Catch: java.lang.Throwable -> Lbc
        L34:
            monitor-exit(r11)
            return r9
        L36:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La0
            r1.<init>()     // Catch: java.lang.Throwable -> La0
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> La0
            r2.<init>()     // Catch: java.lang.Throwable -> La0
        L40:
            boolean r3 = r12.moveToNext()     // Catch: java.lang.Throwable -> La0
            r4 = 0
            if (r3 == 0) goto L71
            com.tencent.bugly.proguard.r r3 = m404a(r12)     // Catch: java.lang.Throwable -> La0
            if (r3 == 0) goto L51
            r2.add(r3)     // Catch: java.lang.Throwable -> La0
            goto L40
        L51:
            java.lang.String r3 = "_id"
            int r3 = r12.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L69
            long r5 = r12.getLong(r3)     // Catch: java.lang.Throwable -> L69
            java.lang.String r3 = " or _id"
            r1.append(r3)     // Catch: java.lang.Throwable -> L69
            java.lang.String r3 = " = "
            r1.append(r3)     // Catch: java.lang.Throwable -> L69
            r1.append(r5)     // Catch: java.lang.Throwable -> L69
            goto L40
        L69:
            java.lang.String r3 = "[Database] unknown id."
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> La0
            com.tencent.bugly.proguard.C0657x.m467d(r3, r4)     // Catch: java.lang.Throwable -> La0
            goto L40
        L71:
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> La0
            int r3 = r1.length()     // Catch: java.lang.Throwable -> La0
            if (r3 <= 0) goto L99
            r3 = 4
            java.lang.String r1 = r1.substring(r3)     // Catch: java.lang.Throwable -> La0
            java.lang.String r3 = "t_lr"
            int r0 = r0.delete(r3, r1, r9)     // Catch: java.lang.Throwable -> La0
            java.lang.String r1 = "[Database] deleted %s illegal data %d"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> La0
            java.lang.String r5 = "t_lr"
            r3[r4] = r5     // Catch: java.lang.Throwable -> La0
            r4 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> La0
            r3[r4] = r0     // Catch: java.lang.Throwable -> La0
            com.tencent.bugly.proguard.C0657x.m467d(r1, r3)     // Catch: java.lang.Throwable -> La0
        L99:
            if (r12 == 0) goto L9e
            r12.close()     // Catch: java.lang.Throwable -> Lbc
        L9e:
            monitor-exit(r11)
            return r2
        La0:
            r0 = move-exception
            r10 = r0
            r0 = r12
            r12 = r10
        La4:
            boolean r1 = com.tencent.bugly.proguard.C0657x.m462a(r12)     // Catch: java.lang.Throwable -> Lb3
            if (r1 != 0) goto Lad
            r12.printStackTrace()     // Catch: java.lang.Throwable -> Lb3
        Lad:
            if (r0 == 0) goto Lba
            r0.close()     // Catch: java.lang.Throwable -> Lbc
            goto Lba
        Lb3:
            r12 = move-exception
            if (r0 == 0) goto Lb9
            r0.close()     // Catch: java.lang.Throwable -> Lbc
        Lb9:
            throw r12     // Catch: java.lang.Throwable -> Lbc
        Lba:
            monitor-exit(r11)
            return r9
        Lbc:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C0649p.m419a(int):java.util.List");
    }

    /* JADX INFO: renamed from: a */
    public final synchronized void m421a(List<C0651r> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder sb = new StringBuilder();
                    for (C0651r c0651r : list) {
                        sb.append(" or _id");
                        sb.append(" = ");
                        sb.append(c0651r.f706a);
                    }
                    String string = sb.toString();
                    if (string.length() > 0) {
                        string = string.substring(4);
                    }
                    sb.setLength(0);
                    try {
                        C0657x.m466c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", string, null)));
                    } catch (Throwable th) {
                        if (C0657x.m462a(th)) {
                            return;
                        }
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: b */
    public final synchronized void m424b(int i) {
        String str;
        SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
        if (writableDatabase != null) {
            if (i >= 0) {
                try {
                    str = "_tp = " + i;
                } catch (Throwable th) {
                    if (C0657x.m462a(th)) {
                        return;
                    }
                    th.printStackTrace();
                    return;
                }
            } else {
                str = null;
            }
            C0657x.m466c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", str, null)));
        }
    }

    /* JADX INFO: renamed from: c */
    private static ContentValues m413c(C0651r c0651r) {
        if (c0651r == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (c0651r.f706a > 0) {
                contentValues.put("_id", Long.valueOf(c0651r.f706a));
            }
            contentValues.put("_tp", Integer.valueOf(c0651r.f707b));
            contentValues.put("_pc", c0651r.f708c);
            contentValues.put("_th", c0651r.f709d);
            contentValues.put("_tm", Long.valueOf(c0651r.f710e));
            if (c0651r.f712g != null) {
                contentValues.put("_dt", c0651r.f712g);
            }
            return contentValues;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private static C0651r m404a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            C0651r c0651r = new C0651r();
            c0651r.f706a = cursor.getLong(cursor.getColumnIndex("_id"));
            c0651r.f707b = cursor.getInt(cursor.getColumnIndex("_tp"));
            c0651r.f708c = cursor.getString(cursor.getColumnIndex("_pc"));
            c0651r.f709d = cursor.getString(cursor.getColumnIndex("_th"));
            c0651r.f710e = cursor.getLong(cursor.getColumnIndex("_tm"));
            c0651r.f712g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return c0651r;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: c */
    private synchronized List<C0651r> m414c(int i) {
        Cursor cursorQuery;
        try {
            SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
            if (writableDatabase != null) {
                String str = "_id = " + i;
                cursorQuery = writableDatabase.query("t_pf", null, str, null, null, null, null);
                if (cursorQuery == null) {
                    return null;
                }
                try {
                    StringBuilder sb = new StringBuilder();
                    ArrayList arrayList = new ArrayList();
                    while (cursorQuery.moveToNext()) {
                        C0651r c0651rM411b = m411b(cursorQuery);
                        if (c0651rM411b != null) {
                            arrayList.add(c0651rM411b);
                        } else {
                            try {
                                String string = cursorQuery.getString(cursorQuery.getColumnIndex("_tp"));
                                sb.append(" or _tp");
                                sb.append(" = ");
                                sb.append(string);
                            } catch (Throwable unused) {
                                C0657x.m467d("[Database] unknown id.", new Object[0]);
                            }
                        }
                    }
                    if (sb.length() > 0) {
                        sb.append(" and _id");
                        sb.append(" = ");
                        sb.append(i);
                        C0657x.m467d("[Database] deleted %s illegal data %d.", "t_pf", Integer.valueOf(writableDatabase.delete("t_pf", str.substring(4), null)));
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    try {
                        if (!C0657x.m462a(th)) {
                            th.printStackTrace();
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    } finally {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public synchronized boolean m407a(int i, String str, InterfaceC0648o interfaceC0648o) {
        boolean z;
        String str2;
        z = false;
        try {
            SQLiteDatabase writableDatabase = f682b.getWritableDatabase();
            if (writableDatabase != null) {
                if (C0659z.m509a(str)) {
                    str2 = "_id = " + i;
                } else {
                    str2 = "_id = " + i + " and _tp = \"" + str + "\"";
                }
                int iDelete = writableDatabase.delete("t_pf", str2, null);
                C0657x.m466c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(iDelete));
                if (iDelete > 0) {
                    z = true;
                }
            }
        } catch (Throwable th) {
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                if (interfaceC0648o != null) {
                }
            } finally {
                if (interfaceC0648o != null) {
                    Boolean.valueOf(false);
                }
            }
        }
        return z;
    }

    /* JADX INFO: renamed from: d */
    private static ContentValues m415d(C0651r c0651r) {
        if (c0651r != null && !C0659z.m509a(c0651r.f711f)) {
            try {
                ContentValues contentValues = new ContentValues();
                if (c0651r.f706a > 0) {
                    contentValues.put("_id", Long.valueOf(c0651r.f706a));
                }
                contentValues.put("_tp", c0651r.f711f);
                contentValues.put("_tm", Long.valueOf(c0651r.f710e));
                if (c0651r.f712g != null) {
                    contentValues.put("_dt", c0651r.f712g);
                }
                return contentValues;
            } catch (Throwable th) {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: b */
    private static C0651r m411b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            C0651r c0651r = new C0651r();
            c0651r.f706a = cursor.getLong(cursor.getColumnIndex("_id"));
            c0651r.f710e = cursor.getLong(cursor.getColumnIndex("_tm"));
            c0651r.f711f = cursor.getString(cursor.getColumnIndex("_tp"));
            c0651r.f712g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return c0651r;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: com.tencent.bugly.proguard.p$a */
    /* JADX INFO: compiled from: BUGLY */
    class a extends Thread {

        /* JADX INFO: renamed from: a */
        private int f684a;

        /* JADX INFO: renamed from: b */
        private InterfaceC0648o f685b;

        /* JADX INFO: renamed from: c */
        private String f686c;

        /* JADX INFO: renamed from: d */
        private ContentValues f687d;

        /* JADX INFO: renamed from: e */
        private boolean f688e;

        /* JADX INFO: renamed from: f */
        private String[] f689f;

        /* JADX INFO: renamed from: g */
        private String f690g;

        /* JADX INFO: renamed from: h */
        private String[] f691h;

        /* JADX INFO: renamed from: i */
        private String f692i;

        /* JADX INFO: renamed from: j */
        private String f693j;

        /* JADX INFO: renamed from: k */
        private String f694k;

        /* JADX INFO: renamed from: l */
        private String f695l;

        /* JADX INFO: renamed from: m */
        private String f696m;

        /* JADX INFO: renamed from: n */
        private String[] f697n;

        /* JADX INFO: renamed from: o */
        private int f698o;

        /* JADX INFO: renamed from: p */
        private String f699p;

        /* JADX INFO: renamed from: q */
        private byte[] f700q;

        public a(int i, InterfaceC0648o interfaceC0648o) {
            this.f684a = i;
            this.f685b = interfaceC0648o;
        }

        /* JADX INFO: renamed from: a */
        public final void m426a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
            this.f688e = z;
            this.f686c = str;
            this.f689f = strArr;
            this.f690g = str2;
            this.f691h = strArr2;
            this.f692i = str3;
            this.f693j = str4;
            this.f694k = str5;
            this.f695l = str6;
        }

        /* JADX INFO: renamed from: a */
        public final void m425a(int i, String str, byte[] bArr) {
            this.f698o = i;
            this.f699p = str;
            this.f700q = bArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            switch (this.f684a) {
                case 1:
                    C0649p.this.m399a(this.f686c, this.f687d, this.f685b);
                    break;
                case 2:
                    C0649p.this.m397a(this.f686c, this.f696m, this.f697n, this.f685b);
                    break;
                case 3:
                    Cursor cursorM401a = C0649p.this.m401a(this.f688e, this.f686c, this.f689f, this.f690g, this.f691h, this.f692i, this.f693j, this.f694k, this.f695l, this.f685b);
                    if (cursorM401a != null) {
                        cursorM401a.close();
                    }
                    break;
                case 4:
                    C0649p.this.m408a(this.f698o, this.f699p, this.f700q, this.f685b);
                    break;
                case 5:
                    C0649p.this.m405a(this.f698o, this.f685b);
                    break;
                case 6:
                    C0649p.this.m407a(this.f698o, this.f699p, this.f685b);
                    break;
            }
        }
    }
}
