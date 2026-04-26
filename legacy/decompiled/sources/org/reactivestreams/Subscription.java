package org.reactivestreams;

/* JADX INFO: loaded from: classes.dex */
public interface Subscription {
    void cancel();

    void request(long j);
}
