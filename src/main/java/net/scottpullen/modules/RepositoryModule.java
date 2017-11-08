package net.scottpullen.modules;

import static net.scottpullen.Configuration.CONNECTION_TEST_QUERY;
import static net.scottpullen.Configuration.DB_CONNECTION_TIMEOUT;
import static net.scottpullen.Configuration.MAX_DB_CONNECTIONS;
import static net.scottpullen.Configuration.MIN_DB_CONNECTIONS;

public class RepositoryModule {
    private static final Logger log = LoggerFactory.getLogger(RepositoryModule.class);

    private final HikariDataSource dataSource;

    private HikariDataSource createDataSource(final Configuration configuration) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(configuration.getDatabaseUrl());
        dataSourceConfig.setUsername(configuration.getDatabaseUsername());
        dataSourceConfig.setPassword(configuration.getDatabasePassword());
        dataSourceConfig.setConnectionTestQuery(CONNECTION_TEST_QUERY);
        dataSourceConfig.setMaximumPoolSize(MAX_DB_CONNECTIONS);
        dataSourceConfig.setMinimumIdle(MIN_DB_CONNECTIONS);
        dataSourceConfig.setIdleTimeout(DB_CONNECTION_TIMEOUT.toMillis());

        return new HikariDataSource(dataSourceConfig);
    }

    public RepositoryModule(final Configuration configuration) {
        dataSource = createDataSource(configuration);

        // TODO configure other repositories
    }
}