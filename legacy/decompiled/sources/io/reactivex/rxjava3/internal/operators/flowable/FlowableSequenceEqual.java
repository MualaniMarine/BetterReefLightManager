package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableSequenceEqual<T> extends Flowable<Boolean> {
    final BiPredicate<? super T, ? super T> comparer;
    final Publisher<? extends T> first;
    final int prefetch;
    final Publisher<? extends T> second;

    interface EqualCoordinatorHelper {
        void drain();

        void innerError(Throwable ex);
    }

    public FlowableSequenceEqual(Publisher<? extends T> first, Publisher<? extends T> second, BiPredicate<? super T, ? super T> comparer, int prefetch) {
        this.first = first;
        this.second = second;
        this.comparer = comparer;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Boolean> s) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(s, this.prefetch, this.comparer);
        s.onSubscribe(equalCoordinator);
        equalCoordinator.subscribe(this.first, this.second);
    }

    static final class EqualCoordinator<T> extends DeferredScalarSubscription<Boolean> implements EqualCoordinatorHelper {
        private static final long serialVersionUID = -6178010334400373240L;
        final BiPredicate<? super T, ? super T> comparer;
        final AtomicThrowable errors;
        final EqualSubscriber<T> first;
        final EqualSubscriber<T> second;

        /* JADX INFO: renamed from: v1 */
        T f823v1;

        /* JADX INFO: renamed from: v2 */
        T f824v2;
        final AtomicInteger wip;

        EqualCoordinator(Subscriber<? super Boolean> actual, int prefetch, BiPredicate<? super T, ? super T> comparer) {
            super(actual);
            this.comparer = comparer;
            this.wip = new AtomicInteger();
            this.first = new EqualSubscriber<>(this, prefetch);
            this.second = new EqualSubscriber<>(this, prefetch);
            this.errors = new AtomicThrowable();
        }

        void subscribe(Publisher<? extends T> source1, Publisher<? extends T> source2) {
            source1.subscribe(this.first);
            source2.subscribe(this.second);
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.first.cancel();
            this.second.cancel();
            this.errors.tryTerminateAndReport();
            if (this.wip.getAndIncrement() == 0) {
                this.first.clear();
                this.second.clear();
            }
        }

        void cancelAndClear() {
            this.first.cancel();
            this.first.clear();
            this.second.cancel();
            this.second.clear();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqual.EqualCoordinatorHelper
        public void drain() {
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            do {
                SimpleQueue<T> simpleQueue = this.first.queue;
                SimpleQueue<T> simpleQueue2 = this.second.queue;
                if (simpleQueue != null && simpleQueue2 != null) {
                    while (!isCancelled()) {
                        if (this.errors.get() != null) {
                            cancelAndClear();
                            this.errors.tryTerminateConsumer(this.downstream);
                            return;
                        }
                        boolean z = this.first.done;
                        T tPoll = this.f823v1;
                        if (tPoll == null) {
                            try {
                                tPoll = simpleQueue.poll();
                                this.f823v1 = tPoll;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                cancelAndClear();
                                this.errors.tryAddThrowableOrReport(th);
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            }
                        }
                        boolean z2 = tPoll == null;
                        boolean z3 = this.second.done;
                        T tPoll2 = this.f824v2;
                        if (tPoll2 == null) {
                            try {
                                tPoll2 = simpleQueue2.poll();
                                this.f824v2 = tPoll2;
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                cancelAndClear();
                                this.errors.tryAddThrowableOrReport(th2);
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            }
                        }
                        boolean z4 = tPoll2 == null;
                        if (z && z3 && z2 && z4) {
                            complete(true);
                            return;
                        }
                        if (z && z3 && z2 != z4) {
                            cancelAndClear();
                            complete(false);
                            return;
                        }
                        if (!z2 && !z4) {
                            try {
                                if (!this.comparer.test(tPoll, tPoll2)) {
                                    cancelAndClear();
                                    complete(false);
                                    return;
                                } else {
                                    this.f823v1 = null;
                                    this.f824v2 = null;
                                    this.first.request();
                                    this.second.request();
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                cancelAndClear();
                                this.errors.tryAddThrowableOrReport(th3);
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            }
                        }
                    }
                    this.first.clear();
                    this.second.clear();
                    return;
                }
                if (isCancelled()) {
                    this.first.clear();
                    this.second.clear();
                    return;
                } else if (this.errors.get() != null) {
                    cancelAndClear();
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                }
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqual.EqualCoordinatorHelper
        public void innerError(Throwable t) {
            if (this.errors.tryAddThrowableOrReport(t)) {
                drain();
            }
        }
    }

    static final class EqualSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4804128302091633067L;
        volatile boolean done;
        final int limit;
        final EqualCoordinatorHelper parent;
        final int prefetch;
        long produced;
        volatile SimpleQueue<T> queue;
        int sourceMode;

        EqualSubscriber(EqualCoordinatorHelper parent, int prefetch) {
            this.parent = parent;
            this.limit = prefetch - (prefetch >> 2);
            this.prefetch = prefetch;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.setOnce(this, s)) {
                if (s instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        s.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                s.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 0 && !this.queue.offer(t)) {
                onError(new MissingBackpressureException());
            } else {
                this.parent.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            this.parent.innerError(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        public void request() {
            if (this.sourceMode != 1) {
                long j = this.produced + 1;
                if (j >= this.limit) {
                    this.produced = 0L;
                    get().request(j);
                } else {
                    this.produced = j;
                }
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        void clear() {
            SimpleQueue<T> simpleQueue = this.queue;
            if (simpleQueue != null) {
                simpleQueue.clear();
            }
        }
    }
}
