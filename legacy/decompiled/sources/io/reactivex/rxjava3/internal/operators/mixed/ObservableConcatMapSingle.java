package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class ObservableConcatMapSingle<T, R> extends Observable<R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final int prefetch;
    final ObservableSource<T> source;

    public ObservableConcatMapSingle(ObservableSource<T> source, Function<? super T, ? extends SingleSource<? extends R>> mapper, ErrorMode errorMode, int prefetch) {
        this.source = source;
        this.mapper = mapper;
        this.errorMode = errorMode;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        if (ScalarXMapZHelper.tryAsSingle(this.source, this.mapper, observer)) {
            return;
        }
        this.source.subscribe(new ConcatMapSingleMainObserver(observer, this.mapper, this.prefetch, this.errorMode));
    }

    static final class ConcatMapSingleMainObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        static final int STATE_ACTIVE = 1;
        static final int STATE_INACTIVE = 0;
        static final int STATE_RESULT_VALUE = 2;
        private static final long serialVersionUID = -9140123220065488293L;
        volatile boolean cancelled;
        volatile boolean done;
        final Observer<? super R> downstream;
        final ErrorMode errorMode;
        final AtomicThrowable errors = new AtomicThrowable();
        final ConcatMapSingleObserver<R> inner = new ConcatMapSingleObserver<>(this);
        R item;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final SimplePlainQueue<T> queue;
        volatile int state;
        Disposable upstream;

        ConcatMapSingleMainObserver(Observer<? super R> downstream, Function<? super T, ? extends SingleSource<? extends R>> mapper, int prefetch, ErrorMode errorMode) {
            this.downstream = downstream;
            this.mapper = mapper;
            this.errorMode = errorMode;
            this.queue = new SpscLinkedArrayQueue(prefetch);
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
            this.queue.offer(t);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t) {
            if (this.errors.tryAddThrowableOrReport(t)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.inner.dispose();
                }
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
            this.cancelled = true;
            this.upstream.dispose();
            this.inner.dispose();
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue.clear();
                this.item = null;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void innerSuccess(R item) {
            this.item = item;
            this.state = 2;
            drain();
        }

        void innerError(Throwable ex) {
            if (this.errors.tryAddThrowableOrReport(ex)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.dispose();
                }
                this.state = 0;
                drain();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
        
            r2.clear();
            r10.item = null;
            r3.tryTerminateConsumer(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0036, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drain() {
            /*
                r10 = this;
                int r0 = r10.getAndIncrement()
                if (r0 == 0) goto L7
                return
            L7:
                io.reactivex.rxjava3.core.Observer<? super R> r0 = r10.downstream
                io.reactivex.rxjava3.internal.util.ErrorMode r1 = r10.errorMode
                io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue<T> r2 = r10.queue
                io.reactivex.rxjava3.internal.util.AtomicThrowable r3 = r10.errors
                r4 = 1
                r5 = r4
            L11:
                boolean r6 = r10.cancelled
                r7 = 0
                if (r6 == 0) goto L1c
                r2.clear()
                r10.item = r7
                goto L84
            L1c:
                int r6 = r10.state
                java.lang.Object r8 = r3.get()
                if (r8 == 0) goto L37
                io.reactivex.rxjava3.internal.util.ErrorMode r8 = io.reactivex.rxjava3.internal.util.ErrorMode.IMMEDIATE
                if (r1 == r8) goto L2e
                io.reactivex.rxjava3.internal.util.ErrorMode r8 = io.reactivex.rxjava3.internal.util.ErrorMode.BOUNDARY
                if (r1 != r8) goto L37
                if (r6 != 0) goto L37
            L2e:
                r2.clear()
                r10.item = r7
                r3.tryTerminateConsumer(r0)
                return
            L37:
                r8 = 0
                if (r6 != 0) goto L77
                boolean r6 = r10.done
                java.lang.Object r7 = r2.poll()
                if (r7 != 0) goto L43
                r8 = r4
            L43:
                if (r6 == 0) goto L4b
                if (r8 == 0) goto L4b
                r3.tryTerminateConsumer(r0)
                return
            L4b:
                if (r8 == 0) goto L4e
                goto L84
            L4e:
                io.reactivex.rxjava3.functions.Function<? super T, ? extends io.reactivex.rxjava3.core.SingleSource<? extends R>> r6 = r10.mapper     // Catch: java.lang.Throwable -> L64
                java.lang.Object r6 = r6.apply(r7)     // Catch: java.lang.Throwable -> L64
                java.lang.String r7 = "The mapper returned a null SingleSource"
                java.lang.Object r6 = java.util.Objects.requireNonNull(r6, r7)     // Catch: java.lang.Throwable -> L64
                io.reactivex.rxjava3.core.SingleSource r6 = (io.reactivex.rxjava3.core.SingleSource) r6     // Catch: java.lang.Throwable -> L64
                r10.state = r4
                io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapSingle$ConcatMapSingleMainObserver$ConcatMapSingleObserver<R> r7 = r10.inner
                r6.subscribe(r7)
                goto L84
            L64:
                r1 = move-exception
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r1)
                io.reactivex.rxjava3.disposables.Disposable r4 = r10.upstream
                r4.dispose()
                r2.clear()
                r3.tryAddThrowableOrReport(r1)
                r3.tryTerminateConsumer(r0)
                return
            L77:
                r9 = 2
                if (r6 != r9) goto L84
                R r6 = r10.item
                r10.item = r7
                r0.onNext(r6)
                r10.state = r8
                goto L11
            L84:
                int r5 = -r5
                int r5 = r10.addAndGet(r5)
                if (r5 != 0) goto L11
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapSingle.ConcatMapSingleMainObserver.drain():void");
        }

        static final class ConcatMapSingleObserver<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final ConcatMapSingleMainObserver<?, R> parent;

            ConcatMapSingleObserver(ConcatMapSingleMainObserver<?, R> parent) {
                this.parent = parent;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d) {
                DisposableHelper.replace(this, d);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R t) {
                this.parent.innerSuccess(t);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e) {
                this.parent.innerError(e);
            }

            void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
