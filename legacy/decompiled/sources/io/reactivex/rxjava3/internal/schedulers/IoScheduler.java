package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class IoScheduler extends Scheduler {
    static final RxThreadFactory EVICTOR_THREAD_FACTORY;
    private static final String EVICTOR_THREAD_NAME_PREFIX = "RxCachedWorkerPoolEvictor";
    public static final long KEEP_ALIVE_TIME_DEFAULT = 60;
    private static final String KEY_IO_PRIORITY = "rx3.io-priority";
    static final CachedWorkerPool NONE;
    static final ThreadWorker SHUTDOWN_THREAD_WORKER;
    static final RxThreadFactory WORKER_THREAD_FACTORY;
    private static final String WORKER_THREAD_NAME_PREFIX = "RxCachedThreadScheduler";
    final AtomicReference<CachedWorkerPool> pool;
    final ThreadFactory threadFactory;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
    private static final String KEY_KEEP_ALIVE_TIME = "rx3.io-keep-alive-time";
    private static final long KEEP_ALIVE_TIME = Long.getLong(KEY_KEEP_ALIVE_TIME, 60).longValue();

    static {
        ThreadWorker threadWorker = new ThreadWorker(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
        SHUTDOWN_THREAD_WORKER = threadWorker;
        threadWorker.dispose();
        int iMax = Math.max(1, Math.min(10, Integer.getInteger(KEY_IO_PRIORITY, 5).intValue()));
        WORKER_THREAD_FACTORY = new RxThreadFactory(WORKER_THREAD_NAME_PREFIX, iMax);
        EVICTOR_THREAD_FACTORY = new RxThreadFactory(EVICTOR_THREAD_NAME_PREFIX, iMax);
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(0L, null, WORKER_THREAD_FACTORY);
        NONE = cachedWorkerPool;
        cachedWorkerPool.shutdown();
    }

    static final class CachedWorkerPool implements Runnable {
        final CompositeDisposable allWorkers;
        private final ScheduledExecutorService evictorService;
        private final Future<?> evictorTask;
        private final ConcurrentLinkedQueue<ThreadWorker> expiringWorkerQueue;
        private final long keepAliveTime;
        private final ThreadFactory threadFactory;

        CachedWorkerPool(long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
            ScheduledFuture<?> scheduledFutureScheduleWithFixedDelay;
            this.keepAliveTime = unit != null ? unit.toNanos(keepAliveTime) : 0L;
            this.expiringWorkerQueue = new ConcurrentLinkedQueue<>();
            this.allWorkers = new CompositeDisposable();
            this.threadFactory = threadFactory;
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = null;
            if (unit != null) {
                scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, IoScheduler.EVICTOR_THREAD_FACTORY);
                long j = this.keepAliveTime;
                scheduledFutureScheduleWithFixedDelay = scheduledExecutorServiceNewScheduledThreadPool.scheduleWithFixedDelay(this, j, j, TimeUnit.NANOSECONDS);
            } else {
                scheduledFutureScheduleWithFixedDelay = null;
            }
            this.evictorService = scheduledExecutorServiceNewScheduledThreadPool;
            this.evictorTask = scheduledFutureScheduleWithFixedDelay;
        }

        @Override // java.lang.Runnable
        public void run() {
            evictExpiredWorkers(this.expiringWorkerQueue, this.allWorkers);
        }

        ThreadWorker get() {
            if (this.allWorkers.isDisposed()) {
                return IoScheduler.SHUTDOWN_THREAD_WORKER;
            }
            while (!this.expiringWorkerQueue.isEmpty()) {
                ThreadWorker threadWorkerPoll = this.expiringWorkerQueue.poll();
                if (threadWorkerPoll != null) {
                    return threadWorkerPoll;
                }
            }
            ThreadWorker threadWorker = new ThreadWorker(this.threadFactory);
            this.allWorkers.add(threadWorker);
            return threadWorker;
        }

        void release(ThreadWorker threadWorker) {
            threadWorker.setExpirationTime(now() + this.keepAliveTime);
            this.expiringWorkerQueue.offer(threadWorker);
        }

        static void evictExpiredWorkers(ConcurrentLinkedQueue<ThreadWorker> expiringWorkerQueue, CompositeDisposable allWorkers) {
            if (expiringWorkerQueue.isEmpty()) {
                return;
            }
            long jNow = now();
            for (ThreadWorker threadWorker : expiringWorkerQueue) {
                if (threadWorker.getExpirationTime() > jNow) {
                    return;
                }
                if (expiringWorkerQueue.remove(threadWorker)) {
                    allWorkers.remove(threadWorker);
                }
            }
        }

        static long now() {
            return System.nanoTime();
        }

        void shutdown() {
            this.allWorkers.dispose();
            Future<?> future = this.evictorTask;
            if (future != null) {
                future.cancel(true);
            }
            ScheduledExecutorService scheduledExecutorService = this.evictorService;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdownNow();
            }
        }
    }

    public IoScheduler() {
        this(WORKER_THREAD_FACTORY);
    }

    public IoScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        this.pool = new AtomicReference<>(NONE);
        start();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void start() {
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT, this.threadFactory);
        if (this.pool.compareAndSet(NONE, cachedWorkerPool)) {
            return;
        }
        cachedWorkerPool.shutdown();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void shutdown() {
        CachedWorkerPool andSet = this.pool.getAndSet(NONE);
        if (andSet != NONE) {
            andSet.shutdown();
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(this.pool.get());
    }

    public int size() {
        return this.pool.get().allWorkers.size();
    }

    static final class EventLoopWorker extends Scheduler.Worker {
        private final CachedWorkerPool pool;
        private final ThreadWorker threadWorker;
        final AtomicBoolean once = new AtomicBoolean();
        private final CompositeDisposable tasks = new CompositeDisposable();

        EventLoopWorker(CachedWorkerPool pool) {
            this.pool = pool;
            this.threadWorker = pool.get();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.once.compareAndSet(false, true)) {
                this.tasks.dispose();
                this.pool.release(this.threadWorker);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.once.get();
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable action, long delayTime, TimeUnit unit) {
            if (this.tasks.isDisposed()) {
                return EmptyDisposable.INSTANCE;
            }
            return this.threadWorker.scheduleActual(action, delayTime, unit, this.tasks);
        }
    }

    static final class ThreadWorker extends NewThreadWorker {
        long expirationTime;

        ThreadWorker(ThreadFactory threadFactory) {
            super(threadFactory);
            this.expirationTime = 0L;
        }

        public long getExpirationTime() {
            return this.expirationTime;
        }

        public void setExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
        }
    }
}
