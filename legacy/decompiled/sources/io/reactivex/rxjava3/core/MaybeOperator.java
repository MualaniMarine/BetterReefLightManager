package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface MaybeOperator<Downstream, Upstream> {
    MaybeObserver<? super Upstream> apply(MaybeObserver<? super Downstream> observer) throws Throwable;
}
