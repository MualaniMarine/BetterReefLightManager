package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableTake<T> extends AbstractFlowableWithUpstream<T, T> {

    /* JADX INFO: renamed from: n */
    final long f829n;

    public FlowableTake(Flowable<T> source, long n) {
        super(source);
        this.f829n = n;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> s) {
        this.source.subscribe((FlowableSubscriber) new TakeSubscriber(s, this.f829n));
    }

    static final class TakeSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 2288246011222124525L;
        final Subscriber<? super T> downstream;
        long remaining;
        Subscription upstream;

        TakeSubscriber(Subscriber<? super T> actual, long remaining) {
            this.downstream = actual;
            this.remaining = remaining;
            lazySet(remaining);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                if (this.remaining == 0) {
                    s.cancel();
                    EmptySubscription.complete(this.downstream);
                } else {
                    this.upstream = s;
                    this.downstream.onSubscribe(this);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.remaining;
            if (j > 0) {
                long j2 = j - 1;
                this.remaining = j2;
                this.downstream.onNext(t);
                if (j2 == 0) {
                    this.upstream.cancel();
                    this.downstream.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t) {
            if (this.remaining > 0) {
                this.remaining = 0L;
                this.downstream.onError(t);
            } else {
                RxJavaPlugins.onError(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.remaining > 0) {
                this.remaining = 0L;
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n) {
            long j;
            long jMin;
            if (SubscriptionHelper.validate(n)) {
                do {
                    j = get();
                    if (j == 0) {
                        return;
                    } else {
                        jMin = Math.min(j, n);
                    }
                } while (!compareAndSet(j, j - jMin));
                this.upstream.request(jMin);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }
    }
}
