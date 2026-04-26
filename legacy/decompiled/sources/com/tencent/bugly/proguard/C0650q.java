package com.tencent.bugly.proguard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.bugly.AbstractC0584a;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.C0594b;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.q */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0650q extends SQLiteOpenHelper {

    /* JADX INFO: renamed from: a */
    public static String f702a = "bugly_db";

    /* JADX INFO: renamed from: b */
    private static int f703b = 15;

    /* JADX INFO: renamed from: c */
    private Context f704c;

    /* JADX INFO: renamed from: d */
    private List<AbstractC0584a> f705d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0650q(Context context, List<AbstractC0584a> list) {
        super(context, f702a + "_", (SQLiteDatabase.CursorFactory) null, f703b);
        C0593a.m64a(context).getClass();
        this.f704c = context;
        this.f705d = list;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS t_ui");
            sb.append(" ( _id");
            sb.append(" INTEGER PRIMARY KEY");
            sb.append(" , _tm");
            sb.append(" int");
            sb.append(" , _ut");
            sb.append(" int");
            sb.append(" , _tp");
            sb.append(" int");
            sb.append(" , _dt");
            sb.append(" blob");
            sb.append(" , _pc");
            sb.append(" text");
            sb.append(" ) ");
            C0657x.m466c(sb.toString(), new Object[0]);
            sQLiteDatabase.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS t_lr");
            sb.append(" ( _id");
            sb.append(" INTEGER PRIMARY KEY");
            sb.append(" , _tp");
            sb.append(" int");
            sb.append(" , _tm");
            sb.append(" int");
            sb.append(" , _pc");
            sb.append(" text");
            sb.append(" , _th");
            sb.append(" text");
            sb.append(" , _dt");
            sb.append(" blob");
            sb.append(" ) ");
            C0657x.m466c(sb.toString(), new Object[0]);
            sQLiteDatabase.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS t_pf");
            sb.append(" ( _id");
            sb.append(" integer");
            sb.append(" , _tp");
            sb.append(" text");
            sb.append(" , _tm");
            sb.append(" int");
            sb.append(" , _dt");
            sb.append(" blob");
            sb.append(",primary key(_id");
            sb.append(",_tp");
            sb.append(" )) ");
            C0657x.m466c(sb.toString(), new Object[0]);
            sQLiteDatabase.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS t_cr");
            sb.append(" ( _id");
            sb.append(" INTEGER PRIMARY KEY");
            sb.append(" , _tm");
            sb.append(" int");
            sb.append(" , _s1");
            sb.append(" text");
            sb.append(" , _up");
            sb.append(" int");
            sb.append(" , _me");
            sb.append(" int");
            sb.append(" , _uc");
            sb.append(" int");
            sb.append(" , _dt");
            sb.append(" blob");
            sb.append(" ) ");
            C0657x.m466c(sb.toString(), new Object[0]);
            sQLiteDatabase.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS dl_1002");
            sb.append(" (_id");
            sb.append(" integer primary key autoincrement, _dUrl");
            sb.append(" varchar(100), _sFile");
            sb.append(" varchar(100), _sLen");
            sb.append(" INTEGER, _tLen");
            sb.append(" INTEGER, _MD5");
            sb.append(" varchar(100), _DLTIME");
            sb.append(" INTEGER)");
            C0657x.m466c(sb.toString(), new Object[0]);
            sQLiteDatabase.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append("CREATE TABLE IF NOT EXISTS ge_1002");
            sb.append(" (_id");
            sb.append(" integer primary key autoincrement, _time");
            sb.append(" INTEGER, _datas");
            sb.append(" blob)");
            C0657x.m466c(sb.toString(), new Object[0]);
            sQLiteDatabase.execSQL(sb.toString(), new String[0]);
            sb.setLength(0);
            sb.append(" CREATE TABLE IF NOT EXISTS st_1002");
            sb.append(" ( _id");
            sb.append(" integer");
            sb.append(" , _tp");
            sb.append(" text");
            sb.append(" , _tm");
            sb.append(" int");
            sb.append(" , _dt");
            sb.append(" blob");
            sb.append(",primary key(_id");
            sb.append(",_tp");
            sb.append(" )) ");
            C0657x.m466c(sb.toString(), new Object[0]);
            sQLiteDatabase.execSQL(sb.toString(), new String[0]);
        } catch (Throwable th) {
            if (!C0657x.m465b(th)) {
                th.printStackTrace();
            }
        }
        if (this.f705d == null) {
            return;
        }
        Iterator<AbstractC0584a> it = this.f705d.iterator();
        while (it.hasNext()) {
            try {
                it.next().onDbCreate(sQLiteDatabase);
            } catch (Throwable th2) {
                if (!C0657x.m465b(th2)) {
                    th2.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private synchronized boolean m427a(SQLiteDatabase sQLiteDatabase) {
        try {
            String[] strArr = {"t_lr", "t_ui", "t_pf"};
            for (int i = 0; i < 3; i++) {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + strArr[i], new String[0]);
            }
        } catch (Throwable th) {
            if (!C0657x.m465b(th)) {
                th.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        C0657x.m467d("[Database] Upgrade %d to %d , drop tables!", Integer.valueOf(i), Integer.valueOf(i2));
        if (this.f705d != null) {
            Iterator<AbstractC0584a> it = this.f705d.iterator();
            while (it.hasNext()) {
                try {
                    it.next().onDbUpgrade(sQLiteDatabase, i, i2);
                } catch (Throwable th) {
                    if (!C0657x.m465b(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
        if (m427a(sQLiteDatabase)) {
            onCreate(sQLiteDatabase);
            return;
        }
        C0657x.m467d("[Database] Failed to drop, delete db.", new Object[0]);
        File databasePath = this.f704c.getDatabasePath(f702a);
        if (databasePath != null && databasePath.canWrite()) {
            databasePath.delete();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (C0594b.m116c() >= 11) {
            C0657x.m467d("[Database] Downgrade %d to %d drop tables.", Integer.valueOf(i), Integer.valueOf(i2));
            if (this.f705d != null) {
                Iterator<AbstractC0584a> it = this.f705d.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().onDbDowngrade(sQLiteDatabase, i, i2);
                    } catch (Throwable th) {
                        if (!C0657x.m465b(th)) {
                            th.printStackTrace();
                        }
                    }
                }
            }
            if (m427a(sQLiteDatabase)) {
                onCreate(sQLiteDatabase);
                return;
            }
            C0657x.m467d("[Database] Failed to drop, delete db.", new Object[0]);
            File databasePath = this.f704c.getDatabasePath(f702a);
            if (databasePath != null && databasePath.canWrite()) {
                databasePath.delete();
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase readableDatabase;
        readableDatabase = null;
        int i = 0;
        while (readableDatabase == null && i < 5) {
            i++;
            try {
                readableDatabase = super.getReadableDatabase();
            } catch (Throwable unused) {
                C0657x.m467d("[Database] Try to get db(count: %d).", Integer.valueOf(i));
                if (i == 5) {
                    C0657x.m468e("[Database] Failed to get db.", new Object[0]);
                }
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return readableDatabase;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase writableDatabase;
        writableDatabase = null;
        int i = 0;
        while (writableDatabase == null && i < 5) {
            i++;
            try {
                writableDatabase = super.getWritableDatabase();
            } catch (Throwable unused) {
                C0657x.m467d("[Database] Try to get db(count: %d).", Integer.valueOf(i));
                if (i == 5) {
                    C0657x.m468e("[Database] Failed to get db.", new Object[0]);
                }
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (writableDatabase == null) {
            C0657x.m467d("[Database] db error delay error record 1min.", new Object[0]);
        }
        return writableDatabase;
    }
}
