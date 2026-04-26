package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class SchedulerPoolFactory {
    public static final boolean PURGE_ENABLED;
    static final String PURGE_ENABLED_KEY = "rx3.purge-enabled";
    public static final int PURGE_PERIOD_SECONDS;
    static final String PURGE_PERIOD_SECONDS_KEY = "rx3.purge-period-seconds";
    static final AtomicReference<ScheduledExecutorService> PURGE_THREAD = new AtomicReference<>();
    static final Map<ScheduledThreadPoolExecutor, Object> POOLS = new ConcurrentHashMap();

    private SchedulerPoolFactory() {
        throw new IllegalStateException("No instances!");
    }

    static {
        SystemPropertyAccessor systemPropertyAccessor = new SystemPropertyAccessor();
        boolean booleanProperty = getBooleanProperty(true, PURGE_ENABLED_KEY, true, true, systemPropertyAccessor);
        PURGE_ENABLED = booleanProperty;
        PURGE_PERIOD_SECONDS = getIntProperty(booleanProperty, PURGE_PERIOD_SECONDS_KEY, 1, 1, systemPropertyAccessor);
        start();
    }

    public static void start() {
        tryStart(PURGE_ENABLED);
    }

    static void tryStart(boolean purgeEnabled) {
        if (!purgeEnabled) {
            return;
        }
        while (true) {
            ScheduledExecutorService scheduledExecutorService = PURGE_THREAD.get();
            if (scheduledExecutorService != null) {
                return;
            }
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, new RxThreadFactory("RxSchedulerPurge"));
            if (PURGE_THREAD.compareAndSet(scheduledExecutorService, scheduledExecutorServiceNewScheduledThreadPool)) {
                ScheduledTask scheduledTask = new ScheduledTask();
                int i = PURGE_PERIOD_SECONDS;
                scheduledExecutorServiceNewScheduledThreadPool.scheduleAtFixedRate(scheduledTask, i, i, TimeUnit.SECONDS);
                return;
            }
            scheduledExecutorServiceNewScheduledThreadPool.shutdownNow();
        }
    }

    public static void shutdown() {
        ScheduledExecutorService andSet = PURGE_THREAD.getAndSet(null);
        if (andSet != null) {
            andSet.shutdownNow();
        }
        POOLS.clear();
    }

    static int getIntProperty(boolean enabled, String key, int defaultNotFound, int defaultNotEnabled, Function<String, String> propertyAccessor) {
        if (!enabled) {
            return defaultNotEnabled;
        }
        try {
            String strApply = propertyAccessor.apply(key);
            return strApply == null ? defaultNotFound : Integer.parseInt(strApply);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return defaultNotFound;
        }
    }

    static boolean getBooleanProperty(boolean enabled, String key, boolean defaultNotFound, boolean defaultNotEnabled, Function<String, String> propertyAccessor) {
        if (!enabled) {
            return defaultNotEnabled;
        }
        try {
            String strApply = propertyAccessor.apply(key);
            return strApply == null ? defaultNotFound : "true".equals(strApply);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return defaultNotFound;
        }
    }

    static final class SystemPropertyAccessor implements Function<String, String> {
        SystemPropertyAccessor() {
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public String apply(String t) {
            return System.getProperty(t);
        }
    }

    public static ScheduledExecutorService create(ThreadFactory factory) {
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, factory);
        tryPutIntoPool(PURGE_ENABLED, scheduledExecutorServiceNewScheduledThreadPool);
        return scheduledExecutorServiceNewScheduledThreadPool;
    }

    static void tryPutIntoPool(boolean purgeEnabled, ScheduledExecutorService exec) {
        if (purgeEnabled && (exec instanceof ScheduledThreadPoolExecutor)) {
            POOLS.put((ScheduledThreadPoolExecutor) exec, exec);
        }
    }

    static final class ScheduledTask implements Runnable {
        ScheduledTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            for (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor : new ArrayList(SchedulerPoolFactory.POOLS.keySet())) {
                if (scheduledThreadPoolExecutor.isShutdown()) {
                    SchedulerPoolFactory.POOLS.remove(scheduledThreadPoolExecutor);
                } else {
                    scheduledThreadPoolExecutor.purge();
                }
            }
        }
    }
}
