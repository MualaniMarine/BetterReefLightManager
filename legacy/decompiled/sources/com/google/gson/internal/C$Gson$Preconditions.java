package com.google.gson.internal;

/* JADX INFO: renamed from: com.google.gson.internal.$Gson$Preconditions, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$Gson$Preconditions {
    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw null;
    }

    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }
}
