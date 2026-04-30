# trishul-tenant-persistence-management

Tenant database schema creation, user provisioning, and Flyway migration management.

> **Use this when**: You need to provision tenant database schemas, create database users, and run Flyway migrations for each tenant.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-tenant-persistence-management</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Inject the migration manager
@Autowired
private MigrationManager migrationManager;

// 2. Migrate a new tenant
Tenant tenant = new Tenant().setId(tenantId).setName("Acme Corp");
migrationManager.migrate(tenant);

// 3. Or migrate all tenants at once
migrationManager.migrateAll(tenants);
```

## When Would I Use This?

### When you need to run Flyway migrations for tenants

Use `MigrationManager` to apply migrations:

```java
@Service
public class TenantProvisioningService {
    
    private final MigrationManager migrationManager;
    
    public void provisionTenant(Tenant tenant) {
        // Creates schema, user, and runs migrations
        migrationManager.migrate(tenant);
    }
    
    public void migrateAllTenants(List<Tenant> tenants) {
        migrationManager.migrateAll(tenants);
    }
}
```

### When you need sequential migration with multiple registers

Use `SequentialMigrationManager` with ordered registers:

```java
// The manager executes registers in sequence:
// 1. TenantSchemaRegister - Creates database schema
// 2. TenantUserRegister - Creates database user
// 3. FlywayTenantMigrationRegister - Runs Flyway migrations
```

### When you need to create tenant schemas

Use `TenantSchemaRegister`:

```java
public class TenantSchemaRegister implements TenantRegister {
    
    @Override
    public void register(TenantData tenant) {
        // Creates schema: tenant_{id}
        jdbcDialect.createSchemaIfNotExists(conn, schemaName);
    }
}
```

### When you need to create tenant database users

Use `TenantUserRegister`:

```java
public class TenantUserRegister implements TenantRegister {
    
    @Override
    public void register(TenantData tenant) {
        // Creates user and grants schema access
        jdbcDialect.createUser(conn, username, password);
        jdbcDialect.grantPrivilege(conn, "ALL", "SCHEMA", schemaName, username);
    }
}
```

### When you need to run Flyway migrations per tenant

Use `FlywayTenantMigrationRegister`:

```java
public class FlywayTenantMigrationRegister implements MigrationRegister {
    
    @Override
    public void register(TenantData tenant) {
        Flyway flyway = Flyway.configure()
            .dataSource(tenantDataSource)
            .schemas(schemaName)
            .locations(migrationLocations)
            .load();
        
        flyway.migrate();
    }
}
```

### When you need a unified registration process

Use `UnifiedTenantRegister` to combine multiple registers:

```java
UnifiedTenantRegister unifiedRegister = new UnifiedTenantRegister(
    List.of(schemaRegister, userRegister, migrationRegister)
);

unifiedRegister.register(tenant);  // Runs all registers in order
```

## Core Components

### Interfaces

| Interface | Purpose |
|-----------|---------|
| `MigrationManager` | Orchestrates tenant migrations |
| `TenantRegister` | Registers a tenant (schema, user, etc.) |
| `MigrationRegister` | Runs database migrations |

### Implementations

| Class | Purpose |
|-------|---------|
| `SequentialMigrationManager` | Executes registers in sequence |
| `TenantSchemaRegister` | Creates tenant database schema |
| `TenantUserRegister` | Creates tenant database user |
| `FlywayTenantMigrationRegister` | Runs Flyway migrations |
| `UnifiedTenantRegister` | Combines multiple registers |

## Migration Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                      MigrationManager                            │
│                     migrateAll(tenants)                          │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                  SequentialMigrationManager                      │
│              For each tenant, run registers in order            │
└─────────────────────────────────────────────────────────────────┘
                              │
              ┌───────────────┼───────────────┐
              ▼               ▼               ▼
    ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
    │TenantSchemaReg. │ │TenantUserReg.   │ │FlywayMigration  │
    │                 │ │                 │ │                 │
    │CREATE SCHEMA    │ │CREATE USER      │ │Flyway.migrate() │
    │tenant_xxx       │ │GRANT privileges │ │                 │
    └─────────────────┘ └─────────────────┘ └─────────────────┘
```

## Auto-Configuration

`TenantPersistenceManagementAutoConfiguration` registers:

| Bean | Type | Description |
|------|------|-------------|
| `migrationManager` | `SequentialMigrationManager` | Migration orchestrator |
| `tenantSchemaRegister` | `TenantSchemaRegister` | Schema creation |
| `tenantUserRegister` | `TenantUserRegister` | User creation |
| `flywayMigrationRegister` | `FlywayTenantMigrationRegister` | Flyway migrations |

## Complete Example

```java
@Configuration
@Import(TenantPersistenceManagementAutoConfiguration.class)
public class TenantConfig {
    
    @Bean
    public MigrationManager migrationManager(
            TenantSchemaRegister schemaRegister,
            TenantUserRegister userRegister,
            FlywayTenantMigrationRegister flywayRegister) {
        
        return new SequentialMigrationManager(
            List.of(schemaRegister, userRegister, flywayRegister)
        );
    }
}

@Service
public class TenantOnboardingService {
    
    private final TenantRepository tenantRepository;
    private final MigrationManager migrationManager;
    
    @Transactional
    public Tenant onboardTenant(CreateTenantRequest request) {
        // Save tenant
        Tenant tenant = tenantRepository.save(
            new Tenant().setName(request.getName())
        );
        
        // Provision database resources
        migrationManager.migrate(tenant);
        
        // Mark as ready
        tenant.setIsReady(true);
        return tenantRepository.save(tenant);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-jdbc` | JDBC dialect for schema/user operations |
| `trishul-data` | DataSource management |
| `trishul-tenant` | Tenant entity model |
| `trishul-tenant-service` | Full tenant lifecycle service |
