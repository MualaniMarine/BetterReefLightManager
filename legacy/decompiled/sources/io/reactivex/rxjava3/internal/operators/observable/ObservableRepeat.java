package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class ObservableRepeat<T> extends AbstractObservableWithUpstream<T, T> {
    final long count;

    public ObservableRepeat(Observable<T> source, long count) {
        super(source);
        this.count = count;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        long j = this.count;
        long j2 = LongCompanionObject.MAX_VALUE;
        if (j != LongCompanionObject.MAX_VALUE) {
            j2 = j - 1;
        }
        new RepeatObserver(observer, j2, sequentialDisposable, this.source).subscribeNext();
    }

    static final class RepeatObserver<T> extends AtomicInteger implements Observer<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final Observer<? super T> downstream;
        long remaining;

        /* JADX INFO: renamed from: sd */
        final SequentialDisposable f845sd;
        final ObservableSource<? extends T> source;

        RepeatObserver(Observer<? super T> actual, long count, SequentialDisposable sd, ObservableSource<? extends T> source) {
            this.downstream = actual;
            this.f845sd = sd;
            this.source = source;
            this.remaining = count;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d) {
            this.f845sd.replace(d);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t) {
            this.downstream.onError(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            long j = this.remaining;
            if (j != LongCompanionObject.MAX_VALUE) {
                this.remaining = j - 1;
            }
            if (j != 0) {
                subscribeNext();
            } else {
                this.downstream.onComplete();
            }
        }

        void subscribeNext() {
            if (getAndIncrement() == 0) {
                int iAddAndGet = 1;
                while (!this.f845sd.isDisposed()) {
                    this.source.subscribe(this);
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }
    }
}
