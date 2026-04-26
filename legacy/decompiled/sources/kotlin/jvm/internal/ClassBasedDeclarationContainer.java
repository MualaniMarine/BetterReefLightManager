package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: ClassBasedDeclarationContainer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, m535d2 = {"Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "Lkotlin/reflect/KDeclarationContainer;", "jClass", "Ljava/lang/Class;", "getJClass", "()Ljava/lang/Class;", "kotlin-stdlib"}, m536k = 1, m537mv = {1, 1, 16})
public interface ClassBasedDeclarationContainer extends KDeclarationContainer {
    Class<?> getJClass();
}
