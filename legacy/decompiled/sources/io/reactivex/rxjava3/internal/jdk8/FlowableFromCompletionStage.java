package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public final class FlowableFromCompletionStage<T> extends Flowable<T> {
    final CompletionStage<T> stage;

    public FlowableFromCompletionStage(CompletionStage<T> stage) {
        this.stage = stage;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> s) {
        BiConsumerAtomicReference biConsumerAtomicReference = new BiConsumerAtomicReference();
        CompletionStageHandler completionStageHandler = new CompletionStageHandler(s, biConsumerAtomicReference);
        biConsumerAtomicReference.lazySet(completionStageHandler);
        s.onSubscribe(completionStageHandler);
        this.stage.whenComplete(biConsumerAtomicReference);
    }

    static final class CompletionStageHandler<T> extends DeferredScalarSubscription<T> implements BiConsumer<T, Throwable> {
        private static final long serialVersionUID = 4665335664328839859L;
        final BiConsumerAtomicReference<T> whenReference;

        CompletionStageHandler(Subscriber<? super T> downstream, BiConsumerAtomicReference<T> whenReference) {
            super(downstream);
            this.whenReference = whenReference;
        }

        @Override // java.util.function.BiConsumer
        public void accept(T item, Throwable error) {
            if (error != null) {
                this.downstream.onError(error);
            } else if (item != null) {
                complete(item);
            } else {
                this.downstream.onError(new NullPointerException("The CompletionStage terminated with null."));
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.whenReference.set(null);
        }
    }

    static final class BiConsumerAtomicReference<T> extends AtomicReference<BiConsumer<T, Throwable>> implements BiConsumer<T, Throwable> {
        private static final long serialVersionUID = 45838553147237545L;

        BiConsumerAtomicReference() {
        }

        @Override // java.util.function.BiConsumer
        public void accept(T t, Throwable u) {
            BiConsumer<T, Throwable> biConsumer = get();
            if (biConsumer != null) {
                biConsumer.accept(t, u);
            }
        }
    }
}
