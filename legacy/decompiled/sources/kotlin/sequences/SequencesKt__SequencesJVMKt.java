package kotlin.sequences;

import java.util.Enumeration;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: SequencesJVM.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b¨\u0006\u0004"}, m535d2 = {"asSequence", "Lkotlin/sequences/Sequence;", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"}, m536k = 5, m537mv = {1, 1, 16}, m539xi = 1, m540xs = "kotlin/sequences/SequencesKt")
class SequencesKt__SequencesJVMKt extends SequencesKt__SequenceBuilderKt {
    private static final <T> Sequence<T> asSequence(Enumeration<T> enumeration) {
        return SequencesKt.asSequence(CollectionsKt.iterator(enumeration));
    }
}
