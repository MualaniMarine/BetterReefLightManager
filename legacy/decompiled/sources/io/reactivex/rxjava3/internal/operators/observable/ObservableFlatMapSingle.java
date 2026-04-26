package io.reactivex.rxjava3.internal.operators.observable;

import android.Manifest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class ObservableFlatMapSingle<T, R> extends AbstractObservableWithUpstream<T, R> {
    final boolean delayErrors;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;

    public ObservableFlatMapSingle(ObservableSource<T> source, Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean delayError) {
        super(source);
        this.mapper = mapper;
        this.delayErrors = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlatMapSingleObserver(observer, this.mapper, this.delayErrors));
    }

    static final class FlatMapSingleObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8600231336733376951L;
        volatile boolean cancelled;
        final boolean delayErrors;
        final Observer<? super R> downstream;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        Disposable upstream;
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicInteger active = new AtomicInteger(1);
        final AtomicReference<SpscLinkedArrayQueue<R>> queue = new AtomicReference<>();

        FlatMapSingleObserver(Observer<? super R> actual, Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean delayErrors) {
            this.downstream = actual;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            try {
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null SingleSource");
                this.active.getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.cancelled || !this.set.add(innerObserver)) {
                    return;
                }
                singleSource.subscribe(innerObserver);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t) {
            this.active.decrementAndGet();
            if (this.errors.tryAddThrowableOrReport(t)) {
                if (!this.delayErrors) {
                    this.set.dispose();
                }
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.active.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.set.dispose();
            this.errors.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0042  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void innerSuccess(io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapSingle.FlatMapSingleObserver<T, R>.InnerObserver r3, R r4) {
            /*
                r2 = this;
                io.reactivex.rxjava3.disposables.CompositeDisposable r0 = r2.set
                r0.delete(r3)
                int r3 = r2.get()
                if (r3 != 0) goto L42
                r3 = 0
                r0 = 1
                boolean r1 = r2.compareAndSet(r3, r0)
                if (r1 == 0) goto L42
                io.reactivex.rxjava3.core.Observer<? super R> r1 = r2.downstream
                r1.onNext(r4)
                java.util.concurrent.atomic.AtomicInteger r4 = r2.active
                int r4 = r4.decrementAndGet()
                if (r4 != 0) goto L21
                r3 = r0
            L21:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue<R>> r4 = r2.queue
                java.lang.Object r4 = r4.get()
                io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue r4 = (io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue) r4
                if (r3 == 0) goto L3b
                if (r4 == 0) goto L33
                boolean r3 = r4.isEmpty()
                if (r3 == 0) goto L3b
            L33:
                io.reactivex.rxjava3.internal.util.AtomicThrowable r3 = r2.errors
                io.reactivex.rxjava3.core.Observer<? super R> r4 = r2.downstream
                r3.tryTerminateConsumer(r4)
                return
            L3b:
                int r3 = r2.decrementAndGet()
                if (r3 != 0) goto L57
                return
            L42:
                io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue r3 = r2.getOrCreateQueue()
                monitor-enter(r3)
                r3.offer(r4)     // Catch: java.lang.Throwable -> L5b
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L5b
                java.util.concurrent.atomic.AtomicInteger r3 = r2.active
                r3.decrementAndGet()
                int r3 = r2.getAndIncrement()
                if (r3 == 0) goto L57
                return
            L57:
                r2.drainLoop()
                return
            L5b:
                r4 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L5b
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapSingle.FlatMapSingleObserver.innerSuccess(io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver$InnerObserver, java.lang.Object):void");
        }

        SpscLinkedArrayQueue<R> getOrCreateQueue() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                return spscLinkedArrayQueue;
            }
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = new SpscLinkedArrayQueue<>(Observable.bufferSize());
            return this.queue.compareAndSet(null, spscLinkedArrayQueue2) ? spscLinkedArrayQueue2 : this.queue.get();
        }

        void innerError(FlatMapSingleObserver<T, R>.InnerObserver inner, Throwable e) {
            this.set.delete(inner);
            if (this.errors.tryAddThrowableOrReport(e)) {
                if (!this.delayErrors) {
                    this.upstream.dispose();
                    this.set.dispose();
                }
                this.active.decrementAndGet();
                drain();
            }
        }

        void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        void clear() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        void drainLoop() {
            Observer<? super R> observer = this.downstream;
            AtomicInteger atomicInteger = this.active;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (!this.delayErrors && this.errors.get() != null) {
                    clear();
                    this.errors.tryTerminateConsumer(observer);
                    return;
                }
                boolean z = atomicInteger.get() == 0;
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue = atomicReference.get();
                Manifest manifestPoll = spscLinkedArrayQueue != null ? spscLinkedArrayQueue.poll() : null;
                boolean z2 = manifestPoll == null;
                if (z && z2) {
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                } else if (!z2) {
                    observer.onNext(manifestPoll);
                } else {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            clear();
        }

        final class InnerObserver extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            InnerObserver() {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d) {
                DisposableHelper.setOnce(this, d);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R value) {
                FlatMapSingleObserver.this.innerSuccess(this, value);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e) {
                FlatMapSingleObserver.this.innerError(this, e);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
