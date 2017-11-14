package net.scottpullen.modules;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static net.scottpullen.Configuration.MAX_DB_CONNECTIONS;
import static net.scottpullen.Configuration.MAX_RABBIT_CONSUMER_THREADS;
import static net.scottpullen.Configuration.SCHEDULED_THREAD_POOL_SIZE;

public class ExecutorModule implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ExecutorModule.class);

    private final ExecutorService executor;
    private final ScheduledExecutorService scheduledExecutor;
    private final Scheduler dataAndMessagingScheduler;

    public ExecutorModule() {
        executor = Executors.newFixedThreadPool(MAX_DB_CONNECTIONS + MAX_RABBIT_CONSUMER_THREADS);
        scheduledExecutor = Executors.newScheduledThreadPool(SCHEDULED_THREAD_POOL_SIZE);
        dataAndMessagingScheduler = Schedulers.from(executor);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
    }

    public Scheduler getDataAndMessagingScheduler() {
        return dataAndMessagingScheduler;
    }

    private void close(final String name, final ExecutorService executor) {
        if (executor != null) {
            try {
                log.info("Closing {}", name);
                executor.shutdown();
            }
            catch (Exception e) {
                log.error("Error closing {}", e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        close("executor service", executor);
        close("scheduled executor service", scheduledExecutor);
    }
}