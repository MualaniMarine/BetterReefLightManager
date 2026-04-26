package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class MaybeFromFuture<T> extends Maybe<T> {
    final Future<? extends T> future;
    final long timeout;
    final TimeUnit unit;

    public MaybeFromFuture(Future<? extends T> future, long timeout, TimeUnit unit) {
        this.future = future;
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        T t;
        Disposable disposableEmpty = Disposable.CC.empty();
        maybeObserver.onSubscribe(disposableEmpty);
        if (disposableEmpty.isDisposed()) {
            return;
        }
        try {
            if (this.timeout <= 0) {
                t = this.future.get();
            } else {
                t = this.future.get(this.timeout, this.unit);
            }
            if (disposableEmpty.isDisposed()) {
                return;
            }
            if (t == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(t);
            }
        } catch (Throwable th) {
            th = th;
            Exceptions.throwIfFatal(th);
            if (th instanceof ExecutionException) {
                th = th.getCause();
            }
            Exceptions.throwIfFatal(th);
            if (disposableEmpty.isDisposed()) {
                return;
            }
            maybeObserver.onError(th);
        }
    }
}
