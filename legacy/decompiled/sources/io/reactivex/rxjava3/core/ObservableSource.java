package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ObservableSource<T> {
    void subscribe(Observer<? super T> observer);
}
