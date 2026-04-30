# trishul-data-management

Spring Boot auto-configuration for DataSource management components.

> **Use this when**: You need auto-configured DataSource management beans for multi-tenant database operations.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-data-management</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Add the dependency - beans are auto-configured
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// 2. Inject the configured DataSourceManager
@Autowired
private DataSourceManager dataSourceManager;

// 3. Use for tenant database connections
DataSource tenantDs = dataSourceManager.getDataSource(tenantConfig);
```

## When Would I Use This?

### When you need auto-configured data source management

The auto-configuration sets up all required beans:

```java
@Configuration
@Import(DataManagementAutoConfiguration.class)
public class AppConfig {
    // DataSourceManager, builders, and providers are registered
}
```

### When you need centralized DataSource configuration

The auto-configuration wires together:
- `DataSourceManager` for managing DataSource instances
- `DataSourceBuilder` implementations
- Configuration providers

## Auto-Configuration

`DataManagementAutoConfiguration` registers:

| Bean | Type | Description |
|------|------|-------------|
| `dataSourceManager` | `DataSourceManager` | Manages DataSource instances |
| `dataSourceBuilder` | `DataSourceBuilder` | Builds HikariCP DataSources |

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-data` | Core DataSource interfaces |
| `trishul-tenant-persistence-management` | Tenant schema management |
| `trishul-secrets` | Credential retrieval |
