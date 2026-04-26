package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface SingleOnSubscribe<T> {
    void subscribe(SingleEmitter<T> emitter) throws Throwable;
}
