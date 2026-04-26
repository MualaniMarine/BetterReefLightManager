package io.reactivex.rxjava3.internal.fuseable;

import io.reactivex.rxjava3.core.ObservableSource;

/* JADX INFO: loaded from: classes.dex */
public interface HasUpstreamObservableSource<T> {
    ObservableSource<T> source();
}
