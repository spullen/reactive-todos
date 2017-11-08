Running flyway migrate from command line with gradle

```
gradle flywayMigrate
```

For a production like environment:

```
gradle flywayMigrate -Pflyway.url=<url> -Pflyway.user=<user> -Pflyway.password=<>
```