package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ObservableTransformer<Upstream, Downstream> {
    ObservableSource<Downstream> apply(Observable<Upstream> upstream);
}
