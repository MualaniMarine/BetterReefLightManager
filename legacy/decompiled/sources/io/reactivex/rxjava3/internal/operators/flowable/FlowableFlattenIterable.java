package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableFlattenIterable<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;

    public FlowableFlattenIterable(Flowable<T> source, Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch) {
        super(source);
        this.mapper = mapper;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        if (this.source instanceof Supplier) {
            try {
                Object obj = ((Supplier) this.source).get();
                if (obj == null) {
                    EmptySubscription.complete(subscriber);
                    return;
                }
                try {
                    FlowableFromIterable.subscribe(subscriber, this.mapper.apply(obj).iterator());
                    return;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                    return;
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
                return;
            }
        }
        this.source.subscribe((FlowableSubscriber) new FlattenIterableSubscriber(subscriber, this.mapper, this.prefetch));
    }

    public static <T, R> Subscriber<T> subscribe(Subscriber<? super R> downstream, Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch) {
        return new FlattenIterableSubscriber(downstream, mapper, prefetch);
    }

    static final class FlattenIterableSubscriber<T, R> extends BasicIntQueueSubscription<R> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -3096000382929934955L;
        volatile boolean cancelled;
        int consumed;
        Iterator<? extends R> current;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        int fusionMode;
        final int limit;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;
        final int prefetch;
        SimpleQueue<T> queue;
        Subscription upstream;
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        FlattenIterableSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends Iterable<? extends R>> mapper, int prefetch) {
            this.downstream = actual;
            this.mapper = mapper;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        s.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
                s.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.fusionMode == 0 && !this.queue.offer(t)) {
                onError(new MissingBackpressureException("Queue is full?!"));
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            if (!this.done && ExceptionHelper.addThrowable(this.error, t)) {
                this.done = true;
                drain();
            } else {
                RxJavaPlugins.onError(t);
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
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:69:0x0124 A[PHI: r6
  0x0124: PHI (r6v4 java.util.Iterator<? extends R>) = (r6v3 java.util.Iterator<? extends R>), (r6v6 java.util.Iterator<? extends R>) binds: [B:30:0x0080, B:67:0x0121] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drain() {
            /*
                Method dump skipped, instruction units count: 303
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableFlattenIterable.FlattenIterableSubscriber.drain():void");
        }

        void consumedOne(boolean enabled) {
            if (enabled) {
                int i = this.consumed + 1;
                if (i == this.limit) {
                    this.consumed = 0;
                    this.upstream.request(i);
                } else {
                    this.consumed = i;
                }
            }
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, SimpleQueue<?> q) {
            if (this.cancelled) {
                this.current = null;
                q.clear();
                return true;
            }
            if (!d) {
                return false;
            }
            if (this.error.get() == null) {
                if (!empty) {
                    return false;
                }
                a.onComplete();
                return true;
            }
            Throwable thTerminate = ExceptionHelper.terminate(this.error);
            this.current = null;
            q.clear();
            a.onError(thTerminate);
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.current = null;
            this.queue.clear();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.current == null && this.queue.isEmpty();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public R poll() throws Throwable {
            Iterator<? extends R> it = this.current;
            while (true) {
                if (it == null) {
                    T tPoll = this.queue.poll();
                    if (tPoll != null) {
                        it = this.mapper.apply(tPoll).iterator();
                        if (it.hasNext()) {
                            this.current = it;
                            break;
                        }
                        it = null;
                    } else {
                        return null;
                    }
                } else {
                    break;
                }
            }
            R r = (R) Objects.requireNonNull(it.next(), "The iterator returned a null value");
            if (!it.hasNext()) {
                this.current = null;
            }
            return r;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int requestedMode) {
            return ((requestedMode & 1) == 0 || this.fusionMode != 1) ? 0 : 1;
        }
    }
}
