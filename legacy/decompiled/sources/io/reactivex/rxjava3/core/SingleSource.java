package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface SingleSource<T> {
    void subscribe(SingleObserver<? super T> observer);
}
