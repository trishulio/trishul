# trishul-auth-aws

AWS Cognito implementation for authentication context extraction from JWT tokens.

> **Use this when**: You use AWS Cognito as your identity provider and need to extract user/tenant context from Cognito JWTs.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-auth-aws</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Extract context from Cognito JWT
Jwt jwt = // from Spring Security
CognitoPrincipalContext context = CognitoPrincipalContext.fromJwt(jwt);

// 2. Access user information
String username = context.getUsername();
List<UUID> tenantIds = context.getTenantIds();
List<String> roles = context.getRoles();
```

## When Would I Use This?

### When you need to extract tenant IDs from Cognito groups

Use `CognitoPrincipalContext.fromJwt()` - maps Cognito groups to tenant IDs:

```java
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain) {

        Jwt jwt = getJwtFromSecurityContext();
        CognitoPrincipalContext context = CognitoPrincipalContext.fromJwt(jwt);

        // Cognito groups are mapped to tenant UUIDs
        List<UUID> tenantIds = context.getTenantIds();

        TenantContextHolder.setTenantIds(tenantIds);
        chain.doFilter(request, response);
    }
}
```

### When you need to extract OAuth scopes as roles

Cognito scopes are mapped to roles:

```java
CognitoPrincipalContext context = CognitoPrincipalContext.fromJwt(jwt);
List<String> roles = context.getRoles();  // From "scope" claim

if (roles.contains("admin")) {
    // Admin-specific logic
}
```

### When you need the Cognito username

```java
CognitoPrincipalContext context = CognitoPrincipalContext.fromJwt(jwt);
String username = context.getUsername();  // From "username" claim
```

## Cognito JWT Claims Mapping

| Cognito Claim | Method | Description |
|---------------|--------|-------------|
| `username` | `getUsername()` | Cognito username |
| `scope` | `getRoles()` | Space-separated OAuth scopes |
| `cognito:groups` | `getTenantIds()` | Group names parsed as UUIDs |

**Expected JWT structure:**
```json
{
  "username": "john.doe",
  "scope": "openid profile admin",
  "cognito:groups": [
    "550e8400-e29b-41d4-a716-446655440000",
    "6ba7b810-9dad-11d1-80b4-00c04fd430c8"
  ]
}
```

## Auto-Configuration

`AuthAwsAutoConfiguration` registers:

| Bean | Type | Description |
|------|------|-------------|
| `cognitoPrincipalContextBuilder` | `CognitoPrincipalContextBuilder` | Builds context from JWT |

## Core Components

| Class | Purpose |
|-------|---------|
| `CognitoPrincipalContext` | Implements `PrincipalContext` with Cognito-specific extraction |
| `CognitoPrincipalContextBuilder` | Factory for creating `CognitoPrincipalContext` |
| `AuthAwsAutoConfiguration` | Spring Boot auto-configuration |

## Complete Example

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(cognitoJwtConverter())
                )
            );
        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> cognitoJwtConverter() {
        return jwt -> {
            CognitoPrincipalContext context = CognitoPrincipalContext.fromJwt(jwt);

            List<GrantedAuthority> authorities = context.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());

            return new JwtAuthenticationToken(jwt, authorities, context.getUsername());
        };
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-auth` | Abstract `PrincipalContext` interface |
| `trishul-iaas-auth-aws` | AWS Cognito IaaS authentication |
| `trishul-tenant` | Tenant context management |
