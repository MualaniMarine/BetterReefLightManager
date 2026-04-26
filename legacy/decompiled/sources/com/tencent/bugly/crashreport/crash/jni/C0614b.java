package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.tencent.bugly.crashreport.common.info.C0593a;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.C0657x;
import com.tencent.bugly.proguard.C0659z;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.tencent.bugly.crashreport.crash.jni.b */
/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public final class C0614b {

    /* JADX INFO: renamed from: a */
    private static List<File> f506a = new ArrayList();

    /* JADX INFO: renamed from: d */
    private static Map<String, Integer> m276d(String str) {
        if (str == null) {
            return null;
        }
        try {
            HashMap map = new HashMap();
            for (String str2 : str.split(",")) {
                String[] strArrSplit = str2.split(":");
                if (strArrSplit.length != 2) {
                    C0657x.m468e("error format at %s", str2);
                    return null;
                }
                map.put(strArrSplit[0], Integer.valueOf(Integer.parseInt(strArrSplit[1])));
            }
            return map;
        } catch (Exception e) {
            C0657x.m468e("error format intStateStr %s", str);
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    protected static String m268a(String str) {
        if (str == null) {
            return "";
        }
        String[] strArrSplit = str.split("\n");
        if (strArrSplit == null || strArrSplit.length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArrSplit) {
            if (!str2.contains("java.lang.Thread.getStackTrace(")) {
                sb.append(str2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: a */
    private static CrashDetailBean m266a(Context context, Map<String, String> map, NativeExceptionHandler nativeExceptionHandler) {
        String str;
        String str2;
        HashMap map2;
        if (map == null) {
            return null;
        }
        if (C0593a.m64a(context) == null) {
            C0657x.m468e("abnormal com info not created", new Object[0]);
            return null;
        }
        String str3 = map.get("intStateStr");
        if (str3 == null || str3.trim().length() <= 0) {
            C0657x.m468e("no intStateStr", new Object[0]);
            return null;
        }
        Map<String, Integer> mapM276d = m276d(str3);
        if (mapM276d == null) {
            C0657x.m468e("parse intSateMap fail", Integer.valueOf(map.size()));
            return null;
        }
        try {
            mapM276d.get("sino").intValue();
            mapM276d.get("sud").intValue();
            String str4 = map.get("soVersion");
            if (TextUtils.isEmpty(str4)) {
                C0657x.m468e("error format at version", new Object[0]);
                return null;
            }
            String str5 = map.get("errorAddr");
            String str6 = EnvironmentCompat.MEDIA_UNKNOWN;
            String str7 = str5 == null ? EnvironmentCompat.MEDIA_UNKNOWN : str5;
            String str8 = map.get("codeMsg");
            if (str8 == null) {
                str8 = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            String str9 = map.get("tombPath");
            String str10 = str9 == null ? EnvironmentCompat.MEDIA_UNKNOWN : str9;
            String str11 = map.get("signalName");
            if (str11 == null) {
                str11 = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            map.get("errnoMsg");
            String str12 = map.get("stack");
            if (str12 == null) {
                str12 = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            String str13 = map.get("jstack");
            if (str13 != null) {
                str12 = str12 + "java:\n" + str13;
            }
            Integer num = mapM276d.get("sico");
            if (num == null || num.intValue() <= 0) {
                str = str8;
                str2 = str11;
            } else {
                str2 = str11 + "(" + str8 + ")";
                str = "KERNEL";
            }
            String str14 = map.get("nativeLog");
            byte[] bArrM511a = (str14 == null || str14.isEmpty()) ? null : C0659z.m511a((File) null, str14, "BuglyNativeLog.txt");
            String str15 = map.get("sendingProcess");
            if (str15 == null) {
                str15 = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            Integer num2 = mapM276d.get("spd");
            if (num2 != null) {
                str15 = str15 + "(" + num2 + ")";
            }
            String str16 = str15;
            String str17 = map.get("threadName");
            if (str17 == null) {
                str17 = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            Integer num3 = mapM276d.get("et");
            if (num3 != null) {
                str17 = str17 + "(" + num3 + ")";
            }
            String str18 = str17;
            String str19 = map.get("processName");
            if (str19 != null) {
                str6 = str19;
            }
            Integer num4 = mapM276d.get("ep");
            if (num4 != null) {
                str6 = str6 + "(" + num4 + ")";
            }
            String str20 = str6;
            String str21 = map.get("key-value");
            if (str21 != null) {
                HashMap map3 = new HashMap();
                String[] strArrSplit = str21.split("\n");
                int length = strArrSplit.length;
                int i = 0;
                while (i < length) {
                    String[] strArrSplit2 = strArrSplit[i].split("=");
                    String[] strArr = strArrSplit;
                    if (strArrSplit2.length == 2) {
                        map3.put(strArrSplit2[0], strArrSplit2[1]);
                    }
                    i++;
                    strArrSplit = strArr;
                }
                map2 = map3;
            } else {
                map2 = null;
            }
            CrashDetailBean crashDetailBeanPackageCrashDatas = nativeExceptionHandler.packageCrashDatas(str20, str18, (((long) mapM276d.get("etms").intValue()) / 1000) + (((long) mapM276d.get("ets").intValue()) * 1000), str2, str7, m268a(str12), str, str16, str10, map.get("sysLogPath"), map.get("jniLogPath"), str4, bArrM511a, map2, false, false);
            if (crashDetailBeanPackageCrashDatas != null) {
                String str22 = map.get("userId");
                if (str22 != null) {
                    C0657x.m466c("[Native record info] userId: %s", str22);
                    crashDetailBeanPackageCrashDatas.f346m = str22;
                }
                String str23 = map.get("sysLog");
                if (str23 != null) {
                    crashDetailBeanPackageCrashDatas.f356w = str23;
                }
                String str24 = map.get("appVersion");
                if (str24 != null) {
                    C0657x.m466c("[Native record info] appVersion: %s", str24);
                    crashDetailBeanPackageCrashDatas.f339f = str24;
                }
                String str25 = map.get("isAppForeground");
                if (str25 != null) {
                    C0657x.m466c("[Native record info] isAppForeground: %s", str25);
                    crashDetailBeanPackageCrashDatas.f323N = str25.equalsIgnoreCase("true");
                }
                String str26 = map.get("launchTime");
                if (str26 != null) {
                    C0657x.m466c("[Native record info] launchTime: %s", str26);
                    try {
                        crashDetailBeanPackageCrashDatas.f322M = Long.parseLong(str26);
                    } catch (NumberFormatException e) {
                        if (!C0657x.m462a(e)) {
                            e.printStackTrace();
                        }
                    }
                }
                crashDetailBeanPackageCrashDatas.f359z = null;
                crashDetailBeanPackageCrashDatas.f344k = true;
            }
            return crashDetailBeanPackageCrashDatas;
        } catch (Throwable th) {
            C0657x.m468e("error format", new Object[0]);
            th.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private static String m267a(BufferedInputStream bufferedInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        if (bufferedInputStream == null) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(1024);
            while (true) {
                try {
                    int i = bufferedInputStream.read();
                    if (i == -1) {
                        byteArrayOutputStream.close();
                        break;
                    }
                    if (i == 0) {
                        String str = new String(byteArrayOutputStream.toByteArray(), "UTf-8");
                        byteArrayOutputStream.close();
                        return str;
                    }
                    byteArrayOutputStream.write(i);
                } catch (Throwable th) {
                    th = th;
                    try {
                        C0657x.m462a(th);
                        return null;
                    } finally {
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v3, types: [boolean] */
    /* JADX INFO: renamed from: a */
    public static CrashDetailBean m265a(Context context, String str, NativeExceptionHandler nativeExceptionHandler) throws Throwable {
        BufferedInputStream bufferedInputStream;
        String str2;
        String strM267a;
        BufferedInputStream bufferedInputStream2 = 0;
        if (context == null || str == null || nativeExceptionHandler == null) {
            C0657x.m468e("get eup record file args error", new Object[0]);
            return null;
        }
        File file = new File(str, "rqd_record.eup");
        if (file.exists()) {
            ?? CanRead = file.canRead();
            try {
                if (CanRead != 0) {
                    try {
                        bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    } catch (IOException e) {
                        e = e;
                        bufferedInputStream = null;
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedInputStream2 != 0) {
                            try {
                                bufferedInputStream2.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                    try {
                        String strM267a2 = m267a(bufferedInputStream);
                        if (strM267a2 != null && strM267a2.equals("NATIVE_RQD_REPORT")) {
                            HashMap map = new HashMap();
                            loop0: while (true) {
                                str2 = null;
                                while (true) {
                                    strM267a = m267a(bufferedInputStream);
                                    if (strM267a == null) {
                                        break loop0;
                                    }
                                    if (str2 == null) {
                                        str2 = strM267a;
                                    }
                                }
                                map.put(str2, strM267a);
                            }
                            if (str2 != null) {
                                C0657x.m468e("record not pair! drop! %s", str2);
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                                return null;
                            }
                            CrashDetailBean crashDetailBeanM266a = m266a(context, map, nativeExceptionHandler);
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                            return crashDetailBeanM266a;
                        }
                        C0657x.m468e("record read fail! %s", strM267a2);
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                        return null;
                    } catch (IOException e6) {
                        e = e6;
                        e.printStackTrace();
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        }
                        return null;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream2 = CanRead;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: b */
    private static String m273b(String str, String str2) {
        BufferedReader bufferedReaderM489a = C0659z.m489a(str, "reg_record.txt");
        if (bufferedReaderM489a == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = bufferedReaderM489a.readLine();
            if (line != null && line.startsWith(str2)) {
                int i = 18;
                int i2 = 0;
                int length = 0;
                while (true) {
                    String line2 = bufferedReaderM489a.readLine();
                    if (line2 == null) {
                        break;
                    }
                    if (i2 % 4 == 0) {
                        if (i2 > 0) {
                            sb.append("\n");
                        }
                        sb.append("  ");
                    } else {
                        if (line2.length() > 16) {
                            i = 28;
                        }
                        sb.append("                ".substring(0, i - length));
                    }
                    length = line2.length();
                    sb.append(line2);
                    i2++;
                }
                sb.append("\n");
                return sb.toString();
            }
            if (bufferedReaderM489a != null) {
                try {
                    bufferedReaderM489a.close();
                } catch (Exception e) {
                    C0657x.m462a(e);
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                C0657x.m462a(th);
                if (bufferedReaderM489a != null) {
                    try {
                        bufferedReaderM489a.close();
                    } catch (Exception e2) {
                        C0657x.m462a(e2);
                    }
                }
                return null;
            } finally {
                if (bufferedReaderM489a != null) {
                    try {
                        bufferedReaderM489a.close();
                    } catch (Exception e3) {
                        C0657x.m462a(e3);
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: c */
    private static String m274c(String str, String str2) {
        BufferedReader bufferedReaderM489a = C0659z.m489a(str, "map_record.txt");
        if (bufferedReaderM489a == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = bufferedReaderM489a.readLine();
            if (line != null && line.startsWith(str2)) {
                while (true) {
                    String line2 = bufferedReaderM489a.readLine();
                    if (line2 == null) {
                        break;
                    }
                    sb.append("  ");
                    sb.append(line2);
                    sb.append("\n");
                }
                return sb.toString();
            }
            if (bufferedReaderM489a != null) {
                try {
                    bufferedReaderM489a.close();
                } catch (Exception e) {
                    C0657x.m462a(e);
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                C0657x.m462a(th);
                if (bufferedReaderM489a != null) {
                    try {
                        bufferedReaderM489a.close();
                    } catch (Exception e2) {
                        C0657x.m462a(e2);
                    }
                }
                return null;
            } finally {
                if (bufferedReaderM489a != null) {
                    try {
                        bufferedReaderM489a.close();
                    } catch (Exception e3) {
                        C0657x.m462a(e3);
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static String m270a(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String strM273b = m273b(str, str2);
        if (strM273b != null && !strM273b.isEmpty()) {
            sb.append("Register infos:\n");
            sb.append(strM273b);
        }
        String strM274c = m274c(str, str2);
        if (strM274c != null && !strM274c.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("System SO infos:\n");
            sb.append(strM274c);
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: b */
    public static String m272b(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str, "backup_record.txt");
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    /* JADX INFO: renamed from: c */
    public static void m275c(String str) {
        File[] fileArrListFiles;
        if (str == null) {
            return;
        }
        try {
            File file = new File(str);
            if (file.canRead() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.canRead() && file2.canWrite() && file2.length() == 0) {
                        file2.delete();
                        C0657x.m466c("Delete empty record file %s", file2.getAbsoluteFile());
                    }
                }
            }
        } catch (Throwable th) {
            C0657x.m462a(th);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m271a(boolean z, String str) {
        if (str != null) {
            f506a.add(new File(str, "rqd_record.eup"));
            f506a.add(new File(str, "reg_record.txt"));
            f506a.add(new File(str, "map_record.txt"));
            f506a.add(new File(str, "backup_record.txt"));
            if (z) {
                m275c(str);
            }
        }
        List<File> list = f506a;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (File file : f506a) {
            if (file.exists() && file.canWrite()) {
                file.delete();
                C0657x.m466c("Delete record file %s", file.getAbsoluteFile());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.String] */
    /* JADX INFO: renamed from: a */
    public static String m269a(String str, int i, String str2, boolean z) {
        BufferedReader bufferedReader = null;
        if (str != null && i > 0) {
            File file = new File(str);
            if (file.exists() && file.canRead()) {
                C0657x.m461a("Read system log from native record file(length: %s bytes): %s", Long.valueOf(file.length()), file.getAbsolutePath());
                f506a.add(file);
                C0657x.m466c("Add this record file to list for cleaning lastly.", new Object[0]);
                if (str2 == null) {
                    return C0659z.m496a(new File(str), i, z);
                }
                String sb = new StringBuilder();
                try {
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                        while (true) {
                            try {
                                String line = bufferedReader2.readLine();
                                if (line == null) {
                                    break;
                                }
                                if (Pattern.compile(str2 + "[ ]*:").matcher(line).find()) {
                                    sb.append(line);
                                    sb.append("\n");
                                }
                                if (i > 0 && sb.length() > i) {
                                    if (z) {
                                        sb.delete(i, sb.length());
                                        break;
                                    }
                                    sb.delete(0, sb.length() - i);
                                }
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                try {
                                    C0657x.m462a(th);
                                    sb.append("\n[error:" + th.toString() + "]");
                                    String string = sb.toString();
                                    if (bufferedReader == null) {
                                        return string;
                                    }
                                    bufferedReader.close();
                                    sb = string;
                                } catch (Throwable th2) {
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Exception e) {
                                            C0657x.m462a(e);
                                        }
                                    }
                                    throw th2;
                                }
                            }
                        }
                        String string2 = sb.toString();
                        bufferedReader2.close();
                        sb = string2;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                    return sb;
                } catch (Exception e2) {
                    C0657x.m462a(e2);
                    return sb;
                }
            }
        }
        return null;
    }
}
