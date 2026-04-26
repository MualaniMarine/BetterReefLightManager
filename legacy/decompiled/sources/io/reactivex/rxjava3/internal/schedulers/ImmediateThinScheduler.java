package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class ImmediateThinScheduler extends Scheduler {
    static final Disposable DISPOSED;
    public static final Scheduler INSTANCE = new ImmediateThinScheduler();
    static final Scheduler.Worker WORKER = new ImmediateThinWorker();

    static {
        Disposable disposableEmpty = Disposable.CC.empty();
        DISPOSED = disposableEmpty;
        disposableEmpty.dispose();
    }

    private ImmediateThinScheduler() {
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Disposable scheduleDirect(Runnable run) {
        run.run();
        return DISPOSED;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Disposable schedulePeriodicallyDirect(Runnable run, long initialDelay, long period, TimeUnit unit) {
        throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Scheduler.Worker createWorker() {
        return WORKER;
    }

    static final class ImmediateThinWorker extends Scheduler.Worker {
        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return false;
        }

        ImmediateThinWorker() {
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable run) {
            run.run();
            return ImmediateThinScheduler.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable run, long delay, TimeUnit unit) {
            throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedulePeriodically(Runnable run, long initialDelay, long period, TimeUnit unit) {
            throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
        }
    }
}
