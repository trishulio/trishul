# trishul-data

DataSource configuration and management with HikariCP connection pooling for multi-tenant database connections.

> **Use this when**: You need to manage database connections for multi-tenant applications with per-tenant credentials and connection pooling.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-data</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Build a DataSource with HikariCP
DataSource ds = new HikariDataSourceBuilder()
    .url("jdbc:postgresql://localhost:5432/mydb")
    .username("app_user")
    .password("secret")
    .schema("tenant_123")
    .poolSize(10)
    .build();

// 2. Or use DataSourceManager for caching
DataSourceConfiguration config = configProvider.getConfiguration(tenantId);
DataSource tenantDs = dataSourceManager.getDataSource(config);

// 3. Execute queries
Connection conn = tenantDs.getConnection();
```

## When Would I Use This?

### When you need to create HikariCP DataSources programmatically

Use `HikariDataSourceBuilder` - fluent builder for HikariCP:

```java
DataSourceBuilder builder = new HikariDataSourceBuilder();

DataSource ds = builder
    .url("jdbc:postgresql://localhost:5432/mydb")
    .username("app_user")
    .password("secret")
    .schema("tenant_123")
    .poolSize(10)
    .autoCommit(false)
    .build();
```

### When you need to cache DataSources to avoid recreating connections

Use `CachingDataSourceManager` - caches DataSource instances by configuration:

```java
@Service
public class TenantDatabaseService {
    
    private final DataSourceManager dataSourceManager;
    private final DataSourceConfigurationProvider configProvider;
    
    public Connection getTenantConnection(String tenantId) throws SQLException, IOException {
        DataSourceConfiguration config = configProvider.getConfiguration(tenantId);
        DataSource ds = dataSourceManager.getDataSource(config);  // Cached
        return ds.getConnection();
    }
}
```

### When you need lazy-loaded tenant credentials from secrets manager

Use `LazyTenantDataSourceConfiguration` - fetches credentials on-demand:

```java
@Bean
public DataSourceConfiguration tenantConfig(
    GlobalDataSourceConfiguration globalConfig,
    SecretsManager<String, String> secretsManager,
    String tenantId
) {
    return new LazyTenantDataSourceConfiguration(
        globalConfig,
        secretsManager,
        tenantId
    );
}
```

### When you need a shared admin DataSource

Use `DataSourceManager.getAdminDataSource()`:

```java
public void createTenantSchema(String schemaName) throws SQLException {
    DataSource adminDs = dataSourceManager.getAdminDataSource();
    try (Connection conn = adminDs.getConnection();
         Statement stmt = conn.createStatement()) {
        stmt.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
    }
}
```

### When you need to execute queries without JPA

Use `DataSourceQueryRunner`:

```java
@Service
public class DatabaseQueryService {
    
    private final DataSourceQueryRunner queryRunner;
    private final DataSourceManager dataSourceManager;
    
    public int getTenantCount() throws SQLException {
        DataSource ds = dataSourceManager.getAdminDataSource();
        return queryRunner.query(
            ds,
            "SELECT COUNT(*) FROM tenants",
            rs -> rs.next() ? rs.getInt(1) : 0
        );
    }
}
```

### When you need multi-tenant database routing

Combine `DataSourceManager` with `DataSourceConfigurationManager`:

```java
@Service
public class MultiTenantDataSourceService {
    
    private final DataSourceManager dataSourceManager;
    private final DataSourceConfigurationManager configManager;
    
    public DataSource getDataSourceForTenant(String tenantId) throws SQLException, IOException {
        DataSourceConfiguration config = configManager.getConfiguration(tenantId);
        if (config == null) {
            throw new TenantNotFoundException(tenantId);
        }
        return dataSourceManager.getDataSource(config);
    }
    
    public void registerTenant(String tenantId, DataSourceConfiguration config) {
        configManager.registerConfiguration(tenantId, config);
    }
}
```

## Configuration

### Application Properties

```yaml
trishul:
  datasource:
    url: jdbc:postgresql://localhost:5432/trishul
    db-name: trishul
    schema-prefix: tenant_
    pool-size: 10
    auto-commit: false
    
    migrations:
      - location: classpath:db/migration/admin
        table: flyway_schema_history
        baseline: true
      - location: classpath:db/migration/tenant
        table: flyway_tenant_history
        baseline: false
```

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      Service Layer                               │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    DataSourceManager                             │
│    getAdminDataSource()    getDataSource(config)                │
└─────────────────────────────────────────────────────────────────┘
                              │
              ┌───────────────┴───────────────┐
              ▼                               ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│ CachingDataSourceManager│     │ DataSourceConfiguration │
│ - Caches DataSources    │     │ - Lazy credential load  │
│ - Thread-safe           │     │ - From SecretsManager   │
└─────────────────────────┘     └─────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                   HikariDataSourceBuilder                        │
│    Creates HikariDataSource with connection pooling             │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│              HikariCP → JDBC → PostgreSQL                       │
└─────────────────────────────────────────────────────────────────┘
```

## Reference

### Core Interfaces

| Interface | Purpose |
|-----------|---------|
| `DataSourceManager` | Manages DataSource instances |
| `DataSourceConfiguration` | Tenant-specific connection config |
| `GlobalDataSourceConfiguration` | Shared connection settings |
| `DataSourceBuilder` | Fluent DataSource construction |
| `DataSourceQueryRunner` | Execute SQL on DataSource |
| `DataSourceConfigurationProvider` | Provides configs by identifier |
| `DataSourceConfigurationManager` | Manages tenant configurations |

### Implementations

| Class | Purpose |
|-------|---------|
| `CachingDataSourceManager` | Caches DataSource by config |
| `HikariDataSourceBuilder` | Builds HikariDataSource |
| `LazyTenantDataSourceConfiguration` | Lazy credential loading |
| `ImmutableGlobalDataSourceConfiguration` | Immutable global settings |
| `MigrationConfiguration` | Flyway migration settings |

### HikariDataSourceBuilder Keys

| Key | Description |
|-----|-------------|
| `dataSource.user` | Database username |
| `dataSource.password` | Database password |
| `jdbcUrl` | JDBC connection URL |
| `autoCommit` | Auto-commit mode |
| `schema` | Default schema |
| `maximumPoolSize` | Connection pool size |

## Complete Example

```java
@Configuration
public class MultiTenantDatabaseConfiguration {
    
    @Bean
    public DataSourceManager dataSourceManager(
        GlobalDataSourceConfiguration globalConfig,
        SecretsManager<String, String> secretsManager
    ) {
        return new CachingDataSourceManager(
            new HikariDataSourceBuilder(),
            globalConfig,
            secretsManager
        );
    }
    
    @Bean
    public TenantConnectionProvider tenantConnectionProvider(
        DataSourceManager dataSourceManager,
        DataSourceConfigurationManager configManager
    ) {
        return new TenantConnectionProviderPool(dataSourceManager, configManager);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-secrets` | Credential retrieval interface |
| `trishul-secrets-aws` | AWS Secrets Manager implementation |
| `trishul-tenant-persistence` | Multi-tenant Hibernate integration |
| `trishul-repo` | JPA repository utilities |
