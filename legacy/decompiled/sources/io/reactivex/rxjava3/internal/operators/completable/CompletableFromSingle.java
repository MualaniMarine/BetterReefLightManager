package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;

/* JADX INFO: loaded from: classes.dex */
public final class CompletableFromSingle<T> extends Completable {
    final SingleSource<T> single;

    public CompletableFromSingle(SingleSource<T> single) {
        this.single = single;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(final CompletableObserver observer) {
        this.single.subscribe(new CompletableFromSingleObserver(observer));
    }

    static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {

        /* JADX INFO: renamed from: co */
        final CompletableObserver f805co;

        CompletableFromSingleObserver(CompletableObserver co) {
            this.f805co = co;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e) {
            this.f805co.onError(e);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d) {
            this.f805co.onSubscribe(d);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.f805co.onComplete();
        }
    }
}
