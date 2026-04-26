package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableFlatMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends U>> mapper;
    final int maxConcurrency;

    public FlowableFlatMap(Flowable<T> source, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        super(source);
        this.mapper = mapper;
        this.delayErrors = delayErrors;
        this.maxConcurrency = maxConcurrency;
        this.bufferSize = bufferSize;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super U> s) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, s, this.mapper)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) subscribe(s, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> s, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        return new MergeSubscriber(s, mapper, delayErrors, maxConcurrency, bufferSize);
    }

    static final class MergeSubscriber<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super U> downstream;
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends Publisher<? extends U>> mapper;
        final int maxConcurrency;
        volatile SimplePlainQueue<U> queue;
        int scalarEmitted;
        final int scalarLimit;
        long uniqueId;
        Subscription upstream;
        static final InnerSubscriber<?, ?>[] EMPTY = new InnerSubscriber[0];
        static final InnerSubscriber<?, ?>[] CANCELLED = new InnerSubscriber[0];
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicReference<InnerSubscriber<?, ?>[]> subscribers = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        MergeSubscriber(Subscriber<? super U> actual, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
            this.downstream = actual;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
            this.maxConcurrency = maxConcurrency;
            this.bufferSize = bufferSize;
            this.scalarLimit = Math.max(1, maxConcurrency >> 1);
            this.subscribers.lazySet(EMPTY);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                int i = this.maxConcurrency;
                if (i == Integer.MAX_VALUE) {
                    s.request(LongCompanionObject.MAX_VALUE);
                } else {
                    s.request(i);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                if (publisher instanceof Supplier) {
                    try {
                        Object obj = ((Supplier) publisher).get();
                        if (obj != null) {
                            tryEmitScalar(obj);
                            return;
                        }
                        if (this.maxConcurrency == Integer.MAX_VALUE || this.cancelled) {
                            return;
                        }
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.errors.tryAddThrowableOrReport(th);
                        drain();
                        return;
                    }
                }
                int i3 = this.bufferSize;
                long j = this.uniqueId;
                this.uniqueId = 1 + j;
                InnerSubscriber innerSubscriber = new InnerSubscriber(this, i3, j);
                if (addInner(innerSubscriber)) {
                    publisher.subscribe(innerSubscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.cancel();
                onError(th2);
            }
        }

        boolean addInner(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                if (innerSubscriberArr == CANCELLED) {
                    innerSubscriber.dispose();
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, (InnerSubscriber<?, ?>[]) innerSubscriberArr2));
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        void removeInner(InnerSubscriber<T, U> inner) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber<?, ?>[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (length == 0) {
                    return;
                }
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (innerSubscriberArr[i2] == inner) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    innerSubscriberArr2 = EMPTY;
                } else {
                    InnerSubscriber<?, ?>[] innerSubscriberArr3 = new InnerSubscriber[length - 1];
                    System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i);
                    System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr3, i, (length - i) - 1);
                    innerSubscriberArr2 = innerSubscriberArr3;
                }
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
        }

        SimpleQueue<U> getMainQueue() {
            SimplePlainQueue<U> spscArrayQueue = this.queue;
            if (spscArrayQueue == null) {
                if (this.maxConcurrency == Integer.MAX_VALUE) {
                    spscArrayQueue = new SpscLinkedArrayQueue<>(this.bufferSize);
                } else {
                    spscArrayQueue = new SpscArrayQueue<>(this.maxConcurrency);
                }
                this.queue = spscArrayQueue;
            }
            return spscArrayQueue;
        }

        void tryEmitScalar(U value) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j = this.requested.get();
                SimpleQueue<U> mainQueue = this.queue;
                if (j != 0 && (mainQueue == null || mainQueue.isEmpty())) {
                    this.downstream.onNext(value);
                    if (j != LongCompanionObject.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                        }
                    }
                } else {
                    if (mainQueue == null) {
                        mainQueue = getMainQueue();
                    }
                    if (!mainQueue.offer(value)) {
                        onError(new MissingBackpressureException("Scalar queue full?!"));
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!getMainQueue().offer(value)) {
                onError(new MissingBackpressureException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        void tryEmit(U value, InnerSubscriber<T, U> inner) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j = this.requested.get();
                SimpleQueue spscArrayQueue = inner.queue;
                if (j != 0 && (spscArrayQueue == null || spscArrayQueue.isEmpty())) {
                    this.downstream.onNext(value);
                    if (j != LongCompanionObject.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    inner.requestMore(1L);
                } else {
                    if (spscArrayQueue == null) {
                        spscArrayQueue = new SpscArrayQueue(this.bufferSize);
                        inner.queue = spscArrayQueue;
                    }
                    if (!spscArrayQueue.offer(value)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue spscArrayQueue2 = inner.queue;
                if (spscArrayQueue2 == null) {
                    spscArrayQueue2 = new SpscArrayQueue(this.bufferSize);
                    inner.queue = spscArrayQueue2;
                }
                if (!spscArrayQueue2.offer(value)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            if (this.errors.tryAddThrowableOrReport(t)) {
                this.done = true;
                if (!this.delayErrors) {
                    for (InnerSubscriber<?, ?> innerSubscriber : this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber.dispose();
                    }
                }
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this.requested, n);
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SimplePlainQueue<U> simplePlainQueue;
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            disposeAll();
            if (getAndIncrement() != 0 || (simplePlainQueue = this.queue) == null) {
                return;
            }
            simplePlainQueue.clear();
        }

        void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void drainLoop() {
            long j;
            long j2;
            long j3;
            boolean z;
            int i;
            int i2;
            long j4;
            long j5;
            Subscriber<? super U> subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!checkTerminate()) {
                SimplePlainQueue<U> simplePlainQueue = this.queue;
                long jAddAndGet = this.requested.get();
                boolean z2 = jAddAndGet == LongCompanionObject.MAX_VALUE;
                long j6 = 0;
                if (simplePlainQueue != null) {
                    long j7 = 0;
                    j = 0;
                    while (jAddAndGet != 0) {
                        U uPoll = simplePlainQueue.poll();
                        if (checkTerminate()) {
                            return;
                        }
                        if (uPoll == null) {
                            break;
                        }
                        subscriber.onNext(uPoll);
                        j++;
                        j7++;
                        jAddAndGet--;
                    }
                    if (j7 != 0) {
                        jAddAndGet = z2 ? LongCompanionObject.MAX_VALUE : this.requested.addAndGet(-j7);
                    }
                } else {
                    j = 0;
                }
                boolean z3 = this.done;
                SimplePlainQueue<U> simplePlainQueue2 = this.queue;
                InnerSubscriber<?, ?>[] innerSubscriberArr = this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (z3 && ((simplePlainQueue2 == null || simplePlainQueue2.isEmpty()) && length == 0)) {
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                }
                int i3 = iAddAndGet;
                if (length != 0) {
                    long j8 = this.lastId;
                    int i4 = this.lastIndex;
                    if (length <= i4 || innerSubscriberArr[i4].f815id != j8) {
                        if (length <= i4) {
                            i4 = 0;
                        }
                        for (int i5 = 0; i5 < length && innerSubscriberArr[i4].f815id != j8; i5++) {
                            i4++;
                            if (i4 == length) {
                                i4 = 0;
                            }
                        }
                        this.lastIndex = i4;
                        this.lastId = innerSubscriberArr[i4].f815id;
                    }
                    int i6 = i4;
                    boolean z4 = false;
                    int i7 = 0;
                    while (true) {
                        if (i7 >= length) {
                            z = z4;
                            break;
                        }
                        if (checkTerminate()) {
                            return;
                        }
                        InnerSubscriber<T, U> innerSubscriber = innerSubscriberArr[i6];
                        Object obj = null;
                        while (true) {
                            SimpleQueue<U> simpleQueue = innerSubscriber.queue;
                            if (simpleQueue == null) {
                                i = length;
                                break;
                            }
                            i = length;
                            Object obj2 = obj;
                            long j9 = j6;
                            while (true) {
                                if (jAddAndGet == j6) {
                                    j4 = j6;
                                    break;
                                }
                                if (checkTerminate()) {
                                    return;
                                }
                                try {
                                    U uPoll2 = simpleQueue.poll();
                                    if (uPoll2 == null) {
                                        obj2 = uPoll2;
                                        j4 = 0;
                                        break;
                                    } else {
                                        subscriber.onNext(uPoll2);
                                        jAddAndGet--;
                                        j9++;
                                        obj2 = uPoll2;
                                        j6 = 0;
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    innerSubscriber.dispose();
                                    this.errors.tryAddThrowableOrReport(th);
                                    if (!this.delayErrors) {
                                        this.upstream.cancel();
                                    }
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    removeInner(innerSubscriber);
                                    i7++;
                                    i2 = i;
                                    z4 = true;
                                }
                            }
                            if (j9 != j4) {
                                jAddAndGet = !z2 ? this.requested.addAndGet(-j9) : LongCompanionObject.MAX_VALUE;
                                innerSubscriber.requestMore(j9);
                                j5 = 0;
                            } else {
                                j5 = j4;
                            }
                            if (jAddAndGet == j5 || obj2 == null) {
                                break;
                            }
                            length = i;
                            obj = obj2;
                            j6 = 0;
                        }
                        boolean z5 = innerSubscriber.done;
                        SimpleQueue<U> simpleQueue2 = innerSubscriber.queue;
                        if (z5 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                            removeInner(innerSubscriber);
                            if (checkTerminate()) {
                                return;
                            }
                            j++;
                            z4 = true;
                        }
                        if (jAddAndGet == 0) {
                            z = z4;
                            break;
                        }
                        i6++;
                        i2 = i;
                        if (i6 == i2) {
                            i6 = 0;
                        }
                        i7++;
                        length = i2;
                        j6 = 0;
                    }
                    this.lastIndex = i6;
                    this.lastId = innerSubscriberArr[i6].f815id;
                    j3 = j;
                    j2 = 0;
                } else {
                    j2 = 0;
                    j3 = j;
                    z = false;
                }
                if (j3 != j2 && !this.cancelled) {
                    this.upstream.request(j3);
                }
                if (z) {
                    iAddAndGet = i3;
                } else {
                    iAddAndGet = addAndGet(-i3);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        boolean checkTerminate() {
            if (this.cancelled) {
                clearScalarQueue();
                return true;
            }
            if (this.delayErrors || this.errors.get() == null) {
                return false;
            }
            clearScalarQueue();
            this.errors.tryTerminateConsumer(this.downstream);
            return true;
        }

        void clearScalarQueue() {
            SimplePlainQueue<U> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        void disposeAll() {
            InnerSubscriber<?, ?>[] andSet = this.subscribers.getAndSet(CANCELLED);
            if (andSet != CANCELLED) {
                for (InnerSubscriber<?, ?> innerSubscriber : andSet) {
                    innerSubscriber.dispose();
                }
                this.errors.tryTerminateAndReport();
            }
        }

        void innerError(InnerSubscriber<T, U> inner, Throwable t) {
            if (this.errors.tryAddThrowableOrReport(t)) {
                inner.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (InnerSubscriber<?, ?> innerSubscriber : this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber.dispose();
                    }
                }
                drain();
            }
        }
    }

    static final class InnerSubscriber<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -4606175640614850599L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;

        /* JADX INFO: renamed from: id */
        final long f815id;
        final int limit;
        final MergeSubscriber<T, U> parent;
        long produced;
        volatile SimpleQueue<U> queue;

        InnerSubscriber(MergeSubscriber<T, U> parent, int bufferSize, long id) {
            this.f815id = id;
            this.parent = parent;
            this.bufferSize = bufferSize;
            this.limit = bufferSize >> 2;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.setOnce(this, s)) {
                if (s instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                    }
                }
                s.request(this.bufferSize);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U t) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(t, this);
            } else {
                this.parent.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.parent.innerError(this, t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        void requestMore(long n) {
            if (this.fusionMode != 1) {
                long j = this.produced + n;
                if (j >= this.limit) {
                    this.produced = 0L;
                    get().request(j);
                } else {
                    this.produced = j;
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }
    }
}
