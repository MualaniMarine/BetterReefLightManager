package io.reactivex.rxjava3.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {
    Subscription upstream;

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription s) {
        if (EndConsumerHelper.validate(this.upstream, s, getClass())) {
            this.upstream = s;
            onStart();
        }
    }

    protected final void request(long n) {
        Subscription subscription = this.upstream;
        if (subscription != null) {
            subscription.request(n);
        }
    }

    protected final void cancel() {
        Subscription subscription = this.upstream;
        this.upstream = SubscriptionHelper.CANCELLED;
        subscription.cancel();
    }

    protected void onStart() {
        request(LongCompanionObject.MAX_VALUE);
    }
}
