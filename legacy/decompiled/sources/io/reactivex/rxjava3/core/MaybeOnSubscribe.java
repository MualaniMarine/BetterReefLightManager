package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface MaybeOnSubscribe<T> {
    void subscribe(MaybeEmitter<T> emitter) throws Throwable;
}
