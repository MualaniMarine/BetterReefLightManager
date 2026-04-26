package com.mylhyl.zxing.scanner.encode;

import android.telephony.PhoneNumberUtils;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
abstract class ContactEncoder {
    abstract String[] encode(List<String> list, String str, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, String str2);

    ContactEncoder() {
    }

    static String trim(String str) {
        if (str == null) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.isEmpty()) {
            return null;
        }
        return strTrim;
    }

    static void append(StringBuilder sb, StringBuilder sb2, String str, String str2, Formatter formatter, char c) {
        String strTrim = trim(str2);
        if (strTrim != null) {
            sb.append(str);
            sb.append(formatter.format(strTrim, 0));
            sb.append(c);
            sb2.append(strTrim);
            sb2.append('\n');
        }
    }

    static void appendUpToUnique(StringBuilder sb, StringBuilder sb2, String str, List<String> list, int i, Formatter formatter, Formatter formatter2, char c) {
        if (list == null) {
            return;
        }
        HashSet hashSet = new HashSet(2);
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            String strTrim = trim(list.get(i3));
            if (strTrim != null && !strTrim.isEmpty() && !hashSet.contains(strTrim)) {
                sb.append(str);
                sb.append(formatter2.format(strTrim, i3));
                sb.append(c);
                sb2.append(formatter == null ? strTrim : formatter.format(strTrim, i3));
                sb2.append('\n');
                i2++;
                if (i2 == i) {
                    return;
                } else {
                    hashSet.add(strTrim);
                }
            }
        }
    }

    static String formatPhone(String str) {
        return PhoneNumberUtils.formatNumber(str);
    }
}
