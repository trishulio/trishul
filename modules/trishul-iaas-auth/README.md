# trishul-iaas-auth

IaaS authorization credentials and context management for cloud API access.

> **Use this when**: You need to manage and propagate cloud provider credentials (access keys, session tokens) for IaaS operations.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-iaas-auth</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create authorization credentials
IaasAuthorization auth = new IaasAuthorization()
    .setAccessKeyId("AKIA...")
    .setAccessSecretKey("secret...")
    .setSessionToken("token...")
    .setExpiration(LocalDateTime.now().plusHours(1));

// 2. Store in thread-local context
IaasAuthorizationCredentialsHolder.set(credentials);

// 3. Retrieve for AWS API calls
IaasAuthorizationCredentials creds = IaasAuthorizationCredentialsHolder.get();
```

## When Would I Use This?

### When you need to represent temporary AWS credentials

Use `IaasAuthorization` for STS credentials:

```java
// From STS AssumeRole response
IaasAuthorization auth = new IaasAuthorization()
    .setAccessKeyId(credentials.getAccessKeyId())
    .setAccessSecretKey(credentials.getSecretAccessKey())
    .setSessionToken(credentials.getSessionToken())
    .setExpiration(toLocalDateTime(credentials.getExpiration()));
```

### When you need to propagate credentials in HTTP headers

Use `IaasAuthorizationCredentials` with the standard header:

```java
// Header name constant
String headerName = IaasAuthorizationCredentials.HEADER_NAME_IAAS_TOKEN;  // "X-Iaas-Token"

// Create credentials from token
IaasAuthorizationCredentials creds = new IaasAuthorizationCredentials(token);

// Add to HTTP request
request.setHeader(headerName, creds.toString());
```

### When you need thread-local credential storage

Use `IaasAuthorizationCredentialsHolder`:

```java
// Store credentials for current thread
ThreadLocalIaasAuthorizationCredentialsHolder holder =
    new ThreadLocalIaasAuthorizationCredentialsHolder();

holder.set(credentials);

// Later, retrieve in the same thread
IaasAuthorizationCredentials creds = holder.get();

// Clean up after request
holder.clear();
```

### When you need to fetch credentials from context

Use `IaasAuthorizationFetcher`:

```java
@Service
public class IaasClientService {

    private final IaasAuthorizationFetcher authFetcher;

    public void callAwsApi() {
        IaasAuthorization auth = authFetcher.fetch();

        AWSCredentials creds = new BasicSessionCredentials(
            auth.getAccessKeyId(),
            auth.getAccessSecretKey(),
            auth.getSessionToken()
        );

        // Use credentials for AWS API call
    }
}
```

### When you need a filter to populate credentials holder

Use `IaasAuthorizationCredentialsHolderFilter`:

```java
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<IaasAuthorizationCredentialsHolderFilter> iaasAuthFilter(
            IaasAuthorizationCredentialsHolder holder) {

        FilterRegistrationBean<IaasAuthorizationCredentialsHolderFilter> bean =
            new FilterRegistrationBean<>();
        bean.setFilter(new IaasAuthorizationCredentialsHolderFilter(holder));
        bean.addUrlPatterns("/api/*");
        return bean;
    }
}
```

## IaasAuthorization Fields

| Field | Type | Description |
|-------|------|-------------|
| `accessKeyId` | `String` | AWS access key ID (also used as ID) |
| `accessSecretKey` | `String` | AWS secret access key |
| `sessionToken` | `String` | STS session token |
| `expiration` | `LocalDateTime` | When credentials expire |

## Core Components

### Models

| Class | Purpose |
|-------|---------|
| `IaasAuthorization` | Full credentials with expiration |
| `BaseIaasAuthorization` | Base interface for creation |
| `UpdateIaasAuthorization` | Interface for updates |
| `IaasAuthorizationCredentials` | Token wrapper for HTTP headers |
| `IaasAuthorizationDto` | DTO for API transfer |

### Context Management

| Class | Purpose |
|-------|---------|
| `IaasAuthorizationCredentialsHolder` | Interface for credential storage |
| `ThreadLocalIaasAuthorizationCredentialsHolder` | Thread-local implementation |
| `IaasAuthorizationFetcher` | Interface to fetch credentials |
| `ContextHolderAuthorizationFetcher` | Fetches from context holder |

### Filters

| Class | Purpose |
|-------|---------|
| `IaasAuthorizationCredentialsHolderFilter` | Servlet filter for credential propagation |

### Mappers

| Class | Purpose |
|-------|---------|
| `IaasAuthorizationMapper` | Entity ↔ DTO mapping |

## Auto-Configuration

`IaasAuthAutoConfiguration` registers:

| Bean | Type | Description |
|------|------|-------------|
| `iaasAuthorizationCredentialsHolder` | `ThreadLocalIaasAuthorizationCredentialsHolder` | Thread-local storage |
| `iaasAuthorizationFetcher` | `ContextHolderAuthorizationFetcher` | Context-based fetcher |

## Complete Example

```java
@Component
public class IaasCredentialPropagationFilter extends OncePerRequestFilter {

    private final IaasAuthorizationCredentialsHolder holder;
    private final IaasAuthorizationMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain) {

        try {
            // Extract token from header
            String token = request.getHeader(IaasAuthorizationCredentials.HEADER_NAME_IAAS_TOKEN);

            if (token != null) {
                IaasAuthorizationCredentials creds = new IaasAuthorizationCredentials(token);
                holder.set(creds);
            }

            chain.doFilter(request, response);

        } finally {
            holder.clear();
        }
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-iaas-auth-aws` | AWS STS implementation |
| `trishul-auth` | Core authentication context |
| `trishul-iaas-access` | IAM policy/role models |
