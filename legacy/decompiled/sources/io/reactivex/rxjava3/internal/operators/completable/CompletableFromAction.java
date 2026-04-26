package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* JADX INFO: loaded from: classes.dex */
public final class CompletableFromAction extends Completable {
    final Action run;

    public CompletableFromAction(Action run) {
        this.run = run;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver observer) {
        Disposable disposableEmpty = Disposable.CC.empty();
        observer.onSubscribe(disposableEmpty);
        if (disposableEmpty.isDisposed()) {
            return;
        }
        try {
            this.run.run();
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
