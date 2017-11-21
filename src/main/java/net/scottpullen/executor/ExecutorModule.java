package net.scottpullen.executor;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static net.scottpullen.database.DatabaseConstants.MAX_DB_CONNECTIONS;
import static net.scottpullen.executor.ExecutorConstants.MAX_RABBIT_CONSUMER_THREADS;
import static net.scottpullen.executor.ExecutorConstants.SCHEDULED_THREAD_POOL_SIZE;

public class ExecutorModule extends AbstractModule {
    @Override
    public void configure() { }

    @Provides
    @Singleton
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(MAX_DB_CONNECTIONS + MAX_RABBIT_CONSUMER_THREADS);
    }

    @Provides
    @Singleton
    public Scheduler getScheduler(ExecutorService executorService) {
        return Schedulers.from(executorService);
    }

    @Provides
    @Singleton
    public ScheduledExecutorService getScheduledExecutorService() {
        return Executors.newScheduledThreadPool(SCHEDULED_THREAD_POOL_SIZE);
    }
}
