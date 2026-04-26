package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.jdk8.FlowableFromCompletionStage;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
public final class MaybeFromCompletionStage<T> extends Maybe<T> {
    final CompletionStage<T> stage;

    public MaybeFromCompletionStage(CompletionStage<T> stage) {
        this.stage = stage;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> observer) {
        FlowableFromCompletionStage.BiConsumerAtomicReference biConsumerAtomicReference = new FlowableFromCompletionStage.BiConsumerAtomicReference();
        CompletionStageHandler completionStageHandler = new CompletionStageHandler(observer, biConsumerAtomicReference);
        biConsumerAtomicReference.lazySet(completionStageHandler);
        observer.onSubscribe(completionStageHandler);
        this.stage.whenComplete(biConsumerAtomicReference);
    }

    static final class CompletionStageHandler<T> implements Disposable, BiConsumer<T, Throwable> {
        final MaybeObserver<? super T> downstream;
        final FlowableFromCompletionStage.BiConsumerAtomicReference<T> whenReference;

        CompletionStageHandler(MaybeObserver<? super T> downstream, FlowableFromCompletionStage.BiConsumerAtomicReference<T> whenReference) {
            this.downstream = downstream;
            this.whenReference = whenReference;
        }

        @Override // java.util.function.BiConsumer
        public void accept(T item, Throwable error) {
            if (error != null) {
                this.downstream.onError(error);
            } else if (item != null) {
                this.downstream.onSuccess(item);
            } else {
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.whenReference.set(null);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.whenReference.get() == null;
        }
    }
}
