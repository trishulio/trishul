# trishul-tenant-auth

Tenant ID provider that extracts the current tenant from authentication context.

> **Use this when**: You need to resolve the current tenant ID from the authenticated user's session for multi-tenant database routing.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-tenant-auth</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Auto-configuration sets up the provider
@Autowired
private TenantIdProvider tenantIdProvider;

// 2. Get current tenant ID (from auth context or default)
UUID tenantId = tenantIdProvider.getTenantId();

// 3. Use for database routing, data filtering, etc.
DataSource tenantDs = dataSourceManager.getDataSource(tenantId);
```

## When Would I Use This?

### When you need to resolve tenant ID from authentication context

Use `ContextHolderTenantIdProvider`:

```java
@Service
public class TenantAwareService {
    
    private final TenantIdProvider tenantIdProvider;
    
    public List<Order> getCurrentTenantOrders() {
        UUID tenantId = tenantIdProvider.getTenantId();
        return orderRepository.findByTenantId(tenantId);
    }
}
```

### When you need a fallback to admin tenant

The provider returns admin tenant ID when no session tenant is set:

```java
// How it works internally:
public UUID getTenantId() {
    UUID tenantId = contextHolder.getSessionTenantId();
    
    if (tenantId != null) {
        return tenantId;  // User's session tenant
    }
    
    return defaultTenantId;  // Admin tenant fallback
}
```

### When you need tenant-scoped Hibernate multi-tenancy

Integrate with Hibernate's `CurrentTenantIdentifierResolver`:

```java
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    
    private final TenantIdProvider tenantIdProvider;
    
    @Override
    public String resolveCurrentTenantIdentifier() {
        return tenantIdProvider.getTenantId().toString();
    }
    
    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
```

### When you need to scope data access to current tenant

Use the provider in repositories or services:

```java
@Repository
public class TenantScopedRepository<T> {
    
    private final TenantIdProvider tenantIdProvider;
    private final EntityManager entityManager;
    
    public List<T> findAll() {
        UUID tenantId = tenantIdProvider.getTenantId();
        
        return entityManager.createQuery(
            "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.tenantId = :tenantId",
            entityClass)
            .setParameter("tenantId", tenantId)
            .getResultList();
    }
}
```

## How It Works

```
┌─────────────────────────────────────────────────────────────────┐
│                     HTTP Request                                 │
│                  (with JWT/Session)                              │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                     ContextHolder                                │
│              Extracts tenant ID from JWT claims                 │
│              (e.g., cognito:groups → tenant UUIDs)              │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│              ContextHolderTenantIdProvider                       │
│                                                                  │
│   if (contextHolder.getSessionTenantId() != null)               │
│       return sessionTenantId;                                   │
│   else                                                          │
│       return adminTenant.getId();  // fallback                  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│              Hibernate / DataSource Routing                      │
│                 Uses tenant ID for schema                        │
└─────────────────────────────────────────────────────────────────┘
```

## Core Components

| Class | Purpose |
|-------|---------|
| `ContextHolderTenantIdProvider` | Resolves tenant ID from `ContextHolder` |
| `TenantAuthAutoConfiguration` | Spring Boot auto-configuration |

## Constructor Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `contextHolder` | `ContextHolder` | Holds authentication context |
| `adminTenant` | `AdminTenant` | Default/fallback tenant |

## Auto-Configuration

`TenantAuthAutoConfiguration` registers:

| Bean | Type | Description |
|------|------|-------------|
| `tenantIdProvider` | `ContextHolderTenantIdProvider` | Tenant ID resolver |

## Complete Example

```java
@Configuration
@Import(TenantAuthAutoConfiguration.class)
public class MultiTenantConfig {
    
    @Bean
    public AdminTenant adminTenant() {
        return new AdminTenant(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }
}

@Service
public class OrderService {
    
    private final TenantIdProvider tenantIdProvider;
    private final OrderRepository orderRepository;
    
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        UUID tenantId = tenantIdProvider.getTenantId();
        
        Order order = new Order()
            .setTenantId(tenantId)
            .setItems(request.getItems())
            .setTotal(calculateTotal(request));
        
        return orderRepository.save(order);
    }
    
    public List<Order> getMyOrders() {
        UUID tenantId = tenantIdProvider.getTenantId();
        return orderRepository.findByTenantId(tenantId);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-auth` | Core `ContextHolder` for authentication |
| `trishul-tenant` | Tenant entity and `TenantIdProvider` interface |
| `trishul-tenant-persistence` | Hibernate multi-tenancy integration |
