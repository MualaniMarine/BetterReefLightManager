package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ObservableConverter<T, R> {
    R apply(Observable<T> upstream);
}
