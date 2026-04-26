package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class CompletableConcatArray extends Completable {
    final CompletableSource[] sources;

    public CompletableConcatArray(CompletableSource[] sources) {
        this.sources = sources;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        ConcatInnerObserver concatInnerObserver = new ConcatInnerObserver(observer, this.sources);
        observer.onSubscribe(concatInnerObserver.f802sd);
        concatInnerObserver.next();
    }

    static final class ConcatInnerObserver extends AtomicInteger implements CompletableObserver {
        private static final long serialVersionUID = -7965400327305809232L;
        final CompletableObserver downstream;
        int index;

        /* JADX INFO: renamed from: sd */
        final SequentialDisposable f802sd = new SequentialDisposable();
        final CompletableSource[] sources;

        ConcatInnerObserver(CompletableObserver actual, CompletableSource[] sources) {
            this.downstream = actual;
            this.sources = sources;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d) {
            this.f802sd.replace(d);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e) {
            this.downstream.onError(e);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            next();
        }

        void next() {
            if (!this.f802sd.isDisposed() && getAndIncrement() == 0) {
                CompletableSource[] completableSourceArr = this.sources;
                while (!this.f802sd.isDisposed()) {
                    int i = this.index;
                    this.index = i + 1;
                    if (i == completableSourceArr.length) {
                        this.downstream.onComplete();
                        return;
                    } else {
                        completableSourceArr[i].subscribe(this);
                        if (decrementAndGet() == 0) {
                            return;
                        }
                    }
                }
            }
        }
    }
}
