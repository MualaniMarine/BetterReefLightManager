package com.tencent.bugly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.C0657x;

/* JADX INFO: renamed from: com.tencent.bugly.a */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0584a {

    /* JADX INFO: renamed from: id */
    public int f142id;
    public String moduleName;
    public String version;
    public String versionKey;

    public abstract String[] getTables();

    public abstract void init(Context context, boolean z, BuglyStrategy buglyStrategy);

    public void onDbCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onServerStrategyChanged(StrategyBean strategyBean) {
    }

    public void onDbUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            if (getTables() == null) {
                return;
            }
            for (String str : getTables()) {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str);
            }
            onDbCreate(sQLiteDatabase);
        } catch (Throwable th) {
            if (C0657x.m465b(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public void onDbDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            if (getTables() == null) {
                return;
            }
            for (String str : getTables()) {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str);
            }
            onDbCreate(sQLiteDatabase);
        } catch (Throwable th) {
            if (C0657x.m465b(th)) {
                return;
            }
            th.printStackTrace();
        }
    }
}
