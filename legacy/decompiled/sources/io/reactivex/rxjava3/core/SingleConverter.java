package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface SingleConverter<T, R> {
    R apply(Single<T> upstream);
}
