package net.scottpullen;

public class Configuration {

    public static final ZoneId TIME_ZONE_ID = ZoneId.of("UTC");

    public static final Environment DEFAULT_ENVIRONMENT = Environment.DEVELOPMENT;

    public static final String POSTGRES_DRIVER = "org.postgresql.Driver";

    public static final String CONNECTION_TEST_QUERY = "SELECT 1";

    public static final Duration DB_CONNECTION_TIMEOUT = Duration.ofSeconds(10);

    public static final int MIN_DB_CONNECTIONS = 5;

    public static final int MAX_DB_CONNECTIONS = 30;

    public static final Integer JWT_TTL = 21600;

    public static final int MAX_RABBIT_CONSUMER_THREADS = 10;

    private final Environment environment;
    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;
    private final String jwtSigningKey;

    public static class Builder {
        private Optional<String> environment = Optional.empty();
        private String databaseUrl;
        private String databaseUsername;
        private String databasePassword;
        private String jwtSigningKey;

        public Builder withEnvironment(final String environment) {
            this.environment = Optional.ofNullable(environment)
            return this;
        }

        public Builder withDatabaseUrl(final String databaseUrl) {
            this.databaseUrl = databaseUrl;
            return this;
        }

        public Builder withDatabaseUsername(final String databaseUsername) {
            this.databaseUsername = databaseUsername;
            return this;
        }

        public Builder withDatabasePassword(final String databasePassword) {
            this.databasePassword = databasePassword;
            return this;
        }

        public Builder withJwtSigningKey(final String jwtSigningKey) {
            this.jwtSigningKey = jwtSigningKey;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    public static Builder builder() { return new Builder(); }

    private Configuration(Builder builder) {
        // TODO parameter validation

        this.environment = builder.environment
            .map(e -> Environment.valueOf(e.toUpperCase()))
            .orElse(DEFAULT_ENVIRONMENT);
        this.databaseUrl = builder.databaseUrl;
        this.databaseUsername = builder.databaseUsername;
        this.databasePassword = builder.databasePassword;
        this.jwtSigningKey = builder.jwtSigningKey;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public String getDatabaseUrl() {
        return this.databaseUrl;
    }

    public String getDatabaseUsername() {
        return this.databaseUsername;
    }

    public String getDatabasePassword() {
        return this.databasePassword;
    }

    public String getJwtSigningKey() {
        return this.jwtSigningKey;
    }

    public static Configuration fromEnvironment() {
        Configuration.builder()
            .withEnvironment(System.getenv("ENVIRONMENT"))
            .withDatabaseUrl(System.getenv("TODOS_DATABASE_URL"))
            .withDatabaseUsername(System.getenv("TODOS_DATABASE_USERNAME"))
            .withDatabasePassword(System.getenv("TODOS_DATABASE_PASSWORD"))
            .withJwtSigningKey(System.getenv("JWT_SIGNING_KEY"))
            .build();
    }
}