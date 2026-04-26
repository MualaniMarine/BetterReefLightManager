package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface CompletableOnSubscribe {
    void subscribe(CompletableEmitter emitter) throws Throwable;
}
