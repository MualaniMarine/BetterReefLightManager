package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class CompletableFromCallable extends Completable {
    final Callable<?> callable;

    public CompletableFromCallable(Callable<?> callable) {
        this.callable = callable;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver observer) {
        Disposable disposableEmpty = Disposable.CC.empty();
        observer.onSubscribe(disposableEmpty);
        try {
            this.callable.call();
            if (disposableEmpty.isDisposed()) {
                return;
            }
            observer.onComplete();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (!disposableEmpty.isDisposed()) {
                observer.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }
    }
}
