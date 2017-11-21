package net.scottpullen.database;

import java.time.Duration;

public class DatabaseConstants {
    public static final String POSTGRES_DRIVER = "org.postgresql.Driver";

    public static final String CONNECTION_TEST_QUERY = "SELECT 1";

    public static final Duration DB_CONNECTION_TIMEOUT = Duration.ofSeconds(10);

    public static final int MIN_DB_CONNECTIONS = 5;

    public static final int MAX_DB_CONNECTIONS = 30;
}
