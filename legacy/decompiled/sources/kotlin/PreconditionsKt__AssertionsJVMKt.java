package kotlin;

import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: AssertionsJVM.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b¨\u0006\u0007"}, m535d2 = {"assert", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "kotlin-stdlib"}, m536k = 5, m537mv = {1, 1, 16}, m539xi = 1, m540xs = "kotlin/PreconditionsKt")
class PreconditionsKt__AssertionsJVMKt {
    /* JADX INFO: renamed from: assert, reason: not valid java name */
    private static final void m566assert(boolean z) {
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
    }

    /* JADX INFO: renamed from: assert, reason: not valid java name */
    private static final void m567assert(boolean z, Function0<? extends Object> function0) {
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError(function0.invoke());
        }
    }
}
