package net.scottpullen.database;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseModule extends AbstractModule {
    @Override
    public void configure() { }

    @Provides
    @Singleton
    public DataSource getDataSource(DatabaseConfig config) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(config.getUrl());
        dataSourceConfig.setUsername(config.getUsername());
        dataSourceConfig.setPassword(config.getPassword());
        dataSourceConfig.setConnectionTestQuery(DatabaseConstants.CONNECTION_TEST_QUERY);
        dataSourceConfig.setMaximumPoolSize(DatabaseConstants.MAX_DB_CONNECTIONS);
        dataSourceConfig.setMinimumIdle(DatabaseConstants.MIN_DB_CONNECTIONS);
        dataSourceConfig.setIdleTimeout(DatabaseConstants.DB_CONNECTION_TIMEOUT.toMillis());

        return new HikariDataSource(dataSourceConfig);
    }
}
