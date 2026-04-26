package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ObservableOnSubscribe<T> {
    void subscribe(ObservableEmitter<T> emitter) throws Throwable;
}
