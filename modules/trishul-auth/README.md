# trishul-auth

> **Use this when**: You need JWT-based authentication with multi-tenant context extraction and thread-safe session storage.

Provides Spring Security OAuth2 JWT integration, `ContextHolder` for accessing the current user/tenant, and security filter chain configuration.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-auth</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Implement PrincipalContextBuilder to extract user info from your JWT
@Component
public class MyPrincipalContextBuilder implements PrincipalContextBuilder {
    @Override
    public PrincipalContext build(Jwt jwt) {
        List<UUID> tenantIds = jwt.getClaimAsStringList("tenant_ids").stream()
            .map(UUID::fromString).toList();
        String username = jwt.getSubject();
        List<String> roles = jwt.getClaimAsStringList("roles");

        return new DefaultPrincipalContext(tenantIds, username, roles);
    }
}

// 2. Inject ContextHolder to access current user/tenant anywhere
@Service
public class MyService {
    @Autowired private ContextHolder contextHolder;

    public void doWork() {
        UUID tenantId = contextHolder.getSessionTenantId();
        String username = contextHolder.getPrincipalContext().getUsername();
        // ...
    }
}

// 3. Clients include tenant ID in header
// curl -H "Authorization: Bearer $JWT" -H "X-TENANT-ID: $TENANT_ID" /api/resource
```

## When Would I Use This?

### When you need to access the current user's context in your services

Inject `ContextHolder`:

```java
@Service
public class OrderService {
    @Autowired private ContextHolder contextHolder;

    public Order createOrder(OrderRequest request) {
        // Get current tenant for the request
        UUID tenantId = contextHolder.getSessionTenantId();

        // Get user information
        PrincipalContext ctx = contextHolder.getPrincipalContext();
        String createdBy = ctx.getUsername();
        List<String> roles = ctx.getRoles();

        // Check if user can access multiple tenants
        if (ctx.getTenantIds().size() > 1) {
            // Handle multi-tenant user
        }

        return new Order()
            .setTenantId(tenantId)
            .setCreatedBy(createdBy);
    }
}
```

### When you need to extract tenant/user info from JWT claims

Implement `PrincipalContextBuilder`:

```java
// For AWS Cognito
@Component
public class CognitoPrincipalContextBuilder implements PrincipalContextBuilder {
    @Override
    public PrincipalContext build(Jwt jwt) {
        // Extract tenant IDs from custom claim
        String tenantClaim = jwt.getClaimAsString("custom:tenant_ids");
        List<UUID> tenantIds = Arrays.stream(tenantClaim.split(","))
            .map(UUID::fromString)
            .toList();

        // Extract username and roles
        String username = jwt.getClaimAsString("cognito:username");
        List<String> roles = jwt.getClaimAsStringList("cognito:groups");

        return new DefaultPrincipalContext(tenantIds, username, roles);
    }
}
```

### When you need to validate tenant access

The `ContextHolder` validates that the requested tenant ID is in the user's allowed list:

```java
// If user's JWT contains tenant_ids: [tenant-A, tenant-B]

contextHolder.setSessionTenantId(tenantA);  // OK
contextHolder.setSessionTenantId(tenantB);  // OK
contextHolder.setSessionTenantId(tenantC);  // Throws IllegalArgumentException
```

### When clients need to specify which tenant to use

Clients include the `X-TENANT-ID` header:

```bash
# User with access to multiple tenants selects one
curl -X GET "https://api.example.com/api/products" \
     -H "Authorization: Bearer $JWT_TOKEN" \
     -H "X-TENANT-ID: 550e8400-e29b-41d4-a716-446655440000"
```

The `ContextHolderFilter` automatically:
1. Extracts JWT from Spring Security's `SecurityContext`
2. Builds `PrincipalContext` using your `PrincipalContextBuilder`
3. Sets session tenant ID from `X-TENANT-ID` header
4. Clears context after request completes

### When you need default public endpoints

Auto-configured `WebSecurityConfig` permits these paths without authentication:

| Path | Purpose |
|------|---------|
| `/actuator/**` | Health checks, metrics |
| `/public/**` | Public content |
| `/static/**` | Static assets |
| `/api-docs/**` | OpenAPI spec |
| `/swagger-ui/**` | Swagger UI |
| `/swagger-resources/**` | Swagger resources |
| `/swagger-ui.html` | Swagger entry point |

All other paths require authentication.

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     HTTP Request                                 │
│                  Authorization: Bearer JWT                       │
│                  X-TENANT-ID: uuid                               │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                 Spring Security Filter Chain                     │
│                                                                  │
│   JwtDecoder → OAuth2ResourceServer → Authentication            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                   ContextHolderFilter                            │
│                                                                  │
│   1. Get JWT from SecurityContext                                │
│   2. PrincipalContextBuilder.build(jwt)                          │
│   3. contextHolder.setContext(principalCtx)                      │
│   4. contextHolder.setSessionTenantId(X-TENANT-ID header)        │
│   5. try { filterChain.doFilter() } finally { holder.clear() }   │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Your Controllers/Services                     │
│                                                                  │
│   @Autowired ContextHolder contextHolder;                        │
│   UUID tenantId = contextHolder.getSessionTenantId();            │
│   String user = contextHolder.getPrincipalContext().getUsername();│
└─────────────────────────────────────────────────────────────────┘
```

## Configuration

### Required: JWT Configuration

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: https://cognito-idp.us-east-1.amazonaws.com/us-east-1_xxx/.well-known/jwks.json
          # OR
          issuer-uri: https://cognito-idp.us-east-1.amazonaws.com/us-east-1_xxx
```

## Testing

### Mock Context in Unit Tests

```java
@Test
void testWithMockedContext() {
    ThreadLocalContextHolder holder = new ThreadLocalContextHolder();
    PrincipalContext mockCtx = mock(PrincipalContext.class);
    when(mockCtx.getTenantIds()).thenReturn(List.of(TENANT_ID));
    when(mockCtx.getUsername()).thenReturn("testuser");

    holder.setContext(mockCtx);
    holder.setSessionTenantId(TENANT_ID);

    try {
        // Test code that uses ContextHolder
        myService.doWork();
    } finally {
        holder.clear();  // Always clean up
    }
}
```

## Key Interfaces

| Interface | Purpose |
|-----------|---------|
| `PrincipalContext` | Read-only access to user's tenants, username, roles |
| `ContextHolder` | Get/set principal context and session tenant ID |
| `PrincipalContextBuilder` | Build `PrincipalContext` from JWT |

## Auto-Configured Beans

| Bean | Condition |
|------|-----------|
| `ThreadLocalContextHolder` | No existing `ContextHolder` bean |
| `ContextHolderFilter` | No existing filter bean |
| `JwtDecoder` | No existing `JwtDecoder` bean |
| `SecurityFilterChain` | Default security configuration |

## Related Modules

| Module | Use When |
|--------|----------|
| `trishul-auth-aws` | You use AWS Cognito for JWT validation |
| `trishul-tenant` | You need the tenant entity |
| `trishul-tenant-persistence` | You need multi-tenant database routing |
| `trishul-tenant-auth` | You need tenant context extraction utilities |
