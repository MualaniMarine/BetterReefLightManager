package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableConcatMapSingle<T, R> extends Flowable<R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final int prefetch;
    final Flowable<T> source;

    public FlowableConcatMapSingle(Flowable<T> source, Function<? super T, ? extends SingleSource<? extends R>> mapper, ErrorMode errorMode, int prefetch) {
        this.source = source;
        this.mapper = mapper;
        this.errorMode = errorMode;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> s) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapSingleSubscriber(s, this.mapper, this.prefetch, this.errorMode));
    }

    static final class ConcatMapSingleSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final int STATE_ACTIVE = 1;
        static final int STATE_INACTIVE = 0;
        static final int STATE_RESULT_VALUE = 2;
        private static final long serialVersionUID = -9140123220065488293L;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        long emitted;
        final ErrorMode errorMode;
        R item;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final int prefetch;
        final SimplePlainQueue<T> queue;
        volatile int state;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable errors = new AtomicThrowable();
        final ConcatMapSingleObserver<R> inner = new ConcatMapSingleObserver<>(this);

        ConcatMapSingleSubscriber(Subscriber<? super R> downstream, Function<? super T, ? extends SingleSource<? extends R>> mapper, int prefetch, ErrorMode errorMode) {
            this.downstream = downstream;
            this.mapper = mapper;
            this.prefetch = prefetch;
            this.errorMode = errorMode;
            this.queue = new SpscArrayQueue(prefetch);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                s.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                this.upstream.cancel();
                onError(new MissingBackpressureException("queue full?!"));
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            if (this.errors.tryAddThrowableOrReport(t)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.inner.dispose();
                }
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n) {
            BackpressureHelper.add(this.requested, n);
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            this.inner.dispose();
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue.clear();
                this.item = null;
            }
        }

        void innerSuccess(R item) {
            this.item = item;
            this.state = 2;
            drain();
        }

        void innerError(Throwable ex) {
            if (this.errors.tryAddThrowableOrReport(ex)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                this.state = 0;
                drain();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0036, code lost:
        
            r2.clear();
            r15.item = null;
            r3.tryTerminateConsumer(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x003e, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drain() {
            /*
                r15 = this;
                int r0 = r15.getAndIncrement()
                if (r0 == 0) goto L7
                return
            L7:
                org.reactivestreams.Subscriber<? super R> r0 = r15.downstream
                io.reactivex.rxjava3.internal.util.ErrorMode r1 = r15.errorMode
                io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue<T> r2 = r15.queue
                io.reactivex.rxjava3.internal.util.AtomicThrowable r3 = r15.errors
                java.util.concurrent.atomic.AtomicLong r4 = r15.requested
                int r5 = r15.prefetch
                int r6 = r5 >> 1
                int r5 = r5 - r6
                r6 = 1
                r7 = r6
            L18:
                boolean r8 = r15.cancelled
                r9 = 0
                if (r8 == 0) goto L24
                r2.clear()
                r15.item = r9
                goto Lae
            L24:
                int r8 = r15.state
                java.lang.Object r10 = r3.get()
                if (r10 == 0) goto L3f
                io.reactivex.rxjava3.internal.util.ErrorMode r10 = io.reactivex.rxjava3.internal.util.ErrorMode.IMMEDIATE
                if (r1 == r10) goto L36
                io.reactivex.rxjava3.internal.util.ErrorMode r10 = io.reactivex.rxjava3.internal.util.ErrorMode.BOUNDARY
                if (r1 != r10) goto L3f
                if (r8 != 0) goto L3f
            L36:
                r2.clear()
                r15.item = r9
                r3.tryTerminateConsumer(r0)
                return
            L3f:
                r10 = 0
                if (r8 != 0) goto L91
                boolean r8 = r15.done
                java.lang.Object r9 = r2.poll()
                if (r9 != 0) goto L4c
                r11 = r6
                goto L4d
            L4c:
                r11 = r10
            L4d:
                if (r8 == 0) goto L55
                if (r11 == 0) goto L55
                r3.tryTerminateConsumer(r0)
                return
            L55:
                if (r11 == 0) goto L58
                goto Lae
            L58:
                int r8 = r15.consumed
                int r8 = r8 + r6
                if (r8 != r5) goto L66
                r15.consumed = r10
                org.reactivestreams.Subscription r8 = r15.upstream
                long r10 = (long) r5
                r8.request(r10)
                goto L68
            L66:
                r15.consumed = r8
            L68:
                io.reactivex.rxjava3.functions.Function<? super T, ? extends io.reactivex.rxjava3.core.SingleSource<? extends R>> r8 = r15.mapper     // Catch: java.lang.Throwable -> L7e
                java.lang.Object r8 = r8.apply(r9)     // Catch: java.lang.Throwable -> L7e
                java.lang.String r9 = "The mapper returned a null SingleSource"
                java.lang.Object r8 = java.util.Objects.requireNonNull(r8, r9)     // Catch: java.lang.Throwable -> L7e
                io.reactivex.rxjava3.core.SingleSource r8 = (io.reactivex.rxjava3.core.SingleSource) r8     // Catch: java.lang.Throwable -> L7e
                r15.state = r6
                io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapSingle$ConcatMapSingleSubscriber$ConcatMapSingleObserver<R> r9 = r15.inner
                r8.subscribe(r9)
                goto Lae
            L7e:
                r1 = move-exception
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r1)
                org.reactivestreams.Subscription r4 = r15.upstream
                r4.cancel()
                r2.clear()
                r3.tryAddThrowableOrReport(r1)
                r3.tryTerminateConsumer(r0)
                return
            L91:
                r11 = 2
                if (r8 != r11) goto Lae
                long r11 = r15.emitted
                long r13 = r4.get()
                int r8 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
                if (r8 == 0) goto Lae
                R r8 = r15.item
                r15.item = r9
                r0.onNext(r8)
                r8 = 1
                long r11 = r11 + r8
                r15.emitted = r11
                r15.state = r10
                goto L18
            Lae:
                int r7 = -r7
                int r7 = r15.addAndGet(r7)
                if (r7 != 0) goto L18
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapSingle.ConcatMapSingleSubscriber.drain():void");
        }

        static final class ConcatMapSingleObserver<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final ConcatMapSingleSubscriber<?, R> parent;

            ConcatMapSingleObserver(ConcatMapSingleSubscriber<?, R> parent) {
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
