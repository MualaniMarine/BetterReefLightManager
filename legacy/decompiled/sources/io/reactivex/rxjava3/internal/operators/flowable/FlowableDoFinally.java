package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableDoFinally<T> extends AbstractFlowableWithUpstream<T, T> {
    final Action onFinally;

    public FlowableDoFinally(Flowable<T> source, Action onFinally) {
        super(source);
        this.onFinally = onFinally;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> s) {
        if (s instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new DoFinallyConditionalSubscriber((ConditionalSubscriber) s, this.onFinally));
        } else {
            this.source.subscribe((FlowableSubscriber) new DoFinallySubscriber(s, this.onFinally));
        }
    }

    static final class DoFinallySubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final Subscriber<? super T> downstream;
        final Action onFinally;

        /* JADX INFO: renamed from: qs */
        QueueSubscription<T> f814qs;
        boolean syncFused;
        Subscription upstream;

        DoFinallySubscriber(Subscriber<? super T> actual, Action onFinally) {
            this.downstream = actual;
            this.onFinally = onFinally;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof QueueSubscription) {
                    this.f814qs = (QueueSubscription) s;
                }
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            this.downstream.onError(t);
            runFinally();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
            runFinally();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            runFinally();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n) {
            this.upstream.request(n);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int mode) {
            QueueSubscription<T> queueSubscription = this.f814qs;
            if (queueSubscription == null || (mode & 4) != 0) {
                return 0;
            }
            int iRequestFusion = queueSubscription.requestFusion(mode);
            if (iRequestFusion != 0) {
                this.syncFused = iRequestFusion == 1;
            }
            return iRequestFusion;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.f814qs.clear();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.f814qs.isEmpty();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public T poll() throws Throwable {
            T tPoll = this.f814qs.poll();
            if (tPoll == null && this.syncFused) {
                runFinally();
            }
            return tPoll;
        }

        void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    static final class DoFinallyConditionalSubscriber<T> extends BasicIntQueueSubscription<T> implements ConditionalSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final ConditionalSubscriber<? super T> downstream;
        final Action onFinally;

        /* JADX INFO: renamed from: qs */
        QueueSubscription<T> f813qs;
        boolean syncFused;
        Subscription upstream;

        DoFinallyConditionalSubscriber(ConditionalSubscriber<? super T> actual, Action onFinally) {
            this.downstream = actual;
            this.onFinally = onFinally;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof QueueSubscription) {
                    this.f813qs = (QueueSubscription) s;
                }
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            return this.downstream.tryOnNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            this.downstream.onError(t);
            runFinally();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
            runFinally();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            runFinally();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n) {
            this.upstream.request(n);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int mode) {
            QueueSubscription<T> queueSubscription = this.f813qs;
            if (queueSubscription == null || (mode & 4) != 0) {
                return 0;
            }
            int iRequestFusion = queueSubscription.requestFusion(mode);
            if (iRequestFusion != 0) {
                this.syncFused = iRequestFusion == 1;
            }
            return iRequestFusion;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.f813qs.clear();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.f813qs.isEmpty();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public T poll() throws Throwable {
            T tPoll = this.f813qs.poll();
            if (tPoll == null && this.syncFused) {
                runFinally();
            }
            return tPoll;
        }

        void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }
}
