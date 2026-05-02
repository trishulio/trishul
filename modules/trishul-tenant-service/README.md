# trishul-tenant-service

Complete tenant lifecycle management service with REST API, database provisioning, and IaaS resource management.

> **Use this when**: You need full tenant CRUD operations with automatic schema creation, migrations, and cloud resource provisioning.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-tenant-service</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Auto-configuration registers all beans
@Autowired
private TenantService tenantService;

// 2. Create a tenant (provisions database + IaaS resources)
Tenant tenant = tenantService.add(List.of(
    new BaseTenant<>()
        .setName("Acme Corp")
        .setUrl(new URL("https://acme.example.com"))
)).get(0);

// 3. Tenant is fully provisioned with:
//    - Database schema created
//    - Migrations applied
//    - IaaS resources (S3, Cognito, etc.) provisioned
//    - isReady = true
```

## When Would I Use This?

### When you need to create tenants via REST API

The `TenantController` provides full CRUD endpoints:

```
GET    /operations/tenants          - List all tenants (paginated, filterable)
GET    /operations/tenants/{id}     - Get tenant by ID
POST   /operations/tenants          - Create tenant(s)
PUT    /operations/tenants          - Update tenant(s) (full replace)
PATCH  /operations/tenants          - Patch tenant(s) (partial update)
DELETE /operations/tenants?ids=...  - Delete tenant(s)
```

```bash
# Create a tenant
curl -X POST /operations/tenants \
  -H "Content-Type: application/json" \
  -d '[{"name": "Acme Corp", "url": "https://acme.example.com"}]'

# List tenants with filtering
curl "/operations/tenants?is_ready=true&page=0&size=10"
```

### When tenant creation should trigger infrastructure provisioning

The `TenantService.add()` automatically:

1. Saves tenant to database
2. Creates tenant database schema via `MigrationManager`
3. Provisions IaaS resources via `TenantIaasService`
4. Marks tenant as ready (`isReady = true`)

```java
@Override
public List<Tenant> add(final List<? extends BaseTenant<?>> additions) {
    final List<Tenant> entities = this.entityMergerService.getAddEntities(additions);
    List<Tenant> tenants = this.repoService.saveAll(entities);

    // Automatic provisioning
    this.migrationManager.migrateAll(tenants);
    this.iaasService.put(tenants);

    tenants.forEach(tenant -> tenant.setIsReady(true));
    return this.repoService.saveAll(tenants);
}
```

### When tenant deletion should clean up all resources

The `TenantService.delete()` handles deprovisioning:

```java
public long delete(Set<UUID> ids) {
    // Mark as not ready first
    tenants.forEach(tenant -> tenant.setIsReady(false));

    // Delete from database
    long deleteCount = this.repoService.delete(ids);

    // Clean up IaaS resources
    this.iaasService.delete(ids);

    return deleteCount;
}
```

### When you need automatic migrations on startup

Use `AutoTenantMigratorOnStartup` to migrate all tenants when the application starts:

```java
@Configuration
@Import(TenantServiceAutoConfiguration.class)
public class AppConfig {
    // AutoTenantMigratorOnStartup runs on ApplicationReadyEvent
}
```

## REST API

### List Tenants

```
GET /operations/tenants
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `ids` | `Set<UUID>` | Filter by IDs |
| `names` | `Set<String>` | Filter by names |
| `urls` | `Set<URL>` | Filter by URLs |
| `is_ready` | `Boolean` | Filter by ready status |
| `sort_by` | `SortedSet<String>` | Sort fields |
| `order_asc` | `boolean` | Sort direction |
| `page` | `int` | Page index |
| `size` | `int` | Page size |
| `attr` | `Set<String>` | Fields to include in response |

### Create Tenants

```
POST /operations/tenants
Content-Type: application/json

[
  {
    "name": "Acme Corp",
    "url": "https://acme.example.com"
  }
]
```

### Update Tenants (PUT)

```
PUT /operations/tenants
Content-Type: application/json

[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Acme Corporation",
    "url": "https://acme.example.com",
    "version": 1
  }
]
```

### Patch Tenants (PATCH)

```
PATCH /operations/tenants
Content-Type: application/json

[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "New Name Only"
  }
]
```

## Core Components

| Class | Purpose |
|-------|---------|
| `TenantService` | Business logic for tenant CRUD + provisioning |
| `TenantController` | REST API endpoints |
| `TenantRepository` | JPA repository |
| `AutoTenantMigratorOnStartup` | Migrates all tenants on app startup |
| `TenantServiceAutoConfiguration` | Spring Boot auto-configuration |

## Auto-Configuration

`TenantServiceAutoConfiguration` registers:

| Bean | Type | Description |
|------|------|-------------|
| `tenantService` | `TenantService` | Tenant lifecycle service |
| `tenantController` | `TenantController` | REST controller |
| `autoMigrator` | `AutoTenantMigratorOnStartup` | Startup migrator |

## Complete Example

```java
@SpringBootApplication
@Import(TenantServiceAutoConfiguration.class)
public class MultiTenantApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiTenantApplication.class, args);
    }
}

// Inject and use the service
@Service
public class TenantOnboardingService {

    private final TenantService tenantService;

    public TenantDto onboardTenant(TenantOnboardingRequest request) {
        Tenant tenant = tenantService.add(List.of(
            new BaseTenant<>()
                .setName(request.getCompanyName())
                .setUrl(request.getSubdomainUrl())
        )).get(0);

        // Tenant is now fully provisioned
        return TenantMapper.INSTANCE.toDto(tenant);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-tenant` | Tenant entity model |
| `trishul-tenant-persistence-management` | Schema and migration management |
| `trishul-iaas-tenant-service` | IaaS resource provisioning |
| `trishul-crud` | CRUD service abstractions |
