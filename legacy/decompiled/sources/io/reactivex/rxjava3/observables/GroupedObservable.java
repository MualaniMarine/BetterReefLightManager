package io.reactivex.rxjava3.observables;

import io.reactivex.rxjava3.core.Observable;

/* JADX INFO: loaded from: classes.dex */
public abstract class GroupedObservable<K, T> extends Observable<T> {
    final K key;

    protected GroupedObservable(K key) {
        this.key = key;
    }

    public K getKey() {
        return this.key;
    }
}
