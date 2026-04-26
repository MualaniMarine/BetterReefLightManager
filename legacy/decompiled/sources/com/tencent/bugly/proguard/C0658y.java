package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.tencent.bugly.crashreport.common.info.C0593a;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.y */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0658y {

    /* JADX INFO: renamed from: a */
    public static boolean f755a = true;

    /* JADX INFO: renamed from: b */
    private static boolean f756b = true;

    /* JADX INFO: renamed from: c */
    private static SimpleDateFormat f757c = null;

    /* JADX INFO: renamed from: d */
    private static int f758d = 30720;

    /* JADX INFO: renamed from: e */
    private static StringBuilder f759e = null;

    /* JADX INFO: renamed from: f */
    private static StringBuilder f760f = null;

    /* JADX INFO: renamed from: g */
    private static boolean f761g = false;

    /* JADX INFO: renamed from: h */
    private static a f762h = null;

    /* JADX INFO: renamed from: i */
    private static String f763i = null;

    /* JADX INFO: renamed from: j */
    private static String f764j = null;

    /* JADX INFO: renamed from: k */
    private static Context f765k = null;

    /* JADX INFO: renamed from: l */
    private static String f766l = null;

    /* JADX INFO: renamed from: m */
    private static boolean f767m = false;

    /* JADX INFO: renamed from: n */
    private static boolean f768n = false;

    /* JADX INFO: renamed from: o */
    private static ExecutorService f769o;

    /* JADX INFO: renamed from: p */
    private static int f770p;

    /* JADX INFO: renamed from: q */
    private static final Object f771q = new Object();

    static {
        try {
            f757c = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
            C0657x.m465b(th.getCause());
        }
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m471a(Context context) {
        if (f767m || context == null || !f755a) {
            return;
        }
        try {
            f769o = Executors.newSingleThreadExecutor();
            f760f = new StringBuilder(0);
            f759e = new StringBuilder(0);
            f765k = context;
            C0593a c0593aM64a = C0593a.m64a(context);
            f763i = c0593aM64a.f246d;
            c0593aM64a.getClass();
            f764j = "";
            f766l = f765k.getFilesDir().getPath() + "/buglylog_" + f763i + "_" + f764j + ".txt";
            f770p = Process.myPid();
        } catch (Throwable unused) {
        }
        f767m = true;
    }

    /* JADX INFO: renamed from: a */
    public static void m470a(int i) {
        synchronized (f771q) {
            f758d = i;
            if (i < 0) {
                f758d = 0;
            } else if (i > 30720) {
                f758d = 30720;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m473a(String str, String str2, Throwable th) {
        if (th == null) {
            return;
        }
        String message = th.getMessage();
        if (message == null) {
            message = "";
        }
        m472a(str, str2, message + '\n' + C0659z.m515b(th));
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m472a(final String str, final String str2, final String str3) {
        if (f767m && f755a) {
            try {
                f769o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.y.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        C0658y.m477c(str, str2, str3);
                    }
                });
            } catch (Exception e) {
                C0657x.m465b(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public static synchronized void m477c(String str, String str2, String str3) {
        if (f756b) {
            m478d(str, str2, str3);
        } else {
            m479e(str, str2, str3);
        }
    }

    /* JADX INFO: renamed from: d */
    private static synchronized void m478d(String str, String str2, String str3) {
        String strM469a = m469a(str, str2, str3, Process.myTid());
        synchronized (f771q) {
            try {
                f760f.append(strM469a);
                if (f760f.length() >= f758d) {
                    f760f = f760f.delete(0, f760f.indexOf("\u0001\r\n") + 1);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: renamed from: e */
    private static synchronized void m479e(String str, String str2, String str3) {
        String strM469a = m469a(str, str2, str3, Process.myTid());
        synchronized (f771q) {
            try {
                f760f.append(strM469a);
                if (f760f.length() <= f758d) {
                    return;
                }
                if (f761g) {
                    return;
                }
                f761g = true;
                if (f762h == null) {
                    f762h = new a(f766l);
                } else if (f762h.f776b == null || f762h.f776b.length() + ((long) f760f.length()) > f762h.f779e) {
                    f762h.m481a();
                }
                if (f762h.m485a(f760f.toString())) {
                    f760f.setLength(0);
                    f761g = false;
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static String m469a(String str, String str2, String str3, long j) {
        String string;
        f759e.setLength(0);
        if (str3.length() > 30720) {
            str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = f757c;
        if (simpleDateFormat != null) {
            string = simpleDateFormat.format(date);
        } else {
            string = date.toString();
        }
        StringBuilder sb = f759e;
        sb.append(string);
        sb.append(" ");
        sb.append(f770p);
        sb.append(" ");
        sb.append(j);
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str3);
        sb.append("\u0001\r\n");
        return f759e.toString();
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m474a() {
        if (f756b) {
            if (f755a) {
                return C0659z.m511a((File) null, f760f.toString(), "BuglyLog.txt");
            }
            return null;
        }
        return m476b();
    }

    /* JADX INFO: renamed from: b */
    private static byte[] m476b() {
        if (!f755a) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        synchronized (f771q) {
            if (f762h != null && f762h.f775a && f762h.f776b != null && f762h.f776b.length() > 0) {
                sb.append(C0659z.m496a(f762h.f776b, 30720, true));
            }
            if (f760f != null && f760f.length() > 0) {
                sb.append(f760f.toString());
            }
        }
        return C0659z.m511a((File) null, sb.toString(), "BuglyLog.txt");
    }

    /* JADX INFO: renamed from: com.tencent.bugly.proguard.y$a */
    /* JADX INFO: compiled from: BUGLY */
    public static class a {

        /* JADX INFO: renamed from: a */
        private boolean f775a;

        /* JADX INFO: renamed from: b */
        private File f776b;

        /* JADX INFO: renamed from: c */
        private String f777c;

        /* JADX INFO: renamed from: d */
        private long f778d;

        /* JADX INFO: renamed from: e */
        private long f779e = 30720;

        public a(String str) {
            if (str == null || str.equals("")) {
                return;
            }
            this.f777c = str;
            this.f775a = m481a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a */
        public boolean m481a() {
            try {
                File file = new File(this.f777c);
                this.f776b = file;
                if (file.exists() && !this.f776b.delete()) {
                    this.f775a = false;
                    return false;
                }
                if (this.f776b.createNewFile()) {
                    return true;
                }
                this.f775a = false;
                return false;
            } catch (Throwable th) {
                C0657x.m462a(th);
                this.f775a = false;
                return false;
            }
        }

        /* JADX INFO: renamed from: a */
        public final boolean m485a(String str) {
            FileOutputStream fileOutputStream;
            if (!this.f775a) {
                return false;
            }
            FileOutputStream fileOutputStream2 = null;
            try {
                fileOutputStream = new FileOutputStream(this.f776b, true);
            } catch (Throwable th) {
                th = th;
            }
            try {
                byte[] bytes = str.getBytes(ByteUtil.ESPTOUCH_ENCODING_CHARSET);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();
                this.f778d += (long) bytes.length;
                this.f775a = true;
                try {
                    fileOutputStream.close();
                } catch (IOException unused) {
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                try {
                    C0657x.m462a(th);
                    this.f775a = false;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return false;
                } catch (Throwable th3) {
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th3;
                }
            }
        }
    }
}
