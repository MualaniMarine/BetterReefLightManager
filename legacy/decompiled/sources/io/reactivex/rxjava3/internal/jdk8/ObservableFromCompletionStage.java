package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
public final class ObservableFromCompletionStage<T> extends Observable<T> {
    final CompletionStage<T> stage;

    public ObservableFromCompletionStage(CompletionStage<T> stage) {
        this.stage = stage;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        BiConsumerAtomicReference biConsumerAtomicReference = new BiConsumerAtomicReference();
        CompletionStageHandler completionStageHandler = new CompletionStageHandler(observer, biConsumerAtomicReference);
        biConsumerAtomicReference.lazySet(completionStageHandler);
        observer.onSubscribe(completionStageHandler);
        this.stage.whenComplete(biConsumerAtomicReference);
    }

    static final class CompletionStageHandler<T> extends DeferredScalarDisposable<T> implements BiConsumer<T, Throwable> {
        private static final long serialVersionUID = 4665335664328839859L;
        final BiConsumerAtomicReference<T> whenReference;

        CompletionStageHandler(Observer<? super T> downstream, BiConsumerAtomicReference<T> whenReference) {
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

        @Override // io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable, io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            super.dispose();
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
