package io.reactivex.rxjava3.internal.subscriptions;

import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes.dex */
public class SubscriptionArbiter extends AtomicInteger implements Subscription {
    private static final long serialVersionUID = -2189523197179400958L;
    Subscription actual;
    final boolean cancelOnReplace;
    volatile boolean cancelled;
    long requested;
    protected boolean unbounded;
    final AtomicReference<Subscription> missedSubscription = new AtomicReference<>();
    final AtomicLong missedRequested = new AtomicLong();
    final AtomicLong missedProduced = new AtomicLong();

    public SubscriptionArbiter(boolean cancelOnReplace) {
        this.cancelOnReplace = cancelOnReplace;
    }

    public final void setSubscription(Subscription s) {
        if (this.cancelled) {
            s.cancel();
            return;
        }
        Objects.requireNonNull(s, "s is null");
        if (get() == 0 && compareAndSet(0, 1)) {
            Subscription subscription = this.actual;
            if (subscription != null && this.cancelOnReplace) {
                subscription.cancel();
            }
            this.actual = s;
            long j = this.requested;
            if (decrementAndGet() != 0) {
                drainLoop();
            }
            if (j != 0) {
                s.request(j);
                return;
            }
            return;
        }
        Subscription andSet = this.missedSubscription.getAndSet(s);
        if (andSet != null && this.cancelOnReplace) {
            andSet.cancel();
        }
        drain();
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long n) {
        if (!SubscriptionHelper.validate(n) || this.unbounded) {
            return;
        }
        if (get() == 0 && compareAndSet(0, 1)) {
            long j = this.requested;
            if (j != LongCompanionObject.MAX_VALUE) {
                long jAddCap = BackpressureHelper.addCap(j, n);
                this.requested = jAddCap;
                if (jAddCap == LongCompanionObject.MAX_VALUE) {
                    this.unbounded = true;
                }
            }
            Subscription subscription = this.actual;
            if (decrementAndGet() != 0) {
                drainLoop();
            }
            if (subscription != null) {
                subscription.request(n);
                return;
            }
            return;
        }
        BackpressureHelper.add(this.missedRequested, n);
        drain();
    }

    public final void produced(long n) {
        if (this.unbounded) {
            return;
        }
        if (get() == 0 && compareAndSet(0, 1)) {
            long j = this.requested;
            if (j != LongCompanionObject.MAX_VALUE) {
                long j2 = j - n;
                if (j2 < 0) {
                    SubscriptionHelper.reportMoreProduced(j2);
                    j2 = 0;
                }
                this.requested = j2;
            }
            if (decrementAndGet() == 0) {
                return;
            }
            drainLoop();
            return;
        }
        BackpressureHelper.add(this.missedProduced, n);
        drain();
    }

    public void cancel() {
        if (this.cancelled) {
            return;
        }
        this.cancelled = true;
        drain();
    }

    final void drain() {
        if (getAndIncrement() != 0) {
            return;
        }
        drainLoop();
    }

    final void drainLoop() {
        int iAddAndGet = 1;
        Subscription subscription = null;
        long jAddCap = 0;
        do {
            Subscription andSet = this.missedSubscription.get();
            if (andSet != null) {
                andSet = this.missedSubscription.getAndSet(null);
            }
            long andSet2 = this.missedRequested.get();
            if (andSet2 != 0) {
                andSet2 = this.missedRequested.getAndSet(0L);
            }
            long andSet3 = this.missedProduced.get();
            if (andSet3 != 0) {
                andSet3 = this.missedProduced.getAndSet(0L);
            }
            Subscription subscription2 = this.actual;
            if (this.cancelled) {
                if (subscription2 != null) {
                    subscription2.cancel();
                    this.actual = null;
                }
                if (andSet != null) {
                    andSet.cancel();
                }
            } else {
                long jAddCap2 = this.requested;
                if (jAddCap2 != LongCompanionObject.MAX_VALUE) {
                    jAddCap2 = BackpressureHelper.addCap(jAddCap2, andSet2);
                    if (jAddCap2 != LongCompanionObject.MAX_VALUE) {
                        jAddCap2 -= andSet3;
                        if (jAddCap2 < 0) {
                            SubscriptionHelper.reportMoreProduced(jAddCap2);
                            jAddCap2 = 0;
                        }
                    }
                    this.requested = jAddCap2;
                }
                if (andSet != null) {
                    if (subscription2 != null && this.cancelOnReplace) {
                        subscription2.cancel();
                    }
                    this.actual = andSet;
                    if (jAddCap2 != 0) {
                        jAddCap = BackpressureHelper.addCap(jAddCap, jAddCap2);
                        subscription = andSet;
                    }
                } else if (subscription2 != null && andSet2 != 0) {
                    jAddCap = BackpressureHelper.addCap(jAddCap, andSet2);
                    subscription = subscription2;
                }
            }
            iAddAndGet = addAndGet(-iAddAndGet);
        } while (iAddAndGet != 0);
        if (jAddCap != 0) {
            subscription.request(jAddCap);
        }
    }

    public final boolean isUnbounded() {
        return this.unbounded;
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }
}
