package com.tencent.bugly.crashreport.crash.p002h5;

import android.webkit.JavascriptInterface;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.inner.InnerApi;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class H5JavaScriptInterface {

    /* JADX INFO: renamed from: a */
    private static HashSet<Integer> f467a = new HashSet<>();

    /* JADX INFO: renamed from: b */
    private String f468b = null;

    /* JADX INFO: renamed from: c */
    private Thread f469c = null;

    /* JADX INFO: renamed from: d */
    private String f470d = null;

    /* JADX INFO: renamed from: e */
    private Map<String, String> f471e = null;

    private H5JavaScriptInterface() {
    }

    public static H5JavaScriptInterface getInstance(CrashReport.WebViewInterface webViewInterface) {
        String string = null;
        if (webViewInterface == null || f467a.contains(Integer.valueOf(webViewInterface.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        f467a.add(Integer.valueOf(webViewInterface.hashCode()));
        Thread threadCurrentThread = Thread.currentThread();
        h5JavaScriptInterface.f469c = threadCurrentThread;
        if (threadCurrentThread != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (int i = 2; i < threadCurrentThread.getStackTrace().length; i++) {
                StackTraceElement stackTraceElement = threadCurrentThread.getStackTrace()[i];
                if (!stackTraceElement.toString().contains("crashreport")) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
            string = sb.toString();
        }
        h5JavaScriptInterface.f470d = string;
        HashMap map = new HashMap();
        StringBuilder sb2 = new StringBuilder();
        sb2.append((Object) webViewInterface.getContentDescription());
        map.put("[WebView] ContentDescription", sb2.toString());
        h5JavaScriptInterface.f471e = map;
        return h5JavaScriptInterface;
    }

    /* JADX INFO: renamed from: a */
    private static C0610a m249a(String str) {
        String string;
        if (str != null && str.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                C0610a c0610a = new C0610a();
                c0610a.f472a = jSONObject.getString("projectRoot");
                if (c0610a.f472a == null) {
                    return null;
                }
                c0610a.f473b = jSONObject.getString("context");
                if (c0610a.f473b == null) {
                    return null;
                }
                c0610a.f474c = jSONObject.getString("url");
                if (c0610a.f474c == null) {
                    return null;
                }
                c0610a.f475d = jSONObject.getString("userAgent");
                if (c0610a.f475d == null) {
                    return null;
                }
                c0610a.f476e = jSONObject.getString("language");
                if (c0610a.f476e == null) {
                    return null;
                }
                c0610a.f477f = jSONObject.getString("name");
                if (c0610a.f477f == null || c0610a.f477f.equals("null") || (string = jSONObject.getString("stacktrace")) == null) {
                    return null;
                }
                int iIndexOf = string.indexOf("\n");
                if (iIndexOf < 0) {
                    C0657x.m467d("H5 crash stack's format is wrong!", new Object[0]);
                    return null;
                }
                c0610a.f479h = string.substring(iIndexOf + 1);
                c0610a.f478g = string.substring(0, iIndexOf);
                int iIndexOf2 = c0610a.f478g.indexOf(":");
                if (iIndexOf2 > 0) {
                    c0610a.f478g = c0610a.f478g.substring(iIndexOf2 + 1);
                }
                c0610a.f480i = jSONObject.getString("file");
                if (c0610a.f477f == null) {
                    return null;
                }
                c0610a.f481j = jSONObject.getLong("lineNumber");
                if (c0610a.f481j < 0) {
                    return null;
                }
                c0610a.f482k = jSONObject.getLong("columnNumber");
                if (c0610a.f482k < 0) {
                    return null;
                }
                C0657x.m461a("H5 crash information is following: ", new Object[0]);
                C0657x.m461a("[projectRoot]: " + c0610a.f472a, new Object[0]);
                C0657x.m461a("[context]: " + c0610a.f473b, new Object[0]);
                C0657x.m461a("[url]: " + c0610a.f474c, new Object[0]);
                C0657x.m461a("[userAgent]: " + c0610a.f475d, new Object[0]);
                C0657x.m461a("[language]: " + c0610a.f476e, new Object[0]);
                C0657x.m461a("[name]: " + c0610a.f477f, new Object[0]);
                C0657x.m461a("[message]: " + c0610a.f478g, new Object[0]);
                C0657x.m461a("[stacktrace]: \n" + c0610a.f479h, new Object[0]);
                C0657x.m461a("[file]: " + c0610a.f480i, new Object[0]);
                C0657x.m461a("[lineNumber]: " + c0610a.f481j, new Object[0]);
                C0657x.m461a("[columnNumber]: " + c0610a.f482k, new Object[0]);
                return c0610a;
            } catch (Throwable th) {
                if (!C0657x.m462a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    @JavascriptInterface
    public void printLog(String str) {
        C0657x.m467d("Log from js: %s", str);
    }

    @JavascriptInterface
    public void reportJSException(String str) {
        if (str == null) {
            C0657x.m467d("Payload from JS is null.", new Object[0]);
            return;
        }
        String strM499a = C0659z.m499a(str.getBytes());
        String str2 = this.f468b;
        if (str2 != null && str2.equals(strM499a)) {
            C0657x.m467d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
            return;
        }
        this.f468b = strM499a;
        C0657x.m467d("Handling JS exception ...", new Object[0]);
        C0610a c0610aM249a = m249a(str);
        if (c0610aM249a == null) {
            C0657x.m467d("Failed to parse payload.", new Object[0]);
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        if (c0610aM249a.f472a != null) {
            linkedHashMap2.put("[JS] projectRoot", c0610aM249a.f472a);
        }
        if (c0610aM249a.f473b != null) {
            linkedHashMap2.put("[JS] context", c0610aM249a.f473b);
        }
        if (c0610aM249a.f474c != null) {
            linkedHashMap2.put("[JS] url", c0610aM249a.f474c);
        }
        if (c0610aM249a.f475d != null) {
            linkedHashMap2.put("[JS] userAgent", c0610aM249a.f475d);
        }
        if (c0610aM249a.f480i != null) {
            linkedHashMap2.put("[JS] file", c0610aM249a.f480i);
        }
        if (c0610aM249a.f481j != 0) {
            linkedHashMap2.put("[JS] lineNumber", Long.toString(c0610aM249a.f481j));
        }
        linkedHashMap.putAll(linkedHashMap2);
        linkedHashMap.putAll(this.f471e);
        linkedHashMap.put("Java Stack", this.f470d);
        Thread thread = this.f469c;
        if (c0610aM249a != null) {
            InnerApi.postH5CrashAsync(thread, c0610aM249a.f477f, c0610aM249a.f478g, c0610aM249a.f479h, linkedHashMap);
        }
    }
}
