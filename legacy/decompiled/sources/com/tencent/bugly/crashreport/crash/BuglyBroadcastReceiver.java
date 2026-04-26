package com.tencent.bugly.crashreport.crash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tencent.bugly.crashreport.biz.C0591b;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.common.info.C0594b;
import com.tencent.bugly.crashreport.common.strategy.C0596a;
import com.tencent.bugly.proguard.C0654u;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class BuglyBroadcastReceiver extends BroadcastReceiver {

    /* JADX INFO: renamed from: d */
    private static BuglyBroadcastReceiver f303d;

    /* JADX INFO: renamed from: b */
    private Context f305b;

    /* JADX INFO: renamed from: c */
    private String f306c;

    /* JADX INFO: renamed from: e */
    private boolean f307e = true;

    /* JADX INFO: renamed from: a */
    private IntentFilter f304a = new IntentFilter();

    public static synchronized BuglyBroadcastReceiver getInstance() {
        if (f303d == null) {
            f303d = new BuglyBroadcastReceiver();
        }
        return f303d;
    }

    public synchronized void addFilter(String str) {
        if (!this.f304a.hasAction(str)) {
            this.f304a.addAction(str);
        }
        C0657x.m466c("add action %s", str);
    }

    public synchronized void register(Context context) {
        this.f305b = context;
        C0659z.m508a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    C0657x.m460a(BuglyBroadcastReceiver.f303d.getClass(), "Register broadcast receiver of Bugly.", new Object[0]);
                    synchronized (this) {
                        BuglyBroadcastReceiver.this.f305b.registerReceiver(BuglyBroadcastReceiver.f303d, BuglyBroadcastReceiver.this.f304a, "com.tencent.bugly.BuglyBroadcastReceiver.permission", null);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    public synchronized void unregister(Context context) {
        try {
            C0657x.m460a(getClass(), "Unregister broadcast receiver of Bugly.", new Object[0]);
            context.unregisterReceiver(this);
            this.f305b = context;
        } catch (Throwable th) {
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        try {
            m152a(context, intent);
        } catch (Throwable th) {
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a */
    private synchronized boolean m152a(Context context, Intent intent) {
        if (context != null && intent != null) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                if (this.f307e) {
                    this.f307e = false;
                    return true;
                }
                String strM115b = C0594b.m115b(this.f305b);
                C0657x.m466c("is Connect BC " + strM115b, new Object[0]);
                C0657x.m461a("network %s changed to %s", this.f306c, strM115b);
                if (strM115b == null) {
                    this.f306c = null;
                    return true;
                }
                String str = this.f306c;
                this.f306c = strM115b;
                long jCurrentTimeMillis = System.currentTimeMillis();
                C0596a c0596aM139a = C0596a.m139a();
                C0654u c0654uM434a = C0654u.m434a();
                C0593a c0593aM64a = C0593a.m64a(context);
                if (c0596aM139a != null && c0654uM434a != null && c0593aM64a != null) {
                    if (!strM115b.equals(str) && jCurrentTimeMillis - c0654uM434a.m441a(C0607c.f411a) > 30000) {
                        C0657x.m461a("try to upload crash on network changed.", new Object[0]);
                        C0607c c0607cM204a = C0607c.m204a();
                        if (c0607cM204a != null) {
                            c0607cM204a.m209a(0L);
                        }
                        C0657x.m461a("try to upload userinfo on network changed.", new Object[0]);
                        C0591b.f183a.m32b();
                    }
                    return true;
                }
                C0657x.m467d("not inited BC not work", new Object[0]);
                return true;
            }
        }
        return false;
    }
}
