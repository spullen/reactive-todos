package net.scottpullen.modules;

public class ExecutorModule implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ExecutorModule.class);

    private static final String INSTRUMENTED_EXECUTOR_NAME = "executor";

    private final ExecutorService executor;
    private final ScheduledExecutorService scheduledExecutor;
    private final Scheduler dataAndMessagingScheduler;

    private ExecutorService createExecutorService() {
        return Executors.newFixedThreadPool(MAX_DB_CONNECTIONS + MAX_RABBIT_CONSUMER_THREADS);
    }

    public ExecutorModule(final TelemetryModule telemetryModule) {
        // TODO, is it possible to create this without being instrumented?
        // or what package does that come from?
        executor = new InstrumentedExecutorService(createExecutorService(),
            telemetryModule.getMetricRegistry(),
            INSTRUMENTED_EXECUTOR_NAME);
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