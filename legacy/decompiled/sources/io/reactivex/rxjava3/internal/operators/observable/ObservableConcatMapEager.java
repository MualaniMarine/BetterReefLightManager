package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.QueueDisposable;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.observers.InnerQueuedObserver;
import io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class ObservableConcatMapEager<T, R> extends AbstractObservableWithUpstream<T, R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;

    public ObservableConcatMapEager(ObservableSource<T> source, Function<? super T, ? extends ObservableSource<? extends R>> mapper, ErrorMode errorMode, int maxConcurrency, int prefetch) {
        super(source);
        this.mapper = mapper;
        this.errorMode = errorMode;
        this.maxConcurrency = maxConcurrency;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new ConcatMapEagerMainObserver(observer, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
    }

    static final class ConcatMapEagerMainObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable, InnerQueuedObserverSupport<R> {
        private static final long serialVersionUID = 8080567949447303262L;
        int activeCount;
        volatile boolean cancelled;
        InnerQueuedObserver<R> current;
        volatile boolean done;
        final Observer<? super R> downstream;
        final ErrorMode errorMode;
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        SimpleQueue<T> queue;
        int sourceMode;
        Disposable upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final ArrayDeque<InnerQueuedObserver<R>> observers = new ArrayDeque<>();

        ConcatMapEagerMainObserver(Observer<? super R> actual, Function<? super T, ? extends ObservableSource<? extends R>> mapper, int maxConcurrency, int prefetch, ErrorMode errorMode) {
            this.downstream = actual;
            this.mapper = mapper;
            this.maxConcurrency = maxConcurrency;
            this.prefetch = prefetch;
            this.errorMode = errorMode;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;
                if (d instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) d;
                    int iRequestFusion = queueDisposable.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T value) {
            if (this.sourceMode == 0) {
                this.queue.offer(value);
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e) {
            if (this.errors.tryAddThrowableOrReport(e)) {
                this.done = true;
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.dispose();
            this.errors.tryTerminateAndReport();
            drainAndDispose();
        }

        void drainAndDispose() {
            if (getAndIncrement() == 0) {
                do {
                    this.queue.clear();
                    disposeAll();
                } while (decrementAndGet() != 0);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void disposeAll() {
            InnerQueuedObserver<R> innerQueuedObserver = this.current;
            if (innerQueuedObserver != null) {
                innerQueuedObserver.dispose();
            }
            while (true) {
                InnerQueuedObserver<R> innerQueuedObserverPoll = this.observers.poll();
                if (innerQueuedObserverPoll == null) {
                    return;
                } else {
                    innerQueuedObserverPoll.dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void innerNext(InnerQueuedObserver<R> inner, R value) {
            inner.queue().offer(value);
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void innerError(InnerQueuedObserver<R> inner, Throwable e) {
            if (this.errors.tryAddThrowableOrReport(e)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.upstream.dispose();
                }
                inner.setDone();
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void innerComplete(InnerQueuedObserver<R> inner) {
            inner.setDone();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.observers.InnerQueuedObserverSupport
        public void drain() {
            R rPoll;
            boolean z;
            if (getAndIncrement() != 0) {
                return;
            }
            SimpleQueue<T> simpleQueue = this.queue;
            ArrayDeque<InnerQueuedObserver<R>> arrayDeque = this.observers;
            Observer<? super R> observer = this.downstream;
            ErrorMode errorMode = this.errorMode;
            int iAddAndGet = 1;
            while (true) {
                int i = this.activeCount;
                while (i != this.maxConcurrency) {
                    if (this.cancelled) {
                        simpleQueue.clear();
                        disposeAll();
                        return;
                    }
                    if (errorMode == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                        simpleQueue.clear();
                        disposeAll();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                    try {
                        T tPoll = simpleQueue.poll();
                        if (tPoll == null) {
                            break;
                        }
                        ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.mapper.apply(tPoll), "The mapper returned a null ObservableSource");
                        InnerQueuedObserver<R> innerQueuedObserver = new InnerQueuedObserver<>(this, this.prefetch);
                        arrayDeque.offer(innerQueuedObserver);
                        observableSource.subscribe(innerQueuedObserver);
                        i++;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.upstream.dispose();
                        simpleQueue.clear();
                        disposeAll();
                        this.errors.tryAddThrowableOrReport(th);
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                }
                this.activeCount = i;
                if (this.cancelled) {
                    simpleQueue.clear();
                    disposeAll();
                    return;
                }
                if (errorMode == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                    simpleQueue.clear();
                    disposeAll();
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                }
                InnerQueuedObserver<R> innerQueuedObserver2 = this.current;
                if (innerQueuedObserver2 == null) {
                    if (errorMode == ErrorMode.BOUNDARY && this.errors.get() != null) {
                        simpleQueue.clear();
                        disposeAll();
                        this.errors.tryTerminateConsumer(observer);
                        return;
                    }
                    boolean z2 = this.done;
                    InnerQueuedObserver<R> innerQueuedObserverPoll = arrayDeque.poll();
                    boolean z3 = innerQueuedObserverPoll == null;
                    if (z2 && z3) {
                        if (this.errors.get() != null) {
                            simpleQueue.clear();
                            disposeAll();
                            this.errors.tryTerminateConsumer(observer);
                            return;
                        }
                        observer.onComplete();
                        return;
                    }
                    if (!z3) {
                        this.current = innerQueuedObserverPoll;
                    }
                    innerQueuedObserver2 = innerQueuedObserverPoll;
                }
                if (innerQueuedObserver2 != null) {
                    SimpleQueue<R> simpleQueueQueue = innerQueuedObserver2.queue();
                    while (!this.cancelled) {
                        boolean zIsDone = innerQueuedObserver2.isDone();
                        if (errorMode == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                            simpleQueue.clear();
                            disposeAll();
                            this.errors.tryTerminateConsumer(observer);
                            return;
                        }
                        try {
                            rPoll = simpleQueueQueue.poll();
                            z = rPoll == null;
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.errors.tryAddThrowableOrReport(th2);
                            this.current = null;
                            this.activeCount--;
                        }
                        if (zIsDone && z) {
                            this.current = null;
                            this.activeCount--;
                        } else if (!z) {
                            observer.onNext(rPoll);
                        }
                    }
                    simpleQueue.clear();
                    disposeAll();
                    return;
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
    }
}
