package io.reactivex.rxjava3.parallel;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ParallelFlowableConverter<T, R> {
    R apply(ParallelFlowable<T> upstream);
}
