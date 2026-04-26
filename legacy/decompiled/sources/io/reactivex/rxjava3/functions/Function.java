package io.reactivex.rxjava3.functions;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t) throws Throwable;
}
