package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.schedulers.NewThreadWorker;
import io.reactivex.rxjava3.internal.schedulers.SchedulerWhen;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class Scheduler {
    static final long CLOCK_DRIFT_TOLERANCE_NANOSECONDS = TimeUnit.MINUTES.toNanos(Long.getLong("rx3.scheduler.drift-tolerance", 15).longValue());

    public abstract Worker createWorker();

    public void shutdown() {
    }

    public void start() {
    }

    public static long clockDriftTolerance() {
        return CLOCK_DRIFT_TOLERANCE_NANOSECONDS;
    }

    public long now(TimeUnit unit) {
        return unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public Disposable scheduleDirect(Runnable run) {
        return scheduleDirect(run, 0L, TimeUnit.NANOSECONDS);
    }

    public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
        Worker workerCreateWorker = createWorker();
        DisposeTask disposeTask = new DisposeTask(RxJavaPlugins.onSchedule(run), workerCreateWorker);
        workerCreateWorker.schedule(disposeTask, delay, unit);
        return disposeTask;
    }

    public Disposable schedulePeriodicallyDirect(Runnable run, long initialDelay, long period, TimeUnit unit) {
        Worker workerCreateWorker = createWorker();
        PeriodicDirectTask periodicDirectTask = new PeriodicDirectTask(RxJavaPlugins.onSchedule(run), workerCreateWorker);
        Disposable disposableSchedulePeriodically = workerCreateWorker.schedulePeriodically(periodicDirectTask, initialDelay, period, unit);
        return disposableSchedulePeriodically == EmptyDisposable.INSTANCE ? disposableSchedulePeriodically : periodicDirectTask;
    }

    public <S extends Scheduler & Disposable> S when(Function<Flowable<Flowable<Completable>>, Completable> combine) {
        Objects.requireNonNull(combine, "combine is null");
        return new SchedulerWhen(combine, this);
    }

    public static abstract class Worker implements Disposable {
        public abstract Disposable schedule(Runnable run, long delay, TimeUnit unit);

        public Disposable schedule(Runnable run) {
            return schedule(run, 0L, TimeUnit.NANOSECONDS);
        }

        public Disposable schedulePeriodically(Runnable run, final long initialDelay, final long period, final TimeUnit unit) {
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            Runnable runnableOnSchedule = RxJavaPlugins.onSchedule(run);
            long nanos = unit.toNanos(period);
            long jNow = now(TimeUnit.NANOSECONDS);
            Disposable disposableSchedule = schedule(new PeriodicTask(jNow + unit.toNanos(initialDelay), runnableOnSchedule, jNow, sequentialDisposable2, nanos), initialDelay, unit);
            if (disposableSchedule == EmptyDisposable.INSTANCE) {
                return disposableSchedule;
            }
            sequentialDisposable.replace(disposableSchedule);
            return sequentialDisposable2;
        }

        public long now(TimeUnit unit) {
            return unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        final class PeriodicTask implements Runnable, SchedulerRunnableIntrospection {
            long count;
            final Runnable decoratedRun;
            long lastNowNanoseconds;
            final long periodInNanoseconds;

            /* JADX INFO: renamed from: sd */
            final SequentialDisposable f783sd;
            long startInNanoseconds;

            PeriodicTask(long firstStartInNanoseconds, Runnable decoratedRun, long firstNowNanoseconds, SequentialDisposable sd, long periodInNanoseconds) {
                this.decoratedRun = decoratedRun;
                this.f783sd = sd;
                this.periodInNanoseconds = periodInNanoseconds;
                this.lastNowNanoseconds = firstNowNanoseconds;
                this.startInNanoseconds = firstStartInNanoseconds;
            }

            @Override // java.lang.Runnable
            public void run() {
                long j;
                this.decoratedRun.run();
                if (this.f783sd.isDisposed()) {
                    return;
                }
                long jNow = Worker.this.now(TimeUnit.NANOSECONDS);
                long j2 = Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS + jNow;
                long j3 = this.lastNowNanoseconds;
                if (j2 < j3 || jNow >= j3 + this.periodInNanoseconds + Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS) {
                    long j4 = this.periodInNanoseconds;
                    long j5 = jNow + j4;
                    long j6 = this.count + 1;
                    this.count = j6;
                    this.startInNanoseconds = j5 - (j4 * j6);
                    j = j5;
                } else {
                    long j7 = this.startInNanoseconds;
                    long j8 = this.count + 1;
                    this.count = j8;
                    j = j7 + (j8 * this.periodInNanoseconds);
                }
                this.lastNowNanoseconds = jNow;
                this.f783sd.replace(Worker.this.schedule(this, j - jNow, TimeUnit.NANOSECONDS));
            }

            @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
            public Runnable getWrappedRunnable() {
                return this.decoratedRun;
            }
        }
    }

    static final class PeriodicDirectTask implements Disposable, Runnable, SchedulerRunnableIntrospection {
        volatile boolean disposed;
        final Runnable run;
        final Worker worker;

        PeriodicDirectTask(Runnable run, Worker worker) {
            this.run = run;
            this.worker = worker;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.disposed) {
                return;
            }
            try {
                this.run.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.worker.dispose();
                throw ExceptionHelper.wrapOrThrow(th);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.worker.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.run;
        }
    }

    static final class DisposeTask implements Disposable, Runnable, SchedulerRunnableIntrospection {
        final Runnable decoratedRun;
        Thread runner;

        /* JADX INFO: renamed from: w */
        final Worker f782w;

        DisposeTask(Runnable decoratedRun, Worker w) {
            this.decoratedRun = decoratedRun;
            this.f782w = w;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.runner = Thread.currentThread();
            try {
                this.decoratedRun.run();
            } finally {
                dispose();
                this.runner = null;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.runner == Thread.currentThread()) {
                Worker worker = this.f782w;
                if (worker instanceof NewThreadWorker) {
                    ((NewThreadWorker) worker).shutdown();
                    return;
                }
            }
            this.f782w.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.f782w.isDisposed();
        }

        @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            return this.decoratedRun;
        }
    }
}
