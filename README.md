# zula-database-package

Single Spring Boot database package for Zula services using JDBI.

## Dependency

All database support is published as one Maven package:

```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/Zula-Z/zula-database-package</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.zula</groupId>
    <artifactId>zula-database-package</artifactId>
    <version>VERSION</version>
  </dependency>
</dependencies>
```

The package version is controlled only in the root `pom.xml`.

## Database Selection

PostgreSQL is the default.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: myuser
    password: secret
    driver-class-name: org.postgresql.Driver
```

For MySQL, use a MySQL datasource. The package detects this automatically:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: myuser
    password: secret
    driver-class-name: com.mysql.cj.jdbc.Driver
```

You can also force the provider:

```yaml
zula:
  database:
    provider: mysql
```

Supported provider values:

- `postgres` default
- `mysql`
- `ms` alias for MySQL

## Auto-Configuration

The package exposes:

- `Jdbi`
- `DataSourceTransactionManager`
- `DatabaseManager`

Supported properties use the `zula.database` prefix:

```yaml
zula:
  database:
    provider: postgres
    auto-create-schema: true
    schema-prefix: zula
    auto-create-queue-schema: true
    queue-schema-suffix: queue
```

## Build

```bash
mvn clean install -DskipTests
```

CI builds and publishes the root jar only, so GitHub Packages should contain one package: `com.zula:zula-database-package`.
