package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: localVariableReferences.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0016¨\u0006\n"}, m535d2 = {"Lkotlin/jvm/internal/MutableLocalVariableReference;", "Lkotlin/jvm/internal/MutablePropertyReference0;", "()V", "get", "", "getOwner", "Lkotlin/reflect/KDeclarationContainer;", "set", "", "value", "kotlin-stdlib"}, m536k = 1, m537mv = {1, 1, 16})
public class MutableLocalVariableReference extends MutablePropertyReference0 {
    @Override // kotlin.jvm.internal.CallableReference
    public KDeclarationContainer getOwner() {
        LocalVariableReferencesKt.notSupportedError();
        throw null;
    }

    @Override // kotlin.reflect.KProperty0
    public Object get() {
        LocalVariableReferencesKt.notSupportedError();
        throw null;
    }

    @Override // kotlin.reflect.KMutableProperty0
    public void set(Object value) {
        LocalVariableReferencesKt.notSupportedError();
        throw null;
    }
}
