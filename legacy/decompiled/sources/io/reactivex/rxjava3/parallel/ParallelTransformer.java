package io.reactivex.rxjava3.parallel;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ParallelTransformer<Upstream, Downstream> {
    ParallelFlowable<Downstream> apply(ParallelFlowable<Upstream> upstream);
}
