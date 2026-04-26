package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
final class InstantPeriodicTask implements Callable<Void>, Disposable {
    static final FutureTask<Void> CANCELLED = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
    final ExecutorService executor;
    final AtomicReference<Future<?>> first = new AtomicReference<>();
    final AtomicReference<Future<?>> rest = new AtomicReference<>();
    Thread runner;
    final Runnable task;

    InstantPeriodicTask(Runnable task, ExecutorService executor) {
        this.task = task;
        this.executor = executor;
    }

    @Override // java.util.concurrent.Callable
    public Void call() {
        this.runner = Thread.currentThread();
        try {
            this.task.run();
            setRest(this.executor.submit(this));
            this.runner = null;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            this.runner = null;
            RxJavaPlugins.onError(th);
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Future<?> andSet = this.first.getAndSet(CANCELLED);
        if (andSet != null && andSet != CANCELLED) {
            andSet.cancel(this.runner != Thread.currentThread());
        }
        Future<?> andSet2 = this.rest.getAndSet(CANCELLED);
        if (andSet2 == null || andSet2 == CANCELLED) {
            return;
        }
        andSet2.cancel(this.runner != Thread.currentThread());
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.first.get() == CANCELLED;
    }

    void setFirst(Future<?> f) {
        Future<?> future;
        do {
            future = this.first.get();
            if (future == CANCELLED) {
                f.cancel(this.runner != Thread.currentThread());
                return;
            }
        } while (!this.first.compareAndSet(future, f));
    }

    void setRest(Future<?> f) {
        Future<?> future;
        do {
            future = this.rest.get();
            if (future == CANCELLED) {
                f.cancel(this.runner != Thread.currentThread());
                return;
            }
        } while (!this.rest.compareAndSet(future, f));
    }
}
