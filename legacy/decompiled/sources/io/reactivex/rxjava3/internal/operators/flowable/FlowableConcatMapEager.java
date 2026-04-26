package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableConcatMapEager<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;

    public FlowableConcatMapEager(Flowable<T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch, ErrorMode errorMode) {
        super(source);
        this.mapper = mapper;
        this.maxConcurrency = maxConcurrency;
        this.prefetch = prefetch;
        this.errorMode = errorMode;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> s) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapEagerDelayErrorSubscriber(s, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
    }

    static final class ConcatMapEagerDelayErrorSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, InnerQueuedSubscriberSupport<R> {
        private static final long serialVersionUID = -4255299542215038287L;
        volatile boolean cancelled;
        volatile InnerQueuedSubscriber<R> current;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final ErrorMode errorMode;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> subscribers;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        ConcatMapEagerDelayErrorSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch, ErrorMode errorMode) {
            this.downstream = actual;
            this.mapper = mapper;
            this.maxConcurrency = maxConcurrency;
            this.prefetch = prefetch;
            this.errorMode = errorMode;
            this.subscribers = new SpscLinkedArrayQueue<>(Math.min(prefetch, maxConcurrency));
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                int i = this.maxConcurrency;
                s.request(i == Integer.MAX_VALUE ? LongCompanionObject.MAX_VALUE : i);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                InnerQueuedSubscriber<R> innerQueuedSubscriber = new InnerQueuedSubscriber<>(this, this.prefetch);
                if (this.cancelled) {
                    return;
                }
                this.subscribers.offer(innerQueuedSubscriber);
                publisher.subscribe(innerQueuedSubscriber);
                if (this.cancelled) {
                    innerQueuedSubscriber.cancel();
                    drainAndCancel();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            if (this.errors.tryAddThrowableOrReport(t)) {
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
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            this.errors.tryTerminateAndReport();
            drainAndCancel();
        }

        void drainAndCancel() {
            if (getAndIncrement() == 0) {
                do {
                    cancelAll();
                } while (decrementAndGet() != 0);
            }
        }

        void cancelAll() {
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.current;
            this.current = null;
            if (innerQueuedSubscriber != null) {
                innerQueuedSubscriber.cancel();
            }
            while (true) {
                InnerQueuedSubscriber<R> innerQueuedSubscriberPoll = this.subscribers.poll();
                if (innerQueuedSubscriberPoll == null) {
                    return;
                } else {
                    innerQueuedSubscriberPoll.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this.requested, n);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerNext(InnerQueuedSubscriber<R> inner, R value) {
            if (inner.queue().offer(value)) {
                drain();
            } else {
                inner.cancel();
                innerError(inner, new MissingBackpressureException());
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerError(InnerQueuedSubscriber<R> inner, Throwable e) {
            if (this.errors.tryAddThrowableOrReport(e)) {
                inner.setDone();
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerComplete(InnerQueuedSubscriber<R> inner) {
            inner.setDone();
            drain();
        }

        /* JADX WARN: Code restructure failed: missing block: B:53:0x00bf, code lost:
        
            r0 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00c0, code lost:
        
            if (r13 != r6) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00c4, code lost:
        
            if (r17.cancelled == false) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00c6, code lost:
        
            cancelAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00c9, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00cc, code lost:
        
            if (r3 != io.reactivex.rxjava3.internal.util.ErrorMode.IMMEDIATE) goto L65;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00d6, code lost:
        
            if (r17.errors.get() == null) goto L65;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00d8, code lost:
        
            r17.current = null;
            r8.cancel();
            cancelAll();
            r17.errors.tryTerminateConsumer(r17.downstream);
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x00e7, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00e8, code lost:
        
            r9 = r8.isDone();
            r10 = r12.isEmpty();
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00f0, code lost:
        
            if (r9 == false) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00f2, code lost:
        
            if (r10 == false) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x00f4, code lost:
        
            r17.current = null;
            r17.upstream.request(1);
            r8 = null;
            r0 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x00fd, code lost:
        
            r4 = 0;
         */
        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drain() {
            /*
                Method dump skipped, instruction units count: 302
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapEager.ConcatMapEagerDelayErrorSubscriber.drain():void");
        }
    }
}
