Create .env file with the following environment variables:

* ENVIRONMENT (Defaults to "development")
* TODOS_DATABASE_URL (Example: jdbc:postgresql:reactive_todos)
* TODOS_DATABASE_USERNAME
* TODOS_DATABASE_PASSWORD
* JWT_SIGNING_KEY

Running flyway migrate from command line with gradle

```
gradle flywayMigrate
```

For a production like environment:

```
gradle flywayMigrate -Pflyway.url=<url> -Pflyway.user=<user> -Pflyway.password=<>
```