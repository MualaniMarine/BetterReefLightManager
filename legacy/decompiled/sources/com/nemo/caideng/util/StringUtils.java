package com.nemo.caideng.util;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class StringUtils {
    public static boolean ipVerify(String str) {
        return Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}").matcher(str).matches();
    }
}
