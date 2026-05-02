# trishul-tenant

> **Use this when**: You need the core tenant entity, DTOs, and mappers for a multi-tenant SaaS application.

Defines the `Tenant` JPA entity representing an isolated customer/organization, with associated DTOs and MapStruct mappers.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-tenant</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create a tenant
Tenant tenant = new Tenant()
    .setName("Acme Corporation")
    .setUrl(new URL("https://acme.example.com"));

// 2. Use the mapper for DTOs
TenantDto dto = TenantMapper.INSTANCE.toDto(tenant);

// 3. Make your entities tenant-aware
@Entity
public class Product extends BaseEntity implements TenantAccessor {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Override
    public Tenant getTenant() { return tenant; }
}
```

## When Would I Use This?

### When you need to represent customers/organizations as isolated tenants

Use the `Tenant` entity:

```java
Tenant tenant = new Tenant()
    .setId(UUID.randomUUID())        // Auto-generated if null
    .setName("Acme Corporation")     // Display name
    .setUrl(new URL("https://acme.example.com"))  // Custom subdomain/URL
    .setIsReady(false);              // Provisioning status

// isReady becomes true after provisioning completes (DB created, resources allocated)
```

| Field | Type | Purpose |
|-------|------|---------|
| `id` | `UUID` | Unique tenant identifier |
| `name` | `String` | Human-readable name |
| `url` | `URL` | Tenant's custom URL/subdomain |
| `isReady` | `Boolean` | `true` when provisioning complete |
| `createdAt` | `LocalDateTime` | Creation timestamp |
| `lastUpdated` | `LocalDateTime` | Last modification timestamp |

### When you need to check if a tenant is fully provisioned

Check `isReady` before allowing tenant operations:

```java
Tenant tenant = tenantService.get(tenantId);

if (!tenant.getIsReady()) {
    throw new IllegalStateException("Tenant provisioning in progress");
}

// Safe to proceed with tenant operations
```

### When you need entities that belong to a specific tenant

Implement `TenantAccessor`:

```java
@Entity
public class Product extends BaseEntity implements TenantAccessor {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Override
    public Tenant getTenant() { return tenant; }

    public Product setTenant(Tenant tenant) {
        this.tenant = tenant;
        return this;
    }
}
```

### When you need lightweight access to just the tenant ID

Use `TenantIdProvider` instead of loading the full entity:

```java
public class OrderRequest implements TenantIdProvider {
    private UUID tenantId;

    @Override
    public UUID getTenantId() { return tenantId; }
}

// In service - route to correct tenant database without loading Tenant entity
void processOrder(OrderRequest request) {
    UUID tenantId = request.getTenantId();
    // Use tenantId for routing...
}
```

### When you need to map tenants to/from DTOs

Use `TenantMapper`:

```java
// Entity to DTO
TenantDto dto = TenantMapper.INSTANCE.toDto(tenant);

// Create DTO to Entity
AddTenantDto addDto = new AddTenantDto()
    .setName("New Tenant")
    .setUrl(new URL("https://new.example.com"));
Tenant tenant = TenantMapper.INSTANCE.fromAddDto(addDto);
// id, version, createdAt, lastUpdated, isReady are ignored (set by system)

// Update DTO to Entity
UpdateTenantDto updateDto = new UpdateTenantDto()
    .setId(existingId)
    .setName("Updated Name");
Tenant updated = TenantMapper.INSTANCE.fromUpdateDto(updateDto);
```

## DTOs

| DTO | Purpose | Fields |
|-----|---------|--------|
| `TenantDto` | Response | All fields |
| `AddTenantDto` | Create request | `name`, `url` |
| `UpdateTenantDto` | Update request | `id`, `name`, `url` |

## Interface Hierarchy

```
TenantData (read-only: getName, getUrl, getIsReady)
    |
    +-- MutableTenant<T> (+ setters)
            |
            +-- BaseTenant<T> (+ IdentityAccessor)
                    |
                    +-- UpdateTenant<T> (+ Versioned)
                            |
                            +-- Tenant (concrete entity)
```

## Attribute Constants for MapStruct

```java
// Always use concrete class in @Mapping annotations
@Mapping(target = Tenant.ATTR_ID, ignore = true)
@Mapping(target = Tenant.ATTR_VERSION, ignore = true)
@Mapping(target = Tenant.ATTR_CREATED_AT, ignore = true)
@Mapping(target = Tenant.ATTR_LAST_UPDATED, ignore = true)
@Mapping(target = Tenant.ATTR_IS_READY, ignore = true)  // System-managed
Tenant fromAddDto(AddTenantDto dto);
```

## Database Schema

```sql
CREATE TABLE tenant (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(2048),
    is_ready BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## Special Tenants

### AdminTenant

Use for system-level operations that span all tenants:

```java
// Singleton for admin context
AdminTenant admin = AdminTenant.INSTANCE;

// Use in admin operations
if (currentTenant.equals(AdminTenant.INSTANCE)) {
    // Allow cross-tenant queries
}
```

## Related Modules

| Module | Use When |
|--------|----------|
| `trishul-tenant-persistence` | You need database-per-tenant routing |
| `trishul-tenant-service` | You need full tenant CRUD with lifecycle |
| `trishul-tenant-auth` | You need to extract tenant from JWT |
| `trishul-iaas-tenant-aws` | You need AWS resource provisioning per tenant |
