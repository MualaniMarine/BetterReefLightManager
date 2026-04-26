package com.tencent.bugly.proguard;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: renamed from: com.tencent.bugly.proguard.s */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0652s {

    /* JADX INFO: renamed from: b */
    private static C0652s f713b;

    /* JADX INFO: renamed from: a */
    public Map<String, String> f714a = null;

    /* JADX INFO: renamed from: c */
    private Context f715c;

    private C0652s(Context context) {
        this.f715c = context;
    }

    /* JADX INFO: renamed from: a */
    public static C0652s m428a(Context context) {
        if (f713b == null) {
            f713b = new C0652s(context);
        }
        return f713b;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:131|23|(6:25|(1:27)(1:28)|29|129|30|36)(10:37|(1:47)(1:46)|(3:127|49|(5:138|51|136|52|53)(9:61|62|63|119|64|65|125|66|67))(1:78)|134|79|(1:81)|82|121|83|105)|95|96|(1:98)|117|99|105) */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0180, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0181, code lost:
    
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0186, code lost:
    
        if (com.tencent.bugly.proguard.C0657x.m462a(r4) == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0188, code lost:
    
        r4.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0188 A[PHI: r4 r6 r7 r10 r13 r14 r15
  0x0188: PHI (r4v5 java.lang.Throwable) = (r4v4 java.lang.Throwable), (r4v18 java.lang.Throwable) binds: [B:103:0x0186, B:87:0x0168] A[DONT_GENERATE, DONT_INLINE]
  0x0188: PHI (r6v7 int) = (r6v6 int), (r6v12 int) binds: [B:103:0x0186, B:87:0x0168] A[DONT_GENERATE, DONT_INLINE]
  0x0188: PHI (r7v4 char) = (r7v3 char), (r7v10 char) binds: [B:103:0x0186, B:87:0x0168] A[DONT_GENERATE, DONT_INLINE]
  0x0188: PHI (r10v9 java.lang.String) = (r10v8 java.lang.String), (r10v14 java.lang.String) binds: [B:103:0x0186, B:87:0x0168] A[DONT_GENERATE, DONT_INLINE]
  0x0188: PHI (r13v6 int) = (r13v5 int), (r13v9 int) binds: [B:103:0x0186, B:87:0x0168] A[DONT_GENERATE, DONT_INLINE]
  0x0188: PHI (r14v6 int) = (r14v5 int), (r14v13 int) binds: [B:103:0x0186, B:87:0x0168] A[DONT_GENERATE, DONT_INLINE]
  0x0188: PHI (r15v3 int) = (r15v2 int), (r15v9 int) binds: [B:103:0x0186, B:87:0x0168] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0179 A[Catch: all -> 0x016d, TRY_LEAVE, TryCatch #8 {all -> 0x016d, blocks: (B:23:0x009b, B:25:0x00a3, B:29:0x00b4, B:28:0x00b2, B:49:0x00dd, B:51:0x00e5, B:64:0x0115, B:66:0x011f, B:79:0x013a, B:82:0x015b, B:96:0x0173, B:98:0x0179), top: B:131:0x009b }] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] m433a(java.lang.String r21, byte[] r22, com.tencent.bugly.proguard.RunnableC0655v r23, java.util.Map<java.lang.String, java.lang.String> r24) {
        /*
            Method dump skipped, instruction units count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C0652s.m433a(java.lang.String, byte[], com.tencent.bugly.proguard.v, java.util.Map):byte[]");
    }

    /* JADX INFO: renamed from: a */
    private static Map<String, String> m431a(HttpURLConnection httpURLConnection) {
        HashMap map = new HashMap();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List<String> list = headerFields.get(str);
            if (list.size() > 0) {
                map.put(str, list.get(0));
            }
        }
        return map;
    }

    /* JADX INFO: renamed from: b */
    private static byte[] m432b(HttpURLConnection httpURLConnection) {
        BufferedInputStream bufferedInputStream;
        if (httpURLConnection == null) {
            return null;
        }
        try {
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        } catch (Throwable th) {
            th = th;
            bufferedInputStream = null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i = bufferedInputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                bufferedInputStream.close();
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
            return byteArray;
        } catch (Throwable th3) {
            th = th3;
            try {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
                return null;
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th4) {
                        th4.printStackTrace();
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private HttpURLConnection m430a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            C0657x.m468e("destUrl is null.", new Object[0]);
            return null;
        }
        TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: com.tencent.bugly.proguard.s.1
            @Override // javax.net.ssl.X509TrustManager
            public final X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override // javax.net.ssl.X509TrustManager
            public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str3) throws CertificateException {
                C0657x.m466c("checkClientTrusted", new Object[0]);
            }

            @Override // javax.net.ssl.X509TrustManager
            public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str3) throws CertificateException {
                C0657x.m466c("checkServerTrusted", new Object[0]);
            }
        }};
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpURLConnection httpURLConnectionM429a = m429a(str2, str);
        if (httpURLConnectionM429a == null) {
            C0657x.m468e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            httpURLConnectionM429a.setRequestProperty("wup_version", "3.0");
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpURLConnectionM429a.setRequestProperty(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
                }
            }
            httpURLConnectionM429a.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            httpURLConnectionM429a.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = httpURLConnectionM429a.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return httpURLConnectionM429a;
        } catch (Throwable th) {
            if (!C0657x.m462a(th)) {
                th.printStackTrace();
            }
            C0657x.m468e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private static HttpURLConnection m429a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            if (C0616a.m288b() != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(C0616a.m288b());
            } else if (str != null && str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (C0657x.m462a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
