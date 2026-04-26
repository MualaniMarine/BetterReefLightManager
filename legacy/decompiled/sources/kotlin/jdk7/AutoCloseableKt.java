package kotlin.jdk7;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;

/* JADX INFO: compiled from: AutoCloseable.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001a8\u0010\u0005\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\u0087\b¢\u0006\u0002\u0010\n¨\u0006\u000b"}, m535d2 = {"closeFinally", "", "Ljava/lang/AutoCloseable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"}, m536k = 2, m537mv = {1, 1, 16}, m538pn = "kotlin")
public final class AutoCloseableKt {
    private static final <T extends AutoCloseable, R> R use(T t, Function1<? super T, ? extends R> function1) throws Exception {
        Throwable th = (Throwable) null;
        try {
            R rInvoke = function1.invoke(t);
            InlineMarker.finallyStart(1);
            closeFinally(t, th);
            InlineMarker.finallyEnd(1);
            return rInvoke;
        } finally {
        }
    }

    public static final void closeFinally(AutoCloseable autoCloseable, Throwable th) throws Exception {
        if (autoCloseable == null) {
            return;
        }
        if (th == null) {
            autoCloseable.close();
            return;
        }
        try {
            autoCloseable.close();
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
    }
}
