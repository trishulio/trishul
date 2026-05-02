# trishul-tenant-persistence

> **Use this when**: You need database-per-tenant isolation with automatic connection routing based on the current tenant context.

Provides Hibernate multi-tenancy support with dynamic DataSource routing, connection pooling, and transparent tenant switching.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-tenant-persistence</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Provide a TenantIdProvider bean (tells the system which tenant is active)
@Component
public class RequestTenantIdProvider implements TenantIdProvider {
    @Autowired private ContextHolder contextHolder;

    @Override
    public UUID getTenantId() {
        return contextHolder.getSessionTenantId();
    }
}

// 2. Configure tenant database settings
@Component
public class MyTenantConfigProvider implements TenantDataSourceConfigurationProvider {
    @Override
    public DataSourceConfiguration getConfiguration(UUID tenantId) {
        return new LazyTenantDataSourceConfiguration(
            tenantId.toString(),
            "jdbc:postgresql://localhost:5432/tenant_" + tenantId,
            "tenant_user",
            secretsManager.get("tenant-db-password")
        );
    }
}

// 3. Use standard JPA - routing is automatic
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Queries automatically go to the current tenant's database
}
```

## When Would I Use This?

### When you need complete data isolation between tenants

This module routes each request to the correct tenant database automatically:

```
Request (tenant_id: ABC)  →  TenantIdentifierResolver  →  tenant_abc database
Request (tenant_id: XYZ)  →  TenantIdentifierResolver  →  tenant_xyz database
```

Each tenant has their own database, ensuring:
- Complete data isolation (no accidental cross-tenant leaks)
- Independent scaling per tenant
- Tenant-specific backups and migrations

### When you need automatic connection routing based on context

Once configured, use standard JPA - the module handles routing:

```java
@Service
public class ProductService {
    @Autowired private ProductRepository repository;

    public List<Product> getProducts() {
        // Automatically queries the current tenant's database
        // No tenant filtering needed in queries
        return repository.findAll();
    }
}
```

### When you need to determine the current tenant from the request

Implement `TenantIdProvider`:

```java
// Option 1: From authentication context (recommended)
@Component
public class AuthTenantIdProvider implements TenantIdProvider {
    @Autowired private ContextHolder contextHolder;

    @Override
    public UUID getTenantId() {
        return contextHolder.getSessionTenantId();
    }
}

// Option 2: From HTTP header
@Component
public class HeaderTenantIdProvider implements TenantIdProvider {
    @Autowired private HttpServletRequest request;

    @Override
    public UUID getTenantId() {
        String tenantId = request.getHeader("X-Tenant-ID");
        return UUID.fromString(tenantId);
    }
}

// Option 3: From subdomain
@Component
public class SubdomainTenantIdProvider implements TenantIdProvider {
    @Autowired private HttpServletRequest request;
    @Autowired private TenantLookupService tenantLookup;

    @Override
    public UUID getTenantId() {
        String host = request.getServerName();  // acme.example.com
        String subdomain = host.split("\\.")[0];  // acme
        return tenantLookup.getTenantIdBySubdomain(subdomain);
    }
}
```

### When you need tenant-specific database credentials

Implement `TenantDataSourceConfigurationProvider`:

```java
@Component
public class MyTenantConfigProvider implements TenantDataSourceConfigurationProvider {

    @Value("${trishul.tenant.datasource.url-template}")
    private String urlTemplate;  // jdbc:postgresql://localhost:5432/tenant_{tenantId}

    @Autowired private SecretsManager<String, String> secretsManager;

    @Override
    public DataSourceConfiguration getConfiguration(UUID tenantId) throws IOException {
        String url = urlTemplate.replace("{tenantId}", tenantId.toString());
        String password = secretsManager.get("tenant/" + tenantId + "/db-password");

        return new LazyTenantDataSourceConfiguration(
            tenantId.toString(),
            url,
            "tenant_user",
            password
        );
    }
}
```

### When you need to specify which entity packages to scan

Implement `PackageScanConfig`:

```java
@Component
public class MyPackageScanConfig implements PackageScanConfig {
    @Override
    public String[] getEntityPackagesToScan() {
        return new String[] {
            "com.myapp.domain.product",
            "com.myapp.domain.order",
            "com.myapp.domain.customer"
        };
    }
}
```

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         HTTP Request                             │
│                    (with tenant context)                         │
└─────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│              TenantIdentifierResolver                            │
│         (implements CurrentTenantIdentifierResolver)             │
│                                                                  │
│   Uses TenantIdProvider to get current tenant UUID               │
└─────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│           TenantConnectionProviderPool                           │
│         (implements MultiTenantConnectionProvider)               │
│                                                                  │
│   Uses TenantDataSourceManager to get correct DataSource         │
└─────────────────────────────────────────────────────────────────┘
                               │
               ┌───────────────┼───────────────┐
               ▼               ▼               ▼
        ┌──────────┐     ┌──────────┐     ┌──────────┐
        │ Tenant A │     │ Tenant B │     │ Tenant C │
        │    DB    │     │    DB    │     │    DB    │
        └──────────┘     └──────────┘     └──────────┘
```

## Configuration

### Application Properties

```yaml
# Admin/Global Database (for tenant registry, etc.)
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/admin_db
    username: admin
    password: ${DB_ADMIN_PASSWORD}

# Tenant Database Template
trishul:
  tenant:
    datasource:
      url-template: jdbc:postgresql://localhost:5432/tenant_{tenantId}
      username: tenant_user
      password-secret-id: /app/tenant-db-password
      pool:
        maximum-pool-size: 10
        minimum-idle: 2
        idle-timeout: 300000
```

## Provisioning New Tenants

When creating a new tenant, you typically need to:

```java
@Service
public class TenantProvisioningService {
    @Autowired private DataSourceQueryRunner queryRunner;
    @Autowired private TenantMigrator migrator;
    @Autowired private TenantService tenantService;

    @Transactional
    public Tenant provisionTenant(String name, URL url) throws SQLException {
        // 1. Create tenant record
        Tenant tenant = tenantService.add(List.of(
            new Tenant().setName(name).setUrl(url)
        )).get(0);

        // 2. Create database
        String dbName = "tenant_" + tenant.getId().toString().replace("-", "_");
        queryRunner.execute("CREATE DATABASE " + dbName);

        // 3. Run migrations
        migrator.migrate(tenant.getId());

        // 4. Mark tenant ready
        tenant.setIsReady(true);
        return tenantService.put(List.of(tenant)).get(0);
    }
}
```

Use `trishul-tenant-persistence-management` for migration support.

## Testing

### Unit Tests

Mock the tenant context:

```java
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock private TenantIdProvider tenantIdProvider;

    @BeforeEach
    void setup() {
        when(tenantIdProvider.getTenantId())
            .thenReturn(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }
}
```

### Integration Tests

```java
@DataJpaTest
@TestPropertySource(properties = {
    "trishul.tenant.test.id=00000000-0000-0000-0000-000000000001"
})
class ProductRepositoryTest {
    // Tests run against test tenant database
}
```

## Auto-Configured Beans

| Bean | Type | Purpose |
|------|------|---------|
| `multiTenantConnectionProvider` | `MultiTenantConnectionProvider` | Routes connections to tenant DBs |
| `currentTenantIdentifierResolver` | `CurrentTenantIdentifierResolver` | Resolves current tenant ID |
| `entityManagerFactory` | `LocalContainerEntityManagerFactoryBean` | Hibernate with multi-tenancy |
| `transactionManager` | `JpaTransactionManager` | Transaction management |

## Related Modules

| Module | Use When |
|--------|----------|
| `trishul-tenant` | You need the core tenant entity |
| `trishul-tenant-auth` | You need tenant context from JWT authentication |
| `trishul-tenant-persistence-management` | You need per-tenant database migrations |
| `trishul-data` | You need DataSource configuration utilities |
| `trishul-secrets` | You need secure credential storage |
