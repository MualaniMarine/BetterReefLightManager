package per.goweii.burred;

/* JADX INFO: loaded from: classes.dex */
class Utils {
    Utils() {
    }

    static <T> T requireNonNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static <T> T requireNonNull(T t) {
        return (T) requireNonNull(t, "");
    }
}
