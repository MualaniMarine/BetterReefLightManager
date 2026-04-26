package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.tencent.bugly.C0585b;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.u */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0654u {

    /* JADX INFO: renamed from: a */
    private static C0654u f716a;

    /* JADX INFO: renamed from: c */
    private final Context f718c;

    /* JADX INFO: renamed from: e */
    private long f720e;

    /* JADX INFO: renamed from: f */
    private long f721f;

    /* JADX INFO: renamed from: d */
    private Map<Integer, Long> f719d = new HashMap();

    /* JADX INFO: renamed from: g */
    private LinkedBlockingQueue<Runnable> f722g = new LinkedBlockingQueue<>();

    /* JADX INFO: renamed from: h */
    private LinkedBlockingQueue<Runnable> f723h = new LinkedBlockingQueue<>();

    /* JADX INFO: renamed from: i */
    private final Object f724i = new Object();

    /* JADX INFO: renamed from: j */
    private int f725j = 0;

    /* JADX INFO: renamed from: b */
    private final C0649p f717b = C0649p.m402a();

    /* JADX INFO: renamed from: b */
    static /* synthetic */ int m439b(C0654u c0654u) {
        int i = c0654u.f725j - 1;
        c0654u.f725j = i;
        return i;
    }

    private C0654u(Context context) {
        this.f718c = context;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0654u m435a(Context context) {
        if (f716a == null) {
            f716a = new C0654u(context);
        }
        return f716a;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0654u m434a() {
        return f716a;
    }

    /* JADX INFO: renamed from: a */
    public final void m444a(int i, C0629am c0629am, String str, String str2, InterfaceC0653t interfaceC0653t, long j, boolean z) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            m437a(new RunnableC0655v(this.f718c, i, c0629am.f574g, C0616a.m286a((Object) c0629am), str, str2, interfaceC0653t, true, z), true, true, j);
        } catch (Throwable th2) {
            th = th2;
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a */
    public final void m445a(int i, C0629am c0629am, String str, String str2, InterfaceC0653t interfaceC0653t, boolean z) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            m437a(new RunnableC0655v(this.f718c, i, c0629am.f574g, C0616a.m286a((Object) c0629am), str, str2, interfaceC0653t, 0, 0, false, null), z, false, 0L);
        } catch (Throwable th2) {
            th = th2;
            if (C0657x.m462a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a */
    public final long m442a(boolean z) {
        long jM514b;
        long jM513b = C0659z.m513b();
        int i = z ? 5 : 3;
        List<C0651r> listM419a = this.f717b.m419a(i);
        if (listM419a != null && listM419a.size() > 0) {
            jM514b = 0;
            try {
                C0651r c0651r = listM419a.get(0);
                if (c0651r.f710e >= jM513b) {
                    jM514b = C0659z.m514b(c0651r.f712g);
                    if (i == 3) {
                        this.f720e = jM514b;
                    } else {
                        this.f721f = jM514b;
                    }
                    listM419a.remove(c0651r);
                }
            } catch (Throwable th) {
                C0657x.m462a(th);
            }
            if (listM419a.size() > 0) {
                this.f717b.m421a(listM419a);
            }
        } else {
            jM514b = z ? this.f721f : this.f720e;
        }
        C0657x.m466c("[UploadManager] Local network consume: %d KB", Long.valueOf(jM514b / 1024));
        return jM514b;
    }

    /* JADX INFO: renamed from: a */
    protected final synchronized void m446a(long j, boolean z) {
        int i = z ? 5 : 3;
        C0651r c0651r = new C0651r();
        c0651r.f707b = i;
        c0651r.f710e = C0659z.m513b();
        c0651r.f708c = "";
        c0651r.f709d = "";
        c0651r.f712g = C0659z.m525c(j);
        this.f717b.m424b(i);
        this.f717b.m423a(c0651r);
        if (z) {
            this.f721f = j;
        } else {
            this.f720e = j;
        }
        C0657x.m466c("[UploadManager] Network total consume: %d KB", Long.valueOf(j / 1024));
    }

    /* JADX INFO: renamed from: a */
    public final synchronized void m443a(int i, long j) {
        if (i < 0) {
            C0657x.m468e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i));
            return;
        }
        this.f719d.put(Integer.valueOf(i), Long.valueOf(j));
        C0651r c0651r = new C0651r();
        c0651r.f707b = i;
        c0651r.f710e = j;
        c0651r.f708c = "";
        c0651r.f709d = "";
        c0651r.f712g = new byte[0];
        this.f717b.m424b(i);
        this.f717b.m423a(c0651r);
        C0657x.m466c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i), C0659z.m493a(j));
    }

    /* JADX INFO: renamed from: a */
    public final synchronized long m441a(int i) {
        if (i >= 0) {
            Long l = this.f719d.get(Integer.valueOf(i));
            if (l != null) {
                return l.longValue();
            }
        } else {
            C0657x.m468e("[UploadManager] Unknown upload ID: %d", Integer.valueOf(i));
        }
        return 0L;
    }

    /* JADX INFO: renamed from: b */
    public final boolean m447b(int i) {
        if (C0585b.f145c) {
            C0657x.m466c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - m441a(i);
        C0657x.m466c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(jCurrentTimeMillis / 1000), Integer.valueOf(i));
        if (jCurrentTimeMillis >= 30000) {
            return true;
        }
        C0657x.m461a("[UploadManager] Data only be uploaded once in %d seconds.", 30L);
        return false;
    }

    /* JADX INFO: renamed from: c */
    private void m440c(int i) {
        C0656w c0656wM453a = C0656w.m453a();
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        final LinkedBlockingQueue linkedBlockingQueue2 = new LinkedBlockingQueue();
        synchronized (this.f724i) {
            C0657x.m466c("[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            int size = this.f722g.size();
            final int size2 = this.f723h.size();
            if (size == 0 && size2 == 0) {
                C0657x.m466c("[UploadManager] There is no upload task in queue.", new Object[0]);
                return;
            }
            if (c0656wM453a == null || !c0656wM453a.m458c()) {
                size2 = 0;
            }
            for (int i2 = 0; i2 < size; i2++) {
                Runnable runnablePeek = this.f722g.peek();
                if (runnablePeek == null) {
                    break;
                }
                try {
                    linkedBlockingQueue.put(runnablePeek);
                    this.f722g.poll();
                } catch (Throwable th) {
                    C0657x.m468e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th.getMessage());
                }
            }
            for (int i3 = 0; i3 < size2; i3++) {
                Runnable runnablePeek2 = this.f723h.peek();
                if (runnablePeek2 == null) {
                    break;
                }
                try {
                    linkedBlockingQueue2.put(runnablePeek2);
                    this.f723h.poll();
                } catch (Throwable th2) {
                    C0657x.m468e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th2.getMessage());
                }
            }
            if (size > 0) {
                C0657x.m466c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(size), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            }
            for (int i4 = 0; i4 < size; i4++) {
                final Runnable runnable = (Runnable) linkedBlockingQueue.poll();
                if (runnable == null) {
                    break;
                }
                synchronized (this.f724i) {
                    if (this.f725j >= 2 && c0656wM453a != null) {
                        c0656wM453a.m455a(runnable);
                    } else {
                        C0657x.m461a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
                        if (C0659z.m500a(new Runnable() { // from class: com.tencent.bugly.proguard.u.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                runnable.run();
                                synchronized (C0654u.this.f724i) {
                                    C0654u.m439b(C0654u.this);
                                }
                            }
                        }, "BUGLY_ASYNC_UPLOAD") != null) {
                            synchronized (this.f724i) {
                                this.f725j++;
                            }
                        } else {
                            C0657x.m467d("[UploadManager] Failed to start a thread to execute asynchronous upload task, will try again next time.", new Object[0]);
                            m438a(runnable, true);
                        }
                    }
                }
            }
            if (size2 > 0) {
                C0657x.m466c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(size2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            }
            if (c0656wM453a != null) {
                c0656wM453a.m455a(new Runnable(this) { // from class: com.tencent.bugly.proguard.u.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable2;
                        for (int i5 = 0; i5 < size2 && (runnable2 = (Runnable) linkedBlockingQueue2.poll()) != null; i5++) {
                            runnable2.run();
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private boolean m438a(Runnable runnable, boolean z) {
        if (runnable == null) {
            C0657x.m461a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            C0657x.m466c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.f724i) {
                if (z) {
                    this.f722g.put(runnable);
                } else {
                    this.f723h.put(runnable);
                }
            }
            return true;
        } catch (Throwable th) {
            C0657x.m468e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m437a(Runnable runnable, boolean z, boolean z2, long j) {
        if (runnable == null) {
            C0657x.m467d("[UploadManager] Upload task should not be null", new Object[0]);
        }
        C0657x.m466c("[UploadManager] Add upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (!z2) {
            m438a(runnable, z);
            m440c(0);
            return;
        }
        if (runnable == null) {
            C0657x.m467d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        C0657x.m466c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread threadM500a = C0659z.m500a(runnable, "BUGLY_SYNC_UPLOAD");
        if (threadM500a == null) {
            C0657x.m468e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            m438a(runnable, true);
            return;
        }
        try {
            threadM500a.join(j);
        } catch (Throwable th) {
            C0657x.m468e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            m438a(runnable, true);
            m440c(0);
        }
    }
}
