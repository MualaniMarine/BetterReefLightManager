package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class ObservableIntervalRange extends Observable<Long> {
    final long end;
    final long initialDelay;
    final long period;
    final Scheduler scheduler;
    final long start;
    final TimeUnit unit;

    public ObservableIntervalRange(long start, long end, long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.scheduler = scheduler;
        this.start = start;
        this.end = end;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Long> observer) {
        IntervalRangeObserver intervalRangeObserver = new IntervalRangeObserver(observer, this.start, this.end);
        observer.onSubscribe(intervalRangeObserver);
        Scheduler scheduler = this.scheduler;
        if (scheduler instanceof TrampolineScheduler) {
            Scheduler.Worker workerCreateWorker = scheduler.createWorker();
            intervalRangeObserver.setResource(workerCreateWorker);
            workerCreateWorker.schedulePeriodically(intervalRangeObserver, this.initialDelay, this.period, this.unit);
            return;
        }
        intervalRangeObserver.setResource(scheduler.schedulePeriodicallyDirect(intervalRangeObserver, this.initialDelay, this.period, this.unit));
    }

    static final class IntervalRangeObserver extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 1891866368734007884L;
        long count;
        final Observer<? super Long> downstream;
        final long end;

        IntervalRangeObserver(Observer<? super Long> actual, long start, long end) {
            this.downstream = actual;
            this.count = start;
            this.end = end;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (isDisposed()) {
                return;
            }
            long j = this.count;
            this.downstream.onNext(Long.valueOf(j));
            if (j == this.end) {
                if (!isDisposed()) {
                    this.downstream.onComplete();
                }
                DisposableHelper.dispose(this);
                return;
            }
            this.count = j + 1;
        }

        public void setResource(Disposable d) {
            DisposableHelper.setOnce(this, d);
        }
    }
}
